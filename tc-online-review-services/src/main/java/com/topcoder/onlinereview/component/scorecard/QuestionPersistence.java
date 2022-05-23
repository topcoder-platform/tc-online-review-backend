/*
 * Copyright (c) 2006-2012, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.scorecard;

import com.topcoder.onlinereview.component.id.DBHelper;
import com.topcoder.onlinereview.component.id.IDGenerationException;
import com.topcoder.onlinereview.component.id.IDGenerator;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.component.util.CommonUtils.getFloat;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

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
  /** Selects all questions by parent id. */
  private static final String SELECT_SCORECARD_QUESTION_BY_PARENT_ID =
      "SELECT sq.scorecard_question_id, "
          + "sq.description, sq.guideline, sq.weight, sq.upload_document, sq.upload_document_required, "
          + "type.scorecard_question_type_id, type.name AS TypeName FROM scorecard_question AS sq JOIN "
          + "scorecard_question_type_lu AS type ON sq.scorecard_question_type_id = "
          + "type.scorecard_question_type_id WHERE sq.scorecard_section_id = ? ORDER BY sort";

  /** Select the question by its id. */
  private static final String SELECT_SCORECARD_QUESTION_BY_ID =
      "SELECT sq.scorecard_question_id, "
          + "sq.description, sq.guideline, sq.weight, sq.upload_document, sq.upload_document_required, "
          + "type.scorecard_question_type_id, type.name AS TypeName FROM scorecard_question AS sq JOIN "
          + "scorecard_question_type_lu AS type ON sq.scorecard_question_type_id = type.scorecard_question_type_id "
          + "WHERE sq.scorecard_question_id = ?";

  /** Deletes all questions with ids. */
  private static final String DELETE_SCORECARD_QUESTIONS =
      "DELETE FROM scorecard_question " + "WHERE scorecard_question_id IN ";

  /** Updates the question. */
  private static final String UPDATE_SCORECARD_QUESTION =
      "UPDATE scorecard_question SET "
          + "scorecard_question_type_id = ?, scorecard_section_id = ?, description = ?, guideline = ?, "
          + "weight = ?, sort = ?, upload_document = ?, upload_document_required = ?, modify_user = ?, "
          + "modify_date = ? WHERE scorecard_question_id = ?";

  /** Inserts the question to database. */
  private static final String INSERT_SCORECARD_QUESTION =
      "INSERT INTO scorecard_question "
          + "(scorecard_question_id, scorecard_question_type_id, scorecard_section_id, description, "
          + "guideline, weight, sort, upload_document, upload_document_required, create_user, create_date, "
          + "modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  /** The default name of the id generator for the scorecards. */
  private static final String SCORECARD_QUESTION_ID_SEQUENCE = "scorecard_question_id_seq";

  /** The IDGenerator instance used for scorecards ids. */
  private IDGenerator questionIdGenerator;

  @Autowired private DBHelper dbHelper;

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    questionIdGenerator = new IDGenerator(SCORECARD_QUESTION_ID_SEQUENCE, dbHelper);
  }

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
      long id = questionIdGenerator.getNextID();
      log.debug("insert record into scorecard_question with question id:" + id);
      Date time = new Date();
      executeUpdateSql(
          jdbcTemplate,
          INSERT_SCORECARD_QUESTION,
          newArrayList(
              id,
              question.getQuestionType().getId(),
              parentId,
              question.getDescription(),
              question.getGuideline(),
              question.getWeight(),
              order,
              question.isUploadDocument(),
              question.isUploadRequired(),
              operator,
              time,
              time));
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
    // generate the ids
    long[] ids = DBUtils.generateIdsArray(questions.length, questionIdGenerator);
    try {
      Date time = new Date();
      // set the creation values for all questions.
      for (int i = 0; i < questions.length; i++) {
        log.debug("insert record into scorecard_question with question id:" + ids[i]);
        executeUpdateSql(
            jdbcTemplate,
            INSERT_SCORECARD_QUESTION,
            newArrayList(
                ids[i],
                questions[i].getQuestionType().getId(),
                parentId,
                questions[i].getDescription(),
                questions[i].getGuideline(),
                questions[i].getWeight(),
                i,
                questions[i].isUploadDocument(),
                questions[i].isUploadRequired(),
                operator,
                time,
                operator,
                time));
      }
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  null, operator, "Failed to Create new Questions with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while creating questions.", ex);
    }
    // set the ids to questions
    for (int i = 0; i < questions.length; i++) {
      questions[i].setId(ids[i]);
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
      executeUpdateSql(
          jdbcTemplate,
          UPDATE_SCORECARD_QUESTION,
          newArrayList(
              question.getQuestionType().getId(),
              parentId,
              question.getDescription(),
              question.getGuideline(),
              question.getWeight(),
              order,
              question.isUploadDocument(),
              question.isUploadRequired(),
              operator,
              new Date(),
              question.getId()));
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
  public void deleteQuestions(long[] ids) throws PersistenceException {
    DBUtils.checkIdsArray(ids, "ids");
    log.debug(new LogMessage(null, null, "Delete Questions with ids:" + ids).toString());
    try {
      executeUpdateSql(
          jdbcTemplate,
          DELETE_SCORECARD_QUESTIONS + DBUtils.createQuestionMarks(ids.length),
          newArrayList(ids));
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
      List<Map<String, Object>> rs =
          executeSqlWithParam(jdbcTemplate, SELECT_SCORECARD_QUESTION_BY_ID, newArrayList(id));
      if (!rs.isEmpty()) {
        return populateQuestion(rs.get(0));
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
      List<Map<String, Object>> rs =
          executeSqlWithParam(
              jdbcTemplate, SELECT_SCORECARD_QUESTION_BY_PARENT_ID, newArrayList(parentId));
      List result = new ArrayList();
      for (Map<String, Object> row : rs) {
        result.add(populateQuestion(row));
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
  private Question populateQuestion(Map<String, Object> rs) throws SQLException {
    Question question = new Question();
    question.setId(getLong(rs, "scorecard_question_id"));
    question.setDescription(getString(rs, "description"));
    question.setGuideline(getString(rs, "guideline"));
    question.setUploadDocument(getBoolean(rs, "upload_document"));
    question.setUploadRequired(getBoolean(rs, "upload_document_required"));
    question.setWeight(getFloat(rs, "weight"));

    QuestionType type = new QuestionType();
    type.setId(getLong(rs, "scorecard_question_type_id"));
    type.setName(getString(rs, "TypeName"));

    question.setQuestionType(type);
    return question;
  }
}
