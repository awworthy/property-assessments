package ca.dakota.cmpt305;

import com.sun.javafx.geometry.BoundsUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Property assessment application with a GUI
 *
 * Displays all the property assessments in the dataset and allows for searching and filtering
 *
 * @auther Dakota Doolaege
 */
public class GUIMain extends Application {
    private PropertyAssessments assessments = new PropertyAssessments(new Hashtable<Integer, PropertyAssessment>());
    private TableView<PropertyAssessment> table;
    private ObservableList<PropertyAssessment> assessmentsList;

    /**
     * Main function for the program
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start function runs when the program is first started.
     * Sets up the program
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Edmonton Property Assessments");
        configureTable();
        VBox vbLeft = leftColConstructor();
        VBox vbCenter = centerColConstructor();
        BorderPane window = new BorderPane();
        window.setLeft(vbLeft);
        window.setCenter(vbCenter);
        Scene scene = new Scene(window, 1500, 800);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    /**
     * Constructor for the search button
     * Also contains logic for click events
     *
     * @param Acct
     * @param Addr
     * @param Neigh
     * @param PropertyClass
     * @param Statistics
     * @return
     */
    private Button searchBtnConstructor(TextField Acct,TextField Addr,TextField Neigh,ComboBox PropertyClass,TextArea Statistics){
        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(event -> {

            assessmentsList.clear();
            PropertyAssessments searchResults;
            String propClass = PropertyClass.getValue().toString();
            searchResults = getPropertyAssessmentsSearch(Acct.getText(), Addr.getText(), Neigh.getText(), propClass );
            System.out.println(searchResults.assessments.size());
            Statistics.clear();
            if( searchResults.assessments.size() > 1) {
                Statistics.setText(searchResults.getPropertyStatistics());
            }
            addToList(searchResults);
        });

        return searchBtn;
    }

    private Button resetBtnConstructor(TextField Acct, TextField Addr, TextField Neigh, ComboBox PropertyClass, TextArea Statistics){
        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(event -> {
            Acct.clear();
            Addr.clear();
            Neigh.clear();
            PropertyClass.getSelectionModel().selectFirst();
            Statistics.clear();
            Statistics.setText(assessments.getPropertyStatistics());
            assessmentsList.clear();
            addToList(assessments);
        });
        return resetBtn;
    }

    /**
     * Adds the list of properties that match the search query to the table list
     *
     * @param searchResults
     */
    private void addToList(PropertyAssessments searchResults){
        //Load assessments into observable list
        Enumeration<Integer> accounts = searchResults.assessments.keys(); //can't iterate over this because it is not a hashtable?
        Integer account;
        while(accounts.hasMoreElements()){ //loops collection of property assessments
            account = accounts.nextElement();
            assessmentsList.add(searchResults.assessments.get(account)); //add to collection to be returned
        }
    }


    /**
     * Gets all property assessments that match the requested criteria
     * @param account to get the assessments for
     * @param address to get the assessments for
     * @param neighbourhood to get the assessments for
     * @param propertyClass to get the assessments in
     * @return collection of property assessments
     */
    private PropertyAssessments getPropertyAssessmentsSearch(String account, String address, String neighbourhood, String propertyClass){
        neighbourhood = neighbourhood.toUpperCase();
        address = address.toUpperCase();
        PropertyAssessments searchResults = new PropertyAssessments(new Hashtable<Integer,PropertyAssessment>());
        Enumeration<Integer> accounts = assessments.assessments.keys();
        Integer acct;
        while(accounts.hasMoreElements()){ //loops collection of property assessments
            acct = accounts.nextElement();
            PropertyAssessment property =assessments.assessments.get(acct);
            if(
                    property.getAssessmentClass().contains(propertyClass) &&
                            Integer.toString(property.getAccountNumber()).contains(account) &&
                            property.getAddress().toString().contains(address) &&
                            property.getNeighbourhood().toString().contains(neighbourhood)
            ){
                System.out.println("found a match!");
                searchResults.assessments.put(acct,assessments.assessments.get(acct)); //add to collection to be returned
            }
        }
        return searchResults;
    }

