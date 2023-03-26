/*
 * Copyright (c) 2006-2012, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.onlinereview.component.scorecard;

import com.topcoder.onlinereview.component.grpcclient.scorecard.ScorecardServiceRpc;
import com.topcoder.onlinereview.component.project.management.LogMessage;
import com.topcoder.onlinereview.grpc.scorecard.proto.ScorecardProto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This class contains operations to create and update scorecard instances into the Informix
 * database. It implements the ScorecardPersistence interface to provide a plug-in persistence for
 * Informix database. It is used by the ScorecardManagerImpl class. The constructor takes a
 * namespace parameter to initialize database connection. Note that in this class, delete operation
 * is not supported. To delete the scorecard, its status is set to 'Disabled'. The create and update
 * operation here work on the scorecard and including its sub items as well. It means
 * creating/updating a scorecard would involve creating/updating its sub groups/sections and
 * questions. The get scorecard operation including a 'complete' parameter, so the scorecard can be
 * retrieve with or without its sub items. Thread Safety: The implementation is not thread safe in
 * that two threads running the same method will use the same statement and could overwrite each
 * other's work.
 *
 * @author tuenm
 * @author kr00tki
 * @author George1
 * @author Angen
 * @version 1.0.3
 */
@Slf4j
@Component
public class ScorecardPersistence {
  @Autowired
  ScorecardServiceRpc scorecardServiceRpc;

  @Autowired private GroupPersistence groupPersistence;
  @Autowired private SectionPersistence sectionPersistence;
  @Autowired private QuestionPersistence questionPersistence;

