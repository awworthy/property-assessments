package ca.macewan.c305;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *  Lab3Main.java
 *
 *  CMPT 305:
 *      Fall 2019
 *  Assignment 1:
 *      Milestone 1
 *  Name:
 *      Alex Worthy
 *  ID:
 *      1742554
 */

public class Lab3Main {
    public static void main(String[] args){

        System.out.print("\nCSV filename [Press enter for default data]: ");
        Scanner input = new Scanner(System.in);


        String filename = input.nextLine();

        if (!filename.endsWith(".csv")) {
            filename = "Property_Assessment_Data__Current_Calendar_Year_.csv";
        }
        try {
            PropertyAssessments propertyAssessments = makePropertyAssessments(filename);

            System.out.print("Assessment class: ");

            input = new Scanner(System.in);
            String classification = input.nextLine();
            PropertyAssessments classificationAssessments = propertyAssessments.getAssessmentsByClass(classification);
            System.out.println("Statistics (assessment class = " + classification + ")");
            System.out.println(classificationAssessments);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PropertyAssessments makePropertyAssessments(String filename) throws IOException {
        Scanner file = new Scanner(Paths.get(filename));
        int n = getLength(file);

        // re-read file as scanner needs to point at beginning again
        file = new Scanner(Paths.get(filename));
        if (file.hasNextLine()) { // Skip header
            file.nextLine();
        }

        PropertyAssessments propertyAssessments = new PropertyAssessments();

        for (int i = 0 ; i < n && file.hasNextLine() ; i++){
            // iterate through each line and make a Property Assessment from each
            String currentLine = file.nextLine();
            PropertyAssessment propertyAssessment = propertyAssessments.parseLineToAssessment(currentLine);

            // add propertyAssessment to List
            propertyAssessments.addPropertyAssessment(propertyAssessment);
        }

        return propertyAssessments;
    }

    private static int getLength(Scanner file){
        int n = 0;
        if (file.hasNextLine()) {
            file.nextLine();
        }
        while (file.hasNextLine()) {
            file.nextLine();
            n++;
        }
        return n;
    }
}
