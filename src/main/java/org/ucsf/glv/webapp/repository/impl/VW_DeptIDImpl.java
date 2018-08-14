package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.VW_DeptID;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_DeptIDImpl implements VW_DeptID {
    
    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getByDeptCdAndFyAndFp(Connection connection, String deptCd, int fy, int fp) throws SQLException {
        String query = "SELECT * FROM vw_DeptID WHERE DeptCd = ? AND FY = ? and FP = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, deptCd);
        preparedStatement.setInt(2, fy);
        preparedStatement.setInt(3, fp);
        
        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        
        return result;
    }

    
}
