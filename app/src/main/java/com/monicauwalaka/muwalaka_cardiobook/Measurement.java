package com.monicauwalaka.muwalaka_cardiobook;

//this class creates creates a Measurement object

public class Measurement {

    //attributes
    private String date;
    private String time;
    private String systolicPressure;
    private String diastolicPressure;
    private String heartRate;
    private String comment;

    // constructor
    public Measurement(String  date, String  time, String  systolicPressure, String  diastolicPressure, String  heartRate, String comment ){
        this.date = date;
        this.time = time;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.heartRate = heartRate;
        this.comment = comment;

    }

    //getters and setters
    public String getDate() {

        return date;
    }

    public String getTime() {

        return time;
    }


    public String getSystolicPressure() {

        return systolicPressure;
    }


    public String getDiastolicPressure() {

        return diastolicPressure;
    }


    public String getHeartRate() {

        return heartRate;
    }


    public String getComment() {

        return comment;
    }


}
