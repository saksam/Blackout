package com.example.saksham.blackoutrestuarant.pattern;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by saksham_ on 23-Mar-18.
 */

public class FirebaseSingleDatabase {

    private static FirebaseDatabase firebaseDatabase;
    private FirebaseSingleDatabase()
    {

    }
    public static FirebaseDatabase getInstance(){

        if(firebaseDatabase==null)
            firebaseDatabase=FirebaseDatabase.getInstance();
        return firebaseDatabase;

    }
}
