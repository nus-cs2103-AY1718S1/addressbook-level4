//@@author chilipadiboy
package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;

import com.google.common.collect.Tables;
import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;



/**
 * Controller for Birthday Alarm
 */
public class BirthdayAlarmWindow extends UiPart<Region> {
    private static final String FXML = "BirthdayAlarmWindow.fxml";
    private static final String TITLE = "Birthday Alarm";
    private final Logger logger = LogsCenter.getLogger(BirthdayAlarmWindow.class);

    @FXML
    private TableView<ReadOnlyPerson> BirthdayTable;
    @FXML
    private TableColumn<ReadOnlyPerson , String> NameColumn;
    @FXML
    private TableColumn<ReadOnlyPerson, String> BirthdayColumn;

    private final Stage dialogStage;

    public BirthdayAlarmWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(true);
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing Birthday Alarm Page");
        dialogStage.showAndWait();
    }
}
