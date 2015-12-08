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
import com.google.android.gms.maps.model.PolylineOptions;
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
    public void onResume() {
        super.onResume();
        mMap  = super.getMap();
        ParseQuery<Location> locationListQuery = ParseQuery.getQuery(Location.class);
        locationListQuery.whereEqualTo("tourId", getActivity().getIntent().getStringExtra("tourId"));
        locationListQuery.addAscendingOrder("createdAt");
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
        PolylineOptions route = new PolylineOptions();

        for (Location l : locations) {
            LatLng geoLocation = new LatLng(l.getLatitude().doubleValue(), l.getLongitude().doubleValue());
            boundBuilder.include(geoLocation);
            route.add(geoLocation);
            mMap.addMarker(new MarkerOptions().position(geoLocation).title(l.getName()));
        }
        mMap.addPolyline(route);
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(boundBuilder.build(), MAP_PADDING);
        mMap.moveCamera(cu);

    }
}
