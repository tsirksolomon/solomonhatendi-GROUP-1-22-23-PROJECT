/*
 * Item.java
 * 
 * For COMP2171 - Object Oriented Design and Implementation
 *
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodi.group1.items;

/**
 * The item class contains all the constructors, mutators, and accessors necessary
 * for the creation of an "item".(Uh oh, this description may be bad!)
 *
 * @author Christina Solomon
 * @version 1.0
 *
 */
public class Item {
    /**
     * Creates a new instance of Item.
     */
    public Item(){
    }
    
    /**
     *
     * @param upc 
     * @param name
     * @param unitPrice
     * @param category
     */
    public Item(String upc, String name, double unitPrice, String category) {
        this (upc, name, 0, unitPrice, null, category);
    }
    
    /**
     * Constructor for objects of class Item.
     * @param upc
     * @param name
     * @param units
     * @param unitPrice
     * @param manufacturer
     * @param category
     */
    public Item(String upc, String name, int units, double unitPrice, String 
                manufacturer, String category) {
        this.upc = upc;
        this.name = name;
        this.units = units;
        this.unitPrice = unitPrice;
        this.manufacturer = manufacturer;
        this.category = Category.getEnumFromString(category);
    }
    
    
    /**
     * Accessor for UPC property.
     * @return   Item UPC.
     */
    public String getUPC() {
        return this.upc;
    }

    /**
     * Mutator for UPC property.
     * @param  upc The 12 character unique code possessed by all consumer products
     *             for identification.
     */    
    public void setUPC(String upc) {
        this.upc = upc;
    }
    
    /**
     * Accessor for item name property.
     * @return    Name of the Item.
     */    
    public String getName() {
        return this.name;
    }

    /**
     * Mutator for item name property.
     * @param  name Name of the Item.
     */    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Accessor for item units property.
     * @return    Number of units of Item.
     */
    public int getUnits() {
        return this.units;
    }
    
    /**
     * Mutator for item units property.
     * @param  units Number of units of Item.
     */
    public void setUnits(int units) {
        this.units = units;
    }
    
    /**
     * Accessor for unit price property.
     * @return    Unit price of the Item.
     */   
    public double getunitPrice() {
        return this.unitPrice;
    }

    /**
     * Mutator for unit price property.
     * @param  unitPrice Item price per unit.
     */    
    public void setunitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    /**
     * Accessor for manufacturer property.
     * @return    Name of the manufacturer of the Item.
     */    
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * Mutator for manufacturer property.
     * @param  manufacturer Item manufacturer.
     */    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Accessor for category property.
     * @return    Category the Item belongs to.
     */
    public String getCategory() {
        return this.category.name();
    }

    /**
     * Mutator for category property.
     * @param  category Item Category.
     */    
    public void setCategory(String category) {
        this.category = Category.getEnumFromString(category);
    }
    
    /**
     * TO DO: Override of both comparable and equals. 
     * Whether two items are equal should be predicated on whether they have 
     * the same UPC number.
     */
    
    //instance variables
    private String upc;
    private String name;
    private int units;
    private double unitPrice;
    private String manufacturer;
    private Category category;
    //end of instance variables.
}
}
