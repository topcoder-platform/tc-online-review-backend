/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.review;

import com.topcoder.onlinereview.component.grpcclient.review.ReviewServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.grpc.review.proto.CommentTypeProto;
import com.topcoder.onlinereview.grpc.review.proto.ReviewCommentProto;
import com.topcoder.onlinereview.grpc.review.proto.ReviewItemCommentProto;
import com.topcoder.onlinereview.grpc.review.proto.ReviewItemProto;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * This class is responsible for creating, updating, searching and deleting the review entities
 * stored in the Informix database. Additionally, application users can also add comment for review
 * and item, and get all the comment types from the persistence.
 *
 * <p>Thread Safety: This class is mutable, since it has mutable cachedCommentTypes. But thus class
 * is thread safe when entities passed to it are used by the caller in thread safe manner, since
 * cachedCommentTypes is used in the synchronized getAllCommentTypes() method only.
 *
 * <p>Sample usage:
 *
 * <pre>
 * // first create an instance of InformixReviewPersistence class
 * ReviewPersistence persistence = new InformixReviewPersistence();
 * // get all comment types
 * CommentType[] commentTypes = persistence.getAllCommentTypes();
 * // create the review in the database
 * // first initialize a review object
 * Review review = getSampleReview();
 * persistence.createReview(review, &quot;someReviewer&quot;);
 * // add comment for review
 * // first initialize a comment object
 * Comment reviewComment = getSampleComment();
 * persistence.addReviewComment(1, reviewComment, &quot;admin&quot;);
 * // add comment for item
 * // first initialize a comment object
 * Comment itemComment = getSampleComment();
 * persistence.addItemComment(1, itemComment, &quot;admin&quot;);
 * // update the review
 * review.setCommitted(true);
 * persistence.updateReview(review, &quot;someReviewer&quot;);
 * // get the review
 * review = persistence.getReview(1);
 * // search reviews
 * // search for the reviews which have been committed
 * Filter filter = new EqualToFilter(&quot;committed&quot;, 1);
 * Review[] reviews = persistence.searchReviews(filter, true);
 * // remove review
 * persistence.removeReview(review.getId(), &quot;admin&quot;);
 * </pre>
 *
 * <p>Changes in 1.2:
 *
 * <ul>
 *   <li>Added removeReview() method.
 *   <li>Updated deleteReviewItems() to remove associated uploads.
 *   <li>Specified generic parameters for all generic types in the code.
 *   <li>Made class thread safe by synchronizing getAllCommentTypes() methods.
 * </ul>
 *
 * @author woodjhon, urtks, George1, saarixx, TCSDEVELOPER
 * @version 1.2.4
 */
@Slf4j
@Component
public class ReviewPersistence {

  @Autowired
  ReviewServiceRpc reviewServiceRpc;

  /** Cached comment types. Set and read in the getAllCommentTypes() method. */
  private static CommentType[] cachedCommentTypes = null;

  /**
   * The searchBuilder is used to execute the database search string to retrieve an array of review
   * ids that matches the specified filter. The constructor will first create SearchBundleManager
   * from manager namespace, and then get searchBundle from manager by configured searchBundleName.
   * It's expected that the "alias" and "context" properties in the SearchBundleManager should be
   * configured properly.
   *
   * <p>The context property for SearchBundleManager is: <code>
   * select review_id from review, scorecard, resource
   * where review.scorecard_id=scorecard.scorecard_id and
   * review.resource_id=resource.resource_id and</code>. Following is the alias mapping:
   *
   * <ol>
   *   <li>scorecardType --- scorecard.scorecard_type_id
   *   <li>submission --- review.submission_id
   *   <li>projectPhase --- review.project_phase_id
   *   <li>reviewer --- review.resource_id
   *   <li>project --- resource.project_id
   *   <li>committed --- review.committed
   * </ol>
   */

  /**
   * Check if the given review is valid. A Review object is considered valid if:
   *
   * <ol>
   *   <li>author is positive;
   *   <li>scorecard is positive;
   *   <li>all the review comments it contains are valid;
   *   <li>all the review items it contains are valid;
   *   <li>there are no duplicate entities(review comments, review items, review item comments) by
   *       reference in it.
   * </ol>
   *
   * @param review the review to check
   * @throws IllegalArgumentException if the given review is invalid
   */
  private static void assertReviewValid(Review review) {
    Helper.assertLongPositive(review.getAuthor(), "author of review");
    Helper.assertLongPositive(review.getScorecard(), "scorecard of review");
    Comment[] comments = review.getAllComments();
    for (int i = 0; i < comments.length; ++i) {
      // comments[i] is never null.
      assertCommentValid(comments[i]);
    }
    Item[] items = review.getAllItems();
    for (int i = 0; i < items.length; ++i) {
      // items[i] is never null.
      assertItemValid(items[i]);
    }
    // Note:
    // 1. We do not need to check duplicate Item references, since the
    // implementation of Review class guarantees that there are no duplicate
    // Item references in a Review object.
    // 2. However we do need to check duplicate Comment references, although
    // implementation of Review class and Item class guarantees that there
    // are no duplicate Comment references in one Review or Item object.
    // There are two reasons: 1) review comment and review item comment are
    // both of Comment type, they can reference the same object. 2) review
    // item comments in different item can reference the same object.
    // check if there are duplicate references for Comment objects.
    Set<Comment> commentSet = new HashSet<>();
    for (int i = 0; i < comments.length; ++i) {
      if (commentSet.contains(comments[i])) {
        throw new IllegalArgumentException("Duplicate Comment references found.");
      }
      commentSet.add(comments[i]);
    }
    for (int i = 0; i < items.length; ++i) {
      Comment itemComments[] = items[i].getAllComments();
      for (int j = 0; j < itemComments.length; ++j) {
        if (commentSet.contains(itemComments[j])) {
          throw new IllegalArgumentException("Duplicate Comment references found.");
        }
        commentSet.add(itemComments[j]);
      }
    }
  }

