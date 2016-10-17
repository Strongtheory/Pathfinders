package com.pathfinders;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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

public class BuildingListActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    // Display Data
    SimpleCursorAdapter mAdapter;
    private static final String TAG = "BuildingListActivity";

    // List of Building Names
    static final String[] BUILDINGS = new String[] {
            "Academy of Medicine",
            "Advanced Wood Products Lab",
            "Allen Sustainable Education Building",
            "Alumni/Faculty House",
            "Baker Building",
            "Bill Moore Student Success Center",
            "Boggs Chemistry Building",
            "Bradley Building",
            "Broadband Institute Residential Lab",
            "Bunger-Henry Building",
            "Business Services Building",
            "Capital Assets Accounting",
            "Central Receiving Office",
            "Purchasing Center",
            "Risk Management",
            "Bus Yard",
            "Caddell Building",
            "Callaway Manufacturing Research Center",
            "Capital Assets Accounting Staging",
            "Carbon-Neutral Energy Solutions Laboratory",
            "Carnegie Building",
            "CEISMC",
            "Centennial Research Building",
            "Center for Advanced Brain Imaging",
            "Center for Assistive Technology & Environmental Access",
            "Centergy One",
            "Chapin Building (OMED)",
            "Cherry Emerson Building",
            "Clough Undergraduate Learning Commons",
            "College of Architecture (East)",
            "College of Architecture (West)",
            "College of Computing",
            "Commander Building",
            "Coon Building",
            "Couch Music Building",
            "Crosland Tower",
            "Daniel Laboratory",
            "D.M. Smith Building",
            "Economic Development Building",
            "Engineered Biosystems Building",
            "Engineering Science and Mechanics",
            "Environmental Health & Safety",
            "Facilities Administration Building",
            "Ferst Center for the Arts",
            "Food Processing Technology Building",
            "Ford Environmental Science & Technology",
            "French Building",
            "Georgia Tech Hotel & Conference Center",
            "Georgia Tech Research Institute",
            "Global Learning Center",
            "Goldin House",
            "Grinnell Building",
            "Groseclose Building",
            "GTRI Conference Center",
            "Guggenheim Aerospace Building",
            "Harrison Square",
            "Hayes House",
            "Hinman Research Building",
            "Holland Heating And Cooling Plant",
            "Howey Physics Building",
            "Instructional Center",
            "ISyE Annex",
            "Ivan Allen College",
            "Kessler Campanile",
            "Klaus Advanced Computing Building",
            "Knight Aerospace Engineering",
            "Landscape Offices",
            "Library and Information Center",
            "Love Manufacturing Building",
            "Luck Building",
            "Lyman Hall (Bursar)",
            "Budget Planning and Administration",
            "Bursar's Office/Financial Services",
            "Capital Planning and Space Management",
            "Manufacturing Related Disciplines Complex",
            "Marcus Nanotechnology Building",
            "Mason Building",
            "Office of Human Resources",
            "Office of Information Technology (OIT)",
            "OIT Engineering",
            "O'Keefe Custodial Services Building",
            "O'Keefe Main Building",
            "Old Civil Engineering Building",
            "Paper Tricentenial Building",
            "Parker H. Petit Biotechnology Building",
            "Parking & Transportation Services",
            "Paul Heffernan House",
            "Pettit Microelectronics Research Center",
            "President's House",
            "Printing & Copying Services",
            "Research Administration Building",
            "Rich Computer Center",
            "R. Kirk Landon Learning Center",
            "Robinson Molecular Science & Engineering",
            "Savant Building",
            "Scheller College of Business",
            "Skiles Classroom Building",
            "Smithgall Student Services Building",
            "Southern Regional Education Board",
            "Stamps Student Center Commons",
            "Stein House",
            "Stephen C. Hall Building",
            "Structural Engineering & Materials Research",
            "Swann Building",
            "Technology Enterprise Park",
            "Technology Square Research Building",
            "Tech Tower",
            "Tech Way Building",
            "Van Leer Building",
            "Wardlaw Center",
            "Weber Space Science & Technology 1",
            "Weber Space Science & Technology 3",
            "Wenn Student Center",
            "W.H. Emerson Building",
            "Whitaker Biomedical Engineering Building",
            "Zinn Aerospace Combustion Lab"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final BuildingArrayAdapter mAdapter = new BuildingArrayAdapter(this, BUILDINGS);
        ListView mlistView = (ListView) findViewById(R.id.building_list);
        mlistView.setAdapter(mAdapter);
        mlistView.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textView = (TextView) view.findViewById(R.id.bldgName);
        Log.d(TAG, "Clicked: " + textView.getText());

        Intent intent = new Intent(this, RoomListActivity.class);
        startActivity(intent);
    }

    private class BuildingArrayAdapter extends ArrayAdapter<String> {
        public BuildingArrayAdapter(Context context, String[] buildings) {
            super(context, 0, buildings);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String buildingName = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_building, parent, false);
            }
            TextView bldgName = (TextView) convertView.findViewById(R.id.bldgName);
            bldgName.setText(buildingName);
            return convertView;
        }
    }
}

