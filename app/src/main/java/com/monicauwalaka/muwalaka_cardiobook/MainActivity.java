package com.monicauwalaka.muwalaka_cardiobook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddMeasurementFragment.OnFragmentInteractionListener {


    ListView measurementList; // references the ListView in the MainActivity
    ArrayAdapter<Measurement> measurementAdapter;
    ArrayList<Measurement> measurementDataList; //stores each measurement

    int selectedItem;// variable to represent the position of a measurement item clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        measurementList = findViewById(R.id.measurement_list); //reference to list view from activity_main.xml



        loadData(); // load any saved data whenever the onCreate method of the MainActivity is called

        //to ensure that data is shown on the ListView pass "measurementDataList" to CustomList and set the list view adapter to the custom adapter - from Lab3 instructions
        measurementAdapter = new CustomList(this, measurementDataList);
        measurementList.setAdapter((measurementAdapter));

        //declare Button variables for the buttons in the MainActivity
        final Button deleteButton = findViewById(R.id.deleteMeasurementButton);  //deletes measurement
        final Button editViewButton = findViewById(R.id.edit_view_button);       //edit and view measurement
        final Button saveButton = findViewById(R.id.save_list_button);           //save measurement

        //gets a Measurement object that has been clicked
        measurementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = i;
            }

        });

        //click this button to open the AddMeasurementFragment- Lab3 instructions on Fragment
        final FloatingActionButton addMeasurementButton = findViewById(R.id.add_measurement_button);
        addMeasurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddMeasurementFragment().show(getSupportFragmentManager(), "ADD_MEASUREMENT");
            }
        });

        //selectedItem is deleted if the  deleteButton is clicked
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurementDataList.remove(selectedItem);
                measurementAdapter.notifyDataSetChanged();

            }
        });

        //Entire list of measurements is saved to gson when the saveButton is clicked by calling the saveData method
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        //selectedItem can be viewed and edited when the editViewButton is clicked
        editViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AddMeasurementFragment(measurementDataList.get(selectedItem), selectedItem).show(getSupportFragmentManager(), "Edit City");


            }
        });

    }
        // saveData and loadData methods-YouTube video by 'Coding in Flow' https://www.youtube.com/watch?v=jcliHGR3CHo
        //saveData method saves measurements to json with Gson
        private void saveData(){
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(measurementDataList);
            editor.putString("measurement list", json);
            editor.apply();

        }

        //loadData method loads saved measurements when the onCreate method of the MainActivity is called
        private void loadData(){

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("measurement list", null);
            Type type = new TypeToken<ArrayList<Measurement>>(){}.getType();
            measurementDataList= gson.fromJson(json,type);

            if (measurementDataList==null){
                measurementDataList = new ArrayList<>();
            }

        }


    //onOkPressed and onUpdatePressed methods from - Lab3 Fragment instructions
    @Override
    public void onOkPressed(Measurement newMeasurement) {measurementAdapter.add(newMeasurement); }

    @Override
    public void onUpdatePressed(Measurement newMeasurement, int position) {measurementDataList.set(position,newMeasurement); measurementAdapter.notifyDataSetChanged(); }
}






