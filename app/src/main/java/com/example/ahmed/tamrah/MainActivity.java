package com.example.ahmed.tamrah;
//Ahmed Alqarni

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ahmed.tamrah.AccountActivity.getCorrectlyOrientedImage;
import static com.example.ahmed.tamrah.AccountActivity.getOrientation;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolBar;
    private User user;
    ImageView iV;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private SearchView searchView;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser client ;
    //private FirebaseAuth;
    FirebaseListAdapter<Message_Activity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        //Left menu Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nV);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);


        //set the Default page to HomeFrag fragment
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.flContents, new HomeFrag()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment myFragment = null;
        Class fragmentClass = null;
        //Drawer only
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //home buttons
        switch (item.getItemId()) {
            case R.id.cartIconeHome:
                fragmentClass = ShoppingCartFrag.class;
                break;
            case R.id.cameraIconHome:
                //show message
                dispatchTakePictureIntent();
                return true;
        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents, myFragment).commit();

        return super.onOptionsItemSelected(item);
    }


    //for the left menu choices and transitions
    //and for the buttons action handling
    public void selectItemDrawer(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.Home:
                fragmentClass = HomeFrag.class;
                break;
            case R.id.ShoppingCart:
                fragmentClass = ShoppingCartFrag.class;
                break;
            case R.id.Account_Settings:
                startActivity(new Intent(this, AccountSettingsActivity.class));
                return;
            case R.id.Orders:
                fragmentClass = OrdersFrag.class;
                break;
            case R.id.Login:
                Intent LoginActInt = new Intent(this, LoginActivity.class);
                LoginActInt.putExtra("User", (Serializable) this.user);
                startActivity(LoginActInt);
                return;
            case R.id.Signup:
                Log.i("k", "signUp");
                startActivity(new Intent(this, SignupActivity.class));
                return;
           /* case R.id.acitvity_message:
               // Log.i("k","signUp");
                if(client != null){
                   //displayChatMessages();
                startActivity(new Intent(this,Message_Activity.class));}
                return;*/
            case R.id.logout:
                mDrawerLayout.closeDrawers();
                //logout code goes here
                return;
            case R.id.ContactUs:
                fragmentClass = ContactUsFrag.class;
                break;
            case R.id.OfferPage:
                fragmentClass = OfferFrag.class;
                break;
            //This is profile page
            case R.id.Profile:
                Intent myProfile = new Intent(this, AccountActivity.class);
                myProfile.putExtra("UID", "rzjZ4oY3gMOklf2uBfIfJiEIQSn2");
                startActivity(myProfile);
                return;
           case R.id.Messages:
                startActivity(new Intent(this, MessagesListActivity.class));
                return;

            case R.id.SearchResultPage:
                fragmentClass = SearchResultsFrag.class;
                break;

            default:
                fragmentClass = HomeFrag.class;
        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents, myFragment).addToBackStack("tag").commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        buttonHandeler(HomeFrag.class);
    }

    //Button Handler
    //this is for the button in profile page "Edit Account"
    public void goToAccountSettings(View view) {
        buttonHandeler(AccountSettingsFrag.class);
    }

    //Button Handler
    //this is for the button in shopping cart page "shopping cart"
    public void goToCheckoutpage(View view) {
        buttonHandeler(CheckoutFrag.class);
    }


    //Button Handler
    //this is for any clicked offer in any page
    public void goToOffer(View view) {
        buttonHandeler(OfferFrag.class);
    }

    //Button Handler
    //this is for user photo clicked in left drawer
    public void goToUserProfile(View view) {
        startActivity(new Intent(this, AccountActivity.class));
        mDrawerLayout.closeDrawers();

    }

    //Button Handler
    //this is for the plus button in searchin for offer page
    public void goToAddOffer(View view) {
        startActivity(new Intent(this, AddOfferActivity.class));
    }


    //For reading a picture from the device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //For reading a picture from the deviceif(requestCode ==   SELECTED_PICTURE && data != null) {
            Uri uri = data.getData();
            // Show the Selected Image onImageView ImageView cV = (ImageView) findViewById(R.id.imageViewAdding);
        ImageView cV = (ImageView) findViewById(R.id.imageViewAdding);
        getOrientation(this, uri);
            try {
                //profile_image
                Bitmap loadedBitmap = getCorrectlyOrientedImage(this, uri,1000);
                cV.setImageBitmap(loadedBitmap);
                //cV.setImageURI(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //for the Camera App>>>
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                //mImageView.setImageBitmap(imageBitmap); Image result
            }

        }



    //Button Handler main function for all buttons
    //You can use it in any button page transtions only
    public void buttonHandeler(Class f) {
        Fragment myFragment = null;
        Class fragmentClass;
        fragmentClass = f;
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents, myFragment).addToBackStack("tag").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = (User) getIntent().getSerializableExtra("user");
    }



    //for the camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



}
