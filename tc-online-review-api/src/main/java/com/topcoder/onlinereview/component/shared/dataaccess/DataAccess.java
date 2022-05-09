package com.topcoder.onlinereview.component.shared.dataaccess;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * This bean processes a {@link RequestInt} and returns the data.
 *
 * @author Dave Pecora
 * @version $Revision$
 * @see RequestInt
 */
public class DataAccess {
  private EntityManager entityManager;

  /**
   * Construtor that takes a data source to be used.
   *
   * @param entityManager
   */
  public DataAccess(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  /**
   * This method passes a query command request and a connection to the data retriever and receives
   * and passes on the results
   *
   * @param request A <tt>RequestInt</tt> request object containing a number of input property
   *     values.
   * @return A map of the query results, where the keys are strings of query names and the values
   *     are <tt>ResultSetContainer</tt> objects.
   * @throws Exception if there was an error encountered while retrieving the data from the EJB.
   */
  public Map<String, List<Map<String, Object>>> getData(RequestInt request) throws Exception {
    try {
      DataRetriever dr = getDataRetriever();
      return dr.executeCommand(request.getProperties());
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  protected DataRetriever getDataRetriever() {
    return new DataRetriever(entityManager);
  }
}
