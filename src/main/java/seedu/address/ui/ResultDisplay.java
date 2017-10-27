package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.Commands;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";
    private static final HashMap<String, String> allCommandUsages = Commands.getAllCommandUsages();

    private final StringProperty displayed = new SimpleStringProperty("");

    private String lastFoundCommand = "";
    private TextField linkedCommandInput;

    @FXML
    private TextArea resultDisplay;

    @FXML
    private TextArea infoDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        resultDisplay.setWrapText(true);
        infoDisplay.setWrapText(true);
        registerAsAnEventHandler(this);
    }

    void setLinkedInput(CommandBox commandBox) {
        linkedCommandInput = commandBox.getCommandTextField();
        linkedCommandInput.textProperty().addListener((observable, oldValue, newValue) ->
            updateInfoDisplay(oldValue, newValue));
    }

    /**
     * Updates the information display according to the user input in the command box.
     * Note that "clearhistory" has been hardcoded as a unique case.
     */
    private void updateInfoDisplay(String oldInput, String newInput) {
        if (lastFoundCommand.isEmpty()
            || (newInput.length() < oldInput.length() && !newInput.contains(lastFoundCommand))
            || (newInput.length() > oldInput.length()) && allCommandUsages.containsKey(newInput)) {
            infoDisplay.setText("");
            lastFoundCommand = "";
            if (allCommandUsages.containsKey(newInput)) {
                infoDisplay.setText("How to use:\n" + allCommandUsages.get(newInput));
                lastFoundCommand = newInput;
            }
        }
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
