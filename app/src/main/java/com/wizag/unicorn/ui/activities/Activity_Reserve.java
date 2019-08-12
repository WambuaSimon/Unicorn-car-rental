package com.wizag.unicorn.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.wizag.unicorn.R;

public class Activity_Reserve extends AppCompatActivity {
    TextView pick_up, drop_off, pick_date, pick_time, drop_date, drop_time;
    CheckBox terms;
    TextView rental_terms;
    Button pay;

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
        pick_time = findViewById(R.id.pick_time);
        drop_date = findViewById(R.id.drop_date);
        drop_time = findViewById(R.id.drop_time);
        terms = findViewById(R.id.terms);
        rental_terms = findViewById(R.id.rental_terms);
        pay = findViewById(R.id.pay);


        SharedPreferences prefs = getSharedPreferences("search_car", MODE_PRIVATE);
        String pick_location = prefs.getString("pick_up_location", null);//"No name defined" is the default value.
        String drop_location = prefs.getString("drop_off_location", null);//"No name defined" is the default value.
        String pick_date_txt = prefs.getString("pick_up_date", null);//"No name defined" is the default value.
        String drop_date_txt = prefs.getString("drop_off_date", null);//"No name defined" is the default value.
        String pick_time_txt = prefs.getString("pick_up_time", null);//"No name defined" is the default value.
        String drop_time_txt = prefs.getString("drop_off_time", null);

        pick_up.setText(pick_location);
        drop_off.setText(drop_location);

        pick_date.setText(pick_date_txt);
        drop_date.setText(drop_date_txt);

        pick_time.setText(pick_time_txt);
        drop_time.setText(drop_time_txt);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

}
