package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.util.ArrayList;

import wzmame.pictour.R;
import wzmame.pictour.config.ParseConfig;
import wzmame.pictour.model.Location;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeParse();
        Button newTourBtn = (Button) findViewById(R.id.btnNewTour);
        ListView lvHome = (ListView) findViewById(R.id.lvHome);
        String[] listItems = {"Seattle", "Portland", "Chicago", "New York"};
        ArrayAdapter<String> sampleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        lvHome.setAdapter(sampleAdapter);
        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Home.this, TourView.class);
                startActivity(i);
            }
        });

        newTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), NewTour.class);
                startActivity(i);
            }
        });
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

        ParseObject.registerSubclass(Location.class);
    }

}
