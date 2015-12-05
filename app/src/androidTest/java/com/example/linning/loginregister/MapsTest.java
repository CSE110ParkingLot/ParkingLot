package com.example.linning.loginregister;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
/**
 * Created by Raymond on 12/4/2015.
 */
public class MapsTest extends ActivityInstrumentationTestCase2{

    public MapsTest()
    {
        super(MapsActivity.class);
    }

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        getActivity();
    }


    /* Given that the user is logged in. (We test this in LoginTest.java) */

    public void testSearchSuccess()
    {

        /* when they searches an address */
        onView(withId(R.id.TFaddress)).perform(typeText("Scripps Ranch"), closeSoftKeyboard());
        onView(withId(R.id.Bsearch)).perform(click());

        /* Then the map will focus on the address */
        onView(withId(R.id.addButton)).check(matches(isDisplayed()));

        /* When the user clicks the sell it button */

        onView(withId(R.id.addButton)).perform(click());

         /* Then they should see a dialog pop up and be able to input information */
        onView(withId(R.id.textView_phone)).check(matches(withText("Phone")));
        onView(withId(R.id.textView_rate)).check(matches(withText("Rate")));
        onView(withId(R.id.textView_name)).check(matches(withText("Name")));
    }

}