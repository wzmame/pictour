package wzmame.pictour.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseQuery;

import java.util.List;

import wzmame.pictour.R;
import wzmame.pictour.model.Location;

public class LocationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        final TextView tvName = (TextView) findViewById(R.id.tvName);

        final TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvDescription.setMaxLines(Integer.MAX_VALUE);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());

        final ParseImageView ivPicture = (ParseImageView) findViewById(R.id.ivPicture);
        ivPicture.setVisibility(View.INVISIBLE);

        String locationId = getIntent().getStringExtra("locationId");

        ParseQuery<Location> query = ParseQuery.getQuery(Location.class);
        query.whereEqualTo("objectId", locationId);
        query.findInBackground(new FindCallback<Location>() {
            @Override
            public void done(List<Location> locations, ParseException e) {
                if (e != null || locations.size() != 1) {
                    Toast.makeText(LocationView.this, "Can't get the details :(", Toast.LENGTH_SHORT).show();
                    return;
                }

                Location location = locations.get(0);

                tvName.setText(location.getName());
                tvDescription.setText(location.getDescription());

                ivPicture.setParseFile(location.getPicture());
                ivPicture.loadInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        ivPicture.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }
}
