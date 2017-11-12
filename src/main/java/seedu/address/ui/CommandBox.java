package seedu.address.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

//@@author justintkj
/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {
    public static final String AUTOCOMPLETE_FILE_NAME = "Autocomplete.xml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String STORAGE_FILE_NAME = "Autocomplete.xml";
    public static final String ERROR_MESSAGE_CREATE_FILE_FAILED = "Unable to create file Autocomplete.xml";

    public static final AddressBookStorage NULL_ADDRESSBOOK_STORAGE = null;
    public static final UserPrefsStorage NULL_USER_PREFS_STORAGE = null;

    private static final String FXML = "CommandBox.fxml";
    private static ArrayList<String> mainPossibleSuggestion;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    protected Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);

    @FXML
    private TextField commandTextField;
    private ArrayList<String> prevText = new ArrayList<String>();
    private AutoCompletionBinding autocompletionbinding;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        updateAutocompleteTextField();
        autocompletionbinding = TextFields.bindAutoCompletion(commandTextField, mainPossibleSuggestion);
    }
    //@@author justintkj
    /**
     * updates the autocompleteTextField
     */
    private void updateAutocompleteTextField() {
        try {
            mainPossibleSuggestion = storage.updateAutocomplete();
        } catch (IOException ioe) {
            raise(new DataSavingExceptionEvent(ioe));
        }
    }
    //@@author
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

        }
    }
    //@@author justintkj

    /**
     * Adds in a valid command string into autocomplete.xml storage
     * @param commandWord
     * @throws CommandException if autocomplete.xml cannot be made.
     */
    public static void setAddSuggestion(String commandWord) throws CommandException {
        Storage storage = generateStorage();
        mainPossibleSuggestion = storage.setAddSuggestion(commandWord);
    }

    /**
     * Creates an empty addressbook storageManager instance to be used for autocomplete
     * @return StorageManager
     */
    private static Storage generateStorage() {
        AddressBookStorage addressBookStorage = NULL_ADDRESSBOOK_STORAGE;
        UserPrefsStorage userPrefsStorage = NULL_USER_PREFS_STORAGE;
        return new StorageManager(addressBookStorage, userPrefsStorage);
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
            autocompletionbinding.dispose();
            autocompletionbinding = TextFields.bindAutoCompletion(commandTextField, mainPossibleSuggestion);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.warning("Invalid command: " + commandTextField.getText());
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
