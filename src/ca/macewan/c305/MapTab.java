package ca.macewan.c305;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

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
        final Label searchLabel =  new Label("Map Options");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        Button heatMapBtn = new Button();
        heatMapBtn.setText("Activate Heatmap");
        heatMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(webEngine != null) {
                    jsLoadProperties();
                }

            }
        });
        vbox.getChildren().addAll(searchLabel, heatMapBtn);
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

        //URL mapUrl = getClass().getResource("Map.html"); //PLEASE do not keep this enabled during dev as it will burn into my free credits

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

}
