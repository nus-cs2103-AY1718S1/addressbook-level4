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
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.util.KeyListenerUtil;

/**
 * Listens to key events in the main window.
 */
public class KeyListener {
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;
    private MainWindow mainWindow;
    private PersonListPanel personListPanel;
    private CommandBox commandBox;
    private HashMap<String, KeyCombination> keys = KeyListenerUtil.getKeys();

    public KeyListener(Logic logic, MainWindow mainWindow, PersonListPanel personListPanel,
                       CommandBox commandBox) {
        this.logic = logic;
        this.mainWindow = mainWindow;
        this.personListPanel = personListPanel;
        this.commandBox = commandBox;
    }

    /**
     * Handles the key press events
     */
    public void handleKeyPress() {
        mainWindow.getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            commandBox.setStyleToDefault();

            if (keys.get("FOCUS_PERSON_LIST").match(event)) {
                personListPanel.setFocus();
            }
            if (keys.get("FOCUS_COMMAND_BOX").match(event)) {
                commandBox.setFocus();
            }
            if (keys.get("DELETE_SELECTION").match(event)) {
                // TODO: add support for deletion at selected list
                // Dummy action
                personListPanel.setFocus();
            }
            if (keys.get("CLEAR_LIST").match(event)) {
                executeCommand(ClearCommand.COMMAND_WORD);
            }
            if (keys.get("UNDO").match(event)) {
                executeCommand(UndoCommand.COMMAND_WORD);
            }
            if (keys.get("REDO").match(event)) {
                executeCommand(RedoCommand.COMMAND_WORD);
            }
            if (keys.get("VIEW_HISTORY").match(event)) {
                executeCommand(HistoryCommand.COMMAND_WORD);
            }
        });
    }

    /**
     * Handles execution of command
     * @param command to be executed
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
