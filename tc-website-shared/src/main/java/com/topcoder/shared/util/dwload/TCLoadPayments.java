package com.topcoder.shared.util.dwload;

/**
 * TCLoadPayments.java
 *
 * TCLoadPayments loads payments information to the DW.
 *
 * <p>
 *  Version 1.1 (TopCoder Data Warehouse Loading Update 1)
 *  - Adds new column jira_ticket_id to topcoder_dw:payment and loads data for this new column
 * </p>
 *
 * @author pulky, Veve
 * @version 1.1
 */

import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.Hashtable;

public class TCLoadPayments extends TCLoad {
    private static final int PAYMENTS_LOG_TYPE = 6;
    private static Logger log = Logger.getLogger(TCLoadPayments.class);
    protected java.sql.Timestamp fStartTime = null;
    protected java.sql.Timestamp fLastLogTime = null;

    public TCLoadPayments() {
        DEBUG = false;
    }

    /**
     * This method is passed any parameters passed to this load
     */
    public boolean setParameters(Hashtable params) {
        return true;
    }

    /**
     * This method performs the load for the payments tables
     */
    public void performLoad() throws Exception {
        try {
            fStartTime = new java.sql.Timestamp(System.currentTimeMillis());

            getLastUpdateTime();

            //loadPaymentTypes();
            loadPayments();

            doClearCache();

            setLastUpdateTime();

            log.info("SUCCESS: Payments load ran successfully.");
        } catch (Exception ex) {
            setReasonFailed(ex.getMessage());
            throw ex;
        }
    }

/*    private void loadPaymentTypes() throws Exception {
        int count = 0;
        PreparedStatement psSel = null;
        PreparedStatement psIns = null;
        PreparedStatement psUpd = null;
        ResultSet rs = null;
        StringBuffer query = null;

        try {
            query = new StringBuffer(100);
            query.append("select payment_type_id, payment_type_desc, show_in_profile_ind, show_details_ind ");
            query.append("from payment_type_lu ");
            query.append("where modify_date > ? OR create_date > ? ");
            psSel = prepareStatement(query.toString(), SOURCE_DB);
            psSel.setTimestamp(1, fLastLogTime);
            psSel.setTimestamp(2, fLastLogTime);

            query = new StringBuffer(100);
            query.append("insert into payment_types (payment_type_id, payment_type_desc, ");
            query.append("show_in_profile_ind, show_details_ind) values (?, ?, ?, ?)");
            psIns = prepareStatement(query.toString(), TARGET_DB);

            query = new StringBuffer(100);
            query.append("update payment_types set payment_type_desc = ?, ");
            query.append("show_in_profile_ind = ?, show_details_ind = ? ");
            query.append("where payment_type_id = ?");
            psUpd = prepareStatement(query.toString(), TARGET_DB);

            rs = psSel.executeQuery();

            while (rs.next()) {
                psUpd.clearParameters();
                psUpd.setString(1, rs.getString("payment_type_desc"));
                psUpd.setLong(2, rs.getLong("show_in_profile_ind"));
                psUpd.setLong(3, rs.getLong("show_details_ind"));
                psUpd.setLong(4, rs.getLong("payment_type_id"));
                int retVal = psUpd.executeUpdate();

                if (retVal == 0) {
                    //need to insert
                    psIns.clearParameters();
                    psIns.setLong(1, rs.getLong("payment_type_id"));
                    psIns.setString(2, rs.getString("payment_type_desc"));
                    psIns.setLong(3, rs.getLong("show_in_profile_ind"));
                    psIns.setLong(4, rs.getLong("show_details_ind"));
                    psIns.executeUpdate();
                }

                count = count++;
                printLoadProgress(count, "payment types");
            }
            log.info("payment types records copied = " + count);
        } catch (SQLException sqle) {
            printSqlException(true, sqle);
            throw new Exception("Load of 'payment_type' table failed.\n" +
                    sqle.getMessage());
        } finally {
            DBMS.close(rs);
            DBMS.close(psSel);
            DBMS.close(psIns);
            DBMS.close(psUpd);
        }
    }*/

    private void getLastUpdateTime() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        StringBuffer query = null;

