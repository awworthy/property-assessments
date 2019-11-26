package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropAssessTest {
    PropAssess p1;
    PropAssess p2;
    @BeforeEach
    void setUp() {
        p1 = new PropAssess(123111, new Address("29", "500", "Lessard Drive NW"),
                new Neighborhood(17382, "Lessard", "12"), 400000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        p2 = new PropAssess(9932, new Address("", "2406", "104 Street NW"),
                new Neighborhood(1323, "Ermineskin", "10"), 200000, "Residential",
                new Location(52.12312343, 115.211113), "N");
    }

    @Test
    void getAcctNumber() {
        int expResult = 123111;
        int result = p1.getAcctNumber();
        assertEquals(expResult, result);
    }

    @Test
    void getAddress() {
        Address expResult = new Address("29", "500", "Lessard Drive NW");
        Address result = p1.getAddress();
        assertEquals(result, expResult);
    }

    @Test
    void getNeighborhood() {
        Neighborhood expResult = new Neighborhood(17382, "Lessard", "12");
        Neighborhood result = p1.getNeighborhood();
        assertEquals(result, expResult);
    }

    @Test
    void getValue() {
        int expResult = 400000;
        int result = p1.getValue();
        assertEquals(result, expResult);
    }

    @Test
    void getCls() {
        String expResult = "Residential";
        String result = p1.getCls();
        assertEquals(result, expResult);
    }

    @Test
    void getLocation() {
        Location expResult = new Location(53.12312343, 114.3231231);
        Location result = p1.getLocation();
        assertEquals(result, expResult);
    }

    @Test
    void isHasGarage() {
        String expResult = "Y";
        String result = p1.getGarage();
        assertEquals(result, expResult);
    }

    @Test
    void testToString() {
        String expResult = "Property Assessment for Property ID: 123111";
        String result = p1.toString();
        assertEquals(result, expResult);
    }

    @Test
    void equals() {
        // reflexive
        assertEquals(true, p1.equals(p1));

        // symmetric
        PropAssess secondp1 = new PropAssess(123111, new Address("29", "500", "Lessard Drive NW"),
                new Neighborhood(17382, "Lesssard", "12"), 400000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        assertEquals(true, p1.equals(secondp1));
        assertEquals(true, secondp1.equals(p1));

        // transitive
        PropAssess thirdp1 = new PropAssess(123111, new Address("29", "500", "Lessard Drive NW"),
                new Neighborhood(17382, "Lesssard", "12"), 400000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        assertEquals(true, secondp1.equals(thirdp1));
        assertEquals(true, p1.equals(thirdp1));

        // some false results
        assertEquals(false, p1.equals(p2));
    }

    @Test
    void testHashCode() {
        // reflexive
        assertTrue(p1.hashCode() == p1.hashCode());

        // symmetric
        PropAssess secondp1 = new PropAssess(123111, new Address("29", "500", "Lessard Drive NW"),
                new Neighborhood(17382, "Lesssard", "12"), 400000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        assertTrue(p1.hashCode() == secondp1.hashCode());
        assertTrue(secondp1.hashCode() == p1.hashCode());

        // transitive
        PropAssess thirdp1 = new PropAssess(123111, new Address("29", "500", "Lessard Drive NW"),
                new Neighborhood(17382, "Lesssard", "12"), 400000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        assertTrue(secondp1.hashCode() == thirdp1.hashCode());
        assertTrue(p1.hashCode() == thirdp1.hashCode());

        // some false results
        assertFalse(p1.hashCode() == p2.hashCode());
    }

    @Test
    void compareTo() {
    }
}