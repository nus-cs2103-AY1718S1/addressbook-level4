package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String[] allSuggestions = {"add", "a n/ p/ e/ a/ b/ s/ r/ t/", "clear", "delete",
        "edit", "e n/ p/ e/ a/ b/ s/ r/ t/", "exit", "x", "find", "f", "help", "sort name", "history",
        "list", "removeTag", "select", "undo", "redo", "sort email", "sort phone", "sort address", "sort tag",
        "listBirthday", "sms", "text/", "mail", "remark", "toggle", "reauthenticate"};
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private List<String> listedSuggestions = new ArrayList<>(Arrays.asList(allSuggestions));
    private ContextMenu suggestionsMenu = new ContextMenu();

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        createSuggestions();
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
            keyEvent.consume();
            suggestionsMenu.hide();
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

    // @@author justuswah
    /**
     * Creates a list of Matched Suggestions based on User Input
     */

    public void createSuggestions() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String userInput = commandTextField.getText();
            List<String> matchedSuggestions = listedSuggestions.stream()
                .filter(i-> i.toLowerCase().startsWith(userInput.toLowerCase()))
                .collect(Collectors.toList());

            if (matchedSuggestions.contains(userInput) || userInput.isEmpty() || matchedSuggestions.isEmpty()) {
                suggestionsMenu.hide();
            } else {
                createPopupWindow(matchedSuggestions);
                if (!suggestionsMenu.isShowing()) {
                    suggestionsMenu.show(this.commandTextField, Side.TOP, 200, 0); // Popup position.
                }
            }
        });
    }

    /**
    * Instantiates the Suggestions Popup Window based on the list {@code listedSuggestions}.
    *
    */

    private void createPopupWindow(List<String> matchedSuggestions) {
        List<CustomMenuItem> popupMenu = new ArrayList<>();
        suggestionsMenu.getItems().clear();
        for (int i = 0; i < matchedSuggestions.size(); i++) {
            Label suggestion = new Label(matchedSuggestions.get(i));
            suggestion.setPrefHeight(30);
            CustomMenuItem item = new CustomMenuItem(suggestion, true);
            popupMenu.add(item);

            item.setOnAction(actionEvent -> {
                logger.log(Level.INFO, suggestion.getText());
                suggestionsMenu.hide();
                commandTextField.setText(suggestion.getText());
                commandTextField.positionCaret(suggestion.getText().length());
            });
        }
        suggestionsMenu.getItems().addAll(popupMenu);
    }
}
