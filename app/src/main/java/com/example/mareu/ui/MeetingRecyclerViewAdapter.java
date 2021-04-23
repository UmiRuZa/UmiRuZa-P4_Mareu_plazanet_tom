package com.example.mareu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.service.MeetingApiService;

import java.util.List;


public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    List<Meeting> mMeetingsFinal;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView meetingInfo;
        TextView guestsInfo;
        ImageView roomColor;
        ImageButton deleteButton;
        Meeting meeting;
        MeetingApiService meetingApiService;

        public MeetingApiService getMeetingApiService() {
            meetingApiService = DI.getMeetingApiService();
            return meetingApiService;
        }

        public ViewHolder(View itemView){
            super(itemView);
            getMeetingApiService();

            meetingInfo = (TextView) itemView.findViewById(R.id.meeting_info);
            guestsInfo = (TextView) itemView.findViewById(R.id.guests_info);
            roomColor = (ImageView) itemView.findViewById(R.id.room_color);
            deleteButton = (ImageButton) itemView.findViewById(R.id.delete_button);

            itemView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    meetingApiService.deleteMeeting(meeting);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public MeetingRecyclerViewAdapter(List<Meeting> meetings){
        mMeetingsFinal = meetings;
    }

    @Override
    public MeetingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View meetingView = inflater.inflate(R.layout.fragment_meeting_item, parent, false);

        return new ViewHolder(meetingView);
    }

    @Override
    public void onBindViewHolder(MeetingRecyclerViewAdapter.ViewHolder holder, int position) {
        Meeting meeting = mMeetingsFinal.get(position);

        TextView textView = holder.meetingInfo;
        textView.setText(meeting.getRoom() + "-" + meeting.getTime() + "-" + meeting.getTopic());
        TextView textView1 = holder.guestsInfo;
        textView1.setText(meeting.getGuests());
        ImageView imageView = holder.roomColor;
        imageView.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), meeting.getColorTint()));
        holder.meeting = meeting;
    }


    @Override
    public int getItemCount() { return mMeetingsFinal.size(); }
}
