package com.krrishdholakia.community_reporting.application.Problem_Reporting;

/**
 * Created by community_reporting on 14/10/16.
 */

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

/*
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.community_reporting.community_reporting.com.community_reporting.application.MainActivity;
import com.community_reporting.community_reporting.com.community_reporting.application.Map_Display.CustomMarkerClusteringDemoActivity;
import com.community_reporting.community_reporting.com.community_reporting.application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Image_Select extends AppCompatActivity {
    private String uID = null;
    private BroadcastReceiver mDownloadReceiver;
    private ProgressDialog mProgressDialog;

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private static final String TAG = "Storage#MainActivity" ;
    private ImageView imgPicture;
    Uri data;
    Firebase firebase;
    StorageReference storage;
    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        Bundle bundle = getIntent().getExtras();

        uID = bundle.getString("uID");
       storage = FirebaseStorage.getInstance().getReference();

        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
            // Create a storage reference from our app
            // Create a child reference
            // imagesRef now points to "images"
        /*StorageReference imagesRef = storageRef.child("images");

        // Child references can also take paths
        // spaceRef now points to "users/me/profile.png
        // imagesRef still points to "images"
        StorageReference spaceRef = storageRef.child("images/space.jpg");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://triallerx12.firebaseio.com/");
*/


        //    storage = new FirebaseStorage("https://triallerx15.firebaseio.com/users"+uID+"/image");


/*
        }

        Button doneBtn = (Button) findViewById(R.id.done_Button);


        if (doneBtn != null)
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Image_Select.this, CustomMarkerClusteringDemoActivity.class);
                    String temp = uID;
                    intent.putExtra("uID", temp);
                    /* Starts an active showing the details for the selected list */
/*
                    startActivity(intent);
                     */
