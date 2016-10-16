package com.pathfinders;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class BuildingActivity extends ListActivity
            implements LoaderManager.LoaderCallbacks<Cursor> {

    // Display Data
    SimpleCursorAdapter mAdapter;

    // List of Building Names
    static final String[] BUILDINGS = new String[] {
            "Klaus",
            "CULC",
            "Howey",
            "Love Manufacturing"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
