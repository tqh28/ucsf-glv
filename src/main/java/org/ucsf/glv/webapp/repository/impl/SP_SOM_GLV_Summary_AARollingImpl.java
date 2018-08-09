package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.FN_SOM_BFA_GetWhereFromSavedFilter;
import org.ucsf.glv.webapp.repository.SOM_AA_Dashboard;
import org.ucsf.glv.webapp.repository.SOM_AA_EmployeeListRolling;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;
import org.ucsf.glv.webapp.repository.SP_SOM_GLV_Summary_AARolling;

import com.google.inject.Inject;

public class SP_SOM_GLV_Summary_AARollingImpl implements SP_SOM_GLV_Summary_AARolling {
    
    @Inject
    private Jdbc jdbc;

    @Inject
    private SOM_AA_Dashboard dashboard;

    @Inject
    private SOM_AA_TransactionSummary transactionSummary;

    @Inject
    private SOM_AA_EmployeeListRolling employeeListRolling;
    
    @Inject
    private FN_SOM_BFA_GetWhereFromSavedFilter getWhereFromSavedFilter;

    @Override
    public void execute(String sessionUserId, String deptCdGLV, String deptCdOverride, String businessUnitCd,
            String deptSite, String userId, String filtername, int fy, int fp, int withEmp) throws SQLException {
        dashboard.deleteBySessionUserId(sessionUserId);
        transactionSummary.deleteBySessionUserId(sessionUserId);
        employeeListRolling.deleteBySessionUserId(sessionUserId);

        if (deptSite.equals("*")) {
            deptSite = "%";
        }

        String createTemp1Sql = "create table temp1 ( FiscalYear smallint, FiscalPeriod smallint,"
                + " ReconGroupTitle varchar(20), ReconItemCd smallint, ReconItemTitle varchar(50),"
                + " ReconAssignCd smallint, ReconStatusCd smallint, ReconStatusTitle varchar(50),"
                + " Amt money, Cnt Integer)";
        
        String createTemp2Sql = "create table temp2 (" + 
                "        FiscalYear smallint," + 
                "        ReconGroupTitle varchar(20)," + 
                "        ReconItemTitle varchar(50)," + 
                "        ReconItemCd smallint," + 
                "        StatusAmt0 money," + 
                "        StatusCnt0 Integer,   " + 
                "        StatusAmt1 money," + 
                "        StatusCnt1 Integer,   " + 
                "        StatusAmt2 money," + 
                "        StatusCnt2 Integer,   " + 
                "        StatusAmt3 money," + 
                "        StatusCnt3 Integer,   " + 
                "        PriorStatusAmt0 money," + 
                "        PriorStatusCnt0 Integer,  " + 
                "        PriorStatusAmt1 money," + 
                "        PriorStatusCnt1 Integer,  " + 
                "        AmtM01 money, " + 
                "        AmtM02 money, " + 
                "        AmtM03 money, " + 
                "        AmtM04 money, " + 
                "        AmtM05 money, " + 
                "        AmtM06 money, " + 
                "        AmtM07 money, " + 
                "        AmtM08 money, " + 
                "        AmtM09 money, " + 
                "        AmtM10 money, " + 
                "        AmtM11 money, " + 
                "        AmtM12 money, " + 
                "        IncM01 integer, " + 
                "        IncM02 integer, " + 
                "        IncM03 integer, " + 
                "        IncM04 integer, " + 
                "        IncM05 integer, " + 
                "        IncM06 integer, " + 
                "        IncM07 integer, " + 
                "        IncM08 integer, " + 
                "        IncM09 integer, " + 
                "        IncM10 integer, " + 
                "        IncM11 integer, " + 
                "        IncM12 integer " + 
                "        )";
        
        Statement statement = jdbc.getStatement();
//        statement.execute(createTemp1Sql);
//        statement.execute(createTemp2Sql);
        
        
        /**
         * Insert into temp1
         */
        String whereCondition = getWhereFromSavedFilter.execute(userId, deptCdGLV, deptCdOverride, filtername, 0);
        System.out.println(whereCondition);
        
        String insertIntoTemp1Query = "INSERT INTO temp1 " + 
                "SELECT 0 AS FiscalYear, FiscalPeriod, ReconGroupTitle, ReconItemCd, ReconItemTitle, ReconAssignCd, ReconStatusCd, ReconStatusTitle, SUM(Amount) AS Amt, SUM(1) AS Cnt " + 
                "   FROM vw_COA_Report_Ledger_Details " + 
                "   WHERE (FiscalYear = ? AND FiscalPeriod BETWEEN ? AND 12 " + 
                "       OR FiscalYear = ? AND FiscalPeriod BETWEEN 1 AND ?) " + 
                "    AND BusinessUnitCd = ? " + 
                "    AND AccountLevelACd IN ('4000A','5000A','5700A') " + 
                "    AND " + whereCondition +
                "   GROUP BY FiscalYear, FiscalPeriod, ReconGroupTitle, ReconItemTitle, ReconItemCd, ReconAssignCd, ReconStatusTitle, ReconStatusCd ";
        
        PreparedStatement insertIntoTemp1Statement = jdbc.getPrepareStatement(insertIntoTemp1Query);
        insertIntoTemp1Statement.setInt(1, fy - 1);
        insertIntoTemp1Statement.setInt(2, fp + 1);
        insertIntoTemp1Statement.setInt(3, fy);
        insertIntoTemp1Statement.setInt(4, fp);
        insertIntoTemp1Statement.setString(5, businessUnitCd);
        
        insertIntoTemp1Statement.executeUpdate();
        insertIntoTemp1Statement.close();
        
        
        /**
         * insert into temp2
         */
        String insertIntoTemp2Query = "INSERT INTO temp2 "
                + "        SELECT FiscalYear, ReconGroupTitle, ReconItemTitle, ReconItemCd, "
                + "            SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod = ? THEN Amt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod = ? THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod = ? THEN Amt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod = ? THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 2000 AND FiscalPeriod = ? THEN Amt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 2000 AND FiscalPeriod = ? THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 3000 AND FiscalPeriod = ? THEN Amt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 3000 AND FiscalPeriod = ? THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod < ? THEN Amt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod < ? THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod < ? THEN Amt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod < ? THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 11) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 10) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 9) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 8) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 7) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 6) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 5) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 4) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 3) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 2) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 1) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN FiscalPeriod = ((? + 0) % 12) + 1 THEN Amt ELSE 0 END),"
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 11) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 10) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 9) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 8) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 7) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 6) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 5) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 4) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 3) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 2) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 1) % 12) + 1 THEN Cnt ELSE 0 END), "
                + "            SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 0) % 12) + 1 THEN Cnt ELSE 0 END) "
                + "            FROM temp1 "
                + "        GROUP BY FiscalYear, ReconGroupTitle, ReconItemTitle, ReconItemCd ";
        PreparedStatement insertIntoTemp2Statement = jdbc.getPrepareStatement(insertIntoTemp2Query);
        for (int i = 1; i <= 36; i++) {
            insertIntoTemp2Statement.setInt(i, fp);
        }
        insertIntoTemp2Statement.executeUpdate();
        insertIntoTemp2Statement.close();
        
        
        String insertIntoDashboardQuery = "INSERT INTO SOM_AA_Dashboard ( " + 
                "        SessionUserid, SessionDate, " + 
                "        FiscalYear, ReconGroupTitle, " + 
                "        StatusAmt0, StatusCnt0, StatusAmt1, StatusCnt1, StatusAmt2, StatusCnt2, StatusAmt3, StatusCnt3, " + 
                "        PriorStatusAmt0, PriorStatusCnt0, PriorStatusAmt1, PriorStatusCnt1, " + 
                "        AmtM01, AmtM02, AmtM03, AmtM04, AmtM05, AmtM06, AmtM07, AmtM08, AmtM09, AmtM10, AmtM11, AmtM12, " + 
                "        IncM01, IncM02, IncM03, IncM04, IncM05, IncM06, IncM07, IncM08, IncM09, IncM10, IncM11, IncM12 " + 
                "        )" + 
                "        SELECT ?, ?, T1.FiscalYear, T1.ReconGroupTitle, " + 
                "            SUM(T1.StatusAmt0), SUM(T1.StatusCnt0), SUM(T1.StatusAmt1), SUM(T1.StatusCnt1), " + 
                "            SUM(T1.StatusAmt2), SUM(T1.StatusCnt2), SUM(T1.StatusAmt3), SUM(T1.StatusCnt3), " + 
                "            SUM(T1.PriorStatusAmt0), SUM(T1.PriorStatusCnt0), SUM(T1.PriorStatusAmt1), SUM(T1.PriorStatusCnt1), " + 
                "            SUM(T1.AmtM01), SUM(T1.AmtM02), SUM(T1.AmtM03), SUM(T1.AmtM04), SUM(T1.AmtM05), SUM(T1.AmtM06), " + 
                "            SUM(T1.AmtM07), SUM(T1.AmtM08), SUM(T1.AmtM09), SUM(T1.AmtM10), SUM(T1.AmtM11), SUM(T1.AmtM12), " + 
                "            SUM(T1.IncM01), SUM(T1.IncM02), SUM(T1.IncM03), SUM(T1.IncM04), SUM(T1.IncM05), SUM(T1.IncM06), " + 
                "            SUM(T1.IncM07), SUM(T1.IncM08), SUM(T1.IncM09), SUM(T1.IncM10), SUM(T1.IncM11), SUM(T1.IncM12) " + 
                "            FROM temp2 as T1" + 
                "            GROUP BY T1.FiscalYear, T1.ReconGroupTitle";
        PreparedStatement insertIntoDashboardStatement = jdbc.getPrepareStatement(insertIntoDashboardQuery);
        insertIntoDashboardStatement.setString(1, sessionUserId);
        insertIntoDashboardStatement.setDate(2, new java.sql.Date(new Date().getTime()));
        
        insertIntoDashboardStatement.executeUpdate();
        insertIntoDashboardStatement.close();
        
        
        String deleteTemp1Sql = "DROP TABLE temp1";
        String deleteTemp2Sql = "DROP TABLE temp2";
//        statement.execute(deleteTemp1Sql);
//        statement.execute(deleteTemp2Sql);
        
        statement.close();
    }

}