  /**
   * Check if the given item is valid. An Item object is considered valid if:
   *
   * <ol>
   *   <li>question is positive;
   *   <li>answer is not null and of String type;
   *   <li>all the review item comments it contains are valid.
   * </ol>
   *
   * @param item the item to check
   * @throws IllegalArgumentException if the given item is invalid
   */
  private static void assertItemValid(Item item) {
    Helper.assertLongPositive(item.getQuestion(), "question of item");
    // check if the answer is null or not of String type.
    if (!(item.getAnswer() instanceof String)) {
      throw new IllegalArgumentException("answer of item is null or not of String type.");
    }
    Comment[] comments = item.getAllComments();
    for (int i = 0; i < comments.length; ++i) {
      // comments[i] is never null.
      assertCommentValid(comments[i]);
    }
  }

  /**
   * Check if the given comment(review comment or review item comment) is valid. An Comment object
   * is considered valid if:
   *
   * <ol>
   *   <li>author is positive;
   *   <li>comment type is not null and valid;
   *   <li>content is not null;
   *   <li>extra info is of String type.
   * </ol>
   *
   * @param comment the comment to check
   * @throws IllegalArgumentException if the given comment is invalid
   */
  private static void assertCommentValid(Comment comment) {
    Helper.assertLongPositive(comment.getAuthor(), "author of comment");
    Helper.assertObjectNotNull(comment.getCommentType(), "type of comment");
    assertCommentTypeValid(comment.getCommentType());
    Helper.assertObjectNotNull(comment.getComment(), "content of comment");
    if (comment.getExtraInfo() != null && !(comment.getExtraInfo() instanceof String)) {
      throw new IllegalArgumentException("extra info of comment is not of String type.");
    }
  }

  /**
   * Check if the given comment type is valid. An CommentType object is considered valid if:
   *
   * <ol>
   *   <li>id is positive;
   *   <li>name is not null.
   * </ol>
   *
   * @param commentType the commentType to check
   * @throws IllegalArgumentException if the given comment type is invalid
   */
  private static void assertCommentTypeValid(CommentType commentType) {
    Helper.assertLongPositive(commentType.getId(), "id of comment type");
    Helper.assertObjectNotNull(commentType.getName(), "name of comment type");
  }

  /**
   * Set the new entity ids finally according to the change table when no exception occurred.
   *
   * @param changeTable the change table
   */
  private static void setEntityIds(Map<Object, Long> changeTable) {
    // enumerate each pair in the change table.
    for (Entry<Object, Long> entry : changeTable.entrySet()) {
      // get the new id.
      long newId = entry.getValue();
      // set the new Id according to the type of the object
      if (entry.getKey() instanceof Review) {
        ((Review) entry.getKey()).setId(newId);
      } else if (entry.getKey() instanceof Comment) {
        ((Comment) entry.getKey()).setId(newId);
      } else if (entry.getKey() instanceof Item) {
        ((Item) entry.getKey()).setId(newId);
      }
    }
  }

  /**
   * Create the review in the persistence. This method is also responsible for creating the
   * associated Comment, and Item, but not for CommentType.
   *
   * <p>This method will check if the given review is valid before accessing the database.
   *
   * <p>A Review object is considered valid if:
   *
   * <ol>
   *   <li>author is positive;
   *   <li>scorecard is positive;
   *   <li>all the review comments it contains are valid;
   *   <li>all the review items it contains are valid;
   *   <li>there are no duplicate entity references (review comments, review items, review item
   *       comments) in it.
   * </ol>
   *
   * <p>An Item object is considered valid if:
   *
   * <ol>
   *   <li>question is positive;
   *   <li>answer is not null and of String type;
   *   <li>all the review item comments it contains are valid.
   * </ol>
   *
   * <p>An Comment object is considered valid if:
   *
   * <ol>
   *   <li>author is positive;
   *   <li>comment type is not null and valid;
   *   <li>content is not null;
   *   <li>extra info is null or of String type.
   * </ol>
   *
   * <p>An CommentType object is considered valid if:
   *
   * <ol>
   *   <li>id is positive;
   *   <li>name is not null.
   * </ol>
   *
   * @param review the Review instance to create
   * @param operator the operator who creates the review instance
   * @exception IllegalArgumentException if either of arguments is null, or operator is empty string
   *     or review fails to pass the validation.
   * @exception DuplicateReviewEntityException if review id already exists.
   * @exception ReviewPersistenceException if any other error occurred.
   */
  @Transactional
  public void createReview(Review review, String operator) throws ReviewPersistenceException {
    Helper.assertObjectNotNull(review, "review");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    assertReviewValid(review);
    // create a change table to record the new Ids for Review, Item or
    // Comment.
    Map<Object, Long> changeTable = new HashMap<>();

    Date createDate = new Date();
    log.debug(new LogMessage(null, operator, "creating new Review.").toString());
    try {
      // check whether the review id is already in the database
      if (review.getId() > 0) {
        if (reviewServiceRpc.isReviewExists(review.getId())) {
          log.error(
              new LogMessage(
                      review.getId(),
                      operator,
                      "The review with same id [" + review.getId() + "] already exists.")
                  .toString());
          throw new DuplicateReviewEntityException(
              "The review with same id [" + review.getId() + "] already exists.", review.getId());
        }
      }
      // create the review.
      createReview(review, operator, createDate, changeTable);
    } catch (ReviewPersistenceException e) {
      log.error(
          new LogMessage(null, operator, "Error occurs during create new Review.", e).toString());
      throw e;
    }
    // set the creation/modification user and date when no exception
    // occurred.
    review.setCreationUser(operator);
    review.setCreationTimestamp(createDate);
    review.setModificationUser(operator);
    review.setModificationTimestamp(createDate);
    // set the new ids finally when no exception occurred.
    setEntityIds(changeTable);
  }

