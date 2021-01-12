/**
 * Defines a module
 */

module plotter
{
    exports util;
    exports model;
    exports program;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    // allows package to use FXMLLoader
    opens program to javafx.fxml;
}