/*
 * Copyright (c) 2006-2012, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.scorecard;

import com.topcoder.onlinereview.component.grpcclient.scorecard.ScorecardServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.grpc.scorecard.proto.GetSectionResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  @Autowired
  ScorecardServiceRpc scorecardServiceRpc;

  @Autowired private QuestionPersistence questionPersistence;

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
    try {
      long id = scorecardServiceRpc.createSection(section, order, operator, parentId);
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
    try {
      List<Long> sectionIds = scorecardServiceRpc.createSections(sections, operator, parentId);
      for (int i = 0; i < sections.length; i++) {
        sections[i].setId(sectionIds.get(i));
        questionPersistence.createQuestions(sections[i].getAllQuestions(), operator, sectionIds.get(i));
      }
    } catch (Exception ex) {
      log.error(
          new LogMessage(null, operator, "create new Sections with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while creating sections.", ex);
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
      List<Long> deletedQuestionIds)
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

    Set<Long> oldSectionIds = getQuestionsIds(section, oldScorecard, parentId);
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
      scorecardServiceRpc.updateSection(section, operator, parentId, order);
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
  private static Set<Long> getQuestionsIds(Section section, Scorecard scorecard, long groupId) {
    Set<Long> ids = new HashSet<>();
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
  public void deleteSections(Long[] ids) throws PersistenceException {
    DBUtils.checkIdsArray(ids, "ids");
    log.debug(new LogMessage(null, null, "Delete Section with ids:" + ids).toString());
    try {
      log.debug("delete record from scorecard_sections with ids:" + ids);
      // execute the select query
      List<Long> idsToDelete = scorecardServiceRpc.getQuestionIds(ids);
      // if there is anything to delete - create the persistence and drop questions
      if (idsToDelete.size() > 0) {
        questionPersistence.deleteQuestions(DBUtils.listToArray(idsToDelete));
      }
      scorecardServiceRpc.deleteSections(ids);
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
      GetSectionResponse response = scorecardServiceRpc.getSection(id);
      if (response != null) {
        Section section = prepareSection(response);
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
      List<GetSectionResponse> sList = scorecardServiceRpc.getSections(parentId);
      List<Section> result = new ArrayList<>();
      for (GetSectionResponse s : sList) {
        Section section = prepareSection(s);
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
  private Section prepareSection(GetSectionResponse s) throws SQLException {
    Section section = new Section();
    section.setId(s.getScorecardSectionId());
    if (s.hasName()) {
      section.setName(s.getName());
    }
    if (s.hasWeight()) {
      section.setWeight(s.getWeight());
    }
    return section;
  }
}
