package ca.dakota.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address house1,house2,house3;
    @BeforeEach
    void setUp() {
        house1 = new Address("1", "1", "Main Street");
        house2 = new Address("1", "2", "Null");
        house3 = new Address("1", "2", "null");
    }

    @Test
    void getSuite() {
        String expResult = "1";
        String result = house1.getSuite();
        assertEquals(expResult,result);
    }

    @Test
    void getHouseNumber() {
        String expResult = "1";
        String result = house1.getHouseNumber();
        assertEquals(expResult,result);
    }

    @Test
    void getStreetName() {
        String expResult = "Main Street";
        String result = house1.getStreetName();
        assertEquals(expResult,result);

        expResult = "Null";
        result = house2.getStreetName();
        assertEquals(expResult,result);

        expResult = null;
        result = house3.getStreetName();
        assertNotEquals(expResult, result);
    }

    @Test
    void testToString() {
        String expResult = "1 Main Street";
        String result = house1.toString();
        assertEquals(expResult,result);
    }
}