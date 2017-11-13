package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

    /** Resets panel with personList */
    void resetPanel(ObservableList<ReadOnlyPerson> personList);

}
