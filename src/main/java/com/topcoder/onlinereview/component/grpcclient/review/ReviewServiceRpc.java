package com.topcoder.onlinereview.component.grpcclient.review;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import com.topcoder.onlinereview.component.grpcclient.GrpcChannelManager;
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
            ReviewProto r = response.getReviews(i);
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
    }
}
