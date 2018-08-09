package org.ucsf.glv.webapp.repository.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.FN_SOM_BFA_GetWhereFromSavedFilter;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Departments;
import org.ucsf.glv.webapp.repository.VW_SOM_BFA_SavedChartFieldFilters;

import com.google.inject.Inject;

public class FN_SOM_BFA_GetWhereFromSavedFilterImpl implements FN_SOM_BFA_GetWhereFromSavedFilter {

    @Inject
    private VW_COA_SOM_Departments departments;

    @Inject
    private VW_SOM_BFA_SavedChartFieldFilters savedChartFieldFilters;

    @Override
    public String execute(String vUserId, String vDeptCdSaved, String vDeptCdOverride, String vFilterName,
            int vReturnType) throws SQLException {

        String vSQLDeptCdSaved;
        Integer vDeptCdSavedLevel= null;
        Integer vDeptCdOverrideLevel = null;

        String vSQLDept;
        String vSQLDeptSite;
        String vSQLFund;
        String vSQLProjectManagerCd;
        String vSQLProjectUseShort;
        String vSQLProjectCd;
        String vSQLFunctionCd;
        String vSQLFlexCd;

        String vSQLDeptX;
        String vSQLDeptSiteX;
        String vSQLFundX;
        String vSQLProjectManagerCdX;
        String vSQLProjectUseShortX;
        String vSQLProjectCdX;
        String vSQLFunctionCdX;
        String vSQLFlexCdX;

        String vSQL;

        String vChartStrField;
        String vChartStrValue;
        String vExcept;
        int vDeptLevel;
        int vFundLevel;

        String vListDeptCd1 = "";
        String vListDeptCd2 = "";
        String vListDeptCd3 = "";
        String vListDeptCd4 = "";
        String vListDeptCd5 = "";
        String vListDeptCd6 = "";
        String vListDeptSite = "";
        String vListFundCd1 = "";
        String vListFundCd2 = "";
        String vListFundCd3 = "";
        String vListFundCd4 = "";
        String vListFundCd = "";
        String vListProjectManagerCd = "";
        String vListProjectUseShort = "";
        String vListProjectCd = "";
        String vListFunctionCd = "";
        String vListFlexCd = "";

        String vListXDeptCd1 = "";
        String vListXDeptCd2 = "";
        String vListXDeptCd3 = "";
        String vListXDeptCd4 = "";
        String vListXDeptCd5 = "";
        String vListXDeptCd6 = "";
        String vListXDeptSite = "";
        String vListXFundCd1 = "";
        String vListXFundCd2 = "";
        String vListXFundCd3 = "";
        String vListXFundCd4 = "";
        String vListXFundCd = "";
        String vListXProjectManagerCd = "";
        String vListXProjectUseShort = "";
        String vListXProjectCd = "";
        String vListXFunctionCd = "";
        String vListXFlexCd = "";

        vDeptCdSavedLevel = departments.getDeptLevelByDeptCd(vDeptCdSaved);

        if (vDeptCdOverride != null) {
            vDeptCdOverrideLevel = departments.getDeptLevelByDeptCd(vDeptCdOverride);
        }

        List<HashMap<String, Object>> savedFilters = savedChartFieldFilters.getDataForGetWhereSavedFilter(vUserId,
                vFilterName, vDeptCdSaved, vDeptCdOverride);
        for (HashMap<String, Object> filter : savedFilters) {
            vChartStrField = (String) filter.get("ChartStrField");
            vChartStrValue = (String) filter.get("ChartStrValue");
            vExcept = (String) filter.get("Except");
            vDeptLevel = (int) filter.get("DeptLevel");
            vFundLevel = (int) filter.get("FundLevel");
            if (vChartStrField.equals("DeptCd") && vExcept.equals("+")) {
                if (vDeptLevel == 1) {
                    vListDeptCd1 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 2) {
                    vListDeptCd2 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 3) {
                    vListDeptCd3 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 4) {
                    vListDeptCd4 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 5) {
                    vListDeptCd5 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 6) {
                    vListDeptCd6 += "~'" + vChartStrValue + "'";
                }
            } else if (vChartStrField.equals("DeptSite") && vExcept.equals("+")) {
                vListDeptSite += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("FundCd") && vExcept.equals("+")) {
                if (vFundLevel == 1) {
                    vListFundCd1 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel == 2) {
                    vListFundCd2 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel == 3) { 
                    vListFundCd3 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel == 4) {
                    vListFundCd4 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel > 4) {
                    vListFundCd += "~'" + vChartStrValue + "'";
                }
            } else if (vChartStrField.equals("ProjectManagerCd") && vExcept.equals("+")) {
                String tmpValue = vChartStrValue;
                if (vChartStrValue.contains("(")) {
                    tmpValue = tmpValue.substring(tmpValue.length() - 10, tmpValue.length());
                    tmpValue = tmpValue.substring(0, 9);
                }
                vListProjectManagerCd += "~'" + tmpValue + "'";
            } else if (vChartStrField.equals("ProjectUseShort") && vExcept.equals("+")) {
                vListProjectUseShort += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("ProjectCd") && vExcept.equals("+")) {
                vListProjectCd += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("FunctionCd") && vExcept.equals("+")) {
                vListFunctionCd += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("FlexCd") && vExcept.equals("+")) {
                vListFlexCd += "~'" + vChartStrValue + "'";
            }

            if (vChartStrField.equals("DeptCd") && vExcept.equals("-")) {
                if (vDeptLevel == 1) {
                    vListXDeptCd1 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 2) {
                    vListXDeptCd2 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 3) {
                    vListXDeptCd3 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 4) {
                    vListXDeptCd4 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 5) {
                    vListXDeptCd5 += "~'" + vChartStrValue + "'";
                } else if (vDeptLevel == 6) {
                    vListXDeptCd6 += "~'" + vChartStrValue + "'";
                }
            } else if (vChartStrField.equals("DeptSite") && vExcept.equals("+")) {
                vListXDeptSite += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("FundCd") && vExcept.equals("+")) {
                if (vFundLevel == 1) {
                    vListXFundCd1 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel == 2) {
                    vListXFundCd2 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel == 3) {
                    vListXFundCd3 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel == 4) {
                    vListXFundCd4 += "~'" + vChartStrValue + "'";
                } else if (vFundLevel > 4) {
                    vListXFundCd += "~'" + vChartStrValue + "'";
                }
            } else if (vChartStrField.equals("ProjectManagerCd") && vExcept.equals("+")) {
                String tmpValue = vChartStrValue;
                if (vChartStrValue.contains("(")) {
                    tmpValue = tmpValue.substring(tmpValue.length() - 10, tmpValue.length());
                    tmpValue = tmpValue.substring(0, 9);
                }
                vListXProjectManagerCd += "~'" + tmpValue + "'";
            } else if (vChartStrField.equals("ProjectUseShort") && vExcept.equals("+")) {
                vListXProjectUseShort += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("ProjectCd") && vExcept.equals("+")) {
                vListXProjectCd += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("FunctionCd") && vExcept.equals("+")) {
                vListXFunctionCd += "~'" + vChartStrValue + "'";
            } else if (vChartStrField.equals("FlexCd") && vExcept.equals("+")) {
                vListXFlexCd += "~'" + vChartStrValue + "'";
            }
        }

        if (vReturnType != 0) {
            vSQL = "";
            if (vDeptCdOverride == null) {
                if (vListDeptCd1 != "") {
                    vSQL += "~DeptLevel1Cd" + vListDeptCd1;
                }
                if (vListDeptCd2 != "") {
                    vSQL += "~DeptLevel2Cd" + vListDeptCd2;
                }
                if (vListDeptCd3 != "") {
                    vSQL += "~DeptLevel3Cd" + vListDeptCd3;
                }
                if (vListDeptCd4 != "") {
                    vSQL += "~DeptLevel4Cd" + vListDeptCd4;
                }
                if (vListDeptCd5 != "") {
                    vSQL += "~DeptLevel5Cd" + vListDeptCd5;
                }
                if (vListDeptCd6 != "") {
                    vSQL += "~DeptLevel6Cd" + vListDeptCd6;
                }
            } else {
                if (vDeptCdOverride != "" && vDeptCdOverrideLevel != null) {
                    // add check vDeptCdOverrideLevel != null
                    // because if vDeptCdOverrideLevel == null, vSQL will become not found 'DeptLevelnull' column
                    vSQL += "~DeptLevel" + vDeptCdOverrideLevel + "Cd~" + vDeptCdOverride;
                }
            }

            if (vListDeptSite != "") {
                vSQL += "~DeptSite" + vListDeptSite;
            }
            if (vListFundCd1 != "") {
                vSQL += "~FundLevelACd" + vListFundCd1;
            }
            if (vListFundCd2 != "") {
                vSQL += "~FundLevelBCd" + vListFundCd2;
            }
            if (vListFundCd3 != "") {
                vSQL += "~FundLevelCCd" + vListFundCd3;
            }
            if (vListFundCd4 != "") {
                vSQL += "~FundLevelDCd" + vListFundCd4;
            }
            if (vListFundCd != "") {
                vSQL += "~FundCd" + vListFundCd;
            }
            if (vListProjectManagerCd != "") {
                vSQL += "~ProjectManagerCd" + vListProjectManagerCd;
            }
            if (vListProjectUseShort != "") {
                vSQL += "~ProjectUseShort" + vListProjectUseShort;
            }
            if (vListProjectCd != "") {
                vSQL += "~ProjectCd" + vListProjectCd;
            }
            if (vListFunctionCd != "") {
                vSQL += "~FunctionCd" + vListFunctionCd;
            }
            if (vListFlexCd != "") {
                vSQL += "~FlexCd" + vListFlexCd;
            }

            return vSQL.replace("'", "");
        }

        /**
         * build SQL parts
         */

        // dept cd saved
        if (vDeptCdOverride == null) {
            if (vDeptCdSavedLevel != null) {
                // add check vDeptCdOverrideLevel != null
                // because if vDeptCdOverrideLevel == null, vSQLDeptCdSaved will become not found 'DeptLevelnullCd...' column
                vSQLDeptCdSaved = "(DeptLevel" + vDeptCdSavedLevel + "Cd='" + vDeptCdSaved + "')";
            }
            else
                vSQLDeptCdSaved = "";
        } else {
            if (vDeptCdOverrideLevel != null) {
                // add check vDeptCdOverrideLevel != null
                // because if vDeptCdOverrideLevel == null, vSQLDeptCdSaved will become not found 'nullCd...' column
                vSQLDeptCdSaved = "(DeptLevel" + vDeptCdOverrideLevel + "Cd='" + vDeptCdOverride + "')";
            }
            else
                vSQLDeptCdSaved = "";
        }

        // deptCds
        vSQLDept = "(";
        if (vListDeptCd1.length() - vListDeptCd1.replaceAll("~", "").length() == 1) {
            vSQLDept += " OR DeptLevel1Cd = " + vListDeptCd1.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptCd1 != "") {
            vSQLDept += " OR DeptLevel1Cd IN (" + vListDeptCd1.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListDeptCd2.length() - vListDeptCd2.replaceAll("~", "").length() == 1) {
            vSQLDept += " OR DeptLevel2Cd = " + vListDeptCd2.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptCd2 != "") {
            vSQLDept += " OR DeptLevel2Cd IN (" + vListDeptCd2.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListDeptCd3.length() - vListDeptCd3.replaceAll("~", "").length() == 1) {
            vSQLDept += " OR DeptLevel3Cd = " + vListDeptCd3.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptCd3 != "") {
            vSQLDept += " OR DeptLevel3Cd IN (" + vListDeptCd3.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListDeptCd4.length() - vListDeptCd4.replaceAll("~", "").length() == 1) {
            vSQLDept += " OR DeptLevel4Cd = " + vListDeptCd4.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptCd4 != "") {
            vSQLDept += " OR DeptLevel4Cd IN (" + vListDeptCd4.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListDeptCd5.length() - vListDeptCd5.replaceAll("~", "").length() == 1) {
            vSQLDept += " OR DeptLevel5Cd = " + vListDeptCd5.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptCd5 != "") {
            vSQLDept += " OR DeptLevel5Cd IN (" + vListDeptCd5.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListDeptCd6.length() - vListDeptCd6.replaceAll("~", "").length() == 1) {
            vSQLDept += " OR DeptLevel6Cd = " + vListDeptCd6.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptCd6 != "") {
            vSQLDept += " OR DeptLevel6Cd IN (" + vListDeptCd6.substring(1, 513).replaceAll("~", ",") + ")";
        }

        vSQLDept += ")";
        vSQLDept = vSQLDept.replaceAll("[(] OR Dept", "(Dept");

        vSQLDeptX = "(";
        if (vListXDeptCd1.length() - vListXDeptCd1.replaceAll("~", "").length() == 1) {
            vSQLDeptX += " OR DeptLevel1Cd = " + vListXDeptCd1.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptCd1 != "") {
            vSQLDeptX += " OR DeptLevel1Cd IN (" + vListXDeptCd1.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListXDeptCd2.length() - vListXDeptCd2.replaceAll("~", "").length() == 1) {
            vSQLDeptX += " OR DeptLevel2Cd = " + vListXDeptCd2.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptCd2 != "") {
            vSQLDeptX += " OR DeptLevel2Cd IN (" + vListXDeptCd2.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListXDeptCd3.length() - vListXDeptCd3.replaceAll("~", "").length() == 1) {
            vSQLDeptX += " OR DeptLevel3Cd = " + vListXDeptCd3.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptCd3 != "") {
            vSQLDeptX += " OR DeptLevel3Cd IN (" + vListXDeptCd3.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListXDeptCd4.length() - vListXDeptCd4.replaceAll("~", "").length() == 1) {
            vSQLDeptX += " OR DeptLevel4Cd = " + vListXDeptCd4.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptCd4 != "") {
            vSQLDeptX += " OR DeptLevel4Cd IN (" + vListXDeptCd4.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListXDeptCd5.length() - vListXDeptCd5.replaceAll("~", "").length() == 1) {
            vSQLDeptX += " OR DeptLevel5Cd = " + vListXDeptCd5.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptCd5 != "") {
            vSQLDeptX += " OR DeptLevel5Cd IN (" + vListXDeptCd5.substring(1, 513).replaceAll("~", ",") + ")";
        }

        if (vListXDeptCd6.length() - vListXDeptCd6.replaceAll("~", "").length() == 1) {
            vSQLDeptX += " OR DeptLevel6Cd = " + vListXDeptCd6.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptCd6 != "") {
            vSQLDeptX += " OR DeptLevel6Cd IN (" + vListXDeptCd6.substring(1, 513).replaceAll("~", ",") + ")";
        }

        vSQLDeptX += ")";
        vSQLDeptX = vSQLDeptX.replaceAll("[(] OR Dept", "(Dept");

        // dept site
        vSQLDeptSite = "(";
        if (vListDeptSite.length() - vListDeptSite.replaceAll("~", "").length() == 1) {
            vSQLDeptSite += " AND DeptSite = " + vListDeptSite.substring(1, 513).replaceAll("~", ",");
        } else if (vListDeptSite != "") {
            vSQLDeptSite += " AND DeptSite IN (" + vListDeptSite.substring(1, 513).replaceAll("~", ",") + ")";
        }
        vSQLDeptSite += ")";

        vSQLDeptSiteX = "(";
        if (vListXDeptSite.length() - vListXDeptSite.replaceAll("~", "").length() == 1) {
            vSQLDeptSiteX += " AND DeptSite = " + vListXDeptSite.substring(1, 513).replaceAll("~", ",");
        } else if (vListXDeptSite != "") {
            vSQLDeptSiteX += " AND DeptSite IN (" + vListXDeptSite.substring(1, 513).replaceAll("~", ",") + ")";
        }
        vSQLDeptSiteX += ")";

        // fund
        vSQLFund = "(";
        if (vListFundCd1.length() - vListFundCd1.replaceAll("~", "").length() == 1) {
            vSQLFund += " OR FundLevelACd = " + vListFundCd1.substring(1, 513).replaceAll("~", "");
        } else if (vListFundCd1 != "") {
            vSQLFund += " OR FundLevel1Cd IN (" + vListFundCd1.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListFundCd2.length() - vListFundCd2.replaceAll("~", "").length() == 1) {
            vSQLFund += " OR FundLevelBCd = " + vListFundCd2.substring(1, 513).replaceAll("~", "");
        } else if (vListFundCd2 != "") {
            vSQLFund += " OR FundLevel2Cd IN (" + vListFundCd2.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListFundCd3.length() - vListFundCd3.replaceAll("~", "").length() == 1) {
            vSQLFund += " OR FundLevelCCd = " + vListFundCd3.substring(1, 513).replaceAll("~", "");
        } else if (vListFundCd3 != "") {
            vSQLFund += " OR FundLevel3Cd IN (" + vListFundCd3.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListFundCd4.length() - vListFundCd4.replaceAll("~", "").length() == 1) {
            vSQLFund += " OR FundLevelDCd = " + vListFundCd4.substring(1, 513).replaceAll("~", "");
        } else if (vListFundCd4 != "") {
            vSQLFund += " OR FundLevel4Cd IN (" + vListFundCd4.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListFundCd.length() - vListFundCd.replaceAll("~", "").length() == 1) {
            vSQLFund += " OR FundCd = " + vListFundCd.substring(1, 513).replaceAll("~", "");
        } else if (vListFundCd != "") {
            vSQLFund += " OR FundCd IN (" + vListFundCd.substring(1, 513).replaceAll("~", "") + ")";
        }
        
        vSQLFund += ")";

        vSQLFundX = "(";
        if (vListXFundCd1.length() - vListXFundCd1.replaceAll("~", "").length() == 1) {
            vSQLFundX += " OR FundLevelACd = " + vListXFundCd1.substring(1, 513).replaceAll("~", "");
        } else if (vListXFundCd1 != "") {
            vSQLFundX += " OR FundLevel1Cd IN (" + vListXFundCd1.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListXFundCd2.length() - vListXFundCd2.replaceAll("~", "").length() == 1) {
            vSQLFundX += " OR FundLevelBCd = " + vListXFundCd2.substring(1, 513).replaceAll("~", "");
        } else if (vListXFundCd2 != "") {
            vSQLFundX += " OR FundLevel2Cd IN (" + vListXFundCd2.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListXFundCd3.length() - vListXFundCd3.replaceAll("~", "").length() == 1) {
            vSQLFundX += " OR FundLevelCCd = " + vListXFundCd3.substring(1, 513).replaceAll("~", "");
        } else if (vListXFundCd3 != "") {
            vSQLFundX += " OR FundLevel3Cd IN (" + vListXFundCd3.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListXFundCd4.length() - vListXFundCd4.replaceAll("~", "").length() == 1) {
            vSQLFundX += " OR FundLevelDCd = " + vListXFundCd4.substring(1, 513).replaceAll("~", "");
        } else if (vListXFundCd4 != "") {
            vSQLFundX += " OR FundLevel4Cd IN (" + vListXFundCd4.substring(1, 513).replaceAll("~", "") + ")";
        }

        if (vListXFundCd.length() - vListXFundCd.replaceAll("~", "").length() == 1) {
            vSQLFundX += " OR FundCd = " + vListXFundCd.substring(1, 513).replaceAll("~", "");
        } else if (vListXFundCd != "") {
            vSQLFundX += " OR FundCd IN (" + vListXFundCd.substring(1, 513).replaceAll("~", "") + ")";
        }
        
        vSQLFundX += ")";

        // project manager cd
        vSQLProjectManagerCd = "(";
        if (vListProjectManagerCd.length() - vListProjectManagerCd.replace("~", "").length() == 1) {
            vSQLProjectManagerCd = " AND ProjectManagerCd = "
                    + vListProjectManagerCd.substring(1, 513).replace("~", "");
        } else if (vListProjectManagerCd != "") {
            vSQLProjectManagerCd = " AND ProjectManagerCd IN ("
                    + vListProjectManagerCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLProjectManagerCd += ")";

        vSQLProjectManagerCdX = "(";
        if (vListXProjectManagerCd.length() - vListXProjectManagerCd.replace("~", "").length() == 1) {
            vSQLProjectManagerCdX = " AND ProjectManagerCd = "
                    + vListXProjectManagerCd.substring(1, 513).replace("~", "");
        } else if (vListXProjectManagerCd != "") {
            vSQLProjectManagerCdX = " AND ProjectManagerCd IN ("
                    + vListXProjectManagerCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLProjectManagerCdX += ")";

        // project use short
        vSQLProjectUseShort = "(";
        if (vListProjectUseShort.length() - vListProjectUseShort.replace("~", "").length() == 1) {
            vSQLProjectUseShort = " AND ProjectUseShort = " + vListProjectUseShort.substring(1, 513).replace("~", "");
        } else if (vListProjectUseShort != "") {
            vSQLProjectUseShort = " AND ProjectUseShort IN (" + vListProjectUseShort.substring(1, 513).replace("~", "")
                    + ")";
        }
        vSQLProjectUseShort += ")";

        vSQLProjectUseShortX = "(";
        if (vListXProjectUseShort.length() - vListXProjectUseShort.replace("~", "").length() == 1) {
            vSQLProjectUseShortX = " AND ProjectUseShort = " + vListXProjectUseShort.substring(1, 513).replace("~", "");
        } else if (vListXProjectUseShort != "") {
            vSQLProjectUseShortX = " AND ProjectUseShort IN ("
                    + vListXProjectUseShort.substring(1, 513).replace("~", "") + ")";
        }
        vSQLProjectUseShortX += ")";

        // project cd
        vSQLProjectCd = "(";
        if (vListProjectCd.length() - vListProjectCd.replace("~", "").length() == 1) {
            vSQLProjectCd = " AND ProjectCd = " + vListProjectCd.substring(1, 513).replace("~", "");
        } else if (vListProjectCd != "") {
            vSQLProjectCd = " AND ProjectCd IN (" + vListProjectCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLProjectCd += ")";

        vSQLProjectCdX = "(";
        if (vListXProjectCd.length() - vListXProjectCd.replace("~", "").length() == 1) {
            vSQLProjectCdX = " AND ProjectCd = " + vListXProjectCd.substring(1, 513).replace("~", "");
        } else if (vListXProjectCd != "") {
            vSQLProjectCdX = " AND ProjectCd IN (" + vListXProjectCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLProjectCdX += ")";

        // function cd
        vSQLFunctionCd = "(";
        if (vListFunctionCd.length() - vListFunctionCd.replace("~", "").length() == 1) {
            vSQLFunctionCd = " AND FunctionCd = " + vListFunctionCd.substring(1, 513).replace("~", "");
        } else if (vListFunctionCd != "") {
            vSQLFunctionCd = " AND FunctionCd IN (" + vListFunctionCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLFunctionCd += ")";

        vSQLFunctionCdX = "(";
        if (vListXFunctionCd.length() - vListXFunctionCd.replace("~", "").length() == 1) {
            vSQLFunctionCdX = " AND FunctionCd = " + vListXFunctionCd.substring(1, 513).replace("~", "");
        } else if (vListXFunctionCd != "") {
            vSQLFunctionCdX = " AND FunctionCd IN (" + vListXFunctionCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLFunctionCdX += ")";

        // flex cd
        vSQLFlexCd = "(";
        if (vListFlexCd.length() - vListFlexCd.replace("~", "").length() == 1) {
            vSQLFlexCd = " AND FunctionCd = " + vListFlexCd.substring(1, 513).replace("~", "");
        } else if (vListFlexCd != "") {
            vSQLFlexCd = " AND FunctionCd IN (" + vListFlexCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLFlexCd += ")";

        vSQLFlexCdX = "(";
        if (vListXFlexCd.length() - vListXFlexCd.replace("~", "").length() == 1) {
            vSQLFlexCdX = " AND FunctionCd = " + vListXFlexCd.substring(1, 513).replace("~", "");
        } else if (vListXFlexCd != "") {
            vSQLFlexCdX = " AND FunctionCd IN (" + vListXFlexCd.substring(1, 513).replace("~", "") + ")";
        }
        vSQLFlexCdX += ")";

        vSQL = "1=1 AND " + (vSQLDept.equals("()") ? vSQLDeptCdSaved : vSQLDept) + " AND NOT " + vSQLDeptX + " AND "
                + vSQLDeptSite + " AND NOT " + vSQLDeptSiteX + " AND " + vSQLFund + " AND NOT " + vSQLFundX + " AND "
                + vSQLProjectManagerCd + " AND NOT " + vSQLProjectManagerCdX + " AND " + vSQLProjectUseShort
                + " AND NOT " + vSQLProjectUseShortX + " AND " + vSQLProjectCd + " AND NOT " + vSQLProjectCdX + " AND "
                + vSQLFunctionCd + " AND NOT " + vSQLFunctionCdX + " AND " + vSQLFlexCd + " AND NOT " + vSQLFlexCdX;

        vSQL = vSQL.replaceAll(" AND [(][)] AND NOT [(][)]", "").replaceAll(" AND [(][)] AND NOT [(]", " AND NOT (")
                .replaceAll("AND NOT [(][)]", "").replaceAll("[(] AND ", "(").replaceAll("1=1 AND", "");

        return vSQL;
    }

}
