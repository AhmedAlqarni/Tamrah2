package com.example.ahmed.tamrah;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content view to a specific XML layout
        setContentView(R.layout.activity_account);

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
}
