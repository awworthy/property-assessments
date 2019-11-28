package ca.dakota.cmpt305;

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

/**
 * PropertyAssessments Object
 *
 * Stores property assessments by their account number
 *
 * @auther Dakota Doolaege
 */
class PropertyAssessments {
    public Hashtable<Integer, PropertyAssessment> assessments;

    /**
     * Constructor
     * @param assessments
     */
    PropertyAssessments(Hashtable<Integer, PropertyAssessment> assessments) {
        this.assessments = assessments;
    }

    /**
     * Adds a single assessment to the collection
     * @param assessment to be added
     */
    void addAssessment(PropertyAssessment assessment){
        this.assessments.put(assessment.getAccountNumber(),assessment);
    }

    //Getters

    /**
     * Reads in assessments from a csv file and adds them to the collection
     * @param file csv file to read property data from
     * NOTE: csv files for city of edmonton start with a description line that gets removed by this method
     * @throws IOException if the file does not exist or can't be read from
     */
    void getPropertyAssessments(String file) throws IOException {
        FileReader fin = new FileReader(file);
        Scanner src = new Scanner(fin);
        String line = src.nextLine(); //Removes the first line (Description)
        while (src.hasNextLine()) { //Reads the file line by line until there is no more lines
            line = src.nextLine();
            String[] tokens = line.split(",");//splits the line at commas into a array

            Location location = new Location(Double.parseDouble(tokens[10]), Double.parseDouble(tokens[11])); //creates a location object
            Neighbourhood neighbourhood = new Neighbourhood(tokens[6], tokens[7], tokens[8]); //creates a neighbourhood object
            Address address = new Address(tokens[1], tokens[2], tokens[3]); //creates a address object

            PropertyAssessment propertyAssessment = new PropertyAssessment(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[4]),
                    tokens[5], address, neighbourhood, location, tokens[10], tokens[11], tokens[9]); //creates a PropertyAssessment object
            this.assessments.put(propertyAssessment.getAccountNumber(), propertyAssessment); //adds it to the list
        }
    }

    /**
     * Gets all property assessments in a neighbourhood
     * @param neighbourhood to get the assessments for
     * @return collection of property assessments
     */
    PropertyAssessments getPropertyAssessmentsNeighbourhood(String neighbourhood){
        neighbourhood = neighbourhood.toUpperCase();
        PropertyAssessments neighbourhoodAssessments = new PropertyAssessments(new Hashtable<Integer,PropertyAssessment>());
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        while(accounts.hasMoreElements()){ //loops collection of property assessments
            account = accounts.nextElement();
            if(assessments.get(account).getNeighbourhood().toString().equals(neighbourhood)) { //if property neighbourhood matches the requested neighbourhood
                neighbourhoodAssessments.assessments.put(account, assessments.get(account)); //add to collection to be returned
            }
        }
        return neighbourhoodAssessments;
    }

    /**
     * Gets all property assessments in a class
     * @param assessClass to get the assessments for
     * @return collection of property assessments
     */
    PropertyAssessments getPropertyAssessmentsClass(String assessClass){
        assessClass = assessClass.substring(0,1).toUpperCase() + assessClass.substring(1); //Capitalizes class name
        PropertyAssessments classAssessments = new PropertyAssessments(new Hashtable<Integer,PropertyAssessment>());
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        while(accounts.hasMoreElements()){ //loops collection
            account = accounts.nextElement();
            if(assessments.get(account).getAssessmentClass().equals(assessClass)) { //if the property's class matches
                classAssessments.assessments.put(account, assessments.get(account));//add it to the sub collection
            }
        }
        return classAssessments;
    }

    /**
     * Gets the account for a specific property assessment in the collection
     * @param accountNum to get
     * @return PropertyAssessment object that matches the request
     */
    PropertyAssessment getAccount(Integer accountNum){
        return assessments.get(accountNum);
    }

    /**
     * Prints out all the properties in the collection
     */
    void printPropertyAssessments(){
        //show all properties in table
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        while(accounts.hasMoreElements()){
            account = accounts.nextElement();
            System.out.println(assessments.get(account));
        }
    }

    /**
     * Calculates and prints out the statistics for the collection
     */
    public String getPropertyStatistics() {
        Integer n = getAssessmentN();
         Integer min = getAssessmentMin();
         Integer max = getAssessmentMax();
         Integer mean = getMean();
         Integer sd = getAssessmentSd(mean,n);
         Integer median = getMedian();
        NumberFormat moneyMaker = NumberFormat.getCurrencyInstance();
        moneyMaker.setMaximumFractionDigits(0);
        return(
                "Statistics of Assessed Values:\n\n" +
                "Number of properties: " + n+
                "\nMin: "+ moneyMaker.format(min)+
                "\nMax: "+moneyMaker.format(max)+
                "\nRange: "+ moneyMaker.format (max-min)+
                "\nMean: "+moneyMaker.format(mean)+
                "\nMedian: "+moneyMaker.format(median)+
                "\nStandard deviation: "+ moneyMaker.format(sd) + "\n"
        );

    }

    //Statistical calculation functions

    /**
     * Gets the number of properties in the collection
     * @return integer value
     */
    private Integer getAssessmentN() {
        return this.assessments.size();
    }

    /**
     * Gets the lowest assessed value in the collection
     * @return integer value
     */
    private Integer getAssessmentMin(){
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        int min = assessments.get(accounts.nextElement()).getAssessedValue();
        while(accounts.hasMoreElements()){
            account = accounts.nextElement();
            if (assessments.get(account).getAssessedValue() < min){
                min = assessments.get(account).getAssessedValue();
            }
        }
        return min;
    }

    /**
     * Gets the highest value in the collection
     * @return integer value
     */
    private Integer getAssessmentMax(){
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        int max = assessments.get(accounts.nextElement()).getAssessedValue();
        while(accounts.hasMoreElements()){
            account = accounts.nextElement();
            if (assessments.get(account).getAssessedValue() > max){
                max = assessments.get(account).getAssessedValue();
            }
        }
        return max;
    }

    /**
     * Gets the mean value for the collection
     * @return integer value
     */
    private Integer getMean(){
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        double mean = 0;
        double temp;
        while(accounts.hasMoreElements()){
            account = accounts.nextElement();
            temp = assessments.get(account).getAssessedValue();
            mean = mean + temp;
        }
        return (int) Math.round(mean / assessments.size());
    }

    /**
     * Gets the standard deviation for the collection
     * @param mean of the collection
     * @param n number of values in the collection
     * @return integer value
     */
    private Integer getAssessmentSd(Integer mean, Integer n) {
        double sd = 0;
        double distance;

        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        while(accounts.hasMoreElements()){//Gets sum of distances^2 (distances = AssessedValue - mean)
            account = accounts.nextElement();
            distance = assessments.get(account).getAssessedValue() - mean;
            distance = distance * distance;
            sd = sd + distance;
        }
        sd = sd / n;//Divides by the number of properties
        sd = Math.sqrt(sd);//gets the square root
        return (int) Math.round(sd);// returns the rounded result
    }

    /**
     * Gets the median value for the collection
     * @return integer value
     */
    private Integer getMedian() {
        Enumeration<Integer> accounts = assessments.keys();
        Integer account;
        Integer median;
        List<Integer> values = new ArrayList<>();
        while(accounts.hasMoreElements()){//puts the assessed values into the list
            account = accounts.nextElement();
            values.add(assessments.get(account).getAssessedValue());
        }
        Collections.sort(values); // sorts the list
        median = values.get(values.size()/2); //returns the middle value
        return median;
    }

    public String toString(){
        return "propertyAssessment class with " + getAssessmentN() + " properties";
    }
}
