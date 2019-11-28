package ca.macewan.c305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyAssessmentTest {
    private PropertyAssessment prop1;
    private PropertyAssessment prop2;
    private PropertyAssessment prop1Copy;

    @BeforeEach
    void setUp() {
        prop1 = new PropertyAssessment(1000, new Address("200", "390", "Baseline Rd"),
                1200000, "Non Residential", new Neighbourhood(1, "Sherwood Park", "Ward 100"),
                "N", new Location(53.4690477806739,-113.674719091913));
        prop2 = new PropertyAssessment(50000, new Address("", "400", "RR 202"),
                600000, "Farmland", new Neighbourhood(12, "Spruce Grove", "Ward 89"),
                "Y", new Location(53.4690477806739,-113.674719071913));
        prop1Copy = new PropertyAssessment(1000, new Address("200", "390", "Baseline Rd"),
                1200000, "Non Residential", new Neighbourhood(1, "Sherwood Park", "Ward 100"),
                "N", new Location(53.4690477806739,-113.674719091913));
    }

    @Test
    void getAccountNum() {
        int result = 1000;
        assertEquals(result, prop1.getAccountNum());
    }

    @Test
    void getClassification() {
        String result = "Non Residential";
        assertEquals(result, prop1.getClassification());
    }

    @Test
    void getValue() {
        int result = 1200000;
        assertEquals(result, prop1.getValue());
    }

    @Test
    void testToString() {
        String result = "Account Number = 1000"
                + "\nAddress = 200 390 Baseline Rd"
                + "\nAssessed Value = $1200000"
                + "\nAssessment Class = Non Residential"
                + "\nNeighbourhood = Sherwood Park (Ward 100)"
                + "\nLocation = (53.4690477806739, -113.674719091913)";
        assertEquals(result, prop1.toString());
    }

    @Test
    void testEquals() {
        assertEquals(prop1, prop1);

        assertEquals(prop1, prop1Copy);

        assertNotEquals(prop1, prop2);
    }

    @Test
    void compareTo() {
        int result = prop1.compareTo(prop2);
        assertTrue(result > 0);
        int copyResult = prop1.compareTo(prop1Copy);
        assertEquals(0, copyResult);
    }

    @Test
    void getNeighbourhood() {
        Neighbourhood result = new Neighbourhood(1, "Sherwood Park", "Ward 100");
        assertEquals(result, prop1.getNeighbourhood());
    }
}