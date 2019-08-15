package com.wizag.unicorn.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.wizag.unicorn.R;

public class Activity_CarDetails extends AppCompatActivity {
    TextView daily, car_name, body, transmission, fuel_type, car_image;
    String carName, carImage, bodyDesign, transmission_type, fuelType, dailyRate;
    ImageView carType_image;
    Button book;
    String driver_cost;
    String parentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        init();
    }

    void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        daily = findViewById(R.id.daily);
        car_name = findViewById(R.id.car_name);
        body = findViewById(R.id.body);
        transmission = findViewById(R.id.transmission);
        fuel_type = findViewById(R.id.fuel_type);
        carType_image = findViewById(R.id.car_image);
        book = findViewById(R.id.book);


        /*get from intent*/
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            carName = intent.getStringExtra("carName");
            carImage = "https://unicorn.wizag.co.ke/cartypes/" + intent.getStringExtra("carImage");
            bodyDesign = intent.getStringExtra("bodyDesign");
            transmission_type = intent.getStringExtra("transmission");
            fuelType = intent.getStringExtra("fuelType");
            dailyRate = intent.getStringExtra("dailyPricing");
            driver_cost = intent.getStringExtra("driver_cost");
            parentName = intent.getStringExtra("parentName");

        }

        daily.setText("Ksh."+dailyRate);
        car_name.setText(carName);
        body.setText(bodyDesign);
        transmission.setText(transmission_type);
        fuel_type.setText(fuelType);

        Glide.with(getApplicationContext()).load(carImage).into(carType_image);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parentName.equalsIgnoreCase("search")){
                    Intent intent = new Intent(getApplicationContext(), Activity_Reserve.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dailyRate", dailyRate);
                    intent.putExtra("driver_cost", driver_cost);

                    startActivity(intent);

                } else if(parentName.equalsIgnoreCase("featured")){
                    Intent intent = new Intent(getApplicationContext(), Activity_Featured_Reserve.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dailyRate", dailyRate);
                    intent.putExtra("driver_cost", driver_cost);
                    startActivity(intent);
                }


            }
        });

    }

}
