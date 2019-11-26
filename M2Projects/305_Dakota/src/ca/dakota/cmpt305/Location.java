package ca.dakota.cmpt305;

/**
 * Location object
 *
 * Stores the attributes of a location object
 *
 * @author Dakota Doolaege
 */
public class Location {
    private double latitude;
    private double longitude;

    /**
     * Constructor
     *
     * @param latitude for the location
     * @param longitude for the location
     */
    public Location(double latitude, double longitude){ //called a constructor
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the latitude for the location
     * @return the latitude
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Gets the longitude for the location
     * @return the longitude
     */
    public double getLongitude(){
        return longitude;
    }

    public String toString(){
        return "("+latitude+", "+longitude+")";
    }
}
