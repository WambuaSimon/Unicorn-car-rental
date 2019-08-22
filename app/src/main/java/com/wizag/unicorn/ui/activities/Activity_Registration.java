package com.wizag.unicorn.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wizag.unicorn.R;
import com.wizag.unicorn.utils.Constants;
import com.wizag.unicorn.utils.MySingleton;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Registration extends AppCompatActivity {
    TextView login;
    Button register;
    EditText fName, lName, email, phone, password, confirm_password;
    String RegisterUrl = Constants.baseUrl + "register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }


    void init() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.password_edit_text);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!isNetworkConnected()) {
                    Toasty.warning(getApplicationContext(), "Ensure you have internet connection", Toasty.LENGTH_SHORT).show();
                } else if (fName.getText().toString().isEmpty()) {
                    fName.setError("Enter First Name");
                } else if (lName.getText().toString().isEmpty()) {
                    lName.setError("Enter Last Name");
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("Enter Email");
                } else if (phone.getText().toString().isEmpty()) {
                    phone.setError("Enter Phone Number");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Enter Password");
                } else if (!password.getText().toString().equalsIgnoreCase(confirm_password.getText().toString())) {
                    confirm_password.setError("Ensure Both Passwords Match");
                } else {
                    registerUser();
                }


            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    private void registerUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing up...");
        progressDialog.show();
        //getText

        StringRequest stringRequest = new StringRequest(Request.Method.POST, RegisterUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    //converting response to json object

                    JSONObject obj = new JSONObject(response);
                    String token = obj.getString("token");

                    Intent intent = new Intent(getApplicationContext(), Activity_Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toasty.success(getApplicationContext(), "User Registered Successfully", Toasty.LENGTH_SHORT, true).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.getMessage();
                        Toasty.error(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                }


        ) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("first_name", fName.getText().toString());
                params.put("last_name", lName.getText().toString());
                params.put("phone_number", phone.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("password_confirmation", confirm_password.getText().toString());
                return params;
            }


        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
