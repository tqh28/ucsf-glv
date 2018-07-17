package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;

public interface SOM_BFA_ReconApproveTrend {

    public int getMonthlyTrendPercent(String deptId, int fy, int fp) throws SQLException;
}
