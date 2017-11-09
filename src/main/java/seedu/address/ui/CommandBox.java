package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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

    //@@author Jeremy
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
        case DELETE:
        case BACK_SPACE:
            keyEvent.consume();
            deleteByChunk();
            break;
        default:
        }
    }


    /**
     * Deletes the word or a chunk of blank spaces on the left.
     * Does not matter if caret is at end of text or between lines. Method will automatically
     * detect and execute.
     * 1. If Caret is at far left, break;
     * 2. If Caret is at far right, check if left side is blank or word and execute appropriately
     * 3. If " " is present on the left of Caret, delete all blank spaces before
     * 4. If Caret is between word, execute normal delete method
     * 5. If Character is on the left and " " is on the right, delete chunk on left
     */
    private void deleteByChunk() {
        int originalCaretPosition = commandTextField.getCaretPosition();
        int newCaretPosition = commandTextField.getCaretPosition();
        int mostRight = commandTextField.getText().length();
        if (newCaretPosition == 0) {
            return;
        } else if (newCaretPosition == mostRight) {
            newCaretPosition = farRightDeleteChunk(newCaretPosition);
        } else if (isEmptyBefore(newCaretPosition)) {
            newCaretPosition = shiftLeftIgnoringSpaces(newCaretPosition);
        } else if (!isEmptyBefore(newCaretPosition) && !isEmptyAfter(newCaretPosition)) {
            newCaretPosition -= 1;
        } else {
            newCaretPosition = shiftLeftIgnoringWords(newCaretPosition);
        }
        setNewWord(newCaretPosition, originalCaretPosition);
        commandTextField.positionCaret(newCaretPosition);
    }


    /**
     * Deletes chunk in the situation where caret is at the far right
     */
    private int farRightDeleteChunk(int newCaretPosition) {
        if (isEmptyBefore(newCaretPosition)) {
            return shiftLeftIgnoringSpaces(newCaretPosition);
        } else {
            return shiftLeftIgnoringWords(newCaretPosition);
        }
    }


    /**
     * Forms a new word with all string elements between the two parameters removed
     */
    private void setNewWord(int newCaretPosition, int originalCaretPosition) {
        String before = commandTextField.getText().substring(0, newCaretPosition);
        String answer;
        if (atEitherEnds(originalCaretPosition)) {
            answer = before;
        } else {
            String after = commandTextField.getText().substring(originalCaretPosition);
            answer = before + after;
        }
        commandTextField.setText(answer);
    }


    /**
     * Checks if caret is at either ends
     */
    private boolean atEitherEnds(int originalCaretPosition) {
        boolean atFarLeft = (originalCaretPosition == 0);
        boolean atFarRight = (originalCaretPosition == commandTextField.getText().length());
        return atFarLeft || atFarRight;
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
     * 3. If "_" is present on left of Caret, shift left until 2. Condition holds
     * Run Step 2
     */
    private void shiftCaretLeftByWord() {
        int newCaretPosition = commandTextField.getCaretPosition();
        if (newCaretPosition == 0) {
            return;
        } else if (isEmptyBefore(newCaretPosition)) {
            newCaretPosition = shiftLeftIgnoringSpaces(newCaretPosition);
        }
        newCaretPosition = shiftLeftIgnoringWords(newCaretPosition);
        commandTextField.positionCaret(newCaretPosition);
    }


    /**
     * Shifts the caret right to the right of the last character of the next word
     * <p>
     * 1. If Caret is at far right, break
     * <p>
     * 2. If Char is present on right of Caret, shift right until
     * a) Caret is at far right or
     * b) "_" is found
     * <p>
     * 3. If "_" is present on right of Caret, shift right until 2. Condition holds
     * Run Step 2
     */
    private void shiftCaretRightByWord() {
        int newCaretPosition = commandTextField.getCaretPosition();
        int maxAchievablePosition = commandTextField.getText().length();
        if (newCaretPosition == maxAchievablePosition) {
            return;
        } else if (isEmptyAfter(newCaretPosition)) {
            newCaretPosition = shiftRightIgnoringSpaces(newCaretPosition, maxAchievablePosition);
        }
        newCaretPosition = shiftRightIgnoringWords(newCaretPosition, maxAchievablePosition);
        commandTextField.positionCaret(newCaretPosition);
    }


    /**
     * Shifts the caret left, ignoring all empty spaces
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretLeftByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve
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
     * Shifts the caret right, ignoring all empty spaces
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretRightByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the right.
     * It must never be called if there is a possibility of the string after
     * it being not an empty space
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being at most right position
     */
    private int shiftRightIgnoringSpaces(int newCaretPosition, int maxAchievablePosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i < maxAchievablePosition; i++) {
            if (!isEmptyAfter(caretHolder)) {
                break;
            }
            caretHolder += 1;
        }
        return caretHolder;
    }


    /**
     * Shifts the caret left, ignoring all char
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretLeftByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the left.
     * It must never be called if there is a possibility of the string before
     * it being not an empty space
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being 0
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
     * Shifts the caret right, ignoring all char
     * <p>
     * Note: Will not implement exception throwing here as shiftCaretRightByWord is set up in such a way
     * that pre-conditions as follows are met. Do not want to write code which will affect test coverage
     * which is impossible to resolve
     * <p>
     * Pre-Condition 1: Current caret position must have an empty space string on the right.
     * It must never be called if there is a possibility of the string before
     * it being not an empty space
     * <p>
     * Pre-Condition 2: newCaretPosition should never be in the situation where there is a possibility
     * of it being at most right position
     */
    private int shiftRightIgnoringWords(int newCaretPosition, int maxAchievablePosition) {
        int caretHolder = newCaretPosition;
        for (int i = caretHolder; i < maxAchievablePosition; i++) {
            if (isEmptyAfter(caretHolder)) {
                break;
            }
            caretHolder += 1;
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
     * Returns true if string element after currentCaretPosition index is empty
     */
    private boolean isEmptyAfter(int currentCaretPosition) {
        Character charAfter = commandTextField.getText().charAt(currentCaretPosition);
        String convertToString = Character.toString(charAfter);
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
        } else if (containsPrefix("remark")) {
            finalText = concatPrefix(PREFIX_REMARK);
        } else if (containsPrefix("date")) {
            finalText = concatPrefix(PREFIX_DATE);
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
            return (!containsName() && (addPollSuccessful() || editPollSuccessful()));
        case "phone":
            return (!containsPhone() && (addPollSuccessful() || editPollSuccessful()));
        case "email":
            return (!containsEmail() && (addPollSuccessful() || editPollSuccessful()));
        case "address":
            return (!containsAddress() && (addPollSuccessful() || editPollSuccessful()));
        case "bloodtype":
            return (!containsBloodtype() && (addPollSuccessful() || editPollSuccessful()));
        case "remark":
            return (!containsRemark() && (addPollSuccessful() || editPollSuccessful()));
        case "date":
            return (!containsDate() && (addPollSuccessful() || editPollSuccessful()));
        default:
            return (containsAllCompulsoryPrefix() && (addPollSuccessful() || editPollSuccessful()));

        }
    }

    /**
     * Polls the input statement to check if sentence starts with " add " or " a "
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
     * Polls the input statement to check if
     * 1. sentence starts with " edit " or " e " and
     * 2. is followed by a valid INDEX
     * <p>
     * Additional Note: Polling method accounts for blank spaces in front
     */
    private boolean editPollSuccessful() {
        String stringToEvaluate = commandTextField.getText().trim();
        if (stringToEvaluate.length() < 3 || !stringToEvaluate.contains(" ")) {
            return false;
        } else {
            String[] splittedString = stringToEvaluate.split(" ");
            boolean containsEditWord = splittedString[0].equalsIgnoreCase("edit");
            boolean containsEditShorthand = splittedString[0].equalsIgnoreCase("e");
            boolean containsEditCommand = containsEditShorthand || containsEditWord;
            String regex = "[0-9]+";
            boolean containsOnlyNumbers = splittedString[1].matches(regex);
            return containsEditCommand && containsOnlyNumbers;
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
                && containsName() && containsPhone() && containsRemark()
                && containsDate();
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
     * Checks if existing input has Remark Prefix String
     */
    private boolean containsRemark() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_REMARK.getPrefix());
    }

    /**
     * Checks if existing input has Remark Prefix String
     */
    private boolean containsDate() {
        String currentInput = commandTextField.getText();
        return currentInput.contains(PREFIX_DATE.getPrefix());
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
    //@@author

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

    //@@author Jeremy
    /**
     * Gets the text field for testing purposes
     */
    public TextField getCommandTextField() {
        return commandTextField;
    }
}
