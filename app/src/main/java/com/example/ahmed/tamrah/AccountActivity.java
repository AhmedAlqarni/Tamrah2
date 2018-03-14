package com.example.ahmed.tamrah;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;

import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.MediaStore.Images.Media.getBitmap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {
    private static final int SELECTED_PICTURE = 1;
    ImageView iV;
    CircleImageView cV ;



    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content view to a specific XML layout
        setContentView(R.layout.activity_account);
        MainActivity.user.setContext(this);

        //add spinner values
        addRateSpinerValues();


        // profile image area
        CircleImageView cV = (CircleImageView) findViewById(R.id.profile_image);

        //Checking which user to retrieve (Incomplete, needs an 'else' statement
        String data = getIntent().getStringExtra("UID");
        if(data.equals("myAccount")) {
            user = MainActivity.user;
            user.setContext(this);
        }
        //Put 'else' here to retrieve data of another UID


        //Filling up the content of the XML view with legitimate information
        TextView usernameView = (TextView) findViewById(R.id.FirstName);
        usernameView.setText(user.getName());

        TextView regionView = (TextView) findViewById(R.id.Region);
        regionView.setText(user.getRegion());

        TextView descriptionView = (TextView) findViewById(R.id.Description);
        descriptionView.setText(user.getDescription());

        TextView phoneView = (TextView) findViewById(R.id.Phone);
        phoneView.setText(user.getPhoneNum());

        CircleImageView pictureView = (CircleImageView) findViewById(R.id.profile_image);
        pictureView.setImageDrawable(user.getProfilePicture());

    }

    //Button Handler
    //for selecting profile image in the Profile page
    public void changeProfilePictureBtn(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,SELECTED_PICTURE);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //For reading a picture from the device
        if(requestCode ==  SELECTED_PICTURE && data!=null) {
            Uri uri = data.getData();
            // Show the Selected Image on ImageView
            CircleImageView cV = (CircleImageView) findViewById(R.id.profile_image);
            getOrientation(this, uri);
            try {
                //profile_image
                Bitmap loadedBitmap = getCorrectlyOrientedImage(this, uri,1000);
                cV.setImageBitmap(loadedBitmap);
                //cV.setImageURI(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }



    //gets image oreintation  before setting it as profile image
    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor == null || cursor.getCount() != 1) {
            return 90;  //Assuming it was taken portrait
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    /**
     * Rotates and shrinks an image as needed as needed
     * for the profile pic
     */
    public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri, int maxWidth)
            throws IOException {

        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();


        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            Log.d("ImageUtil", "Will be rotated");
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        Log.d("ImageUtil", String.format("rotatedWidth=%s, rotatedHeight=%s, maxWidth=%s",
                rotatedWidth, rotatedHeight, maxWidth));
        if (rotatedWidth > maxWidth || rotatedHeight > maxWidth) {
            float widthRatio = ((float) rotatedWidth) / ((float) maxWidth);
            float heightRatio = ((float) rotatedHeight) / ((float) maxWidth);
            float maxRatio = Math.max(widthRatio, heightRatio);
            Log.d("ImageUtil", String.format("Shrinking. maxRatio=%s",
                    maxRatio));

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            Log.d("ImageUtil", String.format("No need for Shrinking. maxRatio=%s",
                    1));

            srcBitmap = BitmapFactory.decodeStream(is);
            Log.d("ImageUtil", String.format("Decoded bitmap successful"));
        }
        is.close();

        /*
         * if the orientation is not 0 (or -1, which means we don't know), we
         * have to do a rotation.
         */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }


    //Button Handler
    //this is for the button in profile page "Edit Account"
    public void goToAccountSettings(View view) {
        startActivity( new Intent(this,AccountSettingsActivity.class));
    }

    //to add all rating 1...5 spinner values
    public void addRateSpinerValues(){
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter;
        List<String> list;
        list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }




}

