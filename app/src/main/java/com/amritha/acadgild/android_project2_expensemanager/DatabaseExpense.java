package com.amritha.acadgild.android_project2_expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Amritha on 4/20/18.
 */
public class DatabaseExpense extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "expenseDB.db";

    // Labels table name
    private static final String TABLE_NAME = "expenseName";

    private static final String TABLE_NAME_INCOME = "IncomeName";


    public DatabaseExpense(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // String values for two tables

    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_NAME +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, amount INTEGER, dateStr INTEGER, notes TEXT )";

    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_NAME_INCOME +
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT, Amount INTEGER, DateStr INTEGER, Notes TEXT )";

    //creating two tables

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXPENSE);

        db.execSQL(CREATE_TABLE_INCOME);
    }

    //upgrading two tables

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INCOME);
        onCreate(db);
    }

    //creating a method for Date format

    private long getDate(String day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
        }
        return date.getTime();
    }

    //Inserting Lists for Table Expense

    public void insertExpense(String amount, String dateStr, String notes) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //Creating Instance of Content values

        ContentValues contentValues = new ContentValues();

        //putting title,description,date inside content values

        contentValues.put("amount", amount);
        contentValues.put("dateStr", getDate(dateStr));
        contentValues.put("notes", notes);

        //inserting content values inside Table Expense

        db.insert(TABLE_NAME, null, contentValues);

        db.close(); //closing database

    }

    //Inserting Lists for Table Income

    public void insertIncome(String amount, String dateStr, String notes) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //Creating Instance of Content values

        ContentValues contentValues = new ContentValues();

        //putting title,description,date inside content values

        contentValues.put("Amount", amount);
        contentValues.put("DateStr", getDate(dateStr));
        contentValues.put("Notes", notes);

        //inserting content values inside Table Income

        db.insert(TABLE_NAME_INCOME, null, contentValues);

        db.close();//closing database

    }

    //Updating Lists for Table Expense

    public void updateExpense(String id, String amount, String dateStr, String notes) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //Creating Instance of Content values

        ContentValues contentValues = new ContentValues();

        //putting title,description,date inside content values

        contentValues.put("amount", amount);
        contentValues.put("dateStr", getDate(dateStr));
        contentValues.put("notes", notes);

        //updating content values inside Table Expense using particular id

        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{id});

        db.close();//closing database

    }

    //Updating Lists for Table Income

    public void updateIncome(String id, String amount, String dateStr, String notes) {
        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //Creating Instance of Content values

        ContentValues contentValues = new ContentValues();

        //putting title,description,date inside content values

        contentValues.put("Amount", amount);
        contentValues.put("DateStr", getDate(dateStr));
        contentValues.put("Notes", notes);

        //updating content values inside Table Income using particular id

        db.update(TABLE_NAME_INCOME, contentValues, "id = ? ", new String[]{id});

        db.close();//closing database

    }

    //Deleting Lists for Table Expense

    public void deleteData(String id) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //deleting content values inside Table Expense using particular id

        db.delete(TABLE_NAME, "id = ?", new String[]{id});

        db.close();//closing database

    }

    //Deleting Lists for Table Income

    public void deleteDataIncome(String id) {

        //Initializing database  to write

        SQLiteDatabase db = this.getWritableDatabase();

        //deleting content values inside Table Income using particular id

        db.delete(TABLE_NAME_INCOME, "id = ?", new String[]{id});

        db.close();//closing database

    }

    //creating cursor method for Table Expense

    public Cursor getData() {

        //reading database

        SQLiteDatabase db = this.getReadableDatabase();

        //getting all the data inside Table Expense by particular order

        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + " order by dateStr asc", null);

        return cur;//returning cursor

    }

    public Cursor getDataSpecific(String id) {

        //reading database

        SQLiteDatabase db = this.getReadableDatabase();

        //getting all the data inside Table Expense by particular order using id

        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + " WHERE id = '" + id + "' order by dateStr asc", null);

        return cur;//returning cursor

    }

    //creating cursor method for Table Income

    public Cursor getDataIncome() {

        //reading database

        SQLiteDatabase db = this.getReadableDatabase();

        //getting all the data inside Table Income by particular order

        Cursor cur1 = db.rawQuery("select * from " + TABLE_NAME_INCOME + " order by DateStr asc", null);

        return cur1;//returning cursor

    }

    public Cursor getDataSpecificIncome(String id) {

        //reading database

        SQLiteDatabase db = this.getReadableDatabase();

        //getting all the data inside Table Income by particular order using id

        Cursor cur = db.rawQuery("select * from " + TABLE_NAME_INCOME + " WHERE id = '" + id + "' order by DateStr asc", null);

        return cur;//returning cursor

    }
}