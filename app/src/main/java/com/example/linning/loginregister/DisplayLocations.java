package com.example.linning.loginregister;

import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.AsyncTask;

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

/**
 * Created by linning on 10/24/15.
 */
public class DisplayLocations {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://cse110gogogo.web44.net/";
    public ArrayList<Marker> markers;
    private ArrayList<Double> longitude;
    private ArrayList<Double> latitude;
    private GoogleMap gMap;
    private Marker marker;

    /*
        Constructor for this class;
        Takes the context of the class that calls this class and the GoogleMap to be modified.
     */
    public DisplayLocations(Context context, GoogleMap map) {
        latitude = new ArrayList<Double>();
        longitude = new ArrayList<Double>();
        markers = new ArrayList<Marker>();
        this.gMap = map;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing...");
        progressDialog.setMessage("Please wait...");
    }

    /*
        Performs the async task and shows the dialog
     */
    public void fetchUserDataAsyncTask() {
        progressDialog.show();
        new FetchMarkersAsyncTask().execute();
    }

    /*
        Async Task to retrieve marker information from database
     */
    public class FetchMarkersAsyncTask extends AsyncTask<Void, Void, String> {

        public FetchMarkersAsyncTask() {}

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
            JSONArray jArray = null;
            try {
                jArray = new JSONArray(result);
                if (jArray.length() != 0) {
                    for(int i = 0; i < jArray.length(); i++) {
                        latitude.add(Double.parseDouble(jArray.getJSONObject(i).getString("latitude")));
                        longitude.add(Double.parseDouble(jArray.getJSONObject(i).getString("longitude")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            this.displayLocation(gMap);
            progressDialog.cancel();
        }

        protected void displayLocation(GoogleMap map){

            for(int i = 0; i < latitude.size(); i++ ) {
                double lat = latitude.get(i);
                double longi = longitude.get(i);
                LatLng locationparking = new LatLng(lat, longi);
                marker = map.addMarker(new MarkerOptions().position(locationparking).title("Marker for Parking Lot"));
                markers.add(marker);
            }
        }

    }

    /*
        Checks if marker location is identical to any marker in the list of locations in the database
     */
    public boolean isLocationStored(ArrayList<Marker> markers, Marker mark) {
        for(int i = 0; i < markers.size() - 1; i++) {
            if(markers.get(i).getPosition().latitude == mark.getPosition().latitude &&
                    markers.get(i).getPosition().longitude == mark.getPosition().longitude)
              return true;
        }
    return false;
    }
}