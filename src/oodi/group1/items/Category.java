/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oodi.group1.items;


/**
 * Description of Category Class goes here.
 *
 * @author Christina Solomon
 * @version 1.0
 * 
 * For COMP2171 - Object Oriented Design and Implementation
 */
public enum Category {
    Produce, Bakery, Snack, Frozen, Canned, Drink, Other;
    
    public static Category getEnumFromString(String category) {
        switch (category) {
            case "Produce": {
                return Category.Produce;
            }
            case "Bakery": {
                return Category.Bakery;
            }
            case "Snack": {
                return Category.Snack;
            }
            case "Frozen": {
                return Category.Frozen;
            }
            case "Canned": {
                return Category.Canned;
            }
            case "Drink": {
                return Category.Drink;
            }
            default: {
                return Category.Other;
            }
        }
    }
}
