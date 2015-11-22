package com.example.linning.loginregister;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationProvider.LocationCallback{
    public static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private LocationProvider mLocationProvider;
    public DisplayLocations displayLocations;
    private Button addButton;

    private Marker newMarker;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final Bundle savedInstanceStateFinal = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setVisibility(View.GONE);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DialogFragment addButtonDialog = new AddButtonDialogFragment();
                addButtonDialog.show(manager, "gh");
                //Dialog dialog = addButtonDialog.onCreateDialog(savedInstanceStateFinal);
                //dialog.show();
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(int i = 0; i < displayLocations.markers.size(); i++) {
                    if (displayLocations.markers.get(i).equals(marker)) {
                        FragmentManager manager = getFragmentManager();
                        DialogFragment markerDialog = new MarkerDialogFragment();
                        markerDialog.show(manager, "markers");
                        return true;
                    }
                }
                return false;
            }
        });
        setUpMapIfNeeded();
        mLocationProvider = new LocationProvider(this, this);
    }


    //onDialogNegativeClick
      //      onDialogNegativeClick

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mLocationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    public void onSearch(View view)
    {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;
        if(location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location , 1);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude() , address.getLongitude());
            newMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(location));
           // if(displayLocations.isLocationStored(displayLocations.markers, newMarker)) {
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (newMarker.equals(marker)) {
                            FragmentManager manager = getFragmentManager();
                            DialogFragment markerDialog = new MarkerDialogFragment();
                            markerDialog.show(manager, "marker");
                            return true;
                        }
                        return false;
                    }
                });
            //}
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            if(!displayLocations.isLocationStored(displayLocations.markers, newMarker))
                 addButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        //MarkerOptions options = new MarkerOptions()
        //  .position(latLng)
        //   .title("I am here!");
        // mMap.addMarker(options);
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        displayLocations = new DisplayLocations(this, mMap);
        displayLocations.fetchUserDataAsyncTask();

    }


}