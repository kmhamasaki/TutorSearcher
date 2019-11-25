package com.example.tutorsearcherandroid.ui.main;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tutorsearcherandroid.R;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.monday_short, R.string.tuesday_short, R.string.wednesday_short, R.string.thursday_short, R.string.friday_short, R.string.saturday_short, R.string.sunday_short};
    private final Context mContext;
    private ArrayList<Integer> savedTimes;

    public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<Integer> savedTimes) {
        super(fm);
        mContext = context;
        this.savedTimes = savedTimes;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        ArrayList<Integer> dateData = new ArrayList<>();
        for(int i=0;i<savedTimes.size(); i++){
            if(savedTimes.get(i)/8==position){
                dateData.add(i%8);
            }
        }
        return DateFragment.newInstance(dateData);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}