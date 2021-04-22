package com.example.mareu.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.DI.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    FloatingActionButton addMeeting;
    List<Meeting> mMeetings;
    MeetingApiService mMeetingApiService;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMeetingApiService = DI.getMeetingApiService();
        mMeetings = mMeetingApiService.getMeeting();
        recyclerView = findViewById(R.id.rvMeetings);

        addMeeting = (FloatingActionButton)findViewById(R.id.addMeeting);

        FloatingActionButton addButton = (FloatingActionButton)findViewById(R.id.addMeeting);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

        initList();
    }

    private void initList(){
        List<Meeting> mMeetingsFinal;
        List<Meeting> filterList = mMeetingApiService.getFilterList();
        int filterSize = filterList.size();

        if (filterList.isEmpty()){
            mMeetingsFinal = mMeetings;
        } else {
            mMeetingsFinal = filterList;
        }

        MeetingRecyclerViewAdapter meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(mMeetingsFinal);
        recyclerView.setAdapter(meetingRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume(){
        super.onResume();

        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_room) { getFilterRoom(); }

        if (id == R.id.action_time){ getFilterTime(); }

        if (id == R.id.action_reset){
            mMeetingApiService.resetFilter();
            initList();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getFilterRoom() {
        String[] roomsList = getResources().getStringArray(R.array.room_array);
        boolean[] isCheckedList = {false, false, false, false, false, false, false, false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sélectionnez la salle");
        builder.setMultiChoiceItems(roomsList, isCheckedList, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                isCheckedList[which] = isChecked;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ArrayList<String> rooms = new ArrayList<>();
                for (int i = 0; i < roomsList.length; i++) {
                    if (isCheckedList[i]) {
                        rooms.add(roomsList[i]);
                    }
                }
                mMeetingApiService.filterRoom(rooms);
                initList();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void getFilterTime(){
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
        TimePickerDialog picker = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                String calendar ="";
                if (sHour < 10 && sMinute < 10) {
                    calendar = ("0" + sHour + ":" + "0" + sMinute);
                } else if (sHour < 10) {
                    calendar = ("0" + sHour + ":" + sMinute);
                } else if (sMinute < 10) {
                    calendar = (sHour + ":" + "0" + sMinute);
                } else {
                    calendar = (sHour + ":" + sMinute);
                }
                mMeetingApiService.filterDate(calendar);
                initList();
            }
            }, hour, minutes, true);
        picker.show();
    }
}