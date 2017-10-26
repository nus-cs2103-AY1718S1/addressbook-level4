package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * A UI component that displays information on which theme is currently selected
 */
public class ThemeSelectorCard extends UiPart<Region> {

    private static final String FXML = "ThemeSelectorCard.fxml";

    @FXML
    private Circle themeCircle;

    @FXML
    private Label themeLabel;

    public ThemeSelectorCard(String themeName) {
        super(FXML);
        if (themeName.equals("blue")) {
            themeLabel.textProperty().setValue("Blue");
            themeCircle.setFill(Paint.valueOf("#616fd4"));
        } else if (themeName.equals("dark")) {
            themeLabel.textProperty().setValue("Dark");
            themeCircle.setFill(Paint.valueOf("#494b5c"));
        } else if (themeName.equals("light")) {
            themeLabel.textProperty().setValue("Light");
            themeCircle.setFill(Paint.valueOf("#dddff0"));
        }
    }
}
