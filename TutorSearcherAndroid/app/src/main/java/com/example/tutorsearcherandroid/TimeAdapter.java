package com.example.tutorsearcherandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private List<Integer> MatchedAvailability;
    private ArrayList<String> timeCodes;
    private RadioButton lastCheckedButton = null;


    //Constructor: Initialize tutor availabilities and time codes
    public TimeAdapter(List<Integer> avail, ArrayList<String> times){
        MatchedAvailability = avail;
        timeCodes = times;
    }

    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create a new view
        View availView = inflater.inflate(R.layout.tutor_time, parent, false);
        TimeAdapter.ViewHolder viewHolder = new TimeAdapter.ViewHolder(availView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeAdapter.ViewHolder viewholder, int position) {
        TextView textView = viewholder.timeTextView;
        textView.setText(timeCodes.get(position));
        RadioButton radioGroup = viewholder.radioButton;
//        radioGroup.setId(position);
    }

    @Override
    public int getItemCount() {
        return MatchedAvailability.size();
    }

    //ViewHolder Class: Initialize views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public TextView timeTextView;
        public RadioButton radioButton;
        Boolean selected;

        public ViewHolder(View itemView){
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_availability);
            radioButton = itemView.findViewById(R.id.rbutton);
            selected = false;

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selected){
                        radioButton.setChecked(false);
                        selected = false;
                        System.out.println("Test Radio Button Off");
                    }else{
                        radioButton.setChecked(true);
                        selected = true;
                    }
                }
            });
        }

    }
}

