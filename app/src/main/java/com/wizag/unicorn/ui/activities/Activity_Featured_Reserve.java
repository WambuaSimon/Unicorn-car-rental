package com.wizag.unicorn.ui.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wizag.unicorn.R;
import com.wizag.unicorn.utils.Constants;
import com.wizag.unicorn.utils.MySingleton;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Featured_Reserve extends AppCompatActivity {

    /*featured stuff*/
    Spinner featured_pick_up, featured_drop_off;
    EditText featured_pick_date, featured_pick_time,
            featured_drop_date, featured_drop_time;

    CheckBox featured_driver, featured_terms;
    Button featured_reservation;
    ArrayList<String> FeaturedLocations;
    CheckBox terms, driver;
    String needDriver;
    String daily_rate, driverCost;
    JSONArray locations;
    int id_pickup, id_dropoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__featured__reserve);

        initFeatured();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    void initFeatured() {

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FeaturedLocations = new ArrayList<>();
        /*get ride cost*/
        Intent intent = getIntent();
        daily_rate = intent.getStringExtra("dailyRate");
        driverCost = intent.getStringExtra("driver_cost");


        terms = findViewById(R.id.terms);
        driver = findViewById(R.id.driver);

        featured_pick_up = findViewById(R.id.featured_pick_up);
        featured_drop_off = findViewById(R.id.featured_drop_off);


        featured_pick_up.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject dataClicked = locations.getJSONObject(position);
                    id_pickup = dataClicked.getInt("id");
//                    Toast.makeText(getActivity(), ""+id_pickup, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        featured_drop_off.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject dataClicked = locations.getJSONObject(position);
                    id_dropoff = dataClicked.getInt("id");
//                    Toast.makeText(getActivity(), ""+id_dropoff, Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        featured_pick_date = findViewById(R.id.featured_pick_date);
        featured_pick_time = findViewById(R.id.featured_pick_time);

        featured_drop_date = findViewById(R.id.featured_drop_date);
        featured_drop_time = findViewById(R.id.featured_drop_time);

        featured_driver = findViewById(R.id.featured_driver);
        featured_terms = findViewById(R.id.featured_terms);
        featured_reservation = findViewById(R.id.featured_reservation);


        featured_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPickUpDate();


            }
        });

        featured_drop_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDropOffDate();
            }
        });

        featured_pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickupTime();
            }
        });

        featured_drop_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDropOffTime();
            }
        });


        if (!isNetworkConnected()) {
            Toasty.warning(getApplicationContext(), "Ensure you have internet connection", Toasty.LENGTH_SHORT, true).show();
        } else {
            getLocation();
        }


        featured_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (featured_pick_up.getSelectedItem().toString() == null || featured_drop_off.getSelectedItem().toString() == null) {

                    Toasty.warning(getApplicationContext(), "Select Drop off and Pick up locations", Toasty.LENGTH_SHORT, true).show();
                } else if (featured_pick_date.getText().toString().isEmpty() || featured_drop_date.getText().toString().isEmpty()) {
                    Toasty.warning(getApplicationContext(), "Select respective dates", Toasty.LENGTH_SHORT, true).show();

                } else if (featured_pick_time.getText().toString().isEmpty() || featured_drop_time.getText().toString().isEmpty()) {
                    Toasty.warning(getApplicationContext(), "Select respective times", Toasty.LENGTH_SHORT, true).show();

                } else {

                    if (terms.isChecked()) {
                        if (driver.isChecked()) {
                            needDriver = "1";

                            Intent intent = new Intent(getApplicationContext(), Activity_Payment.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                            intent.putExtra("dailyRate", daily_rate);
                            intent.putExtra("driver_cost", driverCost);
                            intent.putExtra("needDriver", needDriver);

                            startActivity(intent);

                        } else if (!driver.isChecked()) {
                            needDriver = "0";
                            /*store stuff in shared prefs*/


                            View dialogView = getLayoutInflater().inflate(R.layout.driver_modal_layout, null);
                            BottomSheetDialog dialog = new BottomSheetDialog(Activity_Featured_Reserve.this);
                            final EditText fName = dialogView.findViewById(R.id.fName);
                            final EditText lName = dialogView.findViewById(R.id.lName);
                            final EditText email = dialogView.findViewById(R.id.email);
                            final EditText phone = dialogView.findViewById(R.id.phone);
                            final FloatingActionButton next = dialogView.findViewById(R.id.next);

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String fName_txt = fName.getText().toString();
                                    String lName_txt = lName.getText().toString();
                                    String email_txt = email.getText().toString();
                                    String phone_txt = phone.getText().toString();

                                    if (fName_txt.isEmpty()) {
                                        fName.setError("Enter First Name");
                                    } else if (lName_txt.isEmpty()) {
                                        lName.setError("Enter Last Name");
                                    } else if (email_txt.isEmpty()) {
                                        email.setError("Enter Email");
                                    } else if (phone_txt.isEmpty()) {
                                        phone.setError("Enter Phone Number");
                                    } else {


                                        Intent intent = new Intent(getApplicationContext(), Activity_Payment.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                        intent.putExtra("dailyRate", daily_rate);
                                        intent.putExtra("driver_cost", driverCost);
                                        intent.putExtra("needDriver", needDriver);

                                        intent.putExtra("driver_fname", fName.getText().toString());
                                        intent.putExtra("driver_lname", lName.getText().toString());
                                        intent.putExtra("driver_email", email.getText().toString());
                                        intent.putExtra("driver_phone", phone.getText().toString());

                                        startActivity(intent);
                                    }
                                }
                            });
                            dialog.setContentView(dialogView);
                            dialog.show();

                        }

                    }
                    SharedPreferences.Editor editor = getSharedPreferences("search_car", MODE_PRIVATE).edit();
                    editor.putString("pick_up_location", featured_pick_up.getSelectedItem().toString());
                    editor.putString("drop_off_location", featured_drop_off.getSelectedItem().toString());

                    editor.putString("pick_up_date", featured_pick_date.getText().toString());
                    editor.putString("drop_off_date", featured_drop_date.getText().toString());

                    editor.putString("pick_up_time", featured_pick_time.getText().toString());
                    editor.putString("drop_off_time", featured_drop_time.getText().toString());

                    editor.putInt("id_pickup", id_pickup);
                    editor.putInt("id_dropoff", id_dropoff);

                    editor.apply();

                }


            }
        });
    }


    private void getLocation() {
        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Featured_Reserve.this);
        final ProgressDialog pDialog = new ProgressDialog(Activity_Featured_Reserve.this);
        pDialog.setMessage("Getting Locations...");
        pDialog.setCancelable(false);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.baseUrl + "locations", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    pDialog.dismiss();
                    if (jsonObject != null) {
                        locations = jsonObject.getJSONArray("locations");
                        for (int i = 0; i < locations.length(); i++) {
                            JSONObject locationsObject = locations.getJSONObject(i);
                            String name = locationsObject.getString("name");

                            if (FeaturedLocations.contains(name)) {

                            } else {
                                FeaturedLocations.add(name);
                            }

                        }


                    }
                    featured_drop_off.setAdapter(new ArrayAdapter<String>(Activity_Featured_Reserve.this, android.R.layout.simple_spinner_dropdown_item, FeaturedLocations));
                    featured_pick_up.setAdapter(new ArrayAdapter<String>(Activity_Featured_Reserve.this, android.R.layout.simple_spinner_dropdown_item, FeaturedLocations));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pDialog.dismiss();
                Toasty.error(Activity_Featured_Reserve.this, "An Error Occurred" + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });

        MySingleton.getInstance(Activity_Featured_Reserve.this).addToRequestQueue(stringRequest);

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    public void getPickUpDate() {
        final Calendar mcurrentDate = Calendar.getInstance();

        mcurrentDate.set(mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH) + 2,
                mcurrentDate.get(Calendar.HOUR_OF_DAY), mcurrentDate.get(Calendar.MINUTE), 0);


        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(Activity_Featured_Reserve.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                featured_pick_date.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }


    public void getDropOffDate() {
        final Calendar mcurrentDate = Calendar.getInstance();

        mcurrentDate.set(mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH) + 2,
                mcurrentDate.get(Calendar.HOUR_OF_DAY), mcurrentDate.get(Calendar.MINUTE), 0);


        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(Activity_Featured_Reserve.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                featured_drop_date.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void getPickupTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Activity_Featured_Reserve.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                featured_pick_time.setText("" + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute));

//                featured_pick_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void getDropOffTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Activity_Featured_Reserve.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                featured_drop_time.setText("" + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute));


//                featured_drop_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
