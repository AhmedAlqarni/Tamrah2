package com.example.ahmed.tamrah;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class AccountActivity extends AppCompatActivity {

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

        TextView usernameView = (TextView) findViewById(R.id.FirstName);
        usernameView.setText(MainActivity.user.getName());

        TextView regionView = (TextView) findViewById(R.id.Region);
        regionView.setText(MainActivity.user.getRegion());

        TextView descriptionView = (TextView) findViewById(R.id.Description);
        descriptionView.setText(MainActivity.user.getDescription());

        TextView phoneView = (TextView) findViewById(R.id.Phone);
        phoneView.setText(MainActivity.user.getPhoneNum());
    }
}
