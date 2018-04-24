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
 * Created by Amritha on 4/19/18.
 */
class CategoryNameAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    CategoryNameAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryNameViewHolder holder;
        if (convertView == null) {
            holder = new CategoryNameViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.category_name, parent, false);
            holder.category_name = convertView.findViewById(R.id.category_name);
            convertView.setTag(holder);
        } else {
            holder = (CategoryNameViewHolder) convertView.getTag();
        }
        holder.category_name.setId(position);

        HashMap<String, String> song;
        song = data.get(position);

        try {
            holder.category_name.setText(song.get(AddCategory.KEY_TITLE));

        } catch (Exception e) {
        }


        return convertView;
    }
}

class CategoryNameViewHolder {
    TextView category_name;
}