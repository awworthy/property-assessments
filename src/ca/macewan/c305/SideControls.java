package ca.macewan.c305;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;

import javax.net.ssl.SNIHostName;
import javax.print.DocFlavor;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;

/**
 * SideControls Class handles the sidebar for the application
 */
public class SideControls{
    private WebEngine webEngine;
    private PropertyAssessments customCollection;
    private PropertyAssessments propertyAssessments;
    private VBox vbox;
    TextArea textArea;
    private ComboBox neighbourhoodBox;
    private ComboBox wardBox;
    private NumberFormat moneyMaker = NumberFormat.getCurrencyInstance();

    /**
     * Creates content for the side bar
     *
     */
    public SideControls(PropertyAssessments propertyAssessments, WebEngine webEngine) throws IOException {
        this.propertyAssessments = propertyAssessments;
        this.webEngine = webEngine;
        vbox = new VBox();
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(10);
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
        final Label searchLabel =  new Label("Dataset Options");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        textArea = new TextArea();
        textArea.setMaxWidth(200);


        VBox neighbourhood = neighbourhoodControl();
        VBox ward = wardControl();
        HBox mapControls = mapControl();
        VBox inputControl = inputControl();

        vbox.getChildren().addAll(
                searchLabel,
                inputControl,
                neighbourhood,
                ward,
                textArea,
                mapControls
        );
    }


    /**
     * Creates the controls for the map tab
     * @return
     */
    private HBox mapControl(){
        HBox controls = new HBox();
        controls.setSpacing(5);
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

        controls.getChildren().addAll(heatMapBtn, clearMapBtn);
        return controls;
    }

    //shea addition

    private VBox inputControl(){
        VBox inputs = new VBox();
        inputs.setSpacing(5);

        final Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        final Label addressLabel = new Label("Address (#suite #house street):");
        TextField addressField = new TextField();



        inputs.getChildren().addAll(accountLabel, accountField, addressLabel, addressField);
        return inputs;
    }





    /**
     * Creates the controls for the neighbourhood selector
     * @return
     * @throws IOException
     */
    private VBox neighbourhoodControl() throws IOException{
        VBox neighbourhood = new VBox();
        neighbourhood.setSpacing(5);
        Map<String, List<Location>> neighborhoodBounds = getCoordinates("Neighbourhood_20Boundaries_20_Tableau_.csv");
        //for testing
        //Map<String, List<Location>> neighborhoodBounds = getCoordinates("nBounds.csv");
        final Label neighbourhoodLabel = new Label("Select neighbourhood");
        Set<String> neighbourhoodSet = propertyAssessments.getNeighborhoodSet();
        ObservableList<String> options = FXCollections.observableArrayList(neighbourhoodSet);
        neighbourhoodBox = new ComboBox(options);
        neighbourhoodBox.setMaxWidth(100);
        Button goButton = new Button("Go");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(neighbourhoodBox, goButton);

        goButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (neighbourhoodBox.getValue() != null) {
                    if(webEngine != null) {
                        webEngine.executeScript("clearMap()");
                    }
                    String neighborhoodName = (String)neighbourhoodBox.getValue();
                    PropertyAssessments neighborhood = propertyAssessments.getAssessmentsByNeighbourhood(neighborhoodName);
                    customCollection = neighborhood;
                    Location centre = getCentre(neighborhoodBounds.get(neighborhoodName));
                    List<Location> neighborhoodCoordinates =  neighborhoodBounds.get(neighborhoodName);
                    if (webEngine != null) {
                        jsGoMap(centre, 14, neighborhoodCoordinates);
                    }
                    updateLegend(neighborhood);
                    textArea.setText(neighborhoodName + "\n" + neighborhood.toString() + "\n" + neighborhood.getFirstPropertyAssessment().getNeighbourhood().getWard());
                    neighbourhoodBox.setValue(null);
                    wardBox.setValue(null);
                }
            }
        });

        neighbourhood.getChildren().addAll(neighbourhoodLabel, hBox);
        return neighbourhood;
    }

    /**
     * Creates controls for the Ward selector
     * @return
     * @throws IOException
     */
    private VBox wardControl() throws IOException {
        VBox ward = new VBox();
        ward.setSpacing(5);
        Map<String, List<Location>> wardBounds = getCoordinates("Municipal_20Ward_20Boundaries_20_Tableau_.csv");
        //Map<String, List<Location>> wardBounds = getCoordinates("wBound.csv");

        final Label wardLabel = new Label("Select ward");
        String[] wardSet = propertyAssessments.getSortedWardList();
        ObservableList<String> options2 = FXCollections.observableArrayList(wardSet);
        wardBox = new ComboBox(options2);
        wardBox.setMinWidth(100);
        Button goButton2 = new Button("Go");
        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(wardBox, goButton2);

        goButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (wardBox.getValue() != null) {
                    if(webEngine != null) {
                        webEngine.executeScript("clearMap()");
                    }
                    String wardName = (String)wardBox.getValue();
                    PropertyAssessments ward = propertyAssessments.getAssessmentsByWard(wardName.toUpperCase());
                    customCollection = ward;
                    Location centre = getCentre(wardBounds.get(wardName.toUpperCase()));
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

        ward.getChildren().addAll(wardLabel, hBox2);
        return ward;
    }

    /**
     * Returns the panel VBox node so that it can be added to the JavaFX program
     * @return
     */
    public VBox getPanel(){
        return vbox;
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

    public Location getCentre(List<Location>coordinates) {
        Double minLatitude = 90.0;
        Double maxLatitude = -90.0;
        Double minLongitude = 180.0;
        Double maxLongitude = -180.0;
        for (Location l : coordinates) {
            if (l.getLatitude() > maxLatitude) {
                maxLatitude = l.getLatitude();
            }
            if (l.getLatitude() < minLatitude) {
                minLatitude = l.getLatitude();
            }
            if (l.getLongitude() > maxLongitude) {
                maxLongitude = l.getLongitude();
            }
            if (l.getLongitude() < minLongitude) {
                minLongitude = l.getLongitude();
            }
        }
        return new Location((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2);
    }

    //shea addition
    private void jsGoMap(Location centre, double zoom, List<Location> bounds){
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

    /**
     * Updates the legend to the max and min value in the current data
     * @param properties
     */
    private void updateLegend(PropertyAssessments properties){
        webEngine.executeScript("legend(\"" + moneyMaker.format(properties.getMax())+ "\",\"" + moneyMaker.format(properties.getMin()) +"\")");

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
}
