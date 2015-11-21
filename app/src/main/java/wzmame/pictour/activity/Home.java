package wzmame.pictour.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseObject;

import wzmame.pictour.R;
import wzmame.pictour.config.ParseConfig;
import wzmame.pictour.model.Location;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeParse();
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
