package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
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
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private int numOfSpaces = 0;
    private int maskFromIndex = 0;
    private String passwordFromInput = "";
    private int inputLength = 0;
    private int prevInputLength = 0;
    private int indexOfFirstWhitespace = 0;
    private int currentMaskFromIndex = 0;


    @FXML
    private TextField commandTextField;


    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            if (commandTextField.getText().contains(LoginCommand.COMMAND_WORD)) {
                Platform.runLater(() -> handlePasswordMasking());
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
    private void handlePasswordMasking() {
        String currentInput = commandTextField.getText();
        numOfSpaces = getNumOfSpaces(currentInput);
        prevInputLength = inputLength;
        inputLength = currentInput.length();
        indexOfFirstWhitespace = currentInput.indexOf(" ");
        currentMaskFromIndex = currentInput.indexOf(" ", indexOfFirstWhitespace + 1) + 1;
        // update index indicating where to start masking from if user deletes and re-enters password
        if (maskFromIndex == 0) {
            maskFromIndex = currentMaskFromIndex;
        }
        // handles the case where user backspaces and change password input from current caret location to
        // before second whitespace
        if (!passwordFromInput.isEmpty() && inputLength < prevInputLength) {
            handleBackspaceEvent();
        }
        // user's caret is before the second whitespace backspace or after second whitespace
        // initialise fields if password is keyed in then deleted
        if (passwordFromInput.isEmpty() && prevInputLength != 0) {
            initialiseVariablesUsedInMasking();
        }
        // starts masking password after the second whitespace and prevent the reading of the asterisk after replacing
        // a character in the command box text field with an asterisk
        if (numOfSpaces >= 2 && currentInput.charAt(currentInput.length() - 1) != ' ' &&
                currentInput.charAt(currentInput.length() - 1) != BLACK_CIRCLE) {
            maskPasswordInput(currentInput);

        }
    }

    /**
     * Initialise variables that are used in password masking
     */
    private void initialiseVariablesUsedInMasking() {
        passwordFromInput = "";
        maskFromIndex = 0;
        inputLength = 0;
        prevInputLength = 0;
    }

    /**
     * Mask text field from second whitespace onwards
     * @param currentInput
     */
    private void maskPasswordInput(String currentInput) {
        // if user enters a new character, mask it with an asterisk
        if (currentMaskFromIndex <= maskFromIndex) {
            passwordFromInput += commandTextField.getText(maskFromIndex, inputLength);
            commandTextField.replaceText(maskFromIndex, currentInput.length(), Character.toString(BLACK_CIRCLE));
            maskFromIndex++;
        }
    }

    /**
     * Updates {@code passwordFromInput} field appropriately when user backspaces
     */
    private void handleBackspaceEvent() {
        passwordFromInput = passwordFromInput.substring(0, inputLength - currentMaskFromIndex);
        maskFromIndex--;
    }

    /**
     * Returns an integer that represents the number of spaces in a given string
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
    //@@author

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            String commandText = commandTextField.getText();
            if (commandText.contains(LoginCommand.COMMAND_WORD) || commandText.contains(ExitCommand.COMMAND_WORD)
                    || commandText.contains(HelpCommand.COMMAND_WORD) || LoginCommand.isLoggedIn()) {
                String currCommandInput = commandTextField.getText();
//                logger.info(String.valueOf(LoginCommand.isLoggedIn()));
                CommandResult commandResult;
                if (currCommandInput.contains(LoginCommand.COMMAND_WORD)) {
                    commandResult = logic.execute(currCommandInput.substring(0,
                            currentMaskFromIndex) + passwordFromInput);
                } else {
                    commandResult = logic.execute(currCommandInput);
                }
                initHistory();
                historySnapshot.next();
                // process result of the command
                commandTextField.setText("");
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            } else {
                // commands that are not permitted before login
                // this includes unknown commands
                setStyleToIndicateCommandFailure();
                raise(new NewResultAvailableEvent(LoginCommand.MESSAGE_LOGIN_REQUEST, true));
            }
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

}
