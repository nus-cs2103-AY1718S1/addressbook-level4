package seedu.address.ui;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CommandBoxContentsChangedEvent;
import seedu.address.commons.events.ui.CommandBoxReplaceTextEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelHideEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelNextSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelPreviousSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelSelectionEvent;
import seedu.address.commons.events.ui.CommandPredictionPanelShowEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAllTagsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MassEmailCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SmsCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author zameschua
/**
 * Panel containing command predictions
 * It only shows when the user types something into the search box
 * And a command prediction is expected
 * Works similarly to Google's search prediction.
 */
public class CommandPredictionPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(CommandPredictionPanel.class);
    private static final String FXML = "CommandPredictionPanel.fxml";
    private static final String helpCommandWord = HelpCommand.COMMAND_WORD;
    private static final String addCommandWord = AddCommand.COMMAND_WORD;
    private static final String listCommandWord = ListCommand.COMMAND_WORD;
    private static final String listAllTagsCommandWord = ListAllTagsCommand.COMMAND_WORD;
    private static final String editCommandWord = EditCommand.COMMAND_WORD;
    private static final String findCommandWord = FindCommand.COMMAND_WORD;
    private static final String deleteCommandWord = DeleteCommand.COMMAND_WORD;
    private static final String selectCommandWord = SelectCommand.COMMAND_WORD;
    private static final String historyCommandWord = HistoryCommand.COMMAND_WORD;
    private static final String calendarCommandWord = CalendarCommand.COMMAND_WORD;
    private static final String addEventCommandWord = AddEventCommand.COMMAND_WORD;
    private static final String massEmailCommandWord = MassEmailCommand.COMMAND_WORD;
    private static final String smsCommandWord = SmsCommand.COMMAND_WORD;
    private static final String undoCommandWord = UndoCommand.COMMAND_WORD;
    private static final String redoCommandWord = RedoCommand.COMMAND_WORD;
    private static final String clearCommandWord = ClearCommand.COMMAND_WORD;
    private static final String exitCommandWord = ExitCommand.COMMAND_WORD;

    private static final ArrayList<String> COMMAND_PREDICTION_RESULTS_INITIAL =
            new ArrayList<String>(Arrays.asList(
                    helpCommandWord, addCommandWord, listCommandWord, listAllTagsCommandWord, editCommandWord,
                    findCommandWord, deleteCommandWord, selectCommandWord, historyCommandWord, calendarCommandWord,
                    addEventCommandWord, massEmailCommandWord, smsCommandWord, undoCommandWord, redoCommandWord,
                    clearCommandWord, exitCommandWord));

    private ObservableList<String> commandPredictionResults;

    // tempPredictionResults used to store the results from filtering through COMMAND_PREDICTION_RESULTS_INITIAL
    private ArrayList<String> tempPredictionResults;

    @FXML
    private ListView<String> commandPredictionListView;

    public CommandPredictionPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        initDataStructures();
        initListView();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * Helper method for the constructor to initialise the various data structures used
     * in the CommandPredictionPanel
     */
    private void initDataStructures() {
        tempPredictionResults = new ArrayList<String>();
        commandPredictionResults = FXCollections.observableArrayList(tempPredictionResults);
    }

    /**
     * Helper method for the constructor to initialise the ListView UI
     */
    private void initListView() {
        // Attach ObservableList to ListView
        commandPredictionListView.setItems(commandPredictionResults);
    }

    /**
     * Helper method for the constructor
     * Attaches an event handler to the CommandPredictionPanel to track when
     * the user changes the CommandPrediction
     *
     * The method fires another event to the {@link seedu.address.commons.core.EventsCenter},
     * which is handled by {@link CommandBox} and in turns changes its state
     */
    private void setEventHandlerForSelectionChangeEvent() {
        commandPredictionListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in command prediction panel changed to : '" + newValue + "'");
                        raise(new CommandPredictionPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Helper method to retrieve the currently selected Command Prediction
     */
    public String getSelection() {
        return commandPredictionListView.getSelectionModel().getSelectedItem();
    }

    /**
     * This method refreshes the CommandPredictionPanel with results that start with `newText`
     * @param newText
     */
    private void updatePredictionResults(String newText) {
        commandPredictionResults.clear();
        tempPredictionResults = filterPredictionResults(newText);

        commandPredictionResults.addAll(tempPredictionResults);
        commandPredictionListView.setItems(commandPredictionResults);
        commandPredictionListView.getSelectionModel().selectFirst();

        // Set the prediction to be invisible if there is nothing typed in the Command Box
        // Or if there is no prediction to show
        if (newText.equals("") || commandPredictionResults.isEmpty()) {
            hideCommandPredictionPanel();
        } else {
            showCommandPredictionPanel();
        }
    }

    /**
     * Helper method to filter the prediction results that starts with {@code inputText}
     * @param inputText the text input by the user into the {@link CommandBox}
     * @return an ArrayList containing all the filtered results
     */
    public static ArrayList<String> filterPredictionResults(String inputText) {
        if ("".equals(inputText)) {
            return new ArrayList<String>();
        }
        return COMMAND_PREDICTION_RESULTS_INITIAL
                .stream()
                .filter(p -> p.startsWith(inputText))
                .collect(toCollection(ArrayList::new));
    }

    /**
     * Helper method to raise an event to hide the {@link CommandPredictionPanel}
     */
    private void hideCommandPredictionPanel() {
        raise(new CommandPredictionPanelHideEvent());
    }

    /**
     * Helper method to raise an event to show the {@link CommandPredictionPanel}
     */
    private void showCommandPredictionPanel() {
        raise(new CommandPredictionPanelShowEvent());
    }

    /**
     * Helper method to handle the event where the user clicks on a Command Prediction
     * in the {@code CommandPredictionPanel}
     */
    @FXML
    private void handleMouseClick() {
        String currentSelection = getSelection();
        raise(new CommandBoxReplaceTextEvent(currentSelection));
        raise(new CommandPredictionPanelHideEvent());
    }

    /**
     * Getter method for testing the contents of the CommandPredictionPanel
     * @return the ListView fx component
     */
    public ListView<String> getListView() {
        return commandPredictionListView;
    }

    /**
     * Detects changes in the {@link CommandBox} and updates the contents of the Command Predictions
     * @param event the event fired by the {@code CommandBox} to indicate a change in content
     */
    @Subscribe
    private void handleCommandBoxContentsChangedEvent(CommandBoxContentsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updatePredictionResults(event.getCommandText());
    }

    /**
     * Shifts the currently selected Command Prediction down by one when the down arrow key is pressed
     * while the user is focused on the {@code CommandBox}
     * @param event the event fired by the {@code CommandBox} to
     * indicate the request to select the next Command Prediction
     */
    @Subscribe
    private void handleCommandPredictionPanelNextSelectionEvent(CommandPredictionPanelNextSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.getSelectionModel().selectNext();
    }

    /**
     * Shifts the currently selected Command Prediction up by one when the up arrow key is pressed
     * while the user is focused on the {@code CommandBox}
     * @param event the event fired by the {@code CommandBox} to
     * indicate the request to select the previous Command Prediction
     */
    @Subscribe
    private void handleCommandPredictionPanelPreviousSelectionEvent(
            CommandPredictionPanelPreviousSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandPredictionListView.getSelectionModel().selectPrevious();
    }

    @Subscribe
    private void handleCommandPredictionPanelSelectionEvent(CommandPredictionPanelSelectionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String currentSelection = getSelection();
        raise(new CommandBoxReplaceTextEvent(currentSelection));
    }
}
