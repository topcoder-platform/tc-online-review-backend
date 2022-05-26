/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.project.phase;

import com.topcoder.onlinereview.component.deliverable.Submission;
import com.topcoder.onlinereview.component.project.phase.handler.PhasesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Contains various methods that are called directly from within the Online Review application.
 * </p>
 * @author VolodymyrK
 * @version 1.7.4
 */
@Component
public class OnlineReviewServices {

    /**
     * Represents the default name space of this class. It is used in the
     * default constructor to load configuration settings.
     */
    public static final String DEFAULT_NAMESPACE = "com.cronos.onlinereview.phases.OnlineReviewServices";

    /**
     * <p>
     * This field is used to retrieve manager instances. It is initialized in the constructor
     * and never change after that. It is never null.
     * </p>
     */
    @Autowired
    private ManagerHelper managerHelper;

    /**
     * Updates the scores and placements of the submissions by aggregation the scores from the reviews.
     * Also updates the 1st place winner and the 2nd place winner in the project properties.
     *
     * @param phase Review phase.
     * @param operator The operator name.
     * @param updateInitialScore True if the "initial" scores of the submissions should be updated.
     * @param updateFinalResults True if the "final" scores and placements of the submissions should be updated.
     * @return Array of all updated submissions.
     */
    public Submission[] updateSubmissionsResults(Phase phase, String operator,
                                                 boolean updateInitialScore, boolean updateFinalResults) throws PhaseHandlingException {
        return PhasesHelper.updateSubmissionsResults(managerHelper, phase, operator,
            updateInitialScore, updateFinalResults);
    }
}
