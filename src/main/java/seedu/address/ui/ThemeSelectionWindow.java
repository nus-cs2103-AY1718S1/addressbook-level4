package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ThemeSelectionWindow extends UiPart<Region> {

    private static final String FXML = "ThemeSelectionWindow.fxml";
    private Stage primaryStage;

    public ThemeSelectionWindow() {
        super(FXML);

        // Create and set stage
        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());
        this.primaryStage.setScene(scene);

        // Configure UI
        this.primaryStage.setTitle("Theme Selection");

    }

    public void show() {
        primaryStage.showAndWait();
    }
}
