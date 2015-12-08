package wzmame.pictour.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import wzmame.pictour.R;
import wzmame.pictour.model.Tour;

/**
 * Created by xmeng on 12/7/15.
 */
public class HomeViewAdapter extends ParseQueryAdapter<Tour>{
    public HomeViewAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<Tour>() {
            public ParseQuery create() {
                ParseQuery<Tour> query = new ParseQuery<>(Tour.class);
                query.addDescendingOrder("createdAt");
                return query;
            }
        });
    }

    @Override
    public View getItemView(Tour t, View v, ViewGroup parent) {
        if(v == null) {
            v = View.inflate(getContext(), R.layout.layout_home_list_item, null);
        }
        super.getItemView(t, v, parent);
        TextView tvTitle = (TextView) v.findViewById(R.id.tvHomeTitle);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvHomeDescription);
        tvTitle.setText(t.getName());
        tvDescription.setText(t.getDescription());
        return v;
    }
}
