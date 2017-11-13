//@@author erik0704
package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.event.Event;

/**
 * Controller for a reminder page
 */
public class ReminderWindow extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ReminderWindow.class);
    private static final String FXML = "ReminderWindow.fxml";
    private static final String TITLE = "You have an event coming up.";
    private static final String ICON = "/images/clock.png";

    public final Event event;
    private final Stage dialogStage;

    @FXML
    private VBox reminder;
    @FXML
    private Label name;
    @FXML
    private Label date;
    @FXML
    private Label description;

    public ReminderWindow(Event event) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        this.event = event;
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Event event) {
        name.textProperty().bind(Bindings.convert(event.nameProperty()));
        date.textProperty().bind(Bindings.convert(event.dateProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));

    }

    /**
     * Show the reminder window
     */
    public void show() {
        logger.fine("Showing reminder");
        dialogStage.showAndWait();
    }

}
