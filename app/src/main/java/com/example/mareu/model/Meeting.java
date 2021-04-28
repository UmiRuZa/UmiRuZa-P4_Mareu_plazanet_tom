package com.example.mareu.model;

public class Meeting {

    private String room;
    private String time;
    private String date;
    private String topic;
    private String guests;
    private int colorTint;

    public Meeting(String room, String time, String date, String topic, String guests, int colorTint) {

        this.room = room;
        this.time = time;
        this.date = date;
        this.topic = topic;
        this.guests = guests;
        this.colorTint = colorTint;
    }

    public String getRoom() {return room;}

    public String getTime() {return time;}

    public String getDate() {return date;}

    public String getTopic() {return topic;}

    public String getGuests() {return guests;}

    public int getColorTint() { return colorTint; }
}
