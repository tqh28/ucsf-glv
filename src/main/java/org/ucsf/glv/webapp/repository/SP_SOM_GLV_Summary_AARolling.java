package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface SP_SOM_GLV_Summary_AARolling {

    public void execute(Connection connection, String sessionUserId, String deptCdGLV, String deptCdOverride,
            String businessUnitCd, String deptSite, String userId, String filtername, int fy, int fp, int withEmp)
            throws SQLException;

}
