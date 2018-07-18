package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_SavedChartFieldFilters;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class SOM_BFA_SavedChartFieldFiltersImpl implements SOM_BFA_SavedChartFieldFilters {

    @Inject
    private Jdbc jdbc;
    
    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getFilterList(String userId, String deptId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT IIF([integer] = 1, '(no filter)', '(default)') AS FilterName0 ")
                .append("FROM zIntegers ")
                .append("WHERE Integer IN (2) ")
                .append("UNION ALL ")
                .append("SELECT FilterName ")
                .append("FROM SOM_BFA_SavedChartFieldFilters ")
                .append("WHERE ((UserId = ?) AND (DeptCdSaved = ?) AND (FilterName NOT IN ('(default)', '(working)')) AND (ChartStrField = 'DeptCdSaved')) ")
                .append("GROUP BY FilterName");
        
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql.toString());
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, deptId);
        
        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        
        rs.close();
        preparedStatement.close();
        
        return result;
    }

    @Override
    public List<HashMap<String, Object>> getDataForDllFilter(String filterId, String userId, String deptId)
            throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ChartStrField, ChartStrValue, \"Except\" ")
                .append("FROM SOM_BFA_SavedChartFieldFilters ")
                .append("WHERE UserId = ? AND DeptCdSaved = ? AND FilterName = ?");
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql.toString());
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, deptId);
        preparedStatement.setString(3, filterId);

        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        preparedStatement.close();

        return result;
    }

}
