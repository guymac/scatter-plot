package program;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * Java FX application that plots points
 * @author Kairi Kozuma
 * @version 2.0
 */
public class PointPlotter extends Application 
{
    
    // Must override this method for class that extends Application
    @Override
    public void start(Stage stage) 
    {
        Pane root;
        
        try
        {
            root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
            Platform.exit();
            return;
        }

        // Container associated with root node
        Scene scene = new Scene(root);

        // Set the scene for the stage
        stage.setScene(scene);

        // Set stage title
        stage.setTitle("Plot Points");

        // Show the window
        stage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
