package com.example.linning.loginregister;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.linning.loginregister.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Alix on 11/21/2015.
 */
public class MarkerDialogFragment extends DialogFragment {
    public static final String SERVER_ADDRESS = "http://cse110gogogo.web44.net/";

    private Context context;
    private RetrieveSpaceInfo spaceInfo;
//    public AddButtonDialogFragment(Context theContext)
//    {
//        this.context = theContext;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        String reserve = "Reserve It!";
        context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.marker_layout, null));
        spaceInfo = new RetrieveSpaceInfo(context, this );
        spaceInfo.displayBuyInfo(this);


        builder.setTitle("Reserve This Spot!")
                .setPositiveButton(reserve, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //user adds the ok button
                        reserveSpace(spaceInfo.getSpaceId());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
    protected void reserveSpace(final int space_id) {

        class ReserveSpaceAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                ArrayList<NameValuePair> dataToSend = new ArrayList<>();
                dataToSend.add(new BasicNameValuePair("space_id", Integer.toString(space_id)));

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(SERVER_ADDRESS + "ReserveParkingSpace.php");

                try {
                    post.setEntity(new UrlEncodedFormEntity(dataToSend));
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                CharSequence text = "Successfully reserved.";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();
            }

        }
        ReserveSpaceAsyncTask reserveSpaceAsyncTask = new ReserveSpaceAsyncTask();
        reserveSpaceAsyncTask.execute();
    }

}

