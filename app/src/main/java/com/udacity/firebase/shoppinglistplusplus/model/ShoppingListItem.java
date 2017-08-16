package com.udacity.firebase.shoppinglistplusplus.model;

/**
 * Defines the data structure for ShoppingListItem objects.
 */
public class ShoppingListItem {
    private String itemName;
    private String owner;

    public ShoppingListItem(){

    }

    public ShoppingListItem(String itemName){
        this.itemName = itemName;
        this.owner = "Anonymous Owner";
    }

    public String getItemName(){
        return itemName;
    }

    public String getOwner() {
        return owner;
    }
}