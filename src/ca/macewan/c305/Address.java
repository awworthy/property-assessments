package ca.macewan.c305;

import java.util.Objects;

/**
 *  Address.java
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

public class Address {
    private String suite;
    private String houseNum;
    private String streetName;

    public Address(String suite, String houseNum, String streetName) {
        this.suite = suite;
        this.houseNum = houseNum;
        this.streetName = streetName;
    }

    public String getSuite(){
        return this.suite;
    }

    public String getHouseNum(){
        return this.houseNum;
    }

    public String getStreetName(){
        return this.streetName;
    }

    public String toString() {
        return (this.suite + " " + this.houseNum + " " + this.streetName).strip();
    }

    public int hashCode() {
        return Objects.hash(this.suite, this.houseNum, this.streetName);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Address))
            return false;
        return this.suite == ((Address) obj).suite
                && this.houseNum == ((Address) obj).houseNum
                && this.streetName == ((Address) obj).streetName;
    }

//    Reflexive: x.equals(x) should return true
//    x = y should return true iff y = x
//    and so on
}