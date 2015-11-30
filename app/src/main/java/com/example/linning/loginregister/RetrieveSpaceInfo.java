package com.example.linning.loginregister;

/**
 * Created by Alix on 11/22/2015.
 */

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.Marker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.widget.TextView;

public class RetrieveSpaceInfo {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://cse110gogogo.web44.net/";
   // public ArrayList<Marker> markers;
    private double longitude;
    private double latitude;
    private DialogFragment fragment;
    private int spaceId;
    private Context context;
    private TextView address;
    private TextView phone;
    private TextView name;
    private TextView startDateTime;
    private TextView endDateTime;
    private TextView rate;
    private DialogFragment dialog;
    private Marker marker;

    public RetrieveSpaceInfo(Context context, TextView add, TextView phoneNum, TextView theName, TextView theStartDateTime, TextView theEndDateTime, TextView theRate) {

        this.context = context;
        this.address = add;
        this.phone = phoneNum;
        this.name = theName;
        this.startDateTime = theStartDateTime;
        this.endDateTime = theEndDateTime;
        this.rate = theRate;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    public void fetchUserDataAsyncTask() {
        progressDialog.show();
        //new FetchMarkerInfo().execute();
    }

    public class FetchMarkerInfo extends AsyncTask<String, Void, String> {
        private Context theContext;
        public FetchMarkerInfo(Context context) {
            theContext = context;
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
            String result = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                result = new BasicResponseHandler().handleResponse(httpResponse);

            } catch (Exception e) {
                e.printStackTrace();
            }
            int i = 0;
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jObj = null;
            try {
                jObj = new JSONObject(result);
                name.setText(jObj.getString("name"));
                phone.setText(jObj.getString("phone"));
                rate.setText(Double.toString(jObj.getDouble("rate")));
                startDateTime.setText(jObj.getString("startDateTime"));
                endDateTime.setText(jObj.getString("endDateTime"));
                address.setText(jObj.getString("address"));
                spaceId = Integer.parseInt(jObj.getString("space_id"));

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.cancel();
        }

    }

    protected void displayBuyInfo(double latitude, double Longitude) {

        String lat = Double.toString(latitude);
        String longi = Double.toString(Longitude);
        new FetchMarkerInfo(context).execute(lat, longi);
        progressDialog.show();

    }

    protected int getSpaceId() {
        return spaceId;
    }
}