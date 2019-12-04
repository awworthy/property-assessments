package ca.macewan.c305;

import javafx.collections.ObservableList;
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
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;

/**
 * Map tab class
 *
 * @author Dakota Doolaege
 */
public class MapTab {
    private WebView webView;
    private WebEngine webEngine;
    private PropertyAssessments customCollection;
    private PropertyAssessments propertyAssessments; //The subset
    private NumberFormat moneyMaker = NumberFormat.getCurrencyInstance();

    /**
     * Creates a new BorderPane that contains the map and a side menu of options
     *
     * @param propertyAssessments
     * @param webView
     * @param webEngine
     * @param properties
     * @return borderPane that contains all the content for the map tab
     */
    public BorderPane start(PropertyAssessments propertyAssessments, WebView webView, WebEngine webEngine, ObservableList<PropertyAssessment> properties) throws IOException {
        moneyMaker.setMaximumFractionDigits(0);
        this.webView = webView;
        this.webEngine = webEngine;
        this.propertyAssessments = propertyAssessments;
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));

        VBox mapBox = createMap();
        SideControls searchBox = new SideControls(propertyAssessments, this.webEngine, properties);

        borderPane.setLeft(searchBox.getPanel());
        borderPane.setCenter(mapBox);
        return borderPane;
    }

    /**
     * Loads the html document that contains the map
     *
     * @return returns a local html document that contains the embed map
     */
    private VBox createMap(){
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(0, 0, 0, 10));
        HBox mapControls = mapControl();
        //vbox.setPadding(new Insets(10,10,10,10));

        URL mapUrl = getClass().getResource("Map.html"); //PLEASE do not keep this enabled during dev as it will burn into my free credits

        /* Use below file for testing. If you want to see fancy map, uncomment the the above
         * line and comment out the below line
         * Don't forget to switch it back
         */
        //URL mapUrl = getClass().getResource("testing.html");

        webEngine.load(mapUrl.toExternalForm());

        vbox.getChildren().addAll(mapControls, webView);
        VBox.setVgrow(webView, Priority.ALWAYS);
        return vbox;
    }
    /**
     * Creates the controls for the map tab
     * @return
     */
    private HBox mapControl(){
        HBox controls = new HBox();
        final Label graphLabel =  new Label("Map Options");
        graphLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        controls.setPadding(new Insets(10,10,10,10));
        controls.setSpacing(10);
        controls.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
        controls.setSpacing(5);

        //Heatmap bttn
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

        //Clear Map bttn
        Button clearMapBtn = new Button();
        clearMapBtn.setMaxWidth(150);
        clearMapBtn.setText("Clear Map");
        clearMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(webEngine != null) {
                    webEngine.executeScript("clearMap()");
                }
                customCollection = null;
            }
        });

        Region spacer = new Region();
        controls.setHgrow(spacer, Priority.ALWAYS);
        controls.getChildren().addAll(graphLabel,spacer, heatMapBtn, clearMapBtn);
        return controls;
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

    /**
     * Updates the legend to the max and min value in the current data
     * @param properties
     */
    private void updateLegend(PropertyAssessments properties){
        if(properties.propertyAssessmentsList.size() > 1)
            webEngine.executeScript("legend(\"" + moneyMaker.format(properties.getMax())+ "\",\"" + moneyMaker.format(properties.getMin()) +"\")");
    }
}
