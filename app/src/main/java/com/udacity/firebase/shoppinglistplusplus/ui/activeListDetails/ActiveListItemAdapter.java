package com.udacity.firebase.shoppinglistplusplus.ui.activeListDetails;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingListItem;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.HashMap;

/**
 * Created by mhigh on 8/15/2017.
 */

public class ActiveListItemAdapter extends FirebaseListAdapter<ShoppingListItem> {

    private ShoppingList mShoppingList;
    private String mListId;
    private String mEncodedEmail;
    FirebaseAuth mFirebaseAuth;


    public ActiveListItemAdapter(Activity activity, Class<ShoppingListItem> modelClass, int modelLayout, Query ref, String listId, String encodedEmail){
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
        this.mListId = listId;
        this.mEncodedEmail = encodedEmail;
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    public void setShoppingList(ShoppingList shoppingList){
        this.mShoppingList = shoppingList;
        this.notifyDataSetChanged();
    }

    @Override
    protected  void populateView(View view, final ShoppingListItem item, int position){
        ImageButton buttonRemoveItem = (ImageButton) view.findViewById(R.id.button_remove_item);
        TextView textViewItemName = (TextView) view.findViewById(R.id.text_view_active_list_item_name);
        TextView textViewBoughtBy = (TextView) view.findViewById(R.id.text_view_bought_by);

        String owner = item.getOwner();

        textViewItemName.setText(item.getItemName());

        setItemApperanceBaseOnBoughtStatus(owner, textViewBoughtBy, buttonRemoveItem, textViewItemName, item);



        final String itemToRemoveId = this.getRef(position).getKey();

        buttonRemoveItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity, R.style.CustomTheme_Dialog)
                        .setTitle(mActivity.getString(R.string.remove_item_option))
                        .setMessage(mActivity.getString(R.string.dialog_message_are_you_sure_remove_item))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(itemToRemoveId);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert);

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    private void removeItem(String itemId){
        Firebase firebaseRef = new Firebase(Constants.FIREBASE_URL);

        //Make a map for the removal
        HashMap<String, Object> updatedRemoveItemMap = new HashMap<String, Object>();

        //Remove the item by passing null
        updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_SHOPPING_LIST_ITEMS + "/" + mListId + "/" + itemId, null);

        //Make the timestamp for last changed
        HashMap<String, Object> changedTimestampMap = new HashMap<>();
        changedTimestampMap.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

        //Add the updated timestamp
        updatedRemoveItemMap.put("/" + Constants.FIREBASE_LOCATION_ACTIVE_LISTS + "/" + mListId + "/" + Constants.FIREBASE_PROPERTY_TIMESTAMP_LAST_CHANGED, changedTimestampMap);

        //Do the update
        firebaseRef.updateChildren(updatedRemoveItemMap);

    }

    private void setItemApperanceBaseOnBoughtStatus(String owner, TextView textViewBoughtBy, ImageButton buttonRemoveItem, TextView textViewItemName, ShoppingListItem item){
        if(item.isBought() && item.getBoughtBy() != null){
            textViewBoughtBy.setVisibility(View.VISIBLE);

            buttonRemoveItem.setVisibility(View.INVISIBLE);

            textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            textViewItemName.setPaintFlags(textViewItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            textViewBoughtBy.setVisibility(View.INVISIBLE);

            buttonRemoveItem.setVisibility(View.VISIBLE);
        }
    }

}
