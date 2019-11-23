package com.example.tutorsearcherandroid;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class ViewRequestsTest extends TestCase {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    //https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/

    @Test
    public void BasicTest() {
        ActivityTestRule<ViewRequests> activity =
                new IntentsTestRule<>(ViewRequests.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withRecyclerView(R.id.recyclerView).atPosition(0))
                .check(matches(hasDescendant(withText("TuteeName1"))))
                .check(matches(hasDescendant(withText("Monday: 12:00 pm - 1:00 pm"))))
                .check(matches(hasDescendant(withText("CSCI 103"))))
                .check(matches(hasDescendant(withText("Accept"))))
                .check(matches(hasDescendant(withText("Reject"))));
        onView(withRecyclerView(R.id.recyclerView).atPosition(1))
                .check(matches(hasDescendant(withText("TuteeName2"))))
                .check(matches(hasDescendant(withText("Tuesday: 10:00 am - 11:00 am"))))
                .check(matches(hasDescendant(withText("CSCI 102"))))
                .check(matches(hasDescendant(withText("Accept"))))
                .check(matches(hasDescendant(withText("Reject"))));
        Intents.release();
    }

    @Test
    public void AcceptTest() {
        ActivityTestRule<ViewRequests> activity =
                new IntentsTestRule<>(ViewRequests.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withRecyclerView(R.id.recyclerView)
                .atPositionOnView(0, R.id.accept_button))
                .perform(click());

        intended(hasComponent(ViewRequestsAccepted.class.getName()));

        Intents.release();
    }

    @Test
    public void RejectTest() {
        ActivityTestRule<ViewRequests> activity =
                new IntentsTestRule<>(ViewRequests.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutor");


        activity.launchActivity(intent);

        activity.getActivity().UserId = "2";
        onView(withRecyclerView(R.id.recyclerView)
                .atPositionOnView(1, R.id.reject_button))
                .perform(click());

        onView(withRecyclerView(R.id.recyclerView).atPosition(0))
                .check(matches(hasDescendant(withText("TuteeName1"))))
                .check(matches(hasDescendant(withText("Monday: 12:00 pm - 1:00 pm"))))
                .check(matches(hasDescendant(withText("CSCI 103"))))
                .check(matches(hasDescendant(withText("Accept"))))
                .check(matches(hasDescendant(withText("Reject"))));

        RecyclerView recyclerView = activity.getActivity().findViewById(R.id.recyclerView);
        int itemCount = recyclerView.getAdapter().getItemCount();
        assertEquals(1, itemCount);

        activity.getActivity().UserId = "3";
        onView(withRecyclerView(R.id.recyclerView)
                .atPositionOnView(0, R.id.reject_button))
                .perform(click());
        onView(withId(R.id.noRequestsFound)).check(matches(not(isDisplayed())));

        Intents.release();
    }

}
