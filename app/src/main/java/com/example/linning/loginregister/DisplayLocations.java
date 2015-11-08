package com.example.linning.loginregister;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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

/**
 * Created by linning on 10/24/15.
 */
public class DisplayLocations {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://cse110gogogo.web44.net/";
    JSONArray longitude;
    JSONArray latitude;

    public DisplayLocations(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }


    public void fetchUserDataAsyncTask() {
        progressDialog.show();
        new fetchUserDataAsyncTask().execute();
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, String> {

        public fetchUserDataAsyncTask() {}

        @Override
        protected String doInBackground(Void... params) {

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "GetMarkers.php");

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
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(result);
                if (jObject.length() != 0) {
                    Log.v("happened", "2");
                    latitude = jObject.getJSONArray("latitude");
                    longitude = jObject.getJSONArray("longitude");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }



    }


    protected void displayLocation(GoogleMap map){

        for(int i = 0; i < latitude.length(); i++ )
            try {
                double lat = (double) latitude.get(i);
                double longi = (double) longitude.get(i);
                LatLng locationparking = new LatLng(lat, longi);
                map.addMarker(new MarkerOptions().position(locationparking).title("Marker for Parking Lot"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }


}
