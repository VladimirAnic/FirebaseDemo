package com.example.vladimir.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoginmail;
    Button btnLoginAnon;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final int SIGN_IN_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLoginmail = (Button) findViewById(R.id.btnLoginmail);
        btnLoginAnon = (Button) findViewById(R.id.btnLoginAnon);

        btnLoginmail.setOnClickListener(this);
        btnLoginAnon.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        // active listen to user logged in or not.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLoginmail:
            startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .build(),
                        SIGN_IN_REQUEST_CODE
                );
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.btnLoginAnon:
                mAuth.signInAnonymously()
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Log.d(TAG, "OnComplete : " +task.isSuccessful());

                                if (!task.isSuccessful()) {
                                   // Log.w(TAG, "Failed : ", task.getException());
                                    Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                startActivity(new Intent(this, MainActivity.class));
                break;
        }


    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // release listener in onStop
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
