package com.roomfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NavigationActivity extends AppCompatActivity {
    private static final String TAG = "NavigationActivity";
    private Building building;
    private String room;
    private String entrance;
    private Directions directions;

    private ListView mlistView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            entrance = extras.getString("entrance");
        } else {
            Log.e(TAG, "Extras == null");
        }
        Log.d(TAG, "Navigating in " + building.getName() + " to room: " + room + ", starting at: " + entrance);

        //Check Network Connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            JSONTask task = new com.roomfinder.JSONTask() {
                @Override
                protected void onPostExecute(String result) { onReceivedJSON(result);}
            };
            //task.execute("https://roomfinders.herokuapp.com/buildings/" + building.getId() + "/rooms");
        } else {
            Log.e(TAG, "Not connected to network");
        }





    }
    public void onReceivedJSON(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray steps = json.getJSONArray("steps");
            NavigationInstruction[] stepTypes = new NavigationInstruction[steps.length()];
            double[] stepDistances = new double[steps.length()];
            for (int i = 0; i < steps.length(); i++) {
                JSONObject step = steps.getJSONObject(i);
                stepTypes[i] = NavigationInstruction.values()[step.getInt("action")];
                stepDistances[i] = step.getDouble("distance");
            }
            directions = new Directions(json.getString("buildingEntrance"),
                    json.getString("destinationBuilding"),
                    json.getString("destinationRoom"),
                    json.getDouble("estimatedTravelTime"),
                    stepTypes,
                    stepDistances);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mAdapter = new ArrayAdapter<String>(this, R.layout.item_generic_list, R.id.itemName, directions.getSteps());
        mlistView.setAdapter(mAdapter);
    }
}
