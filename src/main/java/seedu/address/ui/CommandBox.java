package seedu.address.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.AudioUtil;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListTaskCommand;
import seedu.address.logic.commands.MultiFilterCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private final TextArea taskDisplayed;
    private final TabPane tabPane;
    private final AudioUtil audio = new AudioUtil();
    private ListElementPointer historySnapshot;



    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) throws FileNotFoundException {
        this(logic, null, null);
    }

    public CommandBox(Logic logic, TextArea taskDisplayed, TabPane tabPane) throws FileNotFoundException {
        super(FXML);
        this.logic = logic;
        this.taskDisplayed = taskDisplayed;
        this.tabPane = tabPane;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
    }
    //@@author chairz
    /**
     * Change the tab based on command input
     */
    private void displayTab(String commandTyped) {
        Map<String, String> commandFormatMap = Command.getMapOfCommandFormats();
        List listOfAliases = Command.getListOfAvailableCommandAliases();
        int index = tabPane.getSelectionModel().getSelectedIndex();
        if (commandFormatMap.containsKey(commandTyped) || listOfAliases.contains(commandTyped)) {
            if (index != 0) {
                tabPane.getSelectionModel().selectFirst();
            }
        } else {
            tabPane.getSelectionModel().selectLast();
        }

    }


    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        //@@author JYL123
        // Handles cases where multiple keys are pressed simultaneously
        String getAlphabetPressed = keyEvent.getCode().toString();
        logger.info(getAlphabetPressed);

        if (keyEvent.getCode().isLetterKey() && keyEvent.isControlDown()) {
            //keyboard shortcut for input text heavy command
            switch (getAlphabetPressed) {
            case "A":
                keyEvent.consume();
                commandTextField.setText(AddCommand.COMMAND_WORD);
                break;
            case "D":
                keyEvent.consume();
                commandTextField.setText(DeleteCommand.COMMAND_WORD);
                break;
            case "M":
                keyEvent.consume();
                commandTextField.setText(MultiFilterCommand.COMMAND_WORD);
                break;
            case "C":
                keyEvent.consume();
                commandTextField.setText(ClearCommand.COMMAND_WORD);
                break;
            case "S":
                keyEvent.consume();
                commandTextField.setText(SearchCommand.COMMAND_WORD);
                break;
            case "E":
                keyEvent.consume();
                commandTextField.setText(EditCommand.COMMAND_WORD);
                break;
            case "F":
                keyEvent.consume();
                commandTextField.setText(FindCommand.COMMAND_WORD);
                break;
            default:
                //do nothing
            }
        }

        if (keyEvent.isControlDown()) {
            keyEvent.consume();
            launchExtendedAutocomplete();
            commandTextField.requestFocus();
            commandTextField.end();
            return;
        }

        // Handles single key press
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
            launchAutoComplete();
            commandTextField.requestFocus(); // focus the caret in the command box after autocomplete
            commandTextField.end(); // move caret to the end of the completed command
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    //@@author lancehaoh
    /**
     * Launches extended auto complete mechanism in logic
     * when the special key is pressed in the Command Box
     * Extended auto complete fills the command box with the full format of
     * a command rather than just the command word
     */
    private void launchExtendedAutocomplete() {
        String userCommand = commandTextField.getText().split("\\s+")[0];
        Map<String, String> commandFormatMap = Command.getMapOfCommandFormats();
        Map<String, String> commandTaskFormatMap = Command.getMapofTaskCommandFormats();
        Map<String, String> commandHelpMap = Command.getMapOfCommandHelp();
        if (commandFormatMap.containsKey(userCommand) || commandTaskFormatMap.containsKey(userCommand)) {
            if (commandFormatMap.containsKey(userCommand)) {
                commandTextField.setText(commandFormatMap.get(userCommand));
            } else {
                commandTextField.setText(commandTaskFormatMap.get(userCommand));
            }
            raise(new NewResultAvailableEvent(commandHelpMap.get(userCommand)));
        }
    }

    //@@author lancehaoh
    /**
     * Launches auto complete mechanism in logic
     * when the auto complete hotkey is pressed in the Command Box
     */
    private void launchAutoComplete() {
        List<String> listOfPossibleCommandWords = logic.getPossibleCommands(commandTextField.getText());
        final int numberOfCommandsOnOneLine = 6;
        int count = 0;
        StringBuffer feedbackToUser = new StringBuffer();

        /*
         * Append all possible command words into a single formatted string
         */
        for (String commandWord : listOfPossibleCommandWords) {
            // format the command so that it will be aligned when displayed to user
            String s = String.format("%-20s", commandWord);
            feedbackToUser.append(s);
            count++;
            if (count == numberOfCommandsOnOneLine) {
                count = 0;
                feedbackToUser.append("\n");
            }
        }

        logger.info(listOfPossibleCommandWords.toString());

        if (listOfPossibleCommandWords.size() == 1) {
            /*
             * there is only one possible command for the given prefix
             * directly complete the command in the command box
             */
            commandTextField.setText(listOfPossibleCommandWords.get(0));
            raise(new NewResultAvailableEvent(""));
        } else {
            // Display the list of command words to the user
            if (feedbackToUser.length() > 0) {
                raise(new NewResultAvailableEvent(feedbackToUser.toString()));
            } else {
                // Clear suggestions if there were any when the user types a wrong prefix
                raise(new NewResultAvailableEvent(""));
            }
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

    //@@author lancehaoh
    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            // Parser for checking if the user input command is related to tasks rather than persons
            AddressBookParser parser = new AddressBookParser();
            String userInput = commandTextField.getText();

            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");

            //@@author zhangshuoyang
            if (parser.parseCommand(userInput) instanceof AddTaskCommand) {
                // Process and display the most recently added task in a separate text field
                StringBuffer lastTaskFieldOutput = new StringBuffer();
                List<ReadOnlyTask> listOfTask = logic.getFilteredTaskList();
                lastTaskFieldOutput.append("\n");
                lastTaskFieldOutput.append(listOfTask.get(listOfTask.size() - 1).toString());
                lastTaskFieldOutput.append("\n");
                PrintWriter out = new PrintWriter("taskAdded.txt");
                out.println(lastTaskFieldOutput.toString());
                out.close();

                try {
                    String curr = System.getProperty("user.dir");
                    Scanner s = new Scanner(new File(curr + "/taskAdded.txt"));

                    taskDisplayed.clear();
                    taskDisplayed.appendText("===Last Task Added=== " + "\n");
                    while (s.hasNext()) {
                        String temp = s.next();
                        if (("Description:").equals(temp)
                                || ("Priority:").equals(temp)
                                || ("DueDate:").equals(temp)) {
                            taskDisplayed.appendText("\n");
                        }
                        taskDisplayed.appendText(temp + " ");
                    }
                } catch (FileNotFoundException fne) {
                    throw new ParseException(fne.getMessage(), fne);
                }
            }

            //@@author lancehaoh
            Command currentCommand = parser.parseCommand(userInput);

            if (currentCommand instanceof ListTaskCommand || currentCommand instanceof DeleteTaskCommand) {
                // Process and display tasks in a separate text field
                StringBuffer taskFieldOutput = new StringBuffer();

                List<ReadOnlyTask> listOfTask = logic.getFilteredTaskList();

                for (int i = 0; i < listOfTask.size(); i++) {
                    taskFieldOutput.append("Task no. " + (i + 1) + "\n");
                    taskFieldOutput.append(listOfTask.get(i).toString());
                    taskFieldOutput.append("\n");
                }
                taskDisplayed.setText(taskFieldOutput.toString());
            }
            displayTab(userInput.split("\\s+")[0]);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

            // Play success sound
            audio.playClip("command-success.wav");
            logger.info("Result: " + commandResult.feedbackToUser);
        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));

            // Play failure sound
            try {
                audio.playClip("command-failure.wav");
            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException err) {
                err.printStackTrace();
            }
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
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
