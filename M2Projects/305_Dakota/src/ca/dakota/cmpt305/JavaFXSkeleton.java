package ca.dakota.cmpt305;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class JavaFXSkeleton extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("javaFX Skeleton");
        FlowPane rootNode = new FlowPane();
        Scene scene = new Scene(rootNode, 300, 200);
        primaryStage.setScene(scene);
        Label label = new Label("This is a JavaFX label");
        rootNode.getChildren().add(label);
        primaryStage.show();
    }
}
