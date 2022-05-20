/*
 * Copyright (C) 2007-2012 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.onlinereview.component.reviewupload;

import com.topcoder.onlinereview.component.deliverable.UploadManager;
import com.topcoder.onlinereview.component.project.management.ProjectManager;
import com.topcoder.onlinereview.component.project.phase.PhaseManager;
import com.topcoder.onlinereview.component.resource.ResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * This is the default implementation of <code>ManagersProvider</code>. It provides instances of entity managers
 * used in <code>UploadServices</code> class. The managers are loaded using <code>ObjectFactory</code> or set
 * through constructor. The log is performed for all methods.
 * </p>
 *
 * <p>
 * A sample configuration file that can be used is given below.
 *
 * <pre>
 * &lt;Config name=&quot;com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider&quot;&gt;
 *      &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *          &lt;Value&gt;myObjectFactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;resourceManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;resourceManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;phaseManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;phaseManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;projectManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;projectManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;uploadManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;uploadManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.1 (Online Review Build From Sources) Change notes:
 *   <ol>
 *     <li>Removed dependency on Auto Screening.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Thread safety: It's impossible to change the state of this class, hence the class is thread safe.
 * </p>
 *
 * @author fabrizyo, cyberjag, lmmortal
 * @version 1.1.2
 */
@Slf4j
@Component
public class DefaultManagersProvider implements ManagersProvider {

    /**
     * <p>
     * Represents the default namespace for this class used to load the configuration with
     * <code>ConfigManager</code>.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = DefaultManagersProvider.class.getName();

    /**
     * <p>
     * Represents the manager to manage the resources. It is defined in constructor and can be accessed using
     * {@link #getResourceManager()}. It cannot be <code>null</code>.
     * </p>
     */
    @Autowired
    private ResourceManager resourceManager;

    /**
     * <p>
     * Represents the manager to manage the projects. It is defined in constructor and can be accessed using
     * {@link #getProjectManager()}. It cannot be <code>null</code>.
     * </p>
     */
    @Autowired
    private ProjectManager projectManager;

    /**
     * <p>
     * Represents the manager to manage the phases of a project. It is defined in constructor and can be accessed
     * using {@link #getPhaseManager()}. It cannot be <code>null</code>.
     * </p>
     */
    @Autowired
    private PhaseManager phaseManager;

    /**
     * <p>
     * Represents the manager to manage the deliverables: Submission and Uploads. It is defined in constructor and
     * can be accessed using {@link #getUploadManager()}. It cannot be <code>null</code>.
     * </p>
     */
    @Autowired
    private UploadManager uploadManager;

    /**
     * <p>
     * Returns the <code>ResourceManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ResourceManager</code> instance
     */
    public ResourceManager getResourceManager() {
        log.debug("Entered DefaultManagersProvider#getResourceManager()");
        try {
            return resourceManager;
        } finally {
            log.debug("Exited DefaultManagersProvider#getResourceManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>ProjectManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ProjectManager</code> instance
     */
    public ProjectManager getProjectManager() {
        log.debug("Entered DefaultManagersProvider#getProjectManager()");
        try {
            return projectManager;
        } finally {
            log.debug("Exited DefaultManagersProvider#getProjectManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>PhaseManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>PhaseManager</code> instance
     */
    public PhaseManager getPhaseManager() {
        log.debug("Entered DefaultManagersProvider#getPhaseManager()");
        try {
            return phaseManager;
        } finally {
            log.debug("Exited DefaultManagersProvider#getPhaseManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>UploadManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>UploadManager</code> instance
     */
    public UploadManager getUploadManager() {
        log.debug("Entered DefaultManagersProvider#getUploadManager()");
        try {
            return uploadManager;
        } finally {
            log.debug("Exited DefaultManagersProvider#getUploadManager()");
        }
    }
}
