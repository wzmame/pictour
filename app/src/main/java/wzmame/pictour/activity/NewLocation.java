package wzmame.pictour.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import wzmame.pictour.R;
import wzmame.pictour.fragment.NewLocationFragment;
import wzmame.pictour.model.Location;

public class NewLocation extends AppCompatActivity {

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        String tourId = getIntent().getStringExtra("tourId");

        location = new Location();
        location.setTourId(tourId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new NewLocationFragment();

            FragmentTransaction tx = fragmentManager.beginTransaction();
            tx.add(R.id.fragmentContainer, fragment);
            tx.commit();
        }
    }

    public Location getLocation() {
        return location;
    }
}
