package com.example.tutorsearcherandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        public TextView time;
        public TextView bio;
        public TextView rating;
        public Button accept_button;
        public Button reject_button;


        public ViewHolder(View itemView, final OnButtonClickListener listener) {
            super(itemView);
            tutee_name = itemView.findViewById(R.id.tutee_name);
            class_name = itemView.findViewById(R.id.class_name);
            time = itemView.findViewById(R.id.time);
            bio = itemView.findViewById(R.id.bio);
            rating = itemView.findViewById(R.id.rating);
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
        TextView time = viewholder.time;
        TextView bio = viewholder.bio;
        TextView rating = viewholder.rating;

        tutee_name.setText(tr.getTuteeName());
        class_name.setText(tr.getClassName());
        time.setText(TutorTimeActivity.generateTimesForward().get(Integer.parseInt(tr.getTime())));
        bio.setText((tr.getBio()));
        if(tr.getTuteeRating() == -1) {
            rating.setText("No ratings yet.");
        } else {
            System.out.println(tr.getTuteeRating());
            rating.setText("Rating: " + Client.round(tr.getTuteeRating()));
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }
}
