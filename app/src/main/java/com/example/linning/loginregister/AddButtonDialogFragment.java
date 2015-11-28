package com.example.linning.loginregister;


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
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by shivanimall on 11/14/15.
 */
public class AddButtonDialogFragment extends DialogFragment {

    private Context context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        this.context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        builder.setView(view);
        Double lat = getArguments().getDouble("markerLat");
        Double longt = getArguments().getDouble("markerLong");
        final String latString = lat.toString();
        final String longString = longt.toString();

        /*builder.setItems(R.id.button_startTime, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int choosenAddress) {
                final FragmentManager manager = getFragmentManager();
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(manager, "timePicker");
            })

*/


        builder.setTitle("ENTER SELLING INFO")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //user adds the ok button
                        EditText name = (EditText) view.findViewById(R.id.editText_name);
                        String theName = name.getText().toString();
                        String phone = ((EditText) view.findViewById(R.id.editText_phone)).getText().toString();
                        String rate = ((EditText)view.findViewById(R.id.editText_rate)).getText().toString();
                        String startDateTime = ((EditText)view.findViewById(R.id.pick_start_date)).getText().toString() + " " + ((EditText) view.findViewById(R.id.pick_start_time)).getText().toString();
                        String endDateTime = ((EditText)view.findViewById(R.id.pick_end_date)).getText().toString() + " " + ((EditText)view.findViewById(R.id.pick_end_time)).getText().toString();
                        String address = ((EditText)getActivity().findViewById(R.id.TFaddress)).getText().toString();
                        new StoreSellingInfo(context).sendSellingInfo(theName, phone, rate, startDateTime, endDateTime, latString, longString, address);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }


}
