package com.example.ahmed.tamrah;
//Ahmed Alqarni

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.FragmentManager;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar toolBar;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase.child("MEOW").setValue("MEOWWW");



        //initilize the activity_main and the left drawer
        setContentView(R.layout.activity_main);

        //ToolBar
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);


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
            case R.id.Settings:
                fragmentClass = Settings.class;
                break;
            case R.id.Orders:
                fragmentClass = Orders.class;
                break;
            case R.id.Login:
                fragmentClass = Login.class;
                break;
            case R.id.Signup:
                fragmentClass = Signup.class;
                break;
            case R.id.ContactUs:
                fragmentClass = ContactUs.class;
                break;
            case R.id.Account:
                fragmentClass = Account.class;
                break;
            case R.id.SearchResultPage:
                fragmentClass = SearchResults.class;
                break;


            default:
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

}
