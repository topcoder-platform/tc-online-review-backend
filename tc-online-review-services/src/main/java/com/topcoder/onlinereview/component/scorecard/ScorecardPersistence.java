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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.util.CommonUtils.getDate;
import static com.topcoder.onlinereview.util.CommonUtils.getFloat;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.util.CommonUtils.getString;

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

  /** Selects the scorecards ids that are in use. */
  private static final String SELECT_IN_USE_IDS =
      "SELECT pc.parameter FROM phase_criteria pc "
          + "JOIN phase_criteria_type_lu pct ON pc.phase_criteria_type_id = pct.phase_criteria_type_id "
          + "WHERE pct.name='Scorecard ID' AND pc.parameter IN ";

  /** Selects the scorecards by ids. */
  private static final String SELECT_SCORECARD =
      "SELECT sc.scorecard_id, status.scorecard_status_id, "
          + "type.scorecard_type_id, sc.project_category_id, sc.name, sc.version, sc.min_score, "
          + "sc.max_score, sc.create_user, sc.create_date, sc.modify_user, sc.modify_date, "
          + "status.name AS StatusName, type.name AS TypeName FROM scorecard AS sc JOIN scorecard_type_lu AS type "
          + "ON sc.scorecard_type_id=type.scorecard_type_id JOIN scorecard_status_lu AS status ON "
          + "sc.scorecard_status_id=status.scorecard_status_id WHERE sc.scorecard_id IN ";

  /** Selects the scorecards statuses. */
  private static final String SELECT_SCORECARD_STATUS =
      "SELECT scorecard_status_id, name FROM scorecard_status_lu";

  /** Selects the question types. */
  private static final String SELECT_SCORECARD_QUESTION_TYPE =
      "SELECT scorecard_question_type_id, name " + "FROM scorecard_question_type_lu";

  /** Selects the scorecards types. */
  private static final String SELECT_SCORECARD_TYPE =
      "SELECT scorecard_type_id, name FROM scorecard_type_lu";

  /** Selects the default scorecards. */
  private static final String SELECT_DEFAULT_SCORECARDS_ID_INFO =
      "SELECT scorecard_type_id, scorecard_id "
          + "FROM default_scorecard WHERE project_category_id = ";

  /** Updates the scorecard. */
  private static final String UPDATE_SCORECARD =
      "UPDATE scorecard SET scorecard_status_id = ?, "
          + "scorecard_type_id = ?, project_category_id = ?, name = ?, version = ?, min_score = ?, "
          + "max_score = ?, modify_user = ?, modify_date = ? WHERE scorecard_id = ?";

  /** Insert the scorecard data. */
  private static final String INSERT_SCORECARD =
      "INSERT INTO scorecard(scorecard_id, scorecard_status_id, "
          + "scorecard_type_id, project_category_id, name, version, min_score, max_score, create_user, "
          + "create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  /** The default name of the id generator for the scorecards. */
  private static final String SCORECARD_ID_SEQUENCE = "scorecard_id_seq";

  /** The IDGenerator instance used for scorecards ids. */
  private IDGenerator scorecardIdGenerator;

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @Autowired private DBHelper dbHelper;
  @Autowired private GroupPersistence groupPersistence;
  @Autowired private SectionPersistence sectionPersistence;
  @Autowired private QuestionPersistence questionPersistence;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    scorecardIdGenerator = new IDGenerator(SCORECARD_ID_SEQUENCE, dbHelper);
  }

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
      // generate the id first
      long scorecardId = scorecardIdGenerator.getNextID();
      // get the current time
      Date time = new Date();
      // create scorecard
      createScorecard(scorecard, scorecardId, operator, time);

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
   * Creates the scorecard in the database. Inserts the values into the scorecard table.
   *
   * @param scorecard the scorecard to be stored.
   * @param id the id of the scorecard.
   * @param operator the creation operator.
   * @param time the creation time.
   */
  private void createScorecard(Scorecard scorecard, long id, String operator, Date time) {
    log.debug("insert record into scorecard with id:" + id);
    executeUpdateSql(
        jdbcTemplate,
        INSERT_SCORECARD,
        newArrayList(
            id,
            scorecard.getScorecardStatus().getId(),
            scorecard.getScorecardType().getId(),
            scorecard.getCategory(),
            scorecard.getName(),
            scorecard.getVersion(),
            scorecard.getMinScore(),
            scorecard.getMaxScore(),
            operator,
            time,
            operator,
            time));
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
      Set oldGroupsIds = getGroupsIds(oldScorecard);
      // create update time
      Date time = new Date();
      // update the scorecard data
      updateScorecard(scorecard, operator, time, version);

      // get the new groups
      Group[] newGroups = scorecard.getAllGroups();
      List deletedSectionIds = new ArrayList();
      List deletedQuestionsIds = new ArrayList();

      // add all old groups to the delete list
      List deletedGroupsIds = new ArrayList(oldGroupsIds);

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
  private static Set getGroupsIds(Scorecard oldScorecard) {
    Set ids = new HashSet();
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
    executeUpdateSql(
        jdbcTemplate,
        UPDATE_SCORECARD,
        newArrayList(
            scorecard.getScorecardStatus().getId(),
            scorecard.getScorecardType().getId(),
            scorecard.getCategory(),
            scorecard.getName(),
            version,
            scorecard.getMinScore(),
            scorecard.getMaxScore(),
            operator,
            time,
            scorecard.getId()));
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
  public Scorecard getScorecard(long id, boolean complete) throws PersistenceException {
    Scorecard[] result = getScorecards(new long[] {id}, complete);

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
      // create statement and execute select query
      List<Map<String, Object>> rs = executeSql(jdbcTemplate, SELECT_SCORECARD_TYPE);
      List result = new ArrayList();
      // for each row in the result set create new ScorecardType instance.
      for (Map<String, Object> row : rs) {
        long id = getLong(row, "scorecard_type_id");
        String name = getString(row, "name");
        result.add(new ScorecardType(id, name));
      }
      // convert the list to array
      return (ScorecardType[]) result.toArray(new ScorecardType[result.size()]);
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
      // create statement and execute select query
      List<Map<String, Object>> rs = executeSql(jdbcTemplate, SELECT_SCORECARD_QUESTION_TYPE);

      List result = new ArrayList();

      // for each row in the result set create new QuestionType instance.
      for (Map<String, Object> row : rs) {
        Long id = getLong(row, "scorecard_question_type_id");
        String name = getString(row, "name");
        result.add(new QuestionType(id, name));
      }

      // convert the list to array
      return (QuestionType[]) result.toArray(new QuestionType[result.size()]);
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
      // create statement and execute select query
      List<Map<String, Object>> rs = executeSql(jdbcTemplate, SELECT_SCORECARD_STATUS);
      List result = new ArrayList();
      // for each row in the result set create new QuestionType instance.
      for (Map<String, Object> row : rs) {
        long id = getLong(row, "scorecard_status_id");
        String name = getString(row, "name");
        result.add(new ScorecardStatus(id, name));
      }
      // convert the list to array
      return (ScorecardStatus[]) result.toArray(new ScorecardStatus[result.size()]);
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
  public Scorecard[] getScorecards(long[] ids, boolean complete) throws PersistenceException {
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
    Set inUseIds = selectInUse(ids);
    List scorecards = getScorecards(ids, inUseIds);

    if (complete) {
      for (Iterator it = scorecards.iterator(); it.hasNext(); ) {
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
  public Scorecard[] getScorecards(List<Map<String, Object>> resultSet, boolean complete)
      throws PersistenceException {
    if (resultSet == null) {
      throw new IllegalArgumentException("resultSet cannot be null.");
    }
    List scorecards = new ArrayList();
    try {
      for (Map<String, Object> row : resultSet) {
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
    List scorecardsInfo = new ArrayList();
    try {
      List<Map<String, Object>> rs =
          executeSql(jdbcTemplate, SELECT_DEFAULT_SCORECARDS_ID_INFO + projectCategoryId);
      for (Map<String, Object> row : rs) {
        scorecardsInfo.add(
            new ScorecardIDInfo(getLong(row, "scorecard_type_id"), getLong(row, "scorecard_id")));
      }
      return (ScorecardIDInfo[]) scorecardsInfo.toArray(new ScorecardIDInfo[scorecardsInfo.size()]);
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
  private static Scorecard populateScorecard(Map<String, Object> rs) {
    Scorecard card = new Scorecard(getLong(rs, "scorecard_id"));
    card.setCategory(getLong(rs, "project_category_id"));
    card.setVersion(getString(rs, "version"));
    card.setMinScore(getFloat(rs, "min_score"));
    card.setMaxScore(getFloat(rs, "max_score"));
    card.setName(getString(rs, "scorecard_name"));

    ScorecardStatus status = new ScorecardStatus();
    status.setId(getLong(rs, "status_id"));
    status.setName(getString(rs, "status_name"));
    card.setScorecardStatus(status);

    card.setScorecardType(new ScorecardType(getLong(rs, "type_id"), getString(rs, "type_name")));

    card.setModificationUser(getString(rs, "modify_user"));
    card.setCreationUser(getString(rs, "create_user"));
    card.setCreationTimestamp(getDate(rs, "create_date"));
    card.setModificationTimestamp(getDate(rs, "modify_date"));

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
  private List getScorecards(long[] ids, Set<Long> inUseIds) throws PersistenceException {
    try {
      List<Map<String, Object>> rs =
          executeSqlWithParam(
              jdbcTemplate,
              SELECT_SCORECARD + DBUtils.createQuestionMarks(ids.length),
              newArrayList(ids));
      List<Scorecard> result = new ArrayList();
      for (Map<String, Object> row : rs) {
        Scorecard card = populateScorecard(row);
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
   * Create the Scorecard instance using the data from the ResultSet object.
   *
   * @param rs the source result set.
   * @return the Scorecard instance.
   * @throws SQLException if any database error occurs.
   */
  private static Scorecard populateScorecard(ResultSet rs) throws SQLException {
    Scorecard card = new Scorecard(rs.getLong("scorecard_id"));
    card.setCategory(rs.getLong("project_category_id"));
    card.setVersion(rs.getString("version"));
    card.setMinScore(rs.getFloat("min_score"));
    card.setMaxScore(rs.getFloat("max_score"));
    card.setName(rs.getString("name"));

    ScorecardStatus status = new ScorecardStatus();
    status.setId(rs.getLong("scorecard_status_id"));
    status.setName(rs.getString("StatusName"));
    card.setScorecardStatus(status);

    card.setScorecardType(
        new ScorecardType(rs.getLong("scorecard_type_id"), rs.getString("TypeName")));

    card.setModificationUser(rs.getString("modify_user"));
    card.setCreationUser(rs.getString("create_user"));
    card.setCreationTimestamp(rs.getTimestamp("create_date"));
    card.setModificationTimestamp(rs.getTimestamp("modify_date"));

    return card;
  }

  /**
   * Checks if the scorecard is in use or not. It returns the set of ids that are in use.
   *
   * @param ids the scorecards ids to check.
   * @return true if the scorecard is in use.
   * @throws PersistenceException if database error occurs.
   */
  private Set<Long> selectInUse(long[] ids) throws PersistenceException {
    Set<Long> result = new HashSet<Long>();
    try {
      List<Map<String, Object>> rs =
          executeSqlWithParam(
              jdbcTemplate,
              SELECT_IN_USE_IDS + DBUtils.createQuestionMarks(ids.length),
              newArrayList(ids));
      for (Map<String, Object> row : rs) {
        result.add(getLong(row, "parameter"));
      }
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
