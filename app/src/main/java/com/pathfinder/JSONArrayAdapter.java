package com.pathfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Created by connor on 10/30/16.
 */

public class JSONArrayAdapter extends ArrayAdapter {
    private static final String TAG = "BaseAdapter";
    JSONArray array;

    public JSONArrayAdapter (Context context, JSONArray array) {
        super(context, 0);
        this.array = array;
    }
    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public JSONObject getItem(int i) {
        try {
            return array.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String buildingName = null;
        try {
            buildingName = array.getJSONObject(position).getString("name");
        } catch (JSONException e) {
            Log.e(TAG, "Could not get name of building!!!");
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_building, parent, false);
        }
        TextView bldgName = (TextView) convertView.findViewById(R.id.bldgName);
        bldgName.setText(buildingName);
        return convertView;
    }
}
