package com.topcoder.onlinereview.component.webcommon.model.language;

import com.topcoder.onlinereview.component.grpcclient.GrpcHelper;
import com.topcoder.onlinereview.component.grpcclient.webcommon.WebCommonServiceRpc;
import com.topcoder.onlinereview.grpc.webcommon.proto.DataTypeMappingProto;
import com.topcoder.onlinereview.grpc.webcommon.proto.DataTypeProto;

import java.util.HashMap;
import java.util.List;

public class DataTypeFactory extends SimpleDataTypeFactory {

    protected static void initializeFromDB() {
        try {
            WebCommonServiceRpc webCommonServiceRpc = GrpcHelper.getWebCommonServiceRpc();
            List<DataTypeMappingProto> result = webCommonServiceRpc.getDataTypeMappings();
            HashMap<String, HashMap<String, String>> mappings = new HashMap<>();
            for (DataTypeMappingProto row: result) {
                String dataTypeId = row.hasDataTypeId() ? row.getDataTypeId() : null;
                String languageId = row.hasLanguageId() ? row.getLanguageId() : null;
                String desc = row.hasDisplayValue() ? row.getDisplayValue() : null;
                HashMap<String, String> mapping = mappings.get(dataTypeId);

                if (mapping == null) {
                    mapping = new HashMap<>();
                    mappings.put(dataTypeId, mapping);
                }
                mapping.put(languageId, desc);
            }
            List<DataTypeProto> dtRs = webCommonServiceRpc.getDataTypes();
            for (DataTypeProto row: dtRs) {
                DataType dt = new DataType(row.getDataTypeId(), row.hasDataTypeDesc() ? row.getDataTypeDesc() : null,
                        mappings.get(String.valueOf(row.getDataTypeId())));
                types.put(String.valueOf(row.getDataTypeId()), dt);

            }

            initialized = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static public void initialize() {
        if(initialized)
            return;

        initializeFromDB();
    }
}
