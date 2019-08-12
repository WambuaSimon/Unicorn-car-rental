package com.wizag.unicorn.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wizag.unicorn.R;
import com.wizag.unicorn.adapter.BookingsAdapter;
import com.wizag.unicorn.model.BookingsModel;
import com.wizag.unicorn.ui.activities.Activity_BookingDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingsFragment extends Fragment {
    private List<BookingsModel> bookingsModelsList;
    private RecyclerView recyclerView;
    private BookingsAdapter mAdapter;
    View view;

    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookings, container, false);

        init();
        getBookings();

        return view;
    }

    void init() {
        bookingsModelsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BookingsAdapter(bookingsModelsList, getActivity(), new BookingsAdapter.BookingsAdapterListener() {
            @Override
            public void cardOnClick(View v, int position) {
                Intent intent = new Intent(getActivity(), Activity_BookingDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(mAdapter);



    }

    void getBookings() {
        BookingsModel bookingsModel = new BookingsModel("Toyota Fielder", "2019-08-12   ", "16:00:00", "2019-08-14   ", "16:00:00");
        bookingsModelsList.add(bookingsModel);

        BookingsModel bookingsModel2 = new BookingsModel("Toyota Axio", "2019-08-12   ", "16:00:00", "2019-08-14   ", "16:00:00");
        bookingsModelsList.add(bookingsModel2);


        BookingsModel bookingsModel3 = new BookingsModel("Nissan Xtrail", "2019-08-12   ", "16:00:00", "2019-08-14   ", "16:00:00");
        bookingsModelsList.add(bookingsModel3);


        BookingsModel bookingsModel4 = new BookingsModel("Mitsubishi Outlander", "2019-08-12   ", "16:00:00", "2019-08-14   ", "16:00:00");
        bookingsModelsList.add(bookingsModel4);


    }

}
