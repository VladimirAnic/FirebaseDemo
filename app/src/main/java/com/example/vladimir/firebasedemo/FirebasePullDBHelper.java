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
        Log.v("!!!!koji lecture:", lecture);
        if(id==null){
            ContentValues contentValues = new ContentValues();
            contentValues.put(Schema.FANDLNAME, student.getmFAndLName());
            contentValues.put(Schema.ATTENDENCE,1);
            if(lecture.equals("Lecture1")) {
                contentValues.put(Schema.LECTURE1, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture2")) {
                contentValues.put(Schema.LECTURE2, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture3")) {
                contentValues.put(Schema.LECTURE3, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture4")) {
                contentValues.put(Schema.LECTURE4, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture5")) {
                contentValues.put(Schema.LECTURE5, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture6")) {
                contentValues.put(Schema.LECTURE6, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture7")) {
                contentValues.put(Schema.LECTURE7, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            else if(lecture.equals("Lecture8")) {
                contentValues.put(Schema.LECTURE8, 1);
                Log.v("!!!!!lecture", contentValues.toString());
            }
            writableDatabase.insert(Schema.TABLE_STUDENTS, Schema.FANDLNAME, contentValues);
        }
        else
        {

             final String UPDATE_STUDENTS = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " +  Schema.ATTENDENCE+ " = " + Schema.ATTENDENCE+ " + " + 1 +" WHERE " + Schema.ID + " = " + id + " ;";
            writableDatabase.execSQL(UPDATE_STUDENTS);

            final String UPDATE_LECTURE1 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE1 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE2 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE2 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE3 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE3 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE4 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE4 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE5 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE5 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE6 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE6 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE7 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE7 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String UPDATE_LECTURE8 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE8 + " = 1" + " WHERE "+ Schema.ID + " = " + id + " ;";


            final String GET_ATTENDENCE = "SELECT "+ Schema.ATTENDENCE + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID +" = "+ id+ ";";

/*
            final String GET_STUDENTS_BY_LECTURE1 = "SELECT "+ Schema.LECTURE1 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE2 = "SELECT "+ Schema.LECTURE2 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE3 = "SELECT "+ Schema.LECTURE3 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE4 = "SELECT "+ Schema.LECTURE4 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE5 = "SELECT "+ Schema.LECTURE5 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE6 = "SELECT "+ Schema.LECTURE6 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE7 = "SELECT "+ Schema.LECTURE7 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";
            final String GET_STUDENTS_BY_LECTURE8 = "SELECT "+ Schema.LECTURE8 + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.ID + " = " + id + " ;";

*/

           Cursor userCursor = writableDatabase.rawQuery(GET_ATTENDENCE, null);
            if(userCursor.moveToFirst())
            {
                do{
                    Log.v("!!!!id", String.valueOf(id));
                    Log.v("!!!!attendance", String.valueOf(userCursor.getInt(0)));
                }while(userCursor.moveToNext());
            }
            if(lecture.equals("Lecture1"))
            {
                writableDatabase.execSQL(UPDATE_LECTURE1);

            }
            else if(lecture.equals("Lecture2"))
            {
               writableDatabase.execSQL(UPDATE_LECTURE2);
                Log.v("!!!lecture update taj i taj", lecture);

            }
            else if(lecture.equals("Lecture3"))
            {
                writableDatabase.execSQL(UPDATE_LECTURE3);
                Log.v("!!!lecture update taj i taj", lecture);


            }
            else if(lecture.equals("Lecture4"))
            {
                writableDatabase.execSQL(UPDATE_LECTURE4);
                Log.v("!!!lecture update taj i taj", lecture);


            }
            else if(lecture.equals("Lecture5"))
            {
               writableDatabase.execSQL(UPDATE_LECTURE5);
                Log.v("!!!lecture update taj i taj", lecture);


            }
            else if(lecture.equals("Lecture6"))
            {
                writableDatabase.execSQL(UPDATE_LECTURE6);
                Log.v("!!!lecture update taj i taj", lecture);


            }
            else if(lecture.equals("Lecture7"))
            {
                writableDatabase.execSQL(UPDATE_LECTURE7);
                Log.v("!!!lecture update taj i taj", lecture);


            }
            else if(lecture.equals("Lecture8"))
            {
                writableDatabase.execSQL(UPDATE_LECTURE8);
                Log.v("!!!lecture update taj i taj", lecture);


            }


/*

            if(lecture.equals("Lecture1"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE1, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }

            }
            else if(lecture.equals("Lecture2"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE2, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }

            }
            else if(lecture.equals("Lecture3"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE3, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }


            }
            else if(lecture.equals("Lecture4"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE4, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }


            }
            else if(lecture.equals("Lecture5"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE5, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }


            }
            else if(lecture.equals("Lecture6"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE6, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }


            }
            else if(lecture.equals("Lecture7"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE7, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }


            }
            else if(lecture.equals("Lecture8"))
            {
                userCursor = writableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE8, null);
                if(userCursor.moveToFirst())
                {
                    do{
                        Log.v("!!!!id", String.valueOf(id));
                        Log.v("!!!!lecture get", String.valueOf(userCursor.getInt(0)));
                    }while(userCursor.moveToNext());
                }


            }*/
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

    public ArrayList<Student> getStudentsByAttendance(String spGetString) {
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        ArrayList<Student> students = new ArrayList<>();
        final String GET_STUDENTS_BY_LECTURE1 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE1 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE2 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE2 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE3 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE3 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE4 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE4 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE5 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE5 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE6 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE6 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE7 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE7 +" =  1;";
        final String GET_STUDENTS_BY_LECTURE8 = "SELECT "+ Schema.FANDLNAME + " FROM "+ Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE8 +" =  1;";
        if(spGetString.equals("Lecture1"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE1, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture2"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE2, null);

            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture3"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE3, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture4"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE4, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture5"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE5, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture6"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE6, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture7"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE7, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        else if(spGetString.equals("Lecture8"))
        {
            Cursor userCursor = writeableDatabase.rawQuery(GET_STUDENTS_BY_LECTURE8, null);
            if (userCursor.moveToFirst()) {
                do {
                    String FAndLName = userCursor.getString(0);
                    students.add(new Student(FAndLName));
                } while (userCursor.moveToNext());
            }
            userCursor.close();

        }
        writeableDatabase.close();
        return students;
    }

    public void deleteStudent(String Lecture, String Name)
    {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        final String UPDATE_DELETE_LECTURE1 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE1 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE2 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE2 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE3 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE3 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE4 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE4 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE5 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE5 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE6 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE6 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE7 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE7 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_LECTURE8 = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " + Schema.LECTURE8 + " = 0" + " WHERE "+ Schema.FANDLNAME + " = \"" + Name + "\" ;";
        final String UPDATE_DELETE_STUDENTS = "UPDATE " + Schema.TABLE_STUDENTS+ " SET " +  Schema.ATTENDENCE+ " = " + Schema.ATTENDENCE+ " - 1 "  +" WHERE " + Schema.FANDLNAME + " = \" " + Name + "\" ;";

        writableDatabase.execSQL(UPDATE_DELETE_STUDENTS);

        if(Lecture.equals("Lecture1"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE1);

        }
        else if(Lecture.equals("Lecture2"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE2);


        }
        else if(Lecture.equals("Lecture3"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE3);



        }
        else if(Lecture.equals("Lecture4"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE4);



        }
        else if(Lecture.equals("Lecture5"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE5);



        }
        else if(Lecture.equals("Lecture6"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE6);



        }
        else if(Lecture.equals("Lecture7"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE7);



        }
        else if(Lecture.equals("Lecture8"))
        {
            writableDatabase.execSQL(UPDATE_DELETE_LECTURE8);


        }

    }

    public Float[] countStudentsPerLecture(){
        Float[] students = new Float[8];
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        final String COUNT_STUDENTS_ON_LECTURE1 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE1 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE2 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE2 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE3 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE3 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE4 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE4 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE5 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE5 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE6 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE6 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE7 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE7 + " = 1;";
        final String COUNT_STUDENTS_ON_LECTURE8 = "SELECT COUNT(*) FROM " + Schema.TABLE_STUDENTS + " WHERE "+ Schema.LECTURE8 + " = 1;";

        Cursor userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE1, null);
        if (userCursor.moveToFirst()) {
            do {
                students[0] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE2, null);
        if (userCursor.moveToFirst()) {
            do {
                students[1] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE3, null);
        if (userCursor.moveToFirst()) {
            do {
                students[2] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE4, null);
        if (userCursor.moveToFirst()) {
            do {
                students[3] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE5, null);
        if (userCursor.moveToFirst()) {
            do {
                students[4] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE6, null);
        if (userCursor.moveToFirst()) {
            do {
                students[5] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE7, null);
        if (userCursor.moveToFirst()) {
            do {
                students[6] =(float) userCursor.getInt(0);
            } while (userCursor.moveToNext());
        }
        userCursor = writableDatabase.rawQuery(COUNT_STUDENTS_ON_LECTURE8, null);
        if (userCursor.moveToFirst()) {
            do {
                students[7] =(float) userCursor.getInt(0);
               // Log.v("!!!!count", (float) userCursor.getInt(0))
            } while (userCursor.moveToNext());
        }
        userCursor.close();
        return students;
    }

    public static class Schema {
        private static final int SCHEMA_VERSION = 25;
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
