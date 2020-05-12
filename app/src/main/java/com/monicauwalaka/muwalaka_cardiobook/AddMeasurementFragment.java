package com.monicauwalaka.muwalaka_cardiobook;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class AddMeasurementFragment extends DialogFragment {
    private EditText newDate;
    private EditText newTime;
    private EditText newSystolicPressure;
    private EditText newDiastolicPressure;
    private EditText newHeartRate;
    private EditText newComment;
    private Measurement measurement;

    //position of measurement to the updated
    private int position;
    private OnFragmentInteractionListener listener;

    //interface calls onOkPressed and onUpdatePressed methods in MainActivity
    public interface OnFragmentInteractionListener {
        void onOkPressed(Measurement newMeasurement);

        void onUpdatePressed(Measurement newMeasurement, int position);
    }

    //constructors
    //Adds new measurement
    AddMeasurementFragment() {
    }

    //update existing measurement
    AddMeasurementFragment(Measurement existingMeasurement, int position) {
        this.measurement = existingMeasurement;
        this.position = position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //initialize view for AddMeasurementFragment by referencing the add_edit_fragment_layout
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_edit_fragment_layout, null);

        //initialize EditText fields
        newDate = view.findViewById(R.id.date_editText);
        newTime = view.findViewById(R.id.time_editText);
        newSystolicPressure = view.findViewById(R.id.systolic_pressure_editText);
        newDiastolicPressure = view.findViewById(R.id.diastolic_pressure_editText);
        newHeartRate = view.findViewById(R.id.heart_rate_editText);
        newComment = view.findViewById(R.id.comment_editText);

        //create new AlertDialog using builder method
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //if measurement already exists - to be carried when user wants to update a measurement that already exist in the measurementDataList
        if (measurement != null) {
            //set measurement data
            newDate.setText(measurement.getDate());
            newTime.setText(measurement.getTime());
            newSystolicPressure.setText(String.valueOf(measurement.getSystolicPressure()));
            newDiastolicPressure.setText(String.valueOf(measurement.getDiastolicPressure()));
            newHeartRate.setText(String.valueOf(measurement.getHeartRate()));
            newComment.setText(measurement.getComment());


            return builder
                    .setView(view)
                    .setTitle("Edit/View Measurement")
                    .setNegativeButton("Cancel", null) //exit fragment without storing the data when user click 'cancel' on fragment
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String date = newDate.getText().toString();
                            String time = newTime.getText().toString();
                            String systolicPressure = newSystolicPressure.getText().toString();
                            String diastolicPressure = newDiastolicPressure.getText().toString();
                            String heartRate = newHeartRate.getText().toString();
                            String comment = newComment.getText().toString();

                            String dateRegex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
                            String timeRegex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";

                            Pattern datePattern = Pattern.compile(dateRegex);
                            Pattern timePattern = Pattern.compile(timeRegex);

                            Matcher dateMatcher = datePattern.matcher(date);
                            Matcher timeMatcher = timePattern.matcher(time);
                            if ((dateMatcher.matches() || timeMatcher.matches())) {
                                Toast.makeText(getContext(), "Wrong date format (yyyy-dd-mm) or time (hh:mm)", Toast.LENGTH_SHORT).show();

                                //listener.onOkPressed(new Measurement(date, time, systolicPressure, diastolicPressure, heartRate, comment));
                            } else if (!dateMatcher.matches()) {
                                Toast.makeText(getContext(), "Wrong date format (yyyy-dd-mm)", Toast.LENGTH_SHORT).show();

                            } else if (!timeMatcher.matches()) {
                                Toast.makeText(getContext(), "Wrong time format (hh-mm)", Toast.LENGTH_SHORT).show();

                            } else {
                                // update measurement object using the listener interface by passing new Measurement object
                                listener.onUpdatePressed(new Measurement(date, time, systolicPressure, diastolicPressure, heartRate, comment), position);
                            }


                            if (!isInteger(systolicPressure)){
                                Toast.makeText(getContext(), "Systolic Pressure should be a positive number", Toast.LENGTH_SHORT).show();

                            }
                            if (!isInteger(diastolicPressure)){
                                Toast.makeText(getContext(), "Diastolic pressure should be a positive number", Toast.LENGTH_SHORT).show();
                            }
                            if (!isInteger(heartRate)){
                                Toast.makeText(getContext(), "Heart rate should be a positive number", Toast.LENGTH_SHORT).show();


                            }

                        }
                    }).create();
        } else {

        }

        //if measurement doesn't exist
        return builder
                .setView(view)
                .setTitle("Add Measurement")
                .setNegativeButton("Cancel", null)//exit fragment without storing the data when user click 'cancel' on fragment
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date = newDate.getText().toString();
                        String time = newTime.getText().toString();
                        String systolicPressure = newSystolicPressure.getText().toString();
                        String diastolicPressure = newDiastolicPressure.getText().toString();
                        String heartRate = newHeartRate.getText().toString();
                        String comment = newComment.getText().toString();

                        /*VALIDATE INPUT*/
                        //Date validation using RegEx - HowToInJava Blog post by Lokesh Gupta https://howtodoinjava.com/regex/java-regex-date-format-validation/
                        //Date format validation in Java - Stackoverflow post by Greg Bacon https://stackoverflow.com/questions/2149680/regex-date-format-validation-on-java

                        String dateRegex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
                        String timeRegex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";

                        Pattern datePattern = Pattern.compile(dateRegex);
                        Pattern timePattern = Pattern.compile(timeRegex);

                        Matcher dateMatcher = datePattern.matcher(date);
                        Matcher timeMatcher = timePattern.matcher(time);
                        if (dateMatcher.matches() && timeMatcher.matches()) {
                            listener.onOkPressed(new Measurement(date, time, systolicPressure, diastolicPressure, heartRate, comment));
                        } else if (!dateMatcher.matches()) {
                            Toast.makeText(getContext(), "Wrong date format (yyyy-dd-mm)", Toast.LENGTH_SHORT).show();

                        } else if (!timeMatcher.matches()) {
                            Toast.makeText(getContext(), "Wrong time format (hh-mm)", Toast.LENGTH_SHORT).show();

                        } else {
                        }


                        if (!isInteger(systolicPressure)){
                            Toast.makeText(getContext(), "Systolic Pressure should be a positive number", Toast.LENGTH_SHORT).show();

                        }
                        if (!isInteger(diastolicPressure)){
                            Toast.makeText(getContext(), "Diastolic pressure should be a positive number", Toast.LENGTH_SHORT).show();
                        }
                        if (!isInteger(heartRate)){
                            Toast.makeText(getContext(), "Heart rate should be a positive number", Toast.LENGTH_SHORT).show();


                        }


                        //listener.onOkPressed(new Measurement(date,time,systolicPressure,diastolicPressure,heartRate,comment));
                    }
                }).create();
    }

    public boolean isInteger(String string) {
        try {
            int num = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;

        }
    }


}
