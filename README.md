# Introduction to JavaFX through Building a Scatter Plot Application
---
## Table of Contents
1. [Guide Overview](#overview)
2. [Core Concepts of JavaFX](#core-concepts)
	- JavaFX API
	- Scene Graph
3. [Getting Started with the Application](#application)
	- Application Class
	- Package Organization
	- Defining Models
	- Creating Utilities
4. [Table of Points Module](#table-module)
 	- JavaFX Library Classes
		- ObservableList
		- TableView, TableColumn
		- TextField
		- Button
		- Spinner
		- HBox and VBox
	- Adding the Module to the Application Class
5. [Graph Module](#graph-module)
   - JavaFX Library Class for Scatter Chart
       - ScatterChart
   - Adding the Graph Module to the Application Class
   - Responding to Changes in Data
6. [Conclusion](#conclusion)
7. [References](#reference)


# <a name="overview"></a>Guide Overview
This guide is an introduction to using the JavaFX library, aimed towards beginner Java programmers interested in building a user interface for their program.

After an overview of the JavaFX API and its core concepts, the guide builds a scatter plot application using the JavaFX library, with explanations of each new component. This sample program plots points on a coordinate plane from either user input or a point generator.

# <a name="core-concepts"></a>Core Concepts of JavaFX
## JavaFX API
[JavaFX](http://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html) is a Graphical User Interface (GUI) library for Java, designed to enable rapid development of applications through cross-platform support and a host of built in classes [1]. The library is written completely in Java, so there are no new languages necessary to learn.

## Scene Graph
Before writing any code, it is necessary to understand the underlying framework for JavaFX. This will streamline the application design process and promote clean code structure. 

The scene graph is the basic concept behind JavaFX [2].

![scene graph](https://cloud.githubusercontent.com/assets/8794693/13555138/569f41f4-e386-11e5-9411-462502a8d0a1.png)

**Figure 1.** An example scene graph, the underlying framework for JavaFX [2].

Figure 1 shows an example of a scene graph. Each box in the graph is a [Node](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html), a unit graphical element [3]. The nodes are organized in a tree structure, in which all nodes may have at most one parent [2]. Note that all nodes except the root node has a single parent; the root node has no parent. A leaf node is a any class that cannot have child nodes [2]. These are basic graphic components – buttons, tables, text input boxes – which cannot contain other elements. In contrast, a branch node has zero or more child nodes. Examples include regions, groups, and layout classes that organize child elements. Note that a branch node does not necessarily have a child node, as in Figure 1.

![scenegraphexample](https://cloud.githubusercontent.com/assets/8794693/13555220/b752b8bc-e388-11e5-977b-adee8fd19721.png)

**Figure 2.** Example of a scene graph for a real JavaFX application.

To take advantage of scene graphs, the nodes should be populated with useful mnemonics of graphic components. Then, assign the appropriate JavaFX library or custom class to each node. In Figure 2, the application represented by the scene graph has a "Text Input of Name" `TextField` and "Display Name Button" `Button` as leaf nodes. The root node is a branch node of type `VBox`, which organizes children vertically.

This guide will make use of scene graphs when designing the layout or adding new modules.

# <a name="application"></a>Getting Started with the Application

Ensure that the latest version of Java SDK is installed, as JavaFX comes prepackaged. Java 1.8.0_60 was used for this guide.


## Application Class

Typically, a Java program is made executable by implementing `public static void main(String[] args)` method. In the case of a JavaFX program, the class called by the `java` command must extend [Application](https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html) [4].

Requirements for the class that extends Application in a proper JavaFX program:

1. Override the `public void start(javafx.stage.Stage)` method [4].
2. Create a `Scene` from a root `Node` [4].
3. Set the `Scene` to the `Stage` [4].
4. Show the stage with `stage.show()` [4].
5. Call `launch(args)` in the `public static void main(String[] args)` method [4].

When the JavaFX program is run by the `java` command, the main method executes, calling `launch(args)`. This launches a standalone Java application, creating an instance of the Application class and calling the `start(javafx.stage.Stage)` method. Within this method, the root `Node` of the program is used as an argument to create a `Scene` instance, a class that defines the content for the application window. The `Scene` is then attached to the `Stage` argument, which defines the window itself. Finally, the `stage.show()` call shows the populated window [4].

**Table 1.** JavaFX library classes related to the Application class.

Class           | Description
------------    | -------------
javafx.application.Application  | Class that launches the JavaFX program. There is only a single Application class in each JavaFX program.
javafx.scene.Scene              | Defines the content of the application, with a single root node of the scene graph of all elements in the application structure.
Javafx.stage.Stage              | Defines the window of the application, with the application title. Contains a single scene class.

Table 1 summarizes the three classes related to `Application`.

This guide will build a scatter plot application, which will plot points in a table onto a coordinate plane. The following code implements a blank application with the title "Plot Points".

###PointPlotter.java

```
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Java FX application that plots points
 * @author Kairi Kozuma
 * @version 1.0
 */
public class PointPlotter extends Application {
	    
	@Override
	public void start(Stage stage) {
	
	    // Root node
	    Group root = new Group();
	
	    // Container associated with root node
	    Scene scene = new Scene(root, 400, 300);
	    stage.setScene(scene);
	    stage.setTitle("Plot Points");
	    stage.show();
	}
	
	public static void main(String[] args) {
	    launch(args);
	}
}
```

To run this application, compile with `javac PointPlotter.java` and run with `java PointPlotter`, similar to any other Java program. 

![Blank Application](https://cloud.githubusercontent.com/assets/8794693/13555132/2fefbdb8-e386-11e5-99e2-d1ae40983ff6.png)

**Figure 3.** A blank application screen capture.

The result is a blank application with a window size of 300 by 400, shown in Figure 3. 

Note that all JavaFX libraries used must be imported. It is common for there to be tens of import statements in a JavaFX program class.


## Package Organization

As programs increase in complexity, the number of classes grows. Placing all of these source files into a single directory can confuse developers who want to understand the organization and structure of an application. Packages solves this issue by grouping classes into separate directories.

```
.
├── model
├── program
│   ├── PointPlotter.java
└── util
```

The output of a `tree` command shows the file directory structure for this application.

**Table 2.** Common package name and description.

Package Name    | Description
------------    | -------------
model           | Models, such as a Plain Old Java Object (POJO)
program         | Program related classes, including Application class and individual graphic modules
util            | Utilities, such as a random number generator, formatter, or timer

The package names in Table 2 are common conventions in Java programs; although there are no restrictions, the names should be informative.

To prevent cluttering the source directory with compiled .class files, create a separate "class" folder in the root directory.

```
.
├── class
└── src
    ├── model
    ├── program
    │   ├── PointPlotter.java
    └── util
```

Add `package model;` at the start of the PointPlotter.java file, which tells the compiler that it is part of the model package.

To compile: `javac -d ../class program/PointPlotter.java` from the src directory.

* The `-d` option allows designation of a directory to compile the .class files [5].

To run: `java -cp ../class program.PointPlotter` from the src directory.

* The `-cp` option, which stands for "class path", will locate .class files from a given directory [6]. Notice that `program.PointPlotter` is run, as PointPlotter is part of the program package.

## Define Models

To plot points onto a scatter chart, a class that represents a point becomes necessary. This is a perfect candidate for a POJO, which will be placed in the model package. Define a Point class in Point.java, which represents an integer x, y Cartesian point. An index integer will be added to each point to differentiate between points with equivalent x and y values.

### Point.java

```
package model;

public class Point {
    private int x;
    private int y;
    private int index;
    
    public Point(int x0, int y0, int i0) {
        x = x0;
        y = y0;
        index = i0;
    }
    ... //Getters
    ... //Setters
}
```
This class also has appropriate getters and setters, as well as `equals()` and `hashCode()` methods.

The commit for the Point.java addition is found [here](https://github.com/K2Silver/ScatterPlot/commit/7e87abdd1576f26d44965bb32dd3e121176a6428).

## Create Utilities

The program will also have the ability to generate random, unique points. To implement this functionality, there will be a PointGenerator class that takes an integer number of points and ranges for x and y as arguments. A list of points of type List\<Point\> will be returned, ordered by index values. This PointGenerator is a utility for the application, so it will be placed in the util package. 

### PointGenerator.java
```
package util;

import model.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

public class PointGenerator {

    /**
     * Generate list of random unique points from limits
     * @param   numberOfPoints number of points to generate
     * @param   X_MIN minimum x value
     * @param   X_MAX maximum x value
     * @param   Y_MIN minimum y value
     * @param   Y_MAX maximum y value
     * @return  list of unique Point objects
     */
    public static List<Point> generate(int numberOfPoints,
        final int X_MIN, final int X_MAX, final int Y_MIN, final int Y_MAX) {

        // Use set to avoid duplicate points
        Set<Point> mPoints = new HashSet<Point>();

        int deltaX = X_MAX - X_MIN;
        int deltaY = Y_MAX - Y_MIN;

        while (mPoints.size() < numberOfPoints) {

            // Create random point within bounds
            int x = (int) Math.floor((deltaX + 1) * Math.random()) + X_MIN;
            int y = (int) Math.floor((deltaY + 1) * Math.random()) + Y_MIN;

            mPoints.add(new Point(x, y, mPoints.size() + 1));
        }

        // Convert set to list
        List<Point> mPointList = new ArrayList<Point>(mPoints);

        // Sort by index
        mPointList.sort(Comparator.comparing(Point::getIndex));
        return mPointList;
    }

    /**
     * Generate list of random unique points from array of boundary
     * @param  numberOfPoints number of points to generate
     * @param  BOUND array of bounds [X_MIN, X_MAX, Y_MIN, Y_MAX]
     * @return list of unique Point objects
     */
    public static List<Point> generate(int numberOfPoints, final int[] BOUND) {
        return generate(numberOfPoints, BOUND[0], BOUND[1], BOUND[2], BOUND[3]);
    }
}
```
The commit for PointGenerator.java addition is found [here](https://github.com/K2Silver/ScatterPlot/commit/86ca97e2c35d2bdcfd68953a0d41225d1112e3a1).

# <a name="table-module"></a>Table of Points Module

Define a new class in the program package that will allow user input of points into a table and use the PointGenerator to generate unique, random points.

![pointtablescenegraph](https://cloud.githubusercontent.com/assets/8794693/13555106/c099c8aa-e385-11e5-9aa7-57f7504aa051.png)

**Figure 4.** Scene graph of the table of points module.

Figure 4 shows the scene graph of the class PointTable.java. The mnemonic name is listed in each box, along with the type in brackets. For instance, The "Table of Points" will use class `TableView<Point>`. The following sections will implement each node, with an explanation of the JavaFX library used.

## JavaFX Library Classes for Table of Points

### ObservableList

An [ObservableList](https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html) is a list interface that notifies listeners attached to it of any changes [7]. This will be particularly useful for updating the chart and table whenever new Point objects are added to the list.

The code below instantiates an observable list `data` from a constructor in the `FXCollections` class. A listener is attached to the object, which will execute whenever the list changes.

```
ObservableList<Point> data = FXCollections.observableArrayList();
data.addListener((ListChangeListener.Change<? extends Point> e) -> {
        // Do something here
    }
);
```

### TableView, TableColumn

A [TableView](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html) object displays Point objects with x, y, and index as columns. Creating a TableView correctly requires the following steps:

1\. Instantiate TableView with backend data type. For this program, the Point model is used [8].

```
TableView<Point> table = new TableView<Point>();
```

2\. Bind the backend data of type ObservableList to the table. In the code below, `data` is of type `ObservableList<Point>` [8]. The table will update automatically by listening to changes on the list. 

```
table.setItems(data);
```

3\. Implement all columns for the model [8]. The guide program will show the `x`, `y`, and `index` columns; thus, three TableColumn objects will be instantiated with "X", "Y", and "Index" labels.

```
TableColumn xCol = new TableColumn("X");
xCol.setCellValueFactory(new PropertyValueFactory<Point, Integer>("x"));
xCol.setMinWidth(30);

TableColumn yCol = new TableColumn("Y");
yCol.setCellValueFactory(new PropertyValueFactory<Point, Integer>("y"));
yCol.setMinWidth(30);

TableColumn indexCol = new TableColumn("Index");
indexCol.setCellValueFactory(new PropertyValueFactory<Point, Integer>("index"));
indexCol.setMinWidth(30);
```

4\. Add all enumerated columns to the table. The order in which they were added determines the order displayed in the table [8].

```
table.getColumns().setAll(xCol, yCol, indexCol);
```

5\. Set any properties for the table. [TableView.CONTRAINED\_RESIZE\_POLICY](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html#CONSTRAINED_RESIZE_POLICY) ensures that the sum of the widths of the column equal the width of the entire table [9]. This is to prevent any extra white space in the table not occupied by a column.

```
table.setPrefWidth(300);
table.setPrefHeight(600);
table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
```

### TextField
To receive input from the user, two textfields will be used: one for x coordinate and one for y coordinate. The [TextField](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html) class allows a single line of text to be received as input [10].

```
TextField xinput = new TextField();
xinput.setPrefWidth(80);
xinput.setPromptText("x value");

TextField yinput = new TextField();
yinput.setPrefWidth(80);
yinput.setPromptText("y value");
```
In the code above, the preferred width is set to 80. The prompt text, which is shown when the text input is empty, lets the user know values to put into the field.

To retrieve text from the TextField object, use: `String myString = myTextField.getText();` [10]

To set text, use:`myTextField.setText("Some string");` [10]

### Button
The [Button](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Button.html) class is a graphical component that responds to clicks with the action set by `setOnAction(EventHandler<ActionEvent> value)` method. The [EventHanlder](https://docs.oracle.com/javase/8/javafx/api/javafx/event/EventHandler.html) is a functional interface with a `handle(T event)` method; therefore, a lambda expression can be used [12].
The following code declares a button with a specified label and some action designated in the lambda expression. The label will appear on the button in plain text.

```
Button myButton = new Button("Label");
myButton.setOnAction((ActionEvent e) -> {
        // Do some action
    }
);
```

The scatter plot application will use three buttons:

```
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
            new int[] {-5, 5, -5, 5}));
    }
);
```
The buttons operate on the `data` backend of type `ObservableList<Point>`. The `data.clear()` call removes all elements from the list. Calls to `data.add()` and `data.addAll()` adds a single `Point` and `List<Point>`, respectively. Arbitrary ranges `new int[] {-5, 5, -5, 5}` was passed as input to the PointGenerator.

This program assumes the user will input in only valid integer points in the x and y TextFields. Otherwise, the code must handle errors in the `Integer.parseInt(String)` call. The sample program uses the annotation `@SuppressWarnings("unchecked")` to avoid warnings during compilation. The fields are reset to an empty string once the new point is added.

### Spinner

The [Spinner](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Spinner.html) class lets user selection of a number from a predefined set [13].

```
Spinner numPointsSpinner = new Spinner<Integer>(1, 30, 10, 1);
```
The constructor for the `Spinner` used above is `Spinner(int min, int max, int initialValue, int amountToStepBy)`; thus, the range is [1, 30], the initial value is 10, and the values step by 1 [13].

### HBox and VBox
[HBox](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html) and [VBox](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html) are two classes that organizes its children horizontally or vertically, respectively [14], [15]. They are useful for organizing the layout of the user interface. For instance, if three buttons (button1, button2, and button 3) must be laid out horizontally, the following code can be used:

```
HBox myButtonBar = new HBox();
myButtonBar.getChildren().addAll(button1, button2, button3);

```

HBox and VBox are examples of branch nodes, as they can have children.

![mockup](https://cloud.githubusercontent.com/assets/8794693/13555615/4166e85c-e393-11e5-8056-3ac2c47bdfba.png)

**Figure 5.** Mockup for the table module.

Figure 5 shows the mockup for the table class. The scene graph in Figure 4 was based on this mockup.

The x `TextField`, y `TextField`, and the addPoint `Button` will be added to one horizontal layout. A second horizontal layout will be used, with the numPointsSpinner `Spinner`, generatePoints `Button`, and clearPoints `Button`. Finally, the table and two horizontal layouts will be ordered vertically in a `VBox` layout.

The [Insets](https://docs.oracle.com/javase/8/javafx/api/javafx/geometry/Insets.html) class defines offsets for the HBox or VBox layout [16].

The following code implements the layout:

```
HBox pointAddBox = new HBox();
HBox pointGenBox = new HBox();
VBox root = new VBox();
...
...

pointAddBox.setSpacing(10);
pointAddBox.getChildren().addAll(xinput, yinput, addPoint);
pointAddBox.setPadding(new Insets(10, 10, 10, 10));

pointGenBox.setSpacing(10);
pointGenBox.getChildren().addAll(numPointsSpinner, generatePoints, clearPoints);
pointGenBox.setPadding(new Insets(10, 10, 10, 10));

// Add all nodes to root node
root.getChildren().addAll(table, pointAddBox, pointGenBox);

```

## Adding the Module to the Application Class

Since the table module was defined externally, there are several steps necessary to add it to the main Application class.

1\. Create a constructor for the module with instantiation of all nodes. The Application class will hold an instance of the module as a private variable; thus, a constructor is necessary. Argument of type `ObservableList<Point>` will be used.

### PointTable.java
```
package program;
...
...
public class PointTable {
    // Point data
    private ObservableList<Point> data;
    
    ...
    ...
    
    @SuppressWarnings("unchecked")
    public PointTable(ObservableList<Point> data) {
        this.data = data;
        
        // Instantiate all nodes here
    }
    ...
    ...

```
### PointPlotter.java
```

package program;
...
...
public class PointPlotter extends Application {

    // ObservableList of Points
    private ObservableList<Point> data;

    // Table module
    private PointTable tableModule;

    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) {

        ...
        ...

        // Initialize the observable list
        data = FXCollections.observableArrayList();

        // Initialize instance of table module
        tableModule = new PointTable(data);

        ...
        ...
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

The call `data = FXCollections.observableArrayList();` instantiates the `ObservableList<Point>`. The variable `data` is passed as an argument to the constructor for the PointTable class.

2\. Obtain the root node of the module by defining and calling a `getView()` method that returns the root node of the PointTable.java.

### PointTable.java

```
package program;
...
...
public class PointTable {
    // Root node for point table module
    private VBox root;
    ...
    ...
    /**
     * Get the root node for the module
     * @return Node root node
     */
    public Node getView() {
        return root;
    }
}
```

The `root` for the PointTable class is a `VBox`. Since `Node` is a superclass of `VBox`, it is safe to be returned in the `getView()` method.


3\. Add the root node of the module to the root node of the application.

The `node` returned by the `getView` method is added to the root of the Application class with the following:

```
root.getChildren().addAll(tableModule.getView());
```

![scatterplotoriginal](https://cloud.githubusercontent.com/assets/8794693/13555108/c09abdf0-e385-11e5-87bc-124ea6bfcfa9.png)

**Figure 6.** Scene graph for the main Application class.

The scene graph for the PointPlotter.java, which extends `Application`, is shown in Figure 6. The table module is added as a single node, as the details of that module are defined in another scene graph and abstracted away here.

After adding the module and linking with the main Application class, running the program shows the following window:

![tablemodulewithpoints](https://cloud.githubusercontent.com/assets/8794693/13555109/c09b2696-e385-11e5-87df-0be4c7515e2c.png)

**Figure 7.** Scatter plot application after adding the table module.

After adding the table module, compiling and running the PointPlotter.java file should result in the window shown in Figure 7. The application can now take user input to add new points to the ObservableList, as well as generate unique, random points within a range. The points can be cleared with a button click.

The commit for the table module can be found [here](https://github.com/K2Silver/ScatterPlot/commit/e28217a408c20adb082416f73d82d288143175dc).

# <a name="graph-module"></a>Graph Module

The scatter plot application is incomplete without a coordinate plane to accompany the table of points. This section will implement the graph module of the application.

![pointgraphscenegraph](https://cloud.githubusercontent.com/assets/8794693/13555110/c09d3bca-e385-11e5-837c-9bb57306b4ca.png)

**Figure 8.** Scene graph for the graph module.

Before implementing the module, consult the scene graph in Figure 8. This graph is simple compared to that of the table module in Figure 4. Although only the ScatterPlot node is necessary for this module, a `VBox` class was used as the root to allow additional components in the future.

## JavaFX Library Class for Scatter Chart

### ScatterChart

The [ScatterChart](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/ScatterChart.html) JavaFX library class saves coders the trouble of implementing a custom coordinate plane class [17].

The constructor for `ScatterChart` is `ScatterChart(Axis<X> xAxis, Axis<Y> yAxis)`. The guide will use [NumberAxis](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/NumberAxis.html), which is a subclass of `Axis<T>` to set the X and Y axes [18]. Three double values will be passed to the constructor for `NumberAxis`: lower bound, upper bound, and the tick unit [18].

```
// Define range for coordinate plane
private static final int X_MAX = 10;
private static final int X_MIN = -10;
private static final int Y_MAX = 10;
private static final int Y_MIN = -10;
private static final int RESOLUTION = 1;

// Define public bounds to random points
public static final int[] BOUND = new int[] {X_MIN + 1, X_MAX - 1, Y_MIN + 1, Y_MAX - 1};
```
The sample application uses integer values defined statically, which are cast to double automatically in the constructor. An additional integer array `BOUND` is declared, which define the range of valid points. This will be passed into the point generator used by the table module. 

The following code constructs an instance of `ScatterChart` with the two `NumberAxis` objects, using the static values defined previously as arguments.

```
NumberAxis xAxis = new NumberAxis(X_MIN, X_MAX, RESOLUTION);
NumberAxis yAxis = new NumberAxis(Y_MIN, Y_MAX, RESOLUTION);
ScatterChart<Number, Number> scatterChart = scatterChart = new ScatterChart<Number,Number>(xAxis,yAxis);
```

To clear all points within the chart, call `scatterChart.getData().clear()` [18]. The program will define a function to make this accessible outside of the module:

```
public void clear() {
    scatterChart.getData().clear();
}
```

Adding a point requires the following steps:

1. Create an [XYChart.Series](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/XYChart.Series.html) instance. This object contains the set of points that will be plotted [19].
2. Add [XYChart.Data](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/XYChart.Data.html) objects to the `XYChart.Series`. This class represents the individual points [19].
3. Add the `XYChart.Series` to the `ScatterChart` with `scatterChart.getData().add(XYChart.Series)` function call [19].

These steps are encapsulated in the following code:

```
@SuppressWarnings("unchecked")
public void plotPoints() {
    clear();
    XYChart.Series pointSeries = new XYChart.Series();
    for (Point p : data) {
        pointSeries.getData().add(new XYChart.Data(p.getX(), p.getY()));
    }
    scatterChart.getData().add(pointSeries);
}    
```

A call to `clear()` uses the function declared previously to clear points. It is equivalent to `scatterChart.getData().clear()`. A for loop is used to iterate through the `data`, which is of type `ObservableList<Point>`. For each `Point` in `data`, construct a new `XYChart.Data` object and add it to the `XYChart.Series` instance variable, using getters to obtain the x and y values.

## Adding the Graph Module to the Application Class

The same steps used to add the table will be used for the graph module.

1\. Create a constructor for the module with the same argument of type `ObservableList<Point>`. The two modules will share the same backend `data`.

### PointGraph.java
```
package program;
...
...
public class PointGraph {
    // Point data
    private ObservableList<Point> data;
    
    ...
    ...
    
    public PointGraph(ObservableList<Point> data) {
        this.data = data;
        
        // Instantiate all nodes here
    }
    ...
    ...

```

### PointPlotter.java
```

package program;
...
...
public class PointPlotter extends Application {

    // ObservableList of Points
    private ObservableList<Point> data;

    // Table module
    private PointTable tableModule;
    
    // Graph module
    private PointGraph graphModule;

    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) {

        ...
        ...

        // Initialize the observable list
        data = FXCollections.observableArrayList();

        // Initialize instance of table module
        tableModule = new PointTable(data);
        
        // Initialize instance of graph module
        graphModule = new PointGraph(data);

        ...
        ...
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Note that the only change in the PointPlotter.java file is the construction of the `PointGraph graphModule` object.

2\. Obtain the root node of the module by defining and calling a `getView()` method that returns the root node of the PointGraph.java

### PointGraph.java

```
package program;
...
...
public class PointGraph {
    // Root node for point graph module
    private VBox root;
    ...
    ...
    /**
     * Get the root node for the module
     * @return Node root node
     */
    public Node getView() {
        return root;
    }
}
```

The code is identical to that of PointTable.java, except for the class name.

3\. Add the root node of the module to the Application scene graph.

The root node returned from the graph module cannot be added to the root node of the application class, because the `Group()` object does not support ordering children vertically or horizontally. Instead, an `HBox` node will be used as the root.

![applicationmockup](https://cloud.githubusercontent.com/assets/8794693/13579164/e4af581a-e469-11e5-8fd3-6cd9b09915c3.png)
![scatterplot](https://cloud.githubusercontent.com/assets/8794693/13579165/e4afd696-e469-11e5-99cf-82583679bb30.png)

**Figure 9.** Application mockup with both table and graph modules and the corresponding scene graph.

As before, a mockup will be used to design a scene graph. Figure 9 shows the mockup with both graph and table modules, with its corresponding scene graph. In accordance with the scene graph, replace the `Group` with `HBox` and add all the module nodes as children.

```
HBox root = new HBox();
root.getChildren().addAll(graphModule.getView(), tableModule.getView());
```


## Responding to Changes in Data

Although functions are implemented to plot and clear points, the graph module still needs to respond automatically to changes in the `ObservableList<Point> data` object. This can be achieved by attaching a [ListChangeListener.Change<E>](https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ListChangeListener.Change.html) to the `data` variable [20].

```
data.addListener((ListChangeListener.Change<? extends Point> e) -> {
        plotPoints();
    }
);
```
The lambda expression above calls the `plotPoints()` function defined earlier whenver the list changes.


The final commit can be found [here](https://github.com/K2Silver/ScatterPlot/commit/f158c03d2737eb433a98319d5537861b516f6f48).

# <a name="conclusion"></a>Conclusion

Those who have finished the guide should now have a functioning scatter plot application. 

![graphmodulewithpoints](https://cloud.githubusercontent.com/assets/8794693/13555105/c098fef2-e385-11e5-9192-97667e5131a8.png)

**Figure 11.** Screen capture of the complete scatter plot application.

Compiling and running the program should show an application window resembling Figure 11. The graph module is to the left of the table module. Any time the data in the table changes, the graph replots the data.

Developers should use this guide as a foundation and build their own programs with user interfaces.

# <a name="reference"></a>References
[1] Oracle, *JavaFX - The Rich Client Platform*, Oracle, n.d. [Online]. Available: oracle.com, http://www.oracle.com/technetwork/java/javase/overview/javafx-overview-2158620.html [Accessed: 6 Mar. 2016]
 
[2] S. Hommel, *Working with the JavaFX Scene Graph*, Oracle, Sep. 2013. [Online]. Available: docs.oracle.com, https://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm [Accessed: 6 Mar. 2016]

[3] Oracle, *Class Node*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Node.html [Accessed: 6 Mar. 2016]

[4] Oracle, *Class Application*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html [Accessed: 6 Mar. 2016]

[5] Oracle, *javac - Java programming language compiler*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javac.html [Accessed: 6 Mar. 2016]

[6] Oracle, *java - the Java application launcher*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/java.html [Accessed: 6 Mar. 2016]

[7] Oracle, *Interface ObservableList\<E\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html [Accessed: 6 Mar. 2016]

[8] Oracle, *Class TableView\<S\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html [Accessed: 6 Mar. 2016]

[9] Oracle, *Class TableView\<S\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html#CONSTRAINED_RESIZE_POLICY [Accessed: 6 Mar. 2016]

[10] Oracle, *Class TextField*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html [Accessed: 6 Mar. 2016]

[11] Oracle, *Class Button*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Button.html [Accessed: 6 Mar. 2016]

[12] Oracle, *Interface EventHandler\<T extends Event\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/event/EventHandler.html [Accessed: 6 Mar. 2016]

[13] Oracle, *Class Spinner \<T\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Spinner.html [Accessed: 6 Mar. 2016]

[14] Oracle, *Class HBox*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html [Accessed: 6 Mar. 2016]

[15] Oracle, *Class VBox*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/VBox.html [Accessed: 6 Mar. 2016]

[16] Oracle, *Class Insets*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/geometry/Insets.html [Accessed: 6 Mar. 2016]

[17] Oracle, *Class ScatterChart\<X,Y\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/ScatterChart.html [Accessed: 6 Mar. 2016]

[18] Oracle, *Class NumberAxis*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/NumberAxis.html [Accessed: 6 Mar. 2016]

[19] A. Redko, *12 Table View*, Oracle, Sep. 2013. [Online]. Available docs.oracle.com, https://docs.oracle.com/javafx/2/ui_controls/table-view.htm [Accessed: 6 Mar. 2016]

[20] Oracle, *Class ListChangeListener.Change\<E\>*, Oracle, n.d. [Online]. Available docs.oracle.com, https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ListChangeListener.Change.html [Accessed: 6 Mar. 2016]
