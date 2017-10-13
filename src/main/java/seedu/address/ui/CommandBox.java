package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
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
        case ESCAPE:
            commandTextField.setText("");
            break;
        case ALT:
            commandTextField.positionCaret(0);
            break;
        case CONTROL:
            commandTextField.positionCaret(commandTextField.getText().length());
            break;
        case RIGHT:
            //Check if user's Caret is at the end of the text input
            boolean isCaretWithin = commandTextField.getCaretPosition() < commandTextField.getText().length();
            //If caret is not at the end of text, do nothing
            if (isCaretWithin) {
                break;
            } else { //If caret is at the end, deploy hack that makes user life easy for add command
                String finalText;
                //If only add is present, concat prefix name string
                if (isAdd()) {
                    finalText = concatPrefix(PREFIX_NAME);
                }
                //Checks if necessary prefixes are present
                //Checks based on priority : n/ p/ e/ a/ b/ t/ prefixes
                else if (!containsName()) {
                    finalText = concatPrefix(PREFIX_NAME);
                } else if (!containsPhone()) {
                    finalText = concatPrefix(PREFIX_PHONE);
                } else if (!containsEmail()) {
                    finalText = concatPrefix(PREFIX_EMAIL);
                } else if (!containsAddress()) {
                    finalText = concatPrefix(PREFIX_ADDRESS);
                } else if (!containsBloodtype()) {
                    finalText = concatPrefix(PREFIX_BLOODTYPE);
                } else if (containsAllCompulsoryPrefix()) {
                    finalText = concatPrefix(PREFIX_TAG);
                } else {
                    break;
                }
                commandTextField.setText(finalText);
                commandTextField.positionCaret(finalText.length());
                break;
            }
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Checks if the commandTextField has the word "add" only
     */
    private boolean isAdd() {
        boolean perfectAdd = commandTextField.getText().equalsIgnoreCase("add");
        boolean spacedEndAdd = commandTextField.getText().equalsIgnoreCase("add ");
        boolean spacedStartAdd = commandTextField.getText().equalsIgnoreCase(" add");
        boolean perfectA = commandTextField.getText().equalsIgnoreCase("a");
        boolean spacedEndA = commandTextField.getText().equalsIgnoreCase("a ");
        boolean spacedStartA = commandTextField.getText().equalsIgnoreCase(" a");
        return perfectAdd || spacedEndAdd || spacedStartAdd || perfectA || spacedEndA || spacedStartA;
    }

    /**
     * Checks if the commandTextField all prefixes excluding tag
     */
    private boolean containsAllCompulsoryPrefix() {
        return containsAddress() && containsEmail() && containsBloodtype() &&
                containsName() && containsPhone();
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
