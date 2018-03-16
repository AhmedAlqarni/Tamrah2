package com.example.ahmed.tamrah;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Warsh on 3/15/2018.
 * This is a Singleton Class
 * To open authentication sessions
 * with FirebaseAuth. It can be used
 * anytime, anywhere
 */

public class Auth {
    public static final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
}
