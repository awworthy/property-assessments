package ca.macewan.c305;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTest {
    Business b1;
    Business b2;

    @BeforeEach
    void setUp() {
        b1 = new Business(1234, "General Contractor", "Home Depot",
                new Address("", "1234", "Murray Street"), new Neighbourhood(123,
                "Webber Greens", "4"), new Location(53.123121, 100.123122));
        b2 = new Business(5678, "Restaurant", "McDonald's",
                new Address("12", "500", "16A Avenue"), new Neighbourhood(412,
                "Greenfield", "5"), new Location(53.4124112, 101.231231));
    }


    @Test
    void getLicenseNum() {
        int expResult = 1234;
        int result = b1.getLicense();
        assertEquals(expResult, result);
    }

    @Test
    void getCategory() {
        String expResult = "General Contractor";
        String result = b1.getCategory();
        assertEquals(expResult, result);
    }

    @Test
    void getTradeName() {
        String expResult = "Home Depot";
        String result = b1.getTradeName();
        assertEquals(expResult, result);
    }

    @Test
    void getAddress() {
        Address expResult = new Address("", "1234", "Murray Street");
        Address result = b1.getAddress();
        assertEquals(expResult, result);
    }

    @Test
    void getNeighborhood() {
        Neighbourhood expResult = new Neighbourhood(123, "Webber Greens", "4");
        Neighbourhood result = b1.getNeighborhood();
        assertEquals(expResult, result);
    }

    @Test
    void getLoc() {
        Location expResult = new Location(53.123121, 100.123122);
        Location result = b1.getLoc();
        assertEquals(expResult, result);
    }

    @Test
    void testToString() {
        String expResult = "Home Depot";
        String result = b1.toString();
        assertEquals(expResult, result);
    }

    @Test
    void testEquals() {
        // reflexive
        assertEquals(true, b1.equals(b1));

        // symmetric
        Business secondb1 = new Business(1234, "General Contractor", "Home Depot",
                new Address("", "1234", "Murray Street"), new Neighbourhood(123,
                "Webber Greens", "4"), new Location(53.123121, 100.123122));
        assertEquals(true, b1.equals(secondb1));
        assertEquals(true, secondb1.equals(b1));

        // transitive
        Business thirdb1 = new Business(1234, "General Contractor", "Home Depot",
                new Address("", "1234", "Murray Street"), new Neighbourhood(123,
                "Webber Greens", "4"), new Location(53.123121, 100.123122));
        assertEquals(true, secondb1.equals(thirdb1));
        assertEquals(true, b1.equals(thirdb1));

        // some false results
        assertEquals(false, b1.equals(b2));
    }

    @Test
    void testHashCode() {
        int expResult = 5678;
        int result = b2.hashCode();
        assertEquals(expResult, result);
    }
}