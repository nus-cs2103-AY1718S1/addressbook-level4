package seedu.address.ui;

import static seedu.address.ui.util.KeyListenerUtil.ADD_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.CLEAR_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.DELETE_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.DELETE_SELECTION_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.EDIT_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.FIND_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_COMMAND_BOX_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_PERSON_LIST_ALT_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_PERSON_LIST_ALT_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.FOCUS_RESULT_DISPLAY_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.HISTORY_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.LIST_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.NEW_FILE_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.OPEN_FILE_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.REDO_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.SELECT_KEY_COMBINATION;
import static seedu.address.ui.util.KeyListenerUtil.UNDO_KEY_COMBINATION;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
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
            executeKeyEvent(event);
        });
    }

    /**
     * Executes the key event.
     * Matches {@code keyEvent} with non-command events before handling keys mapped to command words.
     */
    private void executeKeyEvent(KeyEvent keyEvent) {

        if (FOCUS_PERSON_LIST_ALT_KEY_COMBINATION.match(keyEvent)
                || FOCUS_PERSON_LIST_ALT_KEY_COMBINATION.match(keyEvent)) {
            personListPanel.setFocus();

        } else if (FOCUS_COMMAND_BOX_KEY_COMBINATION.match(keyEvent)) {
            commandBox.setFocus();

        } else if (FOCUS_RESULT_DISPLAY_KEY_COMBINATION.match(keyEvent)) {
            resultDisplay.setFocus();

        } else if (DELETE_SELECTION_KEY_COMBINATION.match(keyEvent)) {
            deleteSelectedContact();

        } else if (CLEAR_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(ClearCommand.COMMAND_WORD);

        } else if (HISTORY_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(HistoryCommand.COMMAND_WORD);

        } else if (UNDO_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(UndoCommand.COMMAND_WORD);

        } else if (REDO_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(RedoCommand.COMMAND_WORD);

        } else if (LIST_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(ListCommand.COMMAND_WORD);

        } else if (OPEN_FILE_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(OpenCommand.COMMAND_WORD);

        } else if (NEW_FILE_KEY_COMBINATION.match(keyEvent)) {
            executeCommand(NewCommand.COMMAND_WORD);

        } else if (ADD_KEY_COMBINATION.match(keyEvent)) {
            displayCommandFormat(AddCommand.FORMAT);

        } else if (EDIT_KEY_COMBINATION.match(keyEvent)) {
            displayCommandFormat(EditCommand.FORMAT);

        } else if (FIND_KEY_COMBINATION.match(keyEvent)) {
            displayCommandFormat(FindCommand.FORMAT);

        } else if (SELECT_KEY_COMBINATION.match(keyEvent)) {
            displayCommandFormat(SelectCommand.FORMAT);

        } else if (DELETE_SELECTION_KEY_COMBINATION.match(keyEvent)) {
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
