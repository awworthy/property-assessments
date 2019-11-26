package ca.dakota.cmpt305;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Main class number two with the following behaviour:
 * 1) Prompts user for csv filename
 * 2) prompts user for assessment class
 * 3) displays descriptive statistics for the assessment class
 *
 * @author Dakota Doolaege
 */
public class Lab3Main {
    public static void main(String[] args) throws IOException {
        PropertyAssessments assessments = new PropertyAssessments(new Hashtable<Integer, PropertyAssessment>());
        String file = getString("CSV filename [press enter for default data]: ", "Property_Assessment_Data_2019.csv");
        assessments.getPropertyAssessments(file);

        String assessmentClass = getString("Assessment class: ", "residential");
        PropertyAssessments classAssessments = assessments.getPropertyAssessmentsClass(assessmentClass);

        System.out.println("Statistics (assessment class = "+ assessmentClass+")");
        System.out.println(classAssessments.getPropertyStatistics());
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

