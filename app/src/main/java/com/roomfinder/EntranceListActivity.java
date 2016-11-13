package com.roomfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class EntranceListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "EntranceListActivity";
    private Building building;
    private String room;
    private ListView mlistView;
    private List<Entrance> entranceList;
    private FilterableItemAdapter<Entrance> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            building = new Building(extras.getString("buildingName"),
                    extras.getString("address"),
                    extras.getDouble("latitude"),
                    extras.getDouble("longitude"),
                    extras.getLong("buildingId"),
                    extras.getString("url"),
                    extras.getStringArray("entrances"));
            room = extras.getString("room");
        } else {
            Log.e(TAG, "Extras == null");
        }

        mlistView = (ListView) findViewById(R.id.entrance_list);

        entranceList = new ArrayList<>();
        for (int i = 0; i < building.getEntrances().length; i++) {
            String curr = building.getEntrances()[i];
            entranceList.add(new Entrance(curr));
        }
        mAdapter = new FilterableItemAdapter<Entrance>(this, entranceList);
        mlistView.setAdapter(mAdapter);
        EditText searchText = (EditText) findViewById(R.id.entrance_search_bar);
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



        mlistView.setOnItemClickListener(this);



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = (TextView) view.findViewById(R.id.itemName);
        Intent intent = new Intent(this, NavigationActivity.class);
        intent.putExtra("buildingId", building.getId());
        intent.putExtra("buildingName", building.getName());
        intent.putExtra("latitude", building.getLatitude());
        intent.putExtra("longitude", building.getLongitude());
        intent.putExtra("address", building.getAddress());
        intent.putExtra("url", building.getUrl());
        intent.putExtra("entrances", building.getEntrances());
        intent.putExtra("room", room);
        intent.putExtra("entrance", textView.getText());
        startActivity(intent);
    }
    private class Entrance implements FilterableItem {
        private String name;
        public Entrance(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String valueToFiler() {
            return name;
        }
    }
}
