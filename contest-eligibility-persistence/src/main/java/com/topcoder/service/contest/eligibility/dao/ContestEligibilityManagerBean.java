/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.dao;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.shared.util.DBMS;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.topcoder.shared.util.DBMS.COMMON_OLTP_DATASOURCE_NAME;
// import org.jboss.logging.Logger;

/**
 * It is a stateless session bean that uses JPA to manage the persistence of eligibility entities.
 *
 * <p><b>Thread Safety:</b> This class is thread safe because all operations are transactions in the
 * EJB environment.
 *
 * <p><strong>Sample Usage:</strong>
 *
 * <pre>
 * //Acquire a ContestEligibilityManager
 * Context context = new InitialContext();
 * ContestEligibilityManager contestEligibilityManager =
 *     (ContestEligibilityManager) context
 *         .lookup(&quot;contest_eligibility_persistence/ContestEligibilityManagerBean/remote&quot;);
 *
 * // create a GroupContestEligibility instance named groupContestEligibility
 * ContestEligibility groupContestEligibility = new GroupContestEligibility();
 * groupContestEligibility.setContestId(16);
 * groupContestEligibility.setStudio(true);
 *
 * // insert groupContestEligibility into DB
 * groupContestEligibility = contestEligibilityManager.create(groupContestEligibility);
 *
 * // get a list of eligibilities for a contest
 * List&lt;ContestEligibility&gt; list = contestEligibilityManager.getContestEligibility(16, true);
 * System.out.println(list.size());
 *
 * // Save a list of eligibilities,you can add/update/delete entities.Here we just update it. You also
 * // can refer to ContestEligibilityManagerBeanTests for more tests to add/update/delete.
 * contestEligibilityManager.save(list);
 *
 * // Remove a contest eligibility
 * contestEligibilityManager.remove(groupContestEligibility);
 * </pre>
 *
 * <p>Version 1.0.1 ((TopCoder Online Review Switch To Local Calls Assembly)) Change notes:
 *
 * <ol>
 *   <li>Updated the class to use JBoss Logging for logging the events to make the component usable
 *       in local environment for Online Review application.
 * </ol>
 *
 * @author TCSDEVELOPER
 * @version 1.0.1
 */
public class ContestEligibilityManagerBean implements ContestEligibilityManager {

  /** The logger is used to log the methods. */
  private Logger logger = Logger.getLogger(this.logName);

  /**
   * Represents the log name.Default value is 'contest_eligibility_logger'.You also could change the
   * default value via deploy descriptor.
   */
  private String logName = "contest_eligibility_logger";

  /** Default constructor. */
  public ContestEligibilityManagerBean() {
    // Does nothing
  }

