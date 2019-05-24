package com.wizag.unicorn.adapter


import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wizag.unicorn.model.Bookings

import java.io.ByteArrayOutputStream


/**
 * Created by farooq on 9/12/2017.
 */
class BookingsAdapter(val list: ArrayList<Bookings>) : RecyclerView.Adapter<BookingsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.wizag.unicorn.R.layout.bookings_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: BookingsAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bindItems(data: Bookings) {
            val to: TextView = itemView.findViewById(com.wizag.unicorn.R.id.to)
            val from: TextView = itemView.findViewById(com.wizag.unicorn.R.id.from)
            val date: TextView = itemView.findViewById(com.wizag.unicorn.R.id.date)
            val car_name: TextView = itemView.findViewById(com.wizag.unicorn.R.id.car_name)
            val amount: TextView = itemView.findViewById(com.wizag.unicorn.R.id.amount)


            to.text = data.to
            from.text = data.from
            date.text = data.date
            car_name.text = data.car_name
            amount.text = data.amount

//            val generator = ColorGenerator.MATERIAL
//            val color = generator.getColor(position)
//            holder.mEvent_color_strip.setBackgroundColor(color)

            //set the onclick listener for the single list item
            itemView.setOnClickListener({
                //                Toast.makeText(view.context, data.daily, Toast.LENGTH_LONG).show()


//                val intent = Intent(view.context, Activity_Bookings::class.java)
//                intent.putExtra("to", data.to)
//                intent.putExtra("from", data.from)
//                intent.putExtra("date", data.date)
//                intent.putExtra("car_name", data.car_name)
//                intent.putExtra("amount", data.amount)
////                intent.putExtra("car_image",car_image)
//                view.context.startActivity(intent)


            })
        }

    }
}
