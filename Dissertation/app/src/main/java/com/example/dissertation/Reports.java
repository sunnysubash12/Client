package com.example.dissertation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reports extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    ImageView Home;
    RecyclerView recyclerView;
    List<repList> replist;
    String userId;
    String url,jwtToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reports);
    recyclerView = findViewById(R.id.rv5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        replist = new ArrayList<>();
    Home=findViewById(R.id.back6);
        Intent next=getIntent();
        userId=next.getStringExtra("uiid");
        url="https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/reports/"+userId;

        Home.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    });
        // Retrieve JWT token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("JWT_TOKEN", "");
        SSLUtils.initializeSSL(getApplicationContext());

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Log.d("Reports", "API Response: " + response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String date = object.getString("date");
                    String name = object.getString("name");
                    String doctor = object.getString("doctor");
                    String description = object.getString("description");
                    repList list1 = new repList(date, name, doctor,description);
                    replist.add(list1);
                }
                adapter = new repAdapter(replist, getApplicationContext());
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Reports", "Error parsing JSON: " + e.getMessage());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            // Handle Volley error
            Toast.makeText(getApplicationContext(), "Failed to fetch reports", Toast.LENGTH_SHORT).show();

        }

    }){
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            // Add JWT token to request headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", jwtToken);
            return headers;
        }
    };
    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(stringRequest);
}
    }

