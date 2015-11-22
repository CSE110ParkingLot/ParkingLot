package com.example.linning.loginregister;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.linning.loginregister.R;

/**
 * Created by Alix on 11/21/2015.
 */
public class MarkerDialogFragment extends DialogFragment {

    private Context context;
//    public AddButtonDialogFragment(Context theContext)
//    {
//        this.context = theContext;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        String reserve = "Reserve It!";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.marker_layout, null));

        builder.setTitle(R.string.title)
                .setPositiveButton(reserve, new DialogInterface.OnClickListener() {
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

