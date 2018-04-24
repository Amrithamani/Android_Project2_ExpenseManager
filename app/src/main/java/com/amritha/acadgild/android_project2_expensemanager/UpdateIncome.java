package com.amritha.acadgild.android_project2_expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Amritha on 4/23/18.
 */
public class UpdateIncome extends AppCompatActivity {

    DatabaseExpense mydb;

    Boolean isUpdate;

    TabHost tabHost;

    Intent intent;
    String id;

    String dateIncomeFinal;
    String amountIncomeFinal;
    String notesIncomeFinal;

    static EditText dateEdit, dateEditIncome;

    int startYear = 0, startMonth = 0, startDay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_layout);

        tabHost = findViewById(R.id.tabHost);

        dateEditIncome = findViewById(R.id.date_picker_income_field);

        mydb = new DatabaseExpense(getApplicationContext());
        intent = getIntent();
        isUpdate = intent.getBooleanExtra("isUpdate", false);

        if (isUpdate) {
            init_update();
        }

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("EXPENSE");

        tabSpec.setContent(R.id.EXPENSE);

        tabSpec.setIndicator("EXPENSE");

        tabHost.addTab(tabSpec);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("INCOME");

        tabSpec1.setContent(R.id.INCOME);

        tabSpec1.setIndicator("INCOME");

        tabHost.addTab(tabSpec1);

        dateEditIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }

    public void init_update() {
        id = intent.getStringExtra("id");

        EditText expense_amount = findViewById(R.id.amount_income);
        EditText expense_notes = findViewById(R.id.notes_income);
        dateEdit = findViewById(R.id.date_picker_income_field);

        Cursor task = mydb.getDataSpecificIncome(id);
        if (task != null) {
            task.moveToFirst();

            expense_amount.setText(task.getString(1).toString());
            Calendar cal = Function.Epoch2Calender(task.getString(2).toString());
            startYear = cal.get(Calendar.YEAR);
            startMonth = cal.get(Calendar.MONTH);
            startDay = cal.get(Calendar.DAY_OF_MONTH);
            dateEdit.setText(Function.Epoch2DateString(task.getString(2).toString(), "dd/MM/yyyy"));
            expense_notes.setText(task.getString(3).toString());

        }

    }

    public void buttonSaveClicked(View view) {
        int errorStep = 0;
        EditText income_amount = findViewById(R.id.amount_income);
        EditText income_notes = findViewById(R.id.notes_income);
        double S0 = Double.parseDouble(income_amount.getText().toString());

        amountIncomeFinal = String.valueOf(S0);
        notesIncomeFinal = income_notes.getText().toString();
        dateIncomeFinal = dateEditIncome.getText().toString();

        // Checking
        if (amountIncomeFinal.trim().length() < 2) {
            errorStep++;
            income_amount.setError("Provide a amount.");
        }

        if (notesIncomeFinal.trim().length() < 1) {
            errorStep++;
            income_notes.setError("Provide a description.");
        }

        if (dateIncomeFinal.trim().length() < 4) {
            errorStep++;
            dateEditIncome.setError("Provide a specific date");
        }

        if (errorStep == 0) {

            mydb.updateIncome(id, amountIncomeFinal, dateIncomeFinal, notesIncomeFinal);

            Toast.makeText(getApplicationContext(), "Task updated.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog(View v) {
        android.support.v4.app.DialogFragment dialogFragment = new ExpenseIncome.DatePickerFragment();

        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }
}