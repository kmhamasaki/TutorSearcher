package com.example.tutorsearcherandroid;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tutor.searcher.TutorSearcher.Tutor;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Tutor> tutorResults;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameTextView;
        public TextView availabilityTextView;
        //TODO: Add more profile information

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            availabilityTextView = (TextView) itemView.findViewById(R.id.availability);
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

        ViewHolder viewHolder = new ViewHolder(tutorView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewholder, int position) {

        Tutor tutor = tutorResults.get(position);

        //Set Item Views
        TextView textView = viewholder.nameTextView;
        String tutorName = "Tutor: " + tutor.getFirstName();
        textView.setText(tutorName);

        textView = viewholder.availabilityTextView;
        List<Integer> avail = tutor.getTimeAvailabilities();
        //Display user times
        String temp = "Availability:\n";
        Boolean mon = true, tu = true, wed = true, thu = true,
                f = true, sat = true, sun = true;
        for(int i = 0; i < avail.size(); i++){
            int time = avail.get(i);
            //Monday
            if(time < 9){
                if(mon){
                    temp += "\nMonday: ";
                    mon = false;
                }
                temp += " " + time;
            }
            //Tuesday
            else if(time < 17){
                if(tu){
                    temp += "\nTuesday: ";
                    tu = false;
                }
                temp += " " + time;
            }
            //Wednesday
            else if(time < 25){
                if(wed){
                    temp += "\nWednesday: ";
                    wed = false;
                }
                temp += " " + time;
            }
            //Thursday
            else if(time < 33){
                if(thu){
                    temp += "\nThursday: ";
                    thu = false;
                }
                temp += " " + time;
            }
            //Friday
            else if(time < 41){
                if(f){
                    temp += "\nFriday: ";
                    f = false;
                }
                temp += " " + time;
            }
            //Saturday
            else if(time < 49){
                if(sat){
                    temp += "\nSaturday: ";
                    sat = false;
                }
                temp += " " + time;
            }
            //Sunday
            else{
                if(sun){
                    temp += "\nSunday: ";
                    sun = false;
                }
                temp += " " + time;
            }
        }
        textView.setText(temp);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tutorResults.size();
    }
}
