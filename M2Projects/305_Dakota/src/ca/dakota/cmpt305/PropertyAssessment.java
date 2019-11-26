package ca.dakota.cmpt305;

/**
 * PropertyAssessment Object
 *
 * Stores the various attributes of a property including it's address, location and neighbourhood
 *
 * @auther Dakota Doolaege
 */
public class PropertyAssessment {
    private int accountNumber;
    private int assessedValue;
    private String assessmentClass;
    private Address address;
    private Neighbourhood neighbourhood;
    private Location location;
    private String latitude;
    private String longitude;
    private String garage;

    /**
     * Constructor
     *
     * @param accountNumber unique number that the property can be identified by
     * @param assessedValue  of the latest assessment
     * @param assessmentClass of the property
     * @param address of the property (subclass)
     * @param neighbourhood the property is in (subclass)
     * @param location of the property (subclass)
     * @param garage tracks if garage exists or not
     */
    public PropertyAssessment(int accountNumber, int assessedValue, String assessmentClass, Address address, Neighbourhood neighbourhood, Location location, String latitude, String longitude, String garage){
        this.accountNumber = accountNumber;
        this.assessedValue = assessedValue;
        this.assessmentClass = assessmentClass;
        this.address = address;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.garage = garage;
    }

    /**
     * Gets the account number of the property
     * @return int value account number
     */
    public int getAccountNumber(){
        return accountNumber;
    }

    /**
     * Gets value of the property
     * @return int value
     */
    public int getAssessedValue(){
        return assessedValue;
    }

    /**
     * Gets the class of the assessment
     * @return string value class
     */
    public String getAssessmentClass(){
        return assessmentClass;
    }

    /**
     * Gets the address of the property
     * @return address object
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gets the neighbourhood of the property
     * @return neighbourhood object
     */
    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Gets the latitude of the property
     * @return latitude string
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude of the property
     * @return longitude string
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Gets the location of the property
     * @return location object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the garage status of the property
     * @return string value
     */
    public String getGarage() {
        return garage;
    }

    @Override
    public String toString() {
        return "Account number = "+ accountNumber +
                "\nAddress = " + address +
                "\nAssessed value = $"+ assessedValue +
                "\nAssessment class = "+ assessmentClass+
                "\nNeighbourhood = "+ neighbourhood+
                " (" + neighbourhood.getWard() + ")" +
                "\nLocation = "+ location + "\n";
    }


    /**
     * Compares if two properties are the same (if they have the same account number used by the city)
     * @param obj object to compare to
     * @return boolean if they are the same
     */
    public boolean equals(Object obj){
        if (!(obj instanceof PropertyAssessment))
            return false;
        PropertyAssessment otherProperty = (PropertyAssessment) obj;
        return this.accountNumber == otherProperty.accountNumber;
    }

    public int hashCode(){
        return Integer.hashCode(accountNumber);
    }


}
