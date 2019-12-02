package ca.macewan.c305;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.List;

public class DataTab {

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
        borderPane.setPadding(new Insets(10));

        VBox searchBox = createSearch();
        VBox graphBox = createGraph();
        ObservableList<XYChart.Series<String, Number>> data = getData();

        borderPane.setLeft(searchBox);
        borderPane.setCenter(graphBox);

        return borderPane;
    }

    private VBox createSearch() {
        VBox vbox = new VBox();
        final Label graphLabel =  new Label("Visualization Style");
        graphLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        Button barGraphButton = new Button();
        barGraphButton.setText("Bar Graph");
        barGraphButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                
            }
        });
        vbox.getChildren().addAll(graphLabel, barGraphButton);
        return vbox;
    }

    private VBox createGraph() {
        VBox vbox = new VBox();
        final Label graphLabel = new Label("Data Visualization");
        graphLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        return vbox;
    }

    public ObservableList<PieChart.Data> getChartData()
    {
        // declare List
        for (PropertyAssessment property : propertyAssessments.getPropertyAssessments()) {

        }
        ObservableList<PieChart.Data> data = FXCollections. observableArrayList();
        data.add(new PieChart.Data("China", 1275));
        data.add(new PieChart.Data("India", 1017));
        data.add(new PieChart.Data("Brazil", 172));
        data.add(new PieChart.Data("UK", 59));
        data.add(new PieChart.Data("USA", 285));
        return data;
    }

    private ObservableList<XYChart.Series<String, Number>> getData()
    {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Assessed Value by Neighbourhood");
        for (PropertyAssessment address : propertyAssessments.getPropertyAssessments()) {
            series.getData().add(new XYChart.Data<>(address.getNeighbourhood().toString(), address.getValue()));
        }

        ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
        data.add(series);
        return data;
    }
}
