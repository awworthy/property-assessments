package ca.macewan.c305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location1;
    private Location location2;
    private Location location1Copy1;
    private Location location1Copy2;

    @BeforeEach
    void setUp() {
        location1 = new Location(53.4087290046276, -113.586273013139);
        location2 = new Location(90, 180);
        location1Copy1 = new Location(53.4087290046276, -113.586273013139);
        location1Copy2 = new Location(53.4087290046276, -113.586273013139);
    }
    @Test
    void getLatitude() {
        double expectRes = 53.4087290046276;
        assertEquals(expectRes, location1.getLatitude());
    }

    @Test
    void getLongitude() {
        double expectRes = -113.586273013139;
        assertEquals(expectRes, location1.getLongitude());
    }

    @Test
    void testToString() {
        String expectRes = "(53.4087290046276, -113.586273013139)";
        assertEquals(expectRes, location1.toString());
    }

    @Test
    // Because .equals() uses hashCode, that was not explicitly tested
    void testEquals() {
        assertEquals(true, location1.equals(location1));

        assertEquals(true, location1.equals(location1Copy1));
        assertEquals(true, location1Copy1.equals(location1));

        assertEquals(true, location1.equals(location1Copy2));
        assertEquals(true, location1Copy1.equals(location1Copy2));

        // check false
        assertEquals(false, location1.equals(location2));
        assertEquals(false, location1.equals("not a location"));

        // this is very similar but different, could get rounded
        assertEquals(false, location1.equals(location2 = new Location(53.4087290046276, -113.586273013138)));
    }
}