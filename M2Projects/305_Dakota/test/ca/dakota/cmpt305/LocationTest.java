package ca.dakota.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location1, location2;
    @BeforeEach
    void setUp() {
        location1 = new Location(10.0,-10.0);
        location2 = new Location(53.6308420714181,-113.428512065557);
    }

    @Test
    void getLatitude() {
       double expResult = 10.0;
       double result = location1.getLatitude();
       assertEquals(expResult, result);

    }

    @Test
    void getLongitude() {
        double expResult = -10.0;
        double result = location1.getLongitude();
        assertEquals(expResult, result);
    }

    @Test
    void testToString() {
        String expResult = "(10.0, -10.0)";
        String result = location1.toString();
        assertEquals(expResult, result);
    }
}