package ca.macewan.cmpt305;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean latRangeCheck() {
        if (this.latitude >= -90 && this.latitude <= 90) {
            return true;
        }
        return false;
    }

    public boolean longRangeCheck() {
        if(this.longitude >= -180 && this.longitude <= 180) {
            return true;
        }
        return false;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Override toString, equals, and hashCode here
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Location))
            return false;
        Location otherLocation = (Location) obj;
        return this.latitude == otherLocation.latitude && this.longitude == otherLocation.longitude;
    }
    public int hashCode() {
        double result = 17;
        result = 31 * result + latitude;
        result = 31 * result + longitude;
        return (int)result;
    }
}
