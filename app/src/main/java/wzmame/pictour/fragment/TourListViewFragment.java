package wzmame.pictour.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import wzmame.pictour.R;

/**
 * Created by xmeng on 11/25/15.
 */
public class TourListViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tour_list_view_fragment,container, false);
        String[] listItems = {"Location1", "Location2", "Location3"};
        ListView lvLocation = (ListView) v.findViewById(R.id.lvLocations);
        ArrayAdapter<String> sampleAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems);
        lvLocation.setAdapter(sampleAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
