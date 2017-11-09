package seedu.address;

import javafx.application.Preloader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.ui.SplashScreen;

//@@author fongwz
/**
 * Preloader class
 */
public class FirstPreloader extends Preloader {

    private static final Double WIDTH = 506.0;
    private static final Double HEIGHT = 311.0;

    private Stage stage;

    /**
     * Method to create splash screen
     * @return Scene containing splashscreen
     */
    private Scene createPreloaderScene() {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.getRoot().setBackground(Background.EMPTY);
        Scene scene = new Scene(splashScreen.getRoot(), 460, 250);
        scene.setFill(Color.TRANSPARENT);
        return scene;
    }

    /**
     * Starts the splash screen
     */
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createPreloaderScene());
        stage.initStyle(StageStyle.TRANSPARENT);

        centerStage(stage, WIDTH, HEIGHT);
        stage.show();

    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == Preloader.StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

    /**
     * Roughly centers the stage to your computer screen
     */
    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
}
