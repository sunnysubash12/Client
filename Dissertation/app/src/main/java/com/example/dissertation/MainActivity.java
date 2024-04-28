package com.example.dissertation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity{
    EditText pass;
    EditText user;
    TextView reset;
    ProgressBar loading;
    Button log;
    String URL_LOGIN = "https://healthapp-env.eba-83ymjihv.eu-west-2.elasticbeanstalk.com/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset=findViewById(R.id.reset);
        user = findViewById(R.id.un);
        pass = findViewById(R.id.pass);
        loading = findViewById(R.id.loading);
        log = findViewById(R.id.log);
        SSLUtils.initializeSSL(getApplicationContext());

        log.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (user.getText().toString().trim().equals("") && pass.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz Enter your Details", Toast.LENGTH_SHORT).show();
                } else if (!user.getText().toString().trim().equals("") && pass.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz Enter your Password", Toast.LENGTH_SHORT).show();
                } else if (user.getText().toString().trim().equals("") && !pass.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Plz Enter your Username", Toast.LENGTH_SHORT).show();
                } else {
                    adminLogin(user.getText().toString(),pass.getText().toString().trim());
                    hideKeyboard(v);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Plz wait for 24h your request for new Password has been sent to our Technical Department",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void adminLogin(String Username,String Password) {
        // Create a JSON object for the credentials
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", Username);
            jsonBody.put("password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON exception if any
            return;
        }
        Log.d("JSON_BODY", jsonBody.toString());


        // Create a request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_LOGIN, jsonBody,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Check if the response contains a token
                    if (response.has("token")) {
                        // Successful login
                        String token = response.getString("token");
                        // Proceed with token
                        handleSuccessfulLogin(token, Username);
                        Log.d("token", token);

                    } else {
                        // Invalid credentials
                        handleInvalidCredentials();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON exception if any
                    handleErrorResponse("Error parsing response");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Invalid credentials
                handleInvalidCredentials();
                // Handle network errors
                Log.e("MainActivity", "Error: " + error.toString());
                // Print the response data if available
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    String responseString = new String(error.networkResponse.data);
                    Log.e("MainActivity", "Response: " + responseString);
                }
            }
        });
        // Add the request to the RequestQueue
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    // Method to handle successful login
    private void handleSuccessfulLogin(String token, String user) {
        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
        // Store the JWT token for future use
        saveTokenToSharedPreferences(token);
        //move to next activity
        Intent id = new Intent(getApplicationContext(), Homepage.class);
        id.putExtra("USER", user);
        id.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(id);
        finish();
    }

    // Method to handle invalid credentials
    private void handleInvalidCredentials() {
        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        // Clear input fields or take appropriate action
    }

    // Method to handle error response
    private void handleErrorResponse(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Method to save the JWT token to SharedPreferences
    private void saveTokenToSharedPreferences(String token) {
        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Save the token to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }


    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}


