package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.commons.events.ui.ChangeInformationPanelRequestEvent;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App). */
    void start(Stage primaryStage);

    /** Changes the information panel based on request event. */
    void changeInformationPanel(ChangeInformationPanelRequestEvent event);

    /** Changes the stylesheet used by the UI when change theme command is executed. */
    void changeTheme();

    /** Returns main window. */
    MainWindow getMainWindow();

    /** Returns an unmodifiable child of the information panel currently displayed. */
    String getCurrentInformationPanel();

    /** Returns the current stylesheets. */
    String getCurrentStyleSheets();

    /** Stops the UI. */
    void stop();

}
