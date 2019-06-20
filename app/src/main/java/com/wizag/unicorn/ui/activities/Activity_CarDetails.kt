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

        val carName: String = intent.getStringExtra("carName")
        val carImage: String = intent.getStringExtra("carImage")
        val dailyRate: String = intent.getStringExtra("dailyPricing")

        if (!weeklyRate.isEmpty() || !monthlyRate.isEmpty()) {
            weeklyRate = intent.getStringExtra("weeklyPricing")
            monthlyRate = intent.getStringExtra("monthlyPricing")
        }

        daily.text = dailyRate
        weekly.text = weeklyRate
        monthly.text = monthlyRate
        Glide.with(applicationContext).load(carImage).into(car_image);



    }
}

