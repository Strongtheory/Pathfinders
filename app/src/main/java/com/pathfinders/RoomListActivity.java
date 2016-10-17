package com.pathfinders;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class RoomListActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    // Display Data
    RoomArrayAdapter mAdapter;
    private static final String TAG = "BuildingListActivity";

    // List of Building Names
    static final String[] ROOMS = new String[] {
        "1", "2", "3"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        final RoomArrayAdapter mAdapter = new RoomArrayAdapter(this, ROOMS);
        ListView mlistView = (ListView) findViewById(R.id.room_list);
        mlistView.setAdapter(mAdapter);
        mlistView.setOnItemClickListener(this);
        EditText searchText = (EditText) findViewById(R.id.room_search_bar);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
