/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.onlinereview.component.contest;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManager;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManagerLocal;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class ContestEligibilityService {

    /**
     * <p>
     * A <code>ContestEligibilityValidationManager</code> providing access to available
     * <code>Contest Eligibility Validation EJB</code>.
     * </p>
     */
    @Autowired
    private ContestEligibilityValidationManager contestEligibilityValidationManager;

    /**
     * <p>
     * A <code>ContestEligibilityManager</code> providing access to available
     * <code>Contest Eligibility Persistence EJB</code>.
     * </p>
     */
    @Autowired
    private ContestEligibilityManager contestEligibilityManager;
   
    /**
     * Returns whether a user is eligible for a particular contest.
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestEligibilityValidatorException
     *             if any other error occurs
     * @since 1.2.2
     */
    public boolean isEligible(long userId, long contestId, boolean isStudio) throws ContestEligibilityValidatorException {
        String methodName = "isEligible";
        logger.debug("Enter: " + methodName);

        boolean eligible;
    	
    	try {
			List<ContestEligibility> eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
			eligible = contestEligibilityValidationManager.validate(userId, eligibilities);
		} catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		} catch (ContestEligibilityValidationManagerException e) {
            logger.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		}
    	    	
        logger.debug("Exit: " + methodName);
    	return eligible;
    }

     /**
     * Returns whether a contest has any eligibility
     *
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestEligibilityValidatorException
     *             if any other error occurs
     * @since 1.2
     */
    public boolean hasEligibility(long contestId, boolean isStudio) throws ContestEligibilityValidatorException {

        String methodName = "hasEligibility";
        logger.debug("Enter: " + methodName);

        List<ContestEligibility> eligibilities;
    	
    	try {
			 eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
		} catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		}
    	    	
        if (eligibilities == null || eligibilities.size() == 0) {
            return false;
        }

        return true;
    }


    /**
     * Return a list of contest ids that has eligibility.
     *
     * @param contestIds
     *            the contest id list
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of contest ids
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    public Set<Long> haveEligibility(Long[] contestIds, boolean isStudio) throws ContestEligibilityValidatorException
    {

        try {
			 return contestEligibilityManager.haveEligibility(contestIds, isStudio);
		} catch (ContestEligibilityPersistenceException e) {
            log.error(e.getMessage(), e);
            throw new ContestEligibilityValidatorException(e.getMessage(), e);
		}
    }

    /**
     * <p>Sets the contest eligibility validation manager to be used by this service in local environment.</p>
     *
     * @param contestEligibilityValidationManager a <code>ContestEligibilityValidationManager</code> to be used by this
     *        service in local environment.
     * @throws IllegalArgumentException if specified <code>contestEligibilityValidationManager</code> is
     *         <code>null</code>.
     * @since 1.0.1
     */
    protected void setContestEligibilityValidationManager(
        ContestEligibilityValidationManager contestEligibilityValidationManager) {
        if (contestEligibilityValidationManager == null) {
            throw new IllegalArgumentException("The parameter [contestEligibilityValidationManager] is NULL");
        }
        this.contestEligibilityValidationManager = contestEligibilityValidationManager;
    }

    /**
     * <p>Sets the contest eligibility manager to be used by this service in local environment.</p>
     *
     * @param contestEligibilityManager a <code>ContestEligibilityManager</code> to be used by this
     *        service in local environment.
     * @throws IllegalArgumentException if specified <code>contestEligibilityManager</code> is <code>null</code>.
     * @since 1.0.1
     */
    protected void setContestEligibilityManager(ContestEligibilityManager contestEligibilityManager) {
        if (contestEligibilityManager == null) {
            throw new IllegalArgumentException("The parameter [contestEligibilityManager] is NULL");
        }
        this.contestEligibilityManager = contestEligibilityManager;
    }
}
