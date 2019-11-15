package ca.dakota.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentsTest {
    PropertyAssessments demoAssessments;
    private PropertyAssessment assessment;
    private Address address;
    private Location location;
    private Neighbourhood neighbourhood;

    @BeforeEach
    void setUp() {
        address = new Address("1", "1", "Main Street");
        location = new Location(10.0,-10.0);
        neighbourhood = new Neighbourhood("10", "Hamptons", "Ward 3");
        assessment = new PropertyAssessment(1, 1000,"Residential",
                address, neighbourhood, location,"123","123", "n" );
        demoAssessments = new PropertyAssessments(new Hashtable<Integer, PropertyAssessment>());
    }

    @Test
    void getPropertyAssessments() throws IOException {
        String expResult = "propertyAssessment class with 400541 properties";
        demoAssessments.getPropertyAssessments("Property_Assessment_Data_2019.csv");
        String result = demoAssessments.toString();
        assertEquals(expResult, result);
    }

    @Test
    void getPropertyAssessmentsNeighbourhood() throws IOException {
        String expResult = "propertyAssessment class with 11948 properties";
        demoAssessments.getPropertyAssessments("Property_Assessment_Data_2019.csv");
        PropertyAssessments neighbourhoodAssessments = demoAssessments.getPropertyAssessmentsNeighbourhood("Oliver");
        String result = neighbourhoodAssessments.toString();
        assertEquals(expResult, result);
    }

    @Test
    void getPropertyAssessmentsClass() throws IOException{
        String expResult = "propertyAssessment class with 373638 properties";
        demoAssessments.getPropertyAssessments("Property_Assessment_Data_2019.csv");
        PropertyAssessments classAssessments = demoAssessments.getPropertyAssessmentsClass("Residential");
        String result = classAssessments.toString();
        assertEquals(expResult, result);
    }

    @Test
    void getAccount() throws IOException {
        String expResult = "Account number = 10744008" +
                "\nAddress = 10712 67 AVENUE NW" +
                "\nAssessed value = $576000" +
                "\nAssessment class = Residential" +
                "\nNeighbourhood = ALLENDALE (Ward 8)" +
                "\nLocation = (53.5041708655698, -113.508268226739)\n";
        demoAssessments.getPropertyAssessments("Property_Assessment_Data_2019.csv");
        String result = demoAssessments.getAccount(10744008).toString();
        assertEquals(expResult, result);
    }
}