package com.example.linning.loginregister;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by shivanimall on 11/14/15.
 */
public class AddButtonDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Context context;
    public View inflatedView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        inflatedView = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(inflatedView);

        /*builder.setItems(R.id.button_startTime, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int choosenAddress) {
                final FragmentManager manager = getFragmentManager();
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(manager, "timePicker");
            })

*/
        final DialogFragment timePickerFragmentStart = new TimePickerFragment();
        final DialogFragment timePickerFragmentEnd = new TimePickerFragment();
        final DialogFragment dateFragmentStart = new DatePickerFragment();
        final DialogFragment dateFragmentEnd = new DatePickerFragment();
        final FragmentManager manager = getFragmentManager();

        final Button startDate = (Button) inflatedView.findViewById(R.id.button_startDate);
        final Button endDate = (Button) inflatedView.findViewById(R.id.button_endDate);
        final Button startTime = (Button) inflatedView.findViewById(R.id.button_startTime);
        final Button endTime = (Button) inflatedView.findViewById(R.id.button_endTime);

        if(startDate != null) {
            startDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateFragmentStart.show(manager, "datePickerStart");
                }
            });
        }

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFragmentEnd.show(manager, "datePickerEnd");
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragmentStart.show(manager, "timePickerStart");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerFragmentEnd.show(manager, "timePickerEnd");
            }
        });



        builder.setTitle(R.string.title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //user adds the ok button
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        if(this.getTag().equals("datePickerStart"))
        {
            Button dateStart = (Button) getActivity().findViewById(R.id.button_startDate);
            String theDate = month + "/" + day + "/" + year;
            dateStart.setText(theDate);
        }
        else
        {
            Button dateEnd = (Button) getActivity().findViewById(R.id.button_endDate);
            String theDate = month + "/" + day + "/" + year;
            dateEnd.setText(theDate);
        }
    }

    private void showDatePicker(boolean isStart)
    {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerFragment datePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        datePicker.setArguments(bundle);

        datePicker.setCallBack();

        DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                edittext.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                        + "-" + String.valueOf(year));
            }
        };

    }
}
