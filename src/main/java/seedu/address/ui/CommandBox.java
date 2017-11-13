package seedu.address.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandList;
import seedu.address.logic.commands.CommandResult;
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
    private final SortedSet<String> entries = new TreeSet<>(new CommandList().getCommandList());

    @FXML
    private TextField commandTextField;

    @FXML
    private ContextMenu commandContextMenu;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            //@@author heiseish
            String enteredText = commandTextField.getText();
            //always hide suggestion if nothing has been entered (only "spacebars"
            // are dissalowed in TextFieldWithLengthLimit)
            if (enteredText == null || enteredText.isEmpty()) {
                commandContextMenu.hide();
            } else {
                //filter all possible suggestions depends on "Text", case insensitive
                List<String> filteredEntries = entries.stream()
                        .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                        .collect(Collectors.toList());
                //some suggestions are found
                if (!filteredEntries.isEmpty()) {
                    //build popup - list of "CustomMenuItem"
                    // check if the context menu contains only 1 recommend and its the same as command text input
                    if (filteredEntries.get(0).equalsIgnoreCase(enteredText)) {
                        commandContextMenu.hide();
                    } else {
                        populatePopup(filteredEntries, enteredText);
                        if (!commandContextMenu.isShowing()) { //optional
                            commandContextMenu.show(commandTextField, Side.BOTTOM, 0, 0); //position of popup
                        }
                    }
                    //no suggestions -> hide
                } else {
                    commandContextMenu.hide();
                }
            }
            //@@author
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

        case TAB:
            keyEvent.consume();
            fillInRecommendedCommand();
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
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), true));

            alertUser(e);
        }
    }

    //@@author hthjthtrh
    /**
     * Opens an alert dialogue to inform user of the error
     * @param e exception due to parsing / execution
     */
    private void alertUser(Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        FxViewUtil.setStageIcon(alertStage, "/images/Warning-300px.png");

        if (e.getClass().equals(CommandException.class)) {
            alert.setHeaderText(((CommandException) e).getExceptionHeader());
        } else {
            alert.setHeaderText(((ParseException) e).getExceptionHeader());
        }

        TextArea txtArea = new TextArea(e.getMessage());
        txtArea.setEditable(false);
        txtArea.setWrapText(true);

        txtArea.setMaxWidth(Double.MAX_VALUE);
        txtArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(txtArea, Priority.ALWAYS);
        GridPane.setHgrow(txtArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(txtArea, 0, 0);

        alert.getDialogPane().setContent(expContent);

        alert.showAndWait();

    }
    //@@author

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

    //@@author heiseish
    /**
     * Fill up the command word from the characters
     * if there exists a command word with the same starting chars
     */
    private void fillInRecommendedCommand() {
        ArrayList<String> commandListString = new CommandList().getCommandList();
        for (String command : commandListString) {
            if (command.startsWith(commandTextField.getText())) {
                replaceText(command);
                return;
            }
        }
    }

    /**
     * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult, String searchRequest) {
        //List of "suggestions"
        List<CustomMenuItem> menuItems = new LinkedList<>();
        //List size - 10 or founded suggestions count
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        //Build list as set of labels
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            //label with graphic (text flow) to highlight founded subtext in suggestions
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, searchRequest));
            entryLabel.setPrefHeight(10);  //don't sure why it's changed with "graphic"
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            //if any suggestion is select set it into text and close popup
            item.setOnAction(actionEvent -> {
                item.setText(result);
                commandTextField.positionCaret(result.length());
                commandContextMenu.hide();
            });
        }

        //"Refresh" context menu
        commandContextMenu.getItems().clear();
        commandContextMenu.getItems().addAll(menuItems);
    }


    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() {
        return entries;
    }

    /**
     * Build TextFlow with selected text. Return "case" dependent.
     *
     * @param text - string with text
     * @param filter - string to select in text
     * @return - TextFlow
     */
    public static TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,
                filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12.00));
        return new TextFlow(textBefore, textFilter, textAfter);
    }
    //@@author
}
