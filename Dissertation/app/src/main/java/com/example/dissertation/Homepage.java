package com.example.dissertation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Homepage extends AppCompatActivity {
    Button fit, heal;
    String userName,patId,jwtToken;
    String url;
    TextView pName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        fit = findViewById(R.id.fitt);
        heal = findViewById(R.id.heal);
        pName= findViewById(R.id.pName);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Intent id=getIntent();
        userName=id.getStringExtra("USER");
        url = "https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/fetchProfile/"+userName;
        Log.d("JSON_BODY", userName);
        // Retrieve JWT token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("JWT_TOKEN", "");
        Log.d("token", jwtToken);
        SSLUtils.initializeSSL(getApplicationContext());


        fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Fitness.class);
                startActivity(intent);
            }
        });

        heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Health.class);
                intent.putExtra("username", userName);
                intent.putExtra("id", patId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // Add patient name to TextView
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("addProfile", "API Response: " + response);
                    JSONObject jsonObject = new JSONObject(response);

                    // Check if the response contains "data" object
                    if (jsonObject.has("data")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");

                        // Retrieve patient ID and name
                        patId = dataObject.getString("patient_id");
                        String fullName = dataObject.getString("name");

                        // Set the patient name in the TextView
                        pName.setText("Welcome, " + fullName + "!");
                    } else {
                        // Handle the case where the response does not contain the expected data
                        Log.e("Homepage", "Response does not contain 'data' object");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Homepage", "Error parsing JSON: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Handle Volley error
                Log.e("addProfile", "Failed to fetch name: " + error.getMessage());
                // Display a message to the user indicating the error
                Toast.makeText(Homepage.this, "Failed to fetch name: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Add JWT token to request headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwtToken);
                return headers;
            }
        };

// Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(getApplicationContext()).inflate(R.menu.menu_1, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

