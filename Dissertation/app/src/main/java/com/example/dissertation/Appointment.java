package com.example.dissertation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Appointment extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    ImageView Home;
    RecyclerView recyclerView;
    List<appList> applist;
    String userId;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_appointment);
    recyclerView = findViewById(R.id.rv4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        applist = new ArrayList<>();
    Home=findViewById(R.id.back5);
        Intent next=getIntent();
        userId=next.getStringExtra("uid");
        url="https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/appointment/"+userId;

        Home.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
    });
        SSLUtils.initializeSSL(getApplicationContext());

    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Log.d("Appointment", "API Response: " + response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String date = object.getString("date");
                    String time = object.getString("time");
                    String doctor = object.getString("doctor");
                    String purpose = object.getString("purpose");
                    appList list1 = new appList(date, time, doctor,purpose);
                    applist.add(list1);
                }
                adapter = new appAdapter(applist, getApplicationContext());
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Appointment", "Error parsing JSON: " + e.getMessage());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            // Handle Volley error
            Toast.makeText(getApplicationContext(), "Failed to fetch medicines", Toast.LENGTH_SHORT).show();

        }

    });
    RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(stringRequest);
}
    }

