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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.getFloat;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

/**
 * This class contains operations to create and update section instances into the Informix database.
 * It is package level because it is used only in InformixGroupPersistence class to persist section
 * information. Connection to the database is passed to this class during initialization. Thread
 * Safety: The implementation is not thread safe in that two threads running the same method will
 * use the same statement and could overwrite each other's work.
 *
 * @author tuenm
 * @author kr00tki
 * @version 1.0.3
 */
@Slf4j
@Component
public class SectionPersistence {
  /** Select sections by parent group id. */
  private static final String SELECT_SECTIONS_BY_PARENT_ID =
      "SELECT scorecard_section_id, name, "
          + "weight FROM scorecard_section WHERE scorecard_group_id = ? ORDER BY sort";

  /** Select the section by its id. */
  private static final String SELECT_SCORECARD_SECTION_BY_ID =
      "SELECT scorecard_section_id, name, "
          + "weight FROM scorecard_section WHERE scorecard_section_id = ?";

  /** Deletes the sections with ids. */
  private static final String DELETE_SCORECARD_SECTIONS =
      "DELETE FROM scorecard_section WHERE " + "scorecard_section_id IN ";

  /** Selects questions ids for sections. */
  private static final String SELECT_QUESTION_IDS =
      "SELECT scorecard_question_id FROM scorecard_question " + "WHERE scorecard_section_id IN ";

  /** Updates the section in database. */
  private static final String UPDATE_SCORECARD_SECTION =
      "UPDATE scorecard_section SET scorecard_group_id = ?, "
          + "name = ?, weight = ?, sort = ?, modify_user = ?, modify_date = ? WHERE scorecard_section_id = ?";

  /** Inserts the section informations. */
  private static final String INSERT_SCORECARD_SECTION =
      "INSERT INTO scorecard_section (scorecard_section_id, "
          + "scorecard_group_id, name, weight, sort, create_user, create_date, modify_user, modify_date) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  /** The default name of the id generator for the scorecards. */
  private static final String SCORECARD_SECTION_ID_SEQUENCE = "scorecard_section_id_seq";

  /** The IDGenerator instance used for scorecards ids. */
  private IDGenerator sectionIdGenerator;

  @Autowired @Qualifier("entityManagerMap")private Map<String, EntityManager> entityManagerMap;

  @Value("${scorecard.persistence.entity-manager-name}")
  private String entityManagerName;

