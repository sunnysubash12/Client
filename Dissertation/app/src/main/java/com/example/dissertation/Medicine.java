package com.example.dissertation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class Medicine extends AppCompatActivity {
    RecyclerView.Adapter adapter;
    ImageView Home;
    RecyclerView recyclerView;
    List<medicineList> medicinelist;
    String userId;
    String url,jwtToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_medicine);
    recyclerView = findViewById(R.id.rv2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        medicinelist = new ArrayList<>();
    Home=findViewById(R.id.back3);
        Intent next=getIntent();
        userId=next.getStringExtra("userid");
        url="https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/fetchMed/"+userId;

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
                Log.d("Medicine", "API Response: " + response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String medicine = object.getString("medicine_name");
                    String dosage = object.getString("dosage");
                    String freq = object.getString("frequency");
                    medicineList list1 = new medicineList(medicine, dosage, freq);
                    medicinelist.add(list1);
                }
                adapter = new medAdapter(medicinelist, getApplicationContext());
                recyclerView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Medicine", "Error parsing JSON: " + e.getMessage());
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            // Handle Volley error
            Toast.makeText(getApplicationContext(), "Failed to fetch medicines", Toast.LENGTH_SHORT).show();

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

