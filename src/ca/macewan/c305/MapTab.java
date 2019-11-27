package ca.macewan.c305;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

public class MapTab {

    public BorderPane start(){

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));

        VBox searchBox = createSearch();
        VBox mapBox = createMap();

        borderPane.setLeft(searchBox);
        borderPane.setCenter(mapBox);


        return borderPane;
    }

    private VBox createSearch(){
        VBox vbox = new VBox();
        final Label searchLabel =  new Label("Map Options");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));
        vbox.getChildren().addAll(searchLabel);
        return vbox;
    }

    private VBox createMap(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10,10,10,10));



        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        URL mapUrl = getClass().getResource("Map.html");
        webEngine.load(mapUrl.toExternalForm());
        // add webView to the scene

        vbox.getChildren().addAll(webView);
        return vbox;
    }

}
