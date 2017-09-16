package com.example.vladimir.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btngetdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btngetdata = (Button) findViewById(R.id.btngetdata);

        btngetdata.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {//https://stackoverflow.com/questions/42709084/android-firebase-retrieve-all-data-in-single-path-and-store-it-in-sqlite
        FirebaseDatabase.getInstance().getReference("fir-demo-a022a").child("messageText")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                           for(DataSnapshot snapshot1 : snapshot.getChildren()){
                               Toast.makeText(getApplicationContext(), (snapshot1.getValue().toString()), Toast.LENGTH_SHORT).show();
                               Log.v("podatak:","   " + dataSnapshot.getValue(String.class));
                            }
                            // do SQLite insertion for each data here
                           // Student student = new Student(snapshot.getValue().toString());
                            //FirebasePullDBHelper.getInstance(getApplicationContext()).insertStudents(student);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
