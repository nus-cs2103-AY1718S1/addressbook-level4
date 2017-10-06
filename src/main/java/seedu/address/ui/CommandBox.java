package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
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

    private ArrayList <String> commandKeywords;

    @FXML
    private TextField commandTextField;

    @FXML
    private TextField commandTextFieldKeyword;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        configInactiveKeyword();
        configCommandKeywords();
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
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Handles the key released event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        default:
            listenCommandInputChanged();
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    private void listenCommandInputChanged() {
        String allTextInput = commandTextField.getText();
        String commandKeyword = allTextInput.split(" ")[0];
        if (validCommandKeyword(commandKeyword)) {
            configActiveKeyword(commandKeyword);
        } else {
            configInactiveKeyword();
        }
    }

    /**
     * Check if keyword is a valid command keyword
     * @param keyWord
     * @return
     */
    private boolean validCommandKeyword(String keyWord) {
        for (int i = 0; i < commandKeywords.size(); i++) {
            String commandKeyword = commandKeywords.get(i);
            if (Pattern.matches(commandKeyword, keyWord)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Configure words that are not command keyword
     */
    private void configInactiveKeyword() {
        commandTextFieldKeyword.setVisible(false);
        commandTextFieldKeyword.toBack();
        commandTextFieldKeyword.clear();
        commandTextFieldKeyword.setStyle("    -fx-background-color: transparent #383838 transparent #383838;\n"
                + "    -fx-background-insets: 0;\n"
                + "    -fx-border-color: #383838 #383838 #ffffff #383838;\n"
                + "    -fx-border-insets: 0;\n"
                + "    -fx-border-width: 1;\n"
                + "    -fx-font-family: \"Segoe UI Light\";\n"
                + "    -fx-font-size: 13pt;\n"
                + "    -fx-text-fill: white;");
    }


    /**
     * Configure command keyword when appeared on Command Box
     * @param commandKeyword
     */
    private void configActiveKeyword(String commandKeyword) {
        commandTextFieldKeyword.setVisible(true);
        Text commandText = new Text(commandKeyword);
        commandText.setFont(commandTextField.getFont());
        final double width = commandText.getLayoutBounds().getWidth() + 17;
        commandTextFieldKeyword.setText(commandKeyword);
        commandTextFieldKeyword.setStyle(" -fx-control-inner-background: yellow;\n"
                + "    -fx-font-size: 12pt;\n"
                + "    -fx-text-fill: red;");
        commandTextFieldKeyword.setPrefWidth(width);
        commandTextFieldKeyword.toFront();
    }

    /**
     * Configure command keywords
     */
    private void configCommandKeywords() {
        commandKeywords = new ArrayList<String>();
        commandKeywords.add(AddCommand.COMMAND_WORD);
        commandKeywords.add(DeleteCommand.COMMAND_WORD);
        commandKeywords.add(EditCommand.COMMAND_WORD);
        commandKeywords.add(ExitCommand.COMMAND_WORD);
        commandKeywords.add(FindCommand.COMMAND_WORD);
        commandKeywords.add(HelpCommand.COMMAND_WORD);
        commandKeywords.add(ListCommand.COMMAND_WORD);
        commandKeywords.add(SelectCommand.COMMAND_WORD);
        commandKeywords.add(ClearCommand.COMMAND_WORD);
        commandKeywords.add(UndoCommand.COMMAND_WORD);
        commandKeywords.add(RedoCommand.COMMAND_WORD);
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

}
