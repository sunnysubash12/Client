package com.example.dissertation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Health extends AppCompatActivity {
    ImageView Home;
    Button profile,med,doc,appointment,report,history;
    String userName,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        Intent intent=getIntent();
        userName=intent.getStringExtra("username");
        userId=intent.getStringExtra("id");
        Home=findViewById(R.id.back2);
        profile=findViewById(R.id.profile);
        med=findViewById(R.id.med);
        doc=findViewById(R.id.doctor);
        appointment= findViewById(R.id.appointment);
        report=findViewById(R.id.recentReports);
        history=findViewById(R.id.history);
        Home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),Profile.class);
                next.putExtra("user", userName);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(next);
            }
        });
        med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),Medicine.class);
                next.putExtra("userid", userId);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(next);
            }
        });
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),Docters.class);
                startActivity(next);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),Appointment.class);
                next.putExtra("uid", userId);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(next);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),Reports.class);
                next.putExtra("uiid", userId);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(next);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),History.class);
                next.putExtra("idd", userId);
                next.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(next);
            }
        });


    }
}
