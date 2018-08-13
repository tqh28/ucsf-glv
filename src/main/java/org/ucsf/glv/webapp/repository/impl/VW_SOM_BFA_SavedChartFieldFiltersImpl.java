package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.ucsf.glv.webapp.repository.VW_SOM_BFA_SavedChartFieldFilters;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_SOM_BFA_SavedChartFieldFiltersImpl implements VW_SOM_BFA_SavedChartFieldFilters {
    
    @Inject
    private ConvertData converData;

    @Override
    public List<HashMap<String, Object>> getDataForGetWhereSavedFilter(Connection connection, String userId, String filterName, String deptCdSaved,
            String deptCdOverride) throws SQLException {
        if (deptCdOverride == null || deptCdOverride == "") {
            return new LinkedList<HashMap<String,Object>>();
        }
        String sql = "SELECT ChartStrField, ChartStrValue, [Except], DeptLevel, FundLevel " + 
                "            FROM vw_SOM_BFA_SavedChartFieldFilters " + 
                "            WHERE UserId = ? AND FilterName = ? AND DeptCdSaved= ? AND ChartStrField <> 'DeptCdSaved'" + 
                "            AND ChartStrField <> CASE WHEN " + deptCdOverride + " IS NULL THEN 'xxxxx' ELSE 'DeptCd' END" + 
                "            ORDER BY ChartStrField, DeptLevel, FundLevel ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.setString(2, filterName);
        preparedStatement.setString(3, deptCdSaved);
        
        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = converData.convertResultSetToListHashMap(rs);
        
        rs.close();
        preparedStatement.close();
        return result;
    }

}
