package wzmame.pictour.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseQueryAdapter;

import wzmame.pictour.R;
import wzmame.pictour.activity.LocationView;
import wzmame.pictour.adapter.TourLocationsAdapter;

/**
 * Created by xmeng on 11/25/15.
 */
public class TourListViewFragment extends Fragment {

    private ParseQueryAdapter aLocations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tour_list_view_fragment, container, false);
        ListView lvLocation = (ListView) v.findViewById(R.id.lvLocations);

        String tourId = getActivity().getIntent().getStringExtra("tourId");
        aLocations = new TourLocationsAdapter(getContext(), tourId);
        aLocations.setTextKey("name");

        lvLocation.setAdapter(aLocations);
        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationId = aLocations.getItem(position).getObjectId();

                Intent i = new Intent(getContext(), LocationView.class);
                i.putExtra("locationId", locationId);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
