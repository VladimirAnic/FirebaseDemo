package com.example.vladimir.firebasedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btngetdata, btnSaveDataDB;

    static ArrayList<Student> studenti=new ArrayList<>();

    private FirebaseDatabase mDatabase;

    ListView lvdata;
    StudentAdapter mStudentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.lvdata = (ListView) this.findViewById(R.id.lvdata);

        this.btnSaveDataDB = (Button) this.findViewById(R.id.btSaveDB);

        mStudentAdapter = new StudentAdapter();
        lvdata.setAdapter(mStudentAdapter);

        btngetdata = (Button) findViewById(R.id.btngetdata);

        btngetdata.setOnClickListener(this);
        btnSaveDataDB.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance();




        final DatabaseReference myRef;
        myRef = mDatabase.getReferenceFromUrl(("https://fir-demo-a022a.firebaseio.com/"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i;
                mStudentAdapter.deleteS();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // do SQLite insertion for each data here
                    ChatMessage cm = snapshot.getValue(ChatMessage.class);
                    Student student = new Student(cm.getMessageText());
                    boolean flag=false;

                    for(i=0; i<studenti.size();i++)
                    {
                        if(studenti.get(i).getmFAndLName().equals(student.getmFAndLName()))
                        {
                            flag=true;
                            Log.v("!!!!!jednaki su", studenti.get(i).getmFAndLName());
                            break;
                        }

                    }
                    Log.v("!!!!studenti1:", "  " + studenti);
                    if(flag)
                    {
                        flag = false;
                    }
                    else
                    {
                        studenti.add(i,student);

                    }
                    Log.v("!!!!studenti2:", "  " + studenti);
                    // Toast.makeText(getApplicationContext(), student.toString(), Toast.LENGTH_SHORT).show();
                    //Log.v("!!!!podatak u bazi:", "  " +student.toString());

                    // Toast.makeText(getApplicationContext(), cm.getMessageText(), Toast.LENGTH_SHORT).show();
                    Log.v("!!!!podatak:", "  " + cm.getMessageText());
                    //mStudentAdapter.notifyDataSetChanged();
                }
                Log.v("!!!!studenti4:", "  " + studenti);
                ArrayList<Student> temp=studenti;

                mStudentAdapter.setmStudents(temp);
                i=0;
                Log.v("!!!!studenti3:", "  " + temp);

                //  Log.v("!!!!podatak:", "  " + dataSnapshot.getChildren().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    @Override
    public void onClick(View v) {//https://stackoverflow.com/questions/42709084/android-firebase-retrieve-all-data-in-single-path-and-store-it-in-sqlite
        switch (v.getId()) {
            case R.id.btngetdata:

                break;
            case R.id.btSaveDB:
                /*myRef = mDatabase.getReferenceFromUrl(("https://fir-demo-a022a.firebaseio.com/"));
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // do SQLite insertion for each data here
                            ChatMessage cm = snapshot.getValue(ChatMessage.class);
                            Student student = new Student(cm.getMessageText());
                            FirebasePullDBHelper.getInstance(getApplicationContext()).insertStudents(student);
                            Log.v("!!!!podatak u bazi:", "  " + student.getmFAndLName());
                            Toast.makeText(getApplicationContext(), "Uspjesno uneseno u bazu, ne divljaj ko manijak!!", Toast.LENGTH_SHORT).show();
                            // Toast.makeText(getApplicationContext(), student.toString(), Toast.LENGTH_SHORT).show();
                            //Log.v("!!!!podatak u bazi:", "  " +student.toString());

                            // Toast.makeText(getApplicationContext(), cm.getMessageText(), Toast.LENGTH_SHORT).show();
                            Log.v("!!!!podatak:", "  " + cm.getMessageText());
                            //mStudentAdapter.notifyDataSetChanged();
                        }
                        mStudentAdapter.deleteS();
                        mStudentAdapter.setmStudents(loadStudents());

                        //  Log.v("!!!!podatak:", "  " + dataSnapshot.getChildren().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;*/
        }
    }

    private ArrayList<Student> loadStudents() {
//        mStudentAdapter.deleteS();
        return FirebasePullDBHelper.getInstance(this).getAllStudents();
    }
}