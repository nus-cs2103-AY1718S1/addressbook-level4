package seedu.address.ui;

import java.util.Arrays;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import org.controlsfx.control.textfield.TextFields;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int TIME_SINCE_TYPING = 300;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private Image keyboardIdle;
    private Image keyboardTyping;
    private Image keyboardError;
    private PauseTransition pause;
    private int anchorPosition;
    private int caretPosition;
    private String selectedText = "";
    private String input;
    final String ADD_COMMAND_FORMAT = "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS";
    final String EDIT_COMMAND_FORMAT = "edit INDEX [Field(s) you want to change]";
    final String FIND_COMMAND_FORMAT = "find KEYWORD(S)";
    final String SELECT_COMMAND_FORMAT = "select INDEX";
    final String DELETE_COMMAND_FORMAT = "delete INDEX";
    final String[] AUTOCOMPLETE_COMMAND_LIST = {"add", "a", "delete", "d", "edit", "e", "find", "f", "search",
            "list", "l", "select", "s"};
    final String[] ADD_COMMAND_FIELD_LIST = {"NAME", "PHONE_NUMBER", "EMAIL", "ADDRESS", "TAG", "INDEX", "KEYWORD"};
    final String[] ALL_COMMAND_LIST = {"add", "a", "delete", "edit", "find", "search",
            "list", "l", "select", "s"};


    @FXML
    private TextField commandTextField;

    @FXML
    private ImageView keyboardIcon;


    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        loadKeyboardIcons();
        keyboardIcon.setImage(keyboardIdle);
        pause = new PauseTransition(Duration.millis(TIME_SINCE_TYPING));
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        TextFields.bindAutoCompletion(commandTextField, ALL_COMMAND_LIST);
        //input = commandTextField.getText().trim().toLowerCase();
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        //commandTextField.textProperty().addListener(((observable, oldValue, newValue) -> autocomplete()));
        historySnapshot = logic.getHistorySnapshot();
    }

    public void setFocus() {
        commandTextField.requestFocus();
    }

    /**
     * Load images for keyboard icons in the command box.
     */
    private void loadKeyboardIcons() {
        keyboardIdle = new Image(getClass().getResourceAsStream("/images/keyboard.png"));
        keyboardTyping = new Image(getClass().getResourceAsStream("/images/keyboardTyping.png"));
        keyboardError = new Image(getClass().getResourceAsStream("/images/keyboardError.png"));
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
            autocomplete();
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
        //commandTextField.positionCaret(commandTextField.getText().length());
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
     * Sets the command box style to use the default style.
     * {@code keyboardTyping} icon changes to {@code keyboardIdle} when there is no change
     * to text field after some time.
     */
    protected void setStyleToDefault() {
        input = commandTextField.getText();
        updateKeyboardIconAndStyle();
        autoSelectFirstField();
        if (input.startsWith("add ") && Arrays.asList(ADD_COMMAND_FIELD_LIST).contains(selectedText)) {
            updateSelection();
        }
    }

    private void updateSelection() {
        commandTextField.selectRange(anchorPosition, anchorPosition + selectedText.length());
        selectedText = "";
    }

    private void updateKeyboardIconAndStyle() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        keyboardIcon.setImage(keyboardTyping);
        pause.setOnFinished(event -> {
            if (!styleClass.contains(ERROR_STYLE_CLASS)) {
                keyboardIcon.setImage(keyboardIdle);
            }
        });
        pause.playFromStart();
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    private void autoSelectFirstField() {
        setFocus();
        switch (input) {
            case ADD_COMMAND_FORMAT:
                commandTextField.selectRange(6, 10);
                break;
            case EDIT_COMMAND_FORMAT:
            case FIND_COMMAND_FORMAT:
            case SELECT_COMMAND_FORMAT:
            case DELETE_COMMAND_FORMAT:
                int indexOfFirstSpace = input.indexOf(" ");
                commandTextField.selectRange(indexOfFirstSpace + 1, input.length());
                break;
        }
    }

    protected boolean isAutoCompleteCommand(String command) {
        return Arrays.asList(AUTOCOMPLETE_COMMAND_LIST).contains(command);
    }

    protected boolean isAddCommandFormat(String input) {
        return input.startsWith("add") &&
                input.contains("n/") && input.contains("p/") && input.contains("e/") && input.contains("a/");
    }

    protected void changeSelectionToNextField() {
        commandTextField.selectNextWord();
        anchorPosition = commandTextField.getAnchor();
        caretPosition = commandTextField.getCaretPosition();
        selectedText = commandTextField.getSelectedText().toString().trim();
    }


    protected void autocomplete() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAutoCompleteCommand(input)) {
            displayFullFormat(input);
        } else if (isAddCommandFormat(input)){
            int positionOfNameField = input.indexOf("n/");
            int positionOfPhoneField = input.indexOf("p/");
            int positionOfEmailField = input.indexOf("e/");
            int positionOfAddressField = input.indexOf("a/");
            int currentPosition = commandTextField.getCaretPosition();
            if (currentPosition > positionOfNameField && currentPosition < positionOfPhoneField) {
                commandTextField.positionCaret(positionOfPhoneField + 2);
                changeSelectionToNextField();
            } else if (currentPosition > positionOfPhoneField && currentPosition < positionOfEmailField) {
                commandTextField.positionCaret(positionOfEmailField + 2);
                changeSelectionToNextField();
            } else if (currentPosition > positionOfEmailField && currentPosition < positionOfAddressField) {
                commandTextField.positionCaret(positionOfAddressField + 2);
                changeSelectionToNextField();
            } else {

            }
        }
    }

    private void displayFullFormat(String command) {
        switch (command) {
            case "add":
            case "a":
                replaceText(ADD_COMMAND_FORMAT);
                break;
            case "edit":
            case "e":
                replaceText(EDIT_COMMAND_FORMAT);
                break;
            case "find":
            case "f":
            case "search":
                replaceText(FIND_COMMAND_FORMAT);
                break;
            case "select":
            case "s":
                replaceText(SELECT_COMMAND_FORMAT);
                break;
            case "delete":
            case "d":
                replaceText(DELETE_COMMAND_FORMAT);
                break;
            default:
        }
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    protected void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
        keyboardIcon.setImage(keyboardError);
    }

}
