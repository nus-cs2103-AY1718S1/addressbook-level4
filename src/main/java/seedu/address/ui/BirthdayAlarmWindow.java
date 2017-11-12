//@@author chilipadiboy
package seedu.address.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Controller for Birthday Alarm
 */
public class BirthdayAlarmWindow extends UiPart<Region> implements Initializable {
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

    public BirthdayAlarmWindow(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        ObservableList<ReadOnlyPerson> pl;
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(true);
        pl = personList;
        BirthdayTable.setItems(pl);

    }

    /**
     * Shows the reminders window.
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up columns
        NameColumn.setCellValueFactory(new PropertyValueFactory<ReadOnlyPerson, String>("Name"));
        BirthdayColumn.setCellValueFactory(new PropertyValueFactory<ReadOnlyPerson, String>("Birthday"));
    }
}

