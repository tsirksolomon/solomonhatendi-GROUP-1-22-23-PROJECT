/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodi.group1.items.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author solomon
 */
public class DConnection {
    
    public static Connection getConnection(){
        Connection c = null;
        
        try {
            c = DriverManager.getConnection
                ("jdbc:derby:src/oodi/group1/items/db/inventorydb;create=true");
        } catch (SQLException exception) {
            Logger.getLogger(DConnection.class.getName()).log(Level.SEVERE,
                             null, exception);
        }
       
        return c;
    }
    
    public static void dropConnection(){
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
    
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
    
    
    //delete eventually since i just made this to figure out how connecting to the db works
    public static void main(String[] args) {
        Connection c = null;
        
        try {
            c = DriverManager.getConnection("jdbc:derby:src/oodi/group1/items/db/inventorydb;create=true");
            System.out.println("Connected to Database.");
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        }catch (SQLException exception) {
            if (( (exception.getErrorCode() == 50000)
                            && ("XJ015".equals(exception.getSQLState()) ))) {
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
}