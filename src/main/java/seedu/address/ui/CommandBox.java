package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CommandInputChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.TextUtil;
import seedu.address.logic.Audio;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.HintParser;
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
    @FXML
    private StackPane commandBox;
    @FXML
    private HBox commandBoxItems;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;

        //@@author nicholaschuayunzhi
        commandTextField.textProperty().addListener((ob, o, n) -> {
            // expand the textfield
            double width = TextUtil.computeTextWidth(commandTextField.getFont(),
                    commandTextField.getText(), 0.0D) + 2;
            double halfWindowWidth = (MainWindow.getInstance() == null)
                    ? 250 : MainWindow.getInstance().getRoot().getWidth() / 2;
            width = (width < 1) ? 1 : width;
            width = (width > halfWindowWidth) ? halfWindowWidth : width;
            commandTextField.setPrefWidth(width);
            commandTextField.setAlignment(Pos.CENTER_RIGHT);
            //@@author hanselblack
            //Plays typing Sound
            new Audio("audio/typing.mp3").playSound();
            //@@author
        });
        //@@author nicholaschuayunzhi
        commandBoxItems.setOnMouseClicked((event) -> {
            commandTextField.requestFocus();
            commandTextField.positionCaret(commandTextField.getText().length());
        });

        CommandBoxHints commandBoxHints = new CommandBoxHints(commandTextField);
        commandBoxItems.getChildren().add(commandBoxHints.getRoot());
        commandTextField.prefColumnCountProperty().bind(commandTextField.textProperty().length());

        //@@author goweiwen
        // changes command box style to match validity of the input whenever there is a change
        // to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldInput, newInput) ->
                setStyleByValidityOfInput(newInput));
        //@@author nicholaschuayunzhi

        // posts a CommandInputChangedEvent whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldInput, newInput) ->
                EventsCenter.getInstance().post(new CommandInputChangedEvent(newInput)));
        historySnapshot = logic.getHistorySnapshot();

        //@@author
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
        //@@author goweiwen
        case TAB:
            keyEvent.consume();
            autocomplete();
            break;
        //@@author
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

    //@@author goweiwen
    /**
     * Automatically completes user's input and replaces it in the command box.
     */
    private void autocomplete() {
        String input = commandTextField.getText();
        String autocompletion = HintParser.autocomplete(input);
        commandTextField.textProperty().set(autocompletion);
        commandTextField.positionCaret(autocompletion.length());
    }

    /**
     * Sets the command box style to match validity of the input. (valid -> default, invalid -> failed)
     */
    private void setStyleByValidityOfInput(String input) {
        if (input.equals("")) {
            return;
        }

        try {
            logic.parse(input);
        } catch (ParseException e) {
            setStyleToIndicateCommandFailure();
            return;
        }
        setStyleToDefault();
    }
    //@@author

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
