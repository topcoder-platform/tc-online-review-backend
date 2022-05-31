/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.distribution.commands;

import com.topcoder.onlinereview.component.distribution.DistributionScriptExecutionContext;
import com.topcoder.onlinereview.component.distribution.Util;

import java.util.List;

/**
 * <p>
 * This class is an implementation of DistributionScriptCommand that undefines a
 * variable in the current script execution context. It extends
 * BaseDistributionScriptCommand that provides common functionality for all
 * commands defined in this component.
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and thread safe when
 * DistributionScriptExecutionContext instance is used by the caller in thread
 * safe manner. It uses thread safe Log instance.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class UndefineVariableCommand extends BaseDistributionScriptCommand {
    /**
     * <p>
     * The name of the variable to be undefined. Is initialized in the
     * constructor and never changed after that. Cannot be null or empty. Is
     * used in executeCommand().
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Creates an instance of UndefineVariableCommand with the given parameter.
     * </p>
     *
     * @param name
     *            the name of the variable to be undefined
     * @param conditions
     *            the conditions that indicate when this command must be
     *            executed (all conditions are ANDed, empty list means that the
     *            command must be executed ALWAYS)
     *
     * @throws IllegalArgumentException
     *             if name is null/empty, or if conditions is null or contains
     *             null
     */
    public UndefineVariableCommand(List<CommandExecutionCondition> conditions, String name) {
        super(conditions);

        Util.checkNonNullNonEmpty(name, "name");
        this.name = name;
    }

    /**
     * <p>
     * Executes this command. Un-defines the specific variable. Does nothing if
     * variable is not defined.
     * </p>
     *
     * @param context
     *            the distribution script execution context
     *
     * @throws IllegalArgumentException
     *             if context is null
     */
    protected void executeCommand(DistributionScriptExecutionContext context) {
        Util.checkNonNull(context, "context");

        Util.logInfo(log, "Undefining variable <" + name + ">.");
        context.setVariable(name, null);
    }
}
