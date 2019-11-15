package ca.dakota.cmpt305;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class JavaFXEventDemo extends Application {
    private Label response;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Label");
        FlowPane rootNode = new FlowPane(10,10);
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 300, 200);
        primaryStage.setScene(scene);
        response = new Label("push a button");
        Button btnAlpha = new Button("Alpha");
        Button btnBeta= new Button("Beta");

        btnAlpha.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent event){
               response.setText("Alpha was pressed");
           }
        });

        btnBeta.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                response.setText("Beta was pressed");
            }
        });

        rootNode.getChildren().addAll(btnAlpha, btnBeta, response);
        primaryStage.show();
    }
}
