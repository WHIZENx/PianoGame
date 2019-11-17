package com.cmu.project.pianogame.MainActivity.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Firebase {

    public static FirebaseAuth getmAuth() { return FirebaseAuth.getInstance(); }

    public static FirebaseUser getCurrent() { return FirebaseAuth.getInstance().getCurrentUser(); }
}
