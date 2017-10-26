package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;


/**
 * Autocomplete utility used in the command box
 */
public class Autocompleter {

    private static final String PROMPT_USER_TO_USE_HELP_MESSAGE = "To see what commands are available, type 'help' "
            + "into the command box";
    private static final String MULTIPLE_RESULT_MESSAGE = "Multiple matches found";

    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    private static final String[] commandList = {"add", "clear", "delete", "edit", "exit", "find", "help", "history",
        "list", "redo", "select", "undo"};

    private int resultIndex;
    private String textInCommandBox;
    private CommandBoxParser parser;
    private AutocompleteState state;
    private ArrayList<String> possibleAutocompleteResults;


    public Autocompleter() {
        registerAsAnEventHandler(this);
        resultIndex = 0;
        textInCommandBox = EMPTY_STRING;
        parser = new CommandBoxParser();
        //default state will be autocomplete for command
        state = AutocompleteState.EMPTY;
        possibleAutocompleteResults = new ArrayList<>();
    }

    /**
     * Returns the autocompleted command to be filled into the command box
     * @return autocomplete text
     */
    public String autocomplete() {
        /*
        String[] commandBoxTextArray = commandBoxText.trim().split("\\s+");
        String autocompleteText = commandBoxText;
        if (commandBoxText.equals(EMPTY_STRING)) {
            raise(new NewResultAvailableEvent(PROMPT_USER_TO_USE_HELP_MESSAGE, false));
        } else if (commandBoxTextArray.length == 1) {
            autocompleteText = autocompleteCommandWord(commandBoxTextArray[0]);
        }
        return autocompleteText;
        */
        switch (state) {
            case COMMAND:
                clearResultsWindow();
                return (possibleAutocompleteResults.isEmpty())? EMPTY_STRING : possibleAutocompleteResults.get(0);

            case EMPTY:
                raise(new NewResultAvailableEvent(PROMPT_USER_TO_USE_HELP_MESSAGE, false));
                return EMPTY_STRING;

            case MULTIPLE_COMMAND:
                displayMultipleResults(possibleAutocompleteResults);
                return possibleAutocompleteResults.get(cycleIndex());
        }
        return textInCommandBox;
    }

    /**
     * Updates the state of the autocompleter
     * @param commandBoxText from CommandBox
     */
    public void updateState(String commandBoxText) {
        textInCommandBox = commandBoxText;
        if (commandBoxText.equals(EMPTY_STRING)) {
            state = AutocompleteState.EMPTY;
            return;
        }
        String[] currentTextArray = parser.parseTextBox(commandBoxText);
        String commandWord = currentTextArray[CommandBoxParser.COMMAND_INDEX];
        String arguments = currentTextArray[CommandBoxParser.ARGUMENT_INDEX];

        if (commandWord.equals(EMPTY_STRING) && arguments.equals(EMPTY_STRING)) {
            autocompleteCommandWord(commandBoxText);
            return;
        }

        switch (commandWord) {
            case AddCommand.COMMAND_WORD:
                state = AutocompleteState.ADD;
                break;

            case EditCommand.COMMAND_WORD:
                if (commandBoxText.substring(commandBoxText.length() - 1).equals(SPACE)) {
                    state = AutocompleteState.EDIT;
                }
                break;

            case FindCommand.COMMAND_WORD:
                state = AutocompleteState.FIND;
                break;
        }


    }

    /**
     * return the current value of resultIndex and then increment it by 1 with wrap-around
     * @return current value of resultIndex
     */
    private int cycleIndex() {
        int currentIndex = resultIndex;
        resultIndex = (resultIndex + 1) % possibleAutocompleteResults.size();
        return currentIndex;

    }

    /**
     * Handle autocomplete when there is only word in the command box
     * @param commandBoxText
     * @return result to be placed in side text box
     */
    private void autocompleteCommandWord(String commandBoxText) {
        ArrayList<String> possibleResults = getClosestCommands(commandBoxText);
        possibleAutocompleteResults = possibleResults;
        switch (possibleResults.size()) {
        case 0:
            state = AutocompleteState.COMMAND;
            break;

        case 1:
            state = AutocompleteState.COMMAND;
            break;

        default:
            state = AutocompleteState.MULTIPLE_COMMAND;
            resultIndex = 0;
            break;
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
