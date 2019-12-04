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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.*;

/**
 * Map
 *
 * @author Dakota Doolaege
 */
public class MapTab {
    private WebView webView;
    private WebEngine webEngine;
    private PropertyAssessments propertyAssessments;
    PropertyAssessments customCollection;
    private ObservableList<PropertyAssessment> properties;

    /**
     * Creates a new BorderPane that contains the map and a side menu of options
     *
     * @param propertyAssessments
     * @return borderPane that contains all the content for the map tab
     */
    public BorderPane start(PropertyAssessments propertyAssessments, WebView webView, WebEngine webEngine, ObservableList<PropertyAssessment> properties) throws IOException {
        this.propertyAssessments = propertyAssessments;
        this.webView = webView;
        this.webEngine = webEngine;
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));

        //VBox searchBox = createSearch();
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
        VBox vbox = new VBox();
        //vbox.setPadding(new Insets(10,10,10,10));

        URL mapUrl = getClass().getResource("Map.html"); //PLEASE do not keep this enabled during dev as it will burn into my free credits

        /* Use below file for testing. If you want to see fancy map, uncomment the the above
         * line and comment out the below line
         * Don't forget to switch it back
         */
        //URL mapUrl = getClass().getResource("testing.html");

        webEngine.load(mapUrl.toExternalForm());

        vbox.getChildren().addAll(webView);
        VBox.setVgrow(webView, Priority.ALWAYS);
        return vbox;
    }





}
