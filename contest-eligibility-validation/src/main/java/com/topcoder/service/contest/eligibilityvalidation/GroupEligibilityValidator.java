/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.shared.util.DBMS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.topcoder.shared.util.DBMS.COMMON_OLTP_DATASOURCE_NAME;

/**
 * GroupEligibilityValidator is used to validate whether the user is in the group of
 * contestEligibility.
 *
 * <p><b>Thread Safety:</b> This class is thread safe since it is immutable.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupEligibilityValidator implements ContestEligibilityValidator {

  /** Default constructor. */
  public GroupEligibilityValidator() {
    // does nothing
  }

  /**
   * Validate the given contestEligibility.
   *
   * @param userId the user id
   * @param contestEligibility the contestEligibility to validate
   * @return true if the user is eligible to join,otherwise false
   * @throws IllegalArgumentException if contestEligibility is null or it is not a
   *     GroupContestEligibility instance
   */
  public boolean validate(long userId, ContestEligibility contestEligibility) {

    // might be replaced by real checking web service,so let us make it simple.
    if (!(contestEligibility instanceof GroupContestEligibility)) {
      throw new IllegalArgumentException(
          "The contestEligibility should be a non-null GroupContestEligibility instance.");
    }
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
              "select * from user_group_xref where security_status_id = 1 and login_id=? and group_id=?"
                  + contestEligibility.getContestId());
      ps.setLong(1, userId);
      ps.setLong(2, ((GroupContestEligibility) contestEligibility).getGroupId());
      ResultSet rs = ps.executeQuery();
      boolean result = rs.next();
      rs.close();
      ps.close();
      return result;
    } catch (Exception e) {
      throw new RuntimeException("Some error happens while creating the contestEligibility.", e);
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (Exception e) {
      }
    }
  }
}
