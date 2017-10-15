package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.util.KeyListenerUtil;

/**
 * Listens to key events in the main window.
 */
public class KeyListener {
    private static HashMap<String, KeyCombination> keys = KeyListenerUtil.getKeys();
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;
    private MainWindow mainWindow;
    private PersonListPanel personListPanel;
    private CommandBox commandBox;

    public KeyListener(Logic logic, MainWindow mainWindow, PersonListPanel personListPanel,
                       CommandBox commandBox) {
        this.logic = logic;
        this.mainWindow = mainWindow;
        this.personListPanel = personListPanel;
        this.commandBox = commandBox;
    }

    /**
     * Handles key press events
     */
    public void handleKeyPress() {
        mainWindow.getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            commandBox.setStyleToDefault();
            executeKeyEvent(event);
        });
    }

    /**
     * Executes the key event.
     * Matches {@code keyEvent} with non-command events before handling keys mapped to command words.
     */
    private void executeKeyEvent(KeyEvent keyEvent) {

        // Execute key events for non-command events
        if (keys.get("FOCUS_PERSON_LIST").match(keyEvent)) {
            personListPanel.setFocus();

        } else if (keys.get("FOCUS_COMMAND_BOX").match(keyEvent)) {
            commandBox.setFocus();

        } else if (keys.get("DELETE_SELECTION").match(keyEvent)) {
            // TODO: add support for deletion at selected list
            // Dummy action
            personListPanel.setFocus();

        } else {
            // Execute key events for command words
            executeCommandKeyEvents(keyEvent);
        }
    }

    /**
     * Executes {@code keyEvent} for command words by searching for mapped keys in HashMap {@code keys}
     */
    private void executeCommandKeyEvents(KeyEvent keyEvent) {
        for (HashMap.Entry<String, KeyCombination> key: keys.entrySet()) {
            KeyCombination keyCombination = key.getValue();
            String command = key.getKey();

            if (keyCombination.match(keyEvent)) {
                executeCommand(command);
            }
        }
    }

    /**
     * Handles execution of command
     */
    private void executeCommand(String command) {
        try {
            CommandResult commandResult = logic.execute(command);
            displayResult(commandResult.feedbackToUser);

        } catch (CommandException | ParseException e) {
            commandBox.setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + e.getMessage());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Displays the command result to user
     */
    private void displayResult(String commandResult) {
        logger.info("Result: " + commandResult);
        raise(new NewResultAvailableEvent(commandResult));
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     */
    private void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }
}
