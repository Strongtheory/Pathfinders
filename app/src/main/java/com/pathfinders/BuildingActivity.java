package com.pathfinders;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BuildingActivity extends ListActivity
            implements LoaderManager.LoaderCallbacks<Cursor> {

    // Display Data
    SimpleCursorAdapter mAdapter;

    // List of Building Names
    static final String[] BUILDINGS = new String[] {
            "Klaus",
            "CULC",
            "Howey"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        BuildingAdapter mAdapter = new BuildingAdapter(this, BUILDINGS);
        setListAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    private class BuildingAdapter extends ArrayAdapter<String> {
        public BuildingAdapter(Context context, String[] buildings) {
            super(context, 0, buildings);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String buildingName = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_building, parent, false);
            }
            TextView bldgName = (TextView) convertView.findViewById(R.id.bldgName);
            bldgName.setText(buildingName);
            return convertView;
        }
    }
}

