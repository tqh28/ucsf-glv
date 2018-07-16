package org.ucsf.glv.webapp.repository.glvhome;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface HomeRepo {

    public List<HashMap<String, Object>> getControlPointList() throws SQLException;

}
