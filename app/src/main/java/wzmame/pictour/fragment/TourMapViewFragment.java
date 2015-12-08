package wzmame.pictour.fragment;

import wzmame.pictour.model.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by xmeng on 11/25/15.
 */
public class TourMapViewFragment extends SupportMapFragment {
    private GoogleMap mMap;
    // Offset from the edge of the map in pixel.
    private final static int MAP_PADDING = 10;

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
                    addMapMakers(locations);
                }
            }
        });

    }

    private void addMapMakers(List<Location> locations) {
        LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();
        for (Location l : locations) {
            Toast.makeText(getContext(), "lat:" + l.getLatitude(), Toast.LENGTH_LONG).show();
            LatLng geoLocation = new LatLng(l.getLatitude().doubleValue(), l.getLongitude().doubleValue());
            boundBuilder.include(geoLocation);
            mMap.addMarker(new MarkerOptions().position(geoLocation).title(l.getName()));
        }
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(boundBuilder.build(), MAP_PADDING);
        mMap.moveCamera(cu);

    }
}
