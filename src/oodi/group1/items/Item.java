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
    
    public Item(String upc, String name, double unitPrice, String category) {
        this (upc, name, 0, unitPrice, null, category);
    }
    
    /**
     * Constructor for objects of class Item
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
     * @return    a String
     */
    public String getUPC() {
        return this.upc;
    }

    /**
     * Mutator for UPC property.
     * @param  upc
     */    
    public void setUPC(String upc) {
        this.upc = upc;
    }
    
    /**
     * Accessor for item name property.
     * @return    a String
     */    
    public String getName() {
        return this.name;
    }

    /**
     * Mutator for item name property.
     * @param  name
     */    
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Accessor for item units property.
     * @return    an int
     */
    public int getUnits() {
        return this.units;
    }
    
    /**
     * Mutator for item units property.
     * @param  units
     */
    public void setUnits(int units) {
        this.units = units;
    }
    
    /**
     * Accessor for unit price property.
     * @return    a double
     */   
    public double getunitPrice() {
        return this.unitPrice;
    }

    /**
     * Mutator for unit price property.
     * @param  unitPrice
     */    
    public void setunitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    /**
     * Accessor for manufacturer property.
     * @return    a String
     */    
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * Mutator for manufacturer property.
     * @param  manufacturer
     */    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Accessor for category property.
     * @return    a Category
     */
    public String getCategory() {
        return this.category.name();
    }

    /**
     * Mutator for category property.
     * @param  category
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
    //end of instance variables. So it's easier to keep track of if any are added idk.
}