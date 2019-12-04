package ca.macewan.c305;

/**
 *  PropertyTable.java
 *
 *  CMPT 305:
 *      Fall 2019
 *  Final Project
 *      Custom Property Assessments Application
 *  Names:
 *      Alex Worthy
 *      Dakota Doolaege
 *      Shea Odland
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Scanner;

public class PropertyTable extends Application {

    private TableView<PropertyAssessment> table;
    private ObservableList<PropertyAssessment> properties;
    private PropertyAssessments propertyAssessments;
    private String filename = "Property_Assessment_Data__Current_Calendar_Year_.csv";
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    SideControls searchBox;

    Stage stage;
    DataTab vis;
    MapTab map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        propertyAssessments = new PropertyAssessments();

        this.stage = primaryStage;
        primaryStage.setTitle("Property Assessments");
        makePropertyAssessments(filename);

        VBox tableBox = new VBox(10);

        tableBox.setPadding(new Insets(10, 10, 10, 10));
        BorderPane borderPane = new BorderPane();

        configureTable();

        searchBox = new SideControls(propertyAssessments, webEngine, properties);

        borderPane.setCenter(tableBox);
        borderPane.setLeft(searchBox.getPanel());
        borderPane.setPadding(new Insets(5));

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Assessments");
        Tab tab2 = new Tab("Map");
        Tab tab3 = new Tab("Visualizations");
        tab1.setClosable(false);
        tab2.setClosable(false);
        tab3.setClosable(false);
        tab3.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                vis.refresh();
            }
        });
        tabPane.getTabs().addAll(tab1, tab2, tab3);
        tab1.setContent(borderPane);
        map =  new MapTab();
        vis = new DataTab();

        Scene scene = new Scene(tabPane, 1200, 600);
        primaryStage.setScene(scene);

        String darkMode = getClass().getResource("./darkMode.css").toExternalForm();
        String lightMode = getClass().getResource("./lightMode.css").toExternalForm();
        scene.getStylesheets().add(lightMode);

        final Label tableLabel = new Label("Edmonton Property Assessments");
        tableLabel.setFont(new Font("Arial", 16));

        Region spacer = new Region();

        Button switchTheme = new Button();
        switchTheme.setText("Enable Dark Mode");
        switchTheme.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if( scene.getStylesheets().contains(lightMode)){
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(darkMode);
                    switchTheme.setText("Disable Dark Mode");}
                else{
                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(lightMode);
                    switchTheme.setText("Enable Dark Mode");}
            }
        });

        HBox tableHeader = new HBox();
        tableHeader.setHgrow(spacer, Priority.ALWAYS);
        tableHeader.setSpacing(5);
        /* File open button in the following hBox */
        HBox fileBox = makeFileBox();
        tableHeader.getChildren().addAll(tableLabel, spacer, switchTheme, fileBox);


        final Label searchLabel =  new Label("Find Property Assessment");
        tableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        tableBox.setVgrow(table, Priority.ALWAYS);
        tableBox.getChildren().addAll(tableHeader, table /* hBox */ );

        Task task = new Task<Void>() {
            @Override public Void call() {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        tab3.setContent(vis.start(propertyAssessments));
                    }
                });
                return null;
            }
        };
        tab2.setContent(map.start(propertyAssessments, webView, webEngine, properties));
        primaryStage.show();
        new Thread(task).start();
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

    private void makePropertyAssessments(String filename) throws IOException, NumberFormatException {
        Scanner file = new Scanner(Paths.get(filename));
        int n = getLength(file);

        // re-read file as scanner needs to point at beginning again
        file = new Scanner(Paths.get(filename));
        if (file.hasNextLine()) { // Skip header
            file.nextLine();
        }

        for (int i = 0 ; i < n && file.hasNextLine() ; i++){
            // iterate through each line and make a Property Assessment from each
            String currentLine = file.nextLine();
            PropertyAssessment propertyAssessment = propertyAssessments.parseLineToAssessment(currentLine);

            // add propertyAssessment to List
            propertyAssessments.addPropertyAssessment(propertyAssessment);
        }
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
     * makeFileBox()
     *      Creates a new hBox containing a button. The button allows a user to bring up a file explorer window
     *      and select a custom .csv file for opening.
     * @return HBox
     */
    private HBox makeFileBox() {
        FileChooser fileChooser = new FileChooser();
        final Button openFileButton = new Button("Load Custom Dataset");
        HBox hBox = new HBox(10);
        openFileButton.setOnAction(
                e -> {
                    // let the user choose the file, get the file path.
                    File file = fileChooser.showOpenDialog(stage); //hBox.getScene().getWindow())
                    if (file != null)
                        filename = file.getPath();
                    try {
                        // update the main propertyAssessments collection class
                        System.out.println("here");
                        propertyAssessments.propertyAssessmentsList.clear();
                        System.out.println(propertyAssessments.propertyAssessmentsList.size());
                        makePropertyAssessments(filename);
                        searchBox.updateMasterList();
                        System.out.println(propertyAssessments.propertyAssessmentsList.size());

                        // reset the table
                        //table.setItems(properties);

                        // reset the stats box on the left border
                        //statsText.setText(propertyAssessments.toString());
                        //vis.update(propertyAssessments);
                        //map.update(propertyAssessments);
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                        String err = "The file " + file.getName() + " does not contain property assessment data in a readable format";
                        System.out.println(err);

                        // Display alert dialog if something went wrong in opening the file
                        PopUp.Error(err);
                    }
                });
        hBox.getChildren().addAll(openFileButton);

        return hBox;
    }
}
