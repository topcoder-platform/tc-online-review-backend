/*
 * Copyright (C) 2006-2014 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.onlinereview.component.review.scorecalculator;

import com.topcoder.onlinereview.component.review.Review;
import com.topcoder.onlinereview.component.scorecard.Question;
import com.topcoder.onlinereview.component.scorecard.Scorecard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * A simple facade to the component, which provides an API to evaluate a given review item against a given
 * scorecard structure.
 * </p>
 *
 * <p>
 * To do this it manages a set of score calculators to use for review evaluation. One score calculator will be
 * registered for each used question type occurring in the scorecard. For performance reasons CalculationManager
 * may (optionally) cache intermediate results (ScorecardMatrix objects representing the result of scorecard
 * conversion to the MathMatrix compatible format).
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is thread safe, because it uses a Hashtable (which is thread-safe) to store
 * calculators, and locks the ScorecardMatrix used in the getScore method (in case it is shared via the cache).
 * </p>
 *
 * @author      nicka81, UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0.4
 */
@Slf4j
@Component
public class CalculationManager {
    /**
     * <p>
     * The mapping from a question type to a ScoreCalculator.
     * </p>
     *
     * <p>
     * <b>Invariant</b>: Cannot be null; keys are non-null, positive Long objects; values are non-null
     * ScoreCalculator objects.
     * </p>
     */
    private Map<Long, ScoreCalculator> calculators;

    /**
     * <p>
     * The builder instance that will be used to convert scorecards to the format compatible with the Weighted
     * Calculator component.
     * </p>
     *
     * <p>
     * <b>Invariant</b>: Cannot be null.
     * </p>
     */
    private ScorecardMatrixBuilder scorecardMatrixBuilder;

    @Value("#{'${calculationManager.calculators.binaryQuestionTypes:4}'.split(',')}")
    private List<Long> binaryQuestionTypes;

    @Value("#{'${calculationManager.calculators.binaryAnswers:1:0}'.split(',')}")
    private List<String> binaryAnswers;

    @Value("#{'${calculationManager.calculators.scaleQuestionTypes:1,2,3,5,6,7}'.split(',')}")
    private List<Long> scaleQuestionTypes;

    @Value("#{'${calculationManager.calculators.scaleDefaultScales:4,10,-1,3,9,4}'.split(',')}")
    private List<Long> scaleDefaultScales;