  /**
   * Create the scorecard in the database using the given scorecard instance. The scorecard instance
   * can include sub items such as groups, sections and questions. Those sub items will be persisted
   * as well. The operator parameter is used as the creation/modification user of the scorecard and
   * its subitems. The creation date and modification date will be the current date time when the
   * scorecard is created.
   *
   * @param scorecard The scorecard instance to be created in the database.
   * @param operator The creation user of this scorecard.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void createScorecard(Scorecard scorecard, String operator) throws PersistenceException {
    if (scorecard == null) {
      throw new IllegalArgumentException("scorecard cannot be null.");
    }
    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }
    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }
    log.debug(new LogMessage(null, operator, "Create new Scorecard.").toString());
    try {
      // get the current time
      Date time = new Date();
      // create scorecard
      long scorecardId = scorecardServiceRpc.createScorecard(scorecard, operator, time);

      // create groups
      groupPersistence.createGroups(scorecard.getAllGroups(), operator, scorecardId);

      // set the audit data
      scorecard.setId(scorecardId);
      scorecard.setModificationTimestamp(time);
      scorecard.setCreationTimestamp(time);
      scorecard.setCreationUser(operator);
      scorecard.setModificationUser(operator);
    } catch (Exception ex) {
      log.error(new LogMessage(null, operator, "Fail to create Scorecard.", ex).toString());
      throw new PersistenceException("Error occur during create operation.", ex);
    }
  }

  /**
   * Update the given scorecard instance into the database. The scorecard instance can include sub
   * items such as groups, sections and questions. Those sub items will be updated as well. If sub
   * items are removed from the scorecard, they will be deleted from the persistence. Likewise, if
   * new sub items are added, they will be created in the persistence. The operator parameter is
   * used as the modification user of the scorecard and its subitems. The modification date will be
   * the current date time when the scorecard is updated.
   *
   * @param scorecard The scorecard instance to be updated into the database.
   * @param operator The modification user of this scorecard.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  @Transactional
  public void updateScorecard(Scorecard scorecard, String operator) throws PersistenceException {
    if (scorecard == null) {
      throw new IllegalArgumentException("scorecard cannot be null.");
    }
    if (operator == null) {
      throw new IllegalArgumentException("operator cannot be null.");
    }
    if (operator.trim().length() == 0) {
      throw new IllegalArgumentException("operator cannot be empty String.");
    }
    log.debug(new LogMessage(scorecard.getId(), operator, "update Scorecard.").toString());
    try {
      // get the old scorecard
      Scorecard oldScorecard = getScorecard(scorecard.getId(), true);
      if (oldScorecard.isInUse()) {
        log.error(new LogMessage(null, operator, "The scorecard is in use.").toString());
        throw new PersistenceException("The scorecard is in use. Id: " + scorecard.getId());
      }
      // increment the version number
      String version = incrementVersion(oldScorecard.getVersion());
      // get old group id
      Set<Long> oldGroupsIds = getGroupsIds(oldScorecard);
      // create update time
      Date time = new Date();
      // update the scorecard data
      updateScorecard(scorecard, operator, time, version);

      // get the new groups
      Group[] newGroups = scorecard.getAllGroups();
      List<Long> deletedSectionIds = new ArrayList<>();
      List<Long> deletedQuestionsIds = new ArrayList<>();

      // add all old groups to the delete list
      List<Long> deletedGroupsIds = new ArrayList<>(oldGroupsIds);

      for (int i = 0; i < newGroups.length; i++) {
        Long longId = newGroups[i].getId();

        // if is a new group - create it
        if (newGroups[i].getId() == NamedScorecardStructure.SENTINEL_ID) {
          groupPersistence.createGroup(newGroups[i], i, operator, scorecard.getId());
        } else if (oldGroupsIds.contains(longId)) {
          // if this group exists - update it and remove from the delete list
          groupPersistence.updateGroup(
              newGroups[i],
              i,
              operator,
              scorecard.getId(),
              oldScorecard,
              deletedSectionIds,
              deletedQuestionsIds);
          deletedGroupsIds.remove(longId);
        }
      }

      // delete the groups
      if (deletedGroupsIds.size() > 0) {
        groupPersistence.deleteGroups(DBUtils.listToArray(deletedGroupsIds));
      }

      // delete the sections
      if (deletedSectionIds.size() > 0) {
        sectionPersistence.deleteSections(DBUtils.listToArray(deletedSectionIds));
      }

      // delete the question
      if (deletedQuestionsIds.size() > 0) {
        questionPersistence.deleteQuestions(DBUtils.listToArray(deletedQuestionsIds));
      }

      log.debug("commit the transaction.");
      // commit transaction and set the modification user and date
      scorecard.setVersion(version);
      scorecard.setModificationTimestamp(time);
      scorecard.setModificationUser(operator);
    } catch (Exception ex) {
      String errMsg = scorecard + " op:" + operator;
      errMsg =
          "Scorecard Status: "
              + scorecard.getScorecardStatus().getId()
              + " - Scorecard Type: "
              + scorecard.getScorecardType().getId()
              + " - Scorecard Category: "
              + scorecard.getCategory()
              + " - Scorecard Name: "
              + scorecard.getName()
              + " - Scorecard Version: "
              + scorecard.getVersion()
              + " - Scorecard Min Score: "
              + scorecard.getMinScore()
              + " - Scorecard Max Score: "
              + scorecard.getMaxScore()
              + " - Operator: "
              + operator
              + " - Scorecard ID: "
              + scorecard.getId();
      log.error(
          new LogMessage(scorecard.getId(), operator, "Failed to update Scorecard." + errMsg, ex)
              .toString());
      throw new PersistenceException("Error occurs while deleting the scorecard: " + errMsg, ex);
    }
  }

  /**
   * Returns the set of gourp ids for given scorecard.
   *
   * @param oldScorecard the cource scorcard.
   * @return the set of groups ids.
   */
  private static Set<Long> getGroupsIds(Scorecard oldScorecard) {
    Set<Long> ids = new HashSet<>();
    Group[] groups = oldScorecard.getAllGroups();

    for (int i = 0; i < groups.length; i++) {
      ids.add(new Long(groups[i].getId()));
    }

    return ids;
  }

  /**
   * Updates the Scorecard in the database.
   *
   * @param scorecard the scorecard to be updated.
   * @param operator the update operator.
   * @param time the operation timestamp.
   * @param version the scorecard version.
   * @throws SQLException if any database error occurs.
   */
  private void updateScorecard(Scorecard scorecard, String operator, Date time, String version) {
    log.debug("update scorecard with id : " + scorecard.getId());
    scorecardServiceRpc.updateScorecard(scorecard, operator, time, version);
  }

  /**
   * Increments the version minor number. If the version contains dot, the value after the last will
   * be incremented. In not, the ".1" will be added at the end.
   *
   * @param version the version to increment.
   * @return the incremented version.
   */
  private static String incrementVersion(String version) {
    int idx = version.lastIndexOf('.');

    if (idx == -1) {
      return version + ".1";
    }

    int minor = Integer.parseInt(version.substring(idx + 1)) + 1;

    return version.substring(0, idx) + "." + minor;
  }

