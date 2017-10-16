package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Iterator;
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
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
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
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     * <p>
     * UP:
     * As up and down buttons will alter the position of the caret,
     * consuming it causes the caret's position to remain unchanged
     * <p>
     * RIGHT:
     * 1. Check if user's Caret is at the end of the text input.
     * If caret is not at the end of text, do nothing
     * If caret is at the end, deploy shortcut that makes user life easy for add command
     * 2. If only add is present, concat prefix name string
     * Checks if necessary prefixes are present
     * Checks based on priority : n/ p/ e/ a/ b/ t/ prefixes
     * <p>
     * DEFAULT:
     * Lets JavaFx handle the Key Press
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.isShiftDown()) {
            handleShiftPress(keyEvent);
        } else {
            handleStandardPress(keyEvent);
        }
    }

    /**
     * Handles KeyPress Commands that are not keyed with Shift button held down
     */
    private void handleStandardPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                keyEvent.consume();
                navigateToPreviousInput();
                break;
            case DOWN:
                keyEvent.consume();
                navigateToNextInput();
                break;
            case ESCAPE:
                keyEvent.consume();
                commandTextField.setText("");
                break;
            case ALT:
                keyEvent.consume();
                shiftCaretLeftByWord();
                break;
            case CONTROL:
                keyEvent.consume();
                shiftCaretRightByWord();
                break;
            case RIGHT:
                keyEvent.consume();
                boolean isCaretWithin = commandTextField.getCaretPosition() < commandTextField.getText().length();
                if (isCaretWithin) {
                    break;
                } else {
                    addsNextPrefix();
                    break;
                }
            default:
        }
    }

    /**
     * Handles KeyPress Commands that are keyed with Shift button held down
     */
    private void handleShiftPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ALT:
                keyEvent.consume();
                commandTextField.positionCaret(0);
                break;
            case CONTROL:
                keyEvent.consume();
                commandTextField.positionCaret(commandTextField.getText().length());
                break;
            default:
        }
    }

    /**
     * Shifts the caret left to the left of the first character of the next word
     * <p>
     * 1. If Caret is at far left, break
     * <p>
     * 2. If Char is present on left of Caret, shift left until
     * a) Caret is at far left or
     * b) "_" is found
     * <p>
     * 3. If "_" is present on left of Caret, shift left until 2 Condition holds
     * Run Step 2
     */
    private void shiftCaretLeftByWord() {
        int newCaretPosition = commandTextField.getCaretPosition();
        if (newCaretPosition == 0) {
            return;
        } else if (isEmptyBefore(newCaretPosition)) {
            newCaretPosition = shiftLeftIgnoringSpaces(newCaretPosition);
            newCaretPosition = shiftLeftIgnoringWords(newCaretPosition);
        } else {
            newCaretPosition = shiftLeftIgnoringWords(newCaretPosition);
        }
        commandTextField.positionCaret(newCaretPosition);
    }

    /**
     * Shifts the caret right to the right of the last character of the next word
     */
    private void shiftCaretRightByWord() {

    }

    /**
     * Shifts the caret left, ignoring all empty spaces
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the left.
     * It must never be called if there is a possibility of the string before
     * it being not an empty space
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being 0
     */
    private int shiftLeftIgnoringSpaces(int newCaretPosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i > 0; i--) {
            if (!isEmptyBefore(caretHolder)) {
                break;
            }
            caretHolder -= 1;
        }
        return caretHolder;
    }

    /**
     * Shifts the caret left, ignoring all char
     */
    private int shiftLeftIgnoringWords(int newCaretPosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i > 0; i--) {
            if (isEmptyBefore(caretHolder)) {
                break;
            }
            caretHolder -= 1;
        }
        return caretHolder;
    }

    /**
     * Returns true if string element before currentCaretPosition index is empty
     */
    private boolean isEmptyBefore(int currentCaretPosition) {
        Character charBefore = commandTextField.getText().charAt(currentCaretPosition - 1);
        String convertToString = Character.toString(charBefore);
        return (" ".equals(convertToString));
    }


    /**
     * Adds the next prefix required for the input
     */
    private void addsNextPrefix() {
        String finalText;
        if (containsPrefix("name")) {
            finalText = concatPrefix(PREFIX_NAME);
        } else if (containsPrefix("phone")) {
            finalText = concatPrefix(PREFIX_PHONE);
        } else if (containsPrefix("email")) {
            finalText = concatPrefix(PREFIX_EMAIL);
        } else if (containsPrefix("address")) {
            finalText = concatPrefix(PREFIX_ADDRESS);
        } else if (containsPrefix("bloodtype")) {
            finalText = concatPrefix(PREFIX_BLOODTYPE);
        } else if (containsPrefix("all")) {
            finalText = concatPrefix(PREFIX_TAG);
        } else {
            return;
        }
        commandTextField.setText(finalText);
        commandTextField.positionCaret(finalText.length());
    }

    /**
     * Fundamental Check: Checks if add poll KeyWord is in the input text
     * Additional Checks: Checks if prefix is in the input text
     */
    private boolean containsPrefix(String element) {
        switch (element) {
            case "name":
                return (!containsName() && addPollSuccessful());
            case "phone":
                return (!containsPhone() && addPollSuccessful());
            case "email":
                return (!containsEmail() && addPollSuccessful());
            case "address":
                return (!containsAddress() && addPollSuccessful());
            case "bloodtype":
                return (!containsBloodtype() && addPollSuccessful());
            default:
                return (containsAllCompulsoryPrefix() && addPollSuccessful());

        }
    }

    /**
     * Polls the input statement to check if sentence starts with " add " or " a "
     * Spacing before and after command is required else words like "adda" or "adam" is counted as a add command
     * <p>
     * Additional Note: Polling method accounts for blank spaces in front
     */
    private boolean addPollSuccessful() {
        String stringToEvaluate = commandTextField.getText().trim();
        if (stringToEvaluate.length() == 0) {
            return false;
        } else if (stringToEvaluate.length() == 1) {
            return stringToEvaluate.equalsIgnoreCase(AddCommand.COMMAND_ALIAS);
        } else if (stringToEvaluate.length() == 2) {
            return false;
        } else if (stringToEvaluate.length() == 3) {
            return containsAInFirstTwoChar(stringToEvaluate)
                    || stringToEvaluate.equalsIgnoreCase(AddCommand.COMMAND_WORD);
        } else {
            return containsAInFirstTwoChar(stringToEvaluate)
                    || containsAddInFirstFourChar(stringToEvaluate);
        }
    }

    /**
     * Checks if the first two elements of the string are "a "
     */
    private boolean containsAInFirstTwoChar(String stringToEvaluate) {
        return (Character.toString(stringToEvaluate.charAt(0)).equalsIgnoreCase(AddCommand.COMMAND_ALIAS)
                && Character.toString(stringToEvaluate.charAt(1)).equals(" "));
    }

    /**
     * Checks if the first four elements of the string are "add "
     */
    private boolean containsAddInFirstFourChar(String stringToEvaluate) {
        return (stringToEvaluate.substring(0, 3).equalsIgnoreCase(AddCommand.COMMAND_WORD)
                && Character.toString(stringToEvaluate.charAt(3)).equals(" "));
    }

    /**
     * Checks if the commandTextField all prefixes excluding tag
     */
    private boolean containsAllCompulsoryPrefix() {
        return containsAddress() && containsEmail() && containsBloodtype()
                && containsName() && containsPhone();
    }

    /**
     * Adds prefix string to existing text input
     */
    private String concatPrefix(Prefix prefix) {
        return commandTextField.getText().concat(" ").concat(prefix.getPrefix());
    }

    /**
     * Checks if existing input has Bloodtype Prefix String
     */
    private boolean containsBloodtype() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_BLOODTYPE.getPrefix());
    }

    /**
     * Checks if existing input has Address Prefix String
     */
    private boolean containsAddress() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_ADDRESS.getPrefix());
    }

    /**
     * Checks if existing input has Email Prefix String
     */
    private boolean containsEmail() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_EMAIL.getPrefix());
    }

    /**
     * Checks if existing input has Phone Prefix String
     */
    private boolean containsPhone() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_PHONE.getPrefix());
    }

    /**
     * Checks if existing input has Name Prefix String
     */
    private boolean containsName() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_NAME.getPrefix());
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
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), true));
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

    /**
     * Gets the text field for testing purposes
     */
    public TextField getCommandTextField() {
        return commandTextField;
    }
}
