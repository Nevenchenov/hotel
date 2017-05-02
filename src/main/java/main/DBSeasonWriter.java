package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */

public class DBSeasonWriter {
 
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    public static int count;

    // data writting indicator
    public static String isWrote = "<strong>Fill form <br>and press \"OK\"</strong>";
 
    public static int sendToDB(ArrayList<LocalDate> season) {

        String update;


        String query = "select * FROM `hotel`.`days`";

        try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing insertion to DB
            count = 1;
            for(LocalDate d : season) {
                update = "INSERT INTO hotel.days (dayNumber, month, dayOfMonth, dayOfWeek) \n" +
                        " VALUES (" + count + ", '" +
                        d.getMonth() + "', " +
                        d.getDayOfMonth() + ", '" +
                        d.getDayOfWeek() + "');";
                stmt.executeUpdate(update);
                count++;
            }

            // checking appearance of room added in DB
            rs = stmt.executeQuery(query);
            int rowsCount = 0;
            while (rs.next()) {
                    rowsCount++;
                }
                System.out.print("DB is updated; ");
                isWrote = "Writing Complete!" + rowsCount + " days added to DB";




 
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            System.out.println("Connection is closed");
        }
    return count;
    }
 
}

