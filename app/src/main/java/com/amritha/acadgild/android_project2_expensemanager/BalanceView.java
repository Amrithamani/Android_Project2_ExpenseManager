package com.amritha.acadgild.android_project2_expensemanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amritha on 4/22/18.
 */
public class BalanceView extends AppCompatActivity {

    //Initializing Button, Context, Database, Double, Strings, ListViews, Activity, ArrayLists, TextView

    private ListView lv, lv1;

    Activity activity;

    DatabaseExpense mydb;

    ArrayList<HashMap<String, String>> list = new ArrayList<>();

    ArrayList<HashMap<String, String>> list1 = new ArrayList<>();

    public static String KEY_ID = "ExpenseID";

    public static String KEY_AMOUNT = "ExpenseAmount";

    public static String KEY_DESCRIPTION = "Description";

    public static String KEY_DATE = "DueDate";

    TextView BalanceAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_layout);

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Setting Activity to BalanceView class

        activity = BalanceView.this;

        //creating instance of database using BalanceView

        mydb = new DatabaseExpense(activity);

        //finding id for EditTexts, TextView and List View Items

        lv = findViewById(R.id.lvItem1);

        lv1 = findViewById(R.id.lvItem2);

    }

    //creating instance of Database using BalanceView

    public void populateData() {
        mydb = new DatabaseExpense(activity);

    }

    //After the page loaded it will update with this class

    @Override
    public void onResume() {
        super.onResume();
        LoadTask loadTask = new LoadTask();//creating a instance of LoadTask class
        loadTask.execute();//executing that class
        populateData();//calling the method

    }

    //creating a class extends AsyncTask runs in background

    public class LoadTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

            //clearing the lists variable using Hash Map
            list.clear();
            list1.clear();

            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            String xml = "";

            //calling the getData method in Database

            Cursor today = mydb.getData();

            Cursor today1 = mydb.getDataIncome();

            //calling method passing cursor and list String variables

            loadDataList(today, list);
            loadDataList(today1, list1);

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            //calling the method passing List Views and Hash Map Strings

            loadListView(lv, list);
            loadListView(lv1, list1);

        }
    }

    public void loadDataList(Cursor cursor, ArrayList<HashMap<String, String>> dataList) {
        //checking whether  the cursor is not equal to null

        if (cursor != null) {

            //moving to first position in cursor

            cursor.moveToFirst();

            //cursor will go till its last position

            while (cursor.isAfterLast() == false) {

                //creating another HashMap variable mapToday

                HashMap<String, String> mapToday = new HashMap<>();

                //putting Strings inside mapToday

                mapToday.put(KEY_ID, cursor.getString(0).toString());
                mapToday.put(KEY_AMOUNT, cursor.getString(1).toString());
                mapToday.put(KEY_DATE, Function.Epoch2DateString(cursor.getString(2).toString(), "dd-MM-yyyy"));
                mapToday.put(KEY_DESCRIPTION, cursor.getString(3).toString());

                //adding mapToday Variables to ArrayList

                dataList.add(mapToday);

                //moving to next

                cursor.moveToNext();
            }
        }
    }

    public void loadListView(ListView listView, final ArrayList<HashMap<String, String>> dataList) {

        //creating instance of Adapter passing Arguments BalanceView class and ArrayList

        ExpenseListAdapter adapter = new ExpenseListAdapter(activity, dataList);

        //setting the Adapter

        listView.setAdapter(adapter);

    }
}