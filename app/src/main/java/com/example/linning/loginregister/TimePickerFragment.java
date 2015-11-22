package com.example.linning.loginregister;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by shivanimall on 11/21/15.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public int hour;
    public int minute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
//        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), this, hour, minute,
//                DateFormat.is24HourFormat(getActivity()));
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //******USE THIS TO SEND INFO TO THE DATABASE
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(this.getTag().equals("timePickerStart"))
        {
            Button startTime = (Button) getActivity().findViewById(R.id.button_startTime);
            String theTime = hourOfDay + ":" + minute + ":00";
            startTime.setText(theTime);
        }
        else
        {
            Button endTime = (Button) getActivity().findViewById(R.id.button_endTime);
            String theTime = hourOfDay + ":" + minute + ":00";
            endTime.setText(theTime);
        }
        this.hour = hourOfDay;
        this.minute = minute;
    }
}