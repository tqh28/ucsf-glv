package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;

public interface SP_SOM_GLV_Summary_AARolling {

    public void execute(String sessionUserId, String deptCd, String depcdOverride, String bu, String site, String filtername,
            String userId, int fy, int fp, int withEmp) throws SQLException;

}
