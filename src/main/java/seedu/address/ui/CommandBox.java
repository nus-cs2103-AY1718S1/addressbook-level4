package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
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

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    private StackPane helperContainer;
    private CommandBoxHelper commandBoxHelper;
    private Boolean helpEnabled = false;
    private SplitPane settingsPane;

    //Animation attributes
    private Timeline timelineLeft;
    private Timeline timelineRight;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic, StackPane commandBoxHelp, SplitPane settingsPane) {
        super(FXML);
        this.logic = logic;
        this.commandBoxHelper = new CommandBoxHelper(logic);
        this.helperContainer = commandBoxHelp;
        this.settingsPane = settingsPane;
        setAnimation();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();

            /** Shows helper if there is text in the command field that corresponds to the command list*/
            if (commandBoxHelper.listHelp(commandTextField) && !helpEnabled) {
                showHelper();
            } else if (!commandBoxHelper.listHelp(commandTextField)) {
                hideHelper();
            }

            /** Shows settings screen if there is text in the command field that corresponds to settings*/
            if (checkForSettingsPanelPopup(commandTextField)) {
                timelineLeft.play();
            } else {
                timelineRight.play();
            }
        });
        commandTextField.setStyle("-fx-font-style: italic;" + " -fx-text-fill: lime");
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            //Check if CLI has any text within to trigger commandboxhelper first
            if (!commandBoxHelper.checkEmpty()) {
                commandBoxHelper.selectUpHelperBox();
            } else {
                // As up and down buttons will alter the position of the caret,
                // consuming it causes the caret's position to remain unchanged
                keyEvent.consume();
                navigateToPreviousInput();
            }
            break;
        case DOWN:
            //Check if CLI has any text within to trigger commandboxhelper first
            if (!commandBoxHelper.checkEmpty()) {
                commandBoxHelper.selectDownHelperBox();
            } else {
                keyEvent.consume();
                navigateToNextInput();
            }
            break;
        case TAB:
            if (helperContainer.getChildren().contains(commandBoxHelper.getRoot())
                    && commandBoxHelper.isMainSelected()) {
                try {
                    commandTextField.setText(commandBoxHelper.getHelperText());
                    commandTextField.requestFocus();
                    commandTextField.end();
                    hideHelper();
                } catch (Exception e) {
                    logger.info(e.getMessage() + "Nothing selected in command helper");
                }
            }
            keyEvent.consume();
            break;
        case BACK_SPACE:
            if (commandTextField.getText().trim().length() <= 0 || !commandBoxHelper.listHelp(commandTextField)) {
                hideHelper();
                logger.info("Hiding command helper");
            }
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
            if (helperContainer.getChildren().contains(commandBoxHelper.getRoot())
                    && commandBoxHelper.isMainSelected()) {
                commandTextField.setText(commandBoxHelper.getHelperText());
                commandTextField.requestFocus();
                commandTextField.end();
                hideHelper();
            } else {
                hideHelper();
                CommandResult commandResult = logic.execute(commandTextField.getText());
                initHistory();
                historySnapshot.next();
                // process result of the command
                commandTextField.setText("");
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser, false));
            }

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage(), true));
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

    /**
     * Shows the command helper
     */
    private void showHelper() {
        helperContainer.getChildren().add(commandBoxHelper.getRoot());
        helpEnabled = true;
    }

    /**
     * Hides the command helper
     */
    private void hideHelper() {
        helperContainer.getChildren().remove(commandBoxHelper.getRoot());
        helpEnabled = false;
    }

    /**
     * Check whether to display the settings panel
     */
    private boolean checkForSettingsPanelPopup(TextField commandTextField) {
        if (commandTextField.getText().contains("choose") || commandTextField.getText().contains("pref")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the animation sequence for entering left and right on the settings panel
     */
    private void setAnimation() {
        final Timeline timelineBounce = new Timeline();
        timelineBounce.setCycleCount(1);
        timelineBounce.setAutoReverse(true);
        KeyValue kv1 = new KeyValue(settingsPane.translateXProperty(), 0);
        KeyValue kv2 = new KeyValue(settingsPane.translateXProperty(), -10);
        KeyValue kv3 = new KeyValue(settingsPane.translateXProperty(), 0);
        KeyFrame kf1 = new KeyFrame(Duration.millis(200), kv1, kv2, kv3);
        timelineBounce.getKeyFrames().add(kf1);

        /* Event handler to call bouncing effect after the scroll to left is finished. */
        javafx.event.EventHandler<ActionEvent> onFinished = new javafx.event.EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timelineBounce.play();
            }
        };

        timelineLeft = new Timeline();
        timelineRight = new Timeline();

        timelineLeft.setCycleCount(1);
        timelineLeft.setAutoReverse(true);
        KeyValue kvLeft1 = new KeyValue(settingsPane.translateXProperty(), -10);
        KeyFrame kfLeft = new KeyFrame(Duration.millis(200), onFinished, kvLeft1);
        timelineLeft.getKeyFrames().add(kfLeft);

        timelineRight.setCycleCount(1);
        timelineRight.setAutoReverse(true);
        KeyValue kvRight1 = new KeyValue(settingsPane.translateXProperty(), 300);
        KeyFrame kfRight = new KeyFrame(Duration.millis(200), kvRight1);
        timelineRight.getKeyFrames().add(kfRight);
    }
}
