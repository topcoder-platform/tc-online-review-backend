/*
 * Copyright (c) 2006-2012, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.scorecard;

import com.topcoder.onlinereview.component.grpcclient.scorecard.ScorecardServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.grpc.scorecard.proto.GetQuestionResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains operations to create and update question instances into the Informix
 * database. It is package level because it is used only in InformixSectionPersistence class to
 * persist question information. Connection to the database is passed to this class during
 * initialization. Thread Safety: The implementation is not thread safe in that two threads running
 * the same method will use the same statement and could overwrite each other's work.
 *
 * @author tuenm
 * @author kr00tki
 * @version 1.0.3
 */
@Slf4j
@Component
public class QuestionPersistence {
  @Autowired
  ScorecardServiceRpc scorecardServiceRpc;

  /**
   * Create the question in the database using the given question instance. The operator parameter
   * is used as the creation/modification user of the question. The creation date and modification
   * date will be the current date time when the question is created.
   *
   * @param question The question instance to be created in the database.
   * @param order the order of the question in persistence.
   * @param operator The creation user of this question.
   * @param parentId The id of the section that contains this.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void createQuestion(Question question, int order, String operator, long parentId)
      throws PersistenceException {
    if (question == null) {
      throw new IllegalArgumentException("question cannot be null.");
    }

    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }

    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }

    log.debug(
        new LogMessage(
                null,
                operator,
                "Create new Question with order:" + order + " and parentId:" + parentId)
            .toString());
    try {
      // get the id
      long id = scorecardServiceRpc.createQuestion(question, order, operator, parentId);
      // set the id
      question.setId(id);
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  null,
                  operator,
                  "Failed to Create new Question with order:" + order + " and parentId:" + parentId,
                  ex)
              .toString());
      throw new PersistenceException("Error occurs while creating questions.", ex);
    }
  }

  /**
   * Create the given Questions in the persistence.
   *
   * @param questions the questions to store.
   * @param operator the creation operator.
   * @param parentId the id of the parent section.
   * @throws PersistenceException if any database error occurs.
   */
  void createQuestions(Question[] questions, String operator, long parentId)
      throws PersistenceException {
    log.debug(
        new LogMessage(null, operator, "Create new Questions with parentId:" + parentId)
            .toString());
    try {
      List<Long> questionIds = scorecardServiceRpc.createQuestions(questions, operator, parentId);
      // for each group - set the variables
      for (int i = 0; i < questions.length; i++) {
        questions[i].setId(questionIds.get(i));
      }
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  null, operator, "Failed to Create new Questions with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while creating questions.", ex);
    }
  }

  /**
   * Update the given question instance into the database. The operator parameter is used as the
   * modification user of the question. The modification date will be the current date time when the
   * question is updated.
   *
   * @param question The question instance to be updated into the database.
   * @param order the position of the question in the database.
   * @param operator The modification user of this question.
   * @param parentId The id of the section that contains this.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void updateQuestion(Question question, int order, String operator, long parentId)
      throws PersistenceException {
    if (question == null) {
      throw new IllegalArgumentException("question cannot be null.");
    }

    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }

    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }
    log.debug(
        new LogMessage(
                question.getId(),
                operator,
                "Update Question with order:" + order + " and parentId:" + parentId)
            .toString());
    try {
      log.debug("update record in scorecard_question with question id:" + question.getId());
      scorecardServiceRpc.updateQuestion(question, order, operator, parentId);
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  new Long(question.getId()),
                  operator,
                  "Failed to Update Question with order:" + order + " and parentId:" + parentId,
                  ex)
              .toString());
      throw new PersistenceException("Error occurs while updating the question.", ex);
    }
  }

  /**
   * Remove the questions with the given array of ids from the persistence. Question deletion is
   * required when user udpate a scorecard and remove a question from its question list.
   *
   * @param ids The array of ids of the questions to remove.
   * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array
   *     is null or empty.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void deleteQuestions(Long[] ids) throws PersistenceException {
    DBUtils.checkIdsArray(ids, "ids");
    log.debug(new LogMessage(null, null, "Delete Questions with ids:" + ids).toString());
    try {
      scorecardServiceRpc.deleteQuestions(ids);
    } catch (Exception ex) {
      log.error(
          new LogMessage(null, null, "Failed to delete Questions with ids:" + ids).toString());
      throw new PersistenceException("Error occurs while deleting the questions.", ex);
    }
  }

  /**
   * Retrieves the question instance from the persistence given its id.
   *
   * @return The question instance.
   * @param id The id of the question to be retrieved.
   * @throws IllegalArgumentException if the input id is less than or equal to zero.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Question getQuestion(long id) throws PersistenceException {
    if (id <= 0) {
      throw new IllegalArgumentException("The id must be positive.");
    }
    log.debug(new LogMessage(id, null, "retrieve Question").toString());
    try {
      GetQuestionResponse response = scorecardServiceRpc.getQuestion(id);
      if (response != null) {
        return populateQuestion(response);
      }
    } catch (SQLException ex) {
      log.error(new LogMessage(id, null, "Failed to retrieve Question", ex).toString());
      throw new PersistenceException("Error occurs while retrieving question.", ex);
    }

    return null;
  }

  /**
   * Returns the Questions array for the given parent section id.
   *
   * @param parentId the section id.
   * @return the Questions for the section.
   * @throws PersistenceException if ant database error occurs.
   */
  Question[] getQuestions(long parentId) throws PersistenceException {
    log.debug(
        new LogMessage(null, null, "retrieve Question with parent id:" + parentId).toString());
    try {
      List<GetQuestionResponse> qList = scorecardServiceRpc.getQuestions(parentId);
      List<Question> result = new ArrayList<>();
      for (GetQuestionResponse q : qList) {
        result.add(populateQuestion(q));
      }
      return (Question[]) result.toArray(new Question[result.size()]);
    } catch (SQLException ex) {
      log.error(
          new LogMessage(null, null, "Failed to retrieve Question with parent id:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while retrieving question.", ex);
    }
  }

  /**
   * Creates the question instance using the ResultSet as source.
   *
   * @param rs the source result set.
   * @return the Questio instance.
   * @throws SQLException if any error occurs.
   */
  private Question populateQuestion(GetQuestionResponse q) throws SQLException {
    Question question = new Question();
    question.setId(q.getScorecardQuestionId());
    if (q.hasDescription()) {
      question.setDescription(q.getDescription());
    }
    if (q.hasGuideline()) {
      question.setGuideline(q.getGuideline());
    }
    if (q.hasUploadDocument()) {
      question.setUploadDocument(q.getUploadDocument());
    }
    if (q.hasUploadDocumentRequired()) {
      question.setUploadRequired(q.getUploadDocumentRequired());
    }
    if(q.hasWeight()) {
      question.setWeight(q.getWeight());
    }
    QuestionType type = new QuestionType();
    type.setId(q.getScorecardQuestionTypeId());
    if (q.hasScorecardQuestionTypeName()) {
      type.setName(q.getScorecardQuestionTypeName());
    }
    question.setQuestionType(type);
    return question;
  }
}
