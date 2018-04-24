package com.amritha.acadgild.android_project2_expensemanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Amritha on 4/20/18.
 */

//class extending Base Adapter

class ExpenseListAdapter extends BaseAdapter {

    //Initializing Activity variable and ArrayList

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    //creating constructor for the class having two parameters

    ExpenseListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    //overriding all these methods for count and id

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    //overriding getView method

    public View getView(int position, View convertView, ViewGroup parent) {

        //Initializing  ExpenseListViewHolder

        ExpenseListViewHolder holder;

        //checking convertView is equal to null

        if (convertView == null) {
            holder = new ExpenseListViewHolder();//creating instance of ExpenseListViewHolder
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.to_do_expense_list, parent, false);//Inflating the to_do_list layout in particular activity

            //Assigning values to all the variables in holder instance variable

            holder.expense_amount = convertView.findViewById(R.id.txtView1);
            holder.expense_date = convertView.findViewById(R.id.txtView2);
            holder.expense_notes = convertView.findViewById(R.id.txtView3);

            convertView.setTag(holder);
        } else {
            holder = (ExpenseListViewHolder) convertView.getTag();
        }

        //setting the variables in particular position

        holder.expense_amount.setId(position);
        holder.expense_date.setId(position);
        holder.expense_notes.setId(position);

        HashMap<String, String> song;
        song = data.get(position);//Assigning the position of data ArrayList to song ArrayList

        try {
            //setting the Keys from MainActivity to holder

            holder.expense_amount.setText(song.get(MainActivity.KEY_AMOUNT));
            holder.expense_date.setText(song.get(MainActivity.KEY_DATE));
            holder.expense_notes.setText(song.get(MainActivity.KEY_DESCRIPTION));

        } catch (Exception e) {
        }


        return convertView;//returning the views
    }
}

class ExpenseListViewHolder {

    //Initializing TextView Variables

    TextView expense_amount, expense_notes, expense_date;
}