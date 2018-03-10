package com.example.ahmed.tamrah;
//Ahmed Alqarni

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.zzdym;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.ahmed.tamrah.R.id.search_view;
import static com.example.ahmed.tamrah.R.id.toolbar_title;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolBar;
    private static final int SELECTED_PICTURE = 1;
    ImageView iV;
    private SearchView searchView;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser client ;
    //private FirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DataBase Test
        mDatabase.child("MEOW").setValue("MEOWWW");

        //image
       //iV = (ImageView) findViewById(R.id.imageViewAdding);

        //initilize the activity_main and the left drawer
        setContentView(R.layout.activity_main);

        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        //Left menu Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nV);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);


        //set the Default page to Home fragment
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.flContents, new Home()).commit();

        client=FirebaseAuth.getInstance().getCurrentUser();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment myFragment =null;
        Class fragmentClass;

        //Drawer only
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        //home buttons
        fragmentClass = null;
        switch(item.getItemId()) {
            case R.id.cartIconeHome:
                fragmentClass = ShoppingCart.class;
                break;
            case R.id.cameraIconHome:
                //show message
                Context context = getApplicationContext();
                CharSequence text = "Now you go to Camera...";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
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


    //for the left menu choices and transitions
    //for the buttons action handling
    public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment =null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.Home:
                fragmentClass = Home.class;
                break;
            case R.id.ShoppingCart:
                fragmentClass = ShoppingCart.class;
                break;
            case R.id.Account_Settings:
                fragmentClass = AccountSettings.class;
                break;
            case R.id.Orders:
                fragmentClass = Orders.class;
                break;
            case R.id.Login:
                startActivity(new Intent(this,LoginActivity.class));
                Log.i("k","new Login");
                return;
            case R.id.Signup:
                Log.i("k","signUp");
                startActivity(new Intent(this,Registration.class));
                return;
            case R.id.logout:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("You are signed in as: "+client.getEmail());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                dialog.dismiss();
                                //startActivity(new Intent(this,MainActivity.class));
                                Log.i("1","logged out");
                                Toast.makeText(getApplicationContext(), "LoggedOut", Toast.LENGTH_LONG).show();
                                mDrawerLayout.closeDrawers();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //FirebaseAuth.getInstance().signOut();
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                //logout ccode goes here
                return;
            case R.id.ContactUs:
                fragmentClass = ContactUs.class;
                break;
            case R.id.OfferPage:
                fragmentClass = Offer.class;
                break;
            case R.id.Profile:
                fragmentClass = Account.class;
                break;
            case R.id.SearchResultPage:
                fragmentClass = SearchResults.class;
                break;


            default:
                Log.i("k","not there");

                fragmentClass = Home.class;
        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents,myFragment).addToBackStack( "tag" ).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();

    }


    private void setupDrawerContent(NavigationView navigationView){
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
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Button Handler
    //This is to make the app title clickable
    public void goToHome(View view) {
        buttonHandeler(Home.class);
    }

    //Button Handler
    //this is for the button in profile page "Edit Account"
    public void goToAccountSettings(View view) {
        buttonHandeler(AccountSettings.class);
    }

    //Button Handler
    //this is for the button in shopping cart page "shopping cart"
    public void goToCheckoutpage(View view) {
        buttonHandeler(Checkout.class);
    }


    //Button Handler
    //this is for any clicked offer in any page
    public void goToOffer(View view) {
        buttonHandeler(Offer.class);
    }

    //Button Handler
    //this is for user photo clicked in left drawer
    public void goToUserProfile(View view) {
        buttonHandeler(Account.class);
        mDrawerLayout.closeDrawers();

    }

    //Button Handler
    //for selecting image in the Add Offer page
    public void selectPictureBtn(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,SELECTED_PICTURE);

    }


    //Button Handler
    //this is for the plus button in searchin for offer page
    public void goToAddOffer(View view) {
        buttonHandeler(AddOffer.class);
    }


    //Forreading a picture from the device
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SELECTED_PICTURE:
                if(requestCode == RESULT_OK){
                    Uri uri = data.getData();
                    String[]projection= {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    // String picturePath contains the path of selected Image
                    Bitmap yourSelectedPic = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedPic);
                    iV.setBackground(d);


                    // Show the Selected Image on ImageView
                    ImageView imageView = (ImageView) findViewById(R.id.imageViewAdding);
                    imageView.setImageURI(null);
                    imageView.setImageURI(Uri.parse(filePath));
                    //imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                }
                break;


        }
    }

    //Button Handler main function for all buttons
    //You can use it in any button page transtions only
    public void buttonHandeler(Class f) {
        Fragment myFragment =null;
        Class fragmentClass;
        fragmentClass = f;
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents,myFragment).addToBackStack( "tag" ).commit();
    }
}
