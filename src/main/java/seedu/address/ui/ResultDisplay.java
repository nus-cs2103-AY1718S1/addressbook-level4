package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.Commands;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private static final String[] allCommandWords = Commands.getAllCommandWords();
    private static final HashMap<String, String> allCommandUsages = Commands.getAllCommandUsages();

    private static final String CASE_INSENSITIVE_AND_WORD_START_REGEX = "(?i)^";
    private static final String OPTIONAL_ALPHANUMERIC_CHARACTERS_REGEX = "\\w*";

    private final StringProperty displayed = new SimpleStringProperty("");

    private String lastFoundCommand = "";
    private TextField linkedCommandInput;
    private String topSuggestion = "";

    @FXML
    private TextArea resultDisplay;

    @FXML
    private TextArea infoDisplay;

    //@@author joanneong
    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        resultDisplay.setWrapText(true);
        infoDisplay.setWrapText(true);
        registerAsAnEventHandler(this);
    }

    /**
     * Gets the current top suggestion for custom auto-completion.
     */
    protected String getCurrentTopSuggestion() {
        return this.topSuggestion;
    }

    void setLinkedInput(CommandBox commandBox) {
        linkedCommandInput = commandBox.getCommandTextField();
        linkedCommandInput.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAutoCompleteDisplay(newValue.trim());
            updateInfoDisplay(oldValue, newValue);
        });
    }

    /**
     * Updates the auto complete display according to the user input in the command box.
     *
     * Only command words that contain the input and are shorter than the input length will
     * be considered as valid auto complete options. Also, given an input of length n, the first n
     * characters of the command must match the input (case-insensitive) for it to be a valid suggestion.
     *
     * Furthermore, if the input has a spacing, then there will not be any suggestions displayed
     * since all command words are implemented as single words.
     *
     * The first valid suggestion is also stored for custom auto complete purposes.
     */
    private void updateAutoCompleteDisplay(String currentInput) {
        ArrayList<String> matchingSuggestions = new ArrayList<>();

        if (currentInput.contains(" ")) {
            displayed.setValue("");
            topSuggestion = "";
            return;
        }

        for (String commandWord: allCommandWords) {
            if (currentInput.length() == 0
                    || (currentInput.length() < commandWord.length()
                        && commandWord.matches(CASE_INSENSITIVE_AND_WORD_START_REGEX
                            + currentInput + OPTIONAL_ALPHANUMERIC_CHARACTERS_REGEX))) {
                matchingSuggestions.add(commandWord);
            }
        }
        if (!matchingSuggestions.isEmpty()) {
            topSuggestion = matchingSuggestions.get(0);
        }

        StringBuilder toDisplay = new StringBuilder();
        for (String option: matchingSuggestions) {
            toDisplay.append(option + "\n");
        }

        displayed.setValue(toDisplay.toString());
    }

    /**
     * Updates the information display according to the user input in the command box.
     */
    private void updateInfoDisplay(String oldInput, String newInput) {
        if (lastFoundCommand.isEmpty()
            || (newInput.length() < oldInput.length() && !newInput.contains(lastFoundCommand))
            || (newInput.length() > oldInput.length()) && allCommandUsages.containsKey(newInput)) {
            infoDisplay.setText("");
            lastFoundCommand = "";
            if (allCommandUsages.containsKey(newInput)) {
                infoDisplay.setText("How to use:\n" + allCommandUsages.get(newInput));
                lastFoundCommand = newInput;
            }
        }
    }

    //@@author
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
