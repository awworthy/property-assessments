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
import java.util.ArrayList;
import java.util.List;

public class MapTab {

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
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

        Button heatMapBtn = new Button();
        heatMapBtn.setText("Activate Heatmap");
        heatMapBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(webEngine != null) {
                    List<String> properties = new ArrayList<String>();
                    properties.add("123,-134");
                    properties.add("123423,-9134");
                    properties.add("15523,-1374");

                    System.out.println(formatJS(properties));
                    webEngine.executeScript("loadHeatMap(" + formatJS(properties)+")");
                }

            }
        });
        vbox.getChildren().addAll(searchLabel, heatMapBtn);
        return vbox;
    }

    private VBox createMap(){
        VBox vbox = new VBox();
        //vbox.setPadding(new Insets(10,10,10,10));



        //URL mapUrl = getClass().getResource("Map.html");
        URL mapUrl = getClass().getResource("testing.html");//used for testing so I don't use up my credits lol

        webEngine.load(mapUrl.toExternalForm());


        vbox.getChildren().addAll(webView);
        VBox.setVgrow(webView, Priority.ALWAYS);
        return vbox;
    }

    private String formatJS(List<String> properties){
        StringBuilder jsArray = new StringBuilder();
        jsArray.append("[");
        for (String property : properties){
            jsArray.append(property+",");
        }

        jsArray.append("]");
        return jsArray.toString();
    }

}