  @Autowired private DBHelper dbHelper;
  @Autowired private QuestionPersistence questionPersistence;
  private EntityManager entityManager;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    sectionIdGenerator = new IDGenerator(SCORECARD_SECTION_ID_SEQUENCE, dbHelper);
    entityManager = entityManagerMap.get(entityManagerName);
  }

  /**
   * Create the section in the database using the given section instance. The section instance can
   * include sub items such as questions. Those sub items will be persisted as well. The operator
   * parameter is used as the creation/modification user of the section and its subitems. The
   * creation date and modification date will be the current date time when the section is created.
   *
   * @param section The section instance to be created in the database.
   * @param order the position of the section.
   * @param operator The creation user of this section.
   * @param parentId The id of the group that contains this.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void createSection(Section section, int order, String operator, long parentId)
      throws PersistenceException {
    if (section == null) {
      throw new IllegalArgumentException("section cannot be null.");
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
                "create new Section with order:" + order + " and parentId:" + parentId)
            .toString());

    var time = new Date();
    try {
      long id = sectionIdGenerator.getNextID();
      log.debug("insert record into scorecard_section with group_id:" + id);
      executeSqlWithParam(
          entityManager,
          INSERT_SCORECARD_SECTION,
          newArrayList(
              id,
              parentId,
              section.getName(),
              section.getWeight(),
              order,
              operator,
              time,
              operator,
              time));
      questionPersistence.createQuestions(section.getAllQuestions(), operator, id);
      section.setId(id);
    } catch (Exception ex) {
      log.error(new LogMessage(null, operator, "Fail to create Section.", ex).toString());
      throw new PersistenceException("Error occurs while creating sections.", ex);
    }
  }

  /**
   * Creates the sections in the database.
   *
   * @param sections the sections to be created.
   * @param operator the creation operator.
   * @param parentId the parent group id.
   * @throws PersistenceException if database error occurs.
   */
  void createSections(Section[] sections, String operator, long parentId)
      throws PersistenceException {
    log.debug(
        new LogMessage(null, operator, "create new Sections with parentId:" + parentId).toString());
    long[] ids = DBUtils.generateIdsArray(sections.length, sectionIdGenerator);
    var time = new Date();
    try {
      for (int i = 0; i < sections.length; i++) {
        executeSqlWithParam(
            entityManager,
            INSERT_SCORECARD_SECTION,
            newArrayList(
                ids[i],
                parentId,
                sections[i].getName(),
                sections[i].getWeight(),
                i,
                operator,
                time,
                operator,
                time));
        log.debug("insert record into scorecard_section table with groupId:" + ids[i]);

        questionPersistence.createQuestions(sections[i].getAllQuestions(), operator, ids[i]);
      }
    } catch (Exception ex) {
      log.error(
          new LogMessage(null, operator, "create new Sections with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while creating sections.", ex);
    }
    // assign the ids
    for (int i = 0; i < sections.length; i++) {
      sections[i].setId(ids[i]);
    }
  }

  /**
   * Update the given section instance into the database. The section instance can include sub items
   * such as questions. Those sub items will be updated as well. If sub items are removed from the
   * section, they will be deleted from the persistence. Likewise, if new sub items are added, they
   * will be created in the persistence. The operator parameter is used as the modification user of
   * the section and its subitems. The modification date will be the current date time when the
   * section is updated.
   *
   * @param section The section instance to be updated into the database.
   * @param order the position of the section in persistence.
   * @param operator The modification user of this section.
   * @param parentId The id of the group that contains this.
   * @param oldScorecard The scorecard instance before update. It is used to find out remeved items.
   * @param deletedQuestionIds This is an output parameter. An empty array is expected to be passed
   *     in. Deleted question ids will be saved into this list.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void updateSection(
      Section section,
      int order,
      String operator,
      long parentId,
      Scorecard oldScorecard,
      List deletedQuestionIds)
      throws PersistenceException {
    if (section == null) {
      throw new IllegalArgumentException("section cannot be null.");
    }

    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }

    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }

    if (oldScorecard == null) {
      throw new IllegalArgumentException("oldScorecard cannot be null.");
    }

    if (deletedQuestionIds == null) {
      throw new IllegalArgumentException("deletedQuestionIds cannot be null.");
    }

    log.debug(
        new LogMessage(
                new Long(section.getId()),
                operator,
                "create new section with order:"
                    + order
                    + " ,parentId:"
                    + parentId
                    + ", oldScorecard:"
                    + oldScorecard.getId())
            .toString());

    Set oldSectionIds = getQuestionsIds(section, oldScorecard, parentId);
    // add all old questions to be deleted
    deletedQuestionIds.addAll(oldSectionIds);
    Question[] questions = section.getAllQuestions();
    for (int i = 0; i < questions.length; i++) {
      Long longId = new Long(questions[i].getId());
      if (questions[i].getId() == NamedScorecardStructure.SENTINEL_ID) {
        questionPersistence.createQuestion(questions[i], i, operator, section.getId());
      } else if (oldSectionIds.contains(longId)) {
        questionPersistence.updateQuestion(questions[i], i, operator, section.getId());
        deletedQuestionIds.remove(longId);
      }
    }
    updateSection(section, operator, parentId, order);
  }

  /**
   * Updates the section in the persistence.
   *
   * @param section the section to update.
   * @param operator the update opertor name.
   * @param parentId the id of the paren group.
   * @param order the position of the section in persistence.
   * @throws PersistenceException if any database error occurs.
   */
  private void updateSection(Section section, String operator, long parentId, int order)
      throws PersistenceException {
    try {
      executeSqlWithParam(
          entityManager,
          UPDATE_SCORECARD_SECTION,
          newArrayList(
              parentId,
              section.getName(),
              section.getWeight(),
              order,
              operator,
              new Date(),
              section.getId()));
    } catch (Exception ex) {
      log.error(
          new LogMessage(section.getId(), operator, "Error occurs while updating the Section.", ex)
              .toString());
      throw new PersistenceException("Error occurs while updating the section.", ex);
    }
  }

  /**
   * Returns the ids of the questions for the section in the old scorecard.
   *
   * @param section the current section.
   * @param scorecard the previous scorecard.
   * @param groupId the id of the parent group.
   * @return the set of ids of the questions.
   */
  private static Set getQuestionsIds(Section section, Scorecard scorecard, long groupId) {
    Set ids = new HashSet();
    Group[] oldGroups = scorecard.getAllGroups();
    for (int i = 0; i < oldGroups.length; i++) {
      // find group first
      if (oldGroups[i].getId() == groupId) {
        Section[] sections = oldGroups[i].getAllSections();
        for (int j = 0; j < sections.length; j++) {
          // find the section
          if (sections[j].getId() == section.getId()) {
            Question[] questions = sections[j].getAllQuestions();
            // get all question ids.
            for (int k = 0; k < questions.length; k++) {
              ids.add(new Long(questions[k].getId()));
            }
            break;
          }
        }
      }
    }

    return ids;
  }

  /**
   * Remove the sections with the given array of ids from the persistence. Section deletion is
   * required when user udpate a scorecard and remove a section from its section list.
   *
   * @param ids The id of the section to remove.
   * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array
   *     is null or empty.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void deleteSections(long[] ids) throws PersistenceException {
    DBUtils.checkIdsArray(ids, "ids");
    log.debug(new LogMessage(null, null, "Delete Section with ids:" + ids).toString());
    try {
      log.debug("delete record from scorecard_sections with ids:" + ids);
      // execute the select query
      var rs =
          executeSqlWithParam(
              entityManager,
              SELECT_QUESTION_IDS + DBUtils.createQuestionMarks(ids.length),
              newArrayList(ids));
      List idsToDelete = new ArrayList();
      // collect all the question ids
      for (var row : rs) {
        idsToDelete.add(getLong(row, "scorecard_question_id"));
      }
      // if there is anything to delete - create the persistence and drop questions
      if (idsToDelete.size() > 0) {
        questionPersistence.deleteQuestions(DBUtils.listToArray(idsToDelete));
      }

      executeSqlWithParam(entityManager, DELETE_SCORECARD_SECTIONS, newArrayList(ids));
    } catch (Exception ex) {
      log.error(
          new LogMessage(null, null, "Failed to Delete Sections with ids:" + ids, ex).toString());
      throw new PersistenceException("Error occurs while deleting the sections.", ex);
    }
  }

  /**
   * Retrieves the section instance from the persistence given its id. The section instance is
   * always retrieved with its sub items.
   *
   * @return The section instance.
   * @param id The id of the section to be retrieved.
   * @throws IllegalArgumentException if the input id is less than or equal to zero.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Section getSection(long id) throws PersistenceException {
    if (id <= 0) {
      throw new IllegalArgumentException("The section id must be positive. Id = " + id);
    }
    log.debug(new LogMessage(id, null, "retrieve Section").toString());
    try {
      var rs = executeSqlWithParam(entityManager, SELECT_SCORECARD_SECTION_BY_ID, newArrayList(id));

      if (!rs.isEmpty()) {
        Section section = prepareSection(rs.get(0));
        section.addQuestions(questionPersistence.getQuestions(section.getId()));
        return section;
      }
    } catch (SQLException ex) {
      log.error(new LogMessage(id, null, "Failed to retrieve Section", ex).toString());
      throw new PersistenceException("Error occurs while retrieving the section.", ex);
    }

    return null;
  }

  /**
   * Returns the sections for the given parent group.
   *
   * @param parentId the parent group id.
   * @return the array of Sections for group id.
   * @throws PersistenceException id any database errors occurs.
   */
  Section[] getSections(long parentId) throws PersistenceException {
    try {
      var rs =
          executeSqlWithParam(entityManager, SELECT_SECTIONS_BY_PARENT_ID, newArrayList(parentId));
      List result = new ArrayList();
      for (var row : rs) {
        Section section = prepareSection(row);
        section.addQuestions(questionPersistence.getQuestions(section.getId()));
        result.add(section);
      }
      return (Section[]) result.toArray(new Section[result.size()]);
    } catch (SQLException ex) {
      log.error(
          new LogMessage(null, null, "Failed to retrieve Section with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while retrieving the section.", ex);
    }
  }

  /**
   * Creates the Section instance from the given ResultSet.
   *
   * @param rs the source result set.
   * @return the Section instance.
   * @throws SQLException if error occurs.
   */
  private Section prepareSection(Map<String, Object> rs) throws SQLException {
    Section section = new Section();
    section.setId(getLong(rs, "scorecard_section_id"));
    section.setName(getString(rs, "name"));
    section.setWeight(getFloat(rs, "weight"));
    return section;
  }
}