    @PostConstruct
    public void postRun() {
        calculators = new HashMap<>();
        scorecardMatrixBuilder = new ScorecardMatrixBuilder();
        for (int i = 0; i < binaryQuestionTypes.size(); i++) {
            String[] pN = binaryAnswers.get(i).split(":");
            calculators.put(binaryQuestionTypes.get(i), new BinaryScoreCalculator(pN[0], pN[1]));
        }
        for (int i = 0; i < scaleQuestionTypes.size(); i++) {
            if (scaleDefaultScales.get(i) < 0) {
                calculators.put(scaleQuestionTypes.get(i), new ScaledScoreCalculator());
            } else {
                calculators.put(scaleQuestionTypes.get(i), new ScaledScoreCalculator(scaleDefaultScales.get(i)));
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Accessors

    /**
     * <p>
     * Evaluates the given review against the given scorecard and produces a score for it.
     * </p>
     *
     * <p>
     * Please refer to section 1.3.2 of the Component Specifications for a more detailed explanation of how the
     * score is generated.
     * </p>
     *
     * @param   scorecard
     *          The scorecard that the review is based off of.
     * @param   review
     *          The review to be evaluated.
     *
     * @return  The evaluated review score.
     *
     * @throws  CalculationException
     *          If anything not mentioned in the other exception descriptions occurs.
     * @throws  IllegalArgumentException
     *          The scorecard or review is a null reference.
     * @throws  ScoreCalculatorException
     *          The score calculator could not generate a review score.
     * @throws  ScorecardStructureException
     *          The scorecard structure is invalid.
     */
    public double getScore(Scorecard scorecard, Review review) throws CalculationException {

        // Sanity check arguments.
        Util.checkNotNull(scorecard, "scorecard");
        Util.checkNotNull(review, "review");

        // Grab the matrix associated with the scorecard (from cache or newly created).
        final ScorecardMatrix scMatrix = getScorecardMatrix(scorecard);
        final Set seenQuestionIds = new HashSet();

        // Generate the score.
        synchronized (scMatrix) {
            // Get the list of items.
            final com.topcoder.onlinereview.component.review.Item[] items = review.getAllItems();

            if (items.length == 0) {
                throw new ReviewStructureException("The review has no items!", review);
            }

            /*if (items.length != scMatrix.getNumberOfQuestions()) {
                throw new ReviewStructureException(
                        "The review has does not have the same number of questions (" + items.length
                        + ") as the scorecard matrix (" + scMatrix.getNumberOfQuestions() + ")!", review);
            }*/

            // For each review item.
            for (int i = 0; i < items.length; ++i) {
                // Variables for readability.
                final com.topcoder.onlinereview.component.review.Item item = items[i];
                final long questionId = item.getQuestion();
                final Question question = scMatrix.getQuestion(questionId);
                if (question == null) {
                    continue;
                }

                // Ensure the question is valid.
                checkForValidQuestion(question, review, i, seenQuestionIds);

                // Fetch the calculator to apply.
                final long questionTypeId = question.getQuestionType().getId();
                final ScoreCalculator calculator = getScoreCalculator(questionTypeId);

                if (calculator == null) {
                    throw new ScorecardStructureException(
                            "A calculator was not configured for the question type id " + questionTypeId + ".",
                            scorecard);
                }

                // Evaluate the score.
                final double itemScore = calculator.evaluateItem(item, question);

                // Set the line item score.
                scMatrix.getLineItem(questionId).setActualScore(itemScore);
            }

            // Return the final score.
            return (double) scMatrix.getMathMatrix().getWeightedScore();
        }
    }

    /**
     * Gets the ScoreCalculator associated with the given question type.
     *
     * @param   questionType
     *          The question type whose associated ScoreCalculator should be returned.
     *
     * @return  The ScoreCalculator associated with the given question type, or null if no ScoreCalculator is
     *          associated.
     *
     * @throws  IllegalArgumentException
     *          The questionType is not a positive long.
     */
    public ScoreCalculator getScoreCalculator(long questionType) {
        Util.checkNotNonPositive(questionType, "questionType");
        return (ScoreCalculator) calculators.get(new Long(questionType));
    }

    /**
     * Returns the ScorecardMatrix associated with the given scorecard.
     *
     * @param   scorecard
     *          The scorecard to get the ScorecardMatrix for.
     *
     * @return  The ScorecardMatrix associated with the given scorecard.
     *
     * @throws  ScorecardStructureException
     *          If caching is not used, the scorecard matrix could not be built. If caching is used, either the
     *          scorecard has some uninitialized values, or the scorecard matrix could not be built (if not
     *          already in the cache).
     */
    private ScorecardMatrix getScorecardMatrix(Scorecard scorecard) throws ScorecardStructureException {
        return scorecardMatrixBuilder.buildScorecardMatrix(scorecard);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Mutators

    /**
     * <p>
     * Associates the given ScoreCalculator with the given question type.
     * </p>
     *
     * <p>
     * If an association has already been made, the original association will be overwritten by this one being
     * requested.
     * </p>
     *
     * @param   questionType
     *          The question type being associated.
     * @param   scoreCalculator
     *          The calculator being associated.
     *
     * @throws  IllegalArgumentException
     *          The questionType is not a positive long, or the scoreCalculator is null.
     */
    public void addScoreCalculator(long questionType, ScoreCalculator scoreCalculator) {
        Util.checkNotNonPositive(questionType, "questionType");
        Util.checkNotNull(scoreCalculator, "scoreCalculator");

        calculators.put(new Long(questionType), scoreCalculator);
    }

    /**
     * Removes the ScoreCalculator associated with the given question type, and returns the ScoreCalculator.
     *
     * @param   questionType
     *          The question type being disassociated.
     *
     * @return  The ScoreCalculator that was disassociated, or null if no association for the given questionType
     *          was previously made.
     *
     * @throws  IllegalArgumentException
     *          The questionType is not a positive long.
     */
    public ScoreCalculator removeScoreCalculator(long questionType) {
        Util.checkNotNonPositive(questionType, "questionType");
        return (ScoreCalculator) calculators.remove(new Long(questionType));
    }

    /**
     * Clears all associations between question types and score calculators.
     */
    public void clearScoreCalculators() {
        calculators.clear();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Helper Methods - Checkers

    /**
     * Ensures that the given question is valid (i.e. not null, has a valid id, has a valid type and id, and was
     * not seen before).
     *
     * @param   question
     *          The question to be checked.
     * @param   review
     *          The review that is currently being scored.
     * @param   itemIndex
     *          The index of the item that is currently being scored.
     * @param   seenQuestionIds
     *          The set of previously seen question identifiers.
     *
     * @throws  ReviewStructureException
     *          The question is null, does not have a valid id, does not have a type set, does not have a valid
     *          type id, or the question's id was seen previously during this scoring.
     */
    private static void checkForValidQuestion(Question question, Review review, int itemIndex, Set seenQuestionIds)
        throws ReviewStructureException {

        // Check that question exists.
        if (question == null) {
            throw new ReviewStructureException("No question associated with item index " + itemIndex, review);
        }

        // Check that question id is not a duplicate.
        try {
            if (seenQuestionIds.contains(new Long(question.getId()))) {
                throw new ReviewStructureException(
                        "The question id associated with item index " + itemIndex + " was already seen.", review);
            }
        } catch (IllegalStateException ex) {
            throw new ReviewStructureException(
                    "The question id associated with item index " + itemIndex + " was not initialized.", ex, review);
        }

        // Check the question type was initialized.
        if (question.getQuestionType() == null) {
            throw new ReviewStructureException(
                    "The question associated with item index " + itemIndex + " has no question type.", review);
        }

        // Check the question type's id was initialized.
        try {
            question.getQuestionType().getId();
        } catch (IllegalStateException ex) {
            throw new ReviewStructureException(
                    "The question type associated with item index " + itemIndex + " has an uninitialized id.",
                    review);
        }

        // Question looks good; add it to the set.
        seenQuestionIds.add(new Long(question.getId()));
    }
}
