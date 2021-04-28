package com.example.mareu.ui;

import android.inputmethodservice.Keyboard;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.mareu.R;
import com.example.mareu.ui.utils.MyViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MareuInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> scenario = rule.getScenario();
    }

    public void meetingList_createAction_FilterTestMeeting() {
        onView(ViewMatchers.withId(R.id.activity_main));
        onView(ViewMatchers.withId(R.id.addMeeting)).perform(click());
        onView(withId(R.id.textViewRoom)).perform(click());
        onView(withText("Réunion 3")).perform(click());
        onView(withId(R.id.editTextTime)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.editTextDate)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.topicLyt)).perform(typeText("Mario"));
        onView(withId(R.id.topicLyt)).perform(closeSoftKeyboard());
        onView(withId(R.id.textViewGuests)).perform(click());
        onView(withText("alexandra@lamzone.com")).perform(click());
        onView(withId(R.id.select_button)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
    }

    @Test
    public void A_meetingList_shouldBeEmpty() {
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(0)));
    }

    @Test
    public void B_meetingList_createAction_shouldCreateAndDisplayItem() {
        onView(ViewMatchers.withId(R.id.activity_main));
        onView(ViewMatchers.withId(R.id.addMeeting)).perform(click());
        onView(withId(R.id.textViewRoom)).perform(click());
        onView(withText("Réunion 1")).perform(click());
        onView(withId(R.id.editTextTime)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 30));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.editTextDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021, 4, 6));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.topicLyt)).perform(typeText("Peach"));
        onView(withId(R.id.topicLyt)).perform(closeSoftKeyboard());
        onView(withId(R.id.textViewGuests)).perform(click());
        onView(withText("francis@lamzone.com")).perform(click());
        onView(withId(R.id.select_button)).perform(click());
        onView(withId(R.id.addButton)).perform(click());
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(1)));
        onView(ViewMatchers.withId(R.id.guests_info)).check(matches(withText("francis@lamzone.com")));
    }

    @Test
    public void C_meetingList_dialogInfo_ShouldShowMeetingInfo() {
        onView(withId(R.id.meetingFragment)).perform(click());
        onView(ViewMatchers.withId(R.id.dRoom)).check(matches(withText("Réunion 1")));
        onView(ViewMatchers.withId(R.id.dDate)).check(matches(withText("6/4/2021 - 12:30")));
        onView(ViewMatchers.withId(R.id.dTopic)).check(matches(withText("Peach")));
        onView(ViewMatchers.withId(R.id.dGuests)).check(matches(withText("francis@lamzone.com")));
        onView(withId(R.id.dClose)).perform(click());
    }

    @Test
    public void D_meetingList_deleteAction_shouldDeleteItem() {
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(1)));
        pressBack();
        onView(withId(R.id.delete_button)).perform(click());
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(0)));
    }

    @Test
    public void E_meetingList_filterMeetings_shouldReturnOnlyFilteredMeeting() {
        B_meetingList_createAction_shouldCreateAndDisplayItem();
        meetingList_createAction_FilterTestMeeting();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Salle")).perform(click());
        onView(withText("Réunion 1")).perform(click());
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(1)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Salle")).perform(click());
        onView(withText("Réunion 2")).perform(click());
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(0)));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Horaire")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021, 4, 6));
        onView(withText("OK")).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 30));
        onView(withText("OK")).perform(click());
        onView(ViewMatchers.withId(R.id.guests_info)).check(matches(withText("francis@lamzone.com")));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Reset")).perform(click());
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(2)));
        onView(withId(R.id.rvMeetings)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.delete_button))).perform(click());
        onView(withId(R.id.rvMeetings)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_button))).perform(click());
        onView(ViewMatchers.withId(R.id.rvMeetings)).check(matches(hasChildCount(0)));
    }
}