  /**
   * Create the review in the database.
   *
   * @param review the Review instance to create
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the creation
   */
  private void createReview(Review review, String operator, Date date, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    long newId = reviewServiceRpc.createReview(review, operator, date);
    // add the review and newId pair to the change table.
    changeTable.put(review, newId);

    // create review comments
    createReviewComments(
        review.getAllComments(),
        makeAscendingLongArray(review.getNumberOfComments()),
        newId,
        operator,
        changeTable);
    // create review items
    createReviewItems(
        review.getAllItems(),
        makeAscendingLongArray(review.getNumberOfItems()),
        newId,
        operator,
        changeTable);
  }

  /**
   * Create review comments in database.
   *
   * @param comments the comments to create
   * @param indices the positions of each comment in their corresponding reviews
   * @param reviewId the review id containing these comments
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the creation
   */
  private void createReviewComments(
      Comment[] comments,
      Long[] indices,
      Long reviewId,
      String operator,
      Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    try {
      // enumerate each review comment
      for (int i = 0; i < comments.length; ++i) {
        Comment comment = comments[i];
        long newId = reviewServiceRpc.createReviewComment(comment, reviewId, indices[i], operator, new Date());
        // add the comment and newId pair to the change table.
        changeTable.put(comment, newId);
      }
    } catch (StatusRuntimeException e) {
      throw new ReviewPersistenceException("Unable to generate id for review comment.", e);
    }
  }

  /**
   * Create review items in database.
   *
   * @param items the review items to create
   * @param indices the positions of each item in their corresponding reviews
   * @param reviewId the review id containing these items
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the creation
   */
  private void createReviewItems(
      Item[] items, Long[] indices, Long reviewId, String operator, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    try {
      // enumerate each review item
      for (int i = 0; i < items.length; ++i) {
        Item item = items[i];
        // generate id for the review item
        Long newId = reviewServiceRpc.createReviewItem(item, reviewId, indices[i], operator, new Date());
        changeTable.put(item, newId);
        // create review item comments
        createReviewItemComments(
            item.getAllComments(),
            makeAscendingLongArray(item.getNumberOfComments()),
            newId,
            operator,
            changeTable);
      }
    } catch (StatusRuntimeException e) {
      throw new ReviewPersistenceException("Unable to generate id for review item.", e);
    }
  }

  /**
   * Create review item comments in database.
   *
   * @param comments the review item comments to create
   * @param indices the positions of each comment in their corresponding items
   * @param itemId the review item id containing these comments
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the creation
   */
  private void createReviewItemComments(
      Comment[] comments,
      Long[] indices,
      Long itemId,
      String operator,
      Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    try {
      // enumerate each review item comment
      for (int i = 0; i < comments.length; ++i) {
        Comment comment = comments[i];
        // generate id for the review item comment
        Long newId = reviewServiceRpc.createReviewItemComment(comment, itemId, indices[i], operator, new Date());
        // add the comment and newId pair to the change table.
        changeTable.put(comment, newId);
      }
    } catch (StatusRuntimeException e) {
      throw new ReviewPersistenceException("Unable to generate id for review item comment.", e);
    }
  }

