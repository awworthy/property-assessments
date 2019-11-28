package ca.macewan.c305;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MapTab {

    public static BorderPane start(){

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));

        VBox searchBox = createSearch();
        VBox mapBox = createMap();

        borderPane.setLeft(searchBox);
        borderPane.setCenter(mapBox);


        return borderPane;
    }

    private static VBox createSearch(){
        VBox vbox = new VBox();
        final Label searchLabel =  new Label("Find Property Assessment");
        searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.getChildren().addAll(searchLabel);
        return vbox;
    }

    private static VBox createMap(){
        VBox vbox = new VBox();
        final Label mapLabel = new Label("Edmonton Property Assessments");
        mapLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.getChildren().addAll(mapLabel);
        return vbox;
    }

}
