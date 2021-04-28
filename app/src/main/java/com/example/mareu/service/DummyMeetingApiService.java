package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.List;


public class DummyMeetingApiService implements MeetingApiService{

    private final List<Meeting> mMeetings = DummyMeetingGenerator.getMeetings_list();

    List<Meeting> filterList = new ArrayList<>();

    boolean filterEmpty;

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
        filterEmpty = false;
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
        filterIsEmpty();
        filterList = resultList;
    }

    @Override
    public void filterDate(String date, String time) {
        filterList = new ArrayList<>();
        ArrayList<Meeting> resultListDate = new ArrayList<>();
        for (Meeting meeting : mMeetings) {
            if (time.equalsIgnoreCase(meeting.getTime()) && date.equalsIgnoreCase(meeting.getDate())) {
                resultListDate.add(meeting);
            }
        }
        filterIsEmpty();
        filterList = resultListDate;
    }

    public void filterIsEmpty() {
        List<Meeting> filterIsEmpty = getFilterList();

        filterEmpty = filterIsEmpty.isEmpty();
    }

    @Override
    public Boolean getFilterIsEmpty() { return filterEmpty; }
}
