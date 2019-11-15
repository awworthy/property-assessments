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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        Scene scene = new Scene(borderPane, 1200, 600);
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

        TextArea statsText = new TextArea(propertyAssessments.toString());
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

        hBox.getChildren().addAll(searchBtn, resetBtn);
        vbox.getChildren().addAll(title, accountLabel, accountField, addressLabel, addressField, neighbourhoodLabel, neighbourhoodField, assessLabel, classBox, hBox, separator, statsText);
        return vbox;
    }
}