    /**
     * Adds the list of property assessments to the observable list
     *
     * @return ObservableList of properties
     */
    private ObservableList<PropertyAssessment> getAssessments() {
        javafx.collections.ObservableList<PropertyAssessment> assessmentsList = FXCollections.observableArrayList();

        //Load assessments into observable list
        Enumeration<Integer> accounts =assessments.assessments.keys(); //can't iterate over this because it is not a hashtable?
        Integer account;
        while(accounts.hasMoreElements()){ //loops collection of property assessments
            account = accounts.nextElement();
            assessmentsList.add(assessments.assessments.get(account)); //add to collection to be returned
        }
        return assessmentsList;
    }

    /**
     * Runs through data file and extracts a list of all the property classes
     *
     * @return list of property classes
     */
    private ObservableList<String> getClasses(){
        javafx.collections.ObservableList<String> classes = FXCollections.observableArrayList();
        javafx.collections.ObservableSet<String> classSet = FXCollections.observableSet();

        //Load classes from all properties
        Enumeration<Integer> accounts = assessments.assessments.keys();
        Integer account;
        classSet.add("");
        while(accounts.hasMoreElements()){
            account = accounts.nextElement();
            classSet.add(assessments.assessments.get(account).getAssessmentClass());
        }
        classes.addAll(classSet);
        return classes;
    }

    /**
     * Constructor method for the left column. Sets up the user interface
     *
     * @return VBox containing all the items from the left column
     */
    private VBox leftColConstructor(){
        //left col items
        VBox vbLeft = new VBox(10);
        vbLeft.setPadding(new Insets(10,10,10,10));
        vbLeft.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;");
        final Label leftColLabel = new Label("Find Property Assessment");
        leftColLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        final Label leftColAcct = new Label("Account Number:");
        final Label leftColAddr = new Label("Address (#suite #house street):");
        final Label leftColNeigh = new Label("Neighbourhood:");
        final Label leftColClass = new Label("Assessment Class:");

        TextField Acct = new TextField();
        TextField Addr = new TextField();
        TextField Neigh = new TextField();
        ComboBox PropertyClass = new ComboBox();
        TextArea Statistics = new TextArea();
        Statistics.setText(assessments.getPropertyStatistics());

        PropertyClass.setItems(getClasses());
        PropertyClass.getSelectionModel().selectFirst();
        Statistics.setPrefColumnCount(20);

        HBox hbButtons = new HBox(10);
        Button searchBtn = searchBtnConstructor(Acct, Addr, Neigh, PropertyClass, Statistics);
        Button resetBtn = resetBtnConstructor(Acct, Addr, Neigh, PropertyClass, Statistics);
                new Button("Reset");
        hbButtons.getChildren().addAll(searchBtn, resetBtn);

        vbLeft.getChildren().addAll(
                leftColLabel,
                leftColAcct,Acct,
                leftColAddr,Addr,
                leftColNeigh, Neigh,
                leftColClass,PropertyClass,
                hbButtons,
                Statistics);
        return vbLeft;
    }

    /**
     * Constructor method for the center which contains the table
     *
     * @return VBox containing the table
     */
    private VBox centerColConstructor(){
        VBox vbCenter = new VBox(10);
        vbCenter.setPadding(new Insets(10,10,10,10));
        final Label label = new Label("Edmonton Property Assessments");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        vbCenter.getChildren().addAll(label, table);
        VBox.setVgrow(table, Priority.ALWAYS);

        return vbCenter;
    }

    /**
     * Sets up the main table and connects it to the observableList
     *
     * @throws IOException
     */
    private void configureTable() throws  IOException{
        table = new TableView<>();
        assessments.getPropertyAssessments("Property_Assessment_Data_2019.csv");
        assessmentsList = getAssessments();

        TableColumn<PropertyAssessment, Integer> account = new TableColumn<>("Account");
        account.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        TableColumn<PropertyAssessment, Integer>  assessedValue = new TableColumn<>("Assessed Value");
        assessedValue.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));
        TableColumn<PropertyAssessment, String>  assessmentClass = new TableColumn<>("Assessment Class");
        assessmentClass.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));
        TableColumn<PropertyAssessment, Address>  address = new TableColumn<>("Address");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<PropertyAssessment, Neighbourhood> neighbourhood = new TableColumn<>("Neighbourhood");
        neighbourhood.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        TableColumn<PropertyAssessment, Double> latitude = new TableColumn<>("Latitude");
        latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        TableColumn<PropertyAssessment, Double> longitude = new TableColumn<>("Longitude");
        longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        table.setItems(assessmentsList);
        table.getColumns().setAll(account, address, assessedValue, assessmentClass, neighbourhood, latitude, longitude);
    }
}
