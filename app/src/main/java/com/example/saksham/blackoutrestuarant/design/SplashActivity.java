package com.example.saksham.blackoutrestuarant.design;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.saksham.blackoutrestuarant.R;
import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    // already signed in
                    //Log.i("cms", "phone" + auth.getCurrentUser().getPhoneNumber());
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else {
                    Intent i = new Intent(SplashActivity.this, LoginHome.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
