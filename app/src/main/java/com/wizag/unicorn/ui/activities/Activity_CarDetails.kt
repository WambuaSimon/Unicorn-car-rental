package com.wizag.unicorn.ui.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wizag.unicorn.R
import kotlinx.android.synthetic.main.activity_car_details.*


class Activity_CarDetails : AppCompatActivity() {

    var weeklyRate = ""
    var monthlyRate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.wizag.unicorn.R.layout.activity_car_details)

        // my_child_toolbar is defined in the layout file
        setSupportActionBar(findViewById(R.id.toolbar))
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            val carName: String = intent.getStringExtra("carName")
            val carImage: String = "https://unicorn.wizag.co.ke/cartypes/"+intent.getStringExtra("carImage")
            val dailyRate: String = intent.getStringExtra("dailyPricing")
            val fueltype: String = intent.getStringExtra("fuelType")
            val transmission_type: String = intent.getStringExtra("transmission")
            val bodyDesign: String = intent.getStringExtra("bodyDesign")

            Glide.with(applicationContext).load(carImage).into(car_image)


            if (intent.getStringExtra("monthlyPricing") != null) {
                monthlyRate = intent.getStringExtra("monthlyPricing")
            }

            else if (intent.getStringExtra("weeklyPricing") != null) {
                weeklyRate = intent.getStringExtra("weeklyPricing")
            }


            daily.text = "Ksh "+dailyRate
            weekly.text = weeklyRate
            monthly.text = monthlyRate
            car_name.text = carName
            body.text = bodyDesign
            transmission.text = transmission_type
            fuel_type.text = fueltype
        }

    }
}

