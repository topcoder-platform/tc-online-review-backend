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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getFloat;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;

/**
 * This class contains operations to create and update group instances into the Informix database.
 * It is package level because it is used only in InformixScorecardPersistence class to persist
 * group information. Connection to the database is passed to this class during initialization.
 * Thread Safety: The implementation is not thread safe in that two threads running the same method
 * will use the same statement and could overwrite each other's work.
 *
 * @author tuenm
 * @author kr00tki
 * @version 1.0.3
 */
@Slf4j
@Component
public class GroupPersistence {
  /** Selects the groups using the parent id. */
  private static final String SELECT_SCORECARD_GROUP_BY_PARENT_ID =
      "SELECT scorecard_group_id, "
          + "name, weight FROM scorecard_group WHERE scorecard_id = ? ORDER BY sort";

  /** Selects the group be its id. */
  private static final String SELECT_SCORECARD_GROUP_BY_ID =
      "SELECT scorecard_group_id, name, "
          + "weight FROM scorecard_group WHERE scorecard_group_id = ?";

  /** Deletes the set of groups. */
  private static final String DELETE_SCORECARD_GROUPS =
      "DELETE FROM scorecard_group WHERE scorecard_group_id IN ";

  /** Selects the section ids for given set of groups. */
  private static final String SELECT_SCORECARD_SECTION_ID =
      "SELECT scorecard_section_id FROM scorecard_section " + "WHERE scorecard_group_id IN ";

  /** Updates the group table. */
  private static final String UPDATE_SCORECARD_GROUP =
      "UPDATE scorecard_group SET scorecard_id = ?, "
          + "name = ?, weight = ?, sort = ?, modify_user = ?, modify_date = ? WHERE scorecard_group_id = ?";

  /** Inserts the new group the database. */
  private static final String INSERT_SCORECARD_GROUP =
      "INSERT INTO scorecard_group (scorecard_group_id, "
          + "scorecard_id, name, weight, sort, create_user, create_date, modify_user, modify_date) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  /** The default name of the id generator for the scorecards. */
  private static final String SCORECARD_GROUP_ID_SEQUENCE = "scorecard_group_id_seq";

  /** The IDGenerator instance used for scorecards ids. */
  private IDGenerator groupIdGenerator;

  @Autowired private DBHelper dbHelper;

  @Autowired private SectionPersistence sectionPersistence;

