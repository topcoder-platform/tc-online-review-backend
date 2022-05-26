/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.external;

import java.io.Serializable;

/**
 * This typesafe enum represents the various ratings types (phases) that the <b>User Project Data
 * Store</b> component supports, namely, <em>design</em> and <em>development</em>. The Config
 * Manager is used to retrieve the id number of each phase. The required namespace is defined in
 * this class, along with a method to retrieve the appropriate id number for the rating type.
 *
 * <p>The required configuration is in the given namespace. The keys are the two strings,
 * DESIGN_NAME and DEVELOPMENT_NAME, and the values are the id numbers of the respective phases
 * (112, and 113 respectively).
 *
 * <p>As an example, the namespace would look like this:
 *
 * <pre>
 * &lt;Property name=&quot;Design&quot;&gt;&lt;Value&gt;112&lt;/Value&gt;&lt;/Property&gt;
 * &lt;Property name=&quot;Development&quot;&gt;&lt;Value&gt;113&lt;/Value&gt;&lt;/Property&gt;
 * </pre>
 *
 * <p><b>Thread Safety</b>: This class is immutable and thread-safe.
 *
 * @author dplass, oodinary
 * @author FireIce
 * @version 1.1
 * @since 1.0
 */
public class RatingType implements Serializable {

  /**
   * The name of the architecture rating type, and the property name that stores the architecture
   * phase number.
   */
  public static final String ARCHITECTURE_NAME = "Architecture";

  /**
   * The name of the assembly rating type, and the property name that stores the assembly phase
   * number.
   */
  public static final String ASSEMBLY_NAME = "Assembly";

  /**
   * The name of the conceptualization rating type, and the property name that stores the
   * conceptualization phase number.
   */
  public static final String CONCEPTUALIZATION_NAME = "Conceptualization";

  /**
   * The name of the design rating type, and the property name that stores the design phase number.
   */
  public static final String DESIGN_NAME = "Design";

  /**
   * The name of the development rating type, and the property name that stores the development
   * phase number.
   */
  public static final String DEVELOPMENT_NAME = "Development";

  /**
   * The name of the RIA build rating type, and the property name that stores the RIA build phase
   * number.
   */
  public static final String RIA_BUILD_NAME = "RIA Build";

  /**
   * The name of the specification rating type, and the property name that stores the specification
   * phase number.
   */
  public static final String SPECIFICATION_NAME = "Specification";

  /**
   * The name of the test scenarios rating type, and the property name that stores the test
   * scenarios phase number.
   */
  public static final String TEST_SCENARIOS_NAME = "Test Scenarios";

  /**
   * The name of the test suites rating type, and the property name that stores the test suites
   * phase number.
   */
  public static final String TEST_SUITES_NAME = "Test Suites";

  /**
   * The name of the UI prototype rating type, and the property name that stores the UI prototype
   * phase number.
   */
  public static final String UI_PROTOTYPE_NAME = "UI Prototype";

  /**
   * The name of the Copilot Posting rating type, and the property name that stores the Copilot
   * Posting phase number.
   */
  public static final String COPILOT_POSTING_NAME = "Copilot Posting";

  /**
   * The name of the Content Creation rating type, and the property name that stores the Content
   * Creation phase number.
   */
  public static final String CONTENT_CREATION_NAME = "Content Creation";

  /**
   * The name of the Reporting rating type, and the property name that stores the Reporting phase
   * number.
   */
  public static final String REPORTING_NAME = "Reporting";

  /**
   * The name of the First2Finish rating type, and the property name that stores the First2Finish
   * phase number.
   */
  public static final String FIRST2FINISH_NAME = "First2Finish";

  /** The name of the Code rating type, and the property name that stores the Code phase number. */
  public static final String CODE_NAME = "Code";

  /** The rating type that represents the architecture phase. */
  public static final RatingType ARCHITECTURE = getRatingType(ARCHITECTURE_NAME);

  /** The rating type that represents the assembly phase. */
  public static final RatingType ASSEMBLY = getRatingType(ASSEMBLY_NAME);

  /** The rating type that represents the conceptualization phase. */
  public static final RatingType CONCEPTUALIZATION = getRatingType(CONCEPTUALIZATION_NAME);

  /** The rating type that represents the design phase. */
  public static final RatingType DESIGN = getRatingType(DESIGN_NAME);

  /** The rating type that represents the development phase. */
  public static final RatingType DEVELOPMENT = getRatingType(DEVELOPMENT_NAME);

  /** The rating type that represents the RIA build phase. */
  public static final RatingType RIA_BUILD = getRatingType(RIA_BUILD_NAME);

  /** The rating type that represents the specification phase. */
  public static final RatingType SPECIFICATION = getRatingType(SPECIFICATION_NAME);

  /** The rating type that represents the test scenarios phase. */
  public static final RatingType TEST_SCENARIOS = getRatingType(TEST_SCENARIOS_NAME);

