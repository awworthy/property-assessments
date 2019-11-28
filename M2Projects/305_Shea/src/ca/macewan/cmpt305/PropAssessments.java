package ca.macewan.cmpt305;

import java.util.*;

public class PropAssessments {
    private List<PropAssess> propAssessList;
    private boolean sorted;

    public PropAssessments(){
        this.propAssessList = new ArrayList<>();
        this.sorted = false;
    }

    public int length(){
        return this.propAssessList.size();
    }

    public PropAssess getPropAssess(int index) {
        return this.propAssessList.get(index);
    }

    public List<PropAssess> getPropAssessList() {
        return this.propAssessList;
    }

    public static String setStr(String val){ //helper method for setProp
        if ((val.length() > 0) && (val != " ")) {
            return val;
        }
        return "";
    }

    public static int setInt(String num){
        if (num.length() > 0) {
            return Integer.parseInt(num);
        }
        return 0;
    }

    public PropAssess setPropAssess(String acctNum, String suite, String hNum, String sName, String val, String cls,
                                    String nId, String nbhd, String ward, String grg, String lat, String lng) {
        Location loc = new Location(Double.parseDouble(lat), Double.parseDouble(lng));
        if (!loc.latRangeCheck()) {
            throw new IllegalArgumentException("Illegal range for latitude");
        }
        if (!loc.longRangeCheck()) {
            throw new IllegalArgumentException("Illegal range for latitude");
        }
        PropAssess propAssess = new PropAssess(Integer.parseInt(acctNum), new Address(setStr(suite), setStr(hNum),
                setStr(sName)), new Neighborhood(setInt(nId), setStr(nbhd), setStr(ward)), Integer.parseInt(val), cls,
                new Location(Double.parseDouble(lat), Double.parseDouble(lng)), grg);
        return propAssess;
    }

    public PropAssess makeNewPropAssess(int i) {
        int acctNum = this.getPropAssess(i).getAcctNumber();
        Address address = this.getPropAssess(i).getAddress();
        Neighborhood nbhd = this.getPropAssess(i).getNeighborhood();
        int value = this.getPropAssess(i).getValue();
        String cls = this.getPropAssess(i).getCls();
        Location loc = this.getPropAssess(i).getLocation();
        String garage = this.getPropAssess(i).getGarage();
        return new PropAssess(acctNum, address, nbhd, value, cls, loc, garage);
    }

    public void addProp(PropAssess prop) {
        this.propAssessList.add(prop);
    }

    public int getMin() { // takes sorted array of properties and returns lowest order value
        if(!this.sorted) {
            this.propsSort();
        }
        return this.getPropAssess(0).getValue();
    }

    public int getMax() { // takes sorted array of properties and returns highest order value
        if(!this.sorted) {
            this.propsSort();
        }
        return this.getPropAssess(this.length()-1).getValue();
    }

    public int getRange() { // returns the range of values of a properties array
        if(!this.sorted) {
            this.propsSort();
        }
        return this.getMax() - this.getMin();
    }

    public double getSum() {
        double sum = 0;
        for (int i = 0; i < this.length(); i++) { // iterate through array and add value to sum
            sum += this.getPropAssess(i).getValue();
        }
        return sum;
    }

    public int getMean() {
        double sum = this.getSum();
        double mean = sum/(double)this.length(); // calculates mean (sum/number of values)
        return (int)mean;
    }

    public int getSd() {
        int mean = this.getMean();
        double sum = 0, x;
        for (int i = 0; i < this.length(); i++) {
            x = this.getPropAssess(i).getValue() - mean;
            x *= x;
            sum += x;
        }
        double sd = sum/(double)this.length();
        return (int)Math.sqrt(sd);
    }

