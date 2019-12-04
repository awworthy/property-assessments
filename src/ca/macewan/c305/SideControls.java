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
    private PropertyAssessments propertyAssessmentsMaster; //The list that is loaded in at the start or when changing datasets
    private PropertyAssessments propertyAssessments; //The subset
    private VBox vbox;
    TextArea textArea;
    private ComboBox neighbourhoodBox;
    private ComboBox wardBox;
    private ComboBox classBox;
    private TextField accountField;
    private TextField addressField;
    private ObservableList<PropertyAssessment> properties;
    private NumberFormat moneyMaker = NumberFormat.getCurrencyInstance();
    private Map<String, List<Location>> wardBounds;
    Map<String, List<Location>> neighborhoodBounds;

    /**
     * Creates content for the side bar
     *
     */
    public SideControls(PropertyAssessments propertyAssessments, WebEngine webEngine, ObservableList<PropertyAssessment> properties) throws IOException {
        wardBounds = getCoordinates("Municipal_20Ward_20Boundaries_20_Tableau_.csv");
        neighborhoodBounds = getCoordinates("Neighbourhood_20Boundaries_20_Tableau_.csv");
        moneyMaker.setMaximumFractionDigits(0);
        this.propertyAssessments = propertyAssessments;
        this.propertyAssessmentsMaster = deepCopy(propertyAssessments);
        this.webEngine = webEngine;
        this.properties = properties;
        vbox = new VBox();
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(10);
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
        final Label searchLabel =  new Label("Dataset Options");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        textArea = new TextArea();
        textArea.setMaxWidth(200);


        VBox neighbourhoodControls = neighbourhoodControl();
        VBox wardControls = wardControl();
        VBox classControls = classControl();
        HBox mapControls = mapControl();
        VBox inputControls = inputControl();
        HBox searchControls = searchControl();

        vbox.getChildren().addAll(
                searchLabel,
                inputControls,
                neighbourhoodControls,
                wardControls,
                classControls,
                textArea,
                searchControls,
                mapControls
        );
    }

    public void updateMasterList(){
        this.propertyAssessmentsMaster = deepCopy(propertyAssessments);
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


    private HBox searchControl(){
        HBox controls = new HBox();
        controls.setSpacing(5);

        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(event -> {

            System.out.println(propertyAssessments.propertyAssessmentsList.size());
            String account = accountField.getText().strip();
            String address = addressField.getText().strip();
            String neighbourhood = (String)neighbourhoodBox.getValue();
            String ward = (String)wardBox.getValue();
            String assessmentClass = (String)classBox.getValue();
            neighbourhoodBox.setValue(null);
            wardBox.setValue(null);



            //adding this to fix the current implementation of creating a new list every time

            if (!account.equals("")) {
                propertyAssessments.propertyAssessmentsList = propertyAssessments.getAssessmentsByAccount(account).propertyAssessmentsList;
            }
            if (!address.equals("")) {
                propertyAssessments.propertyAssessmentsList = propertyAssessments.getAssessmentsByAddress(address).propertyAssessmentsList;
            }
            if (neighbourhood != null) {
                propertyAssessments.propertyAssessmentsList = propertyAssessments.getAssessmentsByNeighbourhood(neighbourhood).propertyAssessmentsList;
                    if(webEngine != null) {
                        webEngine.executeScript("clearMap()");
                    }
                    Location centre = getCentre(neighborhoodBounds.get(neighbourhood));
                    List<Location> neighborhoodCoordinates =  neighborhoodBounds.get(neighbourhood);
                    if (webEngine != null) {
                        jsGoMap(centre, 14, neighborhoodCoordinates);
                    }
                }

            if (ward != null) {
                propertyAssessments.propertyAssessmentsList = propertyAssessments.getAssessmentsByWard(ward).propertyAssessmentsList;

                if(webEngine != null) {
                    webEngine.executeScript("clearMap()");
                }
                Location centre = getCentre(wardBounds.get(ward.toUpperCase()));
                List<Location> wardCoordinates = wardBounds.get(ward.toUpperCase());
                if (webEngine != null) {
                    jsGoMap(centre, 12, wardCoordinates);
                }
                //updateLegend(ward);
            }
            if (assessmentClass != null) {
                propertyAssessments.propertyAssessmentsList = propertyAssessments.getAssessmentsByClass(assessmentClass).propertyAssessmentsList;
            }

            //table.setItems(properties);//removed!!
            updateOList(propertyAssessments);
            textArea.setText(propertyAssessments.toString());

            accountField.clear();
            addressField.clear();
            //vis.update(searchAssessments);
            //map.update(searchAssessments);
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(event -> {
            accountField.clear();
            addressField.clear();
            neighbourhoodBox.setValue(null);
            classBox.setValue(null);
            PropertyAssessments p = deepCopy(propertyAssessmentsMaster);
            propertyAssessments.propertyAssessmentsList = p.propertyAssessmentsList;
            textArea.setText(propertyAssessments.toString());

            updateOList(propertyAssessmentsMaster);
        });


        controls.getChildren().addAll(searchBtn, resetBtn);
        return controls;
    }

    private VBox inputControl(){
        VBox inputs = new VBox();
        inputs.setSpacing(5);

        final Label accountLabel = new Label("Account Number:");
        accountField = new TextField();
        final Label addressLabel = new Label("Address (#suite #house street):");
        addressField = new TextField();



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
        //for testing
        //Map<String, List<Location>> neighborhoodBounds = getCoordinates("nBounds.csv");
        final Label neighbourhoodLabel = new Label("Select neighbourhood");
        Set<String> neighbourhoodSet = propertyAssessments.getNeighborhoodSet();
        ObservableList<String> options = FXCollections.observableArrayList(neighbourhoodSet);
        neighbourhoodBox = new ComboBox(options);
        neighbourhoodBox.setMaxWidth(100);
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(neighbourhoodBox);

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
        //Map<String, List<Location>> wardBounds = getCoordinates("wBound.csv");

        final Label wardLabel = new Label("Select ward");
        String[] wardSet = propertyAssessments.getSortedWardList();
        ObservableList<String> options2 = FXCollections.observableArrayList(wardSet);
        wardBox = new ComboBox(options2);
        wardBox.setMinWidth(100);
        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(wardBox);

        ward.getChildren().addAll(wardLabel, hBox2);
        return ward;
    }

    /**
     * Creates controls for selecting a class
     * @return
     */
    private VBox classControl(){
        VBox classSelector = new VBox();
        classSelector.setSpacing(5);

        Set<String> classSet = propertyAssessments.getClassSet();
        ObservableList<String> options = FXCollections.observableArrayList(classSet);
        final Label assessLabel = new Label("Assessment Class:");
        classBox = new ComboBox(options);

        classSelector.getChildren().addAll(assessLabel, classBox);
        return classSelector;
    }

    /**
     * Returns the panel VBox node so that it can be added to the JavaFX program
     * @return
     */
    public VBox getPanel(){
        return vbox;
    }


    /**
     * Adds the list of properties that match the search query to the table list
     *
     * @param searchResults
     */
    private void updateOList(PropertyAssessments searchResults){
        //Load assessments into observable list
        properties.clear();
        for(PropertyAssessment p : searchResults.propertyAssessmentsList)
            properties.add(p); //add to collection to be returned
    }

    private PropertyAssessments deepCopy(PropertyAssessments properties){
        PropertyAssessments newProperties = new PropertyAssessments();
        for(PropertyAssessment p : properties.propertyAssessmentsList){
            newProperties.addPropertyAssessment(p);
        }
        return newProperties;
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