  /** The rating type that represents the test suites phase. */
  public static final RatingType TEST_SUITES = getRatingType(TEST_SUITES_NAME);

  /** The rating type that represents the UI prototype phase. */
  public static final RatingType UI_PROTOTYPE = getRatingType(UI_PROTOTYPE_NAME);

  /** The rating type that represents the Copilot Posting phase. */
  public static final RatingType COPILOT_POSTING = getRatingType(COPILOT_POSTING_NAME);

  /** The rating type that represents the Content Creation phase. */
  public static final RatingType CONTENT_CREATION = getRatingType(CONTENT_CREATION_NAME);

  /** The rating type that represents the Reporting phase. */
  public static final RatingType REPORTING = getRatingType(REPORTING_NAME);

  /** The rating type that represents the First2Finish phase. */
  public static final RatingType FIRST2FINISH = getRatingType(FIRST2FINISH_NAME);

  /** The rating type that represents the Code phase. */
  public static final RatingType CODE = getRatingType(CODE_NAME);

  /** The default integer code of the architecture phase. */
  private static final int DEFAULT_ARCHITECTURE_CODE = 118;

  /** The default integer code of the assembly phase. */
  private static final int DEFAULT_ASSEMBLY_CODE = 125;

  /** The default integer code of the conceptualization phase. */
  private static final int DEFAULT_CONCEPTUALIZATION_CODE = 134;

  /** The default integer code of the design phase. */
  private static final int DEFAULT_DESIGN_CODE = 112;

  /** The default integer code of the development phase. */
  private static final int DEFAULT_DEV_CODE = 113;

  /** The default integer code of the RIA build phase. */
  private static final int DEFAULT_RIA_BUILD_CODE = 135;

  /** The default integer code of the specification phase. */
  private static final int DEFAULT_SPECIFICATION_CODE = 117;

  /** The default integer code of the test scenarios phase. */
  private static final int DEFAULT_TEST_SCENARIOS_CODE = 137;

  /** The default integer code of the test suites phase. */
  private static final int DEFAULT_TEST_SUITES_CODE = 124;

  /** The default integer code of the UI prototype phase. */
  private static final int DEFAULT_UI_PROTOTYPE_CODE = 130;

  /** The default integer code of the Copilot Posting phase. */
  private static final int DEFAULT_COPILOT_POSTING_CODE = 140;

  /** The default integer code of the Content Creation phase. */
  private static final int DEFAULT_CONTENT_CREATION_CODE = 146;

  /** The default integer code of the Reporting phase. */
  private static final int DEFAULT_REPORTING_CODE = 147;

  /** The default integer code of the First2Finish phase. */
  private static final int DEFAULT_FIRST2FINISH_CODE = 149;

  /** The default integer code of the Code phase. */
  private static final int DEFAULT_CODE_CODE = 150;

  /**
   * The name of the rating type as set in the constructor and accessed by getName. Will never be
   * null or empty after trim.
   */
  private final String name;

  /**
   * The id of this rating type/phase as set in the constructor and accessed by getId. Will never be
   * negative.
   */
  private final int id;

  /**
   * Constructs this object with the given parameters.
   *
   * @param id the id number of this rating type/phase (e.g., 112 for design, 113 for development).
   * @param name the name of this phase (e.g., Design, Development).
   */
  private RatingType(int id, String name) {
    this.name = name;
    this.id = id;
  }

  /**
   * Builds or retrieves the named rating type from either the typesafe enum registry, or builds a
   * new one with the id named in the configuration file.
   *
   * @param typeName the name of the rating type (e.g., "Design", "Development").
   * @return a RatingType which corresponds to the given typeName.
   * @throws IllegalArgumentException if typeName is <code>null</code> or empty after trim.
   */
  public static RatingType getRatingType(String typeName) {
    // Judges the type of the typeName, and creates the instance of RatingType due to the type.
    if (typeName.equals(DESIGN_NAME)) {
      return new RatingType(DEFAULT_DESIGN_CODE, typeName);
    } else if (typeName.equals(DEVELOPMENT_NAME)) {
      return new RatingType(DEFAULT_DEV_CODE, typeName);
    } else if (typeName.equals(CONCEPTUALIZATION_NAME)) {
      return new RatingType(DEFAULT_CONCEPTUALIZATION_CODE, typeName);
    } else if (typeName.equals(SPECIFICATION_NAME)) {
      return new RatingType(DEFAULT_SPECIFICATION_CODE, typeName);
    } else if (typeName.equals(ARCHITECTURE_NAME)) {
      return new RatingType(DEFAULT_ARCHITECTURE_CODE, typeName);
    } else if (typeName.equals(ASSEMBLY_NAME)) {
      return new RatingType(DEFAULT_ASSEMBLY_CODE, typeName);
    } else if (typeName.equals(TEST_SUITES_NAME)) {
      return new RatingType(DEFAULT_TEST_SUITES_CODE, typeName);
    } else if (typeName.equals(TEST_SCENARIOS_NAME)) {
      return new RatingType(DEFAULT_TEST_SCENARIOS_CODE, typeName);
    } else if (typeName.equals(UI_PROTOTYPE_NAME)) {
      return new RatingType(DEFAULT_UI_PROTOTYPE_CODE, typeName);
    } else if (typeName.equals(RIA_BUILD_NAME)) {
      return new RatingType(DEFAULT_RIA_BUILD_CODE, typeName);
    } else if (typeName.equals(COPILOT_POSTING_NAME)) {
      return new RatingType(DEFAULT_COPILOT_POSTING_CODE, typeName);
    } else if (typeName.equals(CONTENT_CREATION_NAME)) {
      return new RatingType(DEFAULT_CONTENT_CREATION_CODE, typeName);
    } else if (typeName.equals(REPORTING_NAME)) {
      return new RatingType(DEFAULT_REPORTING_CODE, typeName);
    } else if (typeName.equals(FIRST2FINISH_NAME)) {
      return new RatingType(DEFAULT_FIRST2FINISH_CODE, typeName);
    } else if (typeName.equals(CODE_NAME)) {
      return new RatingType(DEFAULT_CODE_CODE, typeName);
    } else {
      return new RatingType(0, typeName);
    }
  }

