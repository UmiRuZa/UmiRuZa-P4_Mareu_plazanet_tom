package com.example.mareu.service;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DummyMeetingGenerator {

    public static List<Meeting> meetings_list = Arrays.asList(
    );

    static List<Meeting> getMeetings_list() {
        return new ArrayList<>(meetings_list);
    }
}
