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
    //@@author Alim95
    private static final String DELETE_ICON = "/images/DeleteBird.png";
    private static final String EDIT_ICON = "/images/EditBird.png";
    private static final String ERROR_ICON = "/images/ErrorBird.png";
    private static final String FIND_ICON = "/images/FindBird.png";
    private static final String HIDE_ICON = "/images/HideBird.png";
    private static final String SUCCESS_ICON = "/images/SuccessBird.png";
    private static final String TASK_ICON = "/images/TaskBird.png";
    private static final String UNDO_ICON = "/images/UndoBird.png";
    //@@author


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
        displayResultIcon(event);
    }

    /**
     * Displays icon feedback for result.
     */
    private void displayResultIcon(ValidResultDisplayEvent event) {
        switch (event.message.trim()) {
        case "delete":
            imageDisplay.setImage(new Image(DELETE_ICON));
            break;
        case "edit":
            imageDisplay.setImage(new Image(EDIT_ICON));
            break;
        case "find":
            imageDisplay.setImage(new Image(FIND_ICON));
            break;
        case "hide":
            imageDisplay.setImage(new Image(HIDE_ICON));
            break;
        case "task":
            imageDisplay.setImage(new Image(TASK_ICON));
            break;
        case "undo":
            imageDisplay.setImage(new Image(UNDO_ICON));
            break;
        default:
            imageDisplay.setImage(new Image(SUCCESS_ICON));
        }
    }

    @Subscribe
    private void handleInvalidResultDisplayEvent(InvalidResultDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        imageDisplay.setImage(new Image(ERROR_ICON));
    }

    public void highlight() {
        this.resultDisplay.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void unhighlight() {
        this.resultDisplay.setStyle("");
    }
}
