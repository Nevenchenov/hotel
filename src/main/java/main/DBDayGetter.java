package main;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 * Created on 04-May-17.
 */

public class DBDayGetter extends DBOperate{
 

    public static int getDay(LocalDate date) {

        String query = "select dayNumber FROM `hotel`.`days` WHERE month = '" + date.getMonth() + "' AND dayOfMonth = " + date.getDayOfMonth();
        int rezultDayNumb = 0;

        try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();

            // finding dayNumber by date in DB
            rs = stmt.executeQuery(query);
            rs.getInt("dayNumber");





 
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            //close connection ,stmt and resultset here
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            System.out.println("Connection is closed");
        }
    return rezultDayNumb;
    }
 
}

