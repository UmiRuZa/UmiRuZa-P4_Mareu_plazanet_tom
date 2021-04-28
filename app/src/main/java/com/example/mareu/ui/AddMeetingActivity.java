package com.example.mareu.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import android.app.TimePickerDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class AddMeetingActivity extends AppCompatActivity {

    TimePickerDialog timePicker;
    EditText eTextTime;

    DatePickerDialog datePicker;
    EditText eTextDate;

    EditText topic;

    TextView room;
    Dialog dialogRoom;
    ImageView colorImageView;
    int color;

    TextView guests;
    Dialog dialogGuests;
    ArrayList<String> guestsList = new ArrayList<>();

    private MeetingApiService mMeetingApiService;
    int buttonTrigger = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_meeting);

        getRoom();
        getGuests();
        getTime();
        getDate();
        getTopic();
        setMeetingInfo();

        mMeetingApiService = DI.getMeetingApiService();
    }

    public void getRoom() {
        final boolean[] hasRun = {false};

        room = findViewById(R.id.textViewRoom);

        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRoom = new Dialog(AddMeetingActivity.this);
                dialogRoom.setContentView(R.layout.dialog_searchable_spinner_room);
                dialogRoom.getWindow().setLayout(1300,1800);
                dialogRoom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogRoom.show();

                EditText editTextRoom = dialogRoom.findViewById(R.id.edit_text_room);
                ListView listViewRoom = dialogRoom.findViewById(R.id.list_view_room);

                ArrayAdapter<CharSequence> adapter_room = ArrayAdapter.createFromResource(AddMeetingActivity.this,
                        R.array.room_array, android.R.layout.simple_spinner_dropdown_item);
                listViewRoom.setAdapter(adapter_room);

                editTextRoom.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) { adapter_room.getFilter().filter(s);}
                    @Override
                    public void afterTextChanged(Editable s) { }
                });

                listViewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        room.setText(adapter_room.getItem(position));
                        getColorTint();
                        dialogRoom.dismiss();
                        if (!hasRun[0]) {
                            buttonTrigger++;
                            setAddButton(buttonTrigger);
                            hasRun[0] = true;
                        }
                    }
                });
            }
        });
    }

    public int getColorTint() {
        colorImageView = findViewById(R.id.roomColorFilter);
        String s = room.getText().toString();

        switch (s) {
            case "Réunion 1":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_1));
                return color = R.color.réunion_1;
            case "Réunion 2":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_2));
                return color = R.color.réunion_2;
            case "Réunion 3":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_3));
                return color = R.color.réunion_3;
            case "Réunion 4":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_4));
                return color = R.color.réunion_4;
            case "Réunion 5":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_5));
                return color = R.color.réunion_5;
            case "Réunion 6":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_6));
                return color = R.color.réunion_6;
            case "Réunion 7":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_7));
                return color = R.color.réunion_7;
            case "Réunion 8":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_8));
                return color = R.color.réunion_8;
            case "Réunion 9":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_9));
                return color = R.color.réunion_9;
            case "Réunion 10":
                colorImageView.setColorFilter(ContextCompat.getColor(AddMeetingActivity.this, R.color.réunion_10));
                return color = R.color.réunion_10;
        }
        return 0;
    }

    public void getTime(){
        final boolean[] hasRun = {false};

        eTextTime = (EditText) findViewById(R.id.editTextTime);
        eTextTime.setInputType(InputType.TYPE_NULL);

        eTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(AddMeetingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if (sHour < 10 && sMinute < 10) {
                                    eTextTime.setText("0" + sHour + ":" + "0" + sMinute);
                                } else if (sHour < 10) {
                                    eTextTime.setText("0" + sHour + ":" + sMinute);
                                } else if (sMinute < 10) {
                                    eTextTime.setText(sHour + ":" + "0" + sMinute);
                                } else {
                                    eTextTime.setText(sHour + ":" + sMinute);
                                }
                                if (!hasRun[0]) {
                                    buttonTrigger++;
                                    setAddButton(buttonTrigger);
                                    hasRun[0] = true;
                                }
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });
    }

    public void getDate() {
        final boolean[] hasRun = {false};

        eTextDate = findViewById(R.id.editTextDate);
        eTextDate.setInputType(InputType.TYPE_NULL);

        eTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar caldr = Calendar.getInstance();
                int year = caldr.get(Calendar.YEAR);
                int month = caldr.get(Calendar.MONTH);
                int day = caldr.get(Calendar.DAY_OF_WEEK);
                datePicker = new DatePickerDialog(AddMeetingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eTextDate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        if (!hasRun[0]) {
                            buttonTrigger++;
                            setAddButton(buttonTrigger);
                            hasRun[0] = true;
                        }
                    }
                }, year, month, day);
                datePicker.show();
            }
        });
    }

    private void getTopic() {
        final boolean[] hasRun = {false};

        topic = findViewById(R.id.topicLyt);

        topic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!hasRun[0]) {
                    buttonTrigger++;
                    setAddButton(buttonTrigger);
                    hasRun[0] = true;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void getGuests() {
        final boolean[] hasRun = {false};

        guests = findViewById(R.id.textViewGuests);

        guests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogGuests = new Dialog(AddMeetingActivity.this);
                dialogGuests.setContentView(R.layout.dialog_searchable_spinner_guests);
                dialogGuests.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogGuests.show();

                EditText editTextGuests = dialogGuests.findViewById(R.id.edit_text_guests);
                ListView listViewGuests = dialogGuests.findViewById(R.id.list_view_guests);
                Button selectButton = dialogGuests.findViewById(R.id.select_button);

                ArrayAdapter<CharSequence> adapter_guests = ArrayAdapter.createFromResource(AddMeetingActivity.this,
                        R.array.guests_array, android.R.layout.simple_list_item_multiple_choice);
                listViewGuests.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listViewGuests.setAdapter(adapter_guests);

                editTextGuests.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter_guests.getFilter().filter(s);
                    }
                    @Override
                    public void afterTextChanged(Editable s) { }});

                listViewGuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        guestsList.add((String) adapter_guests.getItem(position));
                    }
                });

                selectButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getGuestText();
                        dialogGuests.dismiss();
                        if (!hasRun[0]) {
                            buttonTrigger++;
                            setAddButton(buttonTrigger);
                            hasRun[0] = true;
                        }
                    }
                });
            }
        });
    }

    public void getGuestText() {
        StringBuilder guestsText = new StringBuilder(String.valueOf(guestsList));

        guestsText = guestsText.deleteCharAt(0);
        guestsText = guestsText.deleteCharAt(guestsText.length()-1);

        guests.setText(guestsText);
    }

    private void setAddButton(int buttonTrigger) {
        MaterialButton addButton = findViewById(R.id.addButton);
        addButton.setEnabled(buttonTrigger == 5);
    }

    public void setMeetingInfo() {
        final MaterialButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meeting = new Meeting(
                        room.getText().toString(),
                        eTextTime.getText().toString(),
                        eTextDate.getText().toString(),
                        topic.getText().toString(),
                        guests.getText().toString(),
                        color
                );
                mMeetingApiService.createMeeting(meeting);
                mMeetingApiService.resetFilter();
                finish();
            }
        });
    }
}
