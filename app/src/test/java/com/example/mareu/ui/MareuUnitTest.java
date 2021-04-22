package com.example.mareu.ui;

import com.example.mareu.DI.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MareuUnitTest {

    private MeetingApiService mMeetingApiService;

    @Before
    public void setup() {
        mMeetingApiService = DI.getMeetingApiService();
        Meeting meeting = new Meeting("Réunion 1", "09:30", "Mario", "alexandra@lamzone.com", 2131099862);
        mMeetingApiService.createMeeting(meeting);
    }

    @Test
    public void a_getMeetingWithSuccess() {
        List<Meeting> mMeetings = mMeetingApiService.getMeeting();
        Assert.assertEquals(mMeetings.size(), 1);
    }

    @Test
    public void b_deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mMeetingApiService.getMeeting().get(0);
        mMeetingApiService.deleteMeeting(meetingToDelete);
        Assert.assertFalse(mMeetingApiService.getMeeting().contains(meetingToDelete));
    }

    @Test
    public void c_createMeetingWithSuccess() {
        Meeting meetingToCreate = mMeetingApiService.getMeeting().get(0);
        mMeetingApiService.createMeeting(meetingToCreate);
        Assert.assertTrue(mMeetingApiService.getMeeting().contains(meetingToCreate));
    }

    @Test
    public void d_filterRoomWithSuccess() {
        Meeting meetingToFilter = new Meeting("Réunion 3", "11:30", "Peach", "francis@lamzone.com", 2131099865);
        Meeting meetingTestFiltering = mMeetingApiService.getMeeting().get(0);
        mMeetingApiService.createMeeting(meetingToFilter);
        ArrayList<String> roomTest = new ArrayList<>();
        roomTest.add("Réunion 3");
        mMeetingApiService.filterRoom(roomTest);
        Assert.assertTrue(mMeetingApiService.getFilterList().contains(meetingToFilter));
        Assert.assertEquals(mMeetingApiService.getFilterList().size(), 1);
    }

    @Test
    public void e_filterTimeWithSuccess() {
        Meeting meetingToFilter = new Meeting("Réunion 5", "15:15", "Luigi", "eric@lamzone.com", 2131099867);
        Meeting meetingTestFiltering = mMeetingApiService.getMeeting().get(0);
        mMeetingApiService.createMeeting(meetingToFilter);
        mMeetingApiService.filterDate("15:15");
        Assert.assertTrue(mMeetingApiService.getFilterList().contains(meetingToFilter));
        Assert.assertEquals(mMeetingApiService.getFilterList().size(), 1);
    }

    @Test
    public void f_resetFilterWithSuccess() {
        mMeetingApiService.resetFilter();
        List<Meeting> mMeetingsTest = mMeetingApiService.getFilterList();
        Assert.assertEquals(mMeetingsTest.size(), 0);
    }
}