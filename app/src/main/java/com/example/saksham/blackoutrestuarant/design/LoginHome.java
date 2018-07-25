package com.example.saksham.blackoutrestuarant.design;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.saksham.blackoutrestuarant.main.MainActivity;
import com.example.saksham.blackoutrestuarant.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;


public class LoginHome extends AppCompatActivity {

    Button conti;

    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);

        conti=(Button)findViewById(R.id.signIn);

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LoginHome.this,LoginActivity.class));
                //finish();

                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build());


                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    // already signed in
                    Log.i("cms","phone"+auth.getCurrentUser().getPhoneNumber());
                    startActivity(new Intent(LoginHome.this, MainActivity.class));
                    finish();

                } else {

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setLogo(R.drawable.logo)
                                    .setTheme(R.style.Theme_Design_Light)
                                    .build(),
                            RC_SIGN_IN);

                }

            }
        });

    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(LoginHome.this, MainActivity.class));
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button

                    Toast.makeText(LoginHome.this, "Login canceled by User", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this, LoginHome.class));
                    return;
                }
                else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {

                    Toast.makeText(LoginHome.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginActivity.this, LoginHome.class));
                    return;
                }
                else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {

                    Toast.makeText(LoginHome.this, "Unknown error occured", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(LoginHome.this, LoginHome.class));
                    return;
                }

            }
        }
        else
        {
            Toast.makeText(LoginHome.this, "Unknown SignIn response from server", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(LoginHome.this, LoginHome.class));
        }
    }
    void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(LoginHome.this, LoginHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                });

    }
}