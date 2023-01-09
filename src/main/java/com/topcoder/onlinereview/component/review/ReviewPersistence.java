/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.review;

import com.topcoder.onlinereview.component.datavalidator.NotValidator;
import com.topcoder.onlinereview.component.datavalidator.NullValidator;
import com.topcoder.onlinereview.component.datavalidator.ObjectValidator;
import com.topcoder.onlinereview.component.grpcclient.review.ReviewServiceRpc;
import com.topcoder.onlinereview.component.id.DBHelper;
import com.topcoder.onlinereview.component.id.IDGenerationException;
import com.topcoder.onlinereview.component.id.IDGenerator;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.component.search.SearchBundle;
import com.topcoder.onlinereview.component.search.SearchBundleManager;
import com.topcoder.onlinereview.component.search.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.component.util.CommonUtils.getDouble;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

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

  /** Represents the id generator name used to get reviewIDGenerator from IDGeneratorFactory. */
  public static final String REVIEW_ID_SEQ = "review_id_seq";
  /**
   * Represents the id generator name used to get reviewCommentIDGenerator from IDGeneratorFactory.
   */
  public static final String REVIEW_COMMENT_ID_SEQ = "review_comment_id_seq";

  /** Represents the id generator name used to get reviewItemIDGenerator from IDGeneratorFactory. */
  public static final String REVIEW_ITEM_ID_SEQ = "review_item_id_seq";
  /**
   * Represents the id generator name used to get reviewItemCommentIDGenerator from
   * IDGeneratorFactory.
   */
  public static final String REVIEW_ITEM_COMMENT_ID_SEQ = "review_item_comment_id_seq";

  /** Represents the name of search bundle name parameter in configuration. */
  private static final String SEARCH_BUNDLE_NAME = "Review Search Bundle";

  /** Represents the review table. */
  private static final String REVIEW_TABLE = "review";

  /** Represents the review comment table. */
  private static final String REVIEW_COMMENT_TABLE = "review_comment";

  /** Represents the review item table. */
  private static final String REVIEW_ITEM_TABLE = "review_item";

  /** Represents the review item comment table. */
  private static final String REVIEW_ITEM_COMMENT_TABLE = "review_item_comment";

  /** Represents the comment type lookup table. */
  private static final String COMMENT_TYPE_LOOKUP_TABLE = "comment_type_lu";

  /**
   * Represents the name of the table that stores upload records. This is a string constant.
   *
   * @since 1.2
   */
  private static final String UPLOAD_TABLE = "upload";

  /** Represents the placeholder string in a sql statement to be replaced by a set of ids. */
  private static final String ID_ARRAY_PARAMETER_PLACEHOLDER = "$ID_ARRAY$";

  /**
   * Represents the regular expression string used to find the id-array placeholder string in a sql
   * statement.
   */
  private static final String ID_ARRAY_PARAMETER_REGULAR_EXP = "\\$ID_ARRAY\\$";

  /** Represents the sql statement to create review. */
  private static final String CREATE_REVIEW_SQL =
      "INSERT INTO "
          + REVIEW_TABLE
          + " (review_id, resource_id, submission_id, project_phase_id, scorecard_id, committed, score, initial_score,"
          + " create_user, create_date, modify_user, modify_date)"
          + " values (?,?,?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

  /** Represents the sql statement to create review comment. */
  private static final String CREATE_REVIEW_COMMENT_SQL =
      "INSERT INTO "
          + REVIEW_COMMENT_TABLE
          + " (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort,"
          + " create_user, create_date, modify_user, modify_date)"
          + " values (?,?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

  /** Represents the sql statement to create review item. */
  private static final String CREATE_REVIEW_ITEM_SQL =
      "INSERT INTO "
          + REVIEW_ITEM_TABLE
          + " (review_item_id, review_id, scorecard_question_id, upload_id, answer, sort,"
          + " create_user, create_date, modify_user, modify_date)"
          + " values (?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

  /** Represents the sql statement to create review item comment. */
  private static final String CREATE_REVIEW_ITEM_COMMENT_SQL =
      "INSERT INTO "
          + REVIEW_ITEM_COMMENT_TABLE
          + " (review_item_comment_id, resource_id, review_item_id, comment_type_id, content, extra_info, sort,"
          + " create_user, create_date, modify_user, modify_date)"
          + " values (?,?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

  /** Represents the sql statement to update review. */
  private static final String UPDATE_REVIEW_SQL =
      "UPDATE "
          + REVIEW_TABLE
          + " SET resource_id=?, submission_id=?, project_phase_id = ?, scorecard_id=?, committed=?, score=?, initial_score=?,"
          + " modify_user=?, modify_date=CURRENT"
          + " WHERE review_id=?";

  /** Represents the sql statement to update review comment. */
  private static final String UPDATE_REVIEW_COMMENT_SQL =
      "UPDATE "
          + REVIEW_COMMENT_TABLE
          + " SET resource_id=?, comment_type_id=?, content=?, extra_info=?, sort=?,"
          + " modify_user=?, modify_date=CURRENT"
          + " WHERE review_comment_id=?";

  /** Represents the sql statement to update review item. */
  private static final String UPDATE_REVIEW_ITEM_SQL =
      "UPDATE "
          + REVIEW_ITEM_TABLE
          + " SET scorecard_question_id=?, upload_id=?, answer=?, sort=?,"
          + " modify_user=?, modify_date=CURRENT"
          + " WHERE review_item_id=?";

  /** Represents the sql statement to update review item comment. */
  private static final String UPDATE_REVIEW_ITEM_COMMENT_SQL =
      "UPDATE "
          + REVIEW_ITEM_COMMENT_TABLE
          + " SET resource_id=?, comment_type_id=?, content=?, extra_info=?, sort=?,"
          + " modify_user=?, modify_date=CURRENT"
          + " WHERE review_item_comment_id=?";

  /** Represents the sql statement to query reviews. */
  private static final String QUERY_REVIEWS_SQL =
      "SELECT "
          + "review_id, resource_id, submission_id, project_phase_id, scorecard_id, committed, score, initial_score, "
          + "create_user, create_date, modify_user, modify_date FROM "
          + REVIEW_TABLE
          + " WHERE review_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ")";

  /** Represents the sql statement to query review comments. */
  private static final String QUERY_REVIEW_COMMENTS_SQL =
      "SELECT "
          + "review_comment_id, resource_id, review_id, comment_type_id, content, extra_info "
          + "FROM "
          + REVIEW_COMMENT_TABLE
          + " WHERE review_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ") ORDER BY review_id, sort";

  /** Represents the sql statement to query review comments. */
  private static final String QUERY_REVIEW_COMMENTS_NO_CONTENT_SQL =
      "SELECT "
          + "review_comment_id, resource_id, review_id, comment_type_id "
          + "FROM "
          + REVIEW_COMMENT_TABLE
          + " WHERE review_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ") ORDER BY review_id, sort";

  /** Represents the sql statement to query review items. */
  private static final String QUERY_REVIEW_ITEMS_SQL =
      "SELECT "
          + "review_item_id, review_id, scorecard_question_id, upload_id, answer "
          + "FROM "
          + REVIEW_ITEM_TABLE
          + " WHERE review_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ") ORDER BY review_id, sort";

  /** Represents the sql statement to query review item comments. */
  private static final String QUERY_REVIEW_ITEM_COMMENTS_SQL =
      "SELECT "
          + "ric.review_item_comment_id, ric.resource_id, ric.review_item_id, ric.comment_type_id, "
          + "ric.content, ric.extra_info "
          + "FROM "
          + REVIEW_ITEM_COMMENT_TABLE
          + " ric "
          + "INNER JOIN "
          + REVIEW_ITEM_TABLE
          + " ri ON ric.review_item_id=ri.review_item_id AND "
          + "ri.review_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ") "
          + "ORDER BY ric.review_item_id, ric.sort";

  /** Represents the sql statement to query review item comments. */
  private static final String QUERY_REVIEW_ITEM_COMMENTS_NO_CONTENT_SQL =
      "SELECT "
          + "ric.review_item_comment_id, ric.resource_id, ric.review_item_id, ric.comment_type_id "
          + "FROM "
          + REVIEW_ITEM_COMMENT_TABLE
          + " ric "
          + "INNER JOIN "
          + REVIEW_ITEM_TABLE
          + " ri ON ric.review_item_id=ri.review_item_id AND "
          + "ri.review_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ") "
          + "ORDER BY ric.review_item_id, ric.sort";

  /** Represents the sql statement to query review comment IDs. */
  private static final String QUERY_REVIEW_COMMENT_IDS_SQL =
      "SELECT " + "review_comment_id FROM " + REVIEW_COMMENT_TABLE + " WHERE review_id=?";

  /** Represents the sql statement to query review item IDs. */
  private static final String QUERY_REVIEW_ITEM_IDS_SQL =
      "SELECT " + "review_item_id FROM " + REVIEW_ITEM_TABLE + " WHERE review_id=?";

  /** Represents the sql statement to query review item comment IDs. */
  private static final String QUERY_REVIEW_ITEM_COMMENT_IDS_SQL =
      "SELECT "
          + "review_item_comment_id FROM "
          + REVIEW_ITEM_COMMENT_TABLE
          + " WHERE review_item_id=?";

  /** Represents the sql statement to query all comment types. */
  private static final String QUERY_ALL_COMMENT_TYPES_SQL =
      "SELECT comment_type_id, name FROM " + COMMENT_TYPE_LOOKUP_TABLE;

  /**
   * Represents the sql statement to query review items uploads.
   *
   * @since 1.2
   */
  private static final String QUERY_REVIEW_ITEM_UPLOADS_SQL =
      "SELECT upload_id FROM "
          + REVIEW_ITEM_TABLE
          + "  WHERE review_item_id IN ("
          + ID_ARRAY_PARAMETER_PLACEHOLDER
          + ")";

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
  private SearchBundle searchBundle;

  /** The IDGenerator used to generate the review id. It's set in the constructor, not-null. */
  private IDGenerator reviewIDGenerator;

  /**
   * The IDGenerator used to generate the review comment id. It's set in the constructor, not-null.
   */
  private IDGenerator reviewCommentIDGenerator;

  /** The IDGenerator used to generate the review item id. It's set in the constructor, not-null. */
  private IDGenerator reviewItemIDGenerator;

  /**
   * The IDGenerator used to generate the review item comment id. It's set in the constructor,
   * not-null.
   */
  private IDGenerator reviewItemCommentIDGenerator;

  @Autowired private DBHelper dbHelper;
  @Autowired private SearchBundleManager searchBundleManager;

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    reviewIDGenerator = new IDGenerator(REVIEW_ID_SEQ, dbHelper);
    reviewCommentIDGenerator = new IDGenerator(REVIEW_COMMENT_ID_SEQ, dbHelper);
    reviewItemIDGenerator = new IDGenerator(REVIEW_ITEM_ID_SEQ, dbHelper);
    reviewItemCommentIDGenerator = new IDGenerator(REVIEW_ITEM_COMMENT_ID_SEQ, dbHelper);
    searchBundle = searchBundleManager.getSearchBundle(SEARCH_BUNDLE_NAME);
    // create the searchable fields map.
    Map<String, ObjectValidator> fieldsMap = new HashMap<>();
    ObjectValidator notNullValidator = new NotValidator(new NullValidator());
    fieldsMap.put("scorecardType", notNullValidator);
    fieldsMap.put("submission", notNullValidator);
    fieldsMap.put("projectPhase", notNullValidator);
    fieldsMap.put("reviewer", notNullValidator);
    fieldsMap.put("project", notNullValidator);
    fieldsMap.put("committed", notNullValidator);
    searchBundle.setSearchableFields(fieldsMap);
  }

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
    // createDate will contain the create_date retrieved from database.
    Date createDate;
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
      createReview(review, operator, changeTable);
      // get the creation date.
      createDate =
          Helper.doSingleValueQuery(
              jdbcTemplate,
              "SELECT create_date FROM " + REVIEW_TABLE + " WHERE review_id=?",
              new Object[] {changeTable.get(review)},
              d -> getDate(d, "create_date"));
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
  private void createReview(Review review, String operator, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    Long newId;
    try {
      // generate id for the review
      newId = reviewIDGenerator.getNextID();
      log.debug("Get new review id :" + newId);
    } catch (IDGenerationException e) {
      throw new ReviewPersistenceException("Unable to generate id for review.", e);
    }
    // add the review and newId pair to the change table.
    changeTable.put(review, newId);
    // insert the review into database
    Object[] queryArgs;
    if (review.getSubmission() > 0) {
      queryArgs =
          new Object[] {
            newId,
            review.getAuthor(),
            review.getSubmission(),
            review.getProjectPhase(),
            review.getScorecard(),
            review.isCommitted() ? 1 : 0,
            review.getScore(),
            review.getInitialScore(),
            operator,
            operator
          };
    } else {
      queryArgs =
          new Object[] {
            newId,
            review.getAuthor(),
            null,
            review.getProjectPhase(),
            review.getScorecard(),
            review.isCommitted() ? 1 : 0,
            review.getScore(),
            review.getInitialScore(),
            operator,
            operator
          };
    }
    Helper.doDMLQuery(jdbcTemplate, CREATE_REVIEW_SQL, queryArgs);
    log.debug("insert record into " + REVIEW_TABLE + " with new id:" + newId);
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
        // generate id for the review comment
        Long newId = reviewCommentIDGenerator.getNextID();
        log.debug("generate new review comment id :" + newId);
        // add the comment and newId pair to the change table.
        changeTable.put(comment, newId);
        // insert the review comment into database
        Object[] queryArgs = {
          newId,
          comment.getAuthor(),
          reviewId,
          comment.getCommentType().getId(),
          comment.getComment(),
          comment.getExtraInfo(),
          indices[i],
          operator,
          operator
        };
        log.debug("insert record into " + REVIEW_COMMENT_TABLE + " with new id:" + newId);
        Helper.doDMLQuery(jdbcTemplate, CREATE_REVIEW_COMMENT_SQL, queryArgs);
      }
    } catch (IDGenerationException e) {
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
        Long newId = reviewItemIDGenerator.getNextID();
        log.debug("generate new review Item id :" + newId);
        // add the item and newId pair into the change table.
        changeTable.put(item, newId);
        // insert the review item into database
        Object[] queryArgs = {
          newId,
          reviewId,
          item.getQuestion(),
          item.getDocument(),
          item.getAnswer(),
          indices[i],
          operator,
          operator
        };
        log.debug("insert record into " + REVIEW_ITEM_TABLE + " with new id:" + newId);
        Helper.doDMLQuery(jdbcTemplate, CREATE_REVIEW_ITEM_SQL, queryArgs);
        // create review item comments
        createReviewItemComments(
            item.getAllComments(),
            makeAscendingLongArray(item.getNumberOfComments()),
            newId,
            operator,
            changeTable);
      }
    } catch (IDGenerationException e) {
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
        Long newId = reviewItemCommentIDGenerator.getNextID();
        log.debug("generate new review Item comment id :" + newId);
        // add the comment and newId pair to the change table.
        changeTable.put(comment, newId);
        // insert the review item comment into database
        Object[] queryArgs = {
          newId,
          comment.getAuthor(),
          itemId,
          comment.getCommentType().getId(),
          comment.getComment(),
          comment.getExtraInfo(),
          indices[i],
          operator,
          operator
        };
        log.debug("insert record into " + REVIEW_ITEM_COMMENT_TABLE + " with new id:" + newId);
        Helper.doDMLQuery(jdbcTemplate, CREATE_REVIEW_ITEM_COMMENT_SQL, queryArgs);
      }
    } catch (IDGenerationException e) {
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
    Date modifyDate;
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
      updateReview(review, operator, changeTable);
      // get the modification date.
      modifyDate =
          Helper.doSingleValueQuery(
              jdbcTemplate,
              "SELECT modify_date FROM " + REVIEW_TABLE + " WHERE review_id=?",
              new Object[] {review.getId()},
              d -> getDate(d, "modify_date"));
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
  private void updateReview(Review review, String operator, Map<Object, Long> changeTable)
      throws ReviewPersistenceException {
    Long reviewId = review.getId();
    // update the review item in database
    Object[] queryArgs;
    if (review.getSubmission() > 0) {
      queryArgs =
          new Object[] {
            review.getAuthor(),
            review.getSubmission(),
            review.getProjectPhase(),
            review.getScorecard(),
            review.isCommitted() ? 1 : 0,
            review.getScore(),
            review.getInitialScore(),
            operator,
            reviewId
          };
    } else {
      queryArgs =
          new Object[] {
            review.getAuthor(),
            null,
            review.getProjectPhase(),
            review.getScorecard(),
            review.isCommitted() ? 1 : 0,
            review.getScore(),
            review.getInitialScore(),
            operator,
            reviewId
          };
    }
    log.debug("update record in the  " + REVIEW_TABLE + " table with id:" + reviewId);
    Helper.doDMLQuery(jdbcTemplate, UPDATE_REVIEW_SQL, queryArgs);
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
    Object[] queryArgs = {
      comment.getAuthor(),
      comment.getCommentType().getId(),
      comment.getComment(),
      comment.getExtraInfo(),
      index,
      operator,
      comment.getId()
    };
    log.debug("update record in the " + REVIEW_COMMENT_TABLE + " table with id:" + comment.getId());
    Helper.doDMLQuery(jdbcTemplate, UPDATE_REVIEW_COMMENT_SQL, queryArgs);
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
    log.debug("execute sql:" + UPDATE_REVIEW_ITEM_SQL);
    Long itemId = item.getId();
    // update the review item in database
    Object[] queryArgs = {
      item.getQuestion(), item.getDocument(), item.getAnswer(), index, operator, itemId
    };
    Helper.doDMLQuery(jdbcTemplate, UPDATE_REVIEW_ITEM_SQL, queryArgs);
    // update the review item comments
    updateReviewItemComments(
        item.getAllComments(),
        makeAscendingLongArray(item.getNumberOfComments()),
        itemId,
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
    log.debug("execute sql:" + UPDATE_REVIEW_ITEM_COMMENT_SQL);
    // update the review item comment in database
    Object[] queryArgs = {
      comment.getAuthor(),
      comment.getCommentType().getId(),
      comment.getComment(),
      comment.getExtraInfo(),
      index,
      operator,
      comment.getId()
    };
    Helper.doDMLQuery(jdbcTemplate, UPDATE_REVIEW_ITEM_COMMENT_SQL, queryArgs);
  }

  /**
   * get all the review comment ids associated with the review id.
   *
   * @param reviewId the given review id
   * @return A set that contains the review comment ids
   * @throws ReviewPersistenceException if any error occurs during the query
   */
  private Set<Long> getReviewCommentIDs(Long reviewId) {
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, QUERY_REVIEW_COMMENT_IDS_SQL, newArrayList(reviewId));
    // build the set from result list
    Set<Long> reviewCommentIDs = new HashSet<>();
    for (Map<String, Object> map : result) {
      reviewCommentIDs.add(getLong(map, "review_comment_id"));
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
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, QUERY_REVIEW_ITEM_IDS_SQL, newArrayList(reviewId));
    // build the set from result list
    Set<Long> reviewItemIDs = new HashSet<>();
    for (Map<String, Object> map : result) {
      reviewItemIDs.add(getLong(map, "review_item_id"));
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
    log.debug("execute sql:" + QUERY_REVIEW_ITEM_COMMENT_IDS_SQL);
    List<Map<String, Object>> result =
        executeSqlWithParam(jdbcTemplate, QUERY_REVIEW_ITEM_COMMENT_IDS_SQL, newArrayList(itemId));
    // build the set from result list
    Set<Long> reviewItemCommentIDs = new HashSet<>();
    for (Map<String, Object> map : result) {
      reviewItemCommentIDs.add(getLong(map, "review_item_comment_id"));
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
    // enumerate each id
    log.debug(
        "delete entry from "
            + REVIEW_COMMENT_TABLE
            + " with the review comment id:"
            + getCommaSeparatedList(reviewCommentIDs));
    Helper.deleteEntities(
        REVIEW_COMMENT_TABLE, "review_comment_id", reviewCommentIDs, jdbcTemplate);
  }

  /**
   * Delete review item ids in the database.
   *
   * @param reviewItemIDs A set that contains review item ids to delete
   * @throws ReviewPersistenceException if any error occurs during the deletion
   */
  private void deleteReviewItems(Set<Long> reviewItemIDs) throws ReviewPersistenceException {
    // Get IDs of uploads associated with the review item:
    Set<Long> reviewItemUploadIDs = getReviewItemsUploadIDs(reviewItemIDs);
    // review item ids for logging
    String ids = getCommaSeparatedList(reviewItemIDs);
    // delete review item and review item comment first as they have references to upload
    log.debug(
        "delete entries from " + REVIEW_ITEM_COMMENT_TABLE + " with the review item ids: " + ids);
    Helper.deleteEntities(REVIEW_ITEM_COMMENT_TABLE, "review_item_id", reviewItemIDs, jdbcTemplate);
    log.debug("delete entries from " + REVIEW_ITEM_TABLE + " with the review item ids: " + ids);
    Helper.deleteEntities(REVIEW_ITEM_TABLE, "review_item_id", reviewItemIDs, jdbcTemplate);
    // delete item uploads
    if (!reviewItemUploadIDs.isEmpty()) {
      log.debug(
          "delete entries from "
              + UPLOAD_TABLE
              + " with the upload ids: "
              + getCommaSeparatedList(reviewItemUploadIDs));
      // Delete all the found uploads:
      Helper.deleteEntities(UPLOAD_TABLE, "upload_id", reviewItemUploadIDs, jdbcTemplate);
    }
  }

  /**
   * Creates String as comma separated values list.
   *
   * @param collection the collection to get values
   * @return created String as comma separated values list
   */
  private static String getCommaSeparatedList(Collection<Long> collection) {
    boolean first = true;
    StringBuilder sb = new StringBuilder();
    for (Long value : collection) {
      if (first) {
        first = false;
      } else {
        sb.append(",");
      }
      sb.append(value);
    }
    return sb.toString();
  }

  /**
   * Delete review item comment ids in the database.
   *
   * @param reviewItemCommentIDs A set that contains review item comment ids to delete
   * @throws ReviewPersistenceException if any error occurs during the deletion
   */
  private void deleteReviewItemComments(Set<Long> reviewItemCommentIDs)
      throws ReviewPersistenceException {
    // enumerate each id
    log.debug(
        "delete entries from "
            + REVIEW_ITEM_COMMENT_TABLE
            + " with the review_item_comment_ids: "
            + getCommaSeparatedList(reviewItemCommentIDs));
    Helper.deleteEntities(
        REVIEW_ITEM_COMMENT_TABLE, "review_item_comment_id", reviewItemCommentIDs, jdbcTemplate);
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
      Review review = getReviewsComplete(Long.toString(id))[0];
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
    List<Review> reviewsList = new ArrayList<Review>();
    log.debug(
        new LogMessage(
                null, null, "Search " + (complete ? " complete " : " not completed") + " Reviews ")
            .toString());
    try {
      // do the search using search bundle
      List<Map<String, Object>> resultSet = searchBundle.search(filter);
      if (resultSet.isEmpty()) {
        return new Review[0];
      }
      for (Map<String, Object> map : resultSet) {
        reviewsList.add(getReview(map));
      }
    } catch (Exception sbe) {
      throw new ReviewPersistenceException("Error searching the reviews.", sbe);
    }
    Review[] reviews = reviewsList.toArray(new Review[reviewsList.size()]);

    StringBuilder idList = new StringBuilder();
    for (int i = 0; i < reviews.length; ++i) {
      if (i != 0) {
        idList.append(",");
      }
      idList.append(reviews[i].getId());
    }
    try {
      // create the connection
      // get the Id-CommentType map
      Map<Long, CommentType> commentTypeMap = makeIdCommentTypeMap(getAllCommentTypes());
      // get the Id-Review Map
      Map<Long, Review> reviewMap = makeIdReviewMap(reviews);
      String idsList = idList.toString();
      // get the review comments
      getReviewComments(idsList, reviewMap, commentTypeMap, complete);
      // get the review items
      Item[] items = getReviewItems(idsList, reviewMap);
      // get the review item comments
      getReviewItemComments(idsList, makeIdItemMap(items), commentTypeMap, complete);
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
    // find the reviews with review id in idList in the table
    List<Map<String, Object>> result =
        executeSql(
            jdbcTemplate, QUERY_REVIEWS_SQL.replaceFirst(ID_ARRAY_PARAMETER_REGULAR_EXP, idList));
    // create the Review array.
    Review[] reviews = new Review[result.size()];
    for (int i = 0; i < reviews.length; ++i) {
      Map<String, Object> row = result.get(i);
      Review review = new Review();
      review.setId(getLong(row, "review_id"));
      review.setAuthor(getLong(row, "resource_id"));
      if (row.get("submission_id") != null) {
        review.setSubmission(getLong(row, "submission_id"));
      }
      review.setProjectPhase(getLong(row, "project_phase_id"));
      review.setScorecard(getLong(row, "scorecard_id"));
      review.setCommitted(getBoolean(row, "committed"));
      review.setScore(getDouble(row, "score"));
      review.setInitialScore(getDouble(row, "initial_score"));
      review.setCreationUser(getString(row, "create_user"));
      review.setCreationTimestamp(getDate(row, "create_date"));
      review.setModificationUser(getString(row, "modify_user"));
      review.setModificationTimestamp(getDate(row, "modify_date"));
      // assign the current review to the array.
      reviews[i] = review;
    }
    return reviews;
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
   * Creates a review from result set's row.
   *
   * @return created review.
   * @param resultSet a result set pointing to the row describing new review to create
   */
  private static Review getReview(Map<String, Object> resultSet) {
    Review review = new Review();
    try {
      review.setId(getLong(resultSet, "review_id"));
      review.setAuthor(getLong(resultSet, "resource_id"));
      try {
        review.setSubmission(getLong(resultSet, "submission_id"));
      } catch (Exception e) {
        // Submission ID may be NULL
      }
      review.setProjectPhase(getLong(resultSet, "project_phase_id"));
      review.setScorecard(getLong(resultSet, "scorecard_id"));
      review.setCommitted(getLong(resultSet, "committed") != 0);
      if (resultSet.get("score") != null) {
        review.setScore(getDouble(resultSet, "score"));
      } else {
        review.setScore(null);
      }
      if (resultSet.get("initial_score") != null) {
        review.setInitialScore(getDouble(resultSet, "initial_score"));
      } else {
        review.setInitialScore(null);
      }
    } catch (Exception e1) {
      // ignore, never happens
    }
    review.setCreationUser(getString(resultSet, "create_user"));
    review.setCreationTimestamp(getDate(resultSet, "create_date"));
    review.setModificationUser(getString(resultSet, "modify_user"));
    review.setModificationTimestamp(getDate(resultSet, "modify_date"));
    return review;
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
      String idList,
      Map<Long, Review> reviewMap,
      Map<Long, CommentType> commentTypeMap,
      boolean complete)
      throws ReviewPersistenceException {
    String query =
        (complete ? QUERY_REVIEW_COMMENTS_SQL : QUERY_REVIEW_COMMENTS_NO_CONTENT_SQL)
            .replaceFirst(ID_ARRAY_PARAMETER_REGULAR_EXP, idList);
    List<Map<String, Object>> result = executeSql(jdbcTemplate, query);

    // enumerate each comment
    for (int i = 0; i < result.size(); ++i) {
      Map<String, Object> row = result.get(i);
      Comment comment = new Comment();
      comment.setId(getLong(row, "review_comment_id"));
      comment.setAuthor(getLong(row, "resource_id"));
      // add to the corresponding review object.
      reviewMap.get(getLong(row, "review_id")).addComment(comment);
      comment.setCommentType(commentTypeMap.get(getLong(row, "comment_type_id")));

      if (complete) {
        comment.setComment(getString(row, "content"));
        comment.setExtraInfo(getString(row, "extra_info"));
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
  private Item[] getReviewItems(String idList, Map<Long, Review> reviewMap)
      throws ReviewPersistenceException {
    // find the review items with review id in idList in the table
    List<Map<String, Object>> result =
        executeSql(
            jdbcTemplate,
            QUERY_REVIEW_ITEMS_SQL.replaceFirst(ID_ARRAY_PARAMETER_REGULAR_EXP, idList));
    // create the Item array.
    Item[] items = new Item[result.size()];
    for (int i = 0; i < items.length; ++i) {
      Map<String, Object> row = result.get(i);
      // create a new Item object.
      Item item = new Item();
      item.setId(getLong(row, "review_item_id"));
      // add to the corresponding review object.
      reviewMap.get(getLong(row, "review_id")).addItem(item);
      item.setQuestion(getLong(row, "scorecard_question_id"));
      item.setDocument(getLong(row, "upload_id"));
      item.setAnswer(getString(row, "answer"));
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
      String idList,
      Map<Long, Item> itemMap,
      Map<Long, CommentType> commentTypeMap,
      boolean complete)
      throws ReviewPersistenceException {
    String query =
        (complete ? QUERY_REVIEW_ITEM_COMMENTS_SQL : QUERY_REVIEW_ITEM_COMMENTS_NO_CONTENT_SQL)
            .replaceFirst(ID_ARRAY_PARAMETER_REGULAR_EXP, idList);
    List<Map<String, Object>> result = executeSql(jdbcTemplate, query);
    for (int i = 0; i < result.size(); ++i) {
      Map<String, Object> row = result.get(i);
      Comment comment = new Comment();
      comment.setId(getLong(row, "review_item_comment_id"));
      comment.setAuthor(getLong(row, "resource_id"));
      // add to the corresponding item object.
      itemMap.get(getLong(row, "review_item_id")).addComment(comment);
      comment.setCommentType(commentTypeMap.get(getLong(row, "comment_type_id")));

      if (complete) {
        comment.setComment(getString(row, "content"));
        comment.setExtraInfo(getString(row, "extra_info"));
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
      List<Map<String, Object>> result = executeSql(jdbcTemplate, QUERY_ALL_COMMENT_TYPES_SQL);
      // create the CommentType array.
      CommentType[] commentTypes = new CommentType[result.size()];
      for (int i = 0; i < commentTypes.length; ++i) {
        Map<String, Object> row = result.get(i);
        commentTypes[i] = new CommentType(getLong(row, "comment_type_id"), getString(row, "name"));
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
            "The review id [" + reviewId + "] does not exist in table [" + REVIEW_TABLE + "].",
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
            "The item id [" + itemId + "] does not exist in table [" + REVIEW_ITEM_TABLE + "].",
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
    Connection conn = null;
    try {
      // Get IDs of all review items:
      Set<Long> reviewItemIDs = getReviewItemIDs(id);
      // Delete the review items together with associated uploads and comments:
      deleteReviewItems(reviewItemIDs);
      // Delete all review comments:
      Helper.deleteEntities(REVIEW_COMMENT_TABLE, "review_id", id, jdbcTemplate);
      // Delete the review record:
      int deletedNum = Helper.deleteEntities(REVIEW_TABLE, "review_id", id, jdbcTemplate);
      if (deletedNum == 0) {
        throw new ReviewEntityNotFoundException(
            "The review id [" + id + "] does not exist in table [" + REVIEW_TABLE + "].", id);
      }
    } catch (ReviewPersistenceException e) {
      log.error(new LogMessage(id, operator, "Error occurs during deleting review.", e).toString());
      throw e;
    }
  }

  /**
   * Retrieves the IDs of uploads associated with the review items that have the specified IDs.
   *
   * @param itemIds the IDs of the review items
   * @return A set that contains the upload ids
   * @since 1.2
   */
  private Set<Long> getReviewItemsUploadIDs(Set<Long> itemIds) {
    // Create the result set:
    Set<Long> reviewItemsUploadIDs = new HashSet<>();
    // Check that items are not empty
    if (itemIds.isEmpty()) {
      return reviewItemsUploadIDs;
    }
    List<Map<String, Object>> result =
        executeSql(
            jdbcTemplate,
            QUERY_REVIEW_ITEM_UPLOADS_SQL.replaceFirst(
                ID_ARRAY_PARAMETER_REGULAR_EXP, getCommaSeparatedList(itemIds)));
    for (Map<String, Object> map : result) {
      if (map.get("upload_id") != null) {
        reviewItemsUploadIDs.add(getLong(map, "upload_id"));
      }
    }
    return reviewItemsUploadIDs;
  }
}
