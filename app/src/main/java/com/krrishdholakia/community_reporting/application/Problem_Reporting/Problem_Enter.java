package com.krrishdholakia.community_reporting.application.Problem_Reporting;

/**
 * Created by community_reporting on 14/10/16.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.krrishdholakia.community_reporting.application.Problem_Voting.God_Data;
import com.krrishdholakia.community_reporting.application.Problem_Voting.Numerical_Data_Input;
import com.krrishdholakia.community_reporting.application.Problem_Voting.problem_input;
import com.krrishdholakia.community_reporting.application.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Problem_Enter extends AppCompatActivity {

    private String uID = null;
    private static final String TAG = "tESTING#MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_enter);

        Bundle bundle = getIntent().getExtras();

        uID = bundle.getString("uID");

        Firebase.setAndroidContext(this);

     //   final Firebase userFirebaseRef = new Firebase("https://triallerx15.firebaseio.com/users"+uID+"/Problems");

      //  final Firebase myFirebaseRef =  new Firebase("https://triallerx15.firebaseio.com/Problems");

        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference userFirebaseRef = databaseRef.child("users").child(uID).child("Problems").push();

        final DatabaseReference myFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Problems").push();


        //Use the new reference to add the data

        final EditText editted_text = (EditText) findViewById(R.id.editted_text);
        assert editted_text != null;



        Button doneBtn = (Button) findViewById(R.id.doneBtn);

        if (doneBtn != null)
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    problem_input problemtrial = new problem_input(editted_text.getText().toString());
                    Numerical_Data_Input numerical_data_input = new Numerical_Data_Input(0);

                    God_Data god_data = new God_Data(problemtrial.getProblem1(),numerical_data_input.getVote());

                    userFirebaseRef.setValue(god_data);
                    userFirebaseRef.child("Voters");
                    myFirebaseRef.setValue(god_data);
                    myFirebaseRef.child("Voters");

                    editted_text.setText("");

                    String problemKey = myFirebaseRef.getKey();
                    Log.i(TAG, "problemKey1: " +problemKey);
                    Intent intent = new Intent(Problem_Enter.this, Location_Tag.class);
                    String temp = uID;
                    intent.putExtra("uID", temp);
                    intent.putExtra("problemKey", problemKey);
                    /* Starts an active showing the details for the selected list */
                    startActivity(intent);

                }
            });
    }
}