  /**
   * Update the review in the persistence. The update method is also responsible for creating,
   * deleting, updating the associated Items, Comments, and Item Comments.
   *
   * <p>This method will check if the given review is valid before accessing the database.
   *
   * <p>Please refer to the documentation for createReview method to see when a review is considered
   * to be valid.
   *
   * @param review the Review instance to update
   * @param operator the operator who updates the Review instance
   * @exception IllegalArgumentException if either of arguments is null, operator is empty string,
   *     or review id is negative, or review fails to pass the validation.
   * @exception ReviewEntityNotFoundException if given review id does not exist
   * @exception ReviewPersistenceException if any other error occurred.
   */
  @Transactional
  public void updateReview(Review review, String operator) throws ReviewPersistenceException {
    Helper.assertObjectNotNull(review, "review");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    Helper.assertLongPositive(review.getId(), "review id");
    assertReviewValid(review);
    // create a change table to record the new Ids for Review, Item or
    // Comment.
    Map<Object, Long> changeTable = new HashMap<Object, Long>();
    // modifyDate will contain the modify_date retrieved from database.
    Date modifyDate = new Date();
    log.debug(new LogMessage(review.getId(), operator, "Update Review.").toString());
    try {
      // create the connection
      // check whether the review id is already in the database
      if (!reviewServiceRpc.isReviewExists(review.getId())) {
        log.error(
            new LogMessage(
                    review.getId(),
                    operator,
                    "The review id [" + review.getId() + "] does not exist in the database.")
                .toString());
        throw new ReviewEntityNotFoundException(
            "The review id [" + review.getId() + "] does not exist in the database.",
            review.getId());
      }
      // update the review.
      updateReview(review, operator, modifyDate, changeTable);
    } catch (ReviewPersistenceException e) {
      log.error(
          new LogMessage(review.getId(), operator, "Error occurs during update Review.", e)
              .toString());
      throw e;
    }
    // set the modification user and date when no exception
    // occurred.
    review.setModificationUser(operator);
    review.setModificationTimestamp(modifyDate);
    // set the new ids finally when no exception occurred.
    setEntityIds(changeTable);
  }

  /**
   * Update the review in the database.
   *
   * @param review the review to update
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReview(Review review, String operator, Date date, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    Long reviewId = review.getId();
    reviewServiceRpc.updateReview(review, operator, date);
    // update review comments
    updateReviewComments(
        review.getAllComments(),
        makeAscendingLongArray(review.getNumberOfComments()),
        reviewId,
        operator,
        changeTable);
    // update review items
    updateReviewItems(
        review.getAllItems(),
        makeAscendingLongArray(review.getNumberOfItems()),
        reviewId,
        operator,
        changeTable);
  }

  /**
   * Update review comments in the database.
   *
   * @param comments the review comments to update
   * @param indices the positions of each comment in their corresponding reviews
   * @param reviewId the review id containing these comments
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReviewComments(
      Comment[] comments,
      Long[] indices,
      Long reviewId,
      String operator,
      Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    // get a set containing all the review comment ids
    // associated with this review id
    Set<Long> reviewCommentIDs = getReviewCommentIDs(reviewId);
    // create a list containing all the review comments that should be
    // created and another list containing the indices of them.
    List<Comment> createReviewList = new ArrayList<Comment>();
    List<Long> createIndexList = new ArrayList<Long>();
    // enumerate each comment
    for (int i = 0; i < comments.length; ++i) {
      Comment comment = comments[i];
      Long commentId = comment.getId();
      // check if the comment id already exists in the database
      if (reviewCommentIDs.contains(commentId)) {
        reviewCommentIDs.remove(commentId);
        // update the review comment
        updateReviewComment(comment, indices[i], operator);
      } else {
        // add to create list
        createReviewList.add(comment);
        createIndexList.add(indices[i]);
      }
    }
    // create the review comments in the createList
    createReviewComments(
        createReviewList.toArray(new Comment[] {}),
        createIndexList.toArray(new Long[] {}),
        reviewId,
        operator,
        changeTable);
    // delete the reviews comments left in the reviewCommentIDs set
    deleteReviewComments(reviewCommentIDs);
  }

  /**
   * Update the review comment in the database.
   *
   * @param comment the review comment to update
   * @param index the position of the comment in its review
   * @param operator the operator who creates the review instance
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReviewComment(Comment comment, Long index, String operator)
      throws ReviewPersistenceException {
    // update the review comment in database
    reviewServiceRpc.updateReviewComment(comment, index, operator, new Date());
  }

  /**
   * Update review items in the database.
   *
   * @param items the review items to update
   * @param indices the positions of each item in their corresponding reviews
   * @param reviewId the review id containing these items
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReviewItems(
      Item[] items, Long[] indices, Long reviewId, String operator, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    // get a set containing all the review item ids
    // associated with this review id
    Set<Long> reviewItemIDs = getReviewItemIDs(reviewId);
    // create a list containing all the review items that should be
    // created and another list containing the indices of them.
    List<Item> createItemList = new ArrayList<Item>();
    List<Long> createIndexList = new ArrayList<Long>();
    // enumerate each item
    for (int i = 0; i < items.length; ++i) {
      Item item = items[i];
      Long itemId = item.getId();
      // check if the item id already exists in the database
      if (reviewItemIDs.contains(itemId)) {
        reviewItemIDs.remove(itemId);
        // update the review item
        updateReviewItem(item, indices[i], operator, changeTable);
      } else {
        // add to create list
        createItemList.add(item);
        createIndexList.add(indices[i]);
      }
    }
    // create the review items in the create list
    createReviewItems(
        createItemList.toArray(new Item[] {}),
        createIndexList.toArray(new Long[] {}),
        reviewId,
        operator,
        changeTable);
    // delete the review items left in the reviewItemIDs set
    deleteReviewItems(reviewItemIDs);
  }

  /**
   * Update review item in the database.
   *
   * @param item the review item to update
   * @param index the position of the item in its review
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReviewItem(
      Item item, Long index, String operator, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    reviewServiceRpc.updateReviewItem(item, index, operator, new Date());
    // update the review item comments
    updateReviewItemComments(
        item.getAllComments(),
        makeAscendingLongArray(item.getNumberOfComments()),
        item.getId(),
        operator,
        changeTable);
  }

  /**
   * Update review item comments in the database.
   *
   * @param comments the review item comments to update
   * @param indices the positions of each comment in their corresponding items
   * @param itemId the review item id containing these comments
   * @param operator the operator who creates the review instance
   * @param changeTable the change table
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReviewItemComments(
      Comment[] comments,
      Long[] indices,
      Long itemId,
      String operator,
      Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    // get a set containing all the review item comment ids
    // associated with this review id
    Set<Long> reviewItemCommentIDs = getReviewItemCommentIDs(itemId);
    // create a list containing all the review item comments that should be
    // created and another list containing the indices of them.
    List<Comment> createCommentList = new ArrayList<Comment>();
    List<Long> createIndexList = new ArrayList<Long>();
    // enumerate each comment
    for (int i = 0; i < comments.length; ++i) {
      Comment comment = comments[i];
      Long commentId = comment.getId();
      // check if the comment id already exists in the database
      if (reviewItemCommentIDs.contains(commentId)) {
        reviewItemCommentIDs.remove(commentId);
        // update the review item comment
        updateReviewItemComment(comment, indices[i], operator);
      } else {
        // add to create list
        createCommentList.add(comment);
        createIndexList.add(indices[i]);
      }
    }
    // create the review item comments in the create list
    createReviewItemComments(
        createCommentList.toArray(new Comment[] {}),
        createIndexList.toArray(new Long[] {}),
        itemId,
        operator,
        changeTable);
    // delete the reviews item comments left in the reviewCommentIDs set
    deleteReviewItemComments(reviewItemCommentIDs);
  }

  /**
   * Update the review item comment in the database.
   *
   * @param comment the review item comment to update
   * @param index the position of the comment in its item
   * @param operator the operator who creates the review instance
   * @throws ReviewPersistenceException if any error occurs during the update
   */
  private void updateReviewItemComment(Comment comment, Long index, String operator)
      throws ReviewPersistenceException {
    reviewServiceRpc.updateReviewItemComment(comment, index, operator, new Date());
  }

