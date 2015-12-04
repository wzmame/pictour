package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQueryAdapter;
import com.parse.SaveCallback;

import wzmame.pictour.R;
import wzmame.pictour.adapter.TourLocationsAdapter;
import wzmame.pictour.model.Tour;

public class NewTour extends AppCompatActivity {

    private static final int REQUEST_NEW_LOCATION = 201;

    private Tour tour;

    private EditText etName;
    private EditText etDescription;

    private ListView lvLocations;

    private Button btnAddLocation;

    private ParseQueryAdapter aLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tour = new Tour();
        tour.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(NewTour.this, "Couldn't create new tour :(", Toast.LENGTH_LONG).show();
                    setResult(RESULT_CANCELED);
                    finish();
                }

                aLocations = new TourLocationsAdapter(NewTour.this, tour.getObjectId());
                aLocations.setTextKey("name");
                aLocations.setImageKey("picture");

                lvLocations.setAdapter(aLocations);

                btnAddLocation.setEnabled(true);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);

        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);

        lvLocations = (ListView) findViewById(R.id.lvLocations);

        btnAddLocation = (Button) findViewById(R.id.btnAddLocation);
        btnAddLocation.setEnabled(false);
    }

    public void onDoneAction(View view) {
        final String name = etName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Tour name is required", Toast.LENGTH_LONG).show();
            return;
        }

        String description = etDescription.getText().toString();

        final Tour tour = getCurrentTour();
        tour.setName(name);
        tour.setDescription(description);
        tour.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_NEW_LOCATION:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this, "Couldn't make new location", Toast.LENGTH_LONG).show();
                    return;
                }

                aLocations.loadObjects();

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onAddLocationAction(View view) {
        final Tour tour = getCurrentTour();

        String name = etName.getText().toString();
        String description = etDescription.getText().toString();

        tour.setName(name);
        tour.setDescription(description);
        tour.saveInBackground();

        Intent addLocationIntent = new Intent(NewTour.this, NewLocation.class);
        addLocationIntent.putExtra("tourId", tour.getObjectId());
        startActivityForResult(addLocationIntent, REQUEST_NEW_LOCATION);
    }

    public Tour getCurrentTour() {
        return tour;
    }
}
