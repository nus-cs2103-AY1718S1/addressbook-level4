package seedu.address.ui;

import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_ADD;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_CLEAR;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_DELETE;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_DELETE_ALTERNATIVE;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_DELETE_SELECTION;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_EDIT;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_EMAIL;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_FIND;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_FOCUS_COMMAND_BOX;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_FOCUS_PERSON_LIST;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_FOCUS_PERSON_LIST_ALT;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_FOCUS_RESULT_DISPLAY;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_HISTORY;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_LIST;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_NEW_FILE;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_OPEN_FILE;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_REDO;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_REMARK;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_SELECT;
import static seedu.address.ui.util.KeyListenerUtil.KEY_COMBINATION_UNDO;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
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
     * Handles key press events from the user.
     */
    public void handleKeyPress() {
        mainNode.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (isNotScrolling(event)) {
                commandBox.processInput();
            }
            executeKeyEvent(event);
        });
    }

    private boolean isNotScrolling(KeyEvent event) {
        return commandBox.isFocused() || !(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN);
    }

    /**
     * Executes the {@code keyEvent} matching an assigned {@code KeyCombination}.
     */
    private void executeKeyEvent(KeyEvent keyEvent) {

        if (KEY_COMBINATION_FOCUS_PERSON_LIST.match(keyEvent)
                || KEY_COMBINATION_FOCUS_PERSON_LIST_ALT.match(keyEvent)) {
            personListPanel.setFocus();

        } else if (KEY_COMBINATION_FOCUS_COMMAND_BOX.match(keyEvent)) {
            commandBox.setFocus();

        } else if (KEY_COMBINATION_FOCUS_RESULT_DISPLAY.match(keyEvent)) {
            resultDisplay.setFocus();

        } else if (KEY_COMBINATION_DELETE_SELECTION.match(keyEvent)) {
            deleteSelectedContact();

        } else if (KEY_COMBINATION_CLEAR.match(keyEvent)) {
            executeCommand(ClearCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_HISTORY.match(keyEvent)) {
            executeCommand(HistoryCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_UNDO.match(keyEvent)) {
            executeCommand(UndoCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_REDO.match(keyEvent)) {
            executeCommand(RedoCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_LIST.match(keyEvent)) {
            executeCommand(ListCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_OPEN_FILE.match(keyEvent)) {
            displayCommandFormat(OpenRolodexCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_NEW_FILE.match(keyEvent)) {
            displayCommandFormat(NewRolodexCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_ADD.match(keyEvent)) {
            displayCommandFormat(AddCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_EDIT.match(keyEvent)) {
            displayCommandFormat(EditCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_EMAIL.match(keyEvent)) {
            displayCommandFormat(EmailCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_FIND.match(keyEvent)) {
            displayCommandFormat(FindCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_SELECT.match(keyEvent)) {
            displayCommandFormat(SelectCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_DELETE.match(keyEvent) || KEY_COMBINATION_DELETE_ALTERNATIVE.match(keyEvent)) {
            displayCommandFormat(DeleteCommand.COMMAND_WORD);

        } else if (KEY_COMBINATION_REMARK.match(keyEvent)) {
            displayCommandFormat(RemarkCommand.COMMAND_WORD);

        } else {
                // no key combination matches, do nothing
        }
    }

    /**
     * Executes command triggered by key presses.
     */
    private void executeCommand(String command) {
        commandBox.replaceText(command);
        commandBox.handleCommandInputChanged();
    }

    /**
     * Displays the full command format for commands that require multiple fields
     * Pressing the hotkey is the same as entering the command and press tab
     */
    private void displayCommandFormat(String command) {
        //simulate entering the command word and press tab
        commandBox.replaceText(command);
        commandBox.pressTab();
    }

    /**
     * Deletes the selected contact
     * TODO: Implement deletion at selected contact
     * coming in v2.0
     */
    private String deleteSelectedContact() {
        return null;
    }
}
