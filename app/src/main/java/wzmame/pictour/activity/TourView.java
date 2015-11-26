package wzmame.pictour.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import wzmame.pictour.R;
import wzmame.pictour.fragment.TourListViewFragment;
import wzmame.pictour.fragment.TourMapViewFragment;

public class TourView extends AppCompatActivity {
    ViewPager vPager;
    FragmentPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_view);
        vPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new TourViewPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapter);
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tour_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TourViewPagerAdapter extends FragmentPagerAdapter {

        private String[] titles = {"List", "Map"};
        private TourListViewFragment lvFragment;
        private TourMapViewFragment mvFragment;


        public TourViewPagerAdapter(FragmentManager fm) {
            super(fm);
            lvFragment = new TourListViewFragment();
            mvFragment = new TourMapViewFragment();
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return lvFragment;
            } else {
                return mvFragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
