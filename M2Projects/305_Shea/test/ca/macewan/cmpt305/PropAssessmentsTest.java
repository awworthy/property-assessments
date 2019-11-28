package ca.macewan.cmpt305;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropAssessmentsTest {
    private PropAssess p1;
    private PropAssess p2;
    private PropAssess p3;
    private PropAssess p4;
    private PropAssessments props1;
    private PropAssessments props2;
    private PropAssessments props3;
    private PropAssessments props4;
    private Neighborhood n1;

    @BeforeEach
    void setUp() {
        p1 = new PropAssess(123111, new Address("29", "500", "Lessard Drive NW"),
                new Neighborhood(17382, "Lessard", "12"), 400000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        p2 = new PropAssess(9932, new Address("", "2406", "104 Street NW"),
                new Neighborhood(1323, "Ermineskin", "10"), 200000, "Residential",
                new Location(52.12312343, 115.211113), "N");
        p3 = new PropAssess(5231, new Address("", "11711", "35a Avenue"),
                new Neighborhood(500000, "Greenfield", "12"), 300000, "Residential",
                new Location(53.12312343, 114.3231231), "Y");
        p4 = new PropAssess(829283, new Address("42", "5216", "Patrick Street NW"),
                new Neighborhood(500423, "Yellowbird", "4"), 170000, "Residential",
                new Location(53.14442343, 114.3223421), "N");
        props1 = new PropAssessments();
        props1.addProp(p2);
        props1.addProp(p1);
        props1.addProp(p3);
        props2 = new PropAssessments();
        props3 = new PropAssessments();
        props4 = new PropAssessments();
        props3.addProp(p1);
        props4.addProp(p1);
        props4.addProp(p2);
        props4.addProp(p3);
        props4.addProp(p4);
        n1 = new Neighborhood(17382,"Lessard", "12");
    }

    @Test
    void length() {
        int expResult = 3;
        int result = props1.length();
        assertEquals(expResult, result);
    }

    @Test
    void getPropAssess() {
        int expResult = 9932;
        int result = props1.getPropAssess(0).getAcctNumber();
        assertEquals(result, expResult);
    }

    @Test
    void setStr() {
    }

    @Test
    void setInt() {
    }

    @Test
    void setPropAssess() {
        int expResult = 7777;
        PropAssess test = new PropAssess(7777, new Address("11b", "4051", "MacD Drive NW"),
                new Neighborhood(13, "Sunnyvale", "3"), 429000, "Non-Residential",
                new Location(12.34123123, 77.341231), "Yes");
        int result = test.getAcctNumber();
        assertEquals(result, expResult);
    }

    @Test
    void makeNewPropAssess() {
        int expResult = 9932;
        int result = props1.makeNewPropAssess(0).getAcctNumber();
        assertEquals(result, expResult);
    }

    @Test
    void addProp() {
        int expResult = 1;
        props2.addProp(p1);
        int result = props2.length();
        assertEquals(result, expResult);
    }

    @Test
    void getMin() {
        int expResult = 200000;
        int result = props1.getMin();
        assertEquals(expResult, result);
    }

    @Test
    void getMax() {
        int expResult = 400000;
        int result = props1.getMax();
        assertEquals(expResult, result);
    }

    @Test
    void getRange() {
        int expResult = 200000;
        int result = props1.getRange();
        assertEquals(expResult, result);
    }

    @Test
    void getSum() {
        double expResult = 900000;
        double result = props1.getSum();
        assertEquals(expResult, result);
    }

    @Test
    void getMean() {
        int expResult = 300000;
        int result = props1.getMean();
        assertEquals(expResult, result);
    }

    @Test
    void getSd() {
        int expResult = 81649;
        int result = props1.getSd();
        assertEquals(expResult, result);
    }

    @Test
    void getMedian() {
        int expResult = 300000;
        int result = props1.getMedian();

        int expResult2 = 250000;
        int result2 = props4.getMedian();
        assertEquals(expResult, result); // Test with array of odd length
        assertEquals(expResult2, result2); // Test with array of even length
    }

    @Test
    void propsSort() {
        PropAssessments test = new PropAssessments();
        test.addProp(p2);
        test.addProp(p3);
        test.addProp(p1);
        props1.propsSort();
        int expResult = 3, result = 0;
        for(int i = 0; i < test.length(); i++) {
            if(test.getPropAssess(i).equals(props1.getPropAssess(i))) {
                result++;
            }
        }
        assertEquals(expResult, result);
    }

    @Test
    void findPropertyById() {
        int expResult = 400000;
        PropAssess p = props1.findPropertyById("123111");
        int result = p.getValue();
        assertEquals(expResult, result);
    }

    @Test
    void testEquals() {
        // reflexive
        assertEquals(true, props1.equals(props1));

        // symmetric
        PropAssessments secondProps1 = new PropAssessments();
        secondProps1.addProp(p2);
        secondProps1.addProp(p1);
        secondProps1.addProp(p3);
        assertEquals(true, props1.equals(secondProps1));
        assertEquals(true, secondProps1.equals(props1));

        // transitive
        PropAssessments thirdProps1 = new PropAssessments();
        thirdProps1.addProp(p2);
        thirdProps1.addProp(p1);
        thirdProps1.addProp(p3);
        assertEquals(true, secondProps1.equals(thirdProps1));
        assertEquals(true, props1.equals(thirdProps1));

        // some false results
        assertEquals(false, props1.equals(props2));
    }

    @Test
    void testHashCode() {
        // reflexive
        assertEquals(true, props1.hashCode() == props1.hashCode());

        // symmetric
        PropAssessments secondProps1 = new PropAssessments();
        secondProps1.addProp(p2);
        secondProps1.addProp(p1);
        secondProps1.addProp(p3);
        assertEquals(true, props1.hashCode() == secondProps1.hashCode());
        assertEquals(true, secondProps1.hashCode() == props1.hashCode());

        // transitive
        PropAssessments thirdProps1 = new PropAssessments();
        thirdProps1.addProp(p2);
        thirdProps1.addProp(p1);
        thirdProps1.addProp(p3);
        assertEquals(true, secondProps1.hashCode() == thirdProps1.hashCode());
        assertEquals(true, props1.hashCode() == thirdProps1.hashCode());

        // some false results
        assertEquals(false, props1.hashCode() == props2.hashCode());
    }

    @Test
    void testToString() {
        String expResult = "List of Property Assessments ID's: 9932, 123111, 5231";
        String result = props1.toString();
        assertEquals(result, expResult);
    }
}