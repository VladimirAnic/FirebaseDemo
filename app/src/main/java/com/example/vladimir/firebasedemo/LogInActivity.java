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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLoginmail:

                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .build(),
                            SIGN_IN_REQUEST_CODE
                    );

                } else
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));

                break;

            case R.id.btnLoginAnon:
                mAuth.signInAnonymously()//https://medium.com/@changulpaye/firebase-anonymous-authentication-in-android-317b98c56a09
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                if (!task.isSuccessful()) {
                                    Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                break;
        }


    }

    @Override
    public void onStart() {

        super.onStart();

    }

    // release listener in onStop
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && !user.toString().equals(""))
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
    }


}
