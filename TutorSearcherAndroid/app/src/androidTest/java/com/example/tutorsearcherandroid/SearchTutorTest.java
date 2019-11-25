package com.example.tutorsearcherandroid;

import android.content.Intent;

//import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.hamcrest.core.AllOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SearchTutorTest extends TestCase {

    @Test
    public void SuccessRequestTutorTest(){
        ActivityTestRule<SearchResultsActivity> activity =
                new IntentsTestRule<>(SearchResultsActivity.class, true, true);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType","Tutee");
        HashMap<String, Object> attr = new HashMap<>();

        List<Tutor> results = new ArrayList<>();
        Tutor t1 = new Tutor(1, "John", "Trojan",
                "successTutor@usc.edu", "12344567890", "password",
                true,"0 1 2 3 4", 1.0);

        t1.setMatchingAvailabilities((ArrayList<Integer>) t1.getTimeAvailabilities());
        results.add(t1);
        Tutor t2 = new Tutor(1, "Jane", "Doe",
                "successTutor@usc.edu", "12344567890", "password",
                true,"0 1 2 3 4", 1.0);
        t2.setMatchingAvailabilities((ArrayList<Integer>) t2.getTimeAvailabilities());
        results.add(t2);
        attr.put("results",results);
        Request response = new Request("results", attr);

        intent.putExtra("TutorList", response);
        intent.putExtra("ClassName", "CSCI 103");

        activity.launchActivity(intent);

        //Select Tutor
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Select Time
        intended(hasComponent(TutorTimeActivity.class.getName()));
        onView(AllOf.allOf(withText("Monday: 9:00 am - 10:00 am"),
                withParent(withId(R.id.radio_group)))).perform(click());

        onView(withId(R.id.sendRequest)).perform(click());

        intended(hasComponent(ScrollingHomeActivity.class.getName()));

        Intents.release();
    }
}
