package com.example.ahmed.tamrah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MessagesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);
    }
    public void goToHome(View view){
        finish();
    }
}

