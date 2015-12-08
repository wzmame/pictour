package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import wzmame.pictour.R;
import wzmame.pictour.adapter.HomeViewAdapter;
import wzmame.pictour.config.ParseConfig;
import wzmame.pictour.model.Location;
import wzmame.pictour.model.Tour;

public class Home extends AppCompatActivity {

    public final static int REQUEST_NEW_TOUR = 101;

    private ParseQueryAdapter<Tour> aTours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeParse();

        aTours = new HomeViewAdapter(this);
        aTours.setTextKey("name");

        ListView lvTours = (ListView) findViewById(R.id.lvTours);
        lvTours.setAdapter(aTours);
        lvTours.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewTourIntent = new Intent(Home.this, TourView.class);
                String tourId = aTours.getItem(position).getObjectId();
                viewTourIntent.putExtra("tourId", tourId);
                startActivity(viewTourIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_NEW_TOUR:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(this, "Couldn't make new tour", Toast.LENGTH_LONG).show();
                    return;
                }

                aTours.loadObjects();

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public void onTestAreaAction(MenuItem item) {
        Intent testAreaIntent = new Intent(this, TestArea.class);
        startActivity(testAreaIntent);
    }

    private void initializeParse() {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ParseConfig.APPLICATION_ID, ParseConfig.CLIENT_ID);

        ParseObject.registerSubclass(Tour.class);
        ParseObject.registerSubclass(Location.class);
    }

    public void onNewTourAction(View view) {
        Intent newTourIntent = new Intent(this, NewTour.class);
        startActivityForResult(newTourIntent, REQUEST_NEW_TOUR);
    }
}
