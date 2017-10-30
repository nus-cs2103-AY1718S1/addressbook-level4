package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.LoggingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author blaqkrow
/**
 * The UI component that is responsible for deleting selected contacts in the PersonListPanel.
 */
public class ClearLogButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "ClearLogButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private LoggingCommand loggingCommand;

    @FXML
    private Button deleteButton;

    public ClearLogButton() {
        super(FXML);
        loggingCommand = new LoggingCommand();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleClearLogButtonPressed() throws CommandException, ParseException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want clear the log?",
                ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            loggingCommand.clearLog();
            logger.info("Log has been cleared.");
        }
    }
}
