package com.example.vladimir.firebasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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

    static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + Schema.TABLE_STUDENTS + "(" + Schema.FANDLNAME + " TEXT, " + Schema.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ Schema. ATTENDENCE + " INTEGER, "+ Schema.LECTURE1 + " INTEGER, "
            + Schema.LECTURE2 + " INTEGER, "+ Schema.LECTURE3 + " INTEGER, "+ Schema.LECTURE4 + " INTEGER, "+ Schema.LECTURE5 + " INTEGER, "+ Schema.LECTURE6 + " INTEGER, "+ Schema.LECTURE7 + " INTEGER, "+ Schema.LECTURE8 + " INTEGER);";
    static final String DROP_TABLE_STUDENTS = "DROP TABLE IF EXISTS " + Schema.TABLE_STUDENTS;
    static final String SELECT_ALL_STUDENTS = "SELECT * FROM " + Schema.TABLE_STUDENTS;


    public void insertStudents(Student student, String lecture){
        Integer id=checkStudents(student);

        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        if(id==null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(Schema.FANDLNAME, student.getmFAndLName());
            contentValues.put(Schema.ATTENDENCE,1);
            if(lecture.equals("Lecture1"))
                contentValues.put(Schema.LECTURE1, 1);
            else if(lecture.equals("Lecture2"))
                contentValues.put(Schema.LECTURE2, 1);
            else if(lecture.equals("Lecture3"))
                contentValues.put(Schema.LECTURE3, 1);
            else if(lecture.equals("Lecture4"))
                contentValues.put(Schema.LECTURE4, 1);
            else if(lecture.equals("Lecture5"))
                contentValues.put(Schema.LECTURE5, 1);
            else if(lecture.equals("Lecture6"))
                contentValues.put(Schema.LECTURE6, 1);
            else if(lecture.equals("Lecture7"))
                contentValues.put(Schema.LECTURE7, 1);
            else if(lecture.equals("Lecture8"))
                contentValues.put(Schema.LECTURE8, 1);
            writableDatabase.insert(Schema.TABLE_STUDENTS, Schema.FANDLNAME, contentValues);
        }
        else
        {

             final String UPDATE_STUDENTS = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " +  Schema.ATTENDENCE+ " = " + Schema.ATTENDENCE+ " + " + 1 +" WHERE " + Schema.ID + " = " + id + " ;";
            writableDatabase.execSQL(UPDATE_STUDENTS);


            final String GET_ATTENDENCE = "SELECT "+ Schema.ATTENDENCE + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID +" = "+ id+ ";";
            Cursor userCursor = writableDatabase.rawQuery(GET_ATTENDENCE, null);
            if(userCursor.moveToFirst())
            {
                do{
                    Log.v("!!!!id", String.valueOf(id));
                    Log.v("!!!!attendance", String.valueOf(userCursor.getInt(0)));
                }while(userCursor.moveToNext());
            }
              userCursor.close();
        }
        writableDatabase.close();
    }
    public Integer checkStudents(Student studenti){
        Integer flag=null;
        String SELECT_STUDENT = "SELECT * FROM \"students\" WHERE \"username\"=\"" + studenti.getmFAndLName() + "\";";
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(SELECT_STUDENT, null);
        if (cursor.moveToFirst()==true) {
            flag = cursor.getInt(1);
            Log.v("!!!flag", String.valueOf(flag));

        }
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
        private static final int SCHEMA_VERSION = 23;
        private static final String DATABASE_NAME = "students.db";
        static final String TABLE_STUDENTS = "students";
        static final String FANDLNAME = "username";
        static final String ID="id";
        static final String ATTENDENCE="attendence";
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
