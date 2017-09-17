package com.example.vladimir.firebasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by Vladimir on 16.9.2017..
 */

public class FirebasePullDBHelper extends SQLiteOpenHelper {

    private static FirebasePullDBHelper mFirebasePullDBHelper = null;

    public FirebasePullDBHelper(Context context) {
        super(context.getApplicationContext(), Schema.DATABASE_NAME, null, Schema.SCHEMA_VERSION);
    }


    public static synchronized FirebasePullDBHelper getInstance(Context context) {
        if (mFirebasePullDBHelper == null) {

            mFirebasePullDBHelper = new FirebasePullDBHelper(context);
        }
        return mFirebasePullDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_STUDENTS);
        this.onCreate(db);
    }

    static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + Schema.TABLE_STUDENTS + "(" + Schema.FANDLNAME + " TEXT);";
    static final String DROP_TABLE_STUDENTS = "DROP TABLE IF EXISTS " + Schema.TABLE_STUDENTS;
    static final String SELECT_ALL_STUDENTS = "SELECT * FROM " + Schema.TABLE_STUDENTS;


    public void insertStudents(Student students){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.FANDLNAME, students.getmFAndLName());
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.insert(Schema.TABLE_STUDENTS, Schema.FANDLNAME, contentValues);
        writableDatabase.close();
    }
    public boolean checkStudents(Student studenti){
        boolean flag=false;
        String SELECT_STUDENT = "SELECT * FROM \"students\" WHERE \"username\"=\"" + studenti.getmFAndLName() + "\";";
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(SELECT_STUDENT, null);
        if (cursor.moveToFirst()==true) {
            flag = false;
        }
                else flag=true;
        cursor.close();
        readableDatabase.close();
        return flag;
    }


    public ArrayList<Student> getAllStudents() {
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        Cursor userCursor = writeableDatabase.rawQuery(SELECT_ALL_STUDENTS, null);
        ArrayList<Student> students = new ArrayList<>();
        if (userCursor.moveToFirst()) {
            do {
                String FAndLName = userCursor.getString(0);
                students.add(new Student(FAndLName));
            } while (userCursor.moveToNext());
        }
        userCursor.close();
        writeableDatabase.close();
        return students;
    }

    public static class Schema {
        private static final int SCHEMA_VERSION = 14;
        private static final String DATABASE_NAME = "students.db";
        static final String TABLE_STUDENTS = "students";
        static final String FANDLNAME = "username";
        static final Integer ID = id;
        private static Integer attencdence;
        static final Integer ATTENDENCE = attencdence;
        static final String LECTURE1 = "lecture1";
        static final String LECTURE2 = "lecture2";
        static final String LECTURE3 = "lecture3";
        static final String LECTURE4 = "lecture4";
        static final String LECTURE5 = "lecture5";
        static final String LECTURE6 = "lecture6";
        static final String LECTURE7 = "lecture7";
        static final String LECTURE8 = "lecture8";
    }
}
