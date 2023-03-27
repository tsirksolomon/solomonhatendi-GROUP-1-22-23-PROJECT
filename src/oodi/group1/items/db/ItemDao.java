/*
 * ItemDao.java
 *
 * For COMP2171 - Object Oriented Design and Implementation
 *
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodi.group1.items.db;

import oodi.group1.items.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles operations to manage inventory.
 *
 * @author Christina Solomon
 * @version 1.0
 */
public class ItemDao {

    /**
     * Create the tables for inventory db. There is currently no need to invoke
     * this method again.
     *
     * @param dbcon
     * @return true if the table is created successfully, false otherwise.
     */
    private static boolean createTables(Connection dbcon) {
        boolean isCreated = false;
        Statement statement = null;
        try {
            statement = dbcon.createStatement();
            statement.execute(STR_CREATE_INVENTORY_TABLE);
            isCreated = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isCreated;
    }

    /**
     * Adds an item to inventory db.
     *
     * @param item Item object.
     * @return false if exception is caught and item is not added, true
     * otherwise.
     *
     */
    public static boolean addItem(Item item) {
        try {
            Connection db = DConnection.getConnection();

            PreparedStatement ps = db.prepareStatement(STR_SAVE_ITEM);

            ps.setString(1, item.getUPC());
            ps.setString(2, item.getName());
            ps.setInt(3, item.getUnits());
            ps.setDouble(4, item.getunitPrice());
            ps.setString(5, item.getManufacturer());
            ps.setString(6, item.getCategory());

            ps.executeUpdate();

            DConnection.dropConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ItemDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean displayItem() {}

    //start of instance variables
    private static final String STR_CREATE_INVENTORY_TABLE
            = "CREATE TABLE APP.INVENTORY ("
            + "   UPC           VARCHAR(16) NOT NULL PRIMARY KEY, "
            + "   NAME          VARCHAR(30), "
            + "   UNITS         INTEGER, "
            + "   UNIT_PRICE    DOUBLE, "
            + "   MANUFACTURER  VARCHAR(30), "
            + "   CATEGORY      VARCHAR(30) "
            + ")";

    private static final String STR_SAVE_ITEM
            = "INSERT INTO APP.INVENTORY "
            + "   (UPC, NAME, UNITS, UNIT_PRICE, MANUFACTURER, CATEGORY) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
}
