package ca.dakota.cmpt305;

/**
 * Address Object
 *
 * Stores the various attributes of a property address
 *
 * @author Dakota Doolaege
 */
public class Address {
    private String suite;
    private String houseNumber;
    private String streetName;

    /**
     * Constructor
     *
     * @param suite the suite in the building such as apartment number
     * @param houseNumber the number for the building
     * @param streetName street name that the building is on
     */
    public Address(String suite, String houseNumber, String streetName){
        this.suite = suite;
        this.houseNumber = houseNumber;
        this.streetName = streetName;
    }

    /**
     * Gets the suite number for an address
     * @return suite number
     */
    public String getSuite(){
        return suite;
    }

    /**
     * Gets the house number for the address
     * @return house number for the address
     */
    public String getHouseNumber(){
        return houseNumber;
    }

    /**
     * Gets the street name for the address
     * @return street name
     */
    public String getStreetName(){
        return streetName;
    }

    public String toString(){
        return houseNumber +" "+ streetName;
    }
}
