package wzmame.pictour.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
