package com.wizag.unicorn.ui.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_car_details.*


class Activity_CarDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.wizag.unicorn.R.layout.activity_car_details)

        val carName:String = intent.getStringExtra("car_name")
        val dailyRate:String = intent.getStringExtra("daily")
        val weeklyRate:String = intent.getStringExtra("weekly")
        val monthlyRate:String = intent.getStringExtra("monthly")
        val byteArray = intent.getByteArrayExtra("car_image")
        val carImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

        car_name.text = carName
        daily.text = dailyRate
        weekly.text = weeklyRate
        monthly.text = monthlyRate
        car_image.setImageBitmap(carImage)

    }
}
