package wzmame.pictour.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import org.w3c.dom.Text;

import wzmame.pictour.R;
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

    @Override
    public View getItemView(Location l, View v, ViewGroup parent) {
        if (v == null) {
            v = (View) View.inflate(getContext(), R.layout.layout_image_list_item, null);
        }
        super.getItemView(l, v, parent);

        ParseFile locationImage = l.getPicture();
        ParseImageView ivLocationImage = (ParseImageView) v.findViewById(R.id.ivLocationImage);
        TextView tvLocationTitle = (TextView) v.findViewById(R.id.tvLocationTitle);
        if (locationImage != null) {
            ivLocationImage.setParseFile(locationImage);
            ivLocationImage.loadInBackground();
        }
        tvLocationTitle.setText(l.getName());
        return v;
    }
}