  /**
   * Returns a <code>{@link RatingType}</code> which corresponds to the given id.
   *
   * @param id the id (phase #) of the rating type to retrieve.
   * @return a RatingType which corresponds to the given id, or <code>null</code> if not found.
   * @throws IllegalArgumentException if id is not positive.
   */
  public static RatingType getRatingType(int id) {
    if (id == DEFAULT_ARCHITECTURE_CODE) {
      return new RatingType(id, ARCHITECTURE_NAME);
    } else if (id == DEFAULT_ASSEMBLY_CODE) {
      return new RatingType(id, ASSEMBLY_NAME);
    } else if (id == DEFAULT_CONCEPTUALIZATION_CODE) {
      return new RatingType(id, CONCEPTUALIZATION_NAME);
    } else if (id == DEFAULT_DESIGN_CODE) {
      return new RatingType(id, DESIGN_NAME);
    } else if (id == DEFAULT_DEV_CODE) {
      return new RatingType(id, DEVELOPMENT_NAME);
    } else if (id == DEFAULT_RIA_BUILD_CODE) {
      return new RatingType(id, RIA_BUILD_NAME);
    } else if (id == DEFAULT_SPECIFICATION_CODE) {
      return new RatingType(id, SPECIFICATION_NAME);
    } else if (id == DEFAULT_TEST_SCENARIOS_CODE) {
      return new RatingType(id, TEST_SCENARIOS_NAME);
    } else if (id == DEFAULT_TEST_SUITES_CODE) {
      return new RatingType(id, TEST_SUITES_NAME);
    } else if (id == DEFAULT_UI_PROTOTYPE_CODE) {
      return new RatingType(id, UI_PROTOTYPE_NAME);
    } else if (id == DEFAULT_COPILOT_POSTING_CODE) {
      return new RatingType(id, COPILOT_POSTING_NAME);
    } else if (id == DEFAULT_CONTENT_CREATION_CODE) {
      return new RatingType(id, CONTENT_CREATION_NAME);
    } else if (id == DEFAULT_REPORTING_CODE) {
      return new RatingType(id, REPORTING_NAME);
    } else if (id == DEFAULT_FIRST2FINISH_CODE) {
      return new RatingType(id, FIRST2FINISH_NAME);
    } else if (id == DEFAULT_CODE_CODE) {
      return new RatingType(id, CODE_NAME);
    }
    return new RatingType(0, "Unknown RatingType" + id); // second solution
  }

  /**
   * Returns a hash code for this rating type, based on the hashcode of the name.
   *
   * @return the hashCode of the name variable.
   */
  public int hashCode() {
    return name.hashCode();
  }

  /**
   * Returns true if 'that' is also a RatingType, is not null, and has the same hashcode as this
   * object.
   *
   * @return <code>true</code> if 'that' is also a RatingType, is not null, and has the same
   *     hashcode as this object, else <code>false</code>.
   * @param that the object to compare with.
   */
  public boolean equals(Object that) {
    return that instanceof RatingType && that.hashCode() == this.hashCode();
  }

  /**
   * Returns the name of this rating type as set in the constructor.
   *
   * <p>This method is required by the <code>{@link Enum}</code> superclass.
   *
   * @return the name of this rating type. Will never be <code>null</code> or empty after trim.
   */
  public String toString() {
    return getName();
  }

  /**
   * Returns the name of this rating type as set in the constructor.
   *
   * @return the name of this rating type. Will never be <code>null</code> or empty after trim.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the id of this rating type as set in the constructor.
   *
   * @return the id of this rating type. Will never be negative or zero.
   */
  public int getId() {
    return this.id;
  }
}
