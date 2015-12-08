package wzmame.pictour.fragment;

import wzmame.pictour.model.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import wzmame.pictour.R;

/**
 * Created by xmeng on 11/25/15.
 */
public class TourMapViewFragment extends SupportMapFragment {
    private GoogleMap mMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap  = super.getMap();
        ParseQuery<Location> locationListQuery = ParseQuery.getQuery(Location.class);
        locationListQuery.whereEqualTo("tourId", getActivity().getIntent().getStringExtra("tourId"));
        Log.i("Tour Id", getActivity().getIntent().getStringExtra("tourId"));

        locationListQuery.findInBackground(new FindCallback<Location>() {

            @Override
            public void done(List<Location> locations, ParseException e) {
                if (e == null) {
                    for (Location l : locations) {
                        Toast.makeText(getContext(), "lat:" + l.getLat(), Toast.LENGTH_LONG).show();
                        Log.i("Lat", l.getLat() + "");
                        Log.i("lng", l.getLng() + "");
                        LatLng geoLocation = new LatLng(l.getLat().doubleValue(), l.getLng().doubleValue());
                        mMap.addMarker(new MarkerOptions().position(geoLocation).title(l.getName()));
                    }
                }
            }
        });

    }
}
