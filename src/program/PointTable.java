package program;

import model.Point;
import program.PointGraph;
import util.PointGenerator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;

/*
 * Java FX module that allows for user input and generation of points
 * @author Kairi Kozuma
 * @version 1.0
 */
public class PointTable {

    // Root node for point table module
    private VBox root;

    // Branch nodes to organize leaf nodes horizontally
    private HBox pointAddBox;
    private HBox pointGenBox;

    // Graphic elements (leaf nodes)
    private TableView<Point> table;
    private Spinner<Integer> numPointsSpinner;
    private TextField xinput;
    private TextField yinput;

    // Point data
    private ObservableList<Point> data;

    /**
     * Construct new PointTable module
     * @param   data of Points
     */
    @SuppressWarnings("unchecked")
    public PointTable(ObservableList<Point> data) {

        this.data = data;

        // Initialize components
        root = new VBox();

        pointAddBox = new HBox();
        pointGenBox = new HBox();

        table = new TableView<Point>();
        numPointsSpinner = new Spinner<Integer>(1, 30, 10, 1);
        xinput = new TextField();
        yinput = new TextField();

        numPointsSpinner.setPrefWidth(150);

        // Implement button handlers
        Button clearPoints = new Button("Clear");
        clearPoints.setOnAction((ActionEvent e) -> {
                data.clear();
            }
        );

        Button addPoint = new Button("Add");
        addPoint.setOnAction((ActionEvent e) -> {
                data.add(new Point(Integer.parseInt(xinput.getText()), Integer.parseInt(yinput.getText()), data.size() + 1));
                xinput.setText("");
                yinput.setText("");
            }
        );

        Button generatePoints = new Button("Random");
        generatePoints.setOnAction((ActionEvent e) -> {
                data.clear();
                data.addAll(PointGenerator.generate(numPointsSpinner.getValue(),
                    PointGraph.BOUND));
            }
        );

        // Set input text properties
        xinput.setPrefWidth(80);
        xinput.setPromptText("x value");
        yinput.setPrefWidth(80);
        yinput.setPromptText("y value");

        // Set table properties
        table.setPrefWidth(300);
        table.setPrefHeight(600);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Bind the data to table
        table.setItems(data);

        // Implement columns for points
        TableColumn xCol = new TableColumn("X");
        xCol.setCellValueFactory(new PropertyValueFactory<Point, Integer>("x"));
        xCol.setMinWidth(30);

        TableColumn yCol = new TableColumn("Y");
        yCol.setCellValueFactory(new PropertyValueFactory<Point, Integer>("y"));
        yCol.setMinWidth(30);

        TableColumn indexCol = new TableColumn("Index");
        indexCol.setCellValueFactory(new PropertyValueFactory<Point, Integer>("index"));
        indexCol.setMinWidth(30);

        // Add enumerated columns for points
        table.getColumns().setAll(xCol, yCol, indexCol);

        // Add leaf nodes to branch nodes
        pointAddBox.setSpacing(10);
        pointAddBox.getChildren().addAll(xinput, yinput, addPoint);
        pointAddBox.setPadding(new Insets(10, 10, 10, 10));

        pointGenBox.setSpacing(10);
        pointGenBox.getChildren().addAll(numPointsSpinner, generatePoints, clearPoints);
        pointGenBox.setPadding(new Insets(10, 10, 10, 10));

        // Add all nodes to root node
        root.getChildren().addAll(table, pointAddBox, pointGenBox);
    }

    /**
     * Get the root node for the module
     * @return Node root node
     */
    public Node getView() {
        return root;
    }
}
