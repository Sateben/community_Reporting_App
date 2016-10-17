package com.krrishdholakia.community_reporting.application.Problem_Voting;

/**
 * Created by community_reporting on 14/10/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.krrishdholakia.community_reporting.application.Image_Grid_Display.Image_Display;
import com.krrishdholakia.community_reporting.application.R;
import com.krrishdholakia.community_reporting.application.model.MyItem;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.database.FirebaseListAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by community_reporting on 3/8/16.
 */
public class Problem_Voter extends FragmentActivity {
    private static final String TAG = "MyActivity";
    FirebaseListAdapter<God_Data> mListAdapter;
    ActiveListAdapter mActiveListAdapter;
    ImageButton up_vote;
    ImageButton down_vote;
    ListView listView;
    Firebase userRef;
    final List<problem_input> KeyID = new LinkedList<>();
    Firebase mRef;
    Firebase voter_Checking_Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primary_view);

        Firebase.setAndroidContext(this);
        Bundle bundle = getIntent().getExtras();

        final String uID = bundle.getString("uID");

        final List<Double> tempLat = (List<Double>) getIntent().getSerializableExtra("Latitude");

        final List<Double> tempLong = (List<Double>) getIntent().getSerializableExtra("Longitude");

        final List<MyItem> Clusters = new LinkedList<>();

        for (int count = 0; count < tempLat.size(); count++) {
            MyItem tempItem = new MyItem(tempLat.get(count), tempLong.get(count));
            Clusters.add(tempItem);
        }
        final DatabaseReference myFirebaseRef = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(android.R.id.list);

        userRef = new Firebase("https://application-146501.firebaseio.com/").child("users").child(uID).child("Problems");

        final List<God_Data> Data_Master = new LinkedList<>();
        final List<Numerical_Data_Input> newVote = new LinkedList<>();
        final List<problem_input> newProblem = new LinkedList<>();
        final List<String> imageKeys = new LinkedList<>();
        final List<List<String>> voter_ID = new LinkedList<>();

        final ArrayAdapter<problem_input> adapter = new ArrayAdapter<problem_input>(this, R.layout.list_view_custom_layout, newProblem) {
            @Override
            public View getView(final int position, View view, ViewGroup parent) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.list_view_custom_layout, parent, false);
                }
                up_vote = (ImageButton) view.findViewById(R.id.thumbs_up);
                down_vote = (ImageButton) view.findViewById(R.id.thumbs_down);

                problem_input secondary_Problem = newProblem.get(position);
                if (secondary_Problem != null) {

                    final Numerical_Data_Input num3 = newVote.get(position);

                    TextView tv = (TextView) view.findViewById(R.id.list_item);
                    tv.setText(secondary_Problem.problem1);

                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rltv_layout);



                    Log.i(TAG, "Position " + position);
                    Log.i(TAG, "num3: " + num3);
                    final String listId = KeyID.get(position).getProblem1();
                    tv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Log.i(TAG,"testing intent");
                            Intent i = new Intent(Problem_Voter.this, Image_Display.class);
                            String temp_Image_Key = imageKeys.get(position);
                            i.putExtra("Image_Keys", temp_Image_Key);
                            i.putExtra("uID", uID);
                            startActivity(i);

                        }
                    });

                    relativeLayout.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            Log.i(TAG,"testing intent");
                            Intent i = new Intent(Problem_Voter.this, Image_Display.class);
                            String temp_Image_Key = imageKeys.get(position);
                            i.putExtra("Image_Keys", temp_Image_Key);
                            i.putExtra("uID", uID);
                            startActivity(i);

                        }
                    });
                    for(int count = 0; count < voter_ID.size(); count++)
                    {
                        Log.i(TAG,"voter_id: "+voter_ID.size());
                    }
                    final int[] user_voting_for_problem = new int[1];

                    up_vote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<String>finalTemp_Voter_ID = null;
                            if(voter_Checker().get(position) != null)
                               finalTemp_Voter_ID = voter_Checker().get(position);

                            if (finalTemp_Voter_ID != null)
                                for (int count = 0; count < finalTemp_Voter_ID.size(); count++) {
                                    if (uID.equals(finalTemp_Voter_ID.get(count))) {
                                        user_voting_for_problem[0] = 1;
                                        break;
                                    }
                                }

                            if (user_voting_for_problem[0] == 0) {

                                int a = 0;
                                if (num3 != null)
                                {
                                    num3.Vote = num3.getVote() + 1;
                                    Firebase Ref1 = new Firebase("https://application-146501.firebaseio.com/Problems").child(listId).child("vote");
                                    Ref1.setValue(num3);
                                    mRef = new Firebase("https://application-146501.firebaseio.com/").child("Problems").child(listId).child("Voters");
                                    mRef.push().setValue(uID);

                                }
                                if (num3 == null)
                                {
                                    a = 1;
                                    Firebase Ref1 = new Firebase("https://application-146501.firebaseio.com/").child(listId).child("vote");
                                    Ref1.setValue(a);
                                    mRef = new Firebase("https://application-146501.firebaseio.com/").child("Problems").child(listId).child("Voters");
                                    mRef.push().setValue(uID);
                                }

                            } else {
                                Toast.makeText(Problem_Voter.this, "You have already voted", Toast.LENGTH_LONG).show();
                            }

                        }


                    });

                    down_vote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<String>finalTemp_Voter_ID1 = null;
                            if(voter_Checker().get(position) != null)
                                finalTemp_Voter_ID1 = voter_Checker().get(position);

                            if (finalTemp_Voter_ID1 != null)
                                for (int count = 0; count < finalTemp_Voter_ID1.size(); count++) {
                                    if (uID.equals(finalTemp_Voter_ID1.get(count))) {
                                        user_voting_for_problem[0] = 1;
                                        break;
                                    }
                                }

                            if (user_voting_for_problem[0] == 0) {
                                int a = 0;
                                if (num3 != null)
                                {
                                    num3.Vote = num3.getVote() - 1;
                                    Firebase Ref1 = new Firebase("https://application-146501.firebaseio.com//Problems").child(listId).child("vote");
                                    Ref1.setValue(num3);

                                    Firebase mRef3 = new Firebase("https://application-146501.firebaseio.com/").child("Problems").child(listId).child("Voters");
                                    mRef3.push().setValue(uID);

                                }
                                if (num3 == null)
                                {
                                    a = -1;
                                    Firebase Ref1 = new Firebase("https://application-146501.firebaseio.com/Problems").child(listId).child("vote");
                                    Ref1.setValue(a);


                                    Firebase mRef3 = new Firebase("https://application-146501.firebaseio.com/").child("Problems").child(listId).child("Voters");
                                    mRef3.push().setValue(uID);
                                }


                            } else {
                                Toast.makeText(Problem_Voter.this, "You have already voted", Toast.LENGTH_LONG).show();
                            }
                        }
                    });



                }
                return view;
            }



        };


        listView.setAdapter(adapter);

        final Firebase mRef1 = new Firebase("https://application-146501.firebaseio.com/").child("Problems");


        mRef1.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(final com.firebase.client.DataSnapshot dataSnapshot, String s) {
                problem_input problem_trial = dataSnapshot.child("problem").getValue(problem_input.class);
                Numerical_Data_Input num2 = dataSnapshot.child("Vote").getValue(Numerical_Data_Input.class);
                Double temporaryLat = Double.parseDouble(dataSnapshot.child("Co-Ordinates").child("latitude").getValue().toString());
                Double temporaryLong = Double.parseDouble(dataSnapshot.child("Co-Ordinates").child("longitude").getValue().toString());
                String image_Key = dataSnapshot.child("imageKey").getValue().toString();

                problem_input KEY_IDENTIFIER = new problem_input(dataSnapshot.getKey());
                mRef1.orderByChild("Vote");
                Log.i(TAG, "Problem_DB: " + dataSnapshot.child("problem").getValue().toString());
                Log.i(TAG, "Problem " + problem_trial.getProblem1());
                Log.i(TAG, "KEY ID " + KEY_IDENTIFIER.getProblem1());
                Log.i(TAG, "Image Key: "+image_Key);
                    for (int count = 0; count < Clusters.size(); count++) {
                        if (Clusters.get(count).getPosition().latitude == temporaryLat) {

                            if (Clusters.get(count).getPosition().longitude == temporaryLong) {
                                newProblem.add(problem_trial);
                                Log.i(TAG, "newProblem: " + problem_trial);
                                newVote.add(num2);
                                Log.i(TAG, "newVote: " + num2);
                                KeyID.add(KEY_IDENTIFIER);
                                Log.i(TAG, "KeyID_List: " + KEY_IDENTIFIER);
                                imageKeys.add(image_Key);
                                adapter.notifyDataSetChanged();

                                break;
                            }

                        }
                    }
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {
            /*    Numerical_Data_Input num4 = dataSnapshot.child("Vote").getValue(Numerical_Data_Input.class);
            //    God_Data god_data_2 = dataSnapshot.getValue(God_Data.class);
                Log.i(TAG, "Problem " + num4.getVote());
                problem_input KEY_IDENTIFIER = new problem_input(dataSnapshot.getKey());
                int counter = 0;
                for (int count = 0; count < KeyID.size(); count++) {
                    if (KEY_IDENTIFIER.getProblem1().equals(KeyID.get(count).getProblem1())) {
                        counter = count;
                    }
                }
                newVote.get(counter).Vote = num4.getVote();
*/
            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //listliststring voterID can be used here

    }

    public List<List<String>> voter_Checker()
    {
        final List<List<String>> voter_ID = new LinkedList<>();

        for (int count = 0; count < KeyID.size(); count++) {
            final List<String> problem_Voter_ID = new LinkedList<>();

            Log.i(TAG,"key+IDENT: "+KeyID.get(count));
            Firebase voterRef = new Firebase("https://com.community_reporting.application.firebaseio.com/").child("Problems").child(KeyID.get(count).getProblem1()).child("Voters");

            voterRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
                @Override
                public void onChildAdded(final com.firebase.client.DataSnapshot dataSnapshot, String s) {
                    String temporary_Voter_ID = dataSnapshot.getValue().toString();

                    Log.i(TAG, "voter_ID: " + temporary_Voter_ID);

                    problem_Voter_ID.add(temporary_Voter_ID);
                }

                @Override
                public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            voter_ID.add(problem_Voter_ID);


        }
        return voter_ID;
    }
}
