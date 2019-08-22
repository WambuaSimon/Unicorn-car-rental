package com.wizag.unicorn.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wizag.unicorn.R;
import es.dmoral.toasty.Toasty;

public class Activity_Reserve extends AppCompatActivity {
    TextView pick_up, drop_off, pick_date, pick_time, drop_date, drop_time;
    CheckBox terms, driver;
    TextView rental_terms;
    Button make_reservation;

    String driverCost, daily_rate;
    String needDriver;
    String parentName;

    EditText fName, lName, email, phone;

    FloatingActionButton next;


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


        make_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (terms.isChecked()) {

                    if (driver.isChecked()) {
                        needDriver = "1";

                        Intent intent = new Intent(getApplicationContext(), Activity_Payment.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("dailyRate", daily_rate);
                        intent.putExtra("driver_cost", driverCost);
                        intent.putExtra("needDriver", needDriver);

                        startActivity(intent);
                    } else if (!driver.isChecked()) {
                        needDriver = "0";

                        View dialogView = getLayoutInflater().inflate(R.layout.driver_modal_layout, null);
                        BottomSheetDialog dialog = new BottomSheetDialog(Activity_Reserve.this);
                        fName = dialogView.findViewById(R.id.fName);
                        lName = dialogView.findViewById(R.id.lName);
                        email = dialogView.findViewById(R.id.email);
                        phone = dialogView.findViewById(R.id.phone);
                        next = dialogView.findViewById(R.id.next);

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String fName_txt = fName.getText().toString();
                                String lName_txt = lName.getText().toString();
                                String email_txt = email.getText().toString();
                                String phone_txt = phone.getText().toString();

                                if (fName_txt.isEmpty()) {
                                    fName.setError("Enter First Name");
                                } else if (lName_txt.isEmpty()) {
                                    lName.setError("Enter Last Name");
                                } else if (email_txt.isEmpty()) {
                                    email.setError("Enter Email");
                                } else if (phone_txt.isEmpty()) {
                                    phone.setError("Enter Phone Number");
                                } else {


                                    Intent intent = new Intent(getApplicationContext(), Activity_Payment.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    intent.putExtra("dailyRate", daily_rate);
                                    intent.putExtra("driver_cost", driverCost);
                                    intent.putExtra("needDriver", needDriver);

                                    intent.putExtra("driver_fname", fName.getText().toString());
                                    intent.putExtra("driver_lname", lName.getText().toString());
                                    intent.putExtra("driver_email", email.getText().toString());
                                    intent.putExtra("driver_phone", phone.getText().toString());

                                    startActivity(intent);
                                }
                            }
                        });
                        dialog.setContentView(dialogView);
                        dialog.show();
                    }


                } else {
                    Toasty.warning(getApplicationContext(), "Ensure you agree to the terms to proceed", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });
    }


}
