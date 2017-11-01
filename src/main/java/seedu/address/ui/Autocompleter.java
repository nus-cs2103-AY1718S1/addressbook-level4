package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;


/**
 * Autocomplete utility used in the command box
 */
public class Autocompleter {

    private static final String PROMPT_USER_TO_USE_HELP_MESSAGE = "To see what commands are available, type 'help' "
            + "into the command box";
    private static final String MULTIPLE_RESULT_MESSAGE = "Multiple matches found";
    private static final String EMPTY_STRING = "";

    private int resultIndex;
    private String textInCommandBox;
    private CommandBoxParser parser;
    private AutocompleteState state;
    private AutocompleteCommand currentCommand;
    private ArrayList<String> possibleAutocompleteResults;


    public Autocompleter() {
        registerAsAnEventHandler(this);
        parser = new CommandBoxParser();
        resultIndex = 0;
        textInCommandBox = EMPTY_STRING;
        state = AutocompleteState.EMPTY;
        currentCommand = AutocompleteCommand.NONE;
        possibleAutocompleteResults = new ArrayList<>();
    }

    /**
     * Returns the autocompleted command to be filled into the command box
     * @return autocomplete text
     */
    public String autocomplete() {
        switch (state) {
            case COMMAND:
                clearResultsWindow();
                return (possibleAutocompleteResults.isEmpty())? EMPTY_STRING : possibleAutocompleteResults.get(0);

            case EMPTY:
                raise(new NewResultAvailableEvent(PROMPT_USER_TO_USE_HELP_MESSAGE, false));
                return EMPTY_STRING;

            case COMMAND_NEXT_PREFIX:
                if (possibleAutocompleteResults.isEmpty()) {
                    return textInCommandBox;
                }
                return textInCommandBox.trim() + " " +possibleAutocompleteResults.get(0);

            case COMMAND_CYCLE_PREFIX:
                return textInCommandBox.substring(0, textInCommandBox.length() - 2) +
                        possibleAutocompleteResults.get(cycleIndex());

            case MULTIPLE_COMMAND:
                displayMultipleResults(possibleAutocompleteResults);
                return possibleAutocompleteResults.get(cycleIndex());
        }
        return textInCommandBox;
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
     * Updates the state of the autocompleter
     * @param commandBoxText from {@code CommandBox}
     */
    public void updateState(String commandBoxText) {
        textInCommandBox = commandBoxText;

        if (commandBoxText.equals(EMPTY_STRING)) {
            state = AutocompleteState.EMPTY;
            return;
        }

        String[] currentTextArray = parser.parseCommandAndPrefixes(commandBoxText);
        String commandWord = currentTextArray[CommandBoxParser.COMMAND_INDEX];
        currentCommand = AutocompleteCommand.getInstance(commandWord);


        if (currentCommand.equals(AutocompleteCommand.NONE)) {
            autocompleteCommandWord(commandBoxText);
            return;
        }

        if (state.equals(AutocompleteState.MULTIPLE_COMMAND) && textInCommandBox.length() == commandWord.length()) {
            return;
        }

        if (AutocompleteCommand.hasIndexParameter(commandWord)) {
            resetIndexIfNeeded();
            state = AutocompleteState.INDEX;
            return;
        }

        if (AutocompleteCommand.hasPrefixParameter(commandWord)) {
            possibleAutocompleteResults = getMissingPrefixes(commandBoxText);
            if (lastTwoCharactersArePrefix(commandBoxText)) {
                resetIndexIfNeeded();
                state = AutocompleteState.COMMAND_CYCLE_PREFIX;
                return;
            }
            state = AutocompleteState.COMMAND_NEXT_PREFIX;
            return;
        }


    }

    private void resetIndexIfNeeded() {
        if (!state.equals(AutocompleteState.INDEX)
                && !state.equals(AutocompleteState.MULTIPLE_COMMAND)
                && !state.equals(AutocompleteState.COMMAND_CYCLE_PREFIX)) {
            resultIndex = 0;
        }
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
            state = AutocompleteState.NO_RESULT;
            break;

        case 1:
            state = AutocompleteState.COMMAND;
            break;

        default:
            resetIndexIfNeeded();
            state = AutocompleteState.MULTIPLE_COMMAND;
            break;
        }
    }

    private boolean lastTwoCharactersArePrefix(String commandBoxText) {
        String lastTwoCharacters = commandBoxText.substring(commandBoxText.length() - 2);
        return Arrays.stream(AutocompleteCommand.allPrefixes)
                .anyMatch(s -> lastTwoCharacters.equals(s.toString()));
    }

    private ArrayList<String> getMissingPrefixes(String commandBoxText) {
        Prefix[] prefixes = AutocompleteCommand.allPrefixes;
        ArrayList<String> missingPrefixes = new ArrayList<>();
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(commandBoxText, prefixes);
        for (Prefix p : prefixes) {
            if (!argMap.getValue(p).isPresent()) {
                missingPrefixes.add(p.toString());
            }
        }
        return missingPrefixes;
    }

    /**
     * Get a list of possible commands to autocomplete
     * @param commandBoxText
     * @return ArrayList of possible autocomplete results
     */
    private ArrayList<String> getClosestCommands (String commandBoxText) {
        ArrayList<String> possibleResults = new ArrayList<>();
        Arrays.stream(AutocompleteCommand.allCommands)
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
        return (commandBoxText.length() <= commandWord.length()
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
