package com.topcoder.onlinereview.component.idgenerator;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeSqlWithParam;
import static com.topcoder.onlinereview.component.util.CommonUtils.executeUpdateSql;
import static com.topcoder.onlinereview.component.util.CommonUtils.getLong;

/**
 * High value fetcher implementation.
 *
 * @version     1.0
 * @author      Timur Zambalayev
 */
final class HighValueFetcherImpl implements HighValueFetcher {

    private final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final String highValueColumnName;
    private final long maxHi;
    private final boolean autoInit;
    private final String selectSql;
    private final String updateSql;
    private final String insertSql;

    /**
     * Creates an instance of this class.
     *
     * @param tableName             table name.
     * @param userDefColumnName     userDef column name.
     * @param highValueColumnName   high value column name.
     * @param maxHi                 the maximum high value.
     * @param autoInit              if there's auto initialization
     */
    HighValueFetcherImpl(JdbcTemplate jdbcTemplate, String tableName, String userDefColumnName, String highValueColumnName,
            long maxHi, boolean autoInit) {
        this.jdbcTemplate=jdbcTemplate;
        this.tableName=tableName;
        this.highValueColumnName=highValueColumnName;
        this.maxHi=maxHi;
        this.autoInit=autoInit;
        selectSql="SELECT "+highValueColumnName+" FROM "+tableName+" WHERE "+userDefColumnName+"=?";
        updateSql="UPDATE "+tableName+" SET "+highValueColumnName+"="+highValueColumnName+"+1 WHERE "+userDefColumnName+"=?";
        insertSql="INSERT INTO "+tableName+" ("+userDefColumnName+", "+highValueColumnName+") VALUES (?, 0)";
    }

    public long nextHighValue(String tableId) {
        setHighValue(tableId);
        long id=getCurrentHighValue(tableId);
        testId(id);
        return id;
    }

    private void setHighValue(String tableId) {
        int rowCount=executeUpdateSql(jdbcTemplate, updateSql, newArrayList(tableId));
        if (rowCount!=1) {
            if (rowCount!=0) {
                throw new RuntimeException("rowCount="+rowCount);
            }
            if (!autoInit) {
                throw new RuntimeException("no such row in the id generation table, tableId="+tableId);
            }
            insertZero(tableId);
        }
    }

    private long getCurrentHighValue(String tableId) {
        List<Map<String, Object>> resultSet=executeSqlWithParam(jdbcTemplate, selectSql, newArrayList(tableId));
        if (resultSet.isEmpty()) {
            throw new RuntimeException("no such row in the id generation table, tableId="+tableId);
        }
        if (resultSet.size() > 1) {
            throw new RuntimeException("more than one row in the id generation table, tableId="+tableId);
        }
        return getLong(resultSet.get(0), highValueColumnName);
    }

    private void insertZero(String tableId) {
        int rowCount=executeUpdateSql(jdbcTemplate, insertSql, newArrayList(tableId));
        if (rowCount!=1) {
            throw new RuntimeException("rowCount="+rowCount);
        }
    }

    private void testId(long id) {
        if (id>=maxHi || id<0) {
            throw new IllegalStateException("idValue is out of range, idValue="+id+", maxHi="+maxHi);
        }
    }

}
