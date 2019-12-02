package ca.macewan.c305;

import java.util.List;

public class PieBracket {
    private float maximum;
    private float minimum;
    private float average;
    private List<Float> values;

    public PieBracket(float minimum, float maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    // method to add values to this bracket
    public void addValue(float value) {
        if (value < maximum && value > minimum)
            values.add(value);
        else
            System.out.println("Error adding value to Assessed Value Bracket");
    }

    public void getAverage() {

    }
}
