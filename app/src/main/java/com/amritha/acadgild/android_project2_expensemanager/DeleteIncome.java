package com.amritha.acadgild.android_project2_expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Amritha on 4/23/18.
 */
public class DeleteIncome extends AppCompatActivity {

    DatabaseExpense mydb;

    Boolean isDelete;

    Intent intent;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mydb = new DatabaseExpense(getApplicationContext());
        intent = getIntent();
        isDelete = intent.getBooleanExtra("isDelete", false);

        if (isDelete) {
            deleteTask();
            finish();
        }
    }

    private void deleteTask() {

        id = intent.getStringExtra("id");

        if (isDelete) {
            mydb.deleteDataIncome(id);
            Toast.makeText(getApplicationContext(), "Task Deleted.", Toast.LENGTH_SHORT).show();
        }
    }
}