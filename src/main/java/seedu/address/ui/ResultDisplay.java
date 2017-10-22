package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    @FXML
    private ImageView imageDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.message.equals(Messages.MESSAGE_UNKNOWN_COMMAND)
                || event.message.contains(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ""))
                || event.message.contains(Messages.MESSAGE_PERSON_ALREADY_PINNED)
                || event.message.contains(Messages.MESSAGE_PERSON_ALREADY_UNPINNED)
                || event.message.contains(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX)) {
            imageDisplay.setImage(new Image("/images/error.png"));
        } else {
            imageDisplay.setImage(new Image("/images/success.png"));
        }
        Platform.runLater(() -> displayed.setValue(event.message));
    }
}
