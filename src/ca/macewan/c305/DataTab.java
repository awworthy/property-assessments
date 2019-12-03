package ca.macewan.c305;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        ObservableList<PieChart.Data> pieData = getData();
        VBox searchBox = createSearch();
        VBox graphBox = createGraph(pieData);


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

    private VBox createGraph(ObservableList<PieChart.Data> pieData) {
        VBox vbox = new VBox();
        final Label graphLabel = new Label("Data Visualization");
        graphLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setBorder(new Border(new BorderStroke(Color.SILVER,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));

        final PieChart chart = new PieChart(pieData);
        chart.setTitle("Property Assessments by Range of Value");
        chart.setLabelsVisible(false);
        Node ns = chart.lookup(".chart-legend");
        ns.setStyle("-fx-background-color: #505078, blue;" +
                "-fx-background-insets: 0,1;" +
                "-fx-background-radius: 6,5;" +
                "-fx-padding: 6px;");
        vbox.getChildren().add(chart);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    public ObservableList<XYChart<String, Integer>> getBarData()
    {
        // declare set
        Set<String> neighbourhoodSet = propertyAssessments.getNeighborhoodSet();
        ObservableList<XYChart<String, Integer>> data = FXCollections. observableArrayList();
        XYChart.Series<String, Integer> neighbourhoodData = new XYChart.Series<>();
        for (String neighbourhood : neighbourhoodSet) {
            PropertyAssessments neighbourhoodAssessments = propertyAssessments.getAssessmentsByNeighbourhood(neighbourhood);
            neighbourhoodData.getData().add(new XYChart.Data<>(neighbourhood, neighbourhoodAssessments.getMean()));
        }
        return data;
    }

    private ObservableList<PieChart.Data> getData()
    {
        long max = propertyAssessments.getMax();
        int maxInt = (int)max;
        List<Integer> list = new ArrayList<>();

        list.add(200000);
        list.add(500000);
        list.add(1000000);
        list.add(50000000);
        list.add(maxInt);

        List<Integer> quantity = new ArrayList<>();
        while(quantity.size() < 5)
            quantity.add(0);

        for (PropertyAssessment address : propertyAssessments.getPropertyAssessments()) {
            if (address.getValue() < list.get(0)) {
                quantity.set(0, quantity.get(0) + 1);
            } else if (address.getValue() < list.get(1)) {
                quantity.set(1, quantity.get(1) + 1);
            } else if (address.getValue() < list.get(2)) {
                quantity.set(2, quantity.get(2) + 1);
            } else if (address.getValue() < list.get(3)) {
                quantity.set(3, quantity.get(3) + 1);
            } else if (address.getValue() < list.get(4)) {
                quantity.set(4, quantity.get(4) + 1);
            }
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        for (int i = 0; i < 5 ; i++ ) {
            Integer key = (list.get(i)/100000);
            Integer value = quantity.get(i);
            String string = "Less than $" + key.toString() + "00000";
            pieChartData.add(new PieChart.Data(string, value));
        }
        return pieChartData;
    }
}
