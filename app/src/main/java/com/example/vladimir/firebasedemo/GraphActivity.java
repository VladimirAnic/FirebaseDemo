package com.example.vladimir.firebasedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    ListView lvShowDB;
    StudentAdapter mStudentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        this.lvShowDB = (ListView) this.findViewById(R.id.lvShowDB);


        mStudentAdapter = new StudentAdapter(loadStudents());
       // mStudentAdapter.deleteS();
        lvShowDB.setAdapter(mStudentAdapter);

    }

    private ArrayList<Student> loadStudents() {

        return FirebasePullDBHelper.getInstance(this).getAllStudents();
    }
}
