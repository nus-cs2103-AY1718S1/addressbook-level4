package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
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
    private ListElementPointer autoCompleteSnapshot;
    private boolean isAutoCompletePossibilitiesUpToDate = false;
    private int oldCaretPosition = 0;
    private String textAfterCaret = "";

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        autoCompleteSnapshot = logic.getAutoCompleteSnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case TAB:
            // As tab will shift focus away from the text box,
            // consuming it causes the text box to remain focused
            keyEvent.consume();
            autoCompleteCommand();
            break;
        default:
            // let JavaFx handle the keypress

            // There has been a key press event that is not consumed (special)
            // autocomplete possibilities is likely outdated
            isAutoCompletePossibilitiesUpToDate = false;
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
     * Autocompletes the command in the textbox from incomplete input,
     * and if command is already complete change to next possible command
     */
    private void autoCompleteCommand() {
        assert autoCompleteSnapshot != null;
        if (!isAutoCompletePossibilitiesUpToDate) {
            // Update the autocomplete possibilities only when textbox is changed by non-shortcut user key press
            initAutoComplete();
            // Remember old caret position, so that selected text include all autocompleted text
            oldCaretPosition = commandTextField.getCaretPosition();
        }

        // loop back to the start (original user input) if all autocomplete options are exhausted
        if (!autoCompleteSnapshot.hasPrevious()) {
            autoCompleteSnapshot = logic.getAutoCompleteSnapshot();
            replaceText(autoCompleteSnapshot.current());
            appendText(textAfterCaret);
        } else {
            replaceTextAndSelectAllForward(autoCompleteSnapshot.previous());
            appendText(textAfterCaret);
        }
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
     * Sets {@code CommandBox}'s text field with {@code text},
     * selects all text beyond previous caret position,
     * and positions the caret to the end of the {@code text}.
     */
    private void replaceTextAndSelectAllForward(String text) {
        commandTextField.setText(text);
        commandTextField.selectRange(oldCaretPosition, commandTextField.getText().length());
    }

    /**
     * Appends {@code text} to the end of the text already in {@code CommandBox},
     * while maintaining caret position and selection anchor
     */
    private void appendText(String text) {
        int caretPosition = commandTextField.getCaretPosition();
        int anchor = commandTextField.getAnchor();
        commandTextField.setText(commandTextField.getText() + text);
        commandTextField.selectRange(anchor, caretPosition);
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
     * Initializes or reinitializes the autocomplete snapshot.
     */
    private void initAutoComplete() {
        // only pass the text before the caret into autocomplete
        logic.updateAutoCompletePossibilities(commandTextField.getText()
            .substring(0, commandTextField.getCaretPosition()));
        // remember the text after caret
        textAfterCaret = commandTextField.getText()
            .substring(commandTextField.getCaretPosition(), commandTextField.getText().length());
        autoCompleteSnapshot = logic.getAutoCompleteSnapshot();
        isAutoCompletePossibilitiesUpToDate = true;
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

}
