package program;

import model.Point;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

/*
 * Java FX module that allows for plotting points on coordinate plane
 * @author Kairi Kozuma
 * @version 1.0
 */
public class PointGraph {

    // Define range for coordinate plane
    private static final int X_MAX = 10;
    private static final int X_MIN = -10;
    private static final int Y_MAX = 10;
    private static final int Y_MIN = -10;
    private static final int RESOLUTION = 1;

    // Define public bounds to random points
    public static final int[] BOUND
        = new int[] {X_MIN + 1, X_MAX - 1, Y_MIN + 1, Y_MAX - 1};

    private final NumberAxis xAxis;
    private final NumberAxis yAxis;
    private final ScatterChart<Number,Number> scatterChart;
    private final VBox root;

    private final ObservableList<Point> data;

    /**
     * Construct PathGraph module
     * @param  data ObservableList of Point
     */
    public PointGraph(ObservableList<Point> data) {
        this.data = data;
        xAxis = new NumberAxis(X_MIN, X_MAX, RESOLUTION);
        yAxis = new NumberAxis(Y_MIN, Y_MAX, RESOLUTION);
        scatterChart = new ScatterChart<Number,Number>(xAxis,yAxis);
        root = new VBox();

        scatterChart.setPrefHeight(600);
        scatterChart.setPrefWidth(600);
        xAxis.setLabel("X Value");
        yAxis.setLabel("Y Value");
        scatterChart.setTitle("Points");
        scatterChart.setLegendVisible(false);

        data.addListener((ListChangeListener.Change<? extends Point> e) -> {
                plotPoints();
            }
        );

        root.getChildren().addAll(scatterChart);
    }

    /**
     * Clear the points on the graph
     */
    public void clear() {
        scatterChart.getData().clear();
    }

    /**
     * Update the points on the graph
     */
    @SuppressWarnings("unchecked")
    public void plotPoints() {
        clear();
        XYChart.Series pointSeries = new XYChart.Series();
        for (Point p : data) {
            pointSeries.getData().add(new XYChart.Data(p.getX(), p.getY()));
        }
        scatterChart.getData().add(pointSeries);
    }

    /**
     * Get the root view of the module
     * @return VBox root view
     */
    public Node getView() {
        return root;
    }
}
