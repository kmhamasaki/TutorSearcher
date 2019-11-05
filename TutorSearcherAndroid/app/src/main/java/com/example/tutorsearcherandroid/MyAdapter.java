package com.example.tutorsearcherandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tutor.searcher.TutorSearcher.Tutor;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Tutor> tutorResults;
    private OnTutorClickListener mListener;

    public interface OnTutorClickListener{
        void onTutorClick(int position);
    }

    public void setOnTutorClickListener(OnTutorClickListener listener){
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameTextView;
        public RatingBar ratingBar;
        //TODO: Add more profile information

        public ViewHolder(View itemView, final OnTutorClickListener listener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.time);
            ratingBar = itemView.findViewById(R.id.tutor_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onTutorClick(position);
                        }
                    }
                }
            });
        }
    }

    // Constructor
    public MyAdapter(List<Tutor> tutors) {
        tutorResults = tutors;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create a new view
        View tutorView = inflater.inflate(R.layout.tutor_profile, parent, false);

        ViewHolder viewHolder = new ViewHolder(tutorView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewholder, int position) {

        Tutor tutor = tutorResults.get(position);

        //Set Item Views
        TextView textView = viewholder.nameTextView;
        RatingBar ratingBar = viewholder.ratingBar;
        String tutorName = tutor.getFirstName();
        textView.setText(tutorName);
        float rating = (float)0.0; // add code to get rating here
        ratingBar.setRating(rating);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tutorResults.size();
    }
}
