package com.krrishdholakia.community_reporting.application.Problem_Voting;

/**
 * Created by community_reporting on 14/10/16.
 */
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.krrishdholakia.community_reporting.application.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by community_reporting on 7/8/16.
 */
public class ActiveListAdapter extends FirebaseListAdapter<God_Data> {
    public ActiveListAdapter(Activity activity, Class<God_Data> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;


    }

    /**
     * Public constructor that initializes private instance variables when adapter is created
     */
    @Override
    protected void populateView(View v, final God_Data model, int position) {

        TextView textViewListName = (TextView) v.findViewById(R.id.list_item);
        final ImageButton up_vote = (ImageButton) v.findViewById(R.id.thumbs_up);
        final ImageButton down_vote = (ImageButton) v.findViewById(R.id.thumbs_down);
        /* Set the list name and owner */
        textViewListName.setText(model.problem);

        /**
         * Show "1 person is shopping" if one person is shopping
         * Show "N people shopping" if two or more users are shopping
         * Show nothing if nobody is shopping
         */



        /**
         * Set "Created by" text to "You" if current user is owner of the list
         * Set "Created by" text to userName if current user is NOT owner of the list
         */


    }
}
