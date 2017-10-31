package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

// @@author HouDenghao
/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class InformationBoard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(InformationBoard.class);
    private static final String FXML = "InformationBoard.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea informationBoard;
    @FXML
    private Label title;

    public InformationBoard() {
        super(FXML);
        title.textProperty().bind(new SimpleStringProperty("Information Board"));
        informationBoard.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
