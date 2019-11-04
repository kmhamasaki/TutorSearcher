package com.example.tutorsearcherandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    private List<Integer> MatchedAvailability;
    private ArrayList<String> timeCodes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView timeTextView;
        //TODO: Add more profile information

        public ViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.time_availability);
        }
    }
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
//        String avail = Integer.toString(MatchedAvailability.get(position));
        System.out.println(timeCodes.get(position));
        TextView textView = viewholder.timeTextView;
        textView.setText(timeCodes.get(position));
    }

    @Override
    public int getItemCount() {
        return MatchedAvailability.size();
    }
}

