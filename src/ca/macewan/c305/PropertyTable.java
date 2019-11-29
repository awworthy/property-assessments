package ca.macewan.c305;

/**
 *  PropertyTable.java
 *
 *  CMPT 305:
 *      Fall 2019
 *  Assignment 1:
 *      Milestone 2
 *  Name:
 *      Alex Worthy
 *  ID:
 *      1742554
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.Set;

public class PropertyTable extends Application {

    private TableView<PropertyAssessment> table;
    private ObservableList<PropertyAssessment> properties;
    private PropertyAssessments propertyAssessments;
    private String filename = "Property_Assessment_Data__Current_Calendar_Year_.csv";
    private TextArea statsText;
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setTitle("Property Assessments");
        propertyAssessments = makePropertyAssessments(filename);

        VBox tableBox = new VBox(10);
        VBox searchBox = addVBox();
        tableBox.setPadding(new Insets(10, 10, 10, 10));
        searchBox.setPadding(new Insets(10,10,10,10));
        BorderPane borderPane = new BorderPane();
        searchBox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
        borderPane.setCenter(tableBox);
        borderPane.setLeft(searchBox);
        borderPane.setPadding(new Insets(5));

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Assessments");
        Tab tab2 = new Tab("Map");
        Tab tab3 = new Tab("Tab 3");
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tabPane.getTabs().addAll(tab1, tab2, tab3);
        tab1.setContent(borderPane);
        MapTab map =  new MapTab();
        tab2.setContent(map.start(propertyAssessments));

        Scene scene = new Scene(tabPane, 1200, 600);
        primaryStage.setScene(scene);

        final Label tableLabel = new Label("Edmonton Property Assessments");
        tableLabel.setFont(new Font("Arial", 16));

        configureTable();

        final Label searchLabel =  new Label("Find Property Assessment");
        tableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //hBox.getChildren().addAll(firstNameField, lastNameField, taxField, addBtn);
        tableBox.setVgrow(table, Priority.ALWAYS);
        tableBox.getChildren().addAll(tableLabel, table /* hBox */ );
        //tableBox.getChildren().addAll(searchLabel, searchfield1);

        primaryStage.show();
    }

    private void configureTable() {
        table = new TableView<>();


        properties = FXCollections.observableArrayList(propertyAssessments.getPropertyAssessments());
        table.setItems(properties);

        TableColumn<PropertyAssessment, Integer> acctNumCol = new TableColumn<>("Account Number");
        acctNumCol.setCellValueFactory(new PropertyValueFactory<>("accountNum"));
        acctNumCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        TableColumn<PropertyAssessment, Address> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));

        TableColumn<PropertyAssessment, String> classCol = new TableColumn<>("Assessment Class");
        classCol.setCellValueFactory(new PropertyValueFactory<>("classification"));
        classCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        TableColumn<PropertyAssessment, Address> nbhoodCol = new TableColumn<>("Neighbourhood");
        nbhoodCol.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        nbhoodCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));

        TableColumn<PropertyAssessment, Address> latCol = new TableColumn<>("Latitude");
        latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        latCol.prefWidthProperty().bind(table.widthProperty().multiply(0.15));

        TableColumn<PropertyAssessment, Address> longCol = new TableColumn<>("Longitude");
        longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        longCol.prefWidthProperty().bind(table.widthProperty().multiply(0.15));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        TableColumn<PropertyAssessment, Integer> assessedValCol = new TableColumn<>("Assessed Value");
        assessedValCol.setCellValueFactory(new PropertyValueFactory<>("value"));
        assessedValCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });
        assessedValCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        table.getColumns().setAll(acctNumCol, addressCol, assessedValCol, classCol, nbhoodCol, latCol, longCol);
    }

    private static PropertyAssessments makePropertyAssessments(String filename) throws IOException, NumberFormatException {
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

    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(12,10,10,10));
        vbox.setSpacing(10);

        Text title = new Text("Find Property Search");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        final Label accountLabel = new Label("Account Number:");
        TextField accountField = new TextField();
        final Label addressLabel = new Label("Address (#suite #house street):");
        TextField addressField = new TextField();
        final Label neighbourhoodLabel = new Label("Neighbourhood:");
        TextField neighbourhoodField = new TextField();
        final Label assessLabel = new Label("Assessment Class:");

        Set<String> classSet = propertyAssessments.getClassSet();
        ObservableList<String> options = FXCollections.observableArrayList(classSet);
        final ComboBox classBox = new ComboBox(options);

        HBox hBox = new HBox(10);

        statsText = new TextArea();
        statsText.setText(propertyAssessments.toString());
        statsText.setMaxWidth(200);

        Button searchBtn = new Button("Search");
        Button resetBtn = new Button("Reset");
        searchBtn.setOnAction(event -> {
            String account = accountField.getText().strip();
            String address = addressField.getText().strip();
            String neighbourhood = neighbourhoodField.getText().strip();
            String assessmentClass = (String)classBox.getValue();

            PropertyAssessments searchAssessments = propertyAssessments;

            if (!account.equals("")) {
                searchAssessments = searchAssessments.getAssessmentsByAccount(account);
                properties = FXCollections.observableArrayList(searchAssessments.getPropertyAssessments());
            }
            if (!neighbourhood.equals("")) {
                searchAssessments = searchAssessments.getAssessmentsByNeighbourhood(neighbourhood);
                properties = FXCollections.observableArrayList(searchAssessments.getPropertyAssessments());
            }
            if (!address.equals("")) {
                searchAssessments = searchAssessments.getAssessmentsByAddress(address);
                properties = FXCollections.observableArrayList(searchAssessments.getPropertyAssessments());
            }
            if (assessmentClass != null) {
                searchAssessments = searchAssessments.getAssessmentsByClass(assessmentClass);
                properties = FXCollections.observableArrayList(searchAssessments.getPropertyAssessments());
            }

            table.setItems(properties);
            statsText.setText(searchAssessments.toString());

            accountField.clear();
            addressField.clear();
            neighbourhoodField.clear();
        });
        resetBtn.setOnAction(event -> {
            accountField.clear();
            addressField.clear();
            neighbourhoodField.clear();
            classBox.setValue(null);
            statsText.setText(propertyAssessments.toString());

            properties = FXCollections.observableArrayList(propertyAssessments.getPropertyAssessments());
            table.setItems(properties);
        });

        Separator separator = new Separator();

        /* File open button in the following hBox */
        HBox fileBox = makeFileBox();

        hBox.getChildren().addAll(searchBtn, resetBtn);
        vbox.getChildren().addAll(title, accountLabel, accountField, addressLabel, addressField, neighbourhoodLabel, neighbourhoodField, assessLabel, classBox, hBox, separator, statsText, fileBox);
        return vbox;
    }

    /**
     * makeFileBox()
     *      Creates a new hBox containing a button. The button allows a user to bring up a file explorer window
     *      and select a custom .csv file for opening.
     * @return HBox
     */
    private HBox makeFileBox() {
        FileChooser fileChooser = new FileChooser();
        final Button openFileButton = new Button("Choose custom file");
        HBox hBox = new HBox(10);
        openFileButton.setOnAction(
                e -> {
                    // let the user choose the file, get the file path.
                    File file = fileChooser.showOpenDialog(stage);
                    filename = file.getPath();
                    try {
                        // update the main propertyAssessments collection class
                        propertyAssessments = makePropertyAssessments(filename);
                        
                        // reset the table
                        table.setItems(properties);

                        // reset the stats box on the left border
                        statsText.setText(propertyAssessments.toString());
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                        String err = "The file " + file.getName() + " does not contain property assessment data in a readable format";
                        System.out.println(err);

                        // Display alert dialog if something went wrong in opening the file
                        Alert dialog = new Alert(Alert.AlertType.ERROR, err, ButtonType.OK);
                        dialog.setHeaderText("Error: File Not Readable");
                        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        dialog.show();
                    }
                });
        hBox.getChildren().addAll(openFileButton);

        return hBox;
    }
}