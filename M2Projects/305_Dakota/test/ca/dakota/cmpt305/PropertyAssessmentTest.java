package ca.dakota.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentTest {
    private PropertyAssessment assessment,assessment2;
    private Address address;
    private Location location;
    private Neighbourhood neighbourhood;
    @BeforeEach
    void setUp() {
        address = new Address("1", "1", "Main Street");
        location = new Location(10.0,-10.0);
        neighbourhood = new Neighbourhood("10", "Hamptons", "Ward 3");
        assessment = new PropertyAssessment(1, 1000,"Residential", address, neighbourhood, location, "113", "123", "n" );
        assessment2 = new PropertyAssessment(1, 1000,"Residential", address, neighbourhood, location,"113", "123", "n" );
    }

    @Test
    void getAccountNumber() {
        int expResult = 1;
        int result = assessment.getAccountNumber();
        assertEquals(expResult, result);
    }

    @Test
    void getAssessedValue() {
        int expResult = 1000;
        int result = assessment.getAssessedValue();
        assertEquals(expResult, result);
    }

    @Test
    void getAssessmentClass() {
        String expResult = "Residential";
        String result = assessment.getAssessmentClass();
        assertEquals(expResult, result);
    }

    @Test
    void getAddress() {
        String expResult = "1 Main Street";
        String result = assessment.getAddress().toString();
        assertEquals(expResult, result);
    }

    @Test
    void getNeighbourhood() {
        String expResult = "Hamptons";
        String result = assessment.getNeighbourhood().toString();
        assertEquals(expResult, result);
    }

    @Test
    void getLocation() {
        String expResult = "(10.0, -10.0)";
        String result = assessment.getLocation().toString();
        assertEquals(expResult, result);
    }

    @Test
    void getGarage() {
        String expResult = "n";
        String result = assessment.getGarage();
        assertEquals(expResult, result);
    }

    @Test
    void testToString() {
        String expResult = "Account number = 1" +
                "\nAddress = 1 Main Street" +
                "\nAssessed value = $1000" +
                "\nAssessment class = Residential" +
                "\nNeighbourhood = Hamptons (Ward 3)" +
                "\nLocation = (10.0, -10.0)" + "\n";
        String result = assessment.toString();
        assertEquals(expResult, result);
    }

    @Test
    void testEquals() {
        assertEquals(assessment,assessment2);
    }

    @Test
    void testHashCode() {
    }
}