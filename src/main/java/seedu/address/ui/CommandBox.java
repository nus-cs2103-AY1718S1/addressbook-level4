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
    private static final int START_OF_ADD_FIRST_FIELD = 6;
    private static final int END_OF_ADD_FIRST_FIELD = 10;
    private static final int START_OF_REMARK_FIRST_FIELD = 7;
    private static final int END_OF_REMARK_FIRST_FIELD = 12;
    private static final String[] LIST_OF_ALL_COMMANDS = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, EmailCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
        NewRolodexCommand.COMMAND_WORD, OpenRolodexCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
        RemarkCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD, StarWarsCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};

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
    private boolean needToNavigateToNextField;
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
                if (!command.equals(sr.getUserText()) && command.startsWith(sr.getUserText())) {
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
                selectPreviousField();
            } else {
                autocomplete();
            }
            //press control key to make the text selection in command box appear
            pressCtrl();
            break;
        default:
            // let JavaFx handle the keypress
        }

    }


    /**
     * press control key
     */
    public void pressCtrl() {
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
     * process input as user is typing
     */
    public void processInput() {
        setFocus();
        input = commandTextField.getText();
        updateKeyboardIcon();
        setStyleToDefault();
        if (isFirstTab) {
            autoSelectFirstField();
        }
        if (needToNavigateToNextField) {
            updateSelection();
        }
    }

    /**
     * if the command is add, and the next field is selected from pressing tab key, update the field selection
     */
    public void updateSelection() {
        commandTextField.selectRange(anchorPosition, anchorPosition + selectedText.length());
        selectedText = "";
        needToNavigateToNextField = false;
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
     * if the input matches the command format, automatically selects the first field that the user need to key in
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
            // let JavaFx handle the keypress
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

    private void changeSelectionToNextField() {
        commandTextField.selectNextWord();
        anchorPosition = commandTextField.getAnchor();
        selectedText = commandTextField.getSelectedText().trim();
    }

    /**
     * if the current input is a valid command, auto complete the full format
     * in the case of add command, if the user is trying to navigate to the next field, auto select the next field
     */
    private void autocomplete() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAutoCompleteCommand(input)) {
            displayFullFormat(input);
            needToNavigateToNextField = false;
            isFirstTab = true;
        } else if (isAddCommandFormat(input)) {
            needToNavigateToNextField = true;

            int positionOfNameField = input.indexOf(PREFIX_NAME.toString());
            int positionOfPhoneField = input.indexOf(PREFIX_PHONE.toString());
            int positionOfEmailField = input.indexOf(PREFIX_EMAIL.toString());
            int positionOfAddressField = input.indexOf(PREFIX_ADDRESS.toString());
            int positionOfCurrentCaret = commandTextField.getCaretPosition();
            int[] fieldsPositionArray = {positionOfNameField, positionOfPhoneField,
                positionOfEmailField, positionOfAddressField};

            selectNextField(positionOfCurrentCaret, fieldsPositionArray);

        } else if (isRemarkCommandFormat(input)) {
            needToNavigateToNextField = true;

            int positionOfIndex = input.indexOf(" ") - 1;
            int positionOfRemarkFiels = input.indexOf(PREFIX_REMARK.toString());
            int positionOfCurrentCaret = commandTextField.getCaretPosition();
            int[] fieldsPositionArray = {positionOfIndex, positionOfRemarkFiels};

            selectNextField(positionOfCurrentCaret, fieldsPositionArray);
        }
    }

    /**
     * pressing shift+tab changes the selection to the previous field
     * only applies to add command
     * (remark command only has two fields, using tab to toggle between the two fields is enough)
     */
    private void selectPreviousField() {
        input = commandTextField.getText().trim().toLowerCase();
        if (isAddCommandFormat(input)) {

            needToNavigateToNextField = true;

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
     * check the current position is in between which two fields
     * then navigate to the previous field
     * @param fieldsPositionArray array of field positions in the order of left to right
     */
    private void selectPreviousField(int currentPosition, int[] fieldsPositionArray) {
        boolean changedCaretPosition = false;
        for (int i = 1; i < fieldsPositionArray.length - 1; i++) {
            if (currentPosition > fieldsPositionArray[i] && currentPosition <= fieldsPositionArray[i + 1]) {
                commandTextField.positionCaret(fieldsPositionArray[i - 1] + 2);
                changeSelectionToNextField();
                changedCaretPosition = true;
            }
        }
        if (!changedCaretPosition) {
            commandTextField.positionCaret(fieldsPositionArray[fieldsPositionArray.length - 2] + 2);
            changeSelectionToNextField();
        }
    }

    /**
     * check the current position is in between which two fields
     * then navigate to the next field
     * @param fieldsPositionArray array of field positions in the order of left to right
     *                            last element is the end position of text input
     */
    private void selectNextField(int currentPosition, int[] fieldsPositionArray) {
        boolean changedCaretPosition = false;
        for (int i = 0; i < fieldsPositionArray.length - 1; i++) {
            if (currentPosition > fieldsPositionArray[i] && currentPosition < fieldsPositionArray[i + 1]) {
                commandTextField.positionCaret(fieldsPositionArray[i + 1] + 2);
                changeSelectionToNextField();
                changedCaretPosition = true;
            }
        }
        if (!changedCaretPosition) {
            commandTextField.positionCaret(fieldsPositionArray[0] + 2);
            changeSelectionToNextField();
        }
    }

    /**
     * if the command input is a valid command that requires additional field(s), display the full
     * format in the textfield
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
