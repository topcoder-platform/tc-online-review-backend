package com.topcoder.onlinereview.component.commandline;

/**
 * <p>Abstraction for validating command line arguments. Subclasses provide
 *    validation for various types.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: TopCoder</p>
 * @author snard6
 * @version 1.0
 */

public interface ArgumentValidator {

    /**
     * <p>Takes a string from a command line switch and validates it.</p>
     * <p>For instance, in "<code>java app -threads 15</code>", the string
     *    "15" would be passed to the ArgumentValidator set for the "threads"
     *    Switch.</p>
     * @param argument the string to validate
     * @throws ArgumentValidationException used to describe validation errors
     * @see Switch
     */
    public void validate(String argument)
            throws ArgumentValidationException;
}