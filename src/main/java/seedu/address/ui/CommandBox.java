package seedu.address.ui;

import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_LARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_NORMAL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_SMALL;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XLARGE;
import static seedu.address.logic.commands.CustomiseCommand.FONT_SIZE_XSMALL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.ColorKeywordEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ColorKeywordCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CustomiseCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final String TAG_PREFIX = "prefix";

    private static final int CODE = 0;
    private static final int CLASSTYPE = 1;
    private static final int VENUE = 2;
    private static final int GROUP = 3;
    private static final int TIMESLOT = 4;
    private static final int LECTURER = 5;
    private static final int FONT_SIZE = 6;

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    private final AddressBookParser tester;

    private HashMap<String, String> keywordColorMap;
    private ArrayList<String> prefixList;
    private int fontIndex = 0;
    private boolean enableHighlight = false;
    private String userPrefFontSize = "-fx-font-size: medium;";

    private final ImageView tick = new ImageView("/images/tick.png");
    private final ImageView cross = new ImageView("/images/cross.png");


    @FXML
    private TextField commandTextField;

    @FXML
    private Text commandTextDefault;

    @FXML
    private Text commandTextXsmall;

    @FXML
    private Text commandTextSmall;

    @FXML
    private Text commandTextLarge;

    @FXML
    private Text commandTextXLarge;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label keywordLabel;

    @FXML
    private Label checkBox;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        configInactiveKeyword();
        configPrefixList();
        keywordLabel.getStyleClass().add("keyword-label-default");
        keywordColorMap = logic.getCommandKeywordColorMap();
        keywordColorMap = getCommandKeywordColorMap();
        String[] commands = {"help", "add", "list", "edit", "find",
            "delete", "select", "history", "undo", "redo", "clear", "exit", "customise", "view", "swt"};
        TextFields.bindAutoCompletion(commandTextField, commands);
        tick.setFitHeight(30);
        tick.setFitWidth(30);
        cross.setFitHeight(25);
        cross.setFitWidth(25);
        historySnapshot = logic.getHistorySnapshot();
        tester = new AddressBookParser();
        registerAsAnEventHandler(this);
    }

    //@@author caoliangnus
    /**
     * This method create a list of prefix used in the command
     */
    private void configPrefixList() {
        prefixList = new ArrayList<>();
        prefixList.add(CliSyntax.PREFIX_MODULE_CODE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_CLASS_TYPE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_VENUE.getPrefix());
        prefixList.add(CliSyntax.PREFIX_GROUP.getPrefix());
        prefixList.add(CliSyntax.PREFIX_TIME_SLOT.getPrefix());
        prefixList.add(CliSyntax.PREFIX_LECTURER.getPrefix());
        prefixList.add(CliSyntax.PREFIX_FONT_SIZE.getPrefix());
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
            // let JavaFx handle the keypress
        }
    }

    //@@author caoliangnus
    /**
     * Handles the key released event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        default:
            listenCommandInputChanged();
            break;
        }
    }


    /**
     * Handles the Command input changed event.
     */
    private void listenCommandInputChanged() {
        if (enableHighlight) {
            String allTextInput = commandTextField.getText();
            String[] inputArray = allTextInput.split(" ");
            int index = 0;

            configInActiveTag();
            configInactiveKeyword();

            configBorderColor(allTextInput);

            for (int i = 0; i < inputArray.length; i++) {
                String text = inputArray[i];

                //Command Keyword
                if (i == 0 && validCommandKeyword(text)) {
                    configActiveKeyword(text);
                }

                //Code
                if (text.contains(prefixList.get(CODE))) {
                    index = allTextInput.indexOf(prefixList.get(CODE));
                    configActiveTag(index, prefixList.get(CODE));
                }

                //Class type
                if (text.contains(prefixList.get(CLASSTYPE))) {
                    index = allTextInput.indexOf(prefixList.get(CLASSTYPE));
                    configActiveTag(index, prefixList.get(CLASSTYPE));
                }

                //Venue
                if (text.contains(prefixList.get(VENUE))) {
                    index = allTextInput.indexOf(prefixList.get(VENUE));
                    configActiveTag(index, prefixList.get(VENUE));
                }

                //Group
                if (text.contains(prefixList.get(GROUP))) {
                    index = allTextInput.indexOf(prefixList.get(GROUP));
                    configActiveTag(index, prefixList.get(GROUP));
                }

                //Time slot
                if (text.contains(prefixList.get(TIMESLOT))) {
                    index = allTextInput.indexOf(prefixList.get(TIMESLOT));
                    configActiveTag(index, prefixList.get(TIMESLOT));
                }

                //Lecturer
                if (text.contains(prefixList.get(LECTURER))) {
                    ArrayList<Integer> tagList = getTagIndexList(allTextInput);
                    for (int j = 0; j < tagList.size(); j++) {
                        index = tagList.get(j);
                        configActiveTag(index, index + prefixList.get(LECTURER));
                    }
                }

                //font size
                if (text.contains(prefixList.get(FONT_SIZE))) {
                    index = allTextInput.indexOf(prefixList.get(FONT_SIZE));
                    configActiveTag(index, prefixList.get(FONT_SIZE));
                }
            }
        } else {
            commandTextField.setStyle(userPrefFontSize);
            checkBox.setVisible(false);
        }

    }


    private ArrayList<Integer> getTagIndexList(String allTextInput) {
        ArrayList<Integer> tagList = new ArrayList<>();
        int index = 0;
        while (index < allTextInput.length()) {
            int newIndex = allTextInput.indexOf(prefixList.get(LECTURER), index);
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
        return keywordColorMap.containsKey(keyWord);
    }


    /**
     * Configure words that are not command keyword
     */
    private void configInactiveKeyword() {
        keywordLabel.setVisible(false);
        keywordLabel.toBack();
        commandTextField.toFront();
    }

    //@@author junming403
    /**
     * Configure border colour to indicate validity of user input.
     */
    private void configBorderColor(String allTextInput) {
        checkBox.setVisible(true);
        try {
            tester.parseCommand(allTextInput);
            commandTextField.setStyle(userPrefFontSize + "-fx-border-color: green; -fx-border-width: 2");
            checkBox.setGraphic(tick);
            checkBox.toFront();
            checkBox.setVisible(true);
        } catch (ParseException e) {
            commandTextField.setStyle(userPrefFontSize + "-fx-border-color: red; -fx-border-width: 2");
            checkBox.setGraphic(cross);
            checkBox.toFront();
            checkBox.setVisible(true);
        }
    }
    //@@author


    //@@author caoliangnus
    /**
     * Configure command keyword when appeared on Command Box
     * @param commandKeyword
     */
    private void configActiveKeyword(String commandKeyword) {
        keywordLabel.setId("keywordLabel");
        keywordLabel.setText(commandKeyword);
        keywordLabel.setVisible(true);

        keywordLabel.getStyleClass().clear();
        Insets leftInset = new Insets(0, 0, 0, 14);

        switch (fontIndex) {
        case 1:
            keywordLabel.getStyleClass().add("keyword-label-xsmall");
            leftInset = new Insets(0, 0, 0, 11);
            break;
        case 2:
            keywordLabel.getStyleClass().add("keyword-label-small");
            leftInset = new Insets(0, 0, 0, 11);
            break;
        case 3:
            keywordLabel.getStyleClass().add("keyword-label-default");
            leftInset = new Insets(0, 0, 0, 15);
            break;
        case 4:
            keywordLabel.getStyleClass().add("keyword-label-large");
            leftInset = new Insets(0, 0, 0, 11);
            break;
        case 5:
            keywordLabel.getStyleClass().add("keyword-label-xlarge");
            leftInset = new Insets(0, 0, 0, 11);
            break;
        default:
            keywordLabel.getStyleClass().add("keyword-label-default");
            break;
        }

        stackPane.setAlignment(keywordLabel, Pos.CENTER_LEFT);
        stackPane.setMargin(keywordLabel, leftInset);

        String color = keywordColorMap.get(commandKeyword);
        // keywordLabel.setStyle("-fx-background-color: " + color + ";\n"
        // + "-fx-text-fill: red;");
        keywordLabel.setStyle(("-fx-text-fill: " + color));
        keywordLabel.setOpacity(0.7);
        keywordLabel.toFront();
    }


    /**
     * Configure tag that appear in the text field
     */
    private void configActiveTag(int index, String tag) {
        String allTextInput = commandTextField.getText();
        String inputText = allTextInput.substring(0, index);

        String tagName = tag.replaceAll("[0-9]", "");
        Label tagLabel = new Label(tagName);
        tagLabel.setId(TAG_PREFIX + tag);

        tagLabel.getStyleClass().clear();
        double margin = computeMargin(0, inputText);
        Insets leftInset = new Insets(0, 0, 0, margin + 13);

        switch (fontIndex) {
        case 1:
            tagLabel.getStyleClass().add("keyword-label-xsmall");
            margin = computeMargin(1, inputText);
            leftInset = new Insets(0, 0, 0, margin + 11);
            break;
        case 2:
            tagLabel.getStyleClass().add("keyword-label-small");
            margin = computeMargin(2, inputText);
            leftInset = new Insets(0, 0, 0, margin + 11);
            break;
        case 3:
            tagLabel.getStyleClass().add("keyword-label-default");
            margin = computeMargin(3, inputText);
            leftInset = new Insets(0, 0, 0, margin + 15);
            break;
        case 4:
            tagLabel.getStyleClass().add("keyword-label-large");
            margin = computeMargin(4, inputText);
            leftInset = new Insets(0, 0, 0, margin + 11);
            break;
        case 5:
            tagLabel.getStyleClass().add("keyword-label-xlarge");
            margin = computeMargin(5, inputText);
            leftInset = new Insets(0, 0, 0, margin + 11);
            break;
        default:
            tagLabel.getStyleClass().add("keyword-label-default");
        }

        stackPane.getChildren().add(tagLabel);
        stackPane.setAlignment(tagLabel, Pos.CENTER_LEFT);
        stackPane.setMargin(tagLabel, leftInset);

        // tagLabel.setStyle("-fx-background-color:yellow;\n"
        // + "-fx-text-fill: red; ");

        tagLabel.setStyle("-fx-text-fill: yellow");
        tagLabel.setOpacity(0.7);
        tagLabel.setVisible(true);
        tagLabel.toFront();
    }

    //@@author cctdaniel
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.message);
    }
    //@@author

    //@@author caoliangnus
    @Subscribe
    private void handleColorKeywordEvent(ColorKeywordEvent event) {
        setEnableHighlight(event.isEnabled);
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


    /**
     * This method compute the margin for label
     * @param index the type of font size used in command box
     * @param str the text used to compute the width
     * @return
     */
    private double computeMargin(int index, String str) {
        Text text = new Text(str);
        text.getStyleClass().clear();
        switch (index) {
        case 1:
            text.setFont(commandTextXsmall.getFont());
            break;
        case 2:
            text.setFont(commandTextSmall.getFont());
            break;
        case 3:
            text.setFont(commandTextDefault.getFont());
            break;
        case 4:
            text.setFont(commandTextLarge.getFont());
            break;
        case 5:
            text.setFont(commandTextXLarge.getFont());
            break;
        default:
            text.setFont(commandTextDefault.getFont());
            break;

        }

        return text.getBoundsInLocal().getWidth();
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
            configInactiveKeyword();
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

    //@@author cctdaniel
    /**
     * Sets the command box style to user preferred font size.
     */
    private void setFontSize(String userPref) {
        switch (userPref) {
        case FONT_SIZE_XSMALL:
            commandTextField.setStyle("-fx-font-size: x-small;");
            userPrefFontSize = "-fx-font-size: x-small;";
            fontIndex = 1;
            break;

        case FONT_SIZE_SMALL:
            commandTextField.setStyle("-fx-font-size: small;");
            userPrefFontSize = "-fx-font-size: small;";
            fontIndex = 2;
            break;

        case FONT_SIZE_NORMAL:
            commandTextField.setStyle("-fx-font-size: medium;");
            userPrefFontSize = "-fx-font-size: medium;";
            fontIndex = 3;
            break;

        case FONT_SIZE_LARGE:
            commandTextField.setStyle("-fx-font-size: x-large;");
            userPrefFontSize = "-fx-font-size: x-large;";
            fontIndex = 4;
            break;

        case FONT_SIZE_XLARGE:
            commandTextField.setStyle("-fx-font-size: xx-large;");
            userPrefFontSize = "-fx-font-size: xx-large;";
            fontIndex = 5;
            break;

        default:
            break;
        }
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

    //@@author caoliangnus
    /**
     * Sets the command box to enable highlighting of command keywords
     */
    public void setEnableHighlight(boolean enableHighlight) {
        this.enableHighlight = enableHighlight;
    }

    //@@author caoliangnus
    public HashMap<String, String> getCommandKeywordColorMap() {
        HashMap<String, String> keywordColorMap = new HashMap<>();
        keywordColorMap.put(AddCommand.COMMAND_WORD, "red");
        keywordColorMap.put(DeleteCommand.COMMAND_WORD, "red");
        keywordColorMap.put(EditCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ExitCommand.COMMAND_WORD, "red");
        keywordColorMap.put(FindCommand.COMMAND_WORD, "red");
        keywordColorMap.put(HelpCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ListCommand.COMMAND_WORD, "red");
        keywordColorMap.put(SelectCommand.COMMAND_WORD, "red");
        keywordColorMap.put(SortCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ClearCommand.COMMAND_WORD, "red");
        keywordColorMap.put(UndoCommand.COMMAND_WORD, "red");
        keywordColorMap.put(RedoCommand.COMMAND_WORD, "red");
        keywordColorMap.put(CustomiseCommand.COMMAND_WORD, "red");
        keywordColorMap.put(HistoryCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ViewCommand.COMMAND_WORD, "red");
        keywordColorMap.put(ColorKeywordCommand.COMMAND_WORD, "red");
        return keywordColorMap;
    }

}