  /**
   * Add a contest eligibility.
   *
   * @param contestEligibility contest eligibility
   * @return the added contest eligibility
   * @throws IllegalArgumentException if contestEligibility is null
   * @throws ContestEligibilityPersistenceException if any errors occurred when persisting the given
   *     contest eligibility
   */
  public ContestEligibility create(ContestEligibility contestEligibility)
      throws ContestEligibilityPersistenceException {
    logEntrance(
        "ContestEligibilityManagerBean#create",
        new String[] {"contestEligibility"},
        new Object[] {contestEligibility});
    checkNull(contestEligibility, "contestEligibility");
    Connection conn = null;
    try {
      conn = DBMS.getConnection(COMMON_OLTP_DATASOURCE_NAME);
      PreparedStatement ps =
          conn.prepareStatement(
              "insert into contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(CONTEST_ELIGIBILITY_SEQ.NEXTVAL, ?, ?)");
      ps.setLong(1, contestEligibility.getContestId());
      ps.setBoolean(2, contestEligibility.isStudio());
      ps.executeUpdate();
      ps.close();
      ps =
          conn.prepareStatement(
              "select max(contest_eligibility_id) from contest_eligibility where contest_id="
                  + contestEligibility.getContestId());
      ResultSet rs = ps.executeQuery();
      contestEligibility.setId(rs.getLong(1));
      rs.close();
      ps.close();
      logExit("ContestEligibilityManagerBean#create");
      return contestEligibility;
    } catch (Exception e) {
      throw logError(
          new ContestEligibilityPersistenceException(
              "Some error happens while creating the contestEligibility.", e));
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
      }
    }
  }

  /**
   * Remove a contest eligibility.
   *
   * @param contestEligibility the contest eligibility to remove
   * @throws IllegalArgumentException if contestEligibility is null
   * @throws ContestEligibilityPersistenceException if any errors occurred when removing the given
   *     contest eligibility
   */
  public void remove(ContestEligibility contestEligibility)
      throws ContestEligibilityPersistenceException {
    logEntrance(
        "ContestEligibilityManagerBean#remove",
        new String[] {"contestEligibility"},
        new Object[] {contestEligibility});
    checkNull(contestEligibility, "contestEligibility");
    Connection conn = null;
    try {
      conn = DBMS.getConnection(COMMON_OLTP_DATASOURCE_NAME);
      PreparedStatement ps =
          conn.prepareStatement(
              "delete from contest_eligibility where contest_eligibility_id="
                  + contestEligibility.getId());
      ps.executeUpdate();
      ps.close();
      logExit("ContestEligibilityManagerBean#remove");
    } catch (Exception e) {
      throw logError(
          new ContestEligibilityPersistenceException(
              "Some error happens while removing the entity.", e));
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
      }
    }
  }

  /**
   * Save a list of eligibilities, if can be create/update/delete.
   *
   * <ol>
   *   You will get a list of eligibilities as input parameter, for each eligibility in the list,
   *   you do one of these,
   *   <li>create/insert, if id is 0 then insert.
   *   <li>update, if id is not 0, do an update.
   *   <li>delete, if 'delete' flag is true, you remove the eligibility.
   * </ol>
   *
   * @param list a list of eligibilities,can be empty,then do nothing
   * @return the added/updated eligibilities
   * @throws IllegalArgumentException if list is null or it contains null
   * @throws ContestEligibilityPersistenceException if any errors occurred when saving eligibilities
   */
  public List<ContestEligibility> save(List<ContestEligibility> list)
      throws ContestEligibilityPersistenceException {
    logEntrance("ContestEligibilityManagerBean#save", new String[] {"list"}, new Object[] {list});
    checkNull(list, "list");
    for (ContestEligibility contestEligibility : list) {
      checkNull(contestEligibility, "contest eligibility in list");
    }
    List<ContestEligibility> toDelete =
        list.stream().filter(ContestEligibility::isDeleted).collect(Collectors.toList());
    List<ContestEligibility> toUpdate =
        list.stream().filter(c -> !c.isDeleted() && c.getId() > 0).collect(Collectors.toList());
    List<ContestEligibility> toAdd =
        list.stream().filter(c -> c.getId() == 0).collect(Collectors.toList());
    Connection conn = null;
    try {
      conn = DBMS.getConnection(COMMON_OLTP_DATASOURCE_NAME);
      if (!toDelete.isEmpty()) {
        StringBuilder sb =
            new StringBuilder("delete from contest_eligibility where contest_eligibility_id in (?");
        for (int i = 1; i < toDelete.size(); i++) {
          sb.append(",?");
        }
        sb.append(")");
        PreparedStatement ps = conn.prepareStatement(sb.toString());
        for (int i = 0; i < toDelete.size(); i++) {
          ps.setLong(i + 1, toDelete.get(i).getId());
        }
        ps.executeUpdate();
        ps.close();
        list.removeAll(toDelete);
      }
      if (!toUpdate.isEmpty()) {
        PreparedStatement ps =
            conn.prepareStatement(
                "update contest_eligibility set contest_id=?, is_studio=? where contest_eligibility_id=?");
        for (ContestEligibility ce : toUpdate) {
          ps.setLong(1, ce.getContestId());
          ps.setBoolean(2, ce.isStudio());
          ps.setLong(3, ce.getId());
          ps.executeUpdate();
        }
        ps.close();
      }
      if (!toAdd.isEmpty()) {
        PreparedStatement ps =
            conn.prepareStatement(
                "insert into contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(CONTEST_ELIGIBILITY_SEQ.NEXTVAL, ?, ?)");
        PreparedStatement idSql =
            conn.prepareStatement(
                "select max(contest_eligibility_id) from contest_eligibility where contest_id=?");
        for (ContestEligibility ce : toUpdate) {
          ps.setLong(1, ce.getContestId());
          ps.setBoolean(2, ce.isStudio());
          ps.executeUpdate();
          idSql.setLong(1, ce.getContestId());
          ResultSet rs = idSql.executeQuery();
          ce.setId(rs.getLong(1));
          rs.close();
        }
        ps.close();
        idSql.close();
      }
      logExit("ContestEligibilityManagerBean#save");
      return list;
    } catch (SQLException e) {
      throw logError(
          new ContestEligibilityPersistenceException(
              "Some error happens while saving the list of contestEligibility.", e));
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
      }
    }
  }

  /**
   * Return a list of eligibilities for a contest.
   *
   * @param contestId the contest id
   * @param isStudio the flag used to indicate whether it is studio
   * @return a list of eligibilities
   * @throws IllegalArgumentException if contestId is not positive
   */
  @SuppressWarnings("unchecked")
  public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio)
      throws ContestEligibilityPersistenceException {

    logEntrance(
        "ContestEligibilityManagerBean#getContestEligibility",
        new String[] {"contestId", "isStudio"},
        new Object[] {contestId, isStudio});
    checkPositive(contestId, "contestId");
    List<ContestEligibility> results = new ArrayList<>();
    Connection conn = null;
    try {
      conn = DBMS.getConnection(COMMON_OLTP_DATASOURCE_NAME);
      PreparedStatement ps =
          conn.prepareStatement(
              "select contest_eligibility_id, contest_id, is_studio, deleted from contest_eligibility where is_studio = "
                  + (isStudio ? "1" : "0")
                  + " and  contest_id = "
                  + contestId);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        ContestEligibility ce = new ContestEligibility();
        ce.setId(rs.getLong(1));
        ce.setContestId(rs.getLong(2));
        ce.setStudio(rs.getBoolean(3));
        ce.setDeleted(false);
        results.add(ce);
      }
      rs.close();
      ps.close();
      logExit("ContestEligibilityManagerBean#getContestEligibility");
      return results;
    } catch (SQLException e) {
      throw logError(
          new ContestEligibilityPersistenceException(
              "Some error happens while saving the list of contestEligibility.", e));
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
      }
    }
  }

  /**
   * Return a list of contest ids that has eligibility.
   *
   * @param contestIds the contest id list
   * @param isStudio the flag used to indicate whether it is studio
   * @return a list of contest ids
   * @throws IllegalArgumentException if contestId is not positive
   */
  @SuppressWarnings("unchecked")
  public Set<Long> haveEligibility(Long[] contestIds, boolean isStudio)
      throws ContestEligibilityPersistenceException {
    logEntrance(
        "ContestEligibilityManagerBean#haveEligibility",
        new String[] {"contestIds[]", "isStudio"},
        new Object[] {contestIds, isStudio});

    Set<Long> result = new HashSet<>();

    if (contestIds == null || contestIds.length == 0) {
      return result;
    }


    int studio = isStudio ? 1 : 0;

    Connection conn = null;
    try {
      conn = DBMS.getConnection(COMMON_OLTP_DATASOURCE_NAME);
      int startIndex = 0;
      while (true) {
        int count = Math.min(100, contestIds.length - startIndex);
        String ids = "(?";
        if (count > 1) {
          for (int i = 1; i < count; i++) {
            ids += ", ?";
          }
        }
        ids += ")";
        PreparedStatement ps =
                conn.prepareStatement(
                        "select unique contest_id from contest_eligibility where is_studio = "
                                + studio
                                + " and  contest_id in "
                                + ids);
        for (int i = 0; i < contestIds.length; i++) {
          ps.setLong(i + 1, contestIds[i]);
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
          result.add(rs.getLong(1));
        }
        rs.close();
        ps.close();
        startIndex += count;
        if (startIndex >= contestIds.length) {
          break;
        }
      }
      logExit("ContestEligibilityManagerBean#haveEligibility");
      return result;
    } catch (SQLException e) {
      throw logError(
          new ContestEligibilityPersistenceException(
              "Some error happens while saving the list of contestEligibility.", e));
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
      }
    }
  }

  /**
   * Logs the error.
   *
   * @param <T> the generic class type of error
   * @param error the error needs to be logged.
   * @return the error
   */
  private <T extends Exception> T logError(T error) {
    logger.error("Error recognized: " + error.getMessage(), error);
    return error;
  }

  /**
   * Log the entrance of a method and all the input arguments.
   *
   * @param methodName the name of the method
   * @param paramNames the name of the parameters
   * @param params the parameters
   */
  @SuppressWarnings("unchecked")
  private void logEntrance(String methodName, String[] paramNames, Object[] params) {
    logger.debug("Enter into Method: " + methodName + " At " + new Date());
    if (paramNames != null) {
      StringBuilder logInfo = new StringBuilder("Parameters:");
      for (int i = 0; i < paramNames.length; i++) {
        if (params[i] instanceof ContestEligibility) {
          ContestEligibility contestEligibility = (ContestEligibility) params[i];
          logInfo.append(
              " [ "
                  + paramNames[i]
                  + " = "
                  + contestEligibility.getClass().getName()
                  + " with id="
                  + contestEligibility.getId()
                  + " ]");
          continue;
        }
        if (params[i] instanceof List && ((List<ContestEligibility>) params[i]).size() != 0) {
          List<ContestEligibility> list = (List<ContestEligibility>) params[i];
          StringBuilder paramLog = new StringBuilder();
          for (ContestEligibility contestEligibility : list) {
            if (contestEligibility == null) {
              paramLog.append("  null");
              continue;
            }
            paramLog.append(
                contestEligibility.getClass().getName()
                    + " with id="
                    + contestEligibility.getId()
                    + "  ");
          }
          logInfo.append(" [ " + paramNames[i] + " = {" + paramLog.toString() + "} ]");
          continue;
        }
        logInfo.append(" [ " + paramNames[i] + " = " + params[i] + " ]");
      }
      logger.debug(logInfo);
    }
  }

  /**
   * Log the exit of a method.
   *
   * @param methodName the name of the method
   */
  private void logExit(String methodName) {
    logger.debug("Exit out Method: " + methodName + " At " + new Date());
  }

  /**
   * Checks whether the given Object is null.
   *
   * @param arg the argument to check
   * @param name the name of the argument to check
   * @throws IllegalArgumentException if the given Object is null
   */
  private void checkNull(Object arg, String name) {
    if (arg == null) {
      IllegalArgumentException e =
          new IllegalArgumentException("The argument " + name + " cannot be null.");
      throw logError(e);
    }
  }

  /**
   * Checks whether the given argument is positive.
   *
   * @param arg the argument to check
   * @param name the name of the argument to check
   * @throws IllegalArgumentException if the given argument is not positive
   */
  private void checkPositive(long arg, String name) {
    if (arg <= 0) {
      IllegalArgumentException e = new IllegalArgumentException(name + " should be positive.");
      throw logError(e);
    }
  }
}
