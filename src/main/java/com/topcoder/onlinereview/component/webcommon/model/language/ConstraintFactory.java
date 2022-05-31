package com.topcoder.onlinereview.component.webcommon.model.language;

import org.w3c.dom.Node;

public class ConstraintFactory
{
    /**
     * Builds a <code>Constraint</code> appropriate for the given node-set.
     *
     * @param node  The node-set specifying a constraint
     * @see Constraint
     */
    static public Constraint build(Node node)
    {
        return buildUserConstraint(node);
    }

    /**
     * Builds a <code>UserConstraint</code> from a node-set representing a <code>user-constraint</code> element.
     *
     * @param node  The node-set specifying a constraint
     */
    static Constraint buildUserConstraint(Node node)
    {
        return new UserConstraint(NodeElementFactory.build(node));
    }
}

