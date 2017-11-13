package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private static final int maxSuggestionsSize = 5;
    private static final String[] suggestions = {"add", "a n/ p/ b/ e/ a/", "clear", "changetheme",
        "ct", "delete", "edit", "exit", "export", "find", "help",
        "sos", "history", "home", "ho", "list", "birthdaystatistics", "bstats", "tagstatistics", "tstats",
        "select", "undo", "redo"};
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;
    private Set<String> setOfSuggestions;
    private ContextMenu suggestionsPopup;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        this.setOfSuggestions = new HashSet<>(Arrays.asList(suggestions));
        this.suggestionsPopup = new ContextMenu(); // Initalise popup menu.

        addSuggestionsListener(); // Observes the text input and show matched suggestions.

        // Calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
    }

    // @@author johnweikangong
    /**
     * Observes the text input and show matched suggestions.
     */
    private void addSuggestionsListener() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String textInput = commandTextField.getText();

            List<String> matchedSuggestions = setOfSuggestions.stream()
                    .filter(e -> e.toLowerCase().contains(textInput.toLowerCase()))
                    .collect(Collectors.toList());

            if (textInput == null || textInput.isEmpty() || matchedSuggestions.isEmpty()) {
                suggestionsPopup.hide();
            } else {
                showPopup(matchedSuggestions);

                if (!suggestionsPopup.isShowing()) {
                    suggestionsPopup.show(this.commandTextField, Side.BOTTOM, 0, 0); // Popup position.
                }
            }
        });
    }

    /**
     * Shows the set of suggestions in the context menu with the {@code matchedSuggestions}.
     *
     * @param matchedSuggestions The set of matching suggestions.
     */
    private void showPopup(List<String> matchedSuggestions) {
        List<CustomMenuItem> menuItems = new ArrayList<>();
        int maxMenuItemsSize = Math.min(matchedSuggestions.size(), maxSuggestionsSize);

        for (int i = 0; i < maxMenuItemsSize; i++) {
            Label suggestionLabel = new Label(matchedSuggestions.get(i));
            suggestionLabel.setPrefHeight(20);
            CustomMenuItem item = new CustomMenuItem(suggestionLabel, true);
            menuItems.add(item);
            logger.info(suggestionLabel.getText());

            // On selecting a menu item, set the selected menu item into the command text field and close popup.
            item.setOnAction(actionEvent -> {
                commandTextField.setText(suggestionLabel.getText());
                commandTextField.positionCaret(suggestionLabel.getText().length());
                suggestionsPopup.hide();
            });
        }

        suggestionsPopup.getItems().clear();
        suggestionsPopup.getItems().addAll(menuItems);
    }
    // @@author

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged.
            keyEvent.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case ESCAPE:
            keyEvent.consume();
            suggestionsPopup.hide();
            break;
        default:
            // Let JavaFx handle the keypress.
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
            // Process result of the command.
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // Handle command failure.
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
        // Add an empty string to represent the most-recent end of historySnapshot, to be shown to
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
