package seedu.address.ui;

import static seedu.address.logic.commands.LoginCommand.isLoggedIn;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeToLoginViewEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final char BLACK_CIRCLE = '\u25CF';
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String ALREADY_LOGGED_IN_MESSAGE = "Already logged in";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private int numOfSpaces;
    private int maskFromIndex;
    private int inputLength;
    private int prevInputLength;
    private int indexOfFirstWhitespace;
    private int indexOfSecondWhitespace;
    private int currentMaskFromIndex;
    private int indexOfLastChar;
    private String passwordFromInput = "";

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            numOfSpaces = getNumOfSpaces(commandTextField.getText());
            if (commandTextField.getText().contains(LoginCommand.COMMAND_WORD)) {
                if (numOfSpaces < 2) {
                    initialiseVariablesUsedInMasking();
                }
                handlePasswordMasking();
            }

        });
        historySnapshot = logic.getHistorySnapshot();
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

    //@@author jelneo
    /**
     * Masks password starting from the second whitespace(if it exists) until another whitespace is detected.
     */
    private void handlePasswordMasking() {
        String currentInput = commandTextField.getText();
        prevInputLength = inputLength;
        inputLength = commandTextField.getLength();
        indexOfFirstWhitespace = currentInput.indexOf(" ");
        indexOfSecondWhitespace = currentInput.indexOf(" ", indexOfFirstWhitespace + 1);
        currentMaskFromIndex = indexOfSecondWhitespace + 1;
        indexOfLastChar = inputLength - 1;

        // update index that indicates where to start masking from if the user deletes and re-enters password
        if (maskFromIndex == 0) {
            maskFromIndex = currentMaskFromIndex;
        }

        /*
         * mask password after the second whitespace and prevent the reading of the BLACK_CIRCLE after replacing
         * a character in the command box text field with a BLACK_CIRCLE
         */
        if (numOfSpaces >= 2 && currentInput.charAt(currentInput.length() - 1) != ' '
                && currentInput.charAt(currentInput.length() - 1) != BLACK_CIRCLE) {
            maskPasswordInput(currentInput);
        }

        if (!passwordFromInput.isEmpty() && inputLength < prevInputLength) {
            // case 1: user deletes password till just before second whitespace
            handleBackspaceEvent();
        } else if (passwordFromInput.isEmpty() && (indexOfLastChar - 1 != indexOfSecondWhitespace)) {
            // case 2: user deletes password including the second whitespace
            initialiseVariablesUsedInMasking();
        }
    }

    /**
     * Initialise the necessary variables for password masking.
     */
    private void initialiseVariablesUsedInMasking() {
        passwordFromInput = "";
        maskFromIndex = 0;
    }

    /**
     * Mask text field from second whitespace onwards.
     * @param currentInput the current text in the command box
     */
    private void maskPasswordInput(String currentInput) {
        // if user enters a new character, mask it with a BLACK_CIRCLE
        if (currentMaskFromIndex <= maskFromIndex && maskFromIndex <= inputLength) {
            passwordFromInput += commandTextField.getText(maskFromIndex, inputLength);
            StringBuilder maskedPasswordBuilder = new StringBuilder();
            for (int i = maskFromIndex; i < currentInput.length(); i++) {
                maskedPasswordBuilder.append(BLACK_CIRCLE);
            }
            commandTextField.replaceText(maskFromIndex, currentInput.length(), maskedPasswordBuilder.toString());
            maskFromIndex = currentInput.length();
        }
    }

    /**
     * Updates {@code passwordFromInput} field appropriately when user backspaces.
     */
    private void handleBackspaceEvent() {
        if (getNumOfSpaces(commandTextField.getText()) < 2) {
            initialiseVariablesUsedInMasking();
        } else {
            passwordFromInput = passwordFromInput.substring(0, inputLength - currentMaskFromIndex);
            maskFromIndex--;
        }
    }

    /**
     * Returns an integer that represents the number of whitespaces in a given string.
     */
    private int getNumOfSpaces(String currentInput) {
        int count = 0;
        for (int i = 0; i < currentInput.length(); i++) {
            if (currentInput.charAt(i) == ' ') {
                count++;
            }
        }
        return count;
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        String commandText = commandTextField.getText();
        String trimmedCommandText = commandText.trim();
        if (trimmedCommandText.equals(LoginCommand.COMMAND_WORD) && !isLoggedIn()) {
            commandTextField.setText("");
            raise(new ChangeToLoginViewEvent());
            return;
        }

        if (isLoggedIn() && commandText.contains(LoginCommand.COMMAND_WORD)) {
            raise(new NewResultAvailableEvent(ALREADY_LOGGED_IN_MESSAGE, true));
        } else {
            // allow only help, exit and login commands to be executed before login
            boolean isCommandExecutableBeforeLogin = commandText.contains(LoginCommand.COMMAND_WORD)
                    || commandText.contains(ExitCommand.COMMAND_WORD)
                    || commandText.contains(HelpCommand.COMMAND_WORD);

            if (isCommandExecutableBeforeLogin || isLoggedIn()) {
                try {
                    executeAllowedCommands(commandText);
                } catch (CommandException | ParseException e) {
                    initHistory();
                    // handle command failure
                    setStyleToIndicateCommandFailure();
                    logger.info("Invalid command: " + commandText);
                    raise(new NewResultAvailableEvent(e.getMessage(), true));
                }
            } else {
                // commands that are not permitted before login including unknown commands
                setStyleToIndicateCommandFailure();
                raise(new NewResultAvailableEvent(LoginCommand.MESSAGE_LOGIN_REQUEST, true));
            }
        }
    }

    /**
     * Executes the correct command based on {@code commandInput}.
     * @throws CommandException when an error occurs during execution of a command
     * @throws ParseException when an error occurs during the parsing of command arguments
     */
    private void executeAllowedCommands(String commandInput) throws CommandException, ParseException {
        CommandResult commandResult;
        if (commandInput.contains(LoginCommand.COMMAND_WORD)) {
            commandResult = logic.execute(commandInput.substring(0,
                    indexOfSecondWhitespace + 1) + passwordFromInput);
        } else {
            commandResult = logic.execute(commandInput);
        }
        initHistory();
        historySnapshot.next();
        // process result of the command
        commandTextField.setText("");
        logger.info("Result: " + commandResult.feedbackToUser);
        raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
    }
    //@@author

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

}
