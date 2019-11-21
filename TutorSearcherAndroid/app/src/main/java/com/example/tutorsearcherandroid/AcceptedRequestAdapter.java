package com.example.tutorsearcherandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tutor.searcher.TutorSearcher.AcceptedTutorRequest;


public class AcceptedRequestAdapter extends RecyclerView.Adapter<AcceptedRequestAdapter.ViewHolder> {

    private List<AcceptedTutorRequest> requests;
    private String accountType;
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
        public TextView email;
        public TextView phone_number;
        public TextView time;

        public Button accept_button;
        public Button reject_button;


        public ViewHolder(View itemView, final OnButtonClickListener listener) {
            super(itemView);
            tutee_name = itemView.findViewById(R.id.tutee_name);
            class_name = itemView.findViewById(R.id.class_name);
            email = itemView.findViewById(R.id.email);
            phone_number = itemView.findViewById(R.id.phone_number);
            time = itemView.findViewById(R.id.time);

        }
    }

    // Constructor
    public AcceptedRequestAdapter(List<AcceptedTutorRequest> requests, String accountType) {
        this.requests = requests;
        this.accountType = accountType;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AcceptedRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create a new view
        View requestView = inflater.inflate(R.layout.accepted_request, parent, false);

        ViewHolder viewHolder = new ViewHolder(requestView, mListener);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AcceptedRequestAdapter.ViewHolder viewholder, int position) {

        AcceptedTutorRequest tr = requests.get(position);

        //Set Item Views
        TextView tutee_name = viewholder.tutee_name;
        TextView class_name = viewholder.class_name;
        TextView time = viewholder.time;
        TextView email = viewholder.email;
        TextView phone_number = viewholder.phone_number;

        tutee_name.setText(tr.getName());
        class_name.setText(tr.getClassName());
        email.setText(tr.getEmail());
        phone_number.setText(tr.getPhoneNumber());
        time.setText(tr.getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }
}
