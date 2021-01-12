package program;

import java.util.function.UnaryOperator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import model.Point;
import util.PointSupplier;

public class PlotController
{
    private static final String REGEX_INT = "-?([1-9][0-9]*)?";
    
    // Define range for coordinate plane
    @FXML private Integer X_MAX;
    @FXML private Integer X_MIN;
    @FXML private Integer Y_MAX;
    @FXML private Integer Y_MIN;
    @FXML private Integer RESOLUTION;

    @FXML private TableView <Point> table;
    @FXML TableColumn <Point, Number> xCol;
    @FXML TableColumn <Point, Number> yCol;
    @FXML TableColumn <Point, Number> indexCol;
    @FXML private Spinner <Integer> numPointsSpinner;
    @FXML private TextField xinput;
    @FXML private TextField yinput;
    @FXML private Button generatePoints, clearPoints, addPoint;

    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private ScatterChart <Number, Number> scatterChart;

    // ObservableList of Points
    private ObservableList <Point> data = FXCollections.observableArrayList();

    private Point upperLeft, lowerRight;
    
    public void initialize()
    {
        UnaryOperator <Change> filter = val -> val.getControlNewText().matches(REGEX_INT) ? val : null;
        
        upperLeft = new Point(X_MIN + 1, Y_MAX - 1);
        lowerRight = new Point(X_MAX - 1, Y_MIN + 1);
        xinput.setTextFormatter(new TextFormatter<>(filter));
        yinput.setTextFormatter(new TextFormatter<>(filter));

        data.addListener((ListChangeListener.Change<? extends Point> e) -> {
            plotPoints();
        });
    }

    public void addPoint()
    {
        data.add(
                new Point(Integer.parseInt(xinput.getText()), Integer.parseInt(yinput.getText())));
        xinput.clear();
        yinput.clear();
    }
    
    public void generatePoints()
    {
        data.clear();
        data.addAll(PointSupplier.generate(numPointsSpinner.getValue(), upperLeft, lowerRight));
    }
    
    public void clearPoints()
    {
        data.clear();
    }
 
    /**
     * Update the points on the graph
     */
    // @SuppressWarnings("unchecked")
    public void plotPoints()
    {
        scatterChart.getData().clear();
        XYChart.Series <Number, Number> pointSeries = new XYChart.Series <>();
        for (Point p : data)
        {
            pointSeries.getData().add(new XYChart.Data<>(p.x(), p.y()));
        }
        scatterChart.getData().add(pointSeries);
    }

    public ObservableList<Point> getPointItems()
    {
        return data;
    }
}
