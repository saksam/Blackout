package com.example.saksham.blackoutrestuarant.interfaces;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by saksham_ on 03-Apr-18.
 */

public interface OnGetDataListener {

    public void onStart();
    public void onSuccess(DataSnapshot data);
    public void onFailed(DatabaseError databaseError);
}
