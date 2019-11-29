package ca.dakota.cmpt305;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class DrawingDemo extends Application {
    private GraphicsContext gc;
    private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.BLACK};
    int colorIdx = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Demo");
        FlowPane rootNode = new FlowPane();
        rootNode.setAlignment(Pos.CENTER);
        Scene scene = new Scene(rootNode, 450,450);
        primaryStage.setScene(scene);
        Canvas canvas = new Canvas(400, 400);
        gc = canvas.getGraphicsContext2D();

        drawShapes(gc, colors[colorIdx]);

        Button btnChangeColor = new Button("Change Color");
        btnChangeColor.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                colorIdx = ++colorIdx % colors.length;
                drawShapes(gc, colors[colorIdx]);
            }
        });

        rootNode.getChildren().addAll(canvas, btnChangeColor);
        primaryStage.show();
        }

        private void drawShapes(GraphicsContext gc, Color color){
        gc.setStroke(color);
        gc.setFill(color);

        gc.strokeLine(0,0,200,200);
        gc.strokeOval(100,100,200,200);
        gc.strokeRect(0,200,50,200);
        gc.fillOval(0,0,20,20);
        gc.fillRect(100,320,300,40);

        gc.setFont(new Font(20));
        gc.fillText("Drawing on canvas", 60, 50);

    }
}
