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
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InvalidResultDisplayEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ValidResultDisplayEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private StackPane placeHolder;

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
        Platform.runLater(() -> displayed.setValue(event.message));
    }

    //@@author Alim95
    @Subscribe
    private void handleValidResultDisplayEvent(ValidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        imageDisplay.setImage(new Image("/images/success.png"));
    }

    @Subscribe
    private void handleInvalidResultDisplayEvent(InvalidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        imageDisplay.setImage(new Image("/images/error.png"));
    }

    public void highlight() {
        this.resultDisplay.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void unhighlight() {
        this.resultDisplay.setStyle("");
    }
}
