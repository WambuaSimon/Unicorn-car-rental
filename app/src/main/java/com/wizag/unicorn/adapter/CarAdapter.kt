package com.wizag.unicorn.adapter


import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wizag.unicorn.model.Car
import com.wizag.unicorn.ui.activities.Activity_CarDetails
import java.io.ByteArrayOutputStream


/**
 * Created by farooq on 9/12/2017.
 */
class CarAdapter(val list: ArrayList<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.wizag.unicorn.R.layout.cars_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CarAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bindItems(data: Car) {
            val daily: TextView = itemView.findViewById(com.wizag.unicorn.R.id.daily_rate)
            val weekly: TextView = itemView.findViewById(com.wizag.unicorn.R.id.weekly_rate)
            val monthly: TextView = itemView.findViewById(com.wizag.unicorn.R.id.monthly_rate)
            val car_name: TextView = itemView.findViewById(com.wizag.unicorn.R.id.car_name)
            val car: ImageView = itemView.findViewById(com.wizag.unicorn.R.id.car)


            daily.text = data.daily
            weekly.text = data.weekly
            monthly.text = data.monthly
            car_name.text = data.car_name

            car.setImageBitmap(data.image)

            //set the onclick listener for the single list item
            itemView.setOnClickListener({
                //                Toast.makeText(view.context, data.daily, Toast.LENGTH_LONG).show()


                val stream = ByteArrayOutputStream()
                data.image.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val car_image = stream.toByteArray()

                val intent = Intent(view.context, Activity_CarDetails::class.java)
                intent.putExtra("car_name", data.car_name)
                intent.putExtra("daily", data.daily)
                intent.putExtra("weekly", data.weekly)
                intent.putExtra("monthly", data.monthly)
//                intent.putExtra("car_image",car_image)
                view.context.startActivity(intent)


            })
        }

    }
}
