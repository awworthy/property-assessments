package ca.macewan.c305;

import java.util.Objects;

/**
 *  PropertyAssessment.java
 *
 *  CMPT 305:
 *      Fall 2019
 *  Assignment 1:
 *      Milestone 1
 *  Name:
 *      Alex Worthy
 *  ID:
 *      1742554
 */

public class PropertyAssessment implements Comparable {
    // PropertyAssessment(accountNum, address,
    //                    assessedValue, classification, neighbourhood, location);
    private int accountNum;
    private Address address;
    private int value;
    private String classification;
    private Neighbourhood neighbourhood;
    private String garage;
    private Location location;

    public PropertyAssessment(int accountNum, Address address, int value,
                              String classification, Neighbourhood neighbourhood,
                              String garage, Location location) {
        this.accountNum = accountNum;
        this.address = address;
        this.value = value;
        this.classification = classification;
        this.neighbourhood = neighbourhood;
        this.garage = garage;
        this.location = location;
    }

    public int getAccountNum() {
        return this.accountNum;
    }

    public Address getAddress() { return this.address; }

    public String getClassification() { return this.classification; }

    public int getValue() {
        return this.value;
    }

    public double getLatitude() {
        return this.location.getLatitude();
    }

    public double getLongitude() {
        return this.location.getLongitude();
    }

    public String toString() {
        return "Account Number = " + this.accountNum
                + "\nAddress = " + this.address.toString()
                + "\nAssessed Value = $" + this.value
                + "\nAssessment Class = " + this.classification
                + "\nNeighbourhood = " + this.neighbourhood.toString()
                + "\nLocation = " + this.location.toString();
    }

    public int hashCode() {
        return Objects.hash(this.accountNum, this.address, this.value, this.classification,
                this.neighbourhood, this.garage, this.location);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyAssessment))
            return false;
        return this.accountNum == ((PropertyAssessment) obj).accountNum
                && this.address.equals(((PropertyAssessment) obj).address)
                && this.value == ((PropertyAssessment) obj).value
                && this.classification.equals(((PropertyAssessment) obj).classification)
                && this.neighbourhood.equals(((PropertyAssessment) obj).neighbourhood)
                && this.garage.equals(((PropertyAssessment) obj).garage)
                && this.location.equals(((PropertyAssessment) obj).location);
    }

    public int compareTo(Object obj) {
        if (!(obj instanceof PropertyAssessment))
            return 0;
        return this.value - ((PropertyAssessment) obj).value;
    }

    public Neighbourhood getNeighbourhood() {
        return this.neighbourhood;
    }
}
