package com.example.dissertation;

public class medicineList {
    String id, medNam, dosage, freq;

    public String get_Id() {
        return id;
    }

    public String getMedNam() {
        return medNam;
    }

    public String getFreq() {
        return freq;
    }
    public String getDosage() {
        return dosage;
    }




    public medicineList( String medNam, String dosage, String freq) {
        this.medNam = medNam;
        this.dosage = dosage;
        this.freq = freq;
    }
}
