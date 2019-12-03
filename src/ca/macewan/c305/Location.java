package ca.macewan.c305;

import java.util.Objects;

/**
 *  Location.java
 *
 *  CMPT 305:
 *      Fall 2019
 *  Final Project
 *      Custom Property Assessments Application
 *  Names:
 *      Alex Worthy
 *      Dakota Doolaege
 *      Shea Odland
 */

public class Location {
    private double latitude;
    private double longitude;

    public Location(double lat, double lng) {
        if (!this.rangeCheck(lat, lng)) {
            throw new IllegalArgumentException("Latitude and/or Longitude are not valid");
        }
        this.latitude = lat;
        this.longitude = lng;
    }

    private boolean rangeCheck(double lat, double lng) {
        if (-90 <= lat && lat <= 90) {
            if (-180 <= lng && lng <= 180) {
                return true;
            }
        }
        return false;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public Location getCopy() {
        Location locationCopy = new Location(this.getLatitude(), this.getLongitude());
        return locationCopy;
    }

    public String toString() {
        return "(" + this.latitude + ", " + this.longitude + ")";
    }

    public int hashCode() {
        return Objects.hash(this.latitude, this.longitude);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Location))
            return false;
        return this.latitude == ((Location) obj).latitude && this.longitude == ((Location) obj).longitude;
    }
}