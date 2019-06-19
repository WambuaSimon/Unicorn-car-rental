package com.wizag.unicorn.ui.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wizag.unicorn.R
import kotlinx.android.synthetic.main.activity_car_details.*


class Activity_CarDetails : AppCompatActivity() {

    var weeklyRate = ""
    var monthlyRate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.wizag.unicorn.R.layout.activity_car_details)

        val carName: String = intent.getStringExtra("car_name")
        val dailyRate: String = intent.getStringExtra("daily")
        if(!weeklyRate.isEmpty() ||!monthlyRate.isEmpty()) {
            weeklyRate = intent.getStringExtra("weekly")
            monthlyRate = intent.getStringExtra("monthly")
        }
//        val byteArray = intent.getByteArrayExtra("car_image")
//        val carImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

//        car_name.text = carName
        daily.text = dailyRate
        weekly.text = weeklyRate
        monthly.text = monthlyRate

//        car_image.setImageBitmap(carImage)

//        if(carName.equals("Toyota Fielder")){
//            car_image.setImageResource(R.drawable.fielder)
//        }
        if (carName.equals("7 Seater Van (Chauffeur)")) {
            car_image.setImageResource(R.drawable.alphard)
        } else if (carName.equals("Safari Landcruiser (Chauffeur)")) {
            car_image.setImageResource(R.drawable.safari)
        } else if (carName.equals("Double Cabin")) {
            car_image.setImageResource(R.drawable.double_cab)
        } else if (carName.equals("Toyota Landcruiser Prado")) {
            car_image.setImageResource(R.drawable.prado)
        }
    }
}
