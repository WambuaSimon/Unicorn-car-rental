package com.wizag.unicorn.ui.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.wizag.unicorn.R;
import com.wizag.unicorn.ui.activities.Activity_Car;
import com.wizag.unicorn.ui.activities.MainActivity;
import com.wizag.unicorn.utils.Constants;
import com.wizag.unicorn.utils.MySingleton;
import com.wizag.unicorn.utils.SessionManager;
import es.dmoral.toasty.Toasty;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    Button find_car;

    Spinner pick_up, drop_off;
    View view;
    ArrayList<String> Locations;
    EditText pick_date, drop_date;
    EditText pick_time, drop_time;
    SessionManager sessionManager;
    JSONArray locations;
    int id_pickup,id_dropoff;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        init();
        return view;

    }

    void init() {

        Toolbar myToolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(myToolbar);

        find_car = view.findViewById(R.id.find_car);
        sessionManager = new SessionManager(getActivity());

        Locations = new ArrayList<>();

        pick_up = view.findViewById(R.id.pick_up);
        drop_off = view.findViewById(R.id.drop_off);

        pick_up.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        drop_off.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        pick_date = view.findViewById(R.id.pick_date);
        drop_date = view.findViewById(R.id.drop_date);

        pick_time = view.findViewById(R.id.pick_time);
        drop_time = view.findViewById(R.id.drop_time);


        pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickUpDate();
            }
        });

        drop_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDropOffDate();
            }
        });


        pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPickupTime();
            }
        });


        drop_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDropOffTime();
            }
        });


        if (!isNetworkConnected()) {
            Toasty.warning(getActivity(), "Ensure you have internet connection", Toasty.LENGTH_SHORT, true).show();
        } else {
            getLocation();
        }

        find_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*validation*/
                if (pick_up.getSelectedItem().toString() == null || drop_off.getSelectedItem().toString() == null) {

                    Toasty.warning(getActivity(), "Select Drop off and Pick up locations", Toasty.LENGTH_SHORT, true).show();
                } else if (pick_date.getText().toString().isEmpty() || drop_date.getText().toString().isEmpty()) {
                    Toasty.warning(getActivity(), "Select respective dates", Toasty.LENGTH_SHORT, true).show();

                } else if (pick_time.getText().toString().isEmpty() || drop_time.getText().toString().isEmpty()) {
                    Toasty.warning(getActivity(), "Select respective times", Toasty.LENGTH_SHORT, true).show();

                } else {

                    /*store stuff in shared prefs*/
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("search_car", MODE_PRIVATE).edit();
                    editor.putString("pick_up_location", pick_up.getSelectedItem().toString());
                    editor.putString("drop_off_location", drop_off.getSelectedItem().toString());

                    editor.putString("pick_up_date", pick_date.getText().toString());
                    editor.putString("drop_off_date", drop_date.getText().toString());

                    editor.putString("pick_up_time", pick_time.getText().toString());
                    editor.putString("drop_off_time", drop_time.getText().toString());

                    editor.putInt("id_pickup", id_pickup);
                    editor.putInt("id_dropoff", id_dropoff);


                    editor.apply();

                    Intent intent = new Intent(view.getContext(), Activity_Car.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("parentName", "search");
                    startActivity(intent);

                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {


            case R.id.logout:

                MaterialDialog mDialog = new MaterialDialog.Builder(getActivity())
                        .setTitle("Logout?")
                        .setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Logout!", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // logout Operation
                                sessionManager.logoutUser();
                            }
                        })
                        .setNegativeButton("Not now", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getLocation() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final ProgressDialog pDialog = new ProgressDialog(this.getActivity());
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

                            if (Locations.contains(name)) {

                            } else {
                                Locations.add(name);
                            }

                        }


                    }
                    drop_off.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Locations));
                    pick_up.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Locations));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pDialog.dismiss();
                Toasty.error(getActivity(), "An Error Occurred" + error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });

        MySingleton.getInstance(this.getActivity()).addToRequestQueue(stringRequest);


        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);


    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


    void getPickUpDate() {
        final Calendar mcurrentDate = Calendar.getInstance();

        mcurrentDate.set(mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH) + 2,
                mcurrentDate.get(Calendar.HOUR_OF_DAY), mcurrentDate.get(Calendar.MINUTE), 0);


        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                pick_date.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }


    void getDropOffDate() {
        final Calendar mcurrentDate = Calendar.getInstance();

        mcurrentDate.set(mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH) + 2,
                mcurrentDate.get(Calendar.HOUR_OF_DAY), mcurrentDate.get(Calendar.MINUTE), 0);


        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                drop_date.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }


    void getPickupTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                pick_time.setText( "" + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute));


//                pick_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    void getDropOffTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                drop_time.setText( "" + checkDigit(selectedHour) + ":" + checkDigit(selectedMinute));

//                drop_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