  /**
   * Retrieves the scorecard instance from the persistence given its id. The scorecard instance can
   * be retrieved with or without its sub items, depends on the 'complete' parameter.
   *
   * @return The scorecard instance.
   * @param id The id of the scorecard to be retrieved.
   * @param complete Indicates whether to retrieve the scorecard including its sub items.
   * @throws IllegalArgumentException if the input id is less than or equal to zero.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Scorecard getScorecard(Long id, boolean complete) throws PersistenceException {
    Scorecard[] result = getScorecards(new Long[] {id}, complete);

    if (result.length > 0) {
      return result[0];
    }

    return null;
  }

  /**
   * Retrieves all the scorecard types from the persistence.
   *
   * @return An array of scorecard type instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
    log.debug("get all scorecard types.");
    try {
      List<ScorecardType> result = scorecardServiceRpc.getAllScorecardTypes();
      // convert the list to array
      return result.toArray(new ScorecardType[result.size()]);
    } catch (Exception ex) {
      log.error("Failed to get all scorecard types. \n", ex);
      throw new PersistenceException("Error occur during database operation.", ex);
    }
  }

  /**
   * Retrieves all the question types from the persistence.
   *
   * @return An array of question type instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public QuestionType[] getAllQuestionTypes() throws PersistenceException {
    log.debug("get all question types.");
    try {
      List<QuestionType> result = scorecardServiceRpc.getAllQuestionTypes();
      // convert the list to array
      return result.toArray(new QuestionType[result.size()]);
    } catch (Exception ex) {
      log.error("Failed to get all question types. \n", ex);
      throw new PersistenceException("Error occur during database operation.", ex);
    }
  }

  /**
   * Retrieves all the scorecard statuses from the persistence.
   *
   * @return An array of scorecard status instances.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public ScorecardStatus[] getAllScorecardStatuses() throws PersistenceException {
    log.debug("get all scorecard status.");
    try {
      List<ScorecardStatus> result = scorecardServiceRpc.getAllScorecardStatuses();
      // convert the list to array
      return result.toArray(new ScorecardStatus[result.size()]);
    } catch (Exception ex) {
      log.error("Failed to get all scorecard status. \n", ex);
      throw new PersistenceException("Error occur during database operation.", ex);
    }
  }

  /**
   * Retrieves an array of the scorecard instances from the persistence given their ids. The
   * scorecard instances can be retrieved with or without its sub items, depends on the 'complete'
   * parameter.
   *
   * @param ids The array of ids of the scorecards to be retrieved.
   * @param complete Indicates whether to retrieve the scorecard including its sub items.
   * @return An array of scorecard instances.
   * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array
   *     is null or empty.
   * @throws PersistenceException if error occurred while accessing the persistence.
   */
  public Scorecard[] getScorecards(Long[] ids, boolean complete) throws PersistenceException {
    DBUtils.checkIdsArray(ids, "ids");
    log.debug(
        new LogMessage(
                null,
                null,
                "retrieve scorecard with ids:"
                    + ids
                    + ", and is "
                    + (complete ? "complete" : "incomplete"))
            .toString());
    Set<Long> inUseIds = selectInUse(ids);
    List<Scorecard> scorecards = getScorecards(ids, inUseIds);

    if (complete) {
      for (Iterator<Scorecard> it = scorecards.iterator(); it.hasNext(); ) {
        Scorecard card = (Scorecard) it.next();
        card.addGroups(groupPersistence.getGroups(card.getId()));
      }
    }
    return (Scorecard[]) scorecards.toArray(new Scorecard[scorecards.size()]);
  }

  /**
   * Retrieves an array of the scorecard instances from the persistence given their ids. The
   * scorecard instances can be retrieved with or without its sub items, depends on the 'complete'
   * parameter.
   *
   * @param resultSet The result of the SELECT operation.
   * @param complete Indicates whether to retrieve the scorecard including its sub items.
   * @return An array of scorecard instances.
   * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array
   *     is null or empty.
   * @throws PersistenceException if error occurred while accessing the persistence.
   */
  public Scorecard[] getScorecards(List<ScorecardProto> resultSet, boolean complete)
      throws PersistenceException {
    if (resultSet == null) {
      throw new IllegalArgumentException("resultSet cannot be null.");
    }
    List<Scorecard> scorecards = new ArrayList<>();
    try {
      for (ScorecardProto row : resultSet) {
        Scorecard scorecard = populateScorecard(row);
        if (groupPersistence != null) {
          scorecard.addGroups(groupPersistence.getGroups(scorecard.getId()));
        }
        scorecards.add(scorecard);
      }
      return (Scorecard[]) scorecards.toArray(new Scorecard[scorecards.size()]);
    } catch (Exception icse) {
      throw new PersistenceException("Error occured fetching scorecards from the database.", icse);
    }
  }

