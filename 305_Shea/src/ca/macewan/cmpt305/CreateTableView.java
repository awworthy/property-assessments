package ca.macewan.cmpt305;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class CreateTableView extends Application{

    private TableView<PropAssess> table;
    private ObservableList<PropAssess> properties;
    private ObservableList<PropAssess> propSort;
    private ObservableList<String> classes;
    private List<String> clsTypes;
    private TextField acctNumField;
    private TextField addressField;
    private TextField neighborhoodField;
    private ComboBox classBox;
    private TextArea statsArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create table elements
        table = new TableView<>();
        primaryStage.setTitle("Edmonton Property Assessments");
        final Label label = new Label("Edmonton Property Assessments");
        final Label searchLabel = new Label("Find Property Assessments");
        final Label acctIn = new Label("Account Number:");
        final Label addIn = new Label("Address (#suite #house street)");
        final Label nbIn = new Label("Neighborhood");
        final Label clIn = new Label("Assessment Class:");
        final Separator horLine = new Separator();
        BorderPane tableWindow = new BorderPane();
        Scene scene = new Scene(tableWindow, 1350, 700);
        HBox hBox = new HBox(10);
        VBox vBox = new VBox(10);
        VBox vBox2 = new VBox(10);
        Insets insets =  new Insets(10, 10, 10, 10);

        //Configure visual properties of table elements
        vBox.setVgrow(table, Priority.ALWAYS);
        vBox.setPadding(insets);
        vBox2.setStyle("-fx-border-color: lightGray;\n" + "-fx-border-insets: 5;\n" + "-fx-border-width: 1;\n");
        vBox2.setPadding(insets);
        tableWindow.setCenter(vBox);
        tableWindow.setLeft(vBox2);
        primaryStage.setScene(scene);
        label.setFont(Font.font("Arial", FontWeight.BOLD,16));
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        //Create PropertyAssessment objects for tableview to read
        PropAssessments props = readFile("PAs.csv");
        properties = FXCollections.observableArrayList(props.getPropAssessList());
        propSort = FXCollections.observableArrayList(properties);
        configureTable();

        //Instantiate elements
        acctNumField = new TextField();
        addressField = new TextField();
        neighborhoodField = new TextField();
        statsArea = new TextArea();
        clsTypes = props.getClasses();
        classes = FXCollections.observableList(clsTypes);;
        classBox = new ComboBox(classes);
        statsArea.setMaxWidth(240);

        //Configure button functionality
        Button searchBtn = new Button("Search");
        Button resetBtn = new Button ("Reset");
        searchBtn.setOnAction(event -> {
            //Create duplicate PropAssessment object for sorting based on fields chosen by user
            PropAssessments propsDuplicate = props;
            if(acctNumField.getLength() > 0){
                propsDuplicate = propsDuplicate.findPropertiesById(acctNumField.getText());
            }

            if(addressField.getLength() > 0){
                propsDuplicate = propsDuplicate.findPropertiesByAddress(addressField.getText());
            }

            if(neighborhoodField.getLength() > 0){
                propsDuplicate = propsDuplicate.findPropertiesByNeighborhood(neighborhoodField.getText());
            }

            if(classBox.getValue() != null){
                propsDuplicate = propsDuplicate.findPropertiesByClass(classBox.getValue().toString());
            }
            propSort.setAll(propsDuplicate.getPropAssessList());
            if (propsDuplicate.length() > 0) {
                statsArea.setText(printStats(propsDuplicate));
            }
            else {
                statsArea.setText("No properties found");
            }
            configureTable();
        });

        resetBtn.setOnAction(event -> {
            propSort.setAll(properties);
            configureTable();

            acctNumField.clear();
            addressField.clear();
            neighborhoodField.clear();
            statsArea.clear();
            classBox.setValue(null);
        });

        vBox.getChildren().addAll(label, table, hBox);
        hBox.getChildren().addAll(searchBtn,resetBtn);
        vBox2.getChildren().addAll(searchLabel, acctIn, acctNumField, addIn, addressField, nbIn, neighborhoodField,
                clIn, classBox, hBox, horLine, statsArea);
        primaryStage.show();
    }

    private void configureTable() {

        table.setItems(propSort);

        TableColumn<PropAssess, Integer> acctNumCol = new TableColumn<>("Account Number");
        acctNumCol.setCellValueFactory(new PropertyValueFactory<>("acctNumber"));
        acctNumCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        TableColumn<PropAssess, Address> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.272));

        TableColumn<PropAssess, String> assessmentClassCol = new TableColumn<>("Assessment Class");
        assessmentClassCol.setCellValueFactory(new PropertyValueFactory<>("cls"));
        assessmentClassCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        TableColumn<PropAssess, String> neighborhoodCol = new TableColumn<>("Neighborhood");
        neighborhoodCol.setCellValueFactory(new PropertyValueFactory<>("neighborhood"));
        neighborhoodCol.prefWidthProperty().bind(table.widthProperty().multiply(0.143));

        TableColumn<PropAssess, Location> latitudeCol = new TableColumn<>("Latitude");
        latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        latitudeCol.prefWidthProperty().bind(table.widthProperty().multiply(0.143));

        TableColumn<PropAssess, String> longitudeCol = new TableColumn<>("Longitude");
        longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        longitudeCol.prefWidthProperty().bind(table.widthProperty().multiply(0.143));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        TableColumn<PropAssess, Integer> valueColumn = new TableColumn<>("Assessed Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        valueColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });
        table.getColumns().setAll(acctNumCol, addressColumn, valueColumn, assessmentClassCol, neighborhoodCol,
                latitudeCol, longitudeCol);
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
                String[] specs = lineRead.split(","); // Create array of strings from each line of file
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

    public String printStats(PropAssessments props) { // Can be in main class
        String toRet = "";
        if(props.length() > 1){
            NumberFormat formatter = new DecimalFormat("#,###");
            toRet = "Statistics of Assessed Values:\n\n";
            toRet += "Number of properties: " + formatter.format(props.length());
            toRet += "\nMin: $" + formatter.format(props.getMin());
            toRet += "\nMax: $" + formatter.format(props.getMax());
            toRet += "\nRange: $" + formatter.format(props.getRange());
            toRet += "\nMean: $" + formatter.format(props.getMean());
            toRet += "\nMedian: $" + formatter.format(props.getMedian());
            toRet += "\nStandard Deviation: $" + formatter.format(props.getSd());
        }
        return toRet;
    }
}
