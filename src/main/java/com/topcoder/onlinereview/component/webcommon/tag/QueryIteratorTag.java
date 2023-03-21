package com.topcoder.onlinereview.component.webcommon.tag;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.webcommon.WebCommonServiceRpc;
import com.topcoder.onlinereview.grpc.webcommon.proto.DoStartTagResponse;
import com.topcoder.onlinereview.grpc.webcommon.proto.ParameterListProto;
import com.topcoder.onlinereview.grpc.webcommon.proto.ParameterProto;

import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
            WebCommonServiceRpc webCommonServiceRpc = GrpcHelper.getWebCommonServiceRpc();
            DoStartTagResponse result = webCommonServiceRpc.doStartTag(command, params);
    
            rsc = new ArrayList<Map<String, Object>>();
            for (ParameterListProto parameters : result.getParameterListsList()) {
                Map<String, Object> row = new HashMap<>();
                for (ParameterProto parameter : parameters.getParametersList()) {
                    String key = parameter.hasKey() ? parameter.getKey() : null;
                    Object value = parameter.hasValue() ? parameter.getValue() : null;
                    row.put(key, value);
                }
                rsc.add(row);
            }
            setCollection(rsc);
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
