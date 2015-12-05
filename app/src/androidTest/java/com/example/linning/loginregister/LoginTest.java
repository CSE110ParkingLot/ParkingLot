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

import android.support.test.espresso.Espresso;
/**
 * Created by Joshric on 12/3/2015.
 */


public class LoginTest extends ActivityInstrumentationTestCase2<Login> {

    //Constructor
    public LoginTest(){
        super(Login.class);
    }

    /*
     *  Given that the user is on the Login page and has a valid username and password,
     *  when the user inputs their login credentials and clicks Login,
     *  then the user will be directed to the MapsActivity page.
     */

    /* Given that the user is on the Login page */
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        getActivity();
    }

    // Checks that activity is active
    public void testActivityExists() {
        assertNotNull(getActivity());
    }

    // Checks that Login works properly
    public void testLoginSuccess()
    {
        /* When the user inputs their login credentials and clicks Login */
        onView(withId(R.id.etUsername)).perform(typeText("greg"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("greg"), closeSoftKeyboard());
        onView(withId(R.id.bLogin)).perform(click());



        /* Then the user will be directed to the MapsActivity page. */
        assertNotNull(getActivity());
        onView(withId(R.id.profileButton)).check(matches(withText("Profile!")));
    }

}