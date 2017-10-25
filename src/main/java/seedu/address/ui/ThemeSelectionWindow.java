package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.UserPrefs;

/**
 * Theme selection window
 */
public class ThemeSelectionWindow extends UiPart<Region> {

    private static final String FXML = "ThemeSelectionWindow.fxml";
    private Stage primaryStage;
    private UserPrefs prefs;
    private Image theme1Thumbnail;
    private Image theme2Thumbnail;

    @FXML
    private ImageView thumbnail1;

    @FXML
    private ImageView thumbnail2;

    @FXML
    private Label currentThemeLabel;

    public ThemeSelectionWindow(UserPrefs prefs) {
        super(FXML);
        this.prefs = prefs;

        // Create and set stage
        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());
        this.primaryStage.setScene(scene);

        // Set theme
        scene.getStylesheets().add(
                MainApp.class.getResource("/view/" + prefs.getCurrentUserTheme() + ".css").toExternalForm());

        // Configure UI
        this.primaryStage.setTitle("Theme Selection");

        /**
         * TODO: Scalable implementation using additional JSON file
         */
        theme1Thumbnail = new Image(
                MainApp.class.getResource("/images/theme_thumbnails/DarkTheme.png").toExternalForm());
        theme2Thumbnail = new Image(
                MainApp.class.getResource("/images/theme_thumbnails/LightTheme.png").toExternalForm());

        thumbnail1.setImage(theme1Thumbnail);
        thumbnail2.setImage(theme2Thumbnail);

        currentThemeLabel.setText("Current Theme: " + prefs.getCurrentUserTheme());


    }

    public void show() {
        primaryStage.showAndWait();
    }

    /**
     * select theme 1
     */
    public void handleTheme1() {
        System.out.println("Theme 1 selected!");
        prefs.setCurrentUserTheme("DarkTheme");
        currentThemeLabel.setText("Current Theme: " + prefs.getCurrentUserTheme());
    }

    /**
     * select theme 2
     */
    public void handleTheme2() {
        System.out.println("Theme 2 selected!");
        prefs.setCurrentUserTheme("LightTheme");
        currentThemeLabel.setText("Current Theme: " + prefs.getCurrentUserTheme());
    }
}