    public int getMedian() {
        if(!this.sorted) {
            this.propsSort();
        }
        if (this.length() % 2 != 0) { // checks if length of array is odd
            return this.getPropAssess(this.length()/2).getValue(); // returns middle value
        }
        else {
            int n1 = (this.length() / 2) - 1; // get first middle value
            int n2 = this.length() / 2; // get second middle value
            return (this.getPropAssess(n1).getValue() + this.getPropAssess(n2).getValue())/2;
        }
    }

    public void propsSort(){
        Collections.sort(this.propAssessList);
        this.sorted = true;
    }

    public PropAssess findPropertyById(String id) {
        //int acct = Integer.parseInt(id),
         int i;
        for(i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(Integer.toString(this.getPropAssess(i).getAcctNumber()).equals(id)) {
                return this.getPropAssess(i);
            }
        }
        if(i == this.length()) { // If whole list is traversed, no account has been found
            System.out.println("No account found\n");
        }
        return null;
    }

    public PropAssessments findPropertiesById(String id) {
        if(!this.sorted) {
            this.propsSort();
        }
        PropAssessments propsByID = new PropAssessments();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(Integer.toString(this.getPropAssess(i).getAcctNumber()).startsWith(id)) {
                propsByID.addProp(this.getPropAssess(i));
            }
        }
        return propsByID;
    }

    public PropAssessments findPropertiesByAddress(String address) {
        if(!this.sorted) {
            this.propsSort();
        }
        PropAssessments propsByAddress = new PropAssessments();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(this.getPropAssess(i).getAddress().toString().toLowerCase().startsWith(address.toLowerCase())) {
                propsByAddress.addProp(this.getPropAssess(i));
            }
        }
        return propsByAddress;
    }

    public PropAssessments findPropertiesByNeighborhood(String neighborhood) {
        if(!this.sorted) {
            this.propsSort();
        }
        PropAssessments propsByNeighborhood = new PropAssessments();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(this.getPropAssess(i).getNeighborhood().toString().toLowerCase().startsWith(neighborhood.toLowerCase())) {
                propsByNeighborhood.addProp(this.getPropAssess(i));
            }
        }
        return propsByNeighborhood;
    }

    public PropAssessments findPropertiesByClass(String cls) {
        if(!this.sorted) {
            this.propsSort();
        }
        PropAssessments propsByCls = new PropAssessments();
        for(int i = 0; i < this.length(); i++) { //parse through list until acct is found
            if(this.getPropAssess(i).getCls().toLowerCase().startsWith(cls.toLowerCase())) {
                propsByCls.addProp(this.getPropAssess(i));
            }
        }
        return propsByCls;
    }

    public ArrayList<String> getClasses(){
        Set<String> toPop = new HashSet<>();
        for(int i = 0; i < this.length(); i++){
            toPop.add(this.getPropAssess(i).getCls());
        }
        ArrayList<String> toRet = new ArrayList<>(toPop);
        return toRet;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PropAssessments) {
            PropAssessments otherPropAssessments = (PropAssessments) obj;
            if (this.length() == ((PropAssessments) obj).length()) {
                int count = 0;
                int i;
                for (i = 0; i < this.length(); i++) {
                    if (this.getPropAssess(i).getAcctNumber() == otherPropAssessments.getPropAssess(i).getAcctNumber()) {
                        count++;
                    }
                }
                return i == count;
            }
        }
        return false;
    }

    public int hashCode() {
        if (this.length() != 0) {
            int result = Integer.hashCode(this.getPropAssess(0).getAcctNumber());
            for(int i = 1; i < this.length(); i++) {
                result = result * 31 + Integer.hashCode(this.getPropAssess(i).getAcctNumber());
            }
            return result;
        }
        return 0;
    }

    public String toString() {
        String toRet = "List of Property Assessments ID's: ";
        for(int i = 0; i < this.length(); i ++) {
            toRet += propAssessList.get(i).getAcctNumber();
            if(this.length() - 1 != i){
                toRet += ", ";
            }
        }
        return toRet;
    }
}

