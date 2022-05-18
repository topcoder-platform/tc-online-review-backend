/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.topcoder.onlinereview.util.CommonUtils.getBoolean;
import static com.topcoder.onlinereview.util.CommonUtils.getLong;

/**
 * <p>
 * It is a stateless session bean that uses JPA to manage the persistence of eligibility entities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is thread safe because all operations are transactions in the EJB
 * environment.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
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
 * </p>
 *
 * <p>
 * Version 1.0.1 ((TopCoder Online Review Switch To Local Calls Assembly)) Change notes:
 *   <ol>
 *     <li>Updated the class to use JBoss Logging for logging the events to make the component usable in local
 *     environment for Online Review application.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.1
 */
@Slf4j
@Component
public class ContestEligibilityManager {

    @Autowired
    @Qualifier("tcsJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    /**
     * Add a contest eligibility.
     *
     * @param contestEligibility
     *            contest eligibility
     * @return the added contest eligibility
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when persisting the given contest eligibility
     */
    public ContestEligibility create(ContestEligibility contestEligibility)
        throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#create", new String[] {"contestEligibility"},
            new Object[] {contestEligibility});
        checkNull(contestEligibility, "contestEligibility");
        try {
            jdbcTemplate.update("insert into contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(CONTEST_ELIGIBILITY_SEQ.NEXTVAL, ?, ?)", contestEligibility.getContestId(), contestEligibility.getStudio());
            Long id = jdbcTemplate.queryForObject("select max(contest_eligibility_id) from contest_eligibility where contest_id=" + contestEligibility.getContestId(), Long.class);
            contestEligibility.setId(id);
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while persisting the entity.", e));
        }
        logExit("ContestEligibilityManagerBean#create");
        return contestEligibility;
    }

    /**
     * Remove a contest eligibility.
     *
     * @param contestEligibility
     *            the contest eligibility to remove
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when removing the given contest eligibility
     */
    public void remove(ContestEligibility contestEligibility) throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#remove", new String[] {"contestEligibility"},
            new Object[] {contestEligibility});
        checkNull(contestEligibility, "contestEligibility");
        try {
            jdbcTemplate.update("delete from contest_eligibility where contest_eligibility_id="
                    + contestEligibility.getId());
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while removing the entity.", e));
        }
        logExit("ContestEligibilityManagerBean#remove");
    }

    /**
     * <p>
     * Save a list of eligibilities, if can be create/update/delete.
     * </p>
     * <ol>
     * You will get a list of eligibilities as input parameter, for each eligibility in the list, you do one
     * of these,
     * <li>create/insert, if id is 0 then insert.</li>
     * <li>update, if id is not 0, do an update.</li>
     * <li>delete, if 'delete' flag is true, you remove the eligibility.</li>
     * </ol>
     *
     * @param list
     *            a list of eligibilities,can be empty,then do nothing
     * @return the added/updated eligibilities
     * @throws IllegalArgumentException
     *             if list is null or it contains null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when saving eligibilities
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
        try {
            if (!toDelete.isEmpty()) {
                StringBuilder sb =
                        new StringBuilder("delete from contest_eligibility where contest_eligibility_id in (?");
                for (int i = 1; i < toDelete.size(); i++) {
                    sb.append(",?");
                }
                sb.append(")");
                jdbcTemplate.update(sb.toString(), toDelete.toArray());
                list.removeAll(toDelete);
            }
            if (!toUpdate.isEmpty()) {
                for (ContestEligibility ce : toUpdate) {
                    jdbcTemplate.update("update contest_eligibility set contest_id=?, is_studio=? where contest_eligibility_id=?", ce.getContestId(), ce.getStudio(), ce.getId());
                }
            }
            if (!toAdd.isEmpty()) {
                for (ContestEligibility ce : toUpdate) {
                    jdbcTemplate.update("insert into contest_eligibility(contest_eligibility_id, contest_id, is_studio) VALUES(CONTEST_ELIGIBILITY_SEQ.NEXTVAL, ?, ?)", ce.getContestId(), ce.getStudio());
                    Long id = jdbcTemplate.queryForObject("select max(contest_eligibility_id) from contest_eligibility where contest_id=" + ce.getContestId(), Long.class);
                    ce.setId(id);
                }
            }
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while saving the list of contestEligibility.", e));
        }
        logExit("ContestEligibilityManagerBean#save");
        return list;
    }

    /**
     * Return a list of eligibilities for a contest.
     *
     * @param contestId
     *            the contest id
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of eligibilities
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    @SuppressWarnings("unchecked")
    public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio) throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#getContestEligibility", new String[] {"contestId",
        "isStudio"}, new Object[] {contestId, isStudio});
        checkPositive(contestId, "contestId");
        List<Map<String, Object>> qr = jdbcTemplate.queryForList(
                "select c.contest_eligibility_id, c.contest_id, c.is_studio, g.group_id from contest_eligibility AS c JOIN group_contest_eligibility AS g On c.contest_eligibility_id = g.contest_eligibility_id "
                        + "where c.is_studio = "
                        + (isStudio ? "1" : "0")
                        + " and c.contest_id = "
                        + contestId);
        List<ContestEligibility> results = new ArrayList<>();
        for (Map<String, Object> r : qr) {
            GroupContestEligibility ce = new GroupContestEligibility();
            ce.setId(getLong(r, "contest_eligibility_id"));
            ce.setContestId(getLong(r, "contest_id"));
            ce.setStudio(getBoolean(r, "is_studio"));
            ce.setDeleted(false);
            ce.setGroupId(getLong(r, "group_id"));
            results.add(ce);
        }
        logExit("ContestEligibilityManagerBean#getContestEligibility");
        return results;
    
        
    }


    /**
     * Return a list of contest ids that has eligibility.
     *
     * @param contestIds
     *            the contest id list
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of contest ids
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    @SuppressWarnings("unchecked")
    public Set<Long> haveEligibility(Long[] contestIds, boolean isStudio) throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#haveEligibility", new String[] {"contestIds[]",
            "isStudio"}, new Object[]{contestIds, isStudio});

        Set<Long> result = new HashSet<Long>();

        if (contestIds == null || contestIds.length == 0)
        {
            return result;
        }

        int studio = isStudio ? 1 : 0;
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
            Long[] idArray = new Long[count];
            for (int i = 0; i < count; i++) {
                idArray[i] =  contestIds[startIndex + i];
            }
            List<Map<String, Object>> ps =
                    jdbcTemplate.queryForList(
                            "select unique contest_id from contest_eligibility where is_studio = "
                                    + studio
                                    + " and  contest_id in "
                                    + ids, idArray);
            for (Map<String, Object> r : ps) {
                result.add(getLong(r, "contest_id"));
            }
            startIndex += count;
            if (startIndex >= contestIds.length) {
                break;
            }
        }
        logExit("ContestEligibilityManagerBean#haveEligibility");
        return result;
    }

    /**
     * <p>
     * Logs the error.
     * </p>
     *
     * @param <T>
     *            the generic class type of error
     * @param error
     *            the error needs to be logged.
     * @return the error
     */
    private <T extends Exception> T logError(T error) {
        log.error( "Error recognized: " +  error.getMessage(), error);
        return error;
    }

    /**
     * <p>
     * Log the entrance of a method and all the input arguments.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param paramNames
     *            the name of the parameters
     * @param params
     *            the parameters
     */
    @SuppressWarnings("unchecked")
    private void logEntrance(String methodName, String[] paramNames, Object[] params) {
        log.debug("Enter into Method: " + methodName + " At " + new Date());
        if (paramNames != null) {
            StringBuilder logInfo = new StringBuilder("Parameters:");
            for (int i = 0; i < paramNames.length; i++) {
                if (params[i] instanceof ContestEligibility) {
                    ContestEligibility contestEligibility = (ContestEligibility) params[i];
                    logInfo.append(" [ " + paramNames[i] + " = " + contestEligibility.getClass().getName()
                        + " with id=" + contestEligibility.getId() + " ]");
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
                        paramLog.append(contestEligibility.getClass().getName() + " with id="
                            + contestEligibility.getId() + "  ");
                    }
                    logInfo.append(" [ " + paramNames[i] + " = {" + paramLog.toString() + "} ]");
                    continue;
                }
                logInfo.append(" [ " + paramNames[i] + " = " + params[i] + " ]");
            }
            log.debug(logInfo.toString());
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the name of the method
     */
    private void logExit(String methodName) {
        log.debug("Exit out Method: " + methodName + " At " + new Date());
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    private void checkNull(Object arg, String name) {
        if (arg == null) {
            IllegalArgumentException e =
                new IllegalArgumentException("The argument " + name + " cannot be null.");
            throw logError(e);
        }
    }

    /**
     * <p>
     * Checks whether the given argument is positive.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given argument is not positive
     */
    private void checkPositive(long arg, String name) {
        if (arg <= 0) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should be positive.");
            throw logError(e);
        }
    }
}