/*
                }
            });

    }
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }

    //method clicked when user clicks imageupload button
    public void onImageGalleryClicked (View v)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath = pictureDirectory.getPath();

        data = Uri.parse(pictureDirectoryPath);

        //get All image types
        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            //IF WE ARE HERE EVERYTHING PROCESSED SUCCESSFULLY

            if (requestCode == IMAGE_GALLERY_REQUEST)
            {
                //if we are here we are hearing back from the image gallery

                //uri is the address of the image on the sd card
                Uri imageUri = data.getData();

                //declare a stream to read the image data from the sd card
                InputStream inputStream;

                // we are getting an input stream based on the uri of the image
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    //get a bitmap from the stream

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    //show the image to the user
                    imgPicture.setImageBitmap(image);


                    // storeImageToFirebase();

                    mFileUri = data.getData();

                    if (mFileUri != null) {
                        uploadFromUri(mFileUri);
                    } else {
                        Log.w(TAG, "File URI is null");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //show message to user indicating that the image is unavailable
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();

                }

            }
        }
    }


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    private void uploadFromUri(Uri fileUri) {


        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = storage.child("image").child(fileUri.getLastPathSegment());;

        // [END get_child_ref]

        // Upload file to Firebase Storage
        // [START_EXCLUDE]
        // [END_EXCLUDE]
        photoRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        Toast.makeText(Image_Select.this, "Upload successfull", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "uploadFromUri:onSuccess");

                        Bundle bundle = getIntent().getExtras();
                        String problemKey = bundle.getString("problemKey");
                        Firebase imageFirebase = new Firebase("https://triallerx15.firebaseio.com/Problems").child(problemKey).child("imageKey");
                        imageFirebase.setValue(photoRef.getName());
                        // Get the public download URL
                        mDownloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(TAG, "uploadFromUri:onFailure", exception);

                        mDownloadUrl = null;

                        // [START_EXCLUDE]
                        Toast.makeText(Image_Select.this, "Error: upload failed",
                                Toast.LENGTH_LONG).show();
                        // [END_EXCLUDE]
                    }
                });


       /* final long ONE_MEGABYTE = 1024 * 1024;
        storage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap tempBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                Log.i(TAG, "Theo1: "+ tempBitmap);

                imgPicture.setImageBitmap(tempBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        */

  /*  }
    // [END upload_from_uri]





}
*/

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.krrishdholakia.community_reporting.application.Map_Display.CustomMarkerClusteringDemoActivity;
import com.krrishdholakia.community_reporting.application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Image_Select extends FragmentActivity {

    private BroadcastReceiver mDownloadReceiver;
    private ProgressDialog mProgressDialog;
    private String uID = null;

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private static final String TAG = "Storage#MainActivity" ;
    private ImageView imgPicture;
    Uri data;
    Firebase firebase;
    StorageReference storage;
    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        Bundle bundle = getIntent().getExtras();

        uID = bundle.getString("uID");
        storage = FirebaseStorage.getInstance().getReference();

        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
            // Create a storage reference from our app
            // Create a child reference
            // imagesRef now points to "images"
        /*StorageReference imagesRef = storageRef.child("images");

        // Child references can also take paths
        // spaceRef now points to "users/me/profile.png
        // imagesRef still points to "images"
        StorageReference spaceRef = storageRef.child("images/space.jpg");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://triallerx12.firebaseio.com/");
*/
            Firebase.setAndroidContext(this);
            firebase = new Firebase("https://com.community_reporting.application.firebaseio.com/");
        }

        Button doneBtn = (Button) findViewById(R.id.done_Button);


        if (doneBtn != null)
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Image_Select.this, CustomMarkerClusteringDemoActivity.class);
                    String temp = uID;
                    intent.putExtra("uID", temp);
                    /* Starts an active showing the details for the selected list */

                    startActivity(intent);


                }
            });



    }



        @Override
        public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }

    //method clicked when user clicks imageupload button
    public void onImageGalleryClicked (View v)
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath = pictureDirectory.getPath();

        data = Uri.parse(pictureDirectoryPath);

        //get All image types
        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

        Toast.makeText(Image_Select.this, "Please wait while we upload the image to our database", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            //IF WE ARE HERE EVERYTHING PROCESSED SUCCESSFULLY

            if (requestCode == IMAGE_GALLERY_REQUEST)
            {
                //if we are here we are hearing back from the image gallery

                //uri is the address of the image on the sd card
                Uri imageUri = data.getData();

                //declare a stream to read the image data from the sd card
                InputStream inputStream;

                // we are getting an input stream based on the uri of the image
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    //get a bitmap from the stream

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    //show the image to the user
                    imgPicture.setImageBitmap(image);


                    // storeImageToFirebase();

                    mFileUri = data.getData();

                    if (mFileUri != null) {
                        uploadFromUri(mFileUri);
                    } else {
                        Log.w(TAG, "File URI is null");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    //show message to user indicating that the image is unavailable
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();

                }

            }
        }
    }


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void storeToFirebase(){
        imgPicture.setDrawingCacheEnabled(true);
        imgPicture.buildDrawingCache();
        Bitmap bitmap = null;
        if(imgPicture.isDrawingCacheEnabled()){
            bitmap =imgPicture.getDrawingCache(true);
        }imgPicture.destroyDrawingCache();
        imgPicture.setDrawingCacheEnabled(false);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        if (bitmap != null)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        else if (bitmap == null)
            System.out.println("NOT WORKING");
        byte[] image=stream.toByteArray();
        System.out.println("byte array:"+image);

        String img_str = Base64.encodeToString(image, 0);
        System.out.println("string:"+img_str);

        firebase.child("space").setValue(img_str);
        System.out.println("Success");

        //imgPicture.setDrawingCacheEnabled(false);
    }



    public void storeImageToFirebase() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
        Bitmap bitmap = BitmapFactory.decodeFile(data.getPath(), options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        // we finally have our base64 string version of the image, save it.
        firebase.child("pic").setValue(base64Image);
        System.out.println("Stored image with length: " + bytes.length);
    }

    private void uploadFromUri(Uri fileUri) {


        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = storage.child("photos")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        // [START_EXCLUDE]
        // [END_EXCLUDE]
        photoRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded
                        Toast.makeText(Image_Select.this, "Upload successfull", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "uploadFromUri:onSuccess");

                        Bundle bundle = getIntent().getExtras();
                        String problemKey = bundle.getString("problemKey");
                        Log.i(TAG, "problemKey: "+ problemKey);
                        Firebase imageFirebase = new Firebase("https://application-146501.firebaseio.com/Problems").child(problemKey).child("imageKey");
                        imageFirebase.setValue(photoRef.getName().toString());

                        // Get the public download URL
                        mDownloadUrl = taskSnapshot.getMetadata().getDownloadUrl();

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(TAG, "uploadFromUri:onFailure", exception);

                        mDownloadUrl = null;

                        // [START_EXCLUDE]
                        Toast.makeText(Image_Select.this, "Error: upload failed",
                                Toast.LENGTH_LONG).show();
                        // [END_EXCLUDE]
                    }
                });


       /* final long ONE_MEGABYTE = 1024 * 1024;
        storage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap tempBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                Log.i(TAG, "Theo1: "+ tempBitmap);

                imgPicture.setImageBitmap(tempBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        */
    }
    // [END upload_from_uri]

}


