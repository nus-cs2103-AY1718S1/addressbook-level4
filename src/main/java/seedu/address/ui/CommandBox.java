package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;
import org.testfx.api.FxRobot;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StarWarsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.SuggestibleParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final int MILLISECONDS_SINCE_TYPING = 300;
    private static final int MILLISECONDS_PAUSE_BEFORE_PRESSING_CTRL = 100;
    //Start and end position of the placeholder 'NAME' in the full format of add command
    private static final int START_OF_ADD_FIRST_FIELD = 6;
    private static final int END_OF_ADD_FIRST_FIELD = 10;
    //Start and end position of the placeholder 'INDEX' in the full format of remark command
    private static final int START_OF_REMARK_FIRST_FIELD = 7;
    private static final int END_OF_REMARK_FIRST_FIELD = 12;
    private static final String[] LIST_OF_ALL_COMMANDS = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, EmailCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
        NewRolodexCommand.COMMAND_WORD, OpenRolodexCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        RemarkCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, StarWarsCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};
    //The distance from the start of the prefix to the start of placeholder is two
    private static final int JUMP_TO_START_OF_PLACEHOLDER = 2;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private Image keyboardIdle;
    private Image keyboardTyping;
    private Image keyboardError;
    private PauseTransition pause;
    private int anchorPosition;
    private String selectedText = "";
    private String input;
    private FxRobot robot;
    private Set<String> setOfAutoCompleteCommands = new HashSet<>();
    private boolean needToUpdateSelection;
    private boolean isFirstTab;



    @FXML
    private TextField commandTextField;

    @FXML
    private ImageView keyboardIcon;


    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        this.robot = new FxRobot();
        loadKeyboardIcons();
        keyboardIcon.setImage(keyboardIdle);
        pause = new PauseTransition(Duration.millis(MILLISECONDS_SINCE_TYPING));

        TextFields.bindAutoCompletion(commandTextField, sr -> {
            Set<String> suggestedCommands = new HashSet<>();
            for (String command: LIST_OF_ALL_COMMANDS) {
                if (!sr.getUserText().isEmpty() && !command.equals(sr.getUserText())
                        && command.startsWith(sr.getUserText().trim().toLowerCase())) {

                    suggestedCommands.add(command);
                }
            }
            return suggestedCommands;
        });

        setOfAutoCompleteCommands.addAll(AddCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(DeleteCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(EditCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(EmailCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(FindCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(RemarkCommand.COMMAND_WORD_ABBREVIATIONS);
        setOfAutoCompleteCommands.addAll(SelectCommand.COMMAND_WORD_ABBREVIATIONS);

        // calls #processInput() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> processInput());
        historySnapshot = logic.getHistorySnapshot();
    }

    public void setFocus() {
        commandTextField.requestFocus();
    }

    public boolean isFocused() {
        return commandTextField.isFocused();
    }

    /**
     * Loads images for keyboard icons in the command box.
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

            if (keyEvent.isShiftDown()) {
                //nagivate to the previous field when shift+tab is pressed
                selectPreviousField();
            } else {
                autocomplete();
            }

            //press control key to make the text selection highlight appear in command box
            pressCtrl();
            break;

        default:
            //key pressed does not match UP, DOWN or TAB
            //let JavaFx handle the keypress
        }

    }

    /**
     * press tab key, used in KeyListener class to trigger auto-completion
     */
    public void pressTab() {
        robot.push(KeyCode.TAB);
    }

    /**
     * press control key, used to display selected text
     */
    private void pressCtrl() {
        //add pause to prevent pressing tab before the input is updated
        pause = new PauseTransition();
        pause.setDuration(Duration.millis(MILLISECONDS_PAUSE_BEFORE_PRESSING_CTRL));
        pause.setOnFinished(event -> {
            robot.push(KeyCode.CONTROL);
        });
        pause.play();
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
    protected void replaceText(String text) {
        commandTextField.setText(text);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    protected void handleCommandInputChanged() {
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
            setErrorKeyboardIcon();
            logger.info("Invalid command, un-suggestible: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (SuggestibleParseException e) {
            initHistory();
            // handle command failure
            commandTextField.setText("");
            setStyleToIndicateCommandFailure();
            setErrorKeyboardIcon();
            logger.info("Invalid command, suggestible: " + commandTextField.getText());
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
     * Processes input as user is typing
     */
    public void processInput() {
        setFocus();
        input = commandTextField.getText();
        updateKeyboardIcon();
        setStyleToDefault();

        if (isFirstTab) {
            //only do this after the first tab which is used to display the full command format
            autoSelectFirstField();
        }

        if (needToUpdateSelection) {
            updateSelection();
        }
    }

    /**
     * Updates the text selection in command box
     */
    public void updateSelection() {
        commandTextField.selectRange(anchorPosition, anchorPosition + selectedText.length());
        selectedText = "";
        needToUpdateSelection = false;
    }

    /**
     * Changes {@code Image keyboardTyping} icon to {@code Image keyboardIdle} when there is no change
     * to text field after some time.
     */
    private void updateKeyboardIcon() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        keyboardIcon.setImage(keyboardTyping);

        pause.setOnFinished(event -> {
            if (!styleClass.contains(ERROR_STYLE_CLASS)) {
                keyboardIcon.setImage(keyboardIdle);
            }
        });
        pause.playFromStart();
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Automatically selects the first field that the user needs to key in
     */
    public void autoSelectFirstField() {
        switch (input) {
        case AddCommand.FORMAT:
            commandTextField.selectRange(START_OF_ADD_FIRST_FIELD, END_OF_ADD_FIRST_FIELD);
            break;
        case RemarkCommand.FORMAT:
            commandTextField.selectRange(START_OF_REMARK_FIRST_FIELD, END_OF_REMARK_FIRST_FIELD);
            break;
        case EditCommand.FORMAT:
        case EmailCommand.FORMAT:
        case FindCommand.FORMAT:
        case SelectCommand.FORMAT:
        case DeleteCommand.FORMAT:
        case NewRolodexCommand.FORMAT:
        case OpenRolodexCommand.FORMAT:
            int indexOfFirstSpace = input.indexOf(" ");
            commandTextField.selectRange(indexOfFirstSpace + 1, input.length());
            break;
        default:
            //input is not an auto-complete command
            //do nothing
        }

        isFirstTab = false;
    }

    private boolean isAutoCompleteCommand(String command) {
        return setOfAutoCompleteCommands.contains(command);
    }

    private boolean isAddCommandFormat(String input) {
        return input.startsWith("add")
                && input.contains("n/") && input.contains("p/") && input.contains("e/") && input.contains("a/");
    }

    private boolean isRemarkCommandFormat(String input) {
        return input.startsWith("remark") && input.contains("r/");
    }

    /**
     * Selects the word following the current caret position
     */
    private void changeSelectionToNextField() {
        commandTextField.selectNextWord();
        anchorPosition = commandTextField.getAnchor();
        selectedText = commandTextField.getSelectedText().trim();
    }

    /**
     * Displays the full format of the command
     * In the case of add or remark command, if the user is trying to navigate to the next field (full format
     * is already diaplayed), auto-select the next field
     */
    private void autocomplete() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAutoCompleteCommand(input)) {
            displayFullFormat(input);
            needToUpdateSelection = false;
            isFirstTab = true;

        } else if (isAddCommandFormat(input)) {
            needToUpdateSelection = true;

            int positionOfNameField = input.indexOf(PREFIX_NAME.toString());
            int positionOfPhoneField = input.indexOf(PREFIX_PHONE.toString());
            int positionOfEmailField = input.indexOf(PREFIX_EMAIL.toString());
            int positionOfAddressField = input.indexOf(PREFIX_ADDRESS.toString());
            int positionOfCurrentCaret = commandTextField.getCaretPosition();
            int[] fieldsPositionArray = {positionOfNameField, positionOfPhoneField,
                positionOfEmailField, positionOfAddressField};

            selectNextField(positionOfCurrentCaret, fieldsPositionArray);

        } else if (isRemarkCommandFormat(input)) {
            needToUpdateSelection = true;

            int positionOfIndex = input.indexOf(" ") - 1;
            int positionOfRemarkFiels = input.indexOf(PREFIX_REMARK.toString());
            int positionOfCurrentCaret = commandTextField.getCaretPosition();
            int[] fieldsPositionArray = {positionOfIndex, positionOfRemarkFiels};

            selectNextField(positionOfCurrentCaret, fieldsPositionArray);
        }
    }

    /**
     * Changes text selection to the previous field
     * Only applies to add command
     * (Remark command only has two fields, using tab to toggle between the two fields is enough)
     */
    private void selectPreviousField() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAddCommandFormat(input)) {

            needToUpdateSelection = true;

            int positionOfNameField = input.indexOf(PREFIX_NAME.toString());
            int positionOfPhoneField = input.indexOf(PREFIX_PHONE.toString());
            int positionOfEmailField = input.indexOf(PREFIX_EMAIL.toString());
            int positionOfAddressField = input.indexOf(PREFIX_ADDRESS.toString());
            int positionOfEnd = input.length();
            int positionOfCurrentCaret = commandTextField.getCaretPosition();

            int[] fieldsPositionArray = {positionOfNameField, positionOfPhoneField, positionOfEmailField,
                positionOfAddressField, positionOfEnd};
            selectPreviousField(positionOfCurrentCaret, fieldsPositionArray);
        }
    }

    /**
     * Checks the current position is in between which two fields
     * And navigates to the previous field
     * @param fieldsPositionArray array of field positions in the order of left to right
     */
    private void selectPreviousField(int currentPosition, int[] fieldsPositionArray) {
        boolean changedCaretPosition = false;
        for (int i = 1; i < fieldsPositionArray.length - 1; i++) {
            //check if the current position is in between field[i] and field[i + 1], if so, change selection
            //to the placeholder of field[i - 1]
            if (currentPosition > fieldsPositionArray[i] && currentPosition <= fieldsPositionArray[i + 1]) {
                commandTextField.positionCaret(fieldsPositionArray[i - 1] + JUMP_TO_START_OF_PLACEHOLDER);
                changeSelectionToNextField();
                changedCaretPosition = true;
            }
        }
        if (!changedCaretPosition) {
            //if caret position is not changed in the above for loop,
            //which means the caret is currently at the last field, then change selection to the second last field
            //so that continuously pressing shift+tab will go in a cycle
            commandTextField.positionCaret(fieldsPositionArray[fieldsPositionArray.length - 2]
                    + JUMP_TO_START_OF_PLACEHOLDER);
            changeSelectionToNextField();
        }
    }

    /**
     * Checks the current position is in between which two fields
     * And navigates to the next field
     * @param fieldsPositionArray array of field positions in the order of left to right
     *                            last element is the end position of text input
     */
    private void selectNextField(int currentPosition, int[] fieldsPositionArray) {
        boolean changedCaretPosition = false;
        for (int i = 0; i < fieldsPositionArray.length - 1; i++) {
            //check if the current position is in between field[i] and field[i + 1], if so, change selection
            //to the placeholder of field[i + 1]
            if (currentPosition > fieldsPositionArray[i] && currentPosition < fieldsPositionArray[i + 1]) {
                commandTextField.positionCaret(fieldsPositionArray[i + 1] + JUMP_TO_START_OF_PLACEHOLDER);
                changeSelectionToNextField();
                changedCaretPosition = true;
            }
        }
        if (!changedCaretPosition) {
            //if caret position is not changed in the above for loop,
            //which means the caret is currently at the last field, then change selection to the first field
            //so that continuously pressing tab will go in a cycle
            commandTextField.positionCaret(fieldsPositionArray[0] + JUMP_TO_START_OF_PLACEHOLDER);
            changeSelectionToNextField();
        }
    }

    /**
     * Displays the full command format in command box
     * @param command input by the user
     */
    private void displayFullFormat(String command) {
        if (AddCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(AddCommand.FORMAT);
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(EditCommand.FORMAT);
        } else if (EmailCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(EmailCommand.FORMAT);
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(FindCommand.FORMAT);
        } else if (NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(NewRolodexCommand.FORMAT);
        } else if (OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(OpenRolodexCommand.FORMAT);
        } else if (RemarkCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(RemarkCommand.FORMAT);
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(SelectCommand.FORMAT);
        } else if (DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(command)) {
            replaceText(DeleteCommand.FORMAT);
        }
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

    private void setErrorKeyboardIcon() {
        keyboardIcon.setImage(keyboardError);
    }
}
