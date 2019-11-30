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

import java.net.URL;
import java.util.Set;

/**
 * Map class handles the functions related to the Map tab in the property assessments program
 *
 * @author Dakota Doolaege
 */
public class MapTab {
    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    PropertyAssessments propertyAssessments;

    /**
     * Creates a new BorderPane that contains the map and a side menu of options
     *
     * @param propertyAssessments
     * @return borderPane that contains all the content for the map tab
     */
    public BorderPane start(PropertyAssessments propertyAssessments){
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
    private VBox createSearch(){
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        final Label searchLabel =  new Label("Map Options");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        TextArea textArea = new TextArea();
        textArea.setMaxWidth(200);

        //shea addition
        final Label neighbourhoodLabel = new Label("Search for neighbourhood");
        Set<String> neighbourhoodSet = propertyAssessments.getNeighborhoodSet();
        ObservableList<String> options = FXCollections.observableArrayList(neighbourhoodSet);
        final ComboBox neighbourhoodBox = new ComboBox(options);
        neighbourhoodBox.setMaxWidth(100);
        Button goButton = new Button("Go");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(neighbourhoodBox, goButton);

        final Label wardLabel = new Label("Search for ward");
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
                    PropertyAssessments neighborhood = propertyAssessments.getAssessmentsByNeighbourhood((String) neighbourhoodBox.getValue());
                    Location centre = neighborhood.getCentre();
                    if (webEngine != null) {
                        jsResetMap(centre, 15);
                    }
                    textArea.setText(neighbourhoodBox.getValue().toString() + "\n" + neighborhood.toString());
                    neighbourhoodBox.setValue(null);
                    wardBox.setValue(null);
                }
            }
        });

        goButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (wardBox.getValue() != null) {
                    PropertyAssessments ward = propertyAssessments.getAssessmentsByWard((String) wardBox.getValue());
                    Location centre = ward.getCentre();
                    if (webEngine != null) {
                        jsResetMap(centre, 12);
                    }
                    textArea.setText(wardBox.getValue().toString() + "\n" + ward.toString());
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
        vbox.getChildren().addAll(searchLabel, heatMapBtn, neighbourhoodLabel, hBox, wardLabel, hBox2, textArea);
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

        // URL mapUrl = getClass().getResource("Map.html"); //PLEASE do not keep this enabled during dev as it will burn into my free credits

        /* Use below file for testing. If you want to see fancy map, uncomment the the above
         * line and comment out the below line
         * Don't forget to switch it back
         */
        URL mapUrl = getClass().getResource("testing.html");

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
        jsArray.append("[");
        String point = "";
        int c = 0;
        for (PropertyAssessment property : propertyAssessments.getPropertyAssessments()){
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
    private void jsResetMap(Location centre, int zoom){
        StringBuilder jsArray = new StringBuilder();
        jsArray.append(centre.getLatitude() + ", " + centre.getLongitude() + ", " + zoom);
        webEngine.executeScript("setCentreAndZoom(" + jsArray.toString() + ")");
    }
}
