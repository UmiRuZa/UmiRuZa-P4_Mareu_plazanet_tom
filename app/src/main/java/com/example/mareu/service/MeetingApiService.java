package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;


public interface MeetingApiService {

    List<Meeting> getMeeting();

    void deleteMeeting(Meeting meeting);

    void createMeeting(Meeting meeting);



    List<Meeting> getFilterList();

    void resetFilter();

    void filterRoom(ArrayList<String> rooms);

    void filterDate(String date, String time);

    Boolean getFilterIsEmpty();

    void setMeetingRoomTaken(String roomTaken, String date, String time);

    Boolean meetingRoomCheck();
}
