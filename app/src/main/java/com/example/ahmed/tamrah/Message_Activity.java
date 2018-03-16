package com.example.ahmed.tamrah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Message_Activity extends AppCompatActivity {

    private EditText editTextMessage;
    private DatabaseReference databaseReference;
    private RecyclerView mMessageList;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser mCurrentUser;
    private DatabaseReference databaseReference2;

    private String messageText;
    private String messageUser;
    private long messageTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_);

        editTextMessage = (EditText) findViewById(R.id.TextToSend);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("messages");
        mMessageList=(RecyclerView)findViewById(R.id.messageRec);
        mMessageList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent( Message_Activity.this, SignupActivity.class));
                }
            }
        };


    }

    public void sendMessage(View view){
        mCurrentUser = firebaseAuth.getCurrentUser();
        databaseReference2=FirebaseDatabase.getInstance().getReference().child("User").child(mCurrentUser.getUid());
        final String messageValue = editTextMessage.getText().toString().trim();
        if(!TextUtils.isEmpty(messageValue)){
            final DatabaseReference newPost = databaseReference.push();
            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newPost.child("content").setValue(messageValue);
                    newPost.child("name").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }mMessageList.scrollToPosition(mMessageList.getAdapter().getItemCount());
    }


        /*public Message_Activity(String messageText, String messageUser) {
            this.messageText = messageText;
            this.messageUser = messageUser;

            // Initialize to current time
            messageTime = new Date().getTime();
        }*/


        /*public void sendMessage(View view){
            EditText input = (EditText)findViewById(R.id.input);

                    // Read the input field and push a new instance
                    // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new Message_Activity(input.getText().toString(),
                            FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getEmail())
                    );

            // Clear the input
            input.setText("");
        }*/

        public Message_Activity(){

        }
        @Override
        protected void onStart() {
            super.onStart();
            FirebaseRecyclerAdapter<Message,MessageViewHolder> firebaseRecyclerAdapter =
                    new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                            Message.class,
                            R.layout.message,
                            MessageViewHolder.class,
                            databaseReference
                    ){


                @Override
                protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                    viewHolder.setContent(model.getContent());
                    viewHolder.setName(model.getName());
                }
            };
            mMessageList.setAdapter(firebaseRecyclerAdapter);
        }

        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }

        public String getMessageUser() {
            return messageUser;
        }

        public void setMessageUser(String messageUser) {
            this.messageUser = messageUser;
        }

        public long getMessageTime() {
            return messageTime;
        }

        public void setMessageTime(long messageTime) {
            this.messageTime = messageTime;
        }

        public static class MessageViewHolder extends RecyclerView.ViewHolder{

            View mView;

            public MessageViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
            }

            public void setContent(String content){
                Log.i("193",content);

                TextView message_content = (TextView) mView.findViewById(R.id.ChatMessg);
                message_content.setText(content);

            }
            public void setName(String name){
//                Log.i("193",name);
                TextView name_content = (TextView) mView.findViewById(R.id.Sender);
                name_content.setText(name);
            }


        }

}
