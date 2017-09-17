package com.example.vladimir.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btngetdata;

    private FirebaseDatabase mDatabase;

    String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btngetdata = (Button) findViewById(R.id.btngetdata);

        btngetdata.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance();




    }

    @Override
    public void onClick(View v) {//https://stackoverflow.com/questions/42709084/android-firebase-retrieve-all-data-in-single-path-and-store-it-in-sqlite
        final DatabaseReference myRef;
        myRef = mDatabase.getReferenceFromUrl(("https://fir-demo-a022a.firebaseio.com/"));
        myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                           for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // do SQLite insertion for each data here
                                //Student student = new Student(snapshot.getValue().toString());
                                //FirebasePullDBHelper.getInstance(getApplicationContext()).insertStudents(student);
                                ChatMessage cm=snapshot.getValue(ChatMessage.class);
                               // Toast.makeText(getApplicationContext(), cm.getMessageText(), Toast.LENGTH_SHORT).show();
                                Log.v("!!!!podatak:", "  " + cm.getMessageText());
                            }

                        Log.v("!!!!podatak:", "  " + dataSnapshot.getChildren().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
