package com.amritha.acadgild.android_project2_expensemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amritha on 4/18/18.
 */
public class AddCategory extends AppCompatActivity {

    //Initializing Button, Context, Database, String, ListView, Activity, ArrayList

    Button addCategory;

    private Context context = this;

    DatabaseHandler db;

    String categoryName;

    private ListView lv;

    Activity activity;

    ArrayList<HashMap<String, String>> list = new ArrayList<>();

    public static String KEY_ID = "CategoryId";

    public static String KEY_TITLE = "CategoryName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spinner_category_layout);

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //finding view for the button

        addCategory = findViewById(R.id.button);

        //Setting Activity to AddCategory class

        activity = AddCategory.this;

        //creating instance of database using AddCategory

        db = new DatabaseHandler(activity);

        //finding id for List View Items

        lv = findViewById(R.id._dynamic);

        //on Click Listener for Button

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating the dialog

                final Dialog openDialog = new Dialog(context);

                //setting layout for dialog

                openDialog.setContentView(R.layout.add_category_layout);

                //finding views for buttons

                Button dialogCloseButton = openDialog.findViewById(R.id.button4);
                Button dialogSaveButton = openDialog.findViewById(R.id.button3);

                //On Click Event for Button

                dialogSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //initializing Integer variable

                        int errorStep = 0;

                        //finding views for EditText

                        EditText dialogEditTextContent = openDialog.findViewById(R.id.editText);

                        //Converting EditText values to String

                        categoryName = dialogEditTextContent.getText().toString();


                        // Checking for validation
                        if (categoryName.trim().length() < 1) {
                            errorStep++;
                            dialogEditTextContent.setError("Provide a category name.");
                        }

                        //If there is any error in validation it won't insert

                        if (errorStep == 0) {

                            //calling method in database class passing name

                            db.insertCategory(categoryName);

                            //creating a toast

                            Toast.makeText(getApplicationContext(), "Category Added.", Toast.LENGTH_SHORT).show();

                            finish();//finishing after it's done go back to previous activity

                        } else {
                            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                dialogCloseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog.dismiss();//closing dialog
                    }
                });
                openDialog.show();//showing dialog

            }
        });

    }

    //creating instance of Database using AddCategory

    public void populateData() {
        db = new DatabaseHandler(activity);

    }

    //After the page loaded it will update with this class

    @Override
    public void onResume() {
        super.onResume();
        LoadCategoryName loadTask = new LoadCategoryName();//creating a instance of LoadTask class
        loadTask.execute();//executing that class

        populateData();//calling the method

    }

    //creating a class extends AsyncTask runs in background

    public class LoadCategoryName extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

            list.clear();//clearing the list variable using Hash Map

            super.onPreExecute();

        }

        protected String doInBackground(String... arg) {
            String xml = "";

            Cursor today = db.getData();//calling the getData method in Database

            loadDataList(today, list);//calling method passing cursor and list String variables

            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {


            loadListView(lv, list);//calling the method passing List View and Hash Map String

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
                mapToday.put(KEY_TITLE, cursor.getString(1).toString());

                //adding mapToday Variables to ArrayList

                dataList.add(mapToday);

                //moving to next

                cursor.moveToNext();
            }
        }
    }

    public void loadListView(ListView listView, final ArrayList<HashMap<String, String>> dataList) {
        //creating instance of Adapter passing Arguments AddCategory class and ArrayList

        CategoryNameAdapter adapter = new CategoryNameAdapter(activity, dataList);

        //setting the Adapter

        listView.setAdapter(adapter);
    }
}