  /**
   * get all the review comment ids associated with the review id.
   *
   * @param reviewId the given review id
   * @return A set that contains the review comment ids
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Set<Long> getReviewCommentIDs(Long reviewId) {
    List<Long> result = reviewServiceRpc.getReviewCommentIds(reviewId);
    // build the set from result list
    Set<Long> reviewCommentIDs = new HashSet<>();
    for (Long id : result) {
      reviewCommentIDs.add(id);
    }
    return reviewCommentIDs;
  }

  /**
   * get all the review item ids associated with the review id.
   *
   * @param reviewId the given review id
   * @return A set that contains the review item ids
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Set<Long> getReviewItemIDs(Long reviewId) throws ReviewPersistenceException {
    List<Long> result = reviewServiceRpc.getReviewItemIds(reviewId);
    // build the set from result list
    Set<Long> reviewItemIDs = new HashSet<>();
    for (Long id : result) {
      reviewItemIDs.add(id);
    }
    return reviewItemIDs;
  }

  /**
   * get all the review item comment ids associated with the item id.
   *
   * @param itemId the given item id
   * @return A set that contains the review item comment ids
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Set<Long> getReviewItemCommentIDs(Long itemId) {
    List<Long> result = reviewServiceRpc.getReviewItemCommentIds(itemId);
    // build the set from result list
    Set<Long> reviewItemCommentIDs = new HashSet<>();
    for (Long id : result) {
      reviewItemCommentIDs.add(id);
    }
    return reviewItemCommentIDs;
  }

  /**
   * Make an ascending Long array starting from 0 with step 1.
   *
   * @param length the length of the generated array
   * @return an ascending Long array
   */
  private static Long[] makeAscendingLongArray(int length) {
    Long[] array = new Long[length];
    for (int i = 0; i < array.length; ++i) {
      array[i] = (long) i;
    }
    return array;
  }

  /**
   * Delete review comment ids in the database.
   *
   * @param reviewCommentIDs A set that contains review comment ids to delete
   * @throws ReviewPersistenceException if any error occurs during the deletion
   */
  private void deleteReviewComments(Set<Long> reviewCommentIDs) throws ReviewPersistenceException {
    reviewServiceRpc.deleteReviewComments(new ArrayList<>(reviewCommentIDs));
  }

  /**
   * Delete review item ids in the database.
   *
   * @param reviewItemIDs A set that contains review item ids to delete
   * @throws ReviewPersistenceException if any error occurs during the deletion
   */
  private void deleteReviewItems(Set<Long> reviewItemIDs) throws ReviewPersistenceException {
    reviewServiceRpc.deleteReviewItems(new ArrayList<>(reviewItemIDs));
  }

  /**
   * Delete review item comment ids in the database.
   *
   * @param reviewItemCommentIDs A set that contains review item comment ids to delete
   * @throws ReviewPersistenceException if any error occurs during the deletion
   */
  private void deleteReviewItemComments(Set<Long> reviewItemCommentIDs)
      throws ReviewPersistenceException {
    reviewServiceRpc.deleteReviewItemComments(new ArrayList<>(reviewItemCommentIDs));
  }

