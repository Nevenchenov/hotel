package main;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */

public class DBConnector  extends DBOperate{
 
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://151.80.26.234:3306/hotel";
    private static final String user = "hotel";
    private static final String password = "HotelDB2240";


    public static Connection get() {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	
         
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection success!");
            
 
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
    
        
        return con;
    }
    
    public static void closeCon(){
    	try { con.close(); } catch(SQLException se) { /*can't do anything */ }
    	finally {
            //close connection 
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }
}

