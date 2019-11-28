package ca.macewan.c305;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *  Lab2Main.java
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

public class Lab2Main {
    public static void main(String[] args){

        System.out.print("\nCSV filename [Press enter for default data]: ");
        Scanner input = new Scanner(System.in);

        String filename = input.nextLine();

        if (!filename.endsWith(".csv")) {
            filename = "Property_Assessment_Data__Current_Calendar_Year_.csv";
        }
        try {
            PropertyAssessments propertyAssessments = makePropertyAssessments(filename);
            System.out.println("Descriptive statistics of all property assessments");

            // prints the descriptive statistics (used again for Neighbourhoods and Assessment Class)
            System.out.println(propertyAssessments);

            try {
                // Get input from user for which property assessment to search for:
                System.out.print("\nFind a property assessment by account number: ");
                input = new Scanner(System.in);
                int accountNum = input.nextInt();
                PropertyAssessment propertyAssessment = propertyAssessments.getPropertyAssessment(accountNum);
                System.out.println(propertyAssessment);
            } catch (ClassNotFoundException er) {
                System.out.println("Error: Property Assessment not found");
            }

            System.out.print("\nNeighbourhood: ");
            input = new Scanner(System.in);
            String neighbourhood = input.nextLine();
            PropertyAssessments neighbourhoodAssessments = propertyAssessments.getAssessmentsByNeighbourhood(neighbourhood);
            System.out.println("Statistics (neighbourhood = " + neighbourhood + ")");
            System.out.println(neighbourhoodAssessments);

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
