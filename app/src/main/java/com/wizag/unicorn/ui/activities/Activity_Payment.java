package com.wizag.unicorn.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.wizag.unicorn.R;
import com.wizag.unicorn.utils.SessionManager;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class Activity_Payment extends AppCompatActivity implements View.OnClickListener {
    Button previous, make_pay, billing_info;
    ViewFlipper flipper;

    TextView car_type, pick_up, drop_off, pick_date,
            pick_time, drop_date, drop_time, driver_cost, est_total, ride_cost;
    LinearLayout driver;

    String driverCost, daily_rate, needDriver;
    double est_cost;
    SessionManager sessionManager;
    EditText fName, lName, email, phone, address, city, country;
    TextView days, daily_ride_cost, daily_driver_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        init();
    }

    void init() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String fName_txt = user.get("fName");
        String lName_txt = user.get("lName");
        String email_txt = user.get("email");
        String phone_txt = user.get("phone");


        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);

        fName.setText(fName_txt);
        lName.setText(lName_txt);
        email.setText(email_txt);
        phone.setText(phone_txt);


        previous = findViewById(R.id.previous);
        make_pay = findViewById(R.id.make_pay);
        billing_info = findViewById(R.id.billing_info);
        flipper = findViewById(R.id.flipper);

        car_type = findViewById(R.id.car_type);
        pick_up = findViewById(R.id.pick_up);
        drop_off = findViewById(R.id.drop_off);
        pick_date = findViewById(R.id.pick_date);
//        pick_time = findViewById(R.id.pick_time);
        drop_date = findViewById(R.id.drop_date);
//        drop_time = findViewById(R.id.drop_time);
        driver_cost = findViewById(R.id.driver_cost);
        est_total = findViewById(R.id.est_total);
        ride_cost = findViewById(R.id.ride_cost);

        days = findViewById(R.id.days);
        daily_driver_cost = findViewById(R.id.daily_driver_cost);
        daily_ride_cost = findViewById(R.id.daily_ride_cost);

        driver = findViewById(R.id.driver);
        driver.setVisibility(View.GONE);

        make_pay.setOnClickListener(this);
        billing_info.setOnClickListener(this);

        /*get ride n driver cost*/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        daily_rate = intent.getStringExtra("dailyRate");
        driverCost = intent.getStringExtra("driver_cost");
        needDriver = intent.getStringExtra("needDriver");

        if (needDriver.equalsIgnoreCase("0")) {
            driver.setVisibility(View.GONE);
        } else if (needDriver.equalsIgnoreCase("1")) {
            driver.setVisibility(View.VISIBLE);

        }


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showPrevious();
            }
        });

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



        /*get duration in (days)*/
        DateTime pickDate = new DateTime(pick_date_txt);
        DateTime dropDate = new DateTime(drop_date_txt);
        Interval interval = new Interval(pickDate, dropDate);
        String interval_days = String.format(Locale.getDefault(), "%d", interval.toDuration().getStandardDays());
        if (interval_days.equalsIgnoreCase("0")) {
            interval_days = "1";
        }

        days.setText(interval_days);

        /*check if driver available*/
        if (needDriver.equalsIgnoreCase("1")) {
            /*calculate est cost*/
            double duration_cost = Double.parseDouble(interval_days) * Double.parseDouble(daily_rate);

            driver_cost.setText("Ksh." + Double.parseDouble(driverCost) * Double.parseDouble(interval_days));
            ride_cost.setText("Ksh." + duration_cost);

            /*daily driver  and ride cost*/
            daily_driver_cost.setText("Ksh." + Double.parseDouble(driverCost));
            daily_ride_cost.setText("Ksh." +  Double.parseDouble(daily_rate));

            est_cost = (Double.parseDouble(driverCost) * Double.parseDouble(interval_days)) + duration_cost;

//            est_cost = Double.parseDouble(driver_cost.getText().toString()) + Double.parseDouble(ride_cost.getText().toString());
            est_total.setText("Ksh " + NumberFormat.getNumberInstance(Locale.getDefault()).format(est_cost));


//            est_total.setText("Ksh." + est_cost);

        } else if (needDriver.equalsIgnoreCase("0")) {
            double duration_cost = Double.parseDouble(interval_days) * Double.parseDouble(daily_rate);
            est_total.setText("Ksh " + NumberFormat.getNumberInstance(Locale.getDefault()).format(duration_cost));

            ride_cost.setText("Ksh." + duration_cost);
            /*daily driver and ride cost*/
            daily_driver_cost.setText("Ksh." + Double.parseDouble(driverCost));
            daily_ride_cost.setText("Ksh." +  Double.parseDouble(daily_rate));


//            est_total.setText("Ksh." + duration_cost);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_pay:
                if (address.getText().toString().isEmpty()) {
                    address.setError("Enter Your Address");
                } else if (city.getText().toString().isEmpty()) {
                    city.setError("Enter Your City");
                } else if (country.getText().toString().isEmpty()) {
                    country.setError("Enter Your Country");
                } else {

//                    /*open webView for payment*/
//                    Intent intent = new Intent(getApplicationContext(), Activity_PayWebview.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);

                }

                break;

            case R.id.billing_info:
                flipper.showNext();

                break;
        }
    }
}
