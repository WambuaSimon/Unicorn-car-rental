package com.wizag.unicorn.ui.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wizag.unicorn.R;
import com.wizag.unicorn.utils.Constants;
import com.wizag.unicorn.utils.MySingleton;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Reserve extends AppCompatActivity {
    TextView pick_up, drop_off, pick_date, pick_time, drop_date, drop_time;
    CheckBox terms, driver;
    TextView rental_terms;
    Button make_reservation;

    String driverCost, daily_rate;
    String needDriver;
    String parentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_reserve);
            init();

    }




    void init() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pick_up = findViewById(R.id.pick_up);
        drop_off = findViewById(R.id.drop_off);
        pick_date = findViewById(R.id.pick_date);
//        pick_time = findViewById(R.id.pick_time);
        drop_date = findViewById(R.id.drop_date);
//        drop_time = findViewById(R.id.drop_time);
        terms = findViewById(R.id.terms);
        driver = findViewById(R.id.driver);

        rental_terms = findViewById(R.id.rental_terms);
        make_reservation = findViewById(R.id.make_reservation);



        /*get Daily rate value*/
        SharedPreferences prefs = getSharedPreferences("search_car", MODE_PRIVATE);
        String pick_location = prefs.getString("pick_up_location", null);//"No name defined" is the default value.
        String drop_location = prefs.getString("drop_off_location", null);//"No name defined" is the default value.
        String pick_date_txt = prefs.getString("pick_up_date", null);//"No name defined" is the default value.
        String drop_date_txt = prefs.getString("drop_off_date", null);//"No name defined" is the default value.
        String pick_time_txt = prefs.getString("pick_up_time", null);//"No name defined" is the default value.
        String drop_time_txt = prefs.getString("drop_off_time", null);

        pick_up.setText(pick_location);
        drop_off.setText(drop_location);

        pick_date.setText(pick_date_txt + "   " + pick_time_txt);
        drop_date.setText(drop_date_txt + "   " + drop_time_txt);


        /*get ride cost*/
        Intent intent = getIntent();
        daily_rate = intent.getStringExtra("dailyRate");
        driverCost = intent.getStringExtra("driver_cost");

//        if (driver.isChecked()) {
//            needDriver = "1";
//        } else if (!driver.isChecked()) {
//            needDriver = "0";
//        }


        make_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (terms.isChecked()) {

                    if (driver.isChecked()) {
                        needDriver = "1";
                    } else if (!driver.isChecked()) {
                        needDriver = "0";
                    }


                    Intent intent = new Intent(getApplicationContext(), Activity_Payment.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("dailyRate", daily_rate);
                    intent.putExtra("driver_cost", driverCost);
                    intent.putExtra("needDriver", needDriver);
//                    intent.putExtra("parentName", parentName);
                    startActivity(intent);
                    finish();
                } else {
                    Toasty.warning(getApplicationContext(), "Ensure you agree to the terms to proceed", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });
    }


}
