package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Y.Nevenchenov
 *
 * Class have been created to contain JDBC variables for opening and managing connection
 *
 *
 * Created on 04-May-17.
 */
public abstract class DBOperate {

    // JDBC variables for opening and managing connection
    protected static Connection con;
    protected static Statement stmt;
    protected static ResultSet rs;
}
