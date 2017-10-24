package seedu.address.ui;

import static seedu.address.ui.util.KeyListenerUtil.ADD;
import static seedu.address.ui.util.KeyListenerUtil.CLEAR;
import static seedu.address.ui.util.KeyListenerUtil.DELETE;
import static seedu.address.ui.util.KeyListenerUtil.DELETE_SELECTION;
import static seedu.address.ui.util.KeyListenerUtil.EDIT;
import static seedu.address.ui.util.KeyListenerUtil.FIND;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_COMMAND_BOX;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_PERSON_LIST;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_PERSON_LIST_ALT;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_RESULT_DISPLAY;
import static seedu.address.ui.util.KeyListenerUtil.HISTORY;
import static seedu.address.ui.util.KeyListenerUtil.LIST;
import static seedu.address.ui.util.KeyListenerUtil.NEW_FILE;
import static seedu.address.ui.util.KeyListenerUtil.OPEN_FILE;
import static seedu.address.ui.util.KeyListenerUtil.REDO;
import static seedu.address.ui.util.KeyListenerUtil.SELECT;
import static seedu.address.ui.util.KeyListenerUtil.UNDO;

import java.util.logging.Logger;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * Listens to key events in the main window.
 */
public class KeyListener {
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Region mainNode;
    private PersonListPanel personListPanel;
    private CommandBox commandBox;
    private ResultDisplay resultDisplay;

    public KeyListener(Region mainNode, ResultDisplay resultDisplay, PersonListPanel personListPanel,
                       CommandBox commandBox) {
        this.mainNode = mainNode;
        this.personListPanel = personListPanel;
        this.commandBox = commandBox;
        this.resultDisplay = resultDisplay;
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

        if (FOCUS_PERSON_LIST.match(keyEvent) || FOCUS_PERSON_LIST_ALT.match(keyEvent)) {
            personListPanel.setFocus();

        } else if (FOCUS_COMMAND_BOX.match(keyEvent)) {
            commandBox.setFocus();

        } else if (FOCUS_RESULT_DISPLAY.match(keyEvent)) {
            resultDisplay.setFocus();

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

        } else if (OPEN_FILE.match(keyEvent)) {
            executeCommand(OpenCommand.COMMAND_WORD);

        } else if (NEW_FILE.match(keyEvent)) {
            executeCommand(NewCommand.COMMAND_WORD);

        } else if (ADD.match(keyEvent)) {
            displayCommandFormat(AddCommand.FORMAT);

        } else if (EDIT.match(keyEvent)) {
            displayCommandFormat(EditCommand.FORMAT);

        } else if (FIND.match(keyEvent)) {
            displayCommandFormat(FindCommand.FORMAT);

        } else if (SELECT.match(keyEvent)) {
            displayCommandFormat(SelectCommand.FORMAT);

        } else if (DELETE.match(keyEvent)) {
            displayCommandFormat(DeleteCommand.FORMAT);

        } else {
                // no key combination matches, do nothing
        }
    }

    /**
     * Handles execution of command
     */
    private void executeCommand(String command) {
        if (command.equals(OpenCommand.COMMAND_WORD) || command.equals(NewCommand.COMMAND_WORD)) {
            commandBox.replaceText(command + " ");
        } else {
            commandBox.replaceText(command);
            commandBox.handleCommandInputChanged();
        }
    }

    /**
     * display the full command format for commands that require multiple fields
     */
    private void displayCommandFormat(String command) {
        commandBox.replaceText(command);
        commandBox.getRobot().delay(300);
        commandBox.pressCtrl();
    }

    /**
     * Deletes the selected contact
     * TODO: Implement deletion at selected contact
     */
    private String deleteSelectedContact() {
        return null;
    }
}
