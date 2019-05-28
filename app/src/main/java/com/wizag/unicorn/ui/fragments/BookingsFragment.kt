package com.wizag.unicorn.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.wizag.unicorn.R
import com.wizag.unicorn.adapter.BookingsAdapter
import com.wizag.unicorn.model.Bookings
import kotlinx.android.synthetic.main.activity_cars.*
import kotlinx.android.synthetic.main.bookings_fragment.view.*

class BookingsFragment : Fragment() {
    private lateinit var viewOfLayout: View
    val bookings = ArrayList<Bookings>()

    companion object {
        fun newInstance() = BookingsFragment()
    }

    private lateinit var viewModel: BookingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewOfLayout = inflater.inflate(R.layout.bookings_fragment, container, false)

        return viewOfLayout


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BookingsViewModel::class.java)
        // TODO: Use the ViewModel


//        bookings.add(Bookings("Nairobi", "Namanga", "Fielder", "Ksh 4,620", "1-05-2019"))
//        bookings.add(Bookings("Nairobi", "Masai Mara", "Safari Land Cruiser", "Ksh 25,520", "11-05-2019"))
//        bookings.add(Bookings("Nairobi", "Kisumu", "Prado Land Cruiser", "Ksh 19,720", "05-05-2019"))
//        bookings.add(Bookings("Nairobi", "Nakuru", "Navara", "Ksh 15,080", "12-03-2019"))
//        bookings.add(Bookings("Nairobi", "Naivasha", "Aphard", "Ksh 12,760", "15-06-2019"))
//
//        val adapter = BookingsAdapter(bookings)
//        recyclerview.adapter = adapter


    }

    fun getBookings() {


    }
}
