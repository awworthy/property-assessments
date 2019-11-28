package ca.macewan.cmpt305;

public class PropAssess implements Comparable{
    private int acctNumber;
    private Address address;
    private Neighborhood neighborhood;
    private int value;
    private String  cls;
    private Location location;
    private String hasGarage;

    public PropAssess(int acctNumber, Address address, Neighborhood neighborhood, int value, String cls, Location
                        location, String hasGarage) {
        this.acctNumber = acctNumber;
        this.address = address;
        this.neighborhood = neighborhood;
        this.value = value;
        this.cls = cls;
        this.location = location;
        this.hasGarage = hasGarage;
    }

    public int getAcctNumber() {
        return this.acctNumber;
    }

    public Address getAddress() {
        return this.address;
    }

    public Neighborhood getNeighborhood() {
        return this.neighborhood;
    }

    public int getValue() {
        return this.value;
    }

    public String getCls() {
        return this.cls;
    }

    public Location getLocation() {
        return this.location;
    }

    public double getLatitude() {return this.location.getLatitude();}

    public double getLongitude() { return this.location.getLongitude();}

    public String getGarage() {
        return this.hasGarage;
    }

    // Override toString, equals, and hashCode here

    public String toString() {
        return "Property Assessment for Property ID: " + this.getAcctNumber();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PropAssess))
            return false;
        PropAssess otherPropAssess = (PropAssess) obj;
        return this.acctNumber == otherPropAssess.acctNumber && this.address.equals(otherPropAssess.address) &&
                this.neighborhood.equals(otherPropAssess.neighborhood) && this.value == otherPropAssess.value &&
                this.cls.equals(otherPropAssess.cls) && this.location.equals(otherPropAssess.location) &&
                this.hasGarage.equals(otherPropAssess.hasGarage);
    }
    public int hashCode() {
        return acctNumber;
    }

    public int compareTo(Object prop) {
        int compareVal = ((PropAssess) prop).getValue();
        return this.value - compareVal;
    }
}