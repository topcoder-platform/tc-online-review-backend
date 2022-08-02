/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.distribution.commands;

import com.topcoder.onlinereview.component.distribution.DistributionScriptCommand;
import com.topcoder.onlinereview.component.distribution.DistributionScriptCommandExecutionException;
import com.topcoder.onlinereview.component.distribution.DistributionScriptExecutionContext;
import com.topcoder.onlinereview.component.distribution.ProcessExecutionException;
import com.topcoder.onlinereview.component.distribution.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is a base class for all DistributionScriptCommand implementations
 * provided in this component. It provides a common logic for all commands:
 * supports multiple CommandExecutionCondition instances being associated with
 * the command, holds a Log instance and provides methods for replacing variable
 * fields in the given string. This class logs all unknown variable names at
 * WARN level using the provided Logging Wrapper logger. Subclasses should log
 * information about performed operations at INFO level for consistency; they
 * can access the logger using getLog() method. All subclasses must implement
 * executeCommand() method that is called when command execution conditions are
 * met only.
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
public abstract class BaseDistributionScriptCommand implements
        DistributionScriptCommand {
    /**
     * <p>
     * The logger used by this class for logging executed command description
     * and warnings. Is null if logging is not required. Is initialized in the
     * constructor and never changed after that. Has a protected getter. Is used
     * in replaceVariableFields().
     * </p>
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>
     * The conditions that indicate when this command must be executed (all
     * conditions are ANDed, empty list means that the command must be executed
     * ALWAYS). Is initialized in the constructor and never changed after that.
     * Cannot be null. Cannot contain null. Is used in execute().
     * </p>
     */
    private final List<CommandExecutionCondition> conditions;

    /**
     * <p>
     * Creates an instance of BaseDistributionScriptCommand with the given
     * logger and conditions.
     * </p>
     *
     * @param conditions
     *            the conditions that indicate when this command must be
     *            executed (all conditions are ANDed, empty list means that the
     *            command must be executed ALWAYS)
     *
     * @throws IllegalArgumentException
     *             if conditions is null or contains null
     */
    protected BaseDistributionScriptCommand(List<CommandExecutionCondition> conditions) {
        Util.checkList(conditions, "conditions");
        this.conditions = new ArrayList<CommandExecutionCondition>(conditions);
    }

    /**
     * <p>
     * Executes this command. Checks whether command execution conditions are
     * met.
     * </p>
     *
     * @param context
     *            the distribution script execution context
     *
     * @throws IllegalArgumentException
     *             if context is null
     * @throws DistributionScriptCommandExecutionException
     *             if some other error occurred
     */
    public void execute(DistributionScriptExecutionContext context)
        throws DistributionScriptCommandExecutionException {
        Util.checkNonNull(context, "context");

        for (CommandExecutionCondition con : conditions) {
            if (!con.check(context)) {
                return;
            }
        }

        executeCommand(context);
    }

    /**
     * <p>
     * Executes this command. This method is executed when all conditions are
     * met only.
     * </p>
     *
     * @param context
     *            the distribution script execution context
     *
     * @throws IllegalArgumentException
     *             if context is null
     * @throws DistributionScriptCommandExecutionException
     *             if some other error occurred
     */
    protected abstract void executeCommand(DistributionScriptExecutionContext context)
        throws DistributionScriptCommandExecutionException, ProcessExecutionException;

    /**
     * <p>
     * Replaces all variable fields in format "%{variable_name}" in the given
     * string. Values of variables are taken from the given
     * DistributionScriptExecutionContext instance. Unknown variable fields are
     * not replaced (but logged at WARN level if logger was provided).
     * </p>
     *
     * @param value
     *            the string value to be processed
     * @param context
     *            the distribution script execution context
     * @return the string with variable fields replaced (not null)
     *
     * @throws IllegalArgumentException
     *             if value or context is null
     */
    protected String replaceVariableFields(String value,
            DistributionScriptExecutionContext context) {
        Util.checkNonNull(value, "value");
        Util.checkNonNull(context, "context");

        StringBuilder sb = new StringBuilder(value.length());

        for (int i = 0; i < value.length(); ++i) {
            char cur = value.charAt(i);
            if (cur == '%') {
                // this is the last character
                if (i + 1 < value.length()) {
                    char next = value.charAt(i + 1);
                    if (next == '{') {
                        // Find the first '}' character that goes after the
                        // current
                        // character.
                        int index = value.indexOf('}', i);
                        if (index == -1) {
                            // doesn't find
                            sb.append('%');
                            continue;
                        } else {
                            // Extract variableName:String that goes between '{'
                            // and
                            // '}' characters.
                            // i index
                            // %{...}
                            String varValue = null;
                            String varName = value.substring(i + 2, index);
                            // the extracted varName may be null
                            if (varName.trim().length() != 0) {
                                varValue = context.getVariable(varName);
                            }
                            if (varValue != null) {
                                sb.append(varValue);
                                // Ignore all used characters including '}'
                                i = index;
                            } else {
                                if (log != null) {
                                    log.warn("unknown variable " + varName);
                                }
                                // Append the current character ('%') to sb.
                                sb.append(cur);
                            }
                        }
                    } else {
                        sb.append(cur);
                    }
                } else {
                    sb.append(cur);
                }
            } else {
                // Append the current character to sb.
                sb.append(cur);
            }
        }
        return sb.toString();
    }
}
