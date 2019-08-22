package com.wizag.unicorn.ui.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wizag.unicorn.R;
import com.wizag.unicorn.adapter.BookingsAdapter;
import com.wizag.unicorn.model.BookingsModel;
import com.wizag.unicorn.model.VehicleModel;
import com.wizag.unicorn.ui.activities.Activity_BookingDetails;
import com.wizag.unicorn.utils.Constants;
import com.wizag.unicorn.utils.SessionManager;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingsFragment extends Fragment {
    private List<BookingsModel> bookingsModelsList;
    private RecyclerView recyclerView;
    private BookingsAdapter mAdapter;
    View view;
    String email_txt;
    SessionManager sessionManager;

    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bookings, container, false);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetails();
        email_txt = user.get("email");

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
                BookingsModel bookingsModel = bookingsModelsList.get(position);
                String carMake = bookingsModel.getMake();
                String carModel = bookingsModel.getModel();
                String pickUp_Date = bookingsModel.getPickup_date();
                String dropOff_Date = bookingsModel.getDropoff_date();
                String pickUp_location = bookingsModel.getPickup_location();
                String dropoff_location = bookingsModel.getDropoff_location();
//                String carMake = bookingsModel.getMake();
//                String carMake = bookingsModel.getMake();

                Intent intent = new Intent(getActivity(), Activity_BookingDetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(mAdapter);


    }

    void getBookings() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getActivity());
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading your rides...");
        pDialog.setCancelable(false);
        pDialog.show();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.baseUrl+"reservations/"+email_txt,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray vehicles_array = jsonObject.getJSONArray("bookings");

                            for (int p = 0; p < vehicles_array.length(); p++) {


                                JSONObject vehicles_object = vehicles_array.getJSONObject(p);

                                BookingsModel bookingModel = new BookingsModel();

                                String car_id = vehicles_object.getString("id");

                                JSONObject pickup_obj = vehicles_object.getJSONObject("pickup_location");
                                String pickup_location = pickup_obj.getString("name");


                                JSONObject dropoff_obj = vehicles_object.getJSONObject("drop_off_location");
                                String drop_off_location = dropoff_obj.getString("name");

                                String pickup_date = vehicles_object.getString("pickup_date");
                                String pickup_time = vehicles_object.getString("pickup_time");
                                String drop_off_date = vehicles_object.getString("drop_off_date");
                                String drop_off_time = vehicles_object.getString("drop_off_time");

                                JSONObject car_type_obj = vehicles_object.getJSONObject("car_type");

                                JSONObject car_make_object = car_type_obj.getJSONObject("car_make");
                                String make = car_make_object.getString("make");


                                JSONObject car_model_object = car_type_obj.getJSONObject("car_model");
                                String model = car_model_object.getString("model");


                                bookingModel.setPickup_location(pickup_location);
                                bookingModel.setDropoff_location(drop_off_location);
                                bookingModel.setPickup_date(pickup_date);
                                bookingModel.setPickup_time(pickup_time);
                                bookingModel.setDropoff_date(drop_off_date);
                                bookingModel.setDropoff_time(drop_off_time);
                                bookingModel.setMake(make);
                                bookingModel.setModel(model);


                                if (bookingsModelsList.contains(car_id)) {
                                    /*do nothing*/
                                } else {
                                    bookingsModelsList.add(bookingModel);
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            e.getMessage();

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mAdapter.notifyDataSetChanged();

                        //Toast.makeText(Activity_Show_Tasks.this, "", Toast.LENGTH_SHORT).show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                error.printStackTrace();

                Toasty.error(getActivity(), error.getMessage(), Toasty.LENGTH_SHORT, true).show();
                pDialog.dismiss();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
