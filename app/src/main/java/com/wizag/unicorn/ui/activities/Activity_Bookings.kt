//package com.wizag.unicorn.ui.activities
//
//import android.graphics.BitmapFactory
//import android.os.Bundle
//import android.widget.LinearLayout
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.wizag.unicorn.R
//import com.wizag.unicorn.adapter.CarAdapter
//import com.wizag.unicorn.model.Bookings
//import com.wizag.unicorn.model.Car
//
//class Activity_Bookings : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cars)
//
//        val _recyclerView: RecyclerView = findViewById(R.id.recyclerview)
//        _recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//
//        val bookings = ArrayList<Bookings>()
//
//        //adding some dummy data to the list
//        bookings.add(Car("Toyota Fielder","Kshs 4,640","Kshs 29,232","Kshs 104,400",BitmapFactory.decodeResource(resources, R.drawable.fielder)))
//        bookings.add(Car("7 Seater Van (Chauffeur)","Kshs 12,760","Kshs 80,040","Kshs 220,400",BitmapFactory.decodeResource(resources, R.drawable.alphard)))
//        bookings.add(Car("Safari Landcruiser (Chauffeur)","Kshs 25,520","N/A","N/A",BitmapFactory.decodeResource(resources, R.drawable.safari)))
//        bookings.add(Car("Double Cabin","Kshs 15,080","95,120","348,000",BitmapFactory.decodeResource(resources, R.drawable.double_cab)))
//        bookings.add(Car("Toyota Landcruiser Prado","Kshs 19,720","124,520","417,600",BitmapFactory.decodeResource(resources, R.drawable.prado)))
//
//
//        //creating our adapter
//        val adapter = CarAdapter(bookings)
//
//        //now adding the adapter to recyclerview
//        _recyclerView.adapter = adapter
//    }
//
//
//}