  /**
   * Get the Review instance from the persistence with given id.
   *
   * @param id the review id
   * @return the Review instance matching the given id
   * @exception IllegalArgumentException if id is not positive
   * @exception ReviewEntityNotFoundException if given id does not exist in the database
   * @exception ReviewPersistenceException if any other error occurred.
   */
  @Transactional
  public Review getReview(long id) throws ReviewPersistenceException {
    Helper.assertLongPositive(id, "id");
    log.debug(new LogMessage(id, null, "Retrieve Review.").toString());
    try {
      // check whether the review id is already in the database
      if (!reviewServiceRpc.isReviewExists(id)) {
        log.error(
            new LogMessage(id, null, "The review id [" + id + "] does not exist in the database.")
                .toString());
        throw new ReviewEntityNotFoundException(
            "The review id [" + id + "] does not exist in the database.", id);
      }
      // get the review in the database
      Review review = getReviewsComplete(newArrayList(id))[0];
      return review;
    } catch (ReviewPersistenceException e) {
      log.error(new LogMessage(id, null, "Error occurs during retrieve Review.", e).toString());
      throw e;
    }
  }

  /**
   * Search for the matching review in the persistence with given filter. If complete is false, the
   * associated items and comments of the matching review will not be retrieved.
   *
   * <p>In the version 1.0, the filter supports at most five fields:
   *
   * <ol>
   *   <li>scorecardType --- the score card type, must be java.Long type
   *   <li>submission --- the review submission id, must be java.Long type
   *   <li>projectPhase - the project phase id, must be Long type.
   *   <li>reviewer --- the author of the review, must be java.Long type
   *   <li>project --- the project id of the review, must be java.Long type
   *   <li>committed --- indicate if the review has been committed, must be java.lang.Integer type.
   *       Either new Integer(1) representing committed, or new Integer(0), represent not committed
   * </ol>
   *
   * <p>Changes in 1.2:
   *
   * <ul>
   *   <li>Using StringBuilder instead of StringBuffer.
   * </ul>
   *
   * @param filter the filter that specified the filter condition
   * @param complete a boolean indicate if the complete review data will be retrieved
   * @return an array of review matching the given filter
   * @exception IllegalArgumentException if filter is null
   * @exception ReviewPersistenceException if failed to search the reviews.
   */
  @Transactional
  public Review[] searchReviews(Filter filter, boolean complete) throws ReviewPersistenceException {
    Helper.assertObjectNotNull(filter, "filter");
    log.debug(
        new LogMessage(
                null, null, "Search " + (complete ? " complete " : " not completed") + " Reviews ")
            .toString());
    Review[] reviews;
    try {
      reviews = reviewServiceRpc.searchReviews(filter);
      if (reviews.length == 0) {
        return reviews;
      }
    } catch (Exception sbe) {
      throw new ReviewPersistenceException("Error searching the reviews.", sbe);
    }

    List<Long> idList = new ArrayList<>();
    for (int i = 0; i < reviews.length; ++i) {
      idList.add(reviews[i].getId());
    }
    try {
      // create the connection
      // get the Id-CommentType map
      Map<Long, CommentType> commentTypeMap = makeIdCommentTypeMap(getAllCommentTypes());
      // get the Id-Review Map
      Map<Long, Review> reviewMap = makeIdReviewMap(reviews);
      // get the review comments
      getReviewComments(idList, reviewMap, commentTypeMap, complete);
      // get the review items
      Item[] items = getReviewItems(idList, reviewMap);
      // get the review item comments
      getReviewItemComments(idList, makeIdItemMap(items), commentTypeMap, complete);
      return reviews;
    } catch (ReviewPersistenceException e) {
      log.error(
          new LogMessage(null, null, "Error occurs during search/retrieve Review.", e).toString());
      throw e;
    }
  }

