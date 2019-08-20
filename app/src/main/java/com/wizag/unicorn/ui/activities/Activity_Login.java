package com.wizag.unicorn.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.wizag.unicorn.R;
import com.wizag.unicorn.utils.Constants;
import com.wizag.unicorn.utils.MySingleton;
import com.wizag.unicorn.utils.SessionManager;
import es.dmoral.toasty.Toasty;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Login extends AppCompatActivity {
    Button login;
    TextView register;
    String loginUrl = Constants.baseUrl + "login";
    SessionManager sessionManager;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    void init() {

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sessionManager = new SessionManager(getApplicationContext());
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), Activity_Featured_Vehicles.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isNetworkConnected()) {
                    Toasty.warning(getApplicationContext(), "Ensure you have internet connection", Toasty.LENGTH_SHORT).show();
                } else if (email.getText().toString().isEmpty()) {
                    email.setError("Enter Email to continue");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Enter Password to proceed");
                } else {
                    LoginUser();
                }


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Registration.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void LoginUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {

                    JSONObject obj = new JSONObject(response);

                    String token = obj.getString("token");
                    JSONObject user = obj.getJSONObject("user");
                    String fName = user.getString("first_name");
                    String lName = user.getString("last_name");
                    String email = user.getString("email");
                    String phone = user.getString("phone");

                    sessionManager.createLoginSession(token, fName, lName, email, phone);
                    Toasty.success(getApplicationContext(), "User Logged in Successfully", Toast.LENGTH_SHORT, true).show();


                    Intent intent = new Intent(getApplicationContext(), Activity_Featured_Vehicles.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();


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
                        error.printStackTrace();
                        Toasty.error(getApplicationContext(), "An Error Occurred", Toast.LENGTH_SHORT, true).show();
                    }
                }


        ) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }


        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
