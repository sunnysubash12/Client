package com.example.dissertation;

public class docterList {
    String  docNam, special, avail;

    public String getDocNam() {
        return docNam;
    }

    public String getSpecial() {
        return special;
    }
    public String getAvail() {
        return avail;
    }




    public docterList(String docNam, String special, String avail) {
        this.docNam = docNam;
        this.special = special;
        this.avail = avail;
    }
}
