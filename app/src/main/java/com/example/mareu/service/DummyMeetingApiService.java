package com.example.mareu.service;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;


public class DummyMeetingApiService implements MeetingApiService{

    private final List<Meeting> mMeetings = DummyMeetingGenerator.getMeetings_list();

    List<Meeting> filterList = new ArrayList<>();

    @Override
    public List<Meeting> getMeeting() {
        return mMeetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        mMeetings.add(meeting);
    }



    @Override
    public List<Meeting> getFilterList() {return filterList;}

    @Override
    public void resetFilter() {
        filterList = new ArrayList<>();
    }

    @Override
    public void filterRoom(ArrayList<String> rooms) {
        filterList = new ArrayList<>();
        ArrayList<Meeting> resultList = new ArrayList<>();
        if (rooms == null || rooms.isEmpty()){
            filterList = mMeetings;
        }else {
            for (String room : rooms) {
                for (Meeting meeting : mMeetings) {
                    if (room.equalsIgnoreCase(meeting.getRoom())) {
                        resultList.add(meeting);
                    }
                }
            }
        }
        if (resultList.isEmpty()) {
            Meeting meetingNull = new Meeting("Aucune", "Réunion", "Correspondante", "A ce filtre", R.color.Filtre_vide);
            resultList.add(meetingNull);
        }
        filterList = resultList;
    }

    @Override
    public void filterDate(String s) {
        filterList = new ArrayList<>();
        ArrayList<Meeting> resultListDate = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (s.equalsIgnoreCase(meeting.getTime())) {
                resultListDate.add(meeting);
            }
        }
        if (resultListDate.isEmpty()) {
            Meeting meetingNull = new Meeting("Aucune", "Réunion", "Correspondante", "A ce filtre", R.color.Filtre_vide);
            resultListDate.add(meetingNull);
        }
        filterList = resultListDate;
    }
}
