package com.example.debor.cis2323_week14_lab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

//DBHelper class - has all the methods to be used in main (or elsewhere)
//READ, WRITE etc

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME= "TASKS";
    private static final String DB_COLUMN= "TASKNAME";
    private static final int DB_VER= 1;

    //constructor
    public DBHelper(Context context) {

        super(context,DB_NAME, null, DB_VER);

    }

//creates table, if doesn't exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        // this creates database top (headers)
        String q = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, s% TEXT NOT NULL);", DB_NAME, DB_COLUMN);
        // SQL requires ; - in the end and "" around text
        //%s - is a placeholder- it looks for a String - after it and comma - DB-NAME - only with String.format
       db.execSQL(q);

    }

    //upgrade from old to new version - deletes old one and creates empty new table
    @Override
    //UPDATE (SQL)
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String q = String.format("DELETE TABLE IF EXISTS %s;", DB_NAME);
         // SQL requires ; - in the end and "" around text
        //%s - is a placeholder- it looks for a String - after it and comma - DB-NAME - only with String.format
        db.execSQL(q);
        onCreate(db);
    }

    //Create data= INSERT (SQL) - custom method
    public void insertTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(DB_COLUMN, task);// DB_COLUMN - name of a column, task - is the string/text- task itself
        db.insertWithOnConflict(DB_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE); // replace in case there's smth already - it's =5 in this lib
        db.close();
    }


// Read data = SELECT (SQL)

    //ArrayList <String> - is return type here in this method
    public ArrayList <String> getTasklist () {

        ArrayList<String> taskList = new ArrayList<String>();
        //create a new connection to the db
        SQLiteDatabase db = this.getWritableDatabase();

        //SELECT TaskName, ID, DATE - FROM Tasks (DB_NAME)
        Cursor cursor = db.query(DB_NAME, new String[] {DB_COLUMN}, null, null, null, null, null);
       //cursor moves down the column
        while (cursor.moveToNext()){
           int index = cursor.getColumnIndex(DB_COLUMN);
           //adds a string to ArrayList from DB at the index - where cursor is pointing
           taskList.add(cursor.getString(index));
       }

        cursor.close();// must close cursor before closing db
        //closes connection to db
        db.close();
        return taskList;// returns a list of tasks from the array
    }

    public void deleteTask(String task) {

        //creates a new connection to db
        SQLiteDatabase db = this.getWritableDatabase();

        //DELETE FROM TASKS - WHERE TaskList = "smth"
        //deletes a row from db - which is equal to DB_Column
        db.delete(DB_NAME, DB_COLUMN+ " =d?", new String[]{task} );
        db.close();
    }
}
