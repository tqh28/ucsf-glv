package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class SOM_BFA_UserPreferencesImpl implements SOM_BFA_UserPreferences {
    
    @Inject
    private ConvertData convertData;
    
    @Override
    public List<HashMap<String, Object>> getPreferenceByUserId(Connection connection, String userId) throws SQLException {
        String sql = "SELECT Preference, String FROM SOM_BFA_UserPreferences WHERE UserId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public String getDefaultDeptIdByUserId(Connection connection, String userId) throws SQLException {
        String sql = "SELECT string FROM SOM_BFA_UserPreferences WHERE UserId= ? AND Preference= 'Default Deptid'";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        String defaultDeptId = (String) convertData.getObjectByColumnNameFromResultSet(rs, "string");
        rs.close();
        preparedStatement.close();
        return defaultDeptId;
    }

}
