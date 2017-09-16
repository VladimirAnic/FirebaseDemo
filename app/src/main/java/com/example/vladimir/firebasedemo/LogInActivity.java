package com.example.vladimir.firebasedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoginmail;
    Button btnLoginAnon;
    private static final int SIGN_IN_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLoginmail = (Button) findViewById(R.id.btnLoginmail);
        btnLoginAnon = (Button) findViewById(R.id.btnLoginAnon);

        btnLoginmail.setOnClickListener(this);
        btnLoginAnon.setOnClickListener(this);
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

                break;
        }


    }
}
