package ca.dakota.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighbourhoodTest {
    private Neighbourhood neighbourhood1;

    @BeforeEach
    void setUp() {
        neighbourhood1 = new Neighbourhood("10", "Hamptons", "Ward 3");
    }

    @Test
    void getNeighbourhoodId() {
        String expResult = "10";
        String result = neighbourhood1.getNeighbourhoodId();
        assertEquals(expResult, result);
    }

    @Test
    void getNeighbourhood() {
        String expResult = "Hamptons";
        String result = neighbourhood1.getNeighbourhood();
        assertEquals(expResult, result);

    }

    @Test
    void getWard() {
        String expResult = "Ward 3";
        String result = neighbourhood1.getWard();
        assertEquals(expResult, result);
    }

    @Test
    void testToString() {
        String expResult = "Hamptons";
        String result = neighbourhood1.toString();
        assertEquals(expResult, result);
    }
}