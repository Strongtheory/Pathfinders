package com.pathfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;

/**
 * View which displays rooms for a building
 * Connor Reeder
 */

public class RoomListActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = "BuildingListActivity";
    ListView mlistView;

    long buildingId;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.room_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            buildingId = extras.getLong("buildingId");
        } else {
            buildingId = 0;
        }

        //final RoomArrayAdapter mAdapter = new RoomArrayAdapter(this, ROOMS);


        mlistView = (ListView) findViewById(R.id.room_list);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            JSONTask task = new com.pathfinder.JSONTask() {
              @Override
              protected void onPostExecute(JSONArray array) {
                  jsonArray = array;
                  onReceivedJSONArray();
              }
            };

            task.execute("https://roomfinders.herokuapp.com/buildings/" + buildingId + "/rooms");
        } else {
            Log.e(TAG, "Not connected to network");
        }

        mlistView.setOnItemClickListener(this);
    }
    private void onReceivedJSONArray() {
        final JSONArrayAdapter mAdapter = new JSONArrayAdapter(getBaseContext(), jsonArray, "roomNumber");
        mlistView.setAdapter(mAdapter);
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

}
