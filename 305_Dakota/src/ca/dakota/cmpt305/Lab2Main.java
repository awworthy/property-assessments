package ca.dakota.cmpt305;

import java.io.IOException;
import java.util.*;

/**
 * Main class number one with the following behaviour:
 * 1) prompts user for csv file name of assessment data
 * 2) displays the descriptive stats for the assessment data
 * 3) prompts user for account number
 * 4) displays information contained in assessment data for that property
 * 5) prompts user for neighbourhood
 * 6) displays descriptive statistics for that neighbourhood
 *
 * @author Dakota Doolaege
 */
public class Lab2Main {
    public static void main(String[] args) throws IOException {
        PropertyAssessments assessments = new PropertyAssessments(new Hashtable<Integer, PropertyAssessment>());

        String file = getString("CSV filename [press enter for default data]: ", "Property_Assessment_Data_2019.csv");
        assessments.getPropertyAssessments(file);
        System.out.println("Descriptive statistics of all property assessments");
        System.out.println(assessments.getPropertyStatistics());

        Integer account = Integer.valueOf(getString("Find a property assessment by account number:", "10006474"));
        System.out.println(assessments.getAccount(account));

        String neighbourhood = getString("Neighbourhood: ", "Webber Greens");
        PropertyAssessments neighbourhoodAssessments = assessments.getPropertyAssessmentsNeighbourhood(neighbourhood);
        System.out.println("Statistics (neighbourhood = "+neighbourhood+")");
        System.out.println(neighbourhoodAssessments.getPropertyStatistics());
    }

    /**
     * Prompts user for info then reads it in and returns it
     * @param prompt to send user
     * @param defaultResult default result if user enters empty string
     * @return string response
     */
    private static String getString(String prompt, String defaultResult) {
        System.out.print(prompt);
        Scanner console = new Scanner(System.in);
        String input = console.nextLine();
        if (input.equals("") && !defaultResult.equals("")) {
            input = defaultResult;
        }
        return input;
    }
}
