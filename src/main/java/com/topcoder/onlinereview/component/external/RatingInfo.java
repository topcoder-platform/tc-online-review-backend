/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.external;

import java.io.Serializable;

/**
 * This class encapsulates information about ratings for a particular rating type for a user.
 *
 * <p>It includes the actual rating, the reliability, number of ratings and volatility, for a
 * specific user (not maintained in this class) for a specific rating type (design or development.)
 *
 * <p><b>Thread Safety</b>: This class is immutable and thread-safe.
 *
 * @author dplass, oodinary
 * @author FireIce
 * @version 1.1
 * @since 1.0
 */
public class RatingInfo implements Serializable {

  /**
   * The type of rating this object is for - design or development.
   *
   * <p>Sets in the constructor and accessed by getRatingType. Will never be null.
   */
  private final RatingType ratingType;

  /**
   * The rating of the given 'ratingType' phase.
   *
   * <p>Sets in the constructor and accessed by getRating. Will never be negative.
   */
  private final int rating;

  /**
   * The reliability of the given 'ratingType' phase.
   *
   * <p>Sets in the constructor and accessed by getReliability. Will never represent a negative
   * double, but may be null if no reliability is set.
   */
  private final Double reliability;

  /**
   * The number of ratings in the given 'ratingType' phase.
   *
   * <p>Sets in the constructor and accessed by getNumRatings. Will never be negative.
   */
  private final int numRatings;

  /**
   * The volatility in the given 'ratingType' phase.
   *
   * <p>Sets in the constructor and accessed by getVolatility. Will never be negative.
   */
  private final int volatility;

  /**
   * Constructs this object with the given parameters.
   *
   * @param ratingType the type of rating (e.g., design, development).
   * @param rating the actual rating in the design or development category.
   * @param numRatings the number of ratings in the design or development category.
   * @param volatility the volatility in the design or development category.
   * @param reliability the reliability in the design or development category (represents 0 - 1, in
   *     another word, a percentage from 0 - 100).
   * @throws IllegalArgumentException if ratingType is <code>null</code>, or if any other argument
   *     is negative.
   */
  public RatingInfo(
      RatingType ratingType, int rating, int numRatings, int volatility, Double reliability) {
    this.rating = rating;
    this.ratingType = ratingType;
    this.numRatings = numRatings;
    this.volatility = volatility;
    this.reliability = reliability;
  }

  /**
   * Returns the rating type of this object as set in the constructor.
   *
   * @return the type of rating (design, development) that this object encapsulates. Will never be
   *     null.
   */
  public RatingType getRatingType() {
    return this.ratingType;
  }

  /**
   * Returns the rating stored in this object as set in the constructor.
   *
   * @return the rating in the given rating type (design, development). Will never be negative.
   */
  public int getRating() {
    return this.rating;
  }

  /**
   * Returns the reliability stored in this object as set in the constructor.
   *
   * <p>If the reliability was never set (i.e., the field is null), returns null.
   *
   * @return the reliability in the given rating type (design, development). Will never represent a
   *     negative double, but may be null if no reliability is set.
   */
  public Double getReliability() {
    return this.reliability;
  }

  /**
   * Returns the existence of the reliability.
   *
   * <p>If the reliability was set in the constructor, true would be returned. Or if the reliability
   * was kept null, false would be returned.
   *
   * @return <code>true</code> if the reliability was set in the constructor, <code>false</code> if
   *     not.
   */
  public boolean hasReliability() {
    return reliability != null;
  }

  /**
   * Returns the number of ratings stored in this object as set in the constructor.
   *
   * @return the number of ratings in the given rating type (design, development). Will never be
   *     negative.
   */
  public int getNumRatings() {
    return this.numRatings;
  }

  /**
   * Returns the volatility stored in this object as set in the constructor.
   *
   * @return the volatility in the given rating type (design, development). Will never be negative.
   */
  public int getVolatility() {
    return this.volatility;
  }
}
