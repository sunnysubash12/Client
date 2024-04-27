package com.example.dissertation;

public class appList {
    String date, time,doctor,purpose;

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPurpose() {
        return purpose;
    }

    public appList(String date, String time, String doctor, String purpose) {
        this.date = date;
        this.doctor = doctor;
        this.time = time;
        this.purpose = purpose;

    }
}

