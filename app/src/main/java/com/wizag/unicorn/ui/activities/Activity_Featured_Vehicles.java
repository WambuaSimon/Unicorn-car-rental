package com.wizag.unicorn.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.wizag.unicorn.R;
import com.wizag.unicorn.adapter.VehicleAdapter;
import com.wizag.unicorn.model.VehicleModel;
import com.wizag.unicorn.network.ApiClient;
import com.wizag.unicorn.network.ApiInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Featured_Vehicles extends AppCompatActivity {
    RecyclerView recyclerView;
    VehicleAdapter vehicleAdapter;
    ApiInterface apiInterface;
    List<VehicleModel> vehicleModelList;
    String vehicles_url = "https://unicorn.wizag.co.ke/api/get-vehicles";
    String price;
    String pricing_rate_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_vehicles);


        initializeViews();
        getVehiclesData();


    }

    void initializeViews() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.vehicles_recyclerview);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        vehicleModelList = new ArrayList<>();
        vehicleAdapter = new VehicleAdapter(vehicleModelList, this, new VehicleAdapter.VehicleAdapterListener() {
            @Override
            public void fabOnClick(View v, int position) {

                VehicleModel vehicleModel = vehicleModelList.get(position);

                String carMake = vehicleModel.getCarMake();
                String carImage = vehicleModel.getCarImage();
                String carModel = vehicleModel.getCarModel();
                String dailyPricing = vehicleModel.getDailyPricing();
                String fuelType = vehicleModel.getFuelType();
                String transmission = vehicleModel.getTransmission();
                String bodyDesign = vehicleModel.getBodyDesign();
                String weeklyPricing = vehicleModel.getWeeklyPricing();
                String monthlyPricing = vehicleModel.getMonthlyPricing();
                String car_name = carMake + " " + carModel;
                String driver_cost = vehicleModel.getDriver_cost();


                Intent intent = new Intent(getApplicationContext(), Activity_CarDetails.class);

                intent.putExtra("carImage", carImage);
                intent.putExtra("carName", car_name);
                intent.putExtra("dailyPricing", dailyPricing);
                intent.putExtra("fuelType", fuelType);
                intent.putExtra("transmission", transmission);
                intent.putExtra("bodyDesign", bodyDesign);
                intent.putExtra("weeklyPricing", weeklyPricing);
                intent.putExtra("monthlyPricing", monthlyPricing);
                intent.putExtra("driver_cost", driver_cost);

                intent.putExtra("parentName", "featured");
                startActivity(intent);

            }
        });

        recyclerView.setAdapter(vehicleAdapter);
        vehicleAdapter.notifyDataSetChanged();

    }

    void getVehiclesData() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading your rides...");
        pDialog.setCancelable(false);
        pDialog.show();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, vehicles_url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray vehicles_array = jsonObject.getJSONArray("carTypes");
                            for (int p = 0; p < vehicles_array.length(); p++) {


                                JSONObject vehicles_object = vehicles_array.getJSONObject(p);
                                VehicleModel vehicleModel = new VehicleModel();
                                String driver_cost = vehicles_object.getString("driver_cost");

                                String car_type_id = vehicles_object.getString("id");
//                                vehicleModelList.clear();

                                JSONObject car_make_object = vehicles_object.getJSONObject("car_make");
                                String make = car_make_object.getString("make");

                                JSONObject car_image_object = vehicles_object.getJSONObject("car_type_image");
                                String image = car_image_object.getString("image");

                                JSONObject car_model_object = vehicles_object.getJSONObject("car_model");
                                String model = car_model_object.getString("model");

                                JSONArray pricings = vehicles_object.getJSONArray("pricings");

                                JSONObject fuel_type_object = vehicles_object.getJSONObject("fuel_type");
                                String fuel = fuel_type_object.getString("name");

                                JSONObject transmission_object = vehicles_object.getJSONObject("transmission");
                                String transmission = transmission_object.getString("name");

                                JSONObject car_body_design_object = vehicles_object.getJSONObject("car_body_design");
                                String body_design = car_body_design_object.getString("body_design");


                                for (int m = 0; m < pricings.length(); m++) {
                                    JSONObject pricings_object = pricings.getJSONObject(m);
                                    price = pricings_object.getString("price");
                                    pricing_rate_id = pricings_object.getString("pricing_rate_id");

                                    if (pricing_rate_id.equalsIgnoreCase("1")) {
                                        vehicleModel.setDailyPricing(price);
                                    } else if (pricing_rate_id.equalsIgnoreCase("2")) {
                                        vehicleModel.setWeeklyPricing(price);
                                    }

                                }


                                vehicleModel.setCarMake(make);
                                vehicleModel.setCarModel(model);
                                vehicleModel.setCarImage(image);
                                vehicleModel.setFuelType(fuel);
                                vehicleModel.setTransmission(transmission);
                                vehicleModel.setBodyDesign(body_design);
                                vehicleModel.setDriver_cost(driver_cost);


                                if (vehicleModelList.contains(car_type_id)) {
                                    /*do nothing*/
                                } else {
                                    vehicleModelList.add(vehicleModel);
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            e.getMessage();
                        }
                        vehicleAdapter.notifyDataSetChanged();

                        //Toast.makeText(Activity_Show_Tasks.this, "", Toast.LENGTH_SHORT).show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                error.printStackTrace();


                pDialog.dismiss();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

