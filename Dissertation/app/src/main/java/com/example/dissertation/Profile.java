package com.example.dissertation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Profile extends AppCompatActivity {
    String userName;
    String url;
    TextView name, gendeer, agge,num,address,history,allergy;
    ImageView Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.pName);
        gendeer = findViewById(R.id.pGend);
        agge = findViewById(R.id.pAge);
        num = findViewById(R.id.pCont);
        address = findViewById(R.id.pAdd);
        history = findViewById(R.id.pHis);
        allergy = findViewById(R.id.pAll);
        Home=findViewById(R.id.back2heal);
        Intent next=getIntent();
        userName=next.getStringExtra("user");
        url = "https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/fetchProfile/"+userName;
        SSLUtils.initializeSSL(getApplicationContext());
        Home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("addProfile", "API Response: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    String fullName = dataObject.getString("name");
                    name.setText(fullName);
                        String gender = dataObject.getString("gender");
                    gendeer.setText(gender);
                        int age = dataObject.getInt("age");
                        agge.setText(String.valueOf(age));
                        String no = dataObject.getString("contact");
                        num.setText(no);
                        String adress = dataObject.getString("address");
                        address.setText(adress);
                        String hist = dataObject.getString("medical_history");
                        history.setText(hist);
                        String allrgy = dataObject.getString("allergies");
                        allergy.setText(allrgy);

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
}
