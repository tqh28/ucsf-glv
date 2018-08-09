package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;

public interface FN_SOM_BFA_GetWhereFromSavedFilter {

    public String execute(String vUserId, String vDeptCdSaved, String vDeptCdOverride, String vFilterName,
            int vReturnType) throws SQLException;

}
