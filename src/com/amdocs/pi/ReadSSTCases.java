package com.amdocs.pi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReadSSTCases {

public static void main(String args[]){
	new ReadSSTCases().getRecord("csChangeTo");
}	
    public static Connection con = null;

    public static Connection getConnection()
    {
        try
        {
            if (con == null)
            {
            	
//            	Env :UT201
//            	Instance : TKFSX6
//            	Host: 193.23385.95
//            	Post: 7008

                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@195.233.85.95:7008:TKFSX6", "UTAPP201", "UTAPP201");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public ArrayList<SstImpact> getRecord(String record)
    {
        ResultSet rs = null;
        Statement statement = null;
        ArrayList<SstImpact> list = new ArrayList<SstImpact>();
        getConnection();

  //      String sql = "select OUTLINE, IMPACTED_FUNCTION from SST_IMPACT where ACTUAL_FUNCTION='"+record+"'"; //where FUNCTION_CHANGED=";
        String sql = "select IMPACTED_FUNCTION ,OUTLINE from SST_IMPACT where ACTUAL_FUNCTION='"+record.trim()+"'"; //where FUNCTION_CHANGED=";
        
        //IMPACTED_FUNCTION,        PATH,        OUTLINE
        
        try
        {
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
            if (rs.next() == false)
            {	list.add (new SstImpact("Function not found:-",record));
            	return list;
            }
            	while (rs.next())
            {
                    list.add(new SstImpact(rs.getString(1).toString(),rs.getString(2).toString()));
//                	System.out.println(rs.getString(1).toString());
            }

        }
        catch (Exception e)
        {
            System.out.println("Exception in query");
            e.printStackTrace();

        }
        finally
        {
            try
            {
                rs.close();
                statement.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return list;
    }

    
}
