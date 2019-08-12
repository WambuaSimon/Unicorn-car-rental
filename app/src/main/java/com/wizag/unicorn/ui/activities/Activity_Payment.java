package com.wizag.unicorn.ui.activities;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ViewFlipper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.wizag.unicorn.R;

public class Activity_Payment extends AppCompatActivity implements View.OnClickListener {
    Button previous, make_pay, billing_info;
    ViewFlipper flipper;

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

        previous = findViewById(R.id.previous);
        make_pay = findViewById(R.id.make_pay);
        billing_info = findViewById(R.id.billing_info);
        flipper = findViewById(R.id.flipper);

        make_pay.setOnClickListener(this);
        billing_info.setOnClickListener(this);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipper.showPrevious();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_pay:

                break;

            case R.id.billing_info:
                flipper.showNext();

                break;
        }
    }
}