        query = new StringBuffer(100);
        query.append("select timestamp from update_log where log_id = ");
        query.append("(select max(log_id) from update_log where log_type_id = " + PAYMENTS_LOG_TYPE + ")");

        try {
            stmt = createStatement(TARGET_DB);
            rs = stmt.executeQuery(query.toString());
            if (rs.next()) {
                fLastLogTime = rs.getTimestamp(1);
                log.info("Date is " + fLastLogTime.toString());
            } else {
                // A little misleading here as we really didn't hit a SQL
                // exception but all we are doing outside this method is
                // catchin and setting the reason for failure to be the
                // message of the exception.
                throw new SQLException("Last log time not found in " +
                        "update_log table.");
            }
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw new Exception("Failed to retrieve last log time.\n" +
                    sqle.getMessage());
        } finally {
            DBMS.close(rs);
            DBMS.close(stmt);
        }
    }

    private void loadPayments() throws Exception {
        int count = 0;
        int retVal = 0;
        PreparedStatement psSel = null;
        PreparedStatement psInsPayment = null;
        PreparedStatement psInsUsrPayment = null;
        PreparedStatement psDelUserPayment = null;
        PreparedStatement psDelPayment = null;
        PreparedStatement psSelModified = null;
        ResultSet modifiedPayments = null;
        ResultSet rs = null;
        StringBuffer query = null;


        long paymentId = 0;
        try {
            boolean paymentsFound = false;

            query = new StringBuffer(100);
            query.append("select distinct pdx.payment_id from payment_detail pd, payment_detail_xref pdx, payment_type_lu ptl ");
            query.append("where pd.payment_detail_id = pdx.payment_detail_id and pd.payment_type_id = ptl.payment_type_id ");
            query.append("and (pd.date_modified > ? or pd.create_date > ? or ptl.modify_date > ? or ptl.create_date > ? )");
            psSelModified = prepareStatement(query.toString(), SOURCE_DB);
            psSelModified.setTimestamp(1, fLastLogTime);
            psSelModified.setTimestamp(2, fLastLogTime);
            psSelModified.setTimestamp(3, fLastLogTime);
            psSelModified.setTimestamp(4, fLastLogTime);

            query = new StringBuffer(100);
            query.append("delete from payment ");
            query.append("where payment_id = ? ");

            psDelPayment = prepareStatement(query.toString(), TARGET_DB);

            query = new StringBuffer(100);
            query.append("delete from user_payment ");
            query.append("where payment_id = ? ");

            psDelUserPayment = prepareStatement(query.toString(), TARGET_DB);

            modifiedPayments = psSelModified.executeQuery();

            int i = 0;
            while (modifiedPayments.next()) {
                paymentsFound = true;

                paymentId = modifiedPayments.getLong("payment_id");

                // delete modified payments
                psDelUserPayment.clearParameters();
                psDelUserPayment.setLong(1, paymentId);
                psDelUserPayment.executeUpdate();

                psDelPayment.clearParameters();
                psDelPayment.setLong(1, paymentId);
                psDelPayment.executeUpdate();

                log.debug("deleting payment_id = " + paymentId);
                i++;
                if (i % 100 == 0) {
                    log.info("Deleted " + i + " old payments...");
                }
            }
            log.info("total old payment deleted = " + i);

            loadJiraTicketsFirstTime();

            if (paymentsFound) {
                //StringBuffer charity = new StringBuffer(100);

                // insert modified payments
                query = new StringBuffer(100);
                query.append("select payment_id, user_id, net_amount, gross_amount, payment_desc, ");
                query.append("pd.payment_type_id, ptl.payment_type_desc, show_in_profile_ind, show_details_ind, ");
                query.append("ptl.payment_reference_id, date_due, algorithm_round_id, algorithm_problem_id, ");
                query.append("component_contest_id, component_project_id, studio_contest_id, ");
                query.append("digital_run_stage_id, digital_run_season_id, parent_payment_id, ");
                query.append("pd.date_paid, sl.payment_status_id, sl.payment_status_desc, charity_ind, pd.client, pd.date_modified, ");
                query.append("digital_run_track_id, installment_number, total_amount, pd.jira_issue_id, pd.create_date ");
                query.append("from payment_detail pd, payment p, payment_type_lu ptl, payment_status_lu sl ");
                query.append("where pd.payment_detail_id = p.most_recent_detail_id ");
                query.append("and pd.payment_type_id = ptl.payment_type_id ");
                ///not deleted.  have to move canceled payments (at least currently) because they get cancelled when the associated affidavit expires
                query.append("and pd.payment_status_id = sl.payment_status_id and pd.payment_status_id <> 69 ");
                query.append("and (pd.date_modified > ? or pd.create_date > ? or ptl.modify_date > ? or ptl.create_date > ?) ");
                psSel = prepareStatement(query.toString(), SOURCE_DB);
                psSel.setTimestamp(1, fLastLogTime);
                psSel.setTimestamp(2, fLastLogTime);
                psSel.setTimestamp(3, fLastLogTime);
                psSel.setTimestamp(4, fLastLogTime);

                query = new StringBuffer(100);
                query.append("insert into payment (payment_id, payment_desc, payment_type_id, ");
                query.append("payment_type_desc, reference_id, parent_payment_id, charity_ind, ");
                query.append("show_in_profile_ind, show_details_ind, payment_status_id, ");
                query.append("payment_status_desc, client, modified_calendar_id, modified_time_id, ");
                query.append("installment_number, jira_ticket_id, created_calendar_id, created_time_id) ");
                query.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
                psInsPayment = prepareStatement(query.toString(), TARGET_DB);

                query = new StringBuffer(100);
                query.append("insert into user_payment (payment_id, user_id, net_amount, ");
                query.append("gross_amount, due_calendar_id, paid_calendar_id, total_amount) ");
                query.append("values (?, ?, ?, ?, ?, ?, ?) ");
                psInsUsrPayment = prepareStatement(query.toString(), TARGET_DB);


                rs = psSel.executeQuery();

                while (rs.next()) {
                    paymentId = rs.getLong("payment_id");

                    psInsPayment.clearParameters();
                    psInsPayment.setLong(1, paymentId);
                    psInsPayment.setString(2, rs.getString("payment_desc"));
                    psInsPayment.setLong(3, rs.getLong("payment_type_id"));
                    psInsPayment.setString(4, rs.getString("payment_type_desc"));
                    long referenceId = selectReferenceId(rs.getInt("payment_reference_id"),
                            rs.getLong("algorithm_round_id"),
                            rs.getLong("algorithm_problem_id"),
                            rs.getLong("component_contest_id"),
                            rs.getLong("component_project_id"),
                            rs.getLong("studio_contest_id"),
                            rs.getLong("digital_run_stage_id"),
                            rs.getLong("digital_run_season_id"),
                            rs.getLong("digital_run_track_id"));
                    if (referenceId > 0) {
                        psInsPayment.setLong(5, referenceId);
                    } else {
                        psInsPayment.setNull(5, Types.DECIMAL);
                    }
                    psInsPayment.setLong(6, rs.getLong("parent_payment_id"));
                    psInsPayment.setInt(7, rs.getInt("charity_ind"));
                    psInsPayment.setInt(8, rs.getInt("show_in_profile_ind"));
                    psInsPayment.setInt(9, rs.getInt("show_details_ind"));
                    psInsPayment.setLong(10, rs.getLong("payment_status_id"));
                    psInsPayment.setString(11, rs.getString("payment_status_desc"));

                    if (rs.getObject("client") != null) {
                        psInsPayment.setString(12, rs.getString("client"));
                    } else {
                        psInsPayment.setNull(12, Types.VARCHAR);
                    }

                    psInsPayment.setLong(13, lookupCalendarId(rs.getTimestamp("date_modified"), TARGET_DB));
                    psInsPayment.setLong(14, lookupTimeId(rs.getTimestamp("date_modified"), TARGET_DB));
                    psInsPayment.setInt(15, rs.getInt("installment_number"));

                    if (rs.getObject("jira_issue_id") != null) {
                        psInsPayment.setString(16, rs.getString("jira_issue_id"));
                    } else {
                        psInsPayment.setNull(16, Types.VARCHAR);
                    }

                    psInsPayment.setLong(17, lookupCalendarId(rs.getTimestamp("create_date"), TARGET_DB));
                    psInsPayment.setLong(18, lookupTimeId(rs.getTimestamp("create_date"), TARGET_DB));


                    log.debug("inserting payment_id = " + paymentId);

                    retVal = psInsPayment.executeUpdate();

                    if (retVal != 1) {
                        throw new SQLException("TCLoadPayments: Load payment for payment_id " + rs.getLong("payment_id") +
                                " could not be inserted.");
                    }

                    psInsUsrPayment.clearParameters();
                    psInsUsrPayment.setLong(1, paymentId);
                    psInsUsrPayment.setLong(2, rs.getLong("user_id"));
                    psInsUsrPayment.setDouble(3, rs.getDouble("net_amount"));
                    psInsUsrPayment.setDouble(4, rs.getDouble("gross_amount"));

                    if (rs.getTimestamp("date_due") != null) {
                        psInsUsrPayment.setLong(5, lookupCalendarId(rs.getTimestamp("date_due"), TARGET_DB));
                    } else {
                        psInsUsrPayment.setNull(5, Types.DECIMAL);
                    }
                    if (rs.getTimestamp("date_paid") != null) {
                        psInsUsrPayment.setLong(6, lookupCalendarId(rs.getTimestamp("date_paid"), TARGET_DB));
                    } else {
                        psInsUsrPayment.setNull(6, Types.DECIMAL);
                    }
                    psInsUsrPayment.setDouble(7, rs.getDouble("total_amount"));
                    retVal = psInsUsrPayment.executeUpdate();

                    if (retVal != 1) {
                        throw new SQLException("TCLoadPayments: Load payment for payment_id " + rs.getLong("payment_id") +
                                " could not be inserted.");
                    }

                    count++;
                    printLoadProgress(count, "payments");
                }
            }

            log.info("total payment records copied = " + count);
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw new Exception("Load of 'payment' table failed.\n" + "payment_id = " + paymentId + "\n" +
                    sqle.getMessage());
        } finally {
            DBMS.close(modifiedPayments);
            DBMS.close(rs);
            DBMS.close(psSel);
            DBMS.close(psInsPayment);
            DBMS.close(psInsUsrPayment);
            DBMS.close(psDelPayment);
            DBMS.close(psDelUserPayment);
            //DBMS.close(psUpd);
            DBMS.close(psSelModified);
        }
    }

    /**
     * Load new columns (issue_type, payment_status) data for the existing jira_issue records in tcs_dw.
     *
     * @throws SQLException if any error occurs.
     * @since 1.1
     */
    private void loadJiraTicketsFirstTime() throws Exception {
        PreparedStatement countJiraTicketIdColumnPS = null;
        PreparedStatement selectExistingPaymentToUpdatePS = null;
        PreparedStatement selectJiraTicketDataPS = null;
        PreparedStatement updateJiraTicketIDPS = null;


        ResultSet rs = null;
        ResultSet jiraTicketIdRS = null;

        StringBuffer query = null;
        int totalCount = 0;
        long paymentId = -1;


        try {

            query = new StringBuffer(100);
            // check if there existing any records in topcoder_dw:payment
            query.append("SELECT count(*) from payment WHERE jira_ticket_id IS NOT NULL");
            countJiraTicketIdColumnPS = prepareStatement(query.toString(), TARGET_DB);

            rs = countJiraTicketIdColumnPS.executeQuery();
            rs.next();

            boolean firstRun = (rs.getInt(1) == 0);

            if(firstRun) {

                log.info("Start to do the first full load of jira_ticket_id for the existing DW payment records");

                // load jira_ticket_id for the existing payment records in topcoder_dw
                query.delete(0, query.length());
                query.append("SELECT payment_id FROM payment");
                selectExistingPaymentToUpdatePS = prepareStatement(query.toString(), TARGET_DB);
                rs = selectExistingPaymentToUpdatePS.executeQuery();

                // query to get jira_ticket_id data to update from payment_detail
                query.delete(0, query.length());
                query.append("SELECT pd.jira_issue_id FROM payment_detail pd, payment p ");
                query.append("WHERE pd.payment_detail_id = p.most_recent_detail_id AND p.payment_id = ? ");
                selectJiraTicketDataPS = prepareStatement(query.toString(), SOURCE_DB);

                // query to update the existing payment records in topcoder_dw
                query.delete(0, query.length());
                query.append("UPDATE payment SET jira_ticket_id = ? WHERE payment_id = ?");
                updateJiraTicketIDPS = prepareStatement(query.toString(), TARGET_DB);

                while (rs.next()) {
                    paymentId = rs.getLong(1);

                    selectJiraTicketDataPS.clearParameters();
                    selectJiraTicketDataPS.setLong(1, paymentId);
                    jiraTicketIdRS = selectJiraTicketDataPS.executeQuery();
                    boolean hasNext = jiraTicketIdRS.next();

                    if(!hasNext) continue;

                    // only update if the jira_ticket_id value is not null
                    if (jiraTicketIdRS.getObject(1) != null) {
                        String jiraTicketId = jiraTicketIdRS.getString(1);

                        updateJiraTicketIDPS.clearParameters();
                        updateJiraTicketIDPS.setString(1, jiraTicketId);
                        updateJiraTicketIDPS.setLong(2, paymentId);

                        int countUpdated = updateJiraTicketIDPS.executeUpdate();

                        if (countUpdated == 1) {
                            log.info(String.format("Update %s with jira_ticket_id:%s", paymentId, jiraTicketId));
                            totalCount++;
                        }
                    }

                }
            }

            log.info("total payment records updated with jira ticket ID = " + totalCount);
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw new Exception("Full Load of jira ticket data for existing records in 'payment' table failed.\n"
                    + "payment_id = " + paymentId + "\n" + sqle.getMessage());
        } finally {
            DBMS.close(jiraTicketIdRS);
            DBMS.close(rs);
            DBMS.close(countJiraTicketIdColumnPS);
            DBMS.close(selectExistingPaymentToUpdatePS);
            DBMS.close(selectJiraTicketDataPS);
            DBMS.close(updateJiraTicketIDPS);
        }
    }


    private long selectReferenceId(int paymentReferenceId, long algorithmRoundId, long algorithmProblemId,
                                   long componentContestId, long componentProjectId, long studioContestId, long digitalRunStageId,
                                   long digitalRunSeasonId, long digitalRunTrackId) {

        switch (paymentReferenceId) {
            case 1:
                return algorithmRoundId;
            case 2:
                return componentProjectId;
            case 3:
                return algorithmProblemId;
            case 4:
                return studioContestId;
            case 5:
                return componentContestId;
            case 6:
                return digitalRunStageId;
            case 7:
                return digitalRunSeasonId;
            case 9:
                return digitalRunTrackId;
        }
        return 0;
    }


    private void doClearCache() throws Exception {
        String[] keys = new String[]{"member_profile", "payment_detail", "payment_summary", "software_"};

        HashSet<String> s = new HashSet<String>();
        for (String key : keys) {
            s.add(key);
        }
        CacheClearer.removelike(s);
    }


    private void setLastUpdateTime() throws Exception {
        PreparedStatement psUpd = null;
        StringBuffer query = null;

        try {
            int retVal = 0;
            query = new StringBuffer(100);
            query.append("INSERT INTO update_log ");
            query.append("      (log_id ");        // 1
            query.append("       ,calendar_id ");  // 2
            query.append("       ,timestamp ");   // 3
            query.append("       ,log_type_id) ");   // 4
            query.append("VALUES (0, ?, ?, " + PAYMENTS_LOG_TYPE + ")");
            psUpd = prepareStatement(query.toString(), TARGET_DB);

            int calendar_id = lookupCalendarId(fStartTime, TARGET_DB);
            psUpd.setInt(1, calendar_id);
            psUpd.setTimestamp(2, fStartTime);

            retVal = psUpd.executeUpdate();
            if (retVal != 1) {
                throw new SQLException("SetLastUpdateTime " +
                        " modified " + retVal + " rows, not one.");
            }
        } catch (SQLException sqle) {
            DBMS.printSqlException(true, sqle);
            throw new Exception("Failed to set last log time.\n" +
                    sqle.getMessage());
        } finally {
            DBMS.close(psUpd);
        }
    }
}
