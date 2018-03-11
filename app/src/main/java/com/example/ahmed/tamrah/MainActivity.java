package com.example.ahmed.tamrah;
//Ahmed Alqarni

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolBar;
    private SearchView searchView;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser client ;
    private User user;
    //private FirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase.child("MEOW").setValue("MEOWWW");


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
                fragmentClass = ShoppingCartFrag.class;
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
    public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment =null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.Home:
                fragmentClass = Home.class;
                break;
            case R.id.ShoppingCart:
                fragmentClass = ShoppingCartFrag.class;
                break;
            case R.id.UpdateProfile:
                //fragmentClass = ShoppingCartFrag.class;
                user = new User(this);
                //user.updateProfile();
                //user.getUserProfile();
                user.resetPassword("alnamlahk@gmail.com");
                return;

            case R.id.Account_Settings:
                fragmentClass = AccountSettingsFrag.class;
                break;
            case R.id.Orders:
                fragmentClass = OrdersFrag.class;
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
                user = new User(this);

                user.logout();
                mDrawerLayout.closeDrawers();

                /*
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Logout");
                alertDialog.setMessage("You are signed in as: "+client.getEmail());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Logout",
                        new DialogInterface.OnClickListener() {
                            //firebase logout
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseAuth = FirebaseAuth.getInstance();
                                firebaseAuth.signOut();
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
                */
                return;
            case R.id.ContactUs:
                fragmentClass = ContactUsFrag.class;
                break;
            case R.id.OfferPage:
                fragmentClass = OfferFrag.class;
                break;
            //This is profile page
            case R.id.Profile:
                fragmentClass = AccountFrag.class;
                break;
            case R.id.SearchResultPage:
                fragmentClass = SearchResultsFrag.class;
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
        fragmentManager.beginTransaction().replace(R.id.flContents,myFragment).commit();
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

    //This is to make the app title clickable
    public void goToHome(View view) {
        Fragment myFragment =null;
        Class fragmentClass;
        fragmentClass = Home.class;
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents,myFragment).commit();

    }

    //this is for the button in profile page "Edit AccountFrag"
    public void goToAccountSettings(View view) {
        Fragment myFragment =null;
        Class fragmentClass;
        fragmentClass = AccountSettingsFrag.class;
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContents,myFragment).commit();
    }
}