  /**
   * Get the reviews in the id list from database.
   *
   * @param idList the id list string containing the review ids
   * @return A Review array that contains the reviews get
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Review[] getReviews(List<Long> idList) {
    return reviewServiceRpc.getReviews(idList);
  }

  /**
   * Get the reviews in the id list from database, including all the review entities associated to
   * it.
   *
   * @param idList the id list string containing the review ids
   * @return A Review array that contains the reviews get
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Review[] getReviewsComplete(List<Long> idList) throws ReviewPersistenceException {
    // get the reviews from the id list string
    Review[] reviews = getReviews(idList);
    // get the Id-CommentType map
    Map<Long, CommentType> commentTypeMap = makeIdCommentTypeMap(getAllCommentTypes());
    // get the Id-Review Map
    Map<Long, Review> reviewMap = makeIdReviewMap(reviews);
    // get the review comments
    getReviewComments(idList, reviewMap, commentTypeMap, true);
    // get the review items
    Item[] items = getReviewItems(idList, reviewMap);
    // get the review item comments
    getReviewItemComments(idList, makeIdItemMap(items), commentTypeMap, true);
    return reviews;
  }

  /**
   * Get the review comments whose review id is in the id list from database, and add them to the
   * corresponding review objects.
   *
   * @param idList the id list string containing the review ids
   * @param reviewMap the Id-Review map
   * @param commentTypeMap the Id-CommentType map
   * @param complete a boolean indicate if the complete review data will be retrieved
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private void getReviewComments(
      List<Long> idList,
      Map<Long, Review> reviewMap,
      Map<Long, CommentType> commentTypeMap,
      boolean complete)
      throws ReviewPersistenceException {
    List<ReviewCommentProto> result = complete ? reviewServiceRpc.getReviewComments(idList)
        : reviewServiceRpc.getReviewCommentsWithNoContent(idList);

    // enumerate each comment
    for (ReviewCommentProto rc : result) {
      Comment comment = new Comment();
      comment.setId(rc.getReviewCommentId());
      comment.setAuthor(rc.getResourceId());
      // add to the corresponding review object.
      reviewMap.get(rc.getReviewId()).addComment(comment);
      comment.setCommentType(commentTypeMap.get(rc.getCommentTypeId()));

      if (complete) {
        if (rc.hasContent()) {
          comment.setComment(rc.getContent());
        }
        if (rc.hasExtraInfo()) {
          comment.setExtraInfo(rc.getExtraInfo());
        }
      }
    }
  }

  /**
   * Get the review items whose review id is in the id list from database, and add them to the
   * corresponding review objects.
   *
   * @param idList the id list string containing the review ids
   * @param reviewMap the Id-Review map
   * @return A Comment array that contains the review items get
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Item[] getReviewItems(List<Long> idList, Map<Long, Review> reviewMap)
      throws ReviewPersistenceException {
    // find the review items with review id in idList in the table
    List<ReviewItemProto> result = reviewServiceRpc.getReviewItems(idList);
    // create the Item array.
    Item[] items = new Item[result.size()];
    for (int i = 0; i < items.length; ++i) {
      ReviewItemProto ri = result.get(i);
      // create a new Item object.
      Item item = new Item();
      item.setId(ri.getReviewItemId());
      // add to the corresponding review object.
      reviewMap.get(ri.getReviewId()).addItem(item);
      item.setQuestion(ri.getScorecardQuestionId());
      if (ri.hasUploadId()) {
        item.setDocument(ri.getUploadId());
      }
      if (ri.hasAnswer()) {
        item.setAnswer(ri.getAnswer());
      }
      // assign the current Item object to the array.
      items[i] = item;
    }
    return items;
  }

  /**
   * Get the review item comments which are associated with any review id in the id list from
   * database and add them to the corresponding item objects.
   *
   * @param idList the id list string containing the review ids
   * @param itemMap the Id-Item map
   * @param commentTypeMap the Id-CommentType map
   * @param complete a boolean indicate if the complete review data will be retrieved
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private void getReviewItemComments(
      List<Long> idList,
      Map<Long, Item> itemMap,
      Map<Long, CommentType> commentTypeMap,
      boolean complete)
      throws ReviewPersistenceException {
    List<ReviewItemCommentProto> result = complete ? reviewServiceRpc.getReviewItemComments(idList)
        : reviewServiceRpc.getReviewItemCommentsWithNoContent(idList);
    for (ReviewItemCommentProto ric : result) {
      Comment comment = new Comment();
      comment.setId(ric.getReviewItemCommentId());
      comment.setAuthor(ric.getResourceId());
      // add to the corresponding item object.
      itemMap.get(ric.getReviewItemId()).addComment(comment);
      comment.setCommentType(commentTypeMap.get(ric.getCommentTypeId()));

      if (complete) {
        if (ric.hasContent()) {
          comment.setComment(ric.getContent());
        }
        if (ric.hasExtraInfo()) {
          comment.setExtraInfo(ric.getExtraInfo());
        }
      }
    }
  }

  /**
   * Make an Id-CommentType map from CommentType[].
   *
   * @param commentTypes the CommentType array
   * @return an Id-CommentType map
   */
  private static Map<Long, CommentType> makeIdCommentTypeMap(CommentType[] commentTypes) {
    Map<Long, CommentType> map = new HashMap<Long, CommentType>();
    for (int i = 0; i < commentTypes.length; ++i) {
      map.put(commentTypes[i].getId(), commentTypes[i]);
    }
    return map;
  }

  /**
   * Make an Id-Item map from Item[].
   *
   * @param items the Item array
   * @return an Id-Item map
   */
  private static Map<Long, Item> makeIdItemMap(Item[] items) {
    Map<Long, Item> map = new HashMap<Long, Item>();
    for (int i = 0; i < items.length; ++i) {
      map.put(items[i].getId(), items[i]);
    }
    return map;
  }

  /**
   * Make an Id-Review map from Review[].
   *
   * @param reviews the Review array
   * @return an Id-Review map
   */
  private static Map<Long, Review> makeIdReviewMap(Review[] reviews) {
    Map<Long, Review> map = new HashMap<>();
    for (int i = 0; i < reviews.length; ++i) {
      map.put(reviews[i].getId(), reviews[i]);
    }
    return map;
  }

  /**
   * Get all the CommentType instance from the persistence. Return empty array if no type is found.
   *
   * @return all the CommentType instance from the persistence
   */
  @Transactional
  public CommentType[] getAllCommentTypes() {
    log.debug(new LogMessage(null, null, " retrieve all comment types.").toString());
    if (cachedCommentTypes == null) {
      List<CommentTypeProto> result = reviewServiceRpc.getAllCommentTypes();
      // create the CommentType array.
      CommentType[] commentTypes = new CommentType[result.size()];
      for (int i = 0; i < commentTypes.length; ++i) {
        CommentTypeProto ct = result.get(i);
        commentTypes[i] = new CommentType(ct.getCommentTypeId(), ct.getName());
      }
      cachedCommentTypes = commentTypes;
    }
    return cachedCommentTypes;
  }

