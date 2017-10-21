package seedu.address.ui;

import static seedu.address.ui.util.KeyListenerUtil.CLEAR;
import static seedu.address.ui.util.KeyListenerUtil.DELETE_SELECTION;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_COMMAND_BOX;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_PERSON_LIST;
import static seedu.address.ui.util.KeyListenerUtil.HISTORY;
import static seedu.address.ui.util.KeyListenerUtil.LIST;
import static seedu.address.ui.util.KeyListenerUtil.REDO;
import static seedu.address.ui.util.KeyListenerUtil.UNDO;

import java.util.logging.Logger;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Listens to key events in the main window.
 */
public class KeyListener {
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Region mainNode;
    private PersonListPanel personListPanel;
    private CommandBox commandBox;

    public KeyListener(Region mainNode, PersonListPanel personListPanel,
                       CommandBox commandBox) {
        this.mainNode = mainNode;
        this.personListPanel = personListPanel;
        this.commandBox = commandBox;
    }

    /**
     * Handles key press events
     */
    public void handleKeyPress() {

        mainNode.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (commandBox.isFocused() || !(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN)) {
                commandBox.processInput();
            }
            //commandBox.processInput();
            executeKeyEvent(event);
        });
    }

    /**
     * Executes the key event.
     * Matches {@code keyEvent} with non-command events before handling keys mapped to command words.
     */
    private void executeKeyEvent(KeyEvent keyEvent) {

        if (FOCUS_PERSON_LIST.match(keyEvent)) {
            personListPanel.setFocus();

        } else if (FOCUS_COMMAND_BOX.match(keyEvent)) {
            commandBox.setFocus();

        } else if (DELETE_SELECTION.match(keyEvent)) {
            deleteSelectedContact();

        } else if (CLEAR.match(keyEvent)) {
            executeCommand(ClearCommand.COMMAND_WORD);

        } else if (HISTORY.match(keyEvent)) {
            executeCommand(HistoryCommand.COMMAND_WORD);

        } else if (UNDO.match(keyEvent)) {
            executeCommand(UndoCommand.COMMAND_WORD);

        } else if (REDO.match(keyEvent)) {
            executeCommand(RedoCommand.COMMAND_WORD);

        } else if (LIST.match(keyEvent)) {
            executeCommand(ListCommand.COMMAND_WORD);

        } else {
            // no key combination matches, do nothing
        }
    }

    /**
     * Handles execution of command
     */
    private void executeCommand(String command) {
        commandBox.replaceText(command);
        commandBox.handleCommandInputChanged();
    }

    /**
     * Deletes the selected contact
     * TODO: Implement deletion at selected contact
     */
    private String deleteSelectedContact() {
        return null;
    }
}
