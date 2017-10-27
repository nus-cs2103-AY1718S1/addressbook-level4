package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * Autocomplete utility used in the command box
 */
public class Autocompleter {

    private static final String PROMPT_USER_TO_USE_HELP_MESSAGE = "To see what commands are available, type 'help' "
            + "into the command box";
    private static final String MULTIPLE_RESULT_MESSAGE = "Multiple matches found";

    private static final  String EMPTY_STRING = "";

    private static final String[] commandList = {"add", "clear", "delete", "edit", "exit", "find", "help", "history",
        "list", "redo", "select", "undo"};


    public Autocompleter() {
        registerAsAnEventHandler(this);
    }

    /**
     * Returns the autocompleted command to be filled into the command box
     * @param commandBoxText from the command box
     * @return autocomplete text
     */
    public String autocomplete(String commandBoxText) {
        String[] commandBoxTextArray = commandBoxText.trim().split("\\s+");
        String autocompleteText = commandBoxText;
        if (commandBoxText.equals(EMPTY_STRING)) {
            raise(new NewResultAvailableEvent(PROMPT_USER_TO_USE_HELP_MESSAGE, false));
        } else if (commandBoxTextArray.length == 1) {
            autocompleteText = processOneWordAutocomplete(commandBoxTextArray[0]);
        }
        return autocompleteText;
    }

    /**
     * Handle autocomplete when there is only word in the command box
     * @param commandBoxText
     * @return
     */
    private String processOneWordAutocomplete(String commandBoxText) {
        ArrayList<String> possibleResults = getClosestCommands(commandBoxText);
        switch (possibleResults.size()) {
        case 0:
            clearResultsWindow();
            return commandBoxText;
        case 1:
            clearResultsWindow();
            return possibleResults.get(0);
        default:
            displayMultipleResults(possibleResults);
            return commandBoxText;
        }
    }

    /**
     * Get a list of possible commands to autocomplete
     * @param commandBoxText
     * @return ArrayList of possible autocomplete results
     */
    private ArrayList<String> getClosestCommands (String commandBoxText) {
        ArrayList<String> possibleResults = new ArrayList<>();
        Arrays.stream(commandList)
                .filter(s -> isPossibleMatch(commandBoxText.toLowerCase(), s))
                .forEach(s -> possibleResults.add(s));
        return possibleResults;
    }

    /**
     * Checks if the text in the command box is a substring of a particular command word
     * @param commandBoxText
     * @param commandWord
     */
    private boolean isPossibleMatch(String commandBoxText, String commandWord) {
        return (commandBoxText.length() < commandWord.length()
                && commandBoxText.equals(commandWord.substring(0, commandBoxText.length())));
    }

    /**
     * Creates message to tell user that there are multiple results
     * @param results
     */
    private void displayMultipleResults(ArrayList<String> results) {
        String resultToDisplay = MULTIPLE_RESULT_MESSAGE + ":\n";
        for (String result : results) {
            resultToDisplay += result + "\t";
        }
        raise(new NewResultAvailableEvent(resultToDisplay.trim(), false));
    }

    private void clearResultsWindow() {
        raise(new NewResultAvailableEvent(EMPTY_STRING, false));
    }

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    private void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }

    /**
     * Raises the event via {@link EventsCenter#post(BaseEvent)}
     * @param event
     */
    private void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }
}
