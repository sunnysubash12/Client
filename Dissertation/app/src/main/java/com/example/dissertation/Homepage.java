package com.example.dissertation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Homepage extends AppCompatActivity {
    Button fit, heal;
    String userName,patId;
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

        //add patient name in textview
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("addProfile", "API Response: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    patId = dataObject.getString("patient_id");
                    String fullName = dataObject.getString("name");
                    pName.setText("Welcome, "+fullName+"!");

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
                Log.d("addProfile", "API Response: " + "Failed to fetch name");

            }

        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(stringRequest);
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

