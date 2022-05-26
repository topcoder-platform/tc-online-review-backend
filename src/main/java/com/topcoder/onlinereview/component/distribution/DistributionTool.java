/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributionTool {
    /**
     * <p>
     * The name of the input parameter that holds a component version. This is a
     * string constant. Is used in createDistribution().
     * </p>
     */
    public static final String VERSION_PARAM_NAME = "version";

    /**
     * <p>
     * The name of the input parameter that holds a component name. This is a
     * string constant. Is used in createDistribution().
     * </p>
     */
    public static final String COMPONENT_NAME_PARAM_NAME = "Component Name";

    /**
     * <p>
     * The name of the input parameter that holds a package name. This is a
     * string constant. Is used in createDistribution().
     * </p>
     */
    public static final String PACKAGE_NAME_PARAM_NAME = "package.name";

    /**
     * <p>
     * Represents the build bit index in the version.
     * </p>
     */
    private static final int BUILD_BIT = 3;

    /**
     * <p>
     * The mapping between distribution types and distribution scripts
     * associated with them. Is initialized in the constructor and never changed
     * after that. Cannot be null. Cannot contain null/empty key or null value.
     * Is used in createDistribution().
     * </p>
     */
    private final Map<String, DistributionScript> scripts;

    /**
     * <p>
     * Creates an instance of DistributionTool using the given configuration
     * object.
     * </p>
     *
     * @throws IllegalArgumentException
     *             if config is null
     * @throws DistributionToolConfigurationException
     *             if some error occurred when reading configuration of this
     *             class or initializing this class using this configuration
     */
    public DistributionTool(Map<String, String> scriptsConfig) {
        Util.checkNonNull(scriptsConfig, "scriptsConfig");

        // Create map for distribution type-to-script mapping:
        scripts = new HashMap<>();
        DistributionScriptParser scriptParser = new DistributionScriptParser();
        // Get scripts configuration:
        for (Map.Entry<String, String> config : scriptsConfig.entrySet()) {
                // Get distribution type property:
                DistributionScript script = new DistributionScript();
                processScriptCommands(script, config.getValue(), scriptParser);
                scripts.put(config.getKey(), script);
        }
        // At least one script is required, if not, throw
        // DistributionToolConfigurationException
        if (scripts.isEmpty()) {
            throw new DistributionToolConfigurationException(
                    "At least one script should be configured.");
        }
    }

    private void processScriptCommands(DistributionScript script,
                                       String scriptPath,
                                       DistributionScriptParser scriptParser) {
        String scriptFolder = new File(scriptPath).getParent();
        if (scriptFolder == null) {
            // set the script folder to current folder when it's at root
            script.setScriptFolder("./");
        } else {
            script.setScriptFolder(scriptFolder);
        }

        InputStream inputStream = null;
        try {

            inputStream = new FileInputStream(scriptPath);

            // Parse script command from script file:
            scriptParser.parseCommands(inputStream, script);
        } catch (FileNotFoundException e) {
            throw new DistributionToolConfigurationException(
                    "The scriptPath doesn't exist.", e);
        } catch (DistributionScriptParsingException e) {
            throw new DistributionToolConfigurationException(
                    "Fail to parse commands in the script.", e);
        } finally {
            Util.safeClose(inputStream);
        }
    }

    /**
     * <p>
     * Creates a distribution of the specified type using the given parameters.
     * </p>
     *
     * @param parameters
     *            the input parameters to be used
     * @param distributionType
     *            the distribution type
     *
     * @throws IllegalArgumentException
     *             if distributionType is null/empty, parameters is null or
     *             contains null/empty key or null value
     * @throws UnknownDistributionTypeException
     *             if the specified distribution type is unknown
     * @throws MissingInputParameterException
     *             if any of required input parameters is missing
     * @throws DistributionScriptCommandExecutionException
     *             if some error occurred when executing one of script commands
     */
    public void createDistribution(String distributionType, Map<String, String> parameters)
        throws UnknownDistributionTypeException, MissingInputParameterException,
        DistributionScriptCommandExecutionException {
        Util.checkNonNullNonEmpty(distributionType, "distributionType");
        Util.checkParams(parameters, "parameters");

        // Get script for the specified distribution type:
        DistributionScript script = scripts.get(distributionType);
        if (script == null) {
            throw new UnknownDistributionTypeException(
                    "Unknown distribution type " + distributionType);
        }

        List<String> requiredParams = script.getRequiredParams();
        for (String requiredParam : requiredParams) {
            // Check if parameters contains a required parameter:
            if (!parameters.containsKey(requiredParam)) {
                throw new MissingInputParameterException(
                        "The required parameter " + requiredParam
                                + " is missing.");
            }
        }
        // Create new script execution context:
        DistributionScriptExecutionContext context = new DistributionScriptExecutionContext();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            context.setVariable(param.getKey(), param.getValue());
        }
        // Get default parameters for the script:
        Map<String, String> defaultParams = script.getDefaultParams();
        for (Map.Entry<String, String> defaultParam : defaultParams.entrySet()) {
            if (!parameters.containsKey(defaultParam.getKey())) {
                context.setVariable(defaultParam.getKey(), defaultParam
                        .getValue());
            }
        }

        // Prepare special context variables:
        String version = getContextVariable(context, VERSION_PARAM_NAME);
        if (version != null) {
            // Note:
            String[] versionParts = version.split("\\.");

            context.setVariable("version.major", versionParts[0]);
            context.setVariable("version.minor",
                    versionParts.length > 1 ? versionParts[1] : "0");
            context.setVariable("version.micro",
                    versionParts.length > 2 ? versionParts[2] : "0");
            context.setVariable("version.build",
                    versionParts.length > BUILD_BIT ? versionParts[BUILD_BIT] : "1");
        }
        // Get value of "Component Name" variable:
        String componentName = getContextVariable(context, COMPONENT_NAME_PARAM_NAME);
        if (componentName != null) {
            // Get component name with underscores:
            String underscoreComponentName = componentName.replace(" ", "_");
            // Define new variable:
            context.setVariable("component name", componentName.toLowerCase());
            // Define new variable:
            context.setVariable("Component_Name", underscoreComponentName);
            // Define new variable:
            context.setVariable("component_name", underscoreComponentName
                    .toLowerCase());
        }

        // Get value of "package.name" variable:
        String packageName = getContextVariable(context, PACKAGE_NAME_PARAM_NAME);
        if (packageName != null) {
            context.setVariable("package/name", packageName.replace(".", "/"));
            context
                    .setVariable("package\\name", packageName
                            .replace(".", "\\"));
        }
        context.setVariable("distribution_type", distributionType);
        context.setVariable("current_year", new SimpleDateFormat("yyyy")
                .format(new Date()));
        context.setVariable("script_dir", script.getScriptFolder());

        // Get the list of script commands:
        List<DistributionScriptCommand> commands = script.getCommands();
        for (DistributionScriptCommand cmd : commands) {
            cmd.execute(context);
        }
    }

    /**
     * <p>
     * Get the value of given variable name from context.
     * </p>
     *
     * @param context
     *            the context
     * @param name
     *            the name of the variable
     * @return the value, or null if it's not defined in the context or empty
     */
    private String getContextVariable(DistributionScriptExecutionContext context, String name) {
        String value = context.getVariable(name);
        if (value != null) {
            return value.trim().length() == 0 ? null : value;
        }
        return value;
    }
}