  /**
   * Retrieves the scorecard type ids and scorecard ids for a specific category from default
   * scorecards table. This method takes as a parameter projectCategoryId, gets the id information
   * of scorecards for it, and returns an array of ScorecardIDInfo instances, each one containing
   * the scorecard type id and the scorecard id.
   *
   * @param projectCategoryId the id of the project category.
   * @return the ScorecardIDInfo instances array, each one containing the scorecard type id and the
   *     scorecard id.
   * @throws PersistenceException if error occurred while accessing the persistence.
   * @throws IllegalArgumentException if the projectCategoryId is less than or equal to zero.
   */
  public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long projectCategoryId)
      throws PersistenceException {
    if (projectCategoryId <= 0) {
      throw new IllegalArgumentException("The projectCategoryId should be positive.");
    }
    log.debug(
        new LogMessage(
                null,
                null,
                "retrieve scorecards id info with projectCategoryId:" + projectCategoryId)
            .toString());
    try {
      List<ScorecardIDInfo> scorecardsInfo = scorecardServiceRpc.getDefaultScorecardsIDInfo(projectCategoryId);
      return scorecardsInfo.toArray(new ScorecardIDInfo[scorecardsInfo.size()]);
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  null,
                  null,
                  "Fails to retrieve scorecards id info with projectCategoryId:"
                      + projectCategoryId)
              .toString());
      throw new PersistenceException("Error occurs during database operation.", ex);
    }
  }

  /**
   * Create the Scorecard instance using the data from the ResultSet object.
   *
   * @param rs the source result set.
   * @return the Scorecard instance.
   */
  private static Scorecard populateScorecard(ScorecardProto s) {
    Scorecard card = new Scorecard(s.getScorecardId());
    if (s.hasProjectCategoryId()) {
      card.setCategory(s.getProjectCategoryId());
    }
    if (s.hasVersion()) {
      card.setVersion(s.getVersion());
    }
    if (s.hasMinScore()) {
      card.setMinScore(s.getMinScore());
    }
    if (s.hasMaxScore()) {
      card.setMaxScore(s.getMaxScore());
    }
    if (s.hasName()) {
      card.setName(s.getName());
    }

    ScorecardStatus status = new ScorecardStatus();
    if (s.hasScorecardStatusId()) {
      status.setId(s.getScorecardStatusId());
    }
    if (s.hasScorecardStatusName()) {
      status.setName(s.getScorecardStatusName());
    }
    card.setScorecardStatus(status);
    ScorecardType type = new ScorecardType();
    if (s.hasScorecardTypeId()) {
      type.setId(s.getScorecardTypeId());
    }
    if (s.hasScorecardTypeName()) {
      type.setName(s.getScorecardTypeName());
    }
    card.setScorecardType(type);
    if (s.hasCreateUser()) {
      card.setCreationUser(s.getCreateUser());
    }
    if (s.hasCreateDate()) {
      card.setCreationTimestamp(new Date(s.getCreateDate().getSeconds() * 1000));
    }
    if (s.hasModifyUser()) {
      card.setModificationUser(s.getModifyUser());
    }
    if (s.hasModifyDate()) {
      card.setModificationTimestamp(new Date(s.getModifyDate().getSeconds() * 1000));
    }

    return card;
  }

  /**
   * Returns the list of all scorecards with the id from the ids array.
   *
   * @param ids the scorecards ids.
   * @param inUseIds the set of scorecards in use ids.
   * @return the list of Scorecards.
   * @throws PersistenceException if any error occurs.
   */
  private List<Scorecard> getScorecards(Long[] ids, Set<Long> inUseIds) throws PersistenceException {
    try {
      List<ScorecardProto> response = scorecardServiceRpc.getScorecards(ids);
      List<Scorecard> result = new ArrayList<>();
      for (ScorecardProto s : response) {
        Scorecard card = populateScorecard(s);
        if (inUseIds.contains(card.getId())) {
          card.setInUse(true);
        }
        result.add(card);
      }
      return result;
    } catch (Exception ex) {
      log.error(
          new LogMessage(null, null, "Fails to retrieve scorecards:" + ids + ".", ex).toString());
      throw new PersistenceException("Error occurs while retrieving scorecards.", ex);
    }
  }

  /**
   * Checks if the scorecard is in use or not. It returns the set of ids that are in use.
   *
   * @param ids the scorecards ids to check.
   * @return true if the scorecard is in use.
   * @throws PersistenceException if database error occurs.
   */
  private Set<Long> selectInUse(Long[] ids) throws PersistenceException {
    Set<Long> result = new HashSet<Long>();
    try {
      List<Long> response = scorecardServiceRpc.getScorecardsInUse(ids);
      result.addAll(response);
      return result;
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  null, null, "Fails to check if the scorecards:" + ids + " in use or not.", ex)
              .toString());
      throw new PersistenceException("Error occurs during database operation.", ex);
    }
  }
}
