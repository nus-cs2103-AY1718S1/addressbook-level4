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
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LoginCommand;
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
    private int numOfSpaces = 0;
    private int maskFromIndex = 0;
    private boolean isMaskIndexFound = false;
    private String passwordFromInput = "";
    private int inputLength = 0;
    private int prevInputLength = 0;

    @FXML
    private TextField commandTextField;


    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            if (commandTextField.getText().contains(LoginCommand.COMMAND_WORD)) {
                //handlePasswordMasking();
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

//    //@@author jelneo
//    private String handlePasswordMasking() {
//        String currentInput = commandTextField.getText();
////        logger.info("current in:" + currentInput + "-");
//        numOfSpaces = getNumOfSpaces(currentInput);
//        // prevent re-execution of after two spaces are found in input
//        logger.info("numspace"+String.valueOf(numOfSpaces));
//        // there are two spaces in a valid login command input
//        // e.g. login[SPACE]username[SPACE]password
//        prevInputLength = inputLength;
//        inputLength = currentInput.length();
//
//        int currentMaskFromIndex = currentInput.indexOf(" ", currentInput.indexOf(" ") + 1) + 1;
//        if (maskFromIndex == 0) {
//            maskFromIndex = currentMaskFromIndex;
//        }
//
//        // user backspace till after 2nd space
//        logger.info("curr in len:" + inputLength + " pre in len:" + prevInputLength);
//        if (!passwordFromInput.isEmpty() && inputLength < prevInputLength) {
//            logger.info("sfef " + currentMaskFromIndex + inputLength);
//            passwordFromInput = passwordFromInput.substring(0, inputLength - currentMaskFromIndex);
//            maskFromIndex--;
//        }
//
//        // user backspace till 2nd space or delete 2nd space
//        if (passwordFromInput.isEmpty() && prevInputLength != 0) {
//            passwordFromInput = "";
//            maskFromIndex = 0;
//            inputLength = 0;
//            prevInputLength = 0;
//        }
//
//        logger.info("last char:"+String.valueOf(currentInput.charAt(currentInput.length() - 1)));
//        if (numOfSpaces >= 2 && currentInput.charAt(currentInput.length() - 1) != ' ' && currentInput.charAt(currentInput.length() - 1) != '*') {
//            //mask the input from second space onwards
//
//            // recalculate index to mask from if user re-enters password
//            if (currentMaskFromIndex <= maskFromIndex) {
//                logger.info("mask frm index:" + String.valueOf(maskFromIndex));
//                isMaskIndexFound = true;
//                logger.info("maskfrom: " + maskFromIndex + " caret: " + String.valueOf(inputLength));
//                logger.info("to replace: " + commandTextField.getText(maskFromIndex, inputLength));
//
//                passwordFromInput += commandTextField.getText(maskFromIndex, inputLength);
//                commandTextField.replaceText(maskFromIndex, currentInput.length(), "*");
//                maskFromIndex++;
//            }
//        }
//        logger.info("password:" + passwordFromInput + "!");
//        return passwordFromInput;
//    }
//
//    /**
//     * Returns an integer that represents the number of spaces in a given string
//     */
//    private int getNumOfSpaces(String currentInput) {
//        int count = 0;
//        for (int i = 0; i < currentInput.length(); i++ ) {
//            if (currentInput.charAt(i) == ' ') {
//                count++;
//            }
//        }
//        return count;
//    }
//    //@@author

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            String commandText = commandTextField.getText();
            if (commandText.contains(LoginCommand.COMMAND_WORD) || commandText.contains(ExitCommand.COMMAND_WORD)
                    || commandText.contains(HelpCommand.COMMAND_WORD) || LoginCommand.isLoggedIn()) {
                CommandResult commandResult = logic.execute(commandTextField.getText());
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
