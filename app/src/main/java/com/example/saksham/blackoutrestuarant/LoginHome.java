package com.example.saksham.blackoutrestuarant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class LoginHome extends AppCompatActivity {

    Button conti;

    private final static int RC_SIGN_IN = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);

        conti=(Button)findViewById(R.id.conti);

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginHome.this,LoginActivity.class));
                finish();
            }
        });

    }

}