  @Autowired
  @Qualifier("tcsJdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  @PostConstruct
  public void postRun() throws IDGenerationException {
    groupIdGenerator = new IDGenerator(SCORECARD_GROUP_ID_SEQUENCE, dbHelper);
  }

  /**
   * Creates the group in the database using the given group instance. The group instance can
   * include sub items such as sections and questions. Those sub items will be persisted as well.
   * The operator parameter is used as the creation/modification user of the group and its subitems.
   * The creation date and modification date will be the current date time when the group is
   * created.
   *
   * @param group The group instance to be created in the database.
   * @param order the sort order of this group.
   * @param operator The creation user of this group.
   * @param parentId The id of the scorecard that contains this.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void createGroup(Group group, int order, String operator, long parentId)
      throws PersistenceException {
    if (group == null) {
      throw new IllegalArgumentException("group cannot be null.");
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
                "create new Group with order:" + order + " and parentId:" + parentId)
            .toString());
    Date time = new Date();
    try {
      // get next id
      long groupId = groupIdGenerator.getNextID();
      log.debug("insert record into scorecard_group with group_id:" + groupId);
      // create group and create the sections
      executeUpdateSql(
          jdbcTemplate,
          INSERT_SCORECARD_GROUP,
          newArrayList(
              groupId,
              parentId,
              group.getName(),
              group.getWeight(),
              order,
              operator,
              time,
              operator,
              time));
      sectionPersistence.createSections(group.getAllSections(), operator, groupId);
      // set the group id.
      group.setId(groupId);
    } catch (Exception ex) {
      log.error(new LogMessage(null, operator, "Fail to create Group.", ex).toString());
      throw new PersistenceException("Error occur while creating the scorecard group.", ex);
    }
  }

  /**
   * This method creates the groups instances in the datastore. It has the same behavior as {@link
   * #createGroup(Group, int, String, long)} except it executes insert at once.
   *
   * @param groups the groups to be created.
   * @param operator the creation operator.
   * @param parentId the id of the parent scorecard.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  void createGroups(Group[] groups, String operator, long parentId) throws PersistenceException {
    log.debug(
        new LogMessage(null, operator, "create new Groups with parentId:" + parentId).toString());
    // generate the ids
    long[] groupIds = DBUtils.generateIdsArray(groups.length, groupIdGenerator);
    Date time = new Date();
    try {
      // for each group - set the variables
      for (int i = 0; i < groups.length; i++) {
        // execute the update and creates sections of this group
        executeUpdateSql(
            jdbcTemplate,
            INSERT_SCORECARD_GROUP,
            newArrayList(
                groupIds[i],
                parentId,
                groups[i].getName(),
                groups[i].getWeight(),
                i,
                operator,
                time,
                operator,
                time));
        log.debug("insert record into scorecard_group table with groupId:" + groupIds[i]);
        sectionPersistence.createSections(groups[i].getAllSections(), operator, groupIds[i]);
      }
    } catch (Exception ex) {
      log.error(
          new LogMessage(
                  null, operator, "Failed to create new Groups with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occur while creating the scorecard group.", ex);
    }

    // set ids to groups
    for (int i = 0; i < groups.length; i++) {
      groups[i].setId(groupIds[i]);
    }
  }

  /**
   * Update the given group instance into the database. The group instance can include sub items
   * such as sections and questions. Those sub items will be updated as well. If sub items are
   * removed from the group, they will be deleted from the persistence. Likewise, if new sub items
   * are added, they will be created in the persistence. The operator parameter is used as the
   * modification user of the group and its subitems. The modification date will be the current date
   * time when the group is updated.
   *
   * @param group The group instance to be updated into the database.
   * @param order the position of the group.
   * @param operator The modification user of this group.
   * @param parentId The id of the scorecard that contains this.
   * @param oldScorecard The scorecard instance before update. It is used to find out remeved items.
   * @param deletedSectionIds This is an output parameter. An empty array is expected to be passed
   *     in. Deleted section ids will be saved into this list.
   * @param deletedQuestionIds This is an output parameter. An empty array is expected to be passed
   *     in. Deleted question ids will be saved into this list. Delete question ids is collected
   *     from updateSection() call.
   * @throws IllegalArgumentException if any input is null or the operator is empty string.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void updateGroup(
      Group group,
      int order,
      String operator,
      long parentId,
      Scorecard oldScorecard,
      List deletedSectionIds,
      List deletedQuestionIds)
      throws PersistenceException {
    if (group == null) {
      throw new IllegalArgumentException("group cannot be null.");
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

    if (deletedSectionIds == null) {
      throw new IllegalArgumentException("deletedSectionIds cannot be null.");
    }

    if (deletedQuestionIds == null) {
      throw new IllegalArgumentException("deletedQuestionIds cannot be null.");
    }

    log.debug(
        new LogMessage(
                group.getId(),
                operator,
                "create new Group with order:"
                    + order
                    + " ,parentId:"
                    + parentId
                    + ", oldScorecard:"
                    + oldScorecard.getId())
            .toString());

    Set oldSectionIds = getSectionsIds(group, oldScorecard);
    // mark all old section as 'to delete'
    deletedSectionIds.addAll(oldSectionIds);

    // get the section and create its persistence
    Section[] sections = group.getAllSections();
    // for each new section
    for (int i = 0; i < sections.length; i++) {
      Long longId = sections[i].getId();
      // if is new - create it
      if (sections[i].getId() == NamedScorecardStructure.SENTINEL_ID) {
        sectionPersistence.createSection(sections[i], i, operator, group.getId());
      } else if (oldSectionIds.contains(longId)) {
        // if is old - update it and removed from delete list
        sectionPersistence.updateSection(
            sections[i], i, operator, group.getId(), oldScorecard, deletedQuestionIds);
        deletedSectionIds.remove(longId);
      }
    }
    // update the group in the database
    updateGroup(group, operator, parentId, order);
  }

  /**
   * Updates the group (the scorecard_group table) in the database.
   *
   * @param group the groupt to update.
   * @param operator the update operator name.
   * @param parentId the parent of this group (scorecard id).
   * @param order the order of this group in the database.
   * @throws PersistenceException if any database error occurs.
   */
  private void updateGroup(Group group, String operator, long parentId, int order)
      throws PersistenceException {
    log.debug("update scorecard_group with groupId:" + group.getId());
    try {
      executeUpdateSql(
          jdbcTemplate,
          UPDATE_SCORECARD_GROUP,
          newArrayList(
              parentId,
              group.getName(),
              group.getWeight(),
              order,
              operator,
              new Date(),
              group.getId()));
    } catch (Exception ex) {
      log.error(
          new LogMessage(group.getId(), operator, "Error occurs while updating the group.", ex)
              .toString());
      throw new PersistenceException("Error occurs while updating the group.", ex);
    }
  }

  /**
   * Gets the ids of all sections for group from he scorecard.
   *
   * @param group the group for which the sections ids will be collected.
   * @param scorecard the source scorecard.
   * @return the set of sections ids for group.
   */
  private static Set getSectionsIds(Group group, Scorecard scorecard) {
    Set ids = new HashSet();
    // get all groups
    Group[] oldGroups = scorecard.getAllGroups();
    for (int i = 0; i < oldGroups.length; i++) {
      // find the one of given id
      if (oldGroups[i].getId() == group.getId()) {
        // get all sections and add the ids to set
        Section[] sections = oldGroups[i].getAllSections();
        for (int j = 0; j < sections.length; j++) {
          ids.add(new Long(sections[j].getId()));
        }
        break;
      }
    }

    return ids;
  }

  /**
   * Remove the groups with the given array of ids from the persistence. Group deletion is required
   * when user udpate a scorecard and remove a group from its group list.
   *
   * @param ids The id of the group to remove.
   * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array
   *     is null or empty.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public void deleteGroups(long[] ids) throws PersistenceException {
    DBUtils.checkIdsArray(ids, "ids");

    log.debug(new LogMessage(null, null, "Delete Groups with ids:" + ids).toString());
    try {
      log.debug("delete record from scorecard_groups with ids:" + ids);
      // get all the sections to be removed
      List<Map<String, Object>> result =
          executeSqlWithParam(
              jdbcTemplate,
              SELECT_SCORECARD_SECTION_ID + DBUtils.createQuestionMarks(ids.length),
              newArrayList(ids));
      List idsToDelete = new ArrayList();
      for (Map<String, Object> row : result) {
        idsToDelete.add(getLong(row, "scorecard_section_id"));
      }
      // delete the sections
      if (idsToDelete.size() > 0) {
        sectionPersistence.deleteSections(DBUtils.listToArray(idsToDelete));
      }
      // delete the groups
      executeUpdateSql(
          jdbcTemplate,
          DELETE_SCORECARD_GROUPS + DBUtils.createQuestionMarks(ids.length),
          newArrayList(ids));
    } catch (Exception ex) {
      log.error(
          new LogMessage(null, null, "Failed to Delete Groups with ids:" + ids, ex).toString());
      throw new PersistenceException("Error occurs while deleting the questions.", ex);
    }
  }

  /**
   * Retrieves the group instance from the persistence given its id. The group instance is always
   * retrieved with its sub items.
   *
   * @return The group instance or <code>null</code> if group not found.
   * @param id The id of the group to be retrieved.
   * @throws IllegalArgumentException if the input id is less than or equal to zero.
   * @throws PersistenceException if error occurred while accessing the database.
   */
  public Group getGroup(long id) throws PersistenceException {
    if (id <= 0) {
      throw new IllegalArgumentException("The id must be positive. Id: " + id);
    }
    log.debug(new LogMessage(id, null, "retrieve group").toString());
    try {
      // create the statement and set the id
      List<Map<String, Object>> rs =
          executeSqlWithParam(jdbcTemplate, SELECT_SCORECARD_GROUP_BY_ID, newArrayList(id));
      // if the group exists - create it
      if (!rs.isEmpty()) {
        Group group = populateGroup(rs.get(0));
        group.addSections(sectionPersistence.getSections(group.getId()));
        return group;
      }
    } catch (SQLException ex) {
      log.error(new LogMessage(id, null, "Failed to retrieve group", ex).toString());
      throw new PersistenceException("Error occurs while retrieving the group.", ex);
    }
    // return null if group not found.
    return null;
  }

  /**
   * Retrieves the group instances from the persistence with the given parent id. The group instance
   * is always retrieved with its sub items.
   *
   * @param parentId the id of the paren scorecard.
   * @return the list of groups for the given parent.
   * @throws PersistenceException if database error occur.
   */
  Group[] getGroups(Long parentId) throws PersistenceException {
    try {
      // get all groups
      List<Map<String, Object>> rs =
          executeSqlWithParam(
              jdbcTemplate, SELECT_SCORECARD_GROUP_BY_PARENT_ID, newArrayList(parentId));
      List result = new ArrayList();
      for (Map<String, Object> row : rs) {
        Group group = populateGroup(row);
        // get the sections for the group
        group.addSections(sectionPersistence.getSections(group.getId()));
        result.add(group);
      }
      return (Group[]) result.toArray(new Group[result.size()]);
    } catch (SQLException ex) {
      log.error(
          new LogMessage(null, null, "Failed to retrieve group with parentId:" + parentId, ex)
              .toString());
      throw new PersistenceException("Error occurs while retrieving the group.", ex);
    }
  }

  /**
   * Creates the Group instance using the data from the ResultSet.
   *
   * @param rs the source result set.
   * @return the Group instance.
   * @throws SQLException if any error with ResultSet occurs.
   */
  private Group populateGroup(Map<String, Object> rs) throws SQLException {
    Group group = new Group();
    group.setId(getLong(rs, "scorecard_group_id"));
    group.setName(getString(rs, "name"));
    group.setWeight(getFloat(rs, "weight"));

    return group;
  }
}
