package com.example.mareu.model;

public class Meeting {

    private String room;
    private String time;
    private String subject;
    private String guests;
    private int colorTint;

    public Meeting(String room, String time,String subject, String guests, int colorTint) {

        this.room = room;
        this.time = time;
        this.subject = subject;
        this.guests = guests;
        this.colorTint = colorTint;
    }

    public String getRoom() {return room;}

    public String getTime() {return time;}

    public String getSubject() {return subject;}

    public String getGuests() {return guests;}

    public int getColorTint() { return colorTint; }
}
