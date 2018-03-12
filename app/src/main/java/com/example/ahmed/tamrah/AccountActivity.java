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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.MediaStore.Images.Media.getBitmap;


public class AccountActivity extends AppCompatActivity {
    private static final int SELECTED_PICTURE = 1;
    ImageView iV;
    CircleImageView cV ;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment myFragment =null;
        Class fragmentClass;

        //home buttons
        fragmentClass = null;
        if(item.getItemId() == R.id.EditAccount) {
            fragmentClass = AccountSettingsFrag.class;
        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents,myFragment).commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        MainActivity.user.setContext(this);


        // profile image area
        CircleImageView cV = (CircleImageView) findViewById(R.id.profile_image);

        TextView usernameView = (TextView) findViewById(R.id.FirstName);
        usernameView.setText(MainActivity.user.getName());

        TextView regionView = (TextView) findViewById(R.id.Region);
        regionView.setText(MainActivity.user.getRegion());

        TextView descriptionView = (TextView) findViewById(R.id.Description);
        descriptionView.setText(MainActivity.user.getDescription());

        TextView phoneView = (TextView) findViewById(R.id.Phone);
        phoneView.setText(MainActivity.user.getPhoneNum());
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
            //profile_image
            //File imgFile = new File(String.valueOf(uri));
            cV.setImageURI(null);
            cV.setImageURI(uri);

        }



    }
}

