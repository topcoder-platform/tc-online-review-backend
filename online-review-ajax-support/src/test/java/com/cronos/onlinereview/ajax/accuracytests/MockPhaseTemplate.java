/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import java.util.Date;

import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.PhaseTemplate;
import com.topcoder.project.phases.template.PhaseTemplateException;


/**
 * Mock class.
 * 
 * @author assistant
 * @version 1.0
 */
public class MockPhaseTemplate implements PhaseTemplate {

    public String[] getAllTemplateNames() {
        return null;
    }

    @Override
    public String[] getAllTemplateNames(int category) throws PhaseTemplateException {
        return new String[0];
    }

    @Override
    public int getTemplateCategory(String templateName) throws PhaseTemplateException {
        return 0;
    }

    @Override
    public String getDefaultTemplateName(int category) throws PhaseTemplateException {
        return null;
    }

    @Override
    public String getTemplateDescription(String templateName) throws PhaseTemplateException {
        return null;
    }

    @Override
    public Date getTemplateCreationDate(String templateName) throws PhaseTemplateException {
        return null;
    }

    @Override
    public Project applyTemplate(String templateName, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate, Date startDate) throws PhaseTemplateException {
        return null;
    }

    @Override
    public Project applyTemplate(String templateName, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate) throws PhaseTemplateException {
        return null;
    }

    public Project applyTemplate(String template, Date startDate) {
        return null;
    }

    public Project applyTemplate(String template) {
        return null;
    }

    @Override
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds) throws PhaseTemplateException {
        return null;
    }

    @Override
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, Date startDate) throws PhaseTemplateException {
        return null;
    }

    @Override
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate) throws PhaseTemplateException {
        return null;
    }

    @Override
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate, Date startDate) throws PhaseTemplateException {
        return null;
    }
}
