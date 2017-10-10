package seedu.address.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String TAG_PREFIX = "prefix";

    private static final int NAME = 0;
    private static final int EMAIL = 1;
    private static final int PHONE = 2;
    private static final int ADDRESS = 3;
    private static final int TAG = 4;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    private HashMap<String, String> keywordColorMap;
    private ArrayList<String> prefixList;

    @FXML
    private TextField commandTextField;

    @FXML
    private TextField commandTextFieldKeyword;

    @FXML
    private Text commandText;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label keywordLabel;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        configInactiveKeyword();
        configPrefixList();
        keywordColorMap = logic.getCommandKeywordColorMap();
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * This method create a list of prefix used in the command
     */
    private void configPrefixList() {
        prefixList = new ArrayList<>();
        prefixList.add(CliSyntax.PREFIX_NAME.getPrefix());
        prefixList.add(CliSyntax.PREFIX_EMAIL.getPrefix());
        prefixList.add(CliSyntax.PREFIX_PHONE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_ADDRESS.getPrefix());
        prefixList.add(CliSyntax.PREFIX_TAG.getPrefix());
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
        String[] inputArray = allTextInput.split(" ");
        int index = 0;

        configInActiveTag();
        configInactiveKeyword();

        for (int i = 0; i < inputArray.length; i++) {
            String text = inputArray[i];

            //Command Keyword
            if (validCommandKeyword(text)) {
                index = allTextInput.indexOf(text);
                configActiveKeyword(index, text);
            }

            //Name
            if (text.contains(prefixList.get(NAME))) {
                index = allTextInput.indexOf(prefixList.get(NAME));
                configActiveTag(index, prefixList.get(NAME));
            }

            //Email
            if (text.contains(prefixList.get(EMAIL))) {
                index = allTextInput.indexOf(prefixList.get(EMAIL));
                configActiveTag(index, prefixList.get(EMAIL));
            }

            //Phone
            if (text.contains(prefixList.get(PHONE))) {
                index = allTextInput.indexOf(prefixList.get(PHONE));
                configActiveTag(index, prefixList.get(PHONE));
            }

            //Address
            if (text.contains(prefixList.get(ADDRESS))) {
                index = allTextInput.indexOf(prefixList.get(ADDRESS));
                configActiveTag(index, prefixList.get(ADDRESS));
            }

            //Tag
            if (text.contains(prefixList.get(TAG))) {
                ArrayList<Integer> tagList = getTagIndexList(allTextInput);
                for (int j = 0; j < tagList.size(); j++) {
                    index = tagList.get(j);
                    configActiveTag(index, index + prefixList.get(TAG));
                }

            }
        }
    }

    private ArrayList<Integer> getTagIndexList(String allTextInput) {
        ArrayList<Integer> tagList = new ArrayList<>();
        int index = 0;
        while (index < allTextInput.length()) {
            int newIndex = allTextInput.indexOf(prefixList.get(TAG), index);
            if (newIndex == -1) {
                break;
            }
            tagList.add(newIndex);
            index = newIndex + 1;
        }
        return tagList;
    }


    /**
     * Check if keyword is a valid command keyword
     * @param keyWord
     * @return
     */
    private boolean validCommandKeyword(String keyWord) {
        if (keywordColorMap.containsKey(keyWord)) {
            return true;
        }
        return false;
    }


    /**
     * Configure words that are not command keyword
     */
    private void configInactiveKeyword() {
        keywordLabel.setVisible(false);
        keywordLabel.toBack();
        commandTextField.toFront();
    }


    /**
     * Configure command keyword when appeared on Command Box
     * @param commandKeyword
     */
    private void configActiveKeyword(int index, String commandKeyword) {
        String allTextInput = commandTextField.getText();
        String inputText = allTextInput.substring(0, index);
        double margin = computeMargin(inputText);
        keywordLabel.setId("keywordLabel");
        keywordLabel.setText(commandKeyword);
        keywordLabel.setVisible(true);
        keywordLabel.getStyleClass().add("keyword-label");
        Insets leftInset = new Insets(0, 0, 0, margin + 13);
        stackPane.setAlignment(keywordLabel, Pos.CENTER_LEFT);
        stackPane.setMargin(keywordLabel, leftInset);

        String color = keywordColorMap.get(commandKeyword);
        keywordLabel.setStyle("-fx-background-color: " + color + ";\n");
        keywordLabel.toFront();
    }

    /**
     * Configure tag that appear in the text field
     */
    private void configActiveTag(int index, String tag) {
        String allTextInput = commandTextField.getText();
        String inputText = allTextInput.substring(0, index);
        double margin = computeMargin(inputText);
        Insets xInset = new Insets(0, 0, 0, margin + 13);

        String tagName = tag.replaceAll("[0-9]", "");
        Label tagLabel = new Label(tagName);
        tagLabel.setId(TAG_PREFIX + tag);
        tagLabel.getStyleClass().add("keyword-label");
        stackPane.getChildren().add(tagLabel);
        stackPane.setAlignment(tagLabel, Pos.CENTER_LEFT);
        stackPane.setMargin(tagLabel, xInset);

        tagLabel.setStyle("-fx-background-color:yellow;\n "
                + "-fx-text-fill: red; ");
        tagLabel.setVisible(true);
        tagLabel.toFront();
    }


    /**
     * This method only remove all tag label in stack pane
     */
    private void configInActiveTag() {
        ObservableList<Node> list = stackPane.getChildren();
        final List<Node> removalCandidates = new ArrayList<>();

        Iterator<Node> iter = list.iterator();
        while (iter.hasNext()) {
            Node node = iter.next();
            if (node.getId().contains(TAG_PREFIX)) {
                node.setVisible(false);
                removalCandidates.add(node);
            }
        }
        stackPane.getChildren().removeAll(removalCandidates);
    }


    private double computeMargin(String str) {
        Text text = new Text(str);
        text.setFont(commandText.getFont());
        return text.getLayoutBounds().getWidth();
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
