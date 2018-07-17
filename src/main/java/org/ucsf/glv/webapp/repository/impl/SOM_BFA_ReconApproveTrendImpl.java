package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconApproveTrend;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class SOM_BFA_ReconApproveTrendImpl implements SOM_BFA_ReconApproveTrend {

    @Inject
    private Jdbc jdbc;
    
    @Inject
    private ConvertData convertData;

    @Override
    public int getMonthlyTrendPercent(String deptId, int fy, int fp) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT SOM_BFA_ReconApproveTrend.FiscalYear, SOM_BFA_ReconApproveTrend.FiscalPeriod, SUM(SOM_BFA_ReconApproveTrend.CheckedState) AS Verified ")
                        .append("FROM SOM_BFA_ReconApproveTrend ")
                        .append("WHERE SOM_BFA_ReconApproveTrend.ReconDeptCd = ? AND SOM_BFA_ReconApproveTrend.DeptTreeCd LIKE ? AND SOM_BFA_ReconApproveTrend.DeptPostingLevel = 'Y' ")
                        .append("GROUP BY SOM_BFA_ReconApproveTrend.FiscalYear, SOM_BFA_ReconApproveTrend.FiscalPeriod ")
                        .append("HAVING SOM_BFA_ReconApproveTrend.FiscalYear = ? AND SOM_BFA_ReconApproveTrend.FiscalPeriod = ?");
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql.toString());
        preparedStatement.setString(1, deptId);
        preparedStatement.setString(2, "%" + deptId + "%");
        preparedStatement.setInt(3, fy);
        preparedStatement.setInt(4, fp);
        ResultSet rs = preparedStatement.executeQuery();
        
        Object obj = convertData.getObjectByColumnNameFromResultSet(rs, "Verified");
        rs.close();
        preparedStatement.close();
        if (obj != null) {
            int verified = (int) obj;
            return 100 - verified;
        } else {
            return 100;
        }
    }

}
