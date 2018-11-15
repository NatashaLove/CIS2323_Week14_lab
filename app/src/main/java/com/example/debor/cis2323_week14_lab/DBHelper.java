package com.example.debor.cis2323_week14_lab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//DBHelper class - has all the methods to be used in main (or elsewhere)
//READ, WRITE etc

public class DBHelper extends SQLiteOpenHelper {

    //constructor
    public DBHelper(Context context) {

        super(context,"TASKS", null, 1);

    }

//creates table, if doesn't exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        // this creates database top (headers)
       db.execSQL("CREATE TABLE TASKS (ID INTEGER PRIMARY KEY AUTOINCREMENT, TASKNAME TEXT NOT NULL);");

    }

    //upgrade from old to new version - deletes old one and creates empty new table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE TABLE IF EXISTS TASKS;"); // SQL requires ; - in the end and "" around text
        onCreate(db);
    }

    //Create data= INSERT (SQL)
    // Read data = SELECT (SQL)


}
