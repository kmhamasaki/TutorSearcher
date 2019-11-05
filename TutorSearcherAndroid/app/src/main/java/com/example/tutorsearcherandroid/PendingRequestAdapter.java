package com.example.tutorsearcherandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;


import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;
import tutor.searcher.TutorSearcher.TutorRequest;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.ViewHolder> {

    private List<TutorRequest> requests;
    private OnButtonClickListener mListener;

    public interface OnButtonClickListener{
        void onButtonClick(int position, Boolean accept);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tutee_name;
        public TextView class_name;
        public Button accept_button;
        public Button reject_button;


        public ViewHolder(View itemView, final OnButtonClickListener listener) {
            super(itemView);
            tutee_name = itemView.findViewById(R.id.tutee_name);
            class_name = itemView.findViewById(R.id.class_name);
            accept_button = itemView.findViewById(R.id.accept_button);
            reject_button = itemView.findViewById(R.id.reject_button);

            accept_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("PendingRequestAdapter.onClick");
                    int position = getAdapterPosition();
                    listener.onButtonClick(position, true);
                }
            });

            reject_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("PendingRequestAdapter.onClick");
                    int position = getAdapterPosition();
                    listener.onButtonClick(position, false);
                }
            });
        }
    }

    // Constructor
    public PendingRequestAdapter(List<TutorRequest> requests) {
        this.requests = requests;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PendingRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create a new view
        View requestView = inflater.inflate(R.layout.pending_request, parent, false);

        ViewHolder viewHolder = new ViewHolder(requestView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PendingRequestAdapter.ViewHolder viewholder, int position) {

        TutorRequest tr = requests.get(position);

        //Set Item Views
        TextView tutee_name = viewholder.tutee_name;
        TextView class_name = viewholder.class_name;
        tutee_name.setText(tr.getTuteeName());
        class_name.setText(tr.getClassName() + " " + intToTime(Integer.parseInt(tr.getTime())));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }

    public String intToTime(int i) {
        String temp = "";
        if (i < 9) {
            temp += "Monday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        } else if (i < 18) {
            temp += "Tuesday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        } else if (i < 27) {
            temp += "Wednesday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        } else if (i < 36) {
            temp += "Thursday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        } else if (i < 45) {
            temp += "Friday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        } else if (i < 54) {
            temp += "Saturday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        } else {
            temp += "Sunday: ";
            temp += Integer.toString(i % 9 + 8);
            temp += ":00";
        }
        return temp;
    }
}
