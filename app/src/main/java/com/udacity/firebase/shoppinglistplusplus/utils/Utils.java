package com.udacity.firebase.shoppinglistplusplus.utils;

import android.content.Context;

import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;

import java.text.SimpleDateFormat;

/**
 * Utility class
 */
public class Utils {
    /**
     * Format the date with SimpleDateFormat
     */
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Context mContext = null;


    /**
     * Public constructor that takes mContext for later use
     */
    public Utils(Context con) {
        mContext = con;
    }

    //Return true if current user is the same as the shoppinglist owner
    public static boolean checkIfOwner(ShoppingList shoppingList, String currentUserId){
        return(shoppingList.getOwner() != null && shoppingList.getOwnerId().equals(currentUserId));
    }

}
