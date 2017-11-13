package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.events.ui.CommandBoxContentsChangedEvent;
import seedu.address.commons.events.ui.CommandBoxReplaceTextEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelHideEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelNextSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelPreviousSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelSelectionEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.logic = logic;
        // calls textPredictionPanel#updatePredictionResults(newText) whenever there is
        // a change to the text of the command box
        // then calls #setStyleToDefault()
        commandTextField.textProperty().addListener((observable, oldText, newText) -> {
            raise(new CommandBoxContentsChangedEvent(newText));
            setStyleToDefault();
        });
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case PAGE_UP:
            navigateToPreviousInput();
            break;
        case PAGE_DOWN:
            navigateToNextInput();
            break;
        //@@author zameschua
        case TAB:
            // As up, down, and tab buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();
            raise(new CommandPredictionPanelSelectionEvent());
            raise(new CommandPredictionPanelHideEvent());
            break;
        case UP:
            keyEvent.consume();
            raise(new CommandPredictionPanelPreviousSelectionEvent());
            break;
        case DOWN:
            keyEvent.consume();
            raise(new CommandPredictionPanelNextSelectionEvent());
            break;
        case ENTER:
            raise(new CommandPredictionPanelHideEvent());
            break;
        //@@author
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    //@@author zameschua
    /**
     * Event handler to change the contents of the {@code CommandBox}
     * @param event the event fired by CommandPredictionPanel which contains the text of the
     * currently selected Command Prediction
     */
    @Subscribe
    private void handleCommandBoxReplaceTextEvent (CommandBoxReplaceTextEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        replaceText(event.getText());
    }
    //@@author
}
