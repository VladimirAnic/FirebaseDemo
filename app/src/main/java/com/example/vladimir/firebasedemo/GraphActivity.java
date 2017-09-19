package com.example.vladimir.firebasedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lvShowDB;
    StudentAdapter mStudentAdapter;
    Spinner spLectures;
    String spinnerItem;
    Button bAddStudent;
    EditText etNewStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        this.lvShowDB = (ListView) this.findViewById(R.id.lvShowDB);
        bAddStudent = (Button) findViewById(R.id.btnAddNewStudent);
        bAddStudent.setOnClickListener(this);
        etNewStudent = (EditText) findViewById(R.id.etNewStudent);

        //preparing spinner
        this.spLectures = (Spinner) this.findViewById(R.id.spLectures);

        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.Lectures, R.layout.spinner_item);


        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLectures.setAdapter(staticAdapter);
        spLectures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerItem = spLectures.getSelectedItem().toString();
                mStudentAdapter = new StudentAdapter(loadStudents());
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
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Student mName = (Student) parent.getAdapter().getItem(position);
                                String deleteme = mName.getmFAndLName();
                                FirebasePullDBHelper.getInstance(getApplicationContext()).deleteStudent(spinnerItem, deleteme);
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
        return FirebasePullDBHelper.getInstance(this).getStudentsByAttendance(spinnerItem);
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
            startActivity(new Intent(this, LogInActivity.class));
            finish();
        } else if (item.getItemId() == R.id.menu_export) {
            exportDB();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        String StudentName = String.valueOf(etNewStudent.getText());
        if (StudentName != null && !StudentName.equals("")) {
            FirebasePullDBHelper.getInstance(this).insertStudents(new Student(StudentName), spLectures.getSelectedItem().toString());
        }
        etNewStudent.setText("");
        mStudentAdapter.deleteSa();
        mStudentAdapter.setmStudents(loadStudents());
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
