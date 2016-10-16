package com.pathfinders;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RoomListActivity extends ListActivity {

    // Display Data
    RoomArrayAdapter mAdapter;
    private static final String TAG = "BuildingListActivity";

    // List of Building Names
    static final String[] ROOMS = new String[] {
            "1",
            "2",
            "3"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        RoomArrayAdapter mAdapter = new RoomArrayAdapter(this, ROOMS);
        setListAdapter(mAdapter);


    }
    private class RoomArrayAdapter extends ArrayAdapter<String> {
        public RoomArrayAdapter(Context context, String[] buildings) {
            super(context, 0, buildings);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String buildingName = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_room, parent, false);
            }
            TextView bldgName = (TextView) convertView.findViewById(R.id.roomName);
            bldgName.setText(buildingName);
            return convertView;
        }
    }
}
