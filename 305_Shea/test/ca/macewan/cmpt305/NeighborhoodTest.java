package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighborhoodTest {
    Neighborhood n1;
    Neighborhood n2;

    @BeforeEach
    void setUp() {
        n1 = new Neighborhood(1234,"Lessard", "11");
        n2 = new Neighborhood(77, "Greenfield", "8");
    }

    @Test
    void getId() {
        int expResult = 1234;
        int result = n1.getId();
        assertEquals(result, expResult);
    }

    @Test
    void getName() {
        String expResult = "Lessard";
        String result = n1.getName();
        assertEquals(result,expResult);
    }

    @Test
    void getWard() {
        String expResult = "11";
        String result = n1.getWard();
        assertEquals(result, expResult);
    }

    @Test
    void testToString() {
        String expResult = "ID: 1234 Name: Lessard Ward: 11";
        String result = n1.toString();
        assertEquals(result, expResult);
    }

    @Test
    void equals() {
        // reflexive
        assertEquals(true, n1.equals(n1));

        // symmetric
        Neighborhood secondn1 = new Neighborhood(1234,"Lessard", "11");
        assertEquals(true, n1.equals(secondn1));
        assertEquals(true, secondn1.equals(n1));

        // transitive
        Neighborhood thirdn1 = new Neighborhood(1234,"Lessard", "11");
        assertEquals(true, secondn1.equals(thirdn1));
        assertEquals(true, n1.equals(thirdn1));

        // some false results
        assertEquals(false, n1.equals(n2));
    }

    @Test
    void testHashCode() {
        // reflexive
        assertTrue(n1.hashCode() == n1.hashCode());

        Neighborhood secondn1 = new Neighborhood(1234,"Lessard", "11");
        assertTrue(n1.hashCode() == secondn1.hashCode());
        assertTrue(secondn1.hashCode() == n1.hashCode());

        // transitive
        Neighborhood thirdn1 = new Neighborhood(1234,"Lessard", "11");
        assertTrue(secondn1.hashCode() == thirdn1.hashCode());
        assertTrue(n1.hashCode() == thirdn1.hashCode());

        // some false results
        System.out.println(n1.toString() + " " + n1.hashCode());
        System.out.println(n2.toString() + " " + n2.hashCode());
        assertFalse(n1.hashCode() == n2.hashCode());
    }
}