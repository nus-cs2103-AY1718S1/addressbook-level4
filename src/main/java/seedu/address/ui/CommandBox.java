package seedu.address.ui;

import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.font.FontSize;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    //@@author pjunwei95
    private static final String[] suggestedWords = {"add", "delete", "edit", "find",
                                                    "select", "search", "deletetag", "findtag",
                                                    "photo", "facebook", "color",
                                                    "fs", "remark", "map", "theme"};
    //@@author
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private AddressBookParser addressBookParser;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();

        setFontSize(FontSize.getCurrentFontSizeLabel());
        registerAsAnEventHandler(this);
        addressBookParser = new AddressBookParser();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        logger.info("Handling key press.");
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

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            String command = commandTextField.getText();
            CommandResult commandResult = logic.execute(command);
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
     * Handle a input command by passing a string
     */
    public void handleCommandInputChanged(String inputCommand) {
        try {
            CommandResult commandResult = logic.execute(inputCommand);
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
            logger.info("Invalid command: " + inputCommand);
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

    //@@author ChenXiaoman

    /**
     * Handles the key released event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {

        String userInput = commandTextField.getText();

        // If the user has not type in anything yet, there is no need to show error message
        if (userInput.length() != 0) {

            // Parse the user input while user is typing and show the error message if the command is invalid
            parseInput(keyEvent.getCode(), userInput);
        }
    }

    /**
     * Parse user input and raise event to show corresponding message
     */
    private void parseInput(KeyCode keyCode, String userInput) {
        logger.info("Parsing user input: " + userInput);
        try {
            // Try to parse the command to check whether the command is valid
            addressBookParser.parseCommand(userInput);

            if (!keyCode.equals(KeyCode.ENTER)) {

                // If the command is valid, show format valid message
                // If user presses enter key to execute the command, don't show parse message
                raise(new NewResultAvailableEvent("Command format is valid", false));
            }
        } catch (ParseException e) {

            // If user is entering invalid command, shows error message
            raise(new NewResultAvailableEvent(e.getMessage(), true));
        }
    }

    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String newFontSize) {
        String fxFormatFontSize = FontSize.getAssociateFxFontSizeString(newFontSize);
        commandTextField.setStyle(fxFormatFontSize);
    }

    //@@author pjunwei95
    @FXML
    /**
     * Sets the command box style allow autocompletion.
     * @param suggestedWords - list of words that will autocomplete
     */
    private void initialize() {
        TextFields.bindAutoCompletion(commandTextField, suggestedWords);
    }

}
