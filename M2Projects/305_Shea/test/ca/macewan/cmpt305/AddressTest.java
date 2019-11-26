package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    private Address adr1;
    private Address adr2;

    @BeforeEach
    void setUp() {
        adr1 = new Address("12", "1", "Terwilleger Dr.");
        adr2 = new Address("B", "1", "117 St.");
    }

    @Test
    void getSuite() {
        String expResult = "12";
        String result = adr1.getSuite();
        assertEquals(expResult, result);
    }

    @Test
    void getHsNo() {
        String expResult = "1";
        String result = adr1.getHsNo();
        assertEquals(expResult, result);
    }

    @Test
    void getStrNm() {
        String expResult = "Terwilleger Dr.";
        String result = adr1.getStrNm();
        assertEquals(expResult, result);
    }

    @Test
    void testToString() {
        String expResult = "12 1 Terwilleger Dr.";
        String result = adr1.toString();
        assertEquals(result, expResult);
    }

    @Test
    void equals() {
        // reflexive
        assertEquals(true, adr1.equals(adr1));

        // symmetric
        Address secondadr1 = new Address("12", "1", "Terwilleger Dr.");
        assertEquals(true, adr1.equals(secondadr1));
        assertEquals(true, secondadr1.equals(adr1));

        // transitive
        Address thirdadr1 = new Address("12", "1", "Terwilleger Dr.");
        assertEquals(true, secondadr1.equals(thirdadr1));
        assertEquals(true, adr1.equals(thirdadr1));

        // some false results
        assertEquals(false, adr1.equals(adr2));
    }

    @Test
    void testHashCode() {
        // reflexive
        assertTrue(adr1.hashCode() == adr1.hashCode());

        Address secondadr1 = new Address("12", "1", "Terwilleger Dr.");
        assertTrue(adr1.hashCode() == secondadr1.hashCode());
        assertTrue(secondadr1.hashCode() == adr1.hashCode());

        // transitive
        Address thirdadr1 = new Address("12", "1", "Terwilleger Dr.");
        assertTrue(secondadr1.hashCode() == thirdadr1.hashCode());
        assertTrue(adr1.hashCode() == thirdadr1.hashCode());

        // some false results
        assertFalse(adr1.hashCode() == adr2.hashCode());
    }
}