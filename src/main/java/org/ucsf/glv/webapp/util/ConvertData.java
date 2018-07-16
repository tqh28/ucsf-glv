package org.ucsf.glv.webapp.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class ConvertData {

    /**
     * @param rs
     * @return result set in json mode
     */
    public String convertResultSetToJson(ResultSet rs) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(convertResultSetToListHashMap(rs));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public List<HashMap<String, Object>> convertResultSetToListHashMap(ResultSet rs) {
        List<HashMap<String, Object>> returnList = new LinkedList<HashMap<String, Object>>();
        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                int numColumns = rsmd.getColumnCount();
                HashMap<String, Object> obj = new HashMap<String, Object>();
                for (int i = 1; i <= numColumns; i++) {
                    String columnName = rsmd.getColumnName(i);
                    obj.put(columnName, rs.getObject(columnName));
                }
                returnList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * @param rs
     * @return the number of records in select count(*) query
     */
    public int getNumberOfSelectCountQuery(ResultSet rs) {
        ResultSetMetaData rsmd;
        try {
            rsmd = rs.getMetaData();
            if (rs.next()) {
                String columnName = rsmd.getColumnName(1);
                return rs.getInt(columnName);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
}
