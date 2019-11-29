package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location l1;
    private Location l2;

    @BeforeEach
    void setUp() {
        l1 = new Location(23.12312, 343.212312);
        l2 = new Location(224.12312, 43.212312);
    }

    @Test
    void latRangeCheck() {
        assertTrue(l1.latRangeCheck());
        assertFalse(l2.latRangeCheck());
    }

    @Test
    void longRangeCheck() {
        assertFalse(l1.longRangeCheck());
        assertTrue(l2.longRangeCheck());
    }

    @Test
    void getLatitude() {
        double expResult = 23.12312;
        double result = l1.getLatitude();
        assertEquals(expResult, result);
    }

    @Test
    void getLongitude() {
        double expResult = 343.212312;
        double result = l1.getLongitude();
        assertEquals(expResult, result);
    }

    @Test
    void testToString() {
    }

    @Test
    void equals() {
        // reflexive
        assertEquals(true, l1.equals(l1));

        // symmetric
        Location secondL1 = new Location(23.12312, 343.212312);
        assertEquals(true, l1.equals(secondL1));
        assertEquals(true, secondL1.equals(l1));

        // transitive
        Location thirdL1 = new Location(23.12312, 343.212312);
        assertEquals(true, secondL1.equals(thirdL1));
        assertEquals(true, l1.equals(thirdL1));

        // some false results
        assertEquals(false, l1.equals(l2));

    }

    @Test
    void testHashCode() {
    }
}