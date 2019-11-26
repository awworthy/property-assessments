package ca.macewan.c305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourhoodTest {
    Neighbourhood nbhood1;
    Neighbourhood nbhood2;
    Neighbourhood nbhood1Copy;

    @BeforeEach
    void setUp() {
        nbhood1 = new Neighbourhood(25000, "Webber Greens", "Ward 1");
        nbhood2 = new Neighbourhood(1421443, "Claireview", "Ward 100");
        nbhood1Copy = new Neighbourhood(25000, "Webber Greens", "Ward 1");
    }

    @Test
    void getID() {
        int expectRes = 25000;
        assertEquals(expectRes, nbhood1.getID());
    }

    @Test
    void getNeighbourhood() {
        String nbhood = "Webber Greens";
        assertEquals(nbhood, nbhood1.getNeighbourhood());
    }

    @Test
    void getWard() {
        String ward = "Ward 1";
        assertEquals(ward, nbhood1.getWard());
    }

    @Test
    void testToString() {
        String result = "Webber Greens (Ward 1)";
        assertEquals(result, nbhood1.toString());
    }

    @Test
    void testEquals() {
        assertEquals(true, nbhood1.equals(nbhood1));

        assertEquals(true, nbhood1.equals(nbhood1Copy));

        assertEquals(false, nbhood1.equals(nbhood2));
        assertEquals(false, nbhood1.equals("not a neighbourhood"));
    }
}