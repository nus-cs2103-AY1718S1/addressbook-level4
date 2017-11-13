package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Show UI */
    void show();

    /** Hide UI */
    void hide();

    /** Determines if Ui is showing */
    boolean isShowing();

    /** Stops the UI. */
    void stop();

}
