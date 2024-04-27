package com.example.dissertation;

public class hisList {
    String date1,date2,doctor1,doctor2,description1,description2;

    public String getDate1() {
        return date1;
    }
    public String getDate2() {
        return date2;
    }

    public String getDescription1() {
        return description1;
    }
    public String getDescription2() {
        return description2;
    }

    public String getDoctors1() {
        return doctor1;
    }

    public String getDoctors2() {
        return doctor2;
    }

    public hisList(String date1, String date2,String doctor1,String doctor2, String description1, String description2) {
        this.date1 = date1;
        this.date2 = date2;
        this.doctor1 = doctor1;
        this.doctor2 = doctor2;
        this.description1 = description1;
        this.description2 = description2;

    }
}

