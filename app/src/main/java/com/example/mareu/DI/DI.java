package com.example.mareu.DI;

import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;


public class DI {

    private static MeetingApiService mMeetingApiService = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() { return mMeetingApiService; }
}
