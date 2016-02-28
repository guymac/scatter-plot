package program;

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

    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) {

        // Root node
        Group root = new Group();

        // Container associated with root node
        Scene scene = new Scene(root, 400, 300); // Size of window 400 x 300

        // Set the scene for the stage
        stage.setScene(scene);

        // Set stage title
        stage.setTitle("Plot Points");

        // Show the window
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
