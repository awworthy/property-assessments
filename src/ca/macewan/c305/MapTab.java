package ca.macewan.c305;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;

/**
 * Map class handles the functions related to the Map tab in the property assessments program
 *
 * @author Dakota Doolaege
 */
public class MapTab {
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    PropertyAssessments propertyAssessments;
    PropertyAssessments customCollection;
    NumberFormat moneyMaker = NumberFormat.getCurrencyInstance();

    /**
     * Creates a new BorderPane that contains the map and a side menu of options
     *
     * @param propertyAssessments
     * @return borderPane that contains all the content for the map tab
     */
    public BorderPane start(PropertyAssessments propertyAssessments) throws IOException {
        moneyMaker.setMaximumFractionDigits(0);
        this.propertyAssessments = propertyAssessments;

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));

        VBox searchBox = createSearch();
        VBox mapBox = createMap();

        borderPane.setLeft(searchBox);
        borderPane.setCenter(mapBox);

        return borderPane;
    }

    /**
     * Creates content for the side bar
     *
     * @return The side options panel for controlling the map
     */
    private VBox createSearch() throws IOException {
        VBox vbox = new VBox();
        final Label searchLabel =  new Label("Map Options");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        TextArea textArea = new TextArea();
        textArea.setMaxWidth(200);

        //shea addition
        Map<String, List<Location>> neighborhoodBounds = getCoordinates("Neighbourhood_20Boundaries_20_Tableau_.csv");
        //for testing
        //Map<String, List<Location>> neighborhoodBounds = getCoordinates("nBounds.csv");
        Map<String, List<Location>> wardBounds = getCoordinates("Municipal_20Ward_20Boundaries_20_Tableau_.csv");
        //Map<String, List<Location>> wardBounds = getCoordinates("wBound.csv");

        final Label neighbourhoodLabel = new Label("Select neighbourhood");
        Set<String> neighbourhoodSet = propertyAssessments.getNeighborhoodSet();
        ObservableList<String> options = FXCollections.observableArrayList(neighbourhoodSet);
        final ComboBox neighbourhoodBox = new ComboBox(options);
        neighbourhoodBox.setMaxWidth(100);
        Button goButton = new Button("Go");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(neighbourhoodBox, goButton);

        final Label wardLabel = new Label("Select ward");
        String[] wardSet = propertyAssessments.getSortedWardList();
        ObservableList<String> options2 = FXCollections.observableArrayList(wardSet);
        final ComboBox wardBox = new ComboBox(options2);
        wardBox.setMinWidth(100);
        Button goButton2 = new Button("Go");
        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(wardBox, goButton2);

        goButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (neighbourhoodBox.getValue() != null) {
                    String neighborhoodName = (String)neighbourhoodBox.getValue();
                    PropertyAssessments neighborhood = propertyAssessments.getAssessmentsByNeighbourhood(neighborhoodName);
                    customCollection = neighborhood;
                    Location centre = neighborhood.getCentre();
                    List<Location> neighborhoodCoordinates =  neighborhoodBounds.get(neighborhoodName);
                    if (webEngine != null) {
                        jsGoMap(centre, 15, neighborhoodCoordinates);
                    }
                    updateLegend(neighborhood);
                    textArea.setText(neighborhoodName + "\n" + neighborhood.toString());
                    neighbourhoodBox.setValue(null);
                    wardBox.setValue(null);
                }
            }
        });

        goButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (wardBox.getValue() != null) {
                    String wardName = (String)wardBox.getValue();
                    PropertyAssessments ward = propertyAssessments.getAssessmentsByWard(wardName.toUpperCase());
                    customCollection = ward;
                    Location centre = ward.getCentre();
                    List<Location> wardCoordinates = wardBounds.get(wardName.toUpperCase());
                    if (webEngine != null) {
                        jsGoMap(centre, 12, wardCoordinates);
                    }
                    updateLegend(ward);
                    textArea.setText(wardName + "\n" + ward.toString());
                    neighbourhoodBox.setValue(null);
                    wardBox.setValue(null);

                }
            }
        });
        //end Shea addition

        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(10);
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        Button heatMapBtn = new Button();
        heatMapBtn.setMaxWidth(150);
        heatMapBtn.setText("Activate Heatmap");
        heatMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(webEngine != null) {
                    jsLoadProperties();
                }
            }
        });
        //Clear Map Bttn
        Button clearMapBtn = new Button();
        clearMapBtn.setMaxWidth(150);
        clearMapBtn.setText("Clear Map");
        clearMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(webEngine != null) {
                    webEngine.executeScript("clearMap()");
                }
                textArea.clear();
                customCollection = null;
            }
        });
        vbox.getChildren().addAll(
                searchLabel,
                neighbourhoodLabel,
                hBox,
                wardLabel,
                hBox2,
                textArea,
                heatMapBtn,
                clearMapBtn);
        return vbox;
    }

    /**
     * Loads the html document that contains the map
     *
     * @return returns a local html document that contains the embed map
     */
    private VBox createMap(){
        VBox vbox = new VBox();
        //vbox.setPadding(new Insets(10,10,10,10));

        URL mapUrl = getClass().getResource("Map.html"); //PLEASE do not keep this enabled during dev as it will burn into my free credits

        /* Use below file for testing. If you want to see fancy map, uncomment the the above
         * line and comment out the below line
         * Don't forget to switch it back
         */
        //URL mapUrl = getClass().getResource("testing.html");

        webEngine.load(mapUrl.toExternalForm());

        vbox.getChildren().addAll(webView);
        VBox.setVgrow(webView, Priority.ALWAYS);
        return vbox;
    }

    /**
     * Converts the list of properties in the PropertyAssessment class into a javascript
     * array and sends the array to the javascript function in the map document
     *
     */
    private void jsLoadProperties(){
        StringBuilder jsArray = new StringBuilder();
        PropertyAssessments dataset;
        if (customCollection != null){
            dataset = customCollection;
        }
        else {
            dataset = propertyAssessments;
        }
        updateLegend(dataset);
        jsArray.append("[");
        String point = "";
        int c = 0;
        for (PropertyAssessment property : dataset.getPropertyAssessments()){
            point = "["+ property.getLatitude()+", "+property.getLongitude() + "," + property.getValue() + "],";
            jsArray.append(point);
            c++;
            if (c > 100000){ //Sends properties in batches of max size 100000 to avoid js range error
                jsArray.append("]");
                webEngine.executeScript("addProperties("+ jsArray.toString()+")");
                jsArray = new StringBuilder();
                jsArray.append("[");
                c=0;
            }
        }
        jsArray.append("]");
        webEngine.executeScript("addProperties("+ jsArray.toString()+")");
    }

    //shea addition
    private void jsGoMap(Location centre, int zoom, List<Location> bounds){
        StringBuilder jsArray = new StringBuilder();
        jsArray.append(centre.getLatitude() + ", " + centre.getLongitude() + ", " + zoom);
        //webEngine.executeScript("legend("+ propertyAssessments.getMax() + ","+ propertyAssessments.getMin() +")");
        webEngine.executeScript("setCentreAndZoom(" + jsArray.toString() + ")");
        StringBuilder jsArray2 = new StringBuilder();
        jsArray2.setLength(0);
        jsArray2.append("[");
        for (Location l: bounds) {
            jsArray2.append("[" + l.getLatitude() + ", " + l.getLongitude() + "],");
        }

        jsArray2.deleteCharAt(jsArray2.length()-1);
        jsArray2.append("]");
        webEngine.executeScript("drawBoundary(" + jsArray2.toString() + ")");
    }

    private static Map<String, List<Location>> getCoordinates(String filename) throws IOException, NumberFormatException {
        Scanner file = new Scanner(Paths.get(filename));
        int n = getLength(file);
        file = new Scanner(Paths.get(filename));
        Map<String, List<Location>> coordinates = new HashMap<>();
        List<Location> bounds = new ArrayList<>();

        String currentLine = file.nextLine(); // set currentLine to first line in file
        currentLine = currentLine.replaceFirst("^\uFEFF", "");
        String[] lineArray = currentLine.split(","); // split line by commas
        String name = lineArray[0]; // set name to first name of file
        String compare = "";
        Location coordinate = new Location(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2])); // set coordinate
        bounds.add(coordinate); // put first coordinate in bounds list
        for (int i = 0 ; i <= n && file.hasNextLine() ; i++){
            // iterate through each line and make a Property Assessment from each
            currentLine = file.nextLine(); // iterate to next line
            lineArray = currentLine.split(",");
            compare = lineArray[0]; ; // set compare string to this line's name
            coordinate = new Location(Double.parseDouble(lineArray[1]), Double.parseDouble(lineArray[2])); // set coordinate
            if(compare.equals(name) && file.hasNextLine()){
                bounds.add(coordinate); // if compare and name are equal, add coordinate to bounds list
            }
            else{
                List<Location>boundsCopy = new ArrayList<>(); // if compare an name are not equal, make a deep copy of bounds list
                for(Location coord : bounds) {
                    boundsCopy.add(coord.getCopy());
                }
                coordinates.put(name, boundsCopy); // add name(key) and deep copy of bounds list(value) to coordinates hash map
                bounds.clear(); // clear bounds list
                bounds.add(coordinate); // add coordinate to boudns list
                name = compare; // reset name
            }

        }
        return coordinates;
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

    /**
     * Updates the legend to the max and min value in the current data
     * @param properties
     */
    private void updateLegend(PropertyAssessments properties){
        webEngine.executeScript("legend(\"" + moneyMaker.format(properties.getMax())+ "\",\"" + moneyMaker.format(properties.getMin()) +"\")");

    }
}
