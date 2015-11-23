package com.example.linning.loginregister;

/**
 * Created by Alix on 11/22/2015.
 */

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RetrieveSpaceInfo {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://cse110gogogo.web44.net/";
   // public ArrayList<Marker> markers;
    private double longitude;
    private double latitude;
    private String name;
    private int phone;
    private double rate;
    private String address;
    private DialogFragment fragment;
    private int spaceId;

    private String startDateTime;
    private String endDateTime;

    private DialogFragment dialog;
    private Marker marker;

    public RetrieveSpaceInfo(Context context, DialogFragment dialog) {

        this.dialog = dialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    public void fetchUserDataAsyncTask() {
        progressDialog.show();
        new FetchUserDataAsyncTask().execute();
    }

    public class FetchUserDataAsyncTask extends AsyncTask<String, Void, String> {

        public FetchUserDataAsyncTask() {
        }

        private HttpParams getHttpRequestParams() {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,
                    CONNECTION_TIMEOUT);
            return httpRequestParams;
        }
        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("latitude", params[0]));
            dataToSend.add(new BasicNameValuePair("longitude", params[1]));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchSpaceInfo.php");

            User returnedUser = null;

            String result = null;

            try {
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(result);
                latitude = Double.parseDouble(jArray.getJSONObject(0).getString("latitude"));
                longitude = Double.parseDouble(jArray.getJSONObject(0).getString("longitude"));
                name = jArray.getJSONObject(0).getString("name");
                phone = Integer.parseInt(jArray.getJSONObject(0).getString("phone"));
                rate = Double.parseDouble(jArray.getJSONObject(0).getString("rate"));
                startDateTime = jArray.getJSONObject(0).getString("startDateTime");
                endDateTime = jArray.getJSONObject(0).getString("endDateTime");
                address = jArray.getJSONObject(0).getString("address");
                spaceId = Integer.parseInt(jArray.getJSONObject(0).getString("space_id"));

            }
            catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            displayBuyInfo(fragment);
            progressDialog.cancel();
        }

    }

    protected void displayBuyInfo(DialogFragment fragment) {
        View view = fragment.getView();
        TextView setAddress = (TextView) view.findViewById(R.id.address);
        setAddress.setText(address);

        TextView setPhone = (TextView) view.findViewById(R.id.phone);
        setPhone.setText(phone);

        TextView setName = (TextView) view.findViewById(R.id.name);
        setName.setText(name);

        TextView setStartDateTime = (TextView) view.findViewById(R.id.startDateTime);
        setStartDateTime.setText(startDateTime);

        TextView setEndDateTime = (TextView) view.findViewById(R.id.endDateTime);
        setEndDateTime.setText(endDateTime);

        String rateString = Double.toString(rate);
        TextView setRate = (TextView) view.findViewById(R.id.rate);
        setRate.setText(rateString);
    }

    protected int getSpaceId() {
        return spaceId;
    }
}