package com.krrishdholakia.community_reporting.application.Image_Grid_Display;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.krrishdholakia.community_reporting.application.Problem_Reporting.Problem_Enter;
import com.krrishdholakia.community_reporting.application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by community_reporting on 14/10/16.
 */
public class Image_Display extends FragmentActivity {
    private ImageView imgPicture;
    private static final String TAG = "Display#MainActivity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        final Bundle bundle = getIntent().getExtras();

        String image_Key = bundle.getString("Image_Keys");

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("photos").child(image_Key);

        imgPicture = (ImageView) findViewById(R.id.imgPicture);

        final long ONE_MEGABYTE = 4000 * 3000;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed

                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap tempBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

                imgPicture.setImageBitmap(tempBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                Log.i(TAG,"failure");
                // Handle any errors
            }
        });

        Button Report_Button = (Button)findViewById(R.id.Report_Btn);

        Report_Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.i(TAG,"true intent");
                Intent i = new Intent(Image_Display.this, Problem_Enter.class);
                String uID = bundle.getString("uID");
                i.putExtra("uID",uID);
                startActivity(i);

            }
        });

    }


}
