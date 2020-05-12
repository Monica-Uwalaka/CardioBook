package com.monicauwalaka.muwalaka_cardiobook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//CustomList class is a customized version of ArrayAdapter<Measurement> so that the Measurement objects can be stored in the ListView
public class CustomList extends ArrayAdapter<Measurement> {

    //These  two variables hold measurements and activity context respectively
    private ArrayList<Measurement> measurements;
    private Context context;

    //constructor
    public CustomList(Context context, ArrayList<Measurement> measurements) {
        super(context, 0, measurements);
        this.measurements = measurements;
        this.context = context;

    }

    //getView sets the values for each measurement data in MeasurementDataList(listView in MainActivity)
    public  View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Measurement measurement = measurements.get(position); //get measurement position

        //reference TextViews in content.xml
        TextView dateEntered = view.findViewById(R.id.date_text);
        TextView timeEntered = view.findViewById(R.id.time_text);
        TextView sysPressureEntered = view.findViewById(R.id.Systolic_pressure_text);
        TextView diaPressureEntered = view.findViewById(R.id.diastolic_Pressure_text);
        TextView heartRateEntered = view.findViewById(R.id.heart_rate_text);
        TextView commentEntered = view.findViewById(R.id.comment_text);

        //set measurement data by getting entry entered by user
        dateEntered.setText("Date: " + measurement.getDate());
        timeEntered.setText("Time: "+measurement.getTime());
        heartRateEntered.setText("Heart Rate: "+measurement.getHeartRate());
        commentEntered.setText("comment: "+measurement.getComment());
        sysPressureEntered.setText("Systolic Pressure: "+measurement.getSystolicPressure());

        //check unusual systolic pressure and set the text colour to red if it is not between 90 and 140
        if (!(Integer.parseInt(measurement.getSystolicPressure())> 90 && Integer.parseInt(measurement.getSystolicPressure())<140)){
            sysPressureEntered.setTextColor(Color.RED);
        }

        //check unusual diastolic pressure and set the text colour to red if it is not between 60 and 90
        diaPressureEntered.setText("Diastolic Pressure: "+measurement.getDiastolicPressure());
        if (!(Integer.parseInt(measurement.getDiastolicPressure())> 60 && Integer.parseInt(measurement.getDiastolicPressure())<90)){
            diaPressureEntered.setTextColor(Color.RED);
        }

        return view;


    }
}
