package com.krrishdholakia.community_reporting.application.Problem_Reporting;

/**
 * Created by community_reporting on 14/10/16.
 */
public class Geo_Data {

    double latitude;
    double longitude;

    public Geo_Data(){

    }

    public Geo_Data(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
