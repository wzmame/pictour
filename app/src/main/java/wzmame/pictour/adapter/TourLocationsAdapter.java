package wzmame.pictour.adapter;

import android.content.Context;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import wzmame.pictour.model.Location;

public class TourLocationsAdapter extends ParseQueryAdapter<Location> {
    public TourLocationsAdapter(Context context, final String tourId) {
        super(context, new ParseQueryAdapter.QueryFactory<Location>() {

            @Override
            public ParseQuery<Location> create() {
                ParseQuery<Location> query = new ParseQuery<>(Location.class);
                query.whereEqualTo("tourId", tourId);
                query.orderByAscending("createdAt");
                return query;
            }
        });
    }
}
