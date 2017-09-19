package com.example.vladimir.firebasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btngetdata, btnSaveDataDB, btnGraph;

    static ArrayList<Student> studenti = new ArrayList<>();

    private FirebaseDatabase mDatabase;

    int i;
    String spinnerItem;

    ListView lvdata;
    StudentAdapter mStudentAdapter;
    DatabaseReference myRef;
    Spinner spLectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.lvdata = (ListView) this.findViewById(R.id.lvdata);

        this.btnSaveDataDB = (Button) this.findViewById(R.id.btSaveDB);
        //preparing spinner
        this.spLectures = (Spinner) this.findViewById(R.id.spLectures);

        this.btnGraph = (Button) this.findViewById(R.id.btnGraph);


        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.Lectures, R.layout.spinner_item);


        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLectures.setAdapter(staticAdapter);
        spLectures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerItem = spLectures.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        mStudentAdapter = new StudentAdapter();
        lvdata.setAdapter(mStudentAdapter);

        btngetdata = (Button) findViewById(R.id.btngetdata);

        btngetdata.setOnClickListener(this);
        btnSaveDataDB.setOnClickListener(this);
        btnGraph.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance();


        myRef = mDatabase.getReferenceFromUrl(("https://fir-demo-a022a.firebaseio.com/"));
        myRef.removeValue();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mStudentAdapter.deleteS();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // do SQLite insertion for each data here
                    ChatMessage cm = snapshot.getValue(ChatMessage.class);
                    Student student = new Student(cm.getMessageText());
                    boolean flag = false;

                    for (i = 0; i < studenti.size(); i++) {
                        if (studenti.get(i).getmFAndLName().equals(student.getmFAndLName())) {
                            flag = true;
                            break;
                        }

                    }
                    if (flag) {
                        flag = false;
                    } else {
                        studenti.add(i, student);

                    }

                }
                ArrayList<Student> temp = studenti;

                mStudentAdapter.setmStudents(temp);
                i = 0;
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
                startActivity(new Intent(this, GraphActivity.class));
                break;
            case R.id.btSaveDB:

                FirebasePullDBHelper baza = new FirebasePullDBHelper(getApplicationContext());
                for (i = 0; i < studenti.size(); i++) {
                    baza.insertStudents(studenti.get(i), spinnerItem);
                }
                startActivity(new Intent(this, GraphActivity.class));
                studenti.clear();
                mStudentAdapter.deleteS();
                finish();
                break;
            case R.id.btnGraph:
                startActivity(new Intent(this, VisualisationActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            FirebaseAuth.getInstance().signOut();
            finish();
        } else if (item.getItemId() == R.id.menu_export) {
            exportDB();
        }
        return true;
    }

    public void exportDB() {

        FirebasePullDBHelper dbhelper = new FirebasePullDBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "potpisi.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM students", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3), curCSV.getString(4), curCSV.getString(5), curCSV.getString(6), curCSV.getString(7), curCSV.getString(8), curCSV.getString(9), curCSV.getString(10)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.e("!!!!!!!!!!Export Error", sqlEx.getMessage(), sqlEx);
        }
        Uri u1 = null;
        u1 = Uri.fromFile(file);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Export database");
        sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
        sendIntent.setType("text/html");
        startActivity(sendIntent);
    }
}