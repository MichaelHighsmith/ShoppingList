package com.udacity.firebase.shoppinglistplusplus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;

/**
 * Created by mhigh on 8/14/2017.
 */

public class ShoppingList {
    String listName;
    String owner;
    String ownerId;
    private HashMap<String, Object> timestampLastChanged;
    //unchanging timestamp for when a list was created
    private HashMap<String, Object> timestampCreated;

    public ShoppingList() {
    }

    public ShoppingList(String listName, String owner, String ownerId, HashMap<String, Object> timestampCreated) {
        this.listName = listName;
        this.owner = owner;
        this.ownerId = ownerId;
        this.timestampCreated = timestampCreated;
        HashMap<String, Object> timestampNowObject = new HashMap<String, Object>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getListName() {
        return listName;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }

    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong(){
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @JsonIgnore
    public long getTimestampCreatedLong(){
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}
