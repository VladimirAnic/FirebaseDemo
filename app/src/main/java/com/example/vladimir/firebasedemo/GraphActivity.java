package com.example.vladimir.firebasedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.Lectures, R.layout.spinner_item);



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



        this.lvShowDB.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Student mName = (Student) parent.getAdapter().getItem(position);
                                String deleteme = mName.getmFAndLName();
                                FirebasePullDBHelper.getInstance(getApplicationContext()).deleteStudent(spinnerItem,deleteme);
                                mStudentAdapter.deleteSa();
                                mStudentAdapter.setmStudents(loadStudents());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            }
        });

    }

    private ArrayList<Student> loadStudents() {

       // return FirebasePullDBHelper.getInstance(this).getAllStudents();
        return FirebasePullDBHelper.getInstance(this).getStudentsByAttendance(spinnerItem);
    }
}
