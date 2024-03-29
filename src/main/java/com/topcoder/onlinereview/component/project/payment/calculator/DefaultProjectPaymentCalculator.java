/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.payment.calculator;

import com.topcoder.onlinereview.component.grpcclient.payment.PaymentServiceRpc;
import com.topcoder.onlinereview.component.project.payment.Helper;
import com.topcoder.onlinereview.grpc.payment.proto.BigDecimalProto;
import com.topcoder.onlinereview.grpc.payment.proto.DefaultPaymentProto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class is a concrete implementation of the <code>ProjectPaymentCalculator</code> interface. It provides a
 * concrete implementation of the {@link #getDefaultPayments(long, List)} method defined in the interface by
 * getting parameters needed to compute the payments values from the database to which it connects using the
 * </p>
 * <p>
 * This class uses the following linear formula to compute the payment for each resource role:
 *
 * <pre>
 * Payment = Fixed_Amount + (Base_Coefficient + Incremental_Coefficient * Number_Of_Submissions) * 1st_place_prize
 * </pre>
 *
 * The parameters values needed to compute the payment are retrieved from the database. <code>Fixed_Amount</code>,
 * <code>Base_Coefficient</code> and <code>Incremental_Coefficient</code> are constant values which depend on the
 * project category and resource role. These values will be read from the <code>default_project_payment</code>
 * table. If the table doesn't contain values for some project category and resource role, the payment is assumed
 * to be unknown and should not be included in the resulting map. The <code>project</code> table is used to get the
 * project category ID by the project ID.
 * </p>
 * <p>
 * <code>Number_Of_Submissions</code> has different meaning depending on the resource role:
 * <ol>
 * <li>For Primary Screener role: the total number of active contests submissions. If the number is zero, assume
 * one submission.</li>
 * <li>For Reviewer, Accuracy Reviewer, Failure Reviewer and Stress Reviewer roles: the total number of active
 * contests submissions that passed screening. If the number is zero, assume one submission.</li>
 * <li>For Checkpoint Screener role: the total number of active checkpoint submissions. If the number is zero, assume
 * one submission.</li>
 * <li>For Checkpoint Reviewer role: the total number of active checkpoint submissions that passed checkpoint
 * screening. If the number is zero, assume one submission.</li>
 * <li>For other resource roles this value doesn't have any meaning so should be assumed 0.</li>
 * </ol>
 * <code>1st_place_prize</code> is the prize for the first place winner of the contest.
 * </p>
 * <p>
 * This class additionally defines the {@link #getDefaultPayment(long, long, BigDecimal, int)} which computes the
 * payment for the specified resource role ID, prize and number of submissions.
 * </p>
 * <p>
 * Please note that this implementation supports only the following resource roles :
 * <ul>
 * <li>Primary Screener (resource role ID 2).</li>
 * <li>Reviewer, Accuracy Reviewer, Failure Reviewer, Stress Reviewer (resource role IDs are 4,5,6,7).</li>
 * <li>Aggregator (resource role 8).</li>
 * <li>Final Reviewer (resource role 9).</li>
 * <li>Specification Reviewer (resource role ID 18).</li>
 * <li>Checkpoint Screener (resource role ID 19).</li>
 * <li>Checkpoint Reviewer (resource role ID 20).</li>
 * <li>Copilot (resource role ID 14).</li>
 * </ul>
 * </p>
 * <p>
 * It uses the Log instance defined in the base class to perform logging.
 * </p>
 * <p>
 * <b>Sample configuration:</b><br/>
 * File <code>SampleConfig.xml</code>
 *
 * <pre>
 * &lt;?xml version="1.0"?>
 * &lt;CMConfig>
 *     &lt;!-- Default project payment calculator -->
 *     &lt;Config
 *         name="com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator">
 *         &lt;Property name="logger_name">
 *             &lt;Value>my_logger&lt;/Value>
 *         &lt;/Property>
 *         &lt;Property name="connection_name">
 *             &lt;Value>my_connection&lt;/Value>
 *         &lt;/Property>
 *         &lt;!-- configuration for DBConnectionFactoryImpl -->
 *         &lt;Property name="db_connection_factory_config">
 *             &lt;Property name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl">
 *                 &lt;Property name="connections">
 *                     &lt;Property name="default">
 *                         &lt;Value>my_connection&lt;/Value>
 *                     &lt;/Property>
 *
 *                     &lt;Property name="my_connection">
 *                         &lt;Property name="producer">
 *                             &lt;Value>com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value>
 *                         &lt;/Property>
 *                         &lt;Property name="parameters">
 *                             &lt;Property name="jdbc_driver">
 *                                 &lt;Value>com.informix.jdbc.IfxDriver&lt;/Value>
 *                             &lt;/Property>
 *                             &lt;Property name="jdbc_url">
 *                  &lt;Value>jdbc:informix-sqli://localhost:9088/tcs_catalog:informixserver=ol_informix1170&lt;/Value>
 *                             &lt;/Property>
 *                             &lt;Property name="user">
 *                                 &lt;Value>informix&lt;/Value>
 *                             &lt;/Property>
 *                             &lt;Property name="password">
 *                                 &lt;Value>dbadmin&lt;/Value>
 *                             &lt;/Property>
 *                         &lt;/Property>
 *                     &lt;/Property>
 *                 &lt;/Property>
 *             &lt;/Property>
 *         &lt;/Property>
 *     &lt;/Config>
 * &lt;/CMConfig>
 * </pre>
 *
 * The configuration file above is loaded into <code>ConfigurationObject</code> instance via Configuration
 * Persistence component by providing a configuration properties file as following:<br/>
 * File <code>config.properties</code>
 *
 * <pre>
 * com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator = SampleConfig.xml
 * </pre>
 *
 * It contains the mapping between namespace and the XML configuration file.
 * </p>
 * <p>
 * <b>Sample usage:</b><br/>
 * Let's say the current records in the database are shown by these following tables:
 * <p>
 * Table <code>default_project_payment</code>
 *
 * <pre>
 * ------------------------------------------------------------------------------------------------------
 * | project_category_id | resource_role_id | fixed_amount | base_coefficient | incremental_coefficient |
 * ------------------------------------------------------------------------------------------------------
 * |          1          |         2        |       0      |       0.00       |           0.01          |
 * |          1          |         4        |       0      |       0.12       |           0.05          |
 * |          1          |         8        |      10      |       0.00       |           0.00          |
 * |          2          |         9        |       0      |       0.05       |           0.00          |
 * ------------------------------------------------------------------------------------------------------
 * </pre>
 *
 * </p>
 * <p>
 * Table <code>project</code>
 *
 * <pre>
 * ------------------------------------
 * | project_id | project_category_id |
 * ------------------------------------
 * |    230     |          1          |
 * |    231     |          2          |
 * |    232     |          3          |
 * |    233     |          4          |
 * ------------------------------------
 * </pre>
 *
 * </p>
 * <p>
 * Table <code>prize</code>
 *
 * <pre>
 * -----------------------------------------------------
 * | project_id | prize_type_id | place | prize_amount |
 * -----------------------------------------------------
 * |     230    |      15       |   1   |      500     |
 * |     231    |      15       |   1   |      400     |
 * |     232    |      15       |   1   |      650     |
 * |     233    |      15       |   1   |     1000     |
 * -----------------------------------------------------
 * </pre>
 *
 * </p>
 * <p>
 * Table <code>submission</code>
 *
 * <pre>
 * ---------------------------------------------------------
 * | submission_type_id | upload_id | submission_status_id |
 * ---------------------------------------------------------
 * |          1         |    500    |          1           |
 * |          1         |    623    |          2           |
 * |          1         |     46    |          7           |
 * ---------------------------------------------------------
 * </pre>
 *
 * </p>
 * <p>
 * Table <code>upload</code>
 *
 * <pre>
 * -------------------------------------------
 * | project_id | upload_type_id | upload_id |
 * -------------------------------------------
 * |    230     |       1        |    500    |
 * |    230     |       1        |    623    |
 * |    233     |       1        |     46    |
 * -------------------------------------------
 * </pre>
 *
 * </p>
 * <p>
 *
 * <pre>
 * // create an instance of DefaultProjectPaymentCalculator using the default configuration file
 * // found in com/topcoder/management/payment/calculator/impl/DefaultProjectPaymentCalculator.properties
 * ProjectPaymentCalculator calculator = new DefaultProjectPaymentCalculator();
 *
 * // create an instance of DefaultProjectPaymentCalculator using the provided configuration file
 * String configFile = &quot;test_files&quot; + File.separator + &quot;config.properties&quot;;
 * String namespace = &quot;com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator&quot;;
 *
 * calculator = new DefaultProjectPaymentCalculator(configFile, namespace);
 *
 * // Get the default payments for the primary screener and reviewer for the project identified
 * // by projectId = 230
 * List&lt;Long&gt; resourceRoleIDs = new ArrayList&lt;Long&gt;();
 * resourceRoleIDs.add(DefaultProjectPaymentCalculator.PRIMARY_SCREENER_RESOURCE_ROLE_ID);
 * resourceRoleIDs.add(DefaultProjectPaymentCalculator.REVIEWER_RESOURCE_ROLE_ID);
 *
 * Map&lt;Long, BigDecimal&gt; result = calculator.getDefaultPayments(230, resourceRoleIDs);
 * System.out.println(&quot;Default payment: &quot;);
 * for (Long roleId : result.keySet()) {
 *     System.out.println(&quot;Payment for role &quot; + roleId + &quot; = &quot; + result.get(roleId));
 * }
 *
 * // The returned Map will contain two entries :
 * // The first element : key = 2, BigDecimal value = 10 = 0 + (0.0 + 0.01*2)*500
 * // This project has two submissions :
 * // (submission.upload_id = 500 , and submission.upload_id = 623)
 *
 * // The second element : key = 4, BigDecimal value = 85 = 0 + (0.12 + 0.05*1)*500
 * // This project has a single submission that passed screening.
 * // (submission.submission_status_id != 2)
 *
 * // Get the default payment for a Final Reviewer for a project with prize = $ 1000.00 having 3
 * // submissions and project category id = 2
 * BigDecimal finalReviewerPayment =
 *     ((DefaultProjectPaymentCalculator) calculator).getDefaultPayment(2,
 *         DefaultProjectPaymentCalculator.FINAL_REVIEWER_RESOURCE_ROLE_ID, new BigDecimal(1000), 3);
 * System.out.println(&quot;Payment for Final Reviewer role = &quot; + finalReviewerPayment);
 *
 * // The value of the finalReviewerPayment will be 50 = 0 + (0.05 + 0.00*0)*1000
 * // in the above calculation the number of submissions is equal to zero, because it has no meaning
 * // for a final reviewer.
 * </pre>
 *
 * </p>
 * <p>
 * Changes in version 1.1 (Online Review - Iterative Review v1.0):
 * <ul>
 * <li>Added logic to calculate iterative reviewer payment.</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread Safety:</b><br/>
 * This class is thread safe since its base class is thread safe and it adds no additional mutable state which can
 * affect the thread safety.
 * </p>
 *
 * @author Schpotsky, TCSDEVELOPER, duxiaoyang
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Component("defaultProjectPaymentCalculator")
public class DefaultProjectPaymentCalculator implements ProjectPaymentCalculator {

    @Autowired
    private PaymentServiceRpc paymentServiceRpc;

    /**
     * <p>
     * The default configuration namespace of this class. It refers to the fully qualified name of this class,
     * <code>com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator</code>.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in the default constructor.
     * </p>
     */
    public static final String DEFAULT_CONFIG_NAMESPACE =
        "com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator";

    /**
     * <p>
     * The primary screener resource role ID. It
     * </p>
     * <p>
     * It is an immutable static constant. It is used in {@link #getDefaultPayments(long, List)} and
     * {@link #getDefaultPayment(long, long, BigDecimal, int)} methods.
     * </p>
     */
    public static final long PRIMARY_SCREENER_RESOURCE_ROLE_ID = 2;

    /**
     * <p>
     * The reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in {@link #getDefaultPayments(long, List)} and
     * {@link #getDefaultPayment(long, long, BigDecimal, int)} methods.
     * </p>
     */
    public static final long REVIEWER_RESOURCE_ROLE_ID = 4;

    /**
     * <p>
     * The accuracy reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in {@link #getDefaultPayments(long, List)} and
     * {@link #getDefaultPayment(long, long, BigDecimal, int)} methods.
     * </p>
     */
    public static final long ACCURACY_REVIEWER_RESOURCE_ROLE_ID = 5;

    /**
     * <p>
     * The failure reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in {@link #getDefaultPayments(long, List)} and
     * {@link #getDefaultPayment(long, long, BigDecimal, int)} methods.
     * </p>
     */
    public static final long FAILURE_REVIEWER_RESOURCE_ROLE_ID = 6;

    /**
     * <p>
     * The stress reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in {@link #getDefaultPayments(long, List)} and
     * {@link #getDefaultPayment(long, long, BigDecimal, int)} methods.
     * </p>
     */
    public static final long STRESS_REVIEWER_RESOURCE_ROLE_ID = 7;

    /**
     * <p>
     * The aggregator resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant.
     * </p>
     */
    public static final long AGGREGATOR_RESOURCE_ROLE_ID = 8;

    /**
     * <p>
     * The final reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant.
     * </p>
     */
    public static final long FINAL_REVIEWER_RESOURCE_ROLE_ID = 9;

    /**
     * <p>
     * The specification reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant.
     * </p>
     */
    public static final long SPECIFICATION_REVIEWER_RESOURCE_ROLE_ID = 18;

    /**
     * <p>
     * The checkpoint screener resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in {@link #getDefaultPayments(long, List)} and
     * {@link #getDefaultPayment(long, long, BigDecimal, int)} methods.
     * </p>
     */
    public static final long CHECKPOINT_SCREENER_RESOURCE_ROLE_ID = 19;

    /**
     * <p>
     * The checkpoint reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in <code>getSubmissionsCount()</code> and
     * <code>getDefaultPayment()</code> methods.
     * </p>
     */
    public static final long CHECKPOINT_REVIEWER_RESOURCE_ROLE_ID = 20;

    /**
     * <p>
     * The iterative reviewer resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant. It is used in <code>getSubmissionsCount()</code> and
     * <code>getDefaultPayment()</code> methods.
     * </p>
     */
    public static final long ITERATIVE_REVIEWER_RESOURCE_ROLE_ID = 21;

    /**
     * <p>
     * The copilot resource role ID.
     * </p>
     * <p>
     * It is an immutable static constant.
     * </p>
     */
    public static final long COPILOT_RESOURCE_ROLE_ID = 14;

    /**
     * <p>
     * This method is a concrete implementation of the namesake method defined in the interface.
     * </p>
     * <p>
     * It provides concrete implementation for computing the default payments of the specified resource role IDs
     * for the project identified by the given project id, it uses the following linear formula to compute the
     * payment:
     *
     * <pre>
     * Payment = Fixed_Amount + (Base_Coefficient + Incremental_Coeficcient * Number_Of_Submissions) * 1st_place_prize
     * </pre>
     *
     * </p>
     * <p>
     * The needed values to compute the payment for each resource role are retrieved from the database, this class
     * to get the data from the database. The explanation of the variables involved in calculation can be found in
     * class documentation.
     * </p>
     * <p>
     * Logging is performed in this method using the Log instance defined in the base class.
     * </p>
     * <p>
     * If no record is found in the database, then an empty Map is returned.
     * </p>
     *
     * @param projectId
     *            The id of the project for which to compute the payments per resource role.
     * @param resourceRoleIDs
     *            The list of the resource roles IDs for which to compute the payments for the specified project.
     * @return The mapping between the resource role id and the corresponding payment for the project identified by
     *         the specified <code>projectId</code>. (can be empty if there is no data found in the database)
     * @throws IllegalArgumentException
     *             If <code>projectId</code> is not positive or if <code>resourceRoleIDs</code> list is
     *             <code>null</code> or is an empty list or contains <code>null</code> elements.
     * @throws ProjectPaymentCalculatorException
     *             If any error occurred during the operation.
     */
    public Map<Long, BigDecimal> getDefaultPayments(long projectId, List<Long> resourceRoleIDs)
        throws ProjectPaymentCalculatorException {
        String signature = DEFAULT_CONFIG_NAMESPACE + "#getDefaultPayments(long, List<Long>)";
        Helper.logEntrance(log, signature, new String[] {"projectId", "resourceRoleIDs"},
            new Object[] {projectId, resourceRoleIDs});

        // arguments checking
        try {
            Helper.checkPositive(projectId, "projectId");
            Helper.checkNotNullNorEmpty(resourceRoleIDs, "resourceRoleIDs");
            Helper.checkNotNullElements(resourceRoleIDs, "resourceRoleIDs");
        } catch (IllegalArgumentException e) {
            throw Helper.logException(log, signature, e);
        }

        try {
          // Execute the query and get the result.
          List<DefaultPaymentProto> defaultPayments = paymentServiceRpc.getDefaultPayments(projectId);

            Map<Long, BigDecimal> defaultPaymentsMap = new HashMap<Long, BigDecimal>();

            // Iterate through the supported resource roles IDs and compute the default payment for each one of the
            // requested resource role
            for (DefaultPaymentProto defaultPayment : defaultPayments) {
                long roleId = defaultPayment.getResourceRoleId();
                if (resourceRoleIDs.contains(roleId)) {
                    BigDecimalProto serialized = defaultPayment.getFixedAmount();
                    BigDecimal fixedAmount = new BigDecimal(new BigInteger(serialized.getValue().toByteArray()),
                            serialized.getScale(), new MathContext(serialized.getPrecision()))
                            .setScale(2, RoundingMode.HALF_UP);
                    float baseCoefficient = defaultPayment.hasBaseCoefficient() ? defaultPayment.getBaseCoefficient() : 0F;
                    float incrementalCoefficient = defaultPayment.hasIncrementalCoefficient() ? defaultPayment.getIncrementalCoefficient() : 0F;
                    float prize = defaultPayment.hasPrize() ? defaultPayment.getPrize() : 0F;

                    // get submission count
                    int submissionsCount = getSubmissionsCount(defaultPayment, roleId);

                    // calculate the payment
                    BigDecimal augend =
                        BigDecimal.valueOf((baseCoefficient + incrementalCoefficient * submissionsCount) * prize);

                    BigDecimal payment = fixedAmount.add(augend.setScale(2, RoundingMode.HALF_UP));

                    // put into the map
                    defaultPaymentsMap.put(roleId, payment);
                }
            }

            Helper.logExit(log, signature, new Object[] {defaultPaymentsMap});

            return defaultPaymentsMap;
        } catch (SQLException e) {
            throw Helper.logException(log, signature, new ProjectPaymentCalculatorException(
                "Fails to query project payments from database", e));
        }
    }

    /**
     * <p>
     * This method computes the default payment value for the resource role identified the specified
     * <code>resourceRoleId</code> for the given project category , prize and the number of submissions.
     * </p>
     * <p>
     * The following linear formula is used to compute the payment:
     *
     * <pre>
     * payment = fixed_amount + (base_coefficient + incremental_coefficient * number of submissions) * prize.
     * </pre>
     *
     * <code>fixed_amount</code>, <code>base_coefficient</code> and <code>incremental_coefficient</code> depend on
     * the project category and resource role id and they are retrieved from the database. Note that number of
     * submissions has different meaning depending on the resource role. Better explanation on this can be found in
     * class documentation.
     * </p>
     * <p>
     * If no record is found in the database, this method returns <code>null</code>.
     * </p>
     *
     * @param projectCategoryId
     *            The id of the project category for which the payment will be computed.
     * @param resourceRoleId
     *            The id of the resource role for which the payment will be computed.
     * @param prize
     *            The first place prize value to be used to compute the payment.
     * @param submissionsCount
     *            The number of submissions to be considered when computing the payment.
     * @return The value of the payment for the specified resource role id, project category id, prize and the
     *         number of submissions as a <code>BigDecimal</code> (with scale 2 and rounding mode
     *         <code>HALF_UP</code>). (Can be <code>null</code> if there is no data found in the database).
     * @throws IllegalArgumentException
     *             if any of <code>projectCategoryId</code>, <code>resourceRoleId</code> is not positive, or if
     *             <code>submissionsCount</code> is negative. (<code>submissionsCount</code> can be 0), or if
     *             <code>prize</code> is <code>null</code>.
     * @throws ProjectPaymentCalculatorException
     *             If any error occurred during the operation.
     */
    public BigDecimal getDefaultPayment(long projectCategoryId, long resourceRoleId, BigDecimal prize,
        int submissionsCount) throws ProjectPaymentCalculatorException {
        String signature = DEFAULT_CONFIG_NAMESPACE + "#getDefaultPayment(long, long, BigDecimal, int)";
        Helper.logEntrance(log, signature, new String[] {"projectCategoryId", "resourceRoleId",
            "prize", "submissionsCount"},
            new Object[] {projectCategoryId, resourceRoleId, prize, submissionsCount});

        // arguments checking
        try {
            Helper.checkPositive(projectCategoryId, "projectCategoryId");
            Helper.checkPositive(resourceRoleId, "resourceRoleId");
            Helper.checkNotNull(prize, "prize");
            Helper.checkNotNegative(submissionsCount, "submissionsCount");
        } catch (IllegalArgumentException e) {
            throw Helper.logException(log, signature, e);
        }

        // Execute the query and get the result.
        DefaultPaymentProto defaultPayment = paymentServiceRpc.getDefaultPayment(projectCategoryId, resourceRoleId);

        // initialize the variable to contain the payment result
        BigDecimal payment = null;

        // compute the payment value
        if (defaultPayment != null) {
            BigDecimalProto serialized = defaultPayment.getFixedAmount();
            BigDecimal fixedAmount = new BigDecimal(new BigInteger(serialized.getValue().toByteArray()),
                    serialized.getScale(), new MathContext(serialized.getPrecision()))
                    .setScale(2, RoundingMode.HALF_UP);
            float baseCoefficient = defaultPayment.getBaseCoefficient();
            float incrementalCoefficient = defaultPayment.getIncrementalCoefficient();

            // check if number of submissions is needed by the role
            // if it is needed but the given submissionsCount is 0 then assume there is one submission
            // otherwise simply use 0
            int count = isSubmissionRequired(resourceRoleId) ? (submissionsCount == 0 ? 1 : submissionsCount) : 0;

            BigDecimal scaledPrize = prize.setScale(2, RoundingMode.HALF_UP);
            BigDecimal multiplicandCoefficient =
                BigDecimal.valueOf(baseCoefficient + incrementalCoefficient * count);
            payment =
                fixedAmount.add(scaledPrize.multiply(multiplicandCoefficient)).setScale(2, RoundingMode.HALF_UP);
        }
        Helper.logExit(log, signature, new Object[] {payment});

        return payment;
    }

    /**
     * <p>
     * This is a private helper method which gets the number of submissions from the specified result set for the
     * given resource role id.
     * </p>
     *
     * @param resultSet
     *            The result set containing the results for execution of the SQL statement which gets the data to
     *            be used to compute the default payments. (please refer to getDefaultPayments for the details of
     *            the SQL statement which produces this result set).
     * @param resourceRoleId
     *            The ID of the resource role for which to get the number of submissions.
     * @return The number of submissions of the contest based on the resource role. (The number of submissions has
     *         different meanings for each resource role, please refer to implementation notes for details).
     * @throws SQLException
     *             If any error occurs while reading from result set.
     */
    private static int getSubmissionsCount(DefaultPaymentProto defaultPayment, long resourceRoleId) throws SQLException {
        int submissionsCount = 0;
        if (resourceRoleId == PRIMARY_SCREENER_RESOURCE_ROLE_ID) {
            submissionsCount = defaultPayment.getTotalContestSubmissions();
        }

        if (resourceRoleId == REVIEWER_RESOURCE_ROLE_ID || resourceRoleId == ACCURACY_REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == FAILURE_REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == STRESS_REVIEWER_RESOURCE_ROLE_ID) {
            submissionsCount = defaultPayment.getPassedContestSubmissions();
        }

        if (resourceRoleId == CHECKPOINT_SCREENER_RESOURCE_ROLE_ID) {
            submissionsCount = defaultPayment.getTotalCheckpointSubmissions();
        }

        if (resourceRoleId == CHECKPOINT_REVIEWER_RESOURCE_ROLE_ID) {
            submissionsCount = defaultPayment.getPassedCheckpointSubmissions();
        }

        if (resourceRoleId == ITERATIVE_REVIEWER_RESOURCE_ROLE_ID) {
            submissionsCount = defaultPayment.getTotalReviewedContestSubmissions();
        }

        // check if submissionsCount is still 0 here, then one submission is assumed
        // for roles: Primary Screener, Reviewer, Accuracy Reviewer, Failure Reviewer, Stress Reviewer
        // Checkpoint Screener and Checkpoint Reviewer
        if (submissionsCount == 0 && isSubmissionRequired(resourceRoleId)) {
            submissionsCount = 1;
        }
        return submissionsCount;
    }

    /**
     * <p>
     * Check if number of submissions is needed by the given role. The number of submissions is meaningful only for
     * following roles: Primary Screener, Reviewer, Accuracy Reviewer, Failure Reviewer, Stress Reviewer, Checkpoint
     * Screener and Checkpoint Reviewer.
     * </p>
     *
     * @param resourceRoleId
     *            the id of the role to check
     * @return true if given role is one of roles specified above, false otherwise
     */
    private static boolean isSubmissionRequired(long resourceRoleId) {
        return (resourceRoleId == PRIMARY_SCREENER_RESOURCE_ROLE_ID || resourceRoleId == REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == ACCURACY_REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == FAILURE_REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == STRESS_REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == CHECKPOINT_SCREENER_RESOURCE_ROLE_ID
            || resourceRoleId == CHECKPOINT_REVIEWER_RESOURCE_ROLE_ID
            || resourceRoleId == ITERATIVE_REVIEWER_RESOURCE_ROLE_ID);
    }


}
