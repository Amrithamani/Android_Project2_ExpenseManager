package com.amritha.acadgild.android_project2_expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Amritha on 4/20/18.
 */
public class DeleteTask extends AppCompatActivity {

    //Initializing Database , Boolean, Intent,String variables

    DatabaseExpense mydb;

    Boolean isDelete;

    Intent intent;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //creating instance of Database using Context

        mydb = new DatabaseExpense(getApplicationContext());

        //getting Intent

        intent = getIntent();

        //Getting the passed value from MainActivity

        isDelete = intent.getBooleanExtra("isDelete", false);

        //checking if the boolean value is true

        if (isDelete) {
            deleteTask();//calling a method
            finish();//closing Activity
        }
    }

    private void deleteTask() {

        //getting id from MainActivity class

        id = intent.getStringExtra("id");

        //checking if the boolean value is true

        if (isDelete) {

            mydb.deleteData(id); //calling method from database passing id
            Toast.makeText(getApplicationContext(), "Task Deleted.", Toast.LENGTH_SHORT).show();//creating Toast

        }
    }
}