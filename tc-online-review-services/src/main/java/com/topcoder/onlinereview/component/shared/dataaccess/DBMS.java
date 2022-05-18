package com.topcoder.onlinereview.component.shared.dataaccess;

import java.sql.SQLException;

/**
 * A class to hold constants related to the database, and some convenience methods.
 *
 * @author Jason Evans
 * @author ademich
 */
public class DBMS {
  /**
   * printSqlException() Iterate through and print out informix sql exception information. Can be
   * called on non-informix sql exceptions.
   *
   * @param verbose - whether or not it should print the stack trace
   * @param sqle - a SQL exception
   */
  public static void printSqlException(boolean verbose, SQLException sqle) {
    int i = 1;
    if (verbose) {
      System.out.println("*******************************");
      do {
        System.out.println("  Error #" + i + ":");
        System.out.println("    SQLState = " + sqle.getSQLState());
        // System.out.println("    Message = " + sqle.getMessage());
        System.out.println("    SQLCODE = " + sqle.getErrorCode());
        sqle.printStackTrace();
        sqle = sqle.getNextException();
        i++;
      } while (sqle != null);
    }
  }
}
