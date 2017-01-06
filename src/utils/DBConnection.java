/*
 * Created at: Tue Aug 30, 2016
 * Last Modified: 
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Omar
 * 
 * Used for getting a connection to the DB
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ehrmanager";
    private static final String USR = "root";
    private static final String PWD = "12345";
    private static Connection conn;
    
    public static Connection getConnection() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(URL, USR, PWD);
            return conn;
 
        }catch(SQLException ex){
            throw new SQLException("Error in creating a connection to DB...");
        } 
           
    }
    
    public static void close(){
        
        try{
            if (conn != null)
                conn.close();
 
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
           
    }
}
