package wzmame.pictour.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import wzmame.pictour.R;
import wzmame.pictour.fragment.NewLocationFragment;
import wzmame.pictour.model.Location;

public class NewLocation extends AppCompatActivity
        implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            LocationListener {

    private static final long LOCATION_REQUEST_INTERVAL = 2000;
    private static final long LOCATION_REQUEST_FASTEST_INTERVAL = 1000;

    private Location location;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        googleApiClient = buildGoogleApiClient();

        String tourId = getIntent().getStringExtra("tourId");

        location = new Location();
        location.setTourId(tourId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewLocationFragment();

            FragmentTransaction tx = fragmentManager.beginTransaction();
            tx.add(R.id.fragmentContainer, fragment);
            tx.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        googleApiClient.disconnect();

        super.onStop();
    }

    public Location getLocation() {
        return location;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    protected synchronized GoogleApiClient buildGoogleApiClient() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        return googleApiClient;
    }

    private LocationRequest buildLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    public void startLocationUpdates() {
        LocationRequest locationRequest = buildLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        android.location.Location geoLocation = LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient());
        if (geoLocation != null) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("DEBUG", "google api client connection failed: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onLocationChanged(android.location.Location geoLocation) {
        location.setLongitude(geoLocation.getLongitude());
        location.setLatitude(geoLocation.getLatitude());
    }
}
