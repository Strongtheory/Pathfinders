package com.pathfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class BuildingListActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private static final String TAG = "BuildingListActivity";
    ListView mlistView;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.building_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //final BuildingArrayAdapter mAdapter = new BuildingArrayAdapter(this, BUILDINGS);

        mlistView = (ListView) findViewById(R.id.building_list);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            com.pathfinder.JSONTask task = new com.pathfinder.JSONTask() {
                @Override
                protected void onPostExecute(JSONArray array) {
                    jsonArray = array;
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
        final JSONArrayAdapter mAdapter = new JSONArrayAdapter(getBaseContext(), jsonArray, "name");
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
    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
        TextView textView = (TextView) view.findViewById(R.id.itemName);
        Log.d(TAG, "Clicked: " + textView.getText());
        if (jsonArray != null) {
            Intent intent = new Intent(this, RoomListActivity.class);
            try {
                intent.putExtra("buildingId", jsonArray.getJSONObject(postion).getLong("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }

    }
    private class JSONTask extends AsyncTask<String, Void, JSONArray> {
        private final String TAG = "JSONTask";

        @Override
        protected JSONArray doInBackground(String... urls) {
            InputStream is = null;
            JSONArray arr;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();
                int response = connection.getResponseCode();
                Log.d(TAG, "GET Request Code: " + response);
                is = new BufferedInputStream(connection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder builder = new StringBuilder();
                String recvd;
                while ((recvd = reader.readLine()) != null)
                    builder.append(recvd);
                try {
                    arr = new JSONArray(builder.toString());
                } catch (JSONException ex) {
                    arr = new JSONArray();

                }

            } catch (Exception e) {
                e.printStackTrace();
                arr = new JSONArray();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            return arr;
        }

    }
}

