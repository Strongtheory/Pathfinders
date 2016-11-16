package com.roomfinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
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
    private Room room;
    private Entrance entrance;
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

        //Java Garbage to individually cast Parcelable[] to Entrance[]
        //because you cannot cast arrays
        Parcelable[] entrancePs = extras.getParcelableArray("entrances");
        Entrance[] entrances = new Entrance[entrancePs.length];
        for (int i = 0; i < entrancePs.length; i++) {
            entrances[i] = (Entrance) entrancePs[i];
        }

        if (extras != null) {
            building = new Building(extras.getString("buildingName"),
                    extras.getString("address"),
                    extras.getDouble("latitude"),
                    extras.getDouble("longitude"),
                    extras.getLong("buildingId"),
                    extras.getString("url"),
                    entrances);
            room = (Room) extras.getParcelable("room");
            entrance = (Entrance) extras.getParcelable("entrance");
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
            Log.d(TAG, "Destination Building: " + building);
            Log.d(TAG, "Building Entrance: " + entrance);
            Log.d(TAG, "Destination Room: " + room);
        } else {
            Log.e(TAG, "Not connected to network");
        }





    }
    public void onReceivedJSON(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray stepsArr = json.getJSONArray("steps");
            Step[] steps = new Step[stepsArr.length()];
            for (int i = 0; i < steps.length; i++) {
                JSONObject step = stepsArr.getJSONObject(i);
                JSONObject sN = step.getJSONObject("startingNode");
                JSONObject eN = step.getJSONObject("endingNode");
                JSONArray sRoomArr = sN.getJSONArray("rooms");
                JSONArray eRoomArr = eN.getJSONArray("rooms");
                String[] sRooms = new String[sRoomArr.length()];
                for (int j = 0; j < sRoomArr.length(); j++) {
                    sRooms[j] = sRoomArr.getString(j);
                }
                String[] eRooms = new String[eRoomArr.length()];
                for (int j = 0; j < eRoomArr.length(); j++) {
                    eRooms[j] = eRoomArr.getString(j);
                }
                Node startingNode = new Node(sN.getLong("id"),sN.getDouble("latitude"),sN.getDouble("longitude"),sN.getInt("floor"),sN.getBoolean("entrance"),sRooms);
                Node endingNode = new Node(eN.getLong("id"), eN.getDouble("latitude"), eN.getDouble("longitude"),eN.getInt("floor"),sN.getBoolean("entrance"),eRooms);
                steps[i] = new Step(NavigationInstruction.values()[step.getInt("action")],
                        step.getDouble("distance"),
                        startingNode,
                        endingNode);
            }
            directions = new Directions(new Entrance(json.getJSONObject("buildingEntrance")),
                    new Building(json.getJSONObject("destinationBuilding")),
                    new Room(json.getJSONObject("destinationRoom")),
                    json.getDouble("estimatedTravelTime"),
                    json.getDouble("totalDistance"),
                    steps);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mAdapter = new ArrayAdapter<String>(this, R.layout.item_generic_list, R.id.itemName, directions.getInstructions());
        mlistView.setAdapter(mAdapter);
    }
}
