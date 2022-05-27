package com.topcoder.onlinereview.component.webcommon.tag;

import com.topcoder.onlinereview.component.shared.dataaccess.DataAccess;
import com.topcoder.onlinereview.component.shared.dataaccess.Request;

import javax.naming.NamingException;
import javax.servlet.jsp.JspException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.topcoder.onlinereview.component.util.SpringUtils.getOltpJdbcTemplate;

/**
 * My comments/description/notes go here
 *
 * @author djFD molc@mail.ru
 * @version 1.02
 * @deprecated
 */
public class QueryIteratorTag extends IteratorTag {
    private String command = null;
    private List<Map<String, Object>> rsc = null;
    private Hashtable params;

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        try {
            DataAccess dai = new DataAccess(getOltpJdbcTemplate());
            Request dataRequest = new Request();
            dataRequest.setContentHandle(command);

            if (params != null) {
                Enumeration e = params.keys();
                while (e.hasMoreElements()) {
                    Object key = e.nextElement();
                    dataRequest.setProperty(
                            (String) key,
                            (String) params.get(key)
                    );
                }
            }

            Map<String, List<Map<String, Object>>> result = dai.getData(dataRequest);
            // for now will take first of queries results
            Iterator i = result.keySet().iterator();
            if (i.hasNext()) {
                rsc = result.get(i.next());
            } else {
                rsc = null;
            }
            setCollection(rsc);
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw new JspException("NamingException occured: " + ne.getMessage());
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new JspException("SQLException occured: " + sqle.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException("Exception occured: " + e.getMessage());
        }
        return super.doStartTag();
    }

    /**
     * Sets the query name.
     *
     * @param cmd The query to set
     */
    public void setCommand(String cmd) {
        command = cmd;
    }

    /**
     * Sets the query parameters. Format is: 'name:value, name:value, ...'.
     *
     * @param param The params to set
     */
    public void setParam(String param) {
        params = new Hashtable();
        StringTokenizer st = new StringTokenizer(param, " ,");
        while (st.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st.nextToken(), ":");
            if (st2.countTokens() != 2) continue;
            params.put(st2.nextToken(), st2.nextToken());
        }
    }

    /**
     * Just in case the app server is caching tag (jboss!!!)
     * we have to clear out all the instance variables at the
     * end of execution.
     */
    public int doEndTag() throws JspException {
        release();
        return super.doEndTag();
    }
    

    public void release() {
        this.command = null;
        this.rsc = null;
        this.params = null;
        super.release();
    }


}
