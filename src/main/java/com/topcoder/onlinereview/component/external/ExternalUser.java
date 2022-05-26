/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.external;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Basic implementation of the <code>{@link ExternalUser}</code> interface.
 *
 * <p>The unique id (user id) is maintained by the super class.
 *
 * <p><b>Thread Safety</b>: This class is not thread-safe.
 *
 * @author dplass, oodinary
 * @author FireIce
 * @version 1.1
 * @since 1.0
 */
public class ExternalUser {

  /** The FACTOR is used in rounding the double. */
  private static final double FACTOR = 10000.0;

  private long id;
  /**
   * The handle of this user as set in the constructor and accessed by getHandle.
   *
   * <p>Will never be null or empty after trim.
   */
  private String handle;

  /**
   * The first name of this user as set in the constructor and accessed by getFirstName.
   *
   * <p>Will never be null or empty after trim.
   */
  private String firstName;

  /**
   * The last name of this user as set in the constructor and accessed by getLastName.
   *
   * <p>Will never be null or empty after trim.
   */
  private String lastName;

  /**
   * The primary email of this user as set in the constructor and accessed by getEmail.
   *
   * <p>Will never be null or empty after trim.
   */
  private String email;

  /**
   * The set of unique alternative email addresses for this user.
   *
   * <p>Entries will never be null or empty after trim. Modified by addAlternativeEmail and
   * retrieved by getAlternativeEmails.
   */
  private Set alternativeEmails = new HashSet();

  /**
   * The RatingInfo objects for this user, as modified by addRatingInfo and accessed by many of the
   * 'getters' which ultimately call getRatingInfo or one of the private methods.
   *
   * <p>The keys are RatingType objects, and values are RatingInfo objects. None of the keys or
   * values will ever be null. Since RatingType objects implement hashCode, there will never be
   * duplicate key values in this map.
   */
  private Map ratings = new HashMap();

