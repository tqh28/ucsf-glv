package org.ucsf.glv.webapp.repository.impl;

import java.sql.SQLException;

import org.ucsf.glv.webapp.repository.SOM_AA_Dashboard;
import org.ucsf.glv.webapp.repository.SOM_AA_EmployeeListRolling;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;
import org.ucsf.glv.webapp.repository.SP_SOM_GLV_Summary_AARolling;

import com.google.inject.Inject;

public class SP_SOM_GLV_Summary_AARollingImpl implements SP_SOM_GLV_Summary_AARolling {
    
    @Inject
    private SOM_AA_Dashboard dashboard;
    
    @Inject
    private SOM_AA_TransactionSummary transactionSummary;
    
    @Inject
    private SOM_AA_EmployeeListRolling employeeListRolling;

    @Override
    public void execute(String sessionUserId, String deptCd, String depcdOverride, String bu, String site, String filtername,
            String userId, int fy, int fp, int withEmp) throws SQLException {
        dashboard.deleteBySessionUserId(sessionUserId);
        transactionSummary.deleteBySessionUserId(sessionUserId);
        employeeListRolling.deleteBySessionUserId(sessionUserId);
    }

}
