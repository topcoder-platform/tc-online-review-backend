package com.topcoder.onlinereview.component.webcommon.model.language;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topcoder.onlinereview.component.util.CommonUtils.executeSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getInt;
import static com.topcoder.onlinereview.component.util.CommonUtils.getString;
import static com.topcoder.onlinereview.component.util.SpringUtils.getOltpJdbcTemplate;

public class DataTypeFactory extends SimpleDataTypeFactory {

    protected static void initializeFromDB() {
        try {
            JdbcTemplate oltpJdbcTemplate = getOltpJdbcTemplate();
            HashMap<String, HashMap<String, String>> mappings = new HashMap<>();
            List<Map<String, Object>> rs = executeSql(oltpJdbcTemplate, "SELECT data_type_id, language_id, display_value FROM data_type_mapping");
            for (Map<String, Object> row: rs) {
                String dataTypeId = getString(row, "data_type_id");
                String languageId = getString(row, "language_id");
                String desc = getString(row, "display_value");
                HashMap<String, String> mapping = mappings.get(dataTypeId);

                if (mapping == null) {
                    mapping = new HashMap<>();
                    mappings.put(dataTypeId, mapping);
                }
                mapping.put(languageId, desc);
            }
            List<Map<String, Object>> dtRs = executeSql(oltpJdbcTemplate, "SELECT data_type_id, data_type_desc  FROM data_type");
            for (Map<String, Object> row: dtRs) {
                DataType dt = new DataType(getInt(row, "data_type_id"), getString(row, "data_type_desc"),
                        mappings.get(getString(row, "data_type_id")));
                types.put(getString(row, "data_type_id"), dt);

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
