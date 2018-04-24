package com.amritha.acadgild.android_project2_expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Amritha on 4/22/18.
 */
public class Settings extends AppCompatActivity {

    //Initializing Switch, TextView, Strings

    Switch switchButton;

    TextView switchText;

    String switchOn = "ON";

    String switchOff = "OFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        // For switch button
        switchButton = findViewById(R.id.switch1);
        switchText = findViewById(R.id.textView5);

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //default button is not checked

        switchButton.setChecked(false);

        //ON Click Listener for Switch Button

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {

                    switchText.setText(switchOn);//setting the text

                    Intent intent = new Intent(Settings.this, BalanceView.class);
                    startActivity(intent);//starting new Activity

                } else {
                    switchText.setText(switchOff);//setting the text
                }
            }
        });

        //checking whether the button is checked or not

        if (switchButton.isChecked()) {
            //setting the text
            switchText.setText(switchOn);//setting the text

        } else {
            //setting the text
            switchText.setText(switchOff);
        }
    }
}