package com.amritha.acadgild.android_project2_expensemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amritha on 4/13/18.
 */
public class ExpenseIncome extends AppCompatActivity {

    //Initializing Database and TabHost

    DatabaseExpense mydb;

    TabHost tabHost;

    //Initializing year, month, day Integer variable

    int startYear = 0, startMonth = 0, startDay = 0;

    //Initializing TextView, Boolean, Intent, String

    TextView categoryExpense, categoryIncome;

    Boolean isUpdate;

    Intent intent;

    String id;

    //creating EditText static variables

    static EditText dateEdit, dateEditIncome;

    //Initializing String variables for title,description,date for two tables

    String dateFinal;
    String amountFinal;
    String notesFinal;

    String dateIncomeFinal;
    String amountIncomeFinal;
    String notesIncomeFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_layout);

        //finding ids for TabHost, TextView, EditText

        tabHost = findViewById(R.id.tabHost);

        categoryExpense = findViewById(R.id.category_field);

        categoryIncome = findViewById(R.id.category_income_field);

        dateEdit = findViewById(R.id.date_edit);

        dateEditIncome = findViewById(R.id.date_picker_income_field);

        //creating instance of Database using Context

        mydb = new DatabaseExpense(getApplicationContext());

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setting the TabHost

        tabHost.setup();

        //setting the two tabs with tab names

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("EXPENSE");

        tabSpec.setContent(R.id.EXPENSE);

        tabSpec.setIndicator("EXPENSE");

        tabHost.addTab(tabSpec);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("INCOME");

        tabSpec1.setContent(R.id.INCOME);

        tabSpec1.setIndicator("INCOME");

        tabHost.addTab(tabSpec1);

        //creating Date variable

        Date your_date = new Date();

        //setting date for calendar

        Calendar cal = Calendar.getInstance();
        cal.setTime(your_date);
        startYear = cal.get(Calendar.YEAR);
        startMonth = cal.get(Calendar.MONTH);
        startDay = cal.get(Calendar.DAY_OF_MONTH);

        //getting Intent

        intent = getIntent();

        //Getting the passed value from MainActivity

        isUpdate = intent.getBooleanExtra("isUpdate", false);

        //Creating on Click event for TextViews and EditTexts

        categoryExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseIncome.this, AddCategory.class);
                startActivity(intent);//starting new Activity
            }
        });

        categoryIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseIncome.this, AddCategory.class);
                startActivity(intent);//starting new Activity
            }
        });

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);//calling method
            }
        });

        dateEditIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);//calling method
            }
        });

        //checking the boolean value is true

        if (isUpdate) {
            init_update();//calling a method
        }

    }

    public void init_update() {

        //getting id from MainActivity class

        id = intent.getStringExtra("id");

        //getting ids for the EditText

        EditText expense_amount = findViewById(R.id.amount_expense);
        EditText expense_notes = findViewById(R.id.notes_expense);

        //calling Database method getDataSpecific passing id

        Cursor task = mydb.getDataSpecific(id);

        //checking cursor position is not equal to null

        if (task != null) {

            //move to first position

            task.moveToFirst();

            //setting all the values from particular column to EditText

            expense_amount.setText(task.getString(1).toString());

            //calling Method in Function class

            Calendar cal = Function.Epoch2Calender(task.getString(2).toString());
            startYear = cal.get(Calendar.YEAR);
            startMonth = cal.get(Calendar.MONTH);
            startDay = cal.get(Calendar.DAY_OF_MONTH);

            //setting date text by calling method in Function class

            dateEdit.setText(Function.Epoch2DateString(task.getString(2).toString(), "dd/MM/yyyy"));

            expense_notes.setText(task.getString(3).toString());

        }

    }


    public void showDatePickerDialog(View v) {

        //creating instance of DatePickerFragment class

        android.support.v4.app.DialogFragment dialogFragment = new DatePickerFragment();

        //showing the dialog using fragment for Date

        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void buttonClicked(View view) {

        //initializing Integer variable

        int errorStep = 0;

        //getting id for All Edit Text

        EditText expense_amount = findViewById(R.id.amount_expense);
        EditText expense_notes = findViewById(R.id.notes_expense);
        double S0 = Double.parseDouble(expense_amount.getText().toString());

        //Converting EditText values to String

        amountFinal = String.valueOf(S0);
        notesFinal = expense_notes.getText().toString();
        dateFinal = dateEdit.getText().toString();

        // Checking for validation

        if (amountFinal.trim().length() < 2) {
            errorStep++;
            expense_amount.setError("Provide a amount.");
        }

        if (notesFinal.trim().length() < 1) {
            errorStep++;
            expense_notes.setError("Provide a description.");
        }

        if (dateFinal.trim().length() < 4) {
            errorStep++;
            dateEdit.setError("Provide a specific date");
        }

        //If there is any error in validation it won't insert or update

        if (errorStep == 0) {

            //checking whether the boolean value is true

            if (isUpdate) {

                //calling method in database class passing id,title, date, notes

                mydb.updateExpense(id, amountFinal, dateFinal, notesFinal);

                //creating a toast

                Toast.makeText(getApplicationContext(), "Task Updated.", Toast.LENGTH_SHORT).show();

            } else {

                //calling method in database class passing title, date, notes

                mydb.insertExpense(amountFinal, dateFinal, notesFinal);

                //creating a toast

                Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();
            }

            finish();//finishing after it's done go back to previous activity

        } else {
            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
        }

    }

    public void buttonSaveClicked(View view) {

        //initializing Integer variable

        int errorStep = 0;

        //getting id for All Edit Text

        EditText income_amount = findViewById(R.id.amount_income);
        EditText income_notes = findViewById(R.id.notes_income);
        double S0 = Double.parseDouble(income_amount.getText().toString());

        //Converting EditText values to String

        amountIncomeFinal = String.valueOf(S0);
        notesIncomeFinal = income_notes.getText().toString();
        dateIncomeFinal = dateEditIncome.getText().toString();

        // Checking for validation

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

        //If there is any error in validation it won't insert

        if (errorStep == 0) {

            //calling method in database class passing title, date, notes

            mydb.insertIncome(amountIncomeFinal, dateIncomeFinal, notesIncomeFinal);

            //creating a toast

            Toast.makeText(getApplicationContext(), "Task Added.", Toast.LENGTH_SHORT).show();

            finish();//finishing after it's done go back to previous activity

        } else {
            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
        }
    }

    //creating static class for DatePicker Dialog extends Dialog Fragment and implements DatePickerDialog

    public static class DatePickerFragment extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        //overriding onDateSet method for setting the date

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Do something with the date chosen by the user
            dateEdit.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            dateEditIncome.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }
    }

}
