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

/**
 * Created by shivanimall on 11/14/15.
 */
public class AddButtonDialogFragment extends DialogFragment {

    private Context context;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_layout, null));

        /*builder.setItems(R.id.button_startTime, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int choosenAddress) {
                final FragmentManager manager = getFragmentManager();
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(manager, "timePicker");
            })

*/
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


}
