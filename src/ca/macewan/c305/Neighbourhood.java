package ca.macewan.c305;

import java.util.Objects;

/**
 *  Neighbourhood.java
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

public class Neighbourhood {
    private int nbhoodID;
    private String nbhood;
    private String ward;

    public Neighbourhood(int nbhoodID, String nbhood, String ward){
        this.nbhoodID = nbhoodID;
        this.nbhood = nbhood;
        this.ward = ward;
    }

    public double getID(){
        return this.nbhoodID;
    }

    public String getNeighbourhood(){
        return this.nbhood;
    }

    public String getWard(){
        return this.ward;
    }

    public String toString() {
        return this.nbhood;
    }

    public int hashCode() {
        return Objects.hash(this.nbhoodID, this.nbhood, this.ward);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Neighbourhood))
            return false;
        return this.nbhoodID == ((Neighbourhood) obj).nbhoodID
                && this.nbhood.equals(((Neighbourhood) obj).nbhood)
                && this.ward.equals(((Neighbourhood) obj).ward);
    }
}
