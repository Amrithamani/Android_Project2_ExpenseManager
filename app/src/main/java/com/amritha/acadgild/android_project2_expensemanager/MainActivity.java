package com.amritha.acadgild.android_project2_expensemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.audiofx.BassBoost;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Initializing Toolbar, DrawerLayout, ActionBarDrawerToggle, FloatingActionButton

    Toolbar toolbar;

    DrawerLayout drawer;

    ActionBarDrawerToggle toggle;

    FloatingActionButton fab;

    //Initializing ListView, Activity, Database, ArrayLists

    private ListView lv, lv1;

    Activity activity;

    DatabaseExpense mydb;

    ArrayList<HashMap<String, String>> list = new ArrayList<>();

    ArrayList<HashMap<String, String>> list1 = new ArrayList<>();

    //Initializing all table Contents

    public static String KEY_ID = "ExpenseID";

    public static String KEY_AMOUNT = "ExpenseAmount";

    public static String KEY_DESCRIPTION = "Description";

    public static String KEY_DATE = "DueDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding id for toolbar

        toolbar = findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);//setting the toolbar

        //finding id for DrawerLayout and FloatingActionButton

        drawer = findViewById(R.id.drawer_layout);

        fab = findViewById(R.id.fab);

        //On Click Listener for FloatingActionButton

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExpenseIncome.class);
                startActivity(i);//starting ExpenseIncome Activity
            }
        });

        //setting drawer toggle state

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Setting Activity to MainActivity class

        activity = MainActivity.this;

        //creating instance of database using MainActivity

        mydb = new DatabaseExpense(activity);

        //finding id for List View Items

        lv = findViewById(R.id.lvItems);

        lv1 = findViewById(R.id.lvItems1);

    }

    //overriding navigation drawer functions

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    // Implement method onCreateOptionsMenu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.activity_main_drawer, menu);//inflating layout
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //checking the id to match nav_item_two

        if (item.getItemId() == R.id.nav_item_two) {

            Intent i = new Intent(MainActivity.this, Settings.class);
            this.startActivity(i);//starting Settings Activity
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //creating instance of Database using MainActivity

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

            //calling the getData method and getDataIncome in Database

            Cursor today = mydb.getData();
            Cursor today1 = mydb.getDataIncome();

            //calling method passing cursors and list String variables

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

        //creating instance of Adapter passing Arguments MainActivity class and ArrayList

        ExpenseListAdapter adapter = new ExpenseListAdapter(activity, dataList);

        //setting the Adapter

        listView.setAdapter(adapter);

        //checking whether dataList is equal to List

        if (dataList == list) {

            //on Click Listener for List View

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                    //creating the dialog clicking List View

                    final Dialog openDialog = new Dialog(MainActivity.this);

                    openDialog.setContentView(R.layout.update_delete_layout);//setting layout for dialog

                    openDialog.show();//showing dialog

                    //finding views for TextViews

                    TextView updateExpense = openDialog.findViewById(R.id.update_expense);

                    TextView deleteExpense = openDialog.findViewById(R.id.delete_expense);

                    //On Click Event for TextViews

                    updateExpense.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(activity, ExpenseIncome.class);
                            i.putExtra("isUpdate", true);
                            i.putExtra("id", dataList.get(+position).get(KEY_ID));
                            startActivity(i);
                            openDialog.dismiss();
                        }
                    });

                    deleteExpense.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(activity, DeleteTask.class);//creating external Intent

                            i.putExtra("isDelete", true);//passing boolean value for update

                            i.putExtra("id", dataList.get(+position).get(KEY_ID));//passing all the data lists

                            startActivity(i);//starting a new Activity

                            openDialog.dismiss();//finishing or closing the dialog

                        }
                    });
                }
            });
        }

        //checking whether dataList is equal to list1

        if (dataList == list1) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Dialog openDialog = new Dialog(MainActivity.this);

                    openDialog.setContentView(R.layout.update_delete_layout);

                    openDialog.show();

                    TextView updateExpense = openDialog.findViewById(R.id.update_expense);

                    TextView deleteExpense = openDialog.findViewById(R.id.delete_expense);


                    updateExpense.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(activity, UpdateIncome.class);
                            i.putExtra("isUpdate", true);
                            i.putExtra("id", dataList.get(+position).get(KEY_ID));
                            startActivity(i);
                            openDialog.dismiss();
                        }
                    });

                    deleteExpense.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(activity, DeleteIncome.class);
                            i.putExtra("isDelete", true);
                            i.putExtra("id", dataList.get(+position).get(KEY_ID));
                            startActivity(i);
                            openDialog.dismiss();

                        }
                    });
                }
            });
        }

    }

}
