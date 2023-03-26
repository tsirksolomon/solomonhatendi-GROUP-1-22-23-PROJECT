/*
 * DConnection.java
 *
 * For COMP2171 - Object Oriented Design and Implementation
 *
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodi.group1.items.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Manages connection to and disconnection from the database.
 * @author Christina Solomon
 */
public class DConnection {
    
    
    /**
     * Gets a connection to the database.
     * @return Connection object.
     */
    public static Connection getConnection(){
        Connection c = null;       
        try {
            c = DriverManager.getConnection
                ("jdbc:derby:src/oodi/group1/items/db/inventorydb;create=true");
        } catch (SQLException exception) {          
          }
        return c;
    }
    
    /**
     * Shuts down the database. Prints a statement for whether the exception
     * caught is unexpected. Receiving the expected exception signifies the 
     * normal shutdown of the database.
     */
    public static void dropConnection() {
        try {            
            DriverManager.getConnection("jdbc:derby:;shutdown=true");        
        } catch(SQLException exception) {
            if ( (exception.getErrorCode() == 50000) &&
                 ("XJ015".equals(exception.getSQLState())) ) {
                // we got the expected exception
                System.out.println("Derby shut down normally");
                // Note that for single database shutdown, the expected
                // SQL state is "08006", and the error code is 45000.
            } else {
                // if the error code or SQLState is different, we have
                // an unexpected exception (shutdown failed)
                System.err.println("Derby did not shut down normally");
                printSQLException(exception);
              }
          }
    }
    
    /**
     * Processes all the exceptions generated by an error.
     * @param sqle the SQLException created by the error.
     */
    public static void printSQLException(SQLException sqle) {
        while (sqle != null) {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + sqle.getSQLState());
            System.err.println("  Error Code: " + sqle.getErrorCode());
            System.err.println("  Message:    " + sqle.getMessage());
            sqle.printStackTrace(System.err);
            sqle = sqle.getNextException();
        }
    }
}