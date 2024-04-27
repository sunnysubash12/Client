package com.example.dissertation;

public class repList {
    String date, name,doctor,description;

    public String getDates() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getDoctors() {
        return doctor;
    }

    public String getNames() {
        return name;
    }

    public repList(String date, String name, String doctor, String description) {
        this.date = date;
        this.doctor = doctor;
        this.name = name;
        this.description = description;

    }
}

