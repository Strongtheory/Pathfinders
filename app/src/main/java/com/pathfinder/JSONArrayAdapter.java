package com.pathfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by connor on 10/30/16.
 */

public class JSONArrayAdapter extends ArrayAdapter {
    private static final String TAG = "BaseAdapter";
    JSONArray array;
    String key;

    public JSONArrayAdapter (Context context, JSONArray array, String key) {
        super(context, 0);
        this.array = array;
        this.key = key;
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
            buildingName = array.getJSONObject(position).getString(key);
        } catch (JSONException e) {
            Log.e(TAG, "Could not find key!!!");
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        TextView bldgName = (TextView) convertView.findViewById(R.id.itemName);
        bldgName.setText(buildingName);
        return convertView;
    }
}
