package com.example.dissertation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class Fitness extends AppCompatActivity {
    ImageView Home;
    RecyclerView recyclerView;
    CardView cardView;
    List<UserList> exerciseList;
    String url1 = "https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/exercises";
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        exerciseList = new ArrayList<>();
        Home=findViewById(R.id.back);

        Home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
        SSLUtils.initializeSSL(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("Fitness", "API Response: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("Exercise_Name");
                        String picUrl = object.getString("icon");
                        int sets = object.getInt("Sets");
                        String timme = object.getString("Time_Per_Set");
                        UserList list1 = new UserList(picUrl, name, timme, sets);
                        exerciseList.add(list1);
                    }
                    adapter = new UserAdapter(exerciseList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Fitness", "Error parsing JSON: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                // Handle Volley error
                Toast.makeText(Fitness.this, "Failed to fetch exercises", Toast.LENGTH_SHORT).show();

            }

        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue1.add(stringRequest);
    }
    }
