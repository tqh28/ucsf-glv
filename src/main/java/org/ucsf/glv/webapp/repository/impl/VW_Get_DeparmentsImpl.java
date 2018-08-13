package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.VW_Get_Deparments;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_Get_DeparmentsImpl implements VW_Get_Deparments {
    
    @Inject
    private ConvertData converData;

    @Override
    public List<HashMap<String, Object>> getListRollUpByDeptId(Connection connection, String deptId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT DeptTreeTitleAbbrev, DeptCd ")
                .append("FROM vw_Get_Deparments ")
                .append("WHERE DeptLevel1Cd LIKE ? OR DeptLevel2Cd LIKE ? ")
                .append("OR DeptLevel3Cd LIKE ? OR DeptLevel4Cd LIKE ? ")
                .append("OR DeptLevel5Cd LIKE ? OR DeptLevel6Cd LIKE ? ")
                .append("ORDER BY DeptTreeCd");
        
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, deptId);
        preparedStatement.setString(2, deptId);
        preparedStatement.setString(3, deptId);
        preparedStatement.setString(4, deptId);
        preparedStatement.setString(5, deptId);
        preparedStatement.setString(6, deptId);
        ResultSet rs = preparedStatement.executeQuery();
        
        List<HashMap<String, Object>> result = converData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }
}
