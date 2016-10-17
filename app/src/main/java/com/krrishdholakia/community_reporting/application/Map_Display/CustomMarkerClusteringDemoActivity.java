package com.krrishdholakia.community_reporting.application.Map_Display;

/**
 * Created by community_reporting on 14/10/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.krrishdholakia.community_reporting.application.Problem_Reporting.Problem_Enter;
import com.krrishdholakia.community_reporting.application.Problem_Voting.Problem_Voter;
import com.krrishdholakia.community_reporting.application.R;
import com.krrishdholakia.community_reporting.application.model.MyItem;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Demonstrates heavy customisation of the look of rendered clusters.
 */
public class CustomMarkerClusteringDemoActivity extends BaseDemoActivity implements ClusterManager.OnClusterClickListener<MyItem>, ClusterManager.OnClusterInfoWindowClickListener<MyItem>, ClusterManager.OnClusterItemClickListener<MyItem>, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem> {
    private ClusterManager<MyItem> mClusterManager;
    private Random mRandom = new Random(1984);
    Firebase mRef;
    private static final String TAG = "Mapping#MainActivity" ;

    @Override
    public boolean onClusterClick(Cluster<MyItem> cluster) {
        // Show a toast with some info when the cluster is clicked.

        Log.i(TAG, "cluster: " +cluster.getSize());
        Bundle bundle = getIntent().getExtras();

        List<MyItem> tempCluster = (List<MyItem>) cluster.getItems();
        List<Double> tempLatitude = new LinkedList<Double>();
        List<Double> tempLongitude = new LinkedList<Double>();
        for(int count = 0; count < cluster.getSize();count++)
        {
            tempLatitude.add(tempCluster.get(count).getPosition().latitude);
            tempLongitude.add(tempCluster.get(count).getPosition().longitude);

            Log.i(TAG, "marker positions: " + tempCluster.get(count).getPosition());
        }
        String uID = bundle.getString("uID");

        Toast.makeText(this, cluster.getSize() + " (including " + cluster.getPosition() + ")", Toast.LENGTH_SHORT).show();

        // Zoom in the cluster. Need to create LatLngBounds and including all the cluster items
        // inside of bounds, then animate to center of the bounds.

        // Create the builder to collect all essential cluster items for the bounds.
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        // Get the LatLngBounds
        final LatLngBounds bounds = builder.build();

        // Animate camera to the bounds
        try {
            getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent i = new Intent(CustomMarkerClusteringDemoActivity.this, Problem_Voter.class);

        String temp1 = uID;
        i.putExtra("size", temp1);

        i.putExtra("uID",uID);
        i.putExtra("Latitude", (Serializable) tempLatitude);
        i.putExtra("Longitude", (Serializable) tempLongitude);


        startActivity(i);


        return true;
    }




    @Override
    protected void startDemo() {

        Firebase.setAndroidContext(this);
         mRef = new Firebase("https://application-146501.firebaseio.com/").child("Co-Ordinates");

        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));

        mClusterManager = new ClusterManager<MyItem>(this, getMap());
        getMap().setOnCameraIdleListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        addItems();
        mClusterManager.cluster();


        Button Report_Btn = (Button)findViewById(R.id.Report_Btn);
        if (Report_Btn != null)
            Report_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = getIntent().getExtras();

                    String uID =  bundle.getString("uID");

                    Intent intent = new Intent(CustomMarkerClusteringDemoActivity.this, Problem_Enter.class);

                    intent.putExtra("uID", uID);

                    /* Starts an active showing the details for the selected list */
                    startActivity(intent);

                }
            });
    }



    private void addItems() {

        mRef.addChildEventListener(new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(final com.firebase.client.DataSnapshot dataSnapshot, String s) {


                List<MyItem> Locations = new LinkedList<MyItem>();
                String Lat1 = dataSnapshot.child("latitude").getValue().toString();
                String Long1 = dataSnapshot.child("longitude").getValue().toString();
                MyItem item = new MyItem(Double.parseDouble(Lat1), Double.parseDouble(Long1));
                Locations.add(item);

                /*
                for (int i = 0; i < 10; i++) {
                    double offset = i / 60d;
                    for (MyItem itemTest : Locations) {
                        LatLng position = item.getPosition();
                        double lat = position.latitude + offset;
                        double lng = position.longitude + offset;
                        MyItem offsetItem = new MyItem(lat, lng);
                        mClusterManager.addItem(offsetItem);
                    }
                }

*/
                mClusterManager.addItems(Locations);

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
    }

    private LatLng position() {
        return new LatLng(random(51.6723432, 51.38494009999999), random(0.148271, -0.3514683));
    }

    private double random(double min, double max) {
        return mRandom.nextDouble() * (max - min) + min;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<MyItem> cluster) {

    }

    @Override
    public boolean onClusterItemClick(MyItem myItem) {
        return false;
    }

    @Override
    public void onClusterItemInfoWindowClick(MyItem myItem) {

    }
    
}
