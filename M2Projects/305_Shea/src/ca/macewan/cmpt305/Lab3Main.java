package ca.macewan.cmpt305;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.lang.*;

public class Lab3Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("CSV filename [press enter for default data]: ");// Prompt user for input
        String data = input.nextLine();  // Read user input

        PropAssessments props = readFile(data);
        System.out.print("Assessment class: "); // Separate Lab2Main.java and Lab3Main.java
        String assessClass = input.nextLine(); // Read user input
        System.out.println("Statistics (assessment class = " + assessClass);
        printClassAssess(props, assessClass);
    }

    private static PropAssessments readFile(String fileName)  {
        PropAssessments propAssessments = new PropAssessments(); // create instance of PropAssess
        Path pathToFile = Paths.get(fileName); // form path string to fileName

        // create an instance of BufferedReader using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String lineRead = br.readLine();
            lineRead = br.readLine();

            // loop until all lines are read
            while (lineRead != null) {
                String[] specs = lineRead.split(","); // Areate array of strings from each line of file
                // Create property
                PropAssess prop = propAssessments.setPropAssess(specs[0], specs[1], specs[2], specs[3], specs[4],
                        specs[5], specs[6], specs[7], specs[8], specs[9], specs[10], specs[11]);
                propAssessments.addProp(prop); // Add property to propAssessments
                lineRead = br.readLine(); // read next line before looping
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return propAssessments;
    }

    public static void printClassAssess(PropAssessments props, String assessClass) {
        PropAssessments classAssess = getClass(props, assessClass);
        printStats(classAssess);
    }

    public static PropAssessments getClass(PropAssessments classAssess, String assessClass) {
        PropAssessments neighborhoods = new PropAssessments();
        for(int i = 0; i < classAssess.length(); i++) { // increment through list and build new properties as matched names are found
            if (assessClass.toUpperCase().equals(classAssess.getPropAssess(i).getCls().toUpperCase())) {
                neighborhoods.addProp(classAssess.makeNewPropAssess(i));
            }
        }
        return neighborhoods;
    }

    public static void printStats(PropAssessments props){ // Can be in main class
        System.out.println("n = " + props.length());
        System.out.println("min = " + props.getMin());
        System.out.println("max = " + props.getMax());
        System.out.println("range = " + props.getRange());
        System.out.println("mean = " + props.getMean());
        System.out.println("sd = " + props.getSd());
        System.out.println("median = " + props.getMedian());
    }
}
