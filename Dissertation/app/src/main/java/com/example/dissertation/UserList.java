package com.example.dissertation;

public class UserList {
    String no, pic, name, timme;
    int sets;

    public String getNo() {
        return no;
    }

    public String getPic() {
        return pic;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public String getTimme() {
        return timme;
    }


    public UserList( String pic, String name, String timme, int sets) {
        this.pic = pic;
        this.name = name;
        this.timme = timme;
        this.sets = sets;
    }
}
