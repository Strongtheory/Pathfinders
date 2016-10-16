package com.pathfinders;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class BuildingListActivity extends ListActivity
            implements LoaderManager.LoaderCallbacks<Cursor> {

    // Display Data
    SimpleCursorAdapter mAdapter;
    private static final String TAG = "BuildingListActivity";

    // List of Building Names
    static final String[] BUILDINGS = new String[] {
            "Klaus",
            "CULC",
            "Howey"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        BuildingArrayAdapter mAdapter = new BuildingArrayAdapter(this, BUILDINGS);
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        TextView textView = (TextView) v.findViewById(R.id.bldgName);
        Log.d(TAG, "Clicked: " + textView.getText());

        Intent intent = new Intent(this, RoomListActivity.class);
        startActivity(intent);
    }
    private class BuildingArrayAdapter extends ArrayAdapter<String> {
        public BuildingArrayAdapter(Context context, String[] buildings) {
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

