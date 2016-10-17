package com.krrishdholakia.community_reporting.application.Problem_Reporting;

/**
 * Created by community_reporting on 14/10/16.
 */
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.krrishdholakia.community_reporting.application.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class Location_Tag extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    private String uID = null;

    private String problemKey = null;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    private GoogleApiClient mGoogleApiClient;

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_tag);


        Bundle bundle = getIntent().getExtras();

        uID = bundle.getString("uID");

        problemKey = bundle.getString("problemKey");

        Firebase.setAndroidContext(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        Button doneBtn = (Button) findViewById(R.id.done_Button);


        if (doneBtn != null)
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Location_Tag.this, Image_Select.class);
                    String temp = uID;
                    intent.putExtra("uID", temp);
                    intent.putExtra("problemKey", problemKey);
                    /* Starts an active showing the details for the selected list */
                    startActivity(intent);

                }
            });


    }
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(null)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                double temp_longitude = place.getLatLng().longitude;
                double temp_latitude = place.getLatLng().latitude;
                Geo_Data geo_data = new Geo_Data(temp_latitude,temp_longitude);
                Log.i(TAG, "Place: " + place.getName());




                final Firebase userFirebaseRef = new Firebase("https://application-146501.firebaseio.com/").child("users").child(uID).child("Co-Ordinates");


                final Firebase problemFirebaseRef = new Firebase("https://application-146501.firebaseio.com/").child("Problems").child(problemKey).child("Co-Ordinates");


                final Firebase myFirebaseRef =  new Firebase("https://application-146501.firebaseio.com/").child("Co-Ordinates");


               // Firebase mRef = new Firebase("https://triallerx7.firebaseio.com/").child("Co-ordinates");
                userFirebaseRef.push().setValue(geo_data);
                myFirebaseRef.push().setValue(geo_data);
                problemFirebaseRef.setValue(place.getLatLng());
                ((TextView)findViewById(R.id.temp_place_holder)).setText(place.getName());
                userFirebaseRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Geo_Data geo_data1 = dataSnapshot.getValue(Geo_Data.class);
                        if (geo_data1 != null) {
                            ((TextView) findViewById(R.id.latitude_holder)).setText("" + geo_data1.getLatitude());
                            ((TextView) findViewById(R.id.longitude_holder)).setText("" + geo_data1.getLongitude());
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());
                isGooglePlayServicesAvailable(this);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }
    }

    public boolean isGooglePlayServicesAvailable(Context context){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }




}