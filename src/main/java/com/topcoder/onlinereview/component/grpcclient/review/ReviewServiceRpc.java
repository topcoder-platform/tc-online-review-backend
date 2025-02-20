package com.topcoder.onlinereview.component.grpcclient.review;

import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
import com.topcoder.onlinereview.component.review.Comment;
import com.topcoder.onlinereview.component.review.Item;
import com.topcoder.onlinereview.component.review.Review;
import com.topcoder.onlinereview.component.search.filter.Filter;
import com.topcoder.onlinereview.grpc.review.proto.*;

@Service
@DependsOn({ "grpcChannelManager" })
public class ReviewServiceRpc {

    @Autowired
    private GrpcChannelManager grpcChannelManager;

    private ReviewServiceGrpc.ReviewServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        stub = ReviewServiceGrpc.newBlockingStub(grpcChannelManager.getChannel());
    }

    public boolean isReviewExists(long reviewId) {
        ReviewIdProto request = ReviewIdProto.newBuilder().setReviewId(reviewId).build();
        return stub.isReviewExists(request).getExists();
    }

    public boolean isReviewItemExists(long reviewItemId) {
        ReviewItemIdProto request = ReviewItemIdProto.newBuilder().setReviewItemId(reviewItemId).build();
        return stub.isReviewItemExists(request).getExists();
    }

    public long countReviewComments(long reviewId) {
        ReviewIdProto request = ReviewIdProto.newBuilder().setReviewId(reviewId).build();
        return stub.countReviewComments(request).getCount();
    }

    public long countReviewItemComments(long reviewItemId) {
        ReviewItemIdProto request = ReviewItemIdProto.newBuilder().setReviewItemId(reviewItemId).build();
        return stub.countReviewItemComments(request).getCount();
    }

    public Review[] getReviews(List<Long> idList) {
        ReviewIdsProto request = ReviewIdsProto.newBuilder().addAllReviewIds(idList).build();
        GetReviewsResponse response = stub.getReviews(request);
        Review[] reviews = new Review[response.getReviewsCount()];
        for (int i = 0; i < response.getReviewsCount(); i++) {
            reviews[i] = loadReview(response.getReviews(i));
        }
        return reviews;
    }

    public List<ReviewCommentProto> getReviewComments(List<Long> idList) {
        ReviewIdsProto request = ReviewIdsProto.newBuilder().addAllReviewIds(idList).build();
        GetReviewCommentsResponse response = stub.getReviewComments(request);
        return response.getReviewCommentsList();
    }

    public List<ReviewCommentProto> getReviewCommentsWithNoContent(List<Long> idList) {
        ReviewIdsProto request = ReviewIdsProto.newBuilder().addAllReviewIds(idList).build();
        GetReviewCommentsResponse response = stub.getReviewCommentsWithNoContent(request);
        return response.getReviewCommentsList();
    }

    public List<ReviewItemProto> getReviewItems(List<Long> idList) {
        ReviewIdsProto request = ReviewIdsProto.newBuilder().addAllReviewIds(idList).build();
        GetReviewItemsResponse response = stub.getReviewItems(request);
        return response.getReviewItemsList();
    }

    public List<ReviewItemCommentProto> getReviewItemComments(List<Long> idList) {
        ReviewIdsProto request = ReviewIdsProto.newBuilder().addAllReviewIds(idList).build();
        GetReviewItemCommentsResponse response = stub.getReviewItemComments(request);
        return response.getReviewItemCommentsList();
    }

    public List<ReviewItemCommentProto> getReviewItemCommentsWithNoContent(List<Long> idList) {
        ReviewIdsProto request = ReviewIdsProto.newBuilder().addAllReviewIds(idList).build();
        GetReviewItemCommentsResponse response = stub.getReviewItemCommentsWithNoContent(request);
        return response.getReviewItemCommentsList();
    }

    public List<CommentTypeProto> getAllCommentTypes() {
        return stub.getAllCommentTypes(null).getCommentTypesList();
    }

    public List<Long> getReviewCommentIds(long reviewId) {
        ReviewIdProto request = ReviewIdProto.newBuilder().setReviewId(reviewId).build();
        IdListProto response = stub.getReviewCommentIds(request);
        return response.getIdsList();
    }

    public List<Long> getReviewItemIds(long reviewId) {
        ReviewIdProto request = ReviewIdProto.newBuilder().setReviewId(reviewId).build();
        IdListProto response = stub.getReviewItemIds(request);
        return response.getIdsList();
    }

    public List<Long> getReviewItemCommentIds(long reviewItemId) {
        ReviewItemIdProto request = ReviewItemIdProto.newBuilder().setReviewItemId(reviewItemId).build();
        IdListProto response = stub.getReviewItemCommentIds(request);
        return response.getIdsList();
    }

    public List<Long> getReviewItemsUploadIds(List<Long> reviewItemIds) {
        ReviewItemIdsProto request = ReviewItemIdsProto.newBuilder().addAllReviewItemIds(reviewItemIds).build();
        IdListProto response = stub.getReviewItemsUploadIds(request);
        return response.getIdsList();
    }

    public long createReview(Review review, String operator, Date date) {
        ReviewProto request = buildReview(review, operator, date, false);
        IdProto response = stub.createReview(request);
        return response.getId();
    }

    public long createReviewComment(Comment comment, Long reviewId, Long sort, String operator, Date date) {
        ReviewCommentProto request = buildReviewComment(comment, reviewId, sort, operator, date, false);
        IdProto response = stub.createReviewComment(request);
        return response.getId();
    }

    public long createReviewItem(Item item, Long reviewId, Long sort, String operator, Date date) {
        ReviewItemProto request = buildReviewItem(item, reviewId, sort, operator, date, false);
        IdProto response = stub.createReviewItem(request);
        return response.getId();
    }

    public long createReviewItemComment(Comment comment, Long itemId, Long sort, String operator, Date date) {
        ReviewItemCommentProto request = buildReviewItemComment(comment, itemId, sort, operator, date, false);
        IdProto response = stub.createReviewItemComment(request);
        return response.getId();
    }

    public int updateReview(Review review, String operator, Date date) {
        ReviewProto request = buildReview(review, operator, date, true);
        CountProto response = stub.updateReview(request);
        return response.getCount();
    }

    public int updateReviewComment(Comment comment, Long sort, String operator, Date date) {
        ReviewCommentProto request = buildReviewComment(comment, null, sort, operator, date, true);
        CountProto response = stub.updateReviewComment(request);
        return response.getCount();
    }

    public long updateReviewItem(Item item, Long sort, String operator, Date date) {
        ReviewItemProto request = buildReviewItem(item, null, sort, operator, date, true);
        CountProto response = stub.updateReviewItem(request);
        return response.getCount();
    }

    public long updateReviewItemComment(Comment comment, Long sort, String operator, Date date) {
        ReviewItemCommentProto request = buildReviewItemComment(comment, null, sort, operator, date, true);
        CountProto response = stub.updateReviewItemComment(request);
        return response.getCount();
    }

    public int deleteReview(long reviewId) {
        ReviewIdProto request = ReviewIdProto.newBuilder().setReviewId(reviewId).build();
        CountProto response = stub.deleteReview(request);
        return response.getCount();
    }

    public int deleteReviewComments(List<Long> reviewCommentIds) {
        ReviewCommentIdsProto request = ReviewCommentIdsProto.newBuilder().addAllReviewCommentIds(reviewCommentIds)
                .build();
        CountProto response = stub.deleteReviewComments(request);
        return response.getCount();
    }

    public int deleteReviewItems(List<Long> reviewItemIds) {
        ReviewItemIdsProto request = ReviewItemIdsProto.newBuilder().addAllReviewItemIds(reviewItemIds).build();
        CountProto response = stub.deleteReviewItems(request);
        return response.getCount();
    }

    public int deleteReviewItemComments(List<Long> reviewItemCommentsIds) {
        ReviewItemCommentIdsProto request = ReviewItemCommentIdsProto.newBuilder()
                .addAllReviewItemCommentIds(reviewItemCommentsIds).build();
        CountProto response = stub.deleteReviewItemComments(request);
        return response.getCount();
    }

    public Review[] searchReviews(Filter filter) {
        FilterProto filterProto = FilterProto.newBuilder()
                .setFilter(ByteString.copyFrom(SerializationUtils.serialize(filter))).build();
        GetReviewsResponse response = stub.searchReviews(filterProto);
        Review[] reviews = new Review[response.getReviewsCount()];
        for (int i = 0; i < response.getReviewsCount(); i++) {
            reviews[i] = loadReview(response.getReviews(i));
        }
        return reviews;
    }

    private Review loadReview(ReviewProto r) {
        Review review = new Review();
        if (r.hasReviewId()) {
            review.setId(r.getReviewId());
        }
        if (r.hasResourceId()) {
            review.setAuthor(r.getResourceId());
        }
        if (r.hasSubmissionId()) {
            review.setSubmission(r.getSubmissionId());
        }
        if (r.hasProjectPhaseId()) {
            review.setProjectPhase(r.getProjectPhaseId());
        }
        if (r.hasScorecardId()) {
            review.setScorecard(r.getScorecardId());
        }
        if (r.hasCommitted()) {
            review.setCommitted(r.getCommitted());
        }
        if (r.hasScore()) {
            review.setScore(r.getScore());
        }
        if (r.hasInitialScore()) {
            review.setInitialScore(r.getInitialScore());
        }
        if (r.hasCreateUser()) {
            review.setCreationUser(r.getCreateUser());
        }
        if (r.hasCreateDate()) {
            review.setCreationTimestamp(new Date(r.getCreateDate().getSeconds() * 1000));
        }
        if (r.hasModifyUser()) {
            review.setModificationUser(r.getModifyUser());
        }
        if (r.hasModifyDate()) {
            review.setModificationTimestamp(new Date(r.getModifyDate().getSeconds() * 1000));
        }
        return review;
    }

    private ReviewProto buildReview(Review review, String operator, Date date, boolean update) {
        ReviewProto.Builder request = ReviewProto.newBuilder();
        if (update) {
            request.setReviewId(review.getId());
        }
        request.setResourceId(review.getAuthor());
        if (review.getSubmission() > 0) {
            request.setSubmissionId(review.getSubmission());
        }
        request.setProjectPhaseId(review.getProjectPhase());
        request.setScorecardId(review.getScorecard());
        request.setCommitted(review.isCommitted());
        if (review.getScore() != null) {
            request.setScore(review.getScore());
        }
        if (review.getInitialScore() != null) {
            request.setInitialScore(review.getInitialScore());
        }
        if (operator != null) {
            if (!update) {
                request.setCreateUser(operator);
            }
            request.setModifyUser(operator);
        }
        if (date != null) {
            if (!update) {
                request.setCreateDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
            }
            request.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
        }
        return request.build();
    }

    private ReviewCommentProto buildReviewComment(Comment comment, Long reviewId, Long sort, String operator, Date date,
            boolean update) {
        ReviewCommentProto.Builder request = ReviewCommentProto.newBuilder();
        if (update) {
            request.setReviewCommentId(comment.getId());
        }
        request.setResourceId(comment.getAuthor());
        if (reviewId != null) {
            request.setReviewId(reviewId);
        }
        if (comment.getCommentType() != null) {
            request.setCommentTypeId(comment.getCommentType().getId());
        }
        if (comment.getComment() != null) {
            request.setContent(comment.getComment());
        }
        if (comment.getExtraInfo() != null) {
            request.setExtraInfo(comment.getExtraInfo().toString());
        }
        if (sort != null) {
            request.setSort(sort);
        }
        if (operator != null) {
            if (!update) {
                request.setCreateUser(operator);
            }
            request.setModifyUser(operator);
        }
        if (date != null) {
            if (!update) {
                request.setCreateDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
            }
            request.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
        }
        return request.build();
    }

    private ReviewItemProto buildReviewItem(Item item, Long reviewId, Long sort, String operator, Date date,
            boolean update) {
        ReviewItemProto.Builder request = ReviewItemProto.newBuilder();
        if (update) {
            request.setReviewItemId(item.getId());
        }
        if (reviewId != null) {
            request.setReviewId(reviewId);
        }
        request.setScorecardQuestionId(item.getQuestion());
        if (item.getDocument() != null) {
            request.setUploadId(item.getDocument());
        }
        if (item.getAnswer() != null) {
            request.setAnswer(item.getAnswer().toString());
        }
        if (sort != null) {
            request.setSort(sort);
        }
        if (operator != null) {
            if (!update) {
                request.setCreateUser(operator);
            }
            request.setModifyUser(operator);
        }
        if (date != null) {
            if (!update) {
                request.setCreateDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
            }
            request.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
        }
        return request.build();
    }

    private ReviewItemCommentProto buildReviewItemComment(Comment comment, Long itemId, Long sort, String operator,
            Date date, boolean update) {
        ReviewItemCommentProto.Builder request = ReviewItemCommentProto.newBuilder();
        if (update) {
            request.setReviewItemCommentId(comment.getId());
        }
        if (itemId != null) {
            request.setReviewItemId(itemId);
        }
        request.setResourceId(comment.getAuthor());
        if (comment.getCommentType() != null) {
            request.setCommentTypeId(comment.getCommentType().getId());
        }
        if (comment.getComment() != null) {
            request.setContent(comment.getComment());
        }
        if (comment.getExtraInfo() != null) {
            request.setExtraInfo(comment.getExtraInfo().toString());
        }
        if (sort != null) {
            request.setSort(sort);
        }
        if (operator != null) {
            if (!update) {
                request.setCreateUser(operator);
            }
            request.setModifyUser(operator);
        }
        if (date != null) {
            if (!update) {
                request.setCreateDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
            }
            request.setModifyDate(Timestamp.newBuilder().setSeconds(date.toInstant().getEpochSecond()).build());
        }
        return request.build();
    }
}
