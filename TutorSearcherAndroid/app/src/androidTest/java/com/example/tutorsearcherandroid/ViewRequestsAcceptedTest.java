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
public class ViewRequestsAcceptedTest extends TestCase {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void BasicTest() {
        ActivityTestRule<ViewRequestsAccepted> activity =
                new IntentsTestRule<>(ViewRequestsAccepted.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withRecyclerView(R.id.recyclerView).atPosition(0))
                .check(matches(hasDescendant(withText("John"))))
                .check(matches(hasDescendant(withText("Monday: 12:00 pm - 1:00 pm"))))
                .check(matches(hasDescendant(withText("CSCI 103"))))
                .check(matches(hasDescendant(withText("12344567890"))))
                .check(matches(hasDescendant(withText("trojan@usc.edu"))));
        onView(withRecyclerView(R.id.recyclerView).atPosition(1))
                .check(matches(hasDescendant(withText("Jill"))))
                .check(matches(hasDescendant(withText("Tuesday: 10:00 am - 11:00 am"))))
                .check(matches(hasDescendant(withText("CSCI 102"))))
                .check(matches(hasDescendant(withText("0987654321"))))
                .check(matches(hasDescendant(withText("jill@usc.edu"))));
        Intents.release();
    }

    @Test
    public void ClickHomeTest() {
        ActivityTestRule<ViewRequestsAccepted> activity =
                new IntentsTestRule<>(ViewRequestsAccepted.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);
        onView(withId(R.id.Home)).perform(click());
        intended(hasComponent(ScrollingHomeActivity.class.getName()));
        Intents.release();
    }

}
