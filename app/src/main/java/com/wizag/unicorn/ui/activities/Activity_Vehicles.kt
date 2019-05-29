package com.wizag.unicorn.ui.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wizag.unicorn.R
import com.wizag.unicorn.adapter.CarGridAdapter
import com.wizag.unicorn.model.Car
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class Activity_Vehicles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        (this as AppCompatActivity).supportActionBar?.setTitle("Featured Rides")
        setContentView(R.layout.activity_vehicles)

        val _recyclerView: RecyclerView = findViewById(R.id.vehicles_recyclerview)
        _recyclerView.layoutManager = GridLayoutManager(this,2)

        val cars = ArrayList<Car>()

        //adding some dummy data to the list
        cars.add(Car("Toyota Fielder","Kshs 4,640","Kshs 29,232","Kshs 104,400",BitmapFactory.decodeResource(resources, R.drawable.fielder)))
        cars.add(Car("7 Seater Van (Chauffeur)","Kshs 12,760","Kshs 80,040","Kshs 220,400",BitmapFactory.decodeResource(resources, R.drawable.alphard)))
        cars.add(Car("Safari Landcruiser (Chauffeur)","Kshs 25,520","N/A","N/A",BitmapFactory.decodeResource(resources, R.drawable.safari)))
        cars.add(Car("Double Cabin","Kshs 15,080","95,120","348,000",BitmapFactory.decodeResource(resources, R.drawable.double_cab)))
        cars.add(Car("Toyota Landcruiser Prado","Kshs 19,720","124,520","417,600",BitmapFactory.decodeResource(resources, R.drawable.prado)))


        //creating our adapter
        val adapter = CarGridAdapter(cars)


        //now adding the adapter to recyclerview
        _recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search_car, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.search) {
            startActivity(intentFor<MainActivity>().singleTop())
        }

        return super.onOptionsItemSelected(item)

    }
}
