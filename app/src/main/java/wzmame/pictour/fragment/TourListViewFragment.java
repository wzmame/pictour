package wzmame.pictour.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import wzmame.pictour.R;
import wzmame.pictour.activity.LocationView;

/**
 * Created by xmeng on 11/25/15.
 */
public class TourListViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] listItems = {"Location1", "Location2", "Location3"};
        ArrayAdapter<String> sampleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listItems);

        View v = inflater.inflate(R.layout.fragment_tour_list_view_fragment, container, false);
        ListView lvLocation = (ListView) v.findViewById(R.id.lvLocations);
        lvLocation.setAdapter(sampleAdapter);
        lvLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locationId = "yqAsJzXHaD"; // TODO: Remove hardcoded value

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
