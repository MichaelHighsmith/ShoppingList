package com.udacity.firebase.shoppinglistplusplus.ui.activeListDetails;

import android.app.Dialog;
import android.os.Bundle;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;
import java.util.Objects;

/**
 * Lets user edit the list name for all copies of the current list
 */
public class EditListNameDialogFragment extends EditListDialogFragment {
    private static final String LOG_TAG = ActiveListDetailsActivity.class.getSimpleName();
    String mListName;
    String ownersId;

    /**
     * Public static constructor that creates fragment and passes a bundle with data into it when adapter is created
     */
    public static EditListNameDialogFragment newInstance(ShoppingList shoppingList, String listId, String ownersId) {
        EditListNameDialogFragment editListNameDialogFragment = new EditListNameDialogFragment();
        Bundle bundle = EditListDialogFragment.newInstanceHelper(shoppingList, R.layout.dialog_edit_list, listId);
        //Add the name value from shoppingList
        bundle.putString(Constants.KEY_LIST_NAME, shoppingList.getListName());
        editListNameDialogFragment.setArguments(bundle);
        return editListNameDialogFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //extract arguments put in the bundle when the newInstance method created the dialog
        mListName = getArguments().getString(Constants.KEY_LIST_NAME);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** {@link EditListDialogFragment#createDialogHelper(int)} is a
         * superclass method that creates the dialog
         **/
        Dialog dialog = super.createDialogHelper(R.string.positive_button_edit_item);

        //use the helper method from EditListDialogFragment to set what text the user sees when the dialog opens
        helpSetDefaultValueEditText(mListName);

        return dialog;
    }

    /**
     * Changes the list name in all copies of the current list
     */
    protected void doListEdit() {
        //Edit the list
        final String inputListName = mEditTextForList.getText().toString();

        if(!inputListName.equals("")){
            if(mListName != null && mListId != null){
                if(!inputListName.equals(mListName)){
                    //If the input text is not empty, and doesn't equal what it was before:
                    Firebase shoppingListRef = new Firebase(Constants.FIREBASE_URL_ACTIVE_LISTS).child(mListId);

                    //Make a hashmap for the specific properties you are changing
                    HashMap<String, Object> updatedProperties = new HashMap<String, Object>();
                    updatedProperties.put(Constants.FIREBASE_PROPERTY_LIST_NAME, inputListName);

                    //Add the timestamp for 'last changed' to the updatedProperties Hashmap
                    HashMap<String, Object> changedTimestampMap = new HashMap<>();
                    changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    //add the updated timestamp
                    updatedProperties.put(Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

                    //do the update
                    shoppingListRef.updateChildren(updatedProperties);
                }
            }
        }
    }
}