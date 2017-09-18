package com.example.vladimir.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    ListView lvShowDB;
    StudentAdapter mStudentAdapter;
    Spinner spLectures;
    String spinnerItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        this.lvShowDB = (ListView) this.findViewById(R.id.lvShowDB);


        //preparing spinner
        this.spLectures = (Spinner) this.findViewById(R.id.spLectures);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.Lectures, android.R.layout.simple_spinner_item);


        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLectures.setAdapter(staticAdapter);
        spLectures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerItem = spLectures.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), spinnerItem, Toast.LENGTH_SHORT).show();

                mStudentAdapter = new StudentAdapter(loadStudents());
                // mStudentAdapter.deleteS();
                lvShowDB.setAdapter(mStudentAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });




    }

    private ArrayList<Student> loadStudents() {

       // return FirebasePullDBHelper.getInstance(this).getAllStudents();
        return FirebasePullDBHelper.getInstance(this).getStudentsByAttendance(spinnerItem);
    }
}