  /**
   * Constructs this object using the given parameters.
   *
   * @param id the unique identifier of this user.
   * @param handle the handle of this user.
   * @param firstName the first name of this user.
   * @param lastName the last name of this user.
   * @param email the primary email address of this user.
   * @throws IllegalArgumentException if id is negative or if any other argument is <code>null
   *     </code> or empty after trim.
   */
  public ExternalUser(long id, String handle, String firstName, String lastName, String email) {
    this.id = id;
    this.handle = handle;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  /**
   * Adds the requested rating information to the map of ratings for this user.
   *
   * <p>If there was already a rating info with the same rating type in the map, the old value is
   * replaced silently.
   *
   * @param info the rating info to add to this user's rating information.
   * @throws IllegalArgumentException if info is <code>null</code>.
   */
  public void addRatingInfo(RatingInfo info) {
    this.ratings.put(info.getRatingType(), info);
  }

  /**
   * Adds the requested alternative email address to the set of alternative email addresses for this
   * user.
   *
   * <p>Since a hashSet is used, there will never be duplicates in this set.
   *
   * @param alternativeEmail the alternative email to add to this user's set of emails.
   * @throws IllegalArgumentException if alternativeEmail is <code>null</code> or empty after trim.
   */
  public void addAlternativeEmail(String alternativeEmail) {
    this.alternativeEmails.add(alternativeEmail);
  }

  /**
   * Returns the handle of this user.
   *
   * @return the handle of this user. Will never be null or empty after trim.
   */
  public String getHandle() {
    return this.handle;
  }

  /**
   * Returns the first name of this user.
   *
   * @return the first name of this user. Will never be null or empty after trim.
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Returns the last name of this user.
   *
   * @return the last name of this user. Will never be null or empty after trim.
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Returns the primary email address of this user.
   *
   * @return the primary email address of this user. Will never be null or empty after trim.
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Returns the alternative email addresses of this user, as added by the addAlternativeEmails
   * method.
   *
   * @return the alternative email addresses of this user. Will never be null and will never have
   *     null or empty elements, but the length of the returned array may be zero to indicate no
   *     alternative email addresses.
   */
  public String[] getAlternativeEmails() {
    return (String[]) this.alternativeEmails.toArray(new String[0]);
  }

  /**
   * Returns the design rating of this user, as either the String representation of a non-negative
   * integer, or N/A if none.
   *
   * @return the design rating of this user, or N/A if there is no design rating. Will never be null
   *     or empty after trim.
   */
  public String getDesignRating() {
    // Delegates to getRating(RatingType).
    return getRating(RatingType.DESIGN);
  }

  /**
   * Returns the requested rating of this user using the 'ratings' map as modified by addRatingInfo.
   *
   * @param ratingType the type of rating information to return, e.g., design or development.
   * @return a String representation of the requested rating of this user, or N/A if there is no
   *     rating of the requested type. Will never be null or empty after trim.
   */
  private String getRating(RatingType ratingType) {
    // Gets RatingInfo from the map.
    RatingInfo ratingInfo = getRatingInfo(ratingType);

    // If no RatingInfo matches, returns N/A; else, returns the rating of the RatingInfo.
    if (ratingInfo == null) {
      return "N/A";
    } else {
      return Integer.toString(ratingInfo.getRating());
    }
  }

  /**
   * Returns the design reliability of this user, as either the String representation of a
   * non-negative percentage, or N/A if none.
   *
   * @return the design reliability of this user, in "##.## %" format, or N/A if there is no design
   *     rating. Will never be null or empty after trim.
   */
  public String getDesignReliability() {
    // Delegates to getReliability(RatingType).
    return getReliability(RatingType.DESIGN);
  }

  /**
   * Returns the requested reliability of this user using the 'ratings' map as modified by
   * addRatingInfo.
   *
   * @param ratingType the type of rating information to return, e.g., design or development.
   * @return a String representation of the requested reliability of this user, in "##.## %" format,
   *     or N/A if there is no rating of the requested type or the reliability was not set. Will
   *     never be null or empty after trim.
   */
  private String getReliability(RatingType ratingType) {
    // Gets RatingInfo from the map.
    RatingInfo ratingInfo = getRatingInfo(ratingType);

    // If no rating info for this type, or if the rating info doesn't have a reliability set,
    // return N/A; else, return the reliability and formats the number as a percentage.
    if (ratingInfo == null || !ratingInfo.hasReliability()) {
      return "N/A";
    } else {
      // Gets the reliability from the RatingInfo.
      double reliability = ratingInfo.getReliability();

      // Rounds the double number first.
      reliability = Math.round(reliability * FACTOR) / FACTOR;

      // Uses DecimalFormat to format the double.
      DecimalFormat formatter = new DecimalFormat("#0.00 %");
      String reliabilityPercentage = formatter.format(reliability);

      return reliabilityPercentage;
    }
  }

  /**
   * Returns the design volatility of this user, as either the String representation of a
   * non-negative integer, or N/A if none.
   *
   * @return the design volatility of this user, or N/A if there is no design rating. Will never be
   *     null or empty after trim.
   */
  public String getDesignVolatility() {
    // Delegates to getVolatility(RatingType).
    return getVolatility(RatingType.DESIGN);
  }

  /**
   * Returns the requested volatility of this user using the 'ratings' map as modified by
   * addRatingInfo.
   *
   * @param ratingType the type of rating information to return, e.g., design or development
   * @return a String representation of the requested volatility of this user, or N/A if there is no
   *     rating of the requested type. Will never be null or empty after trim.
   */
  private String getVolatility(RatingType ratingType) {
    // Gets RatingInfo from the map.
    RatingInfo ratingInfo = getRatingInfo(ratingType);

    if (ratingInfo == null) {
      return "N/A";
    } else {
      return Integer.toString(ratingInfo.getVolatility());
    }
  }

  /**
   * Returns the number of design ratings of this user.
   *
   * @return the number of design ratings, or 0 if none. Will never be negative.
   */
  public int getDesignNumRatings() {
    // Delegates to getNumRatings(RatingType).
    return getNumRatings(RatingType.DESIGN);
  }

  /**
   * Returns the requested number of ratings of this user using the 'ratings' map as modified by
   * addRatingInfo.
   *
   * @param ratingType the type of rating information to return, e.g., design or development.
   * @return the number of ratings of the requested type that this user has, or zero if none. Will
   *     never be negative.
   */
  private int getNumRatings(RatingType ratingType) {
    // Gets RatingInfo from the map.
    RatingInfo ratingInfo = getRatingInfo(ratingType);

    if (ratingInfo == null) {
      return 0;
    } else {
      return ratingInfo.getNumRatings();
    }
  }

  /**
   * Returns the development rating of this user, as either the String representation of a
   * non-negative integer, or N/A if none.
   *
   * @return the development rating of this user, or N/A if there is no development rating. Will
   *     never be null or empty after trim.
   */
  public String getDevRating() {
    // Delegates to getRating(RatingType).
    return getRating(RatingType.DEVELOPMENT);
  }

  /**
   * Returns the development reliability of this user, as either the String representation of a
   * non-negative percentage, or N/A if none.
   *
   * @return the development reliability of this user, in "##.## %" format, or N/A if there is no
   *     development rating. Will never be null or empty after trim.
   */
  public String getDevReliability() {
    // Delegates to getReliability(RatingType).
    return getReliability(RatingType.DEVELOPMENT);
  }

  /**
   * Returns the development volatility of this user, as either the String representation of a
   * non-negative integer, or N/A if none.
   *
   * @return the development volatility of this user, or N/A if there is no development rating. Will
   *     never be null or empty after trim.
   */
  public String getDevVolatility() {
    // Delegates to getVolatility(RatingType).
    return getVolatility(RatingType.DEVELOPMENT);
  }

  /**
   * Returns the number of development ratings of this user.
   *
   * @return the number of development ratings, or 0 if none. Will never be negative.
   */
  public int getDevNumRatings() {
    // Delegates to getNumRatings(RatingType).
    return getNumRatings(RatingType.DEVELOPMENT);
  }

  /**
   * Given the rating type (design or development currently are the only two options), returns the
   * rating information for this user.
   *
   * @param ratingType the type of rating to return.
   * @return the rating information of this user based on the given rating type, or null if there is
   *     no information for the given type.
   * @throws IllegalArgumentException if ratingType is <code>null</code>.
   */
  public RatingInfo getRatingInfo(RatingType ratingType) {
    // Gets RatingInfo from the map, and returns.
    return (RatingInfo) this.ratings.get(ratingType);
  }
}
