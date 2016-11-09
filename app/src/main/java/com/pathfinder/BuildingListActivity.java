package com.pathfinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * View which displays all buildings on GT Campus
 * Connor Reeder
 */

public class BuildingListActivity extends AppCompatActivity implements ListView.OnItemClickListener, DLoadImageTask.Listener{

    private static final String TAG = "BuildingListActivity";
    ListView mlistView;
    List<Building> buildingList;
    FilterableItemAdapter<Building> mAdapter;
    private RoomListActivity child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.building_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        mlistView = (ListView) findViewById(R.id.building_list);

        //Check if we have internet connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            //get jsonObjectArray and then parse it into an array of Building Objects
            com.pathfinder.JSONTask task = new com.pathfinder.JSONTask() {
                @Override
                protected void onPostExecute(JSONArray array) {
                    buildingList = new ArrayList<Building>();
                    try {
                        for (int i = 0; i < array.length(); i++) {
                            buildingList.add(new Building(array.getJSONObject(i)));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    onReceivedJSONArray();
                }
            };
            task.execute("https://roomfinders.herokuapp.com/buildings");
        } else {
            Log.e(TAG, "Not connected to network");
        }



        mlistView.setOnItemClickListener(this);

    }

    private void onReceivedJSONArray() {
        //final BuildingArrayAdapter mAdapter = new BuildingArrayAdapter(getBaseContext(), buildingList);
        new DLoadImageTask(getBaseContext(), this).execute(buildingList);
        mAdapter = new FilterableItemAdapter<Building>(this, buildingList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String name = getItem(position).getName();
                String filename = getItem(position).getId() + ".jpg";

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_building_list, parent, false);
                }
                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
                TextView bldgName = (TextView) convertView.findViewById(R.id.buildingName);

                File imageFile = new File(getCacheDir(), filename);
                if (imageFile.exists()) {
                    Log.d(TAG, "name: " + name + ", " + "imageFile: " + imageFile);
                    //BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap bmp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    imageView.setImageBitmap(bmp);
                }



                bldgName.setText(name);
                return convertView;
            }
        };

        mlistView.setAdapter(mAdapter);
        EditText searchText = (EditText) findViewById(R.id.building_search_bar);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView bldgNameView = (TextView) view.findViewById(R.id.buildingName);
        Log.d(TAG, "Clicked: " + bldgNameView.getText());
        if (buildingList != null) {
            Intent intent = new Intent(this, RoomListActivity.class);
            Building bldg = buildingList.get(position);
            intent.putExtra("buildingId", bldg.getId());
            intent.putExtra("buildingName", bldg.getName());
            intent.putExtra("latitude", bldg.getLatitude());
            intent.putExtra("longitude", bldg.getLongitude());
            intent.putExtra("address", bldg.getAddress());
            intent.putExtra("url", bldg.getUrl());
            startActivity(intent);
        }
    }

    @Override
    public void onIconsDownloaded() {
        mAdapter.notifyDataSetChanged();
    }

}

