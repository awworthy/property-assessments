package ca.macewan.c305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address address1;
    private Address address2;
    private Address address1Copy1;
    private Address address1Copy2;

    @BeforeEach
    void setUp() {
        address1 = new Address("", "10", "ORCHID CRESCENT");
        address2 = new Address("42", "320", "bethel drive");
        address1Copy1 = new Address("", "10", "ORCHID CRESCENT");
        address1Copy2 = new Address("", "10", "ORCHID CRESCENT");
    }

    @Test
    void testGetSuite() {
        String expResult1 = "";
        String expResult2 = "42";
        String result1 = address1.getSuite();
        String result2 = address2.getSuite();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    @Test
    void testGetHouseNum() {
        int expResult1 = 10;
        int expResult2 = 320;
        String result1 = address1.getHouseNum();
        String result2 = address2.getHouseNum();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    @Test
    void testGetStreetName() {
        String expResult1 = "ORCHID CRESCENT";
        String expResult2 = "bethel drive";
        String result1 = address1.getStreetName();
        String result2 = address2.getStreetName();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    @Test
    void testToString() {
        String expResult1 = "10 ORCHID CRESCENT"; // Should not contain extra leading whitespace
        String expResult2 = "42 320 bethel drive";
        String result1 = address1.toString();
        String result2 = address2.toString();
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
    }

    @Test
    void testEquals() {
        //reflexive equality check
        assertEquals(true, address1.equals(address1));

        // symmetric equality check
        assertEquals(true, address1.equals(address1Copy1));
        assertEquals(true, address1Copy1.equals(address1));

        // transitive equality check -> x = y and y = z therefore x = z
        assertEquals(true, address1.equals(address1Copy2));
        assertEquals(true, address1Copy1.equals(address1Copy2));

        // check false results
        assertEquals(false, address1.equals("Not an address"));
        assertEquals(false, address1.equals(address2));
    }
}