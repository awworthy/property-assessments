package ca.macewan.c305;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

/**
 *  PropertyAssessments.java
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

public class PropertyAssessments {
    // This one will return the descriptive statistics
    public List<PropertyAssessment> propertyAssessmentsList = new ArrayList<>();
    private int length;
    private boolean sorted = true;
    private Set<String> assessClassSet = new HashSet<>();

    //shea addition
    private Set<String> neighborhoodSet = new TreeSet<>();
    private Set<String> wardSet = new TreeSet<>();

    public PropertyAssessments() {
        // The Collection is constructed with length 0
        this.length = 0;
    }

    public List<PropertyAssessment> getPropertyAssessments() {
        return this.propertyAssessmentsList;
    }

    public PropertyAssessment getFirstPropertyAssessment() {
        if(!this.propertyAssessmentsList.isEmpty()) {
            return this.propertyAssessmentsList.get(0);
        }
        return null;
    }

    public void addPropertyAssessment(PropertyAssessment propertyAssessment) {
        // Adds a property assessment to the collection
        propertyAssessmentsList.add(propertyAssessment);
        this.length++;          // Keep track of the length for simplicity
        this.sorted = false;    // Keep track of whether the list has been sorted since something was added
                                // so that sorting isn't done on an already sorted list
        this.assessClassSet.add(propertyAssessment.getClassification());

        //shea addition
        this.neighborhoodSet.add(propertyAssessment.getNeighbourhood().getNeighbourhood());
        this.wardSet.add(propertyAssessment.getNeighbourhood().getWard());
    }

    //shea addition
    public PropertyAssessments getAssessmentsByWard(String ward) {
        // Returns ward-specific collection of property assessments
        PropertyAssessments wardAssessments = new PropertyAssessments();

        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            // Compare each assessment's ward with the search word
            String thisWard = propertyAssessment.getNeighbourhood().getWard().toUpperCase();

            if (thisWard.equals(ward.toUpperCase())) {
                // Add a valid property assessment to the new collection
                wardAssessments.addPropertyAssessment(propertyAssessment);
            }
        }
        return wardAssessments;
    }

    public PropertyAssessments getAssessmentsByNeighbourhood(String neighbourhood) {
        // Returns neighbourhood-specific collection of property assessments
        PropertyAssessments neighbourhoodAssessments = new PropertyAssessments();

        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            // Compare each assessment's neighbourhood with the search word
            Neighbourhood nbhood = propertyAssessment.getNeighbourhood();
            String thisNeighbourhood = nbhood.getNeighbourhood();

            if (thisNeighbourhood.startsWith(neighbourhood.toUpperCase())) {
                // Add a valid property assessment to the new collection
                neighbourhoodAssessments.addPropertyAssessment(propertyAssessment);
            }

        }
        return neighbourhoodAssessments;
    }

    public PropertyAssessments getAssessmentsByAddress(String inputAddress) {
        PropertyAssessments addressAssessments = new PropertyAssessments();

        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            String thisAddress = propertyAssessment.getAddress().toString();

            if (thisAddress.contains(inputAddress.toUpperCase())) {
                addressAssessments.addPropertyAssessment(propertyAssessment);
            }
        }
        return addressAssessments;
    }

    public Set<String> getClassSet() {
        return this.assessClassSet;
    }

    //shea add
    public Set<String> getNeighborhoodSet() {
        return this.neighborhoodSet;
    }

    //shea add
    public Set<String> getWardSet() {return this.wardSet;}

    //shea add
    public String[] getSortedWardList() {
        Set<String> wardSetCopy = getWardSet();
        Set<Integer> toConvert = new TreeSet<>();
        Iterator itr = wardSetCopy.iterator();
        itr.next();
        //Convert string set to integer set for sorting
        while (itr.hasNext()) {
            String s = (String) itr.next();
            String[] arr = s.split(" ");
            toConvert.add(Integer.parseInt(arr[1]));
        }
        //convert to sorted string array
        itr = toConvert.iterator();
        String[] toRet = new String[toConvert.size()];
        for (int i = 0; i < toRet.length; i++) {
            toRet[i] = "Ward " + itr.next();
        }
        return toRet;
    }

    public PropertyAssessments getAssessmentsByClass(String classInput) {
        PropertyAssessments classAssessments = new PropertyAssessments();

        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            String propertyClass = (propertyAssessment.getClassification()).toUpperCase();

            if (propertyClass.equals(classInput.toUpperCase() )) {
                classAssessments.addPropertyAssessment(propertyAssessment);
            }
        }
        return classAssessments;
    }

    public PropertyAssessments getAssessmentsByAccount(String accountNum) {
        PropertyAssessments assessmentAccounts = new PropertyAssessments();

        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            if (Integer.toString(propertyAssessment.getAccountNum()).startsWith(accountNum)){
                assessmentAccounts.addPropertyAssessment(propertyAssessment);
            }
        }
        return assessmentAccounts;
    }

    private void sortAssessments() {
        //if (sorted)
            // If already sorted, don't do anything
          //  return;

        //Sort the collection and set sorted flag to true if not already sorted

        Collections.sort(this.propertyAssessmentsList);
        this.sorted = true;
    }

    public PropertyAssessment getPropertyAssessment (int accountNum) throws ClassNotFoundException {
        // Return the property assessment with the matching account number

        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            if (propertyAssessment.getAccountNum() == accountNum) {
                return propertyAssessment;
            }
        }
        throw new ClassNotFoundException();
    }

    public int getMean() {
        long sum = 0;
        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            sum = sum + propertyAssessment.getValue();
        }
        return Math.round((int)((double)sum / this.propertyAssessmentsList.size()));
    }

    public long getMin() {
        sortAssessments();
        return this.propertyAssessmentsList.get(0).getValue();
    }

    public long getMedian() {
        sortAssessments();
        if (this.propertyAssessmentsList.size() % 2 == 0){
            long avg = (this.propertyAssessmentsList.get(this.propertyAssessmentsList.size()/2)).getValue() +
                    (this.propertyAssessmentsList.get(this.propertyAssessmentsList.size()/2+1)).getValue();
            return avg/2;
        }
        return (this.propertyAssessmentsList.get(this.propertyAssessmentsList.size()/2)).getValue();
    }

    public int getStDev() {
        double sum = 0;
        double mean = this.getMean();
        for (PropertyAssessment propertyAssessment : this.propertyAssessmentsList) {
            double val = propertyAssessment.getValue() - mean;
            sum = sum + val * val;
        }

        double result = sum / (double) this.propertyAssessmentsList.size();
        return (int) Math.sqrt(result);
    }

    public long getMax() {
        sortAssessments();
        return (this.propertyAssessmentsList.get(this.propertyAssessmentsList.size() - 1)).getValue();
    }

    public long getRange() {
        return this.getMax() - this.getMin();
    }

    public int getPopulation() {
        return this.propertyAssessmentsList.size();
    }

    public PropertyAssessment parseLineToAssessment(String line) throws NumberFormatException {
        String[] tokens = line.split(",");

        // get account number
        int accountNum = Integer.parseInt(tokens[0]);

        // get address
        String suite = "";
        if (!(tokens[1].length() < 1)) {
            suite = tokens[1];
        }
        String houseNum = "";
        if (!(tokens[2].length() < 1)) {
            houseNum = tokens[2];
        }
        String streetName = tokens[3];
        Address address = new Address(suite, houseNum, streetName);

        // get value and classification
        int assessedValue = Integer.parseInt(tokens[4]);
        String classification = tokens[5];

        // get neighbourhood
        int nbhoodID = 0;
        if (!(tokens[6].length() < 1)) {
            nbhoodID = Integer.parseInt(tokens[6]);
        }
        String nbhood = tokens[7];
        String ward = tokens[8];
        String garage = tokens[9];
        Neighbourhood neighbourhood = new Neighbourhood(nbhoodID, nbhood, ward);

        // get location
        double latitude = Double.parseDouble(tokens[10]);
        double longitude = Double.parseDouble(tokens[11]);
        Location location = new Location(latitude, longitude);

        // create propertyAssessment object with values
        return new PropertyAssessment(accountNum, address,
                assessedValue, classification, neighbourhood, garage, location);
    }

    public String toString() {
        NumberFormat moneyMaker = NumberFormat.getCurrencyInstance();
        // Printing the collection produces the following data
        int N = this.getPopulation();
        String str = "";
        if (N > 0) {
            str = str + "N = " + N;
            str = str + "\nMin = " + moneyMaker.format(this.getMin());
            str = str + "\nMax = " + moneyMaker.format(this.getMax());
            str = str + "\nRange = " + moneyMaker.format(this.getRange());
            str = str + "\nMean = " + moneyMaker.format(this.getMean());
            str = str + "\nStandard Deviation = " + moneyMaker.format(this.getStDev());
            str = str + "\nMedian = " + moneyMaker.format(this.getMedian());
        } else {
            str = str + "Descriptive Statistics not available\n";
        }
        return str;
    }
}
