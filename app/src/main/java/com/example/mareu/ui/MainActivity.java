package com.example.mareu.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

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

        addMeeting = findViewById(R.id.addMeeting);

        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });

        initList();
    }

    @SuppressLint("WrongConstant")
    private void initList(){
        List<Meeting> mMeetingsFinal;
        List<Meeting> filterList = mMeetingApiService.getFilterList();
        boolean filterEmpty = false;

        filterEmpty = mMeetingApiService.getFilterIsEmpty();

        if (filterList.isEmpty() && !filterEmpty){
            mMeetingsFinal = mMeetings;
        } else if (filterList.isEmpty() && filterEmpty){
            mMeetingsFinal = filterList;
            int i = 10;
            Toast.makeText(MainActivity.this, "Aucune réunion ne correspond a votre filtre", i).show();
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

        if (id == R.id.action_time){ getFilterDate(); }

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

    public void getFilterDate(){
        final String[] cldrTime = {""};
        final String[] cldrDate = {""};

        final Calendar cldr = Calendar.getInstance();
        int year = cldr.get(Calendar.YEAR);
        int month = cldr.get(Calendar.MONTH);
        int day = cldr.get(Calendar.DAY_OF_WEEK);
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        DatePickerDialog datePicker = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cldrDate[0] = (dayOfMonth + "/" + (month+1) + "/" + year);

                TimePickerDialog picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if (sHour < 10 && sMinute < 10) {
                                    cldrTime[0] = ("0" + sHour + ":" + "0" + sMinute);
                                } else if (sHour < 10) {
                                    cldrTime[0] = ("0" + sHour + ":" + sMinute);
                                } else if (sMinute < 10) {
                                    cldrTime[0] = (sHour + ":" + "0" + sMinute);
                                } else {
                                    cldrTime[0] = (sHour + ":" + sMinute);
                                }
                                mMeetingApiService.filterDate(cldrDate[0], cldrTime[0]);
                                initList();
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        }, year, month, day);
        datePicker.show();
    }
}