  /**
   * Add the comment review with given review id.
   *
   * <p>Please refer to the documentation for createReview method to see when a comment is
   * considered to be valid.
   *
   * @param reviewId representing the review id
   * @param comment representing the comment
   * @param operator representing the operator
   * @exception IllegalArgumentException if comment or operator is null, reviewId is not positive,
   *     operator is empty string, or comment is invalid.
   * @exception ReviewEntityNotFoundException if reviewId does not exists
   * @exception ReviewPersistenceException if any other error occurred.
   */
  @Transactional
  public void addReviewComment(long reviewId, Comment comment, String operator)
      throws ReviewPersistenceException {
    Helper.assertLongPositive(reviewId, "reviewId");
    Helper.assertObjectNotNull(comment, "comment");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    assertCommentValid(comment);
    // create a change table to record the new Ids for Comment.
    Map<Object, Long> changeTable = new HashMap<Object, Long>();
    log.debug(new LogMessage(reviewId, operator, "Add comment to the review.").toString());
    try {
      // create the connection
      // check if the review id exists
      if (!reviewServiceRpc.isReviewExists(reviewId)) {
        log.error(
            new LogMessage(
                    reviewId,
                    operator,
                    "The review id [" + reviewId + "] does not exist in the database.")
                .toString());
        throw new ReviewEntityNotFoundException(
            "The review id [" + reviewId + "] does not exist.",
            reviewId);
      }
      // get # of comments for this review in the database, which equals
      // to the position of the current comment in its review.
      Long index = reviewServiceRpc.countReviewComments(reviewId);
      // create the comment
      createReviewComments(
          new Comment[] {comment}, new Long[] {index}, reviewId, operator, changeTable);
    } catch (ReviewPersistenceException e) {
      log.error(
          new LogMessage(reviewId, operator, "Error occurs adding comment to review .", e)
              .toString());
      throw e;
    }
    // set the comment id when no exception occurred.
    setEntityIds(changeTable);
  }

  /**
   * Add the comment to the item with given item id.
   *
   * <p>Please refer to the documentation for createReview method to see when a comment is
   * considered to be valid.
   *
   * @param itemId the id of the item
   * @param comment the Comment instance
   * @param operator the operator
   * @exception IllegalArgumentException if comment or operator is null, or itemId is not positive,
   *     or operator is empty string, or comment is invalid.
   * @exception ReviewEntityNotFoundException if itemId does not exists
   * @exception ReviewPersistenceException if any other error occurred.
   */
  @Transactional
  public void addItemComment(long itemId, Comment comment, String operator)
      throws ReviewPersistenceException {
    Helper.assertLongPositive(itemId, "itemId");
    Helper.assertObjectNotNull(comment, "comment");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    assertCommentValid(comment);
    // create a change table to record the new Ids for Comment.
    Map<Object, Long> changeTable = new HashMap<>();
    log.debug(
        new LogMessage(null, operator, "Add comment to the Item with the itemId:" + itemId)
            .toString());
    try {
      // check if the review item id exists
      if (!reviewServiceRpc.isReviewItemExists(itemId)) {
        log.error(
            new LogMessage(
                    itemId,
                    operator,
                    "The item id [" + itemId + "] does not exist in the database.")
                .toString());
        throw new ReviewEntityNotFoundException(
            "The item id [" + itemId + "] does not exist.",
            itemId);
      }
      // get # of comments for this item in the database, which equals
      // to the position of the current comment in its item.
      Long index = reviewServiceRpc.countReviewItemComments(itemId);
      // create the comment
      createReviewItemComments(
          new Comment[] {comment}, new Long[] {index}, itemId, operator, changeTable);
    } catch (ReviewPersistenceException e) {
      log.error(
          new LogMessage(
                  null,
                  operator,
                  "Error occurs add item comment with the item id[" + itemId + "].",
                  e)
              .toString());
      throw e;
    }
    // set the comment id when no exception occurred.
    setEntityIds(changeTable);
  }

  /**
   * Removes the review with the specified ID from persistence. All its review items, associated
   * comments and uploads are also removed.
   *
   * @throws IllegalArgumentException if the id is not positive or operator is null/empty
   * @throws ReviewEntityNotFoundException if given ID does not exist in the database
   * @throws ReviewPersistenceException if any other error occurred
   * @param id the ID of the review to be deleted
   * @param operator the operator who deletes the review entity
   * @since 1.2
   */
  @Transactional
  public void removeReview(long id, String operator) throws ReviewPersistenceException {
    Helper.assertLongPositive(id, "id");
    Helper.assertStringNotNullNorEmpty(operator, "operator");
    log.debug(new LogMessage(id, operator, "Delete Review.").toString());
    try {
      // Delete the review record:
      int deletedNum = reviewServiceRpc.deleteReview(id);
      if (deletedNum == 0) {
        throw new ReviewEntityNotFoundException(
            "The review id [" + id + "] does not exist in.", id);
      }
    } catch (ReviewPersistenceException e) {
      log.error(new LogMessage(id, operator, "Error occurs during deleting review.", e).toString());
      throw e;
    }
  }
}
