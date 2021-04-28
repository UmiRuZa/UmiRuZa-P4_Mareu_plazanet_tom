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
    }

    @Test
    public void a_getMeetingEmptyWithSuccess() {
        List<Meeting> mMeetings = mMeetingApiService.getMeeting();
        Assert.assertEquals(mMeetings.size(), 0);
    }

    @Test
    public void b_createMeetingWithSuccess() {
        Meeting meetingToCreate = new Meeting("Réunion 1", "09:30", "6/4/2021", "Mario", "alexandra@lamzone.com", 2131099862);
        mMeetingApiService.createMeeting(meetingToCreate);
        Assert.assertTrue(mMeetingApiService.getMeeting().contains(meetingToCreate));
    }

    @Test
    public void c_createMeetingOverlapWithSuccess() {
        Meeting meetingToOverlap = new Meeting("Réunion 1", "09:30", "6/4/2021", "Luigi", "bernard@lamzone.com", 2131099862);
        mMeetingApiService.setMeetingRoomTaken("Réunion 1", "09:30","6/4/2021");
        if (!mMeetingApiService.meetingRoomCheck()) {
            mMeetingApiService.createMeeting(meetingToOverlap);
        }
        Assert.assertFalse(mMeetingApiService.getMeeting().contains(meetingToOverlap));
    }

    @Test
    public void d_deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mMeetingApiService.getMeeting().get(0);
        mMeetingApiService.deleteMeeting(meetingToDelete);
        Assert.assertFalse(mMeetingApiService.getMeeting().contains(meetingToDelete));
    }

    @Test
    public void e_filterRoomWithSuccess() {
        Meeting meetingToFilter = new Meeting("Réunion 3", "11:30", "12/4/2021", "Peach", "francis@lamzone.com", 2131099865);
        b_createMeetingWithSuccess();
        mMeetingApiService.createMeeting(meetingToFilter);
        ArrayList<String> roomTest = new ArrayList<>();
        roomTest.add("Réunion 3");
        mMeetingApiService.filterRoom(roomTest);
        Assert.assertTrue(mMeetingApiService.getFilterList().contains(meetingToFilter));
        Assert.assertEquals(mMeetingApiService.getFilterList().size(), 1);
        d_deleteMeetingWithSuccess();
        d_deleteMeetingWithSuccess();
    }

    @Test
    public void f_filterTimeWithSuccess() {
        Meeting meetingToFilter = new Meeting("Réunion 5", "15:15", "9/4/2021", "Luigi", "eric@lamzone.com", 2131099867);
        b_createMeetingWithSuccess();
        mMeetingApiService.createMeeting(meetingToFilter);
        mMeetingApiService.filterDate("9/4/2021", "15:15");
        Assert.assertTrue(mMeetingApiService.getFilterList().contains(meetingToFilter));
        Assert.assertEquals(mMeetingApiService.getFilterList().size(), 1);
    }

    @Test
    public void g_resetFilterWithSuccess() {
        Assert.assertEquals(mMeetingApiService.getFilterList().size(), 1);
        mMeetingApiService.resetFilter();
        List<Meeting> mMeetingsTest = mMeetingApiService.getFilterList();
        Assert.assertEquals(mMeetingsTest.size(), 0);
    }
}