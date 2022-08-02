package com.topcoder.onlinereview.component.webcommon;

import com.topcoder.onlinereview.component.shared.dataaccess.DataAccess;
import com.topcoder.onlinereview.component.shared.dataaccess.RequestInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author Lars Backstrom
 * @version $Revision$
 */
public class CachedDataAccess extends DataAccess {
    private static Logger log = LoggerFactory.getLogger(CachedDataAccess.class);

    /**
     * Construtor that takes the timeout for the object should it need to
     * be cached.  The object will be removed from the cache atfter
     * <code>maxAge.age()</code> milliseconds.
     */
    public CachedDataAccess(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * This method passes a query command request and a connection
     * to the data retriever and receives and passes on the results.
     *
     * @param request A <tt>RequestInt</tt> request object containing a number
     *                of input property values.
     * @return A map of the query results, where the keys are strings
     *         of query names and the values are <tt>ResultSetContainer</tt> objects.
     * @throws Exception if there was an error encountered while retrieving
     *                   the data from the EJB.
     */
    public Map<String, List<Map<String, Object>>> getData(RequestInt request) throws Exception {
        return getDataRetriever().executeCommand(request.getProperties());

    }
}

