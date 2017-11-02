package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;

//@@author Kowalski985
/**
 * Autocomplete utility used in the command box
 */
public class Autocompleter {

    private static final String PROMPT_USER_TO_USE_HELP_MESSAGE = "To see what commands are available, type 'help' "
            + "into the command box";
    private static final String MULTIPLE_RESULT_MESSAGE = "Multiple matches found";
    private static final String EMPTY_STRING = "";
    private static final String SPACE = " ";

    private int resultIndex;
    private int countingIndex;
    private String textInCommandBox;
    private AutocompleteState state;
    private AutocompleteCommand currentCommand;
    private ArrayList<String> possibleAutocompleteResults;
    private CommandBoxParser parser;
    private Logic logic;


    public Autocompleter(Logic logic) {
        registerAsAnEventHandler(this);
        this.logic = logic;
        parser = new CommandBoxParser();
        resultIndex = 0;
        countingIndex = 1;
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
            return (possibleAutocompleteResults.isEmpty()) ? EMPTY_STRING : possibleAutocompleteResults.get(0);

        case EMPTY:
            raise(new NewResultAvailableEvent(PROMPT_USER_TO_USE_HELP_MESSAGE, false));
            return EMPTY_STRING;

        case COMMAND_NEXT_PREFIX:
            clearResultsWindow();
            if (possibleAutocompleteResults.isEmpty()) {
                return textInCommandBox;
            }
            return textInCommandBox.trim() + " " + possibleAutocompleteResults.get(0);

        case COMMAND_CYCLE_PREFIX:
            clearResultsWindow();
            return textInCommandBox.substring(0, textInCommandBox.length() - 2)
                    + possibleAutocompleteResults.get(cycleIndex());

        case INDEX:
            clearResultsWindow();
            String[] temp = textInCommandBox.split(" ");
            return temp[0] + " " + cycleCountingIndex();

        case MULTIPLE_COMMAND:
            displayMultipleResults(possibleAutocompleteResults);
            return possibleAutocompleteResults.get(cycleIndex());

        case NO_RESULT: //fall through to default

        default:
            clearResultsWindow();
            return textInCommandBox;
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
     * returns the current value of countingIndex ,updates the value of maxIndex and then increments
     * countingIndex by 1 with wrap-around
     * @return current value of countingIndex
     */
    private int cycleCountingIndex() {
        int currentIndex = countingIndex;
        int maxIndex = logic.getActiveList().size();
        countingIndex = (countingIndex + 1) % (maxIndex + 1);
        if (countingIndex == 0) {
            countingIndex = 1;
        }
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
        String arguments = currentTextArray[CommandBoxParser.ARGUMENT_INDEX];
        currentCommand = AutocompleteCommand.getInstance(commandWord);

        // first word does not match any commands
        if (currentCommand.equals(AutocompleteCommand.NONE)) {
            autocompleteCommandWord(commandBoxText);
            return;
        }

        if (isCyclingThroughCommands(commandWord)) {
            return;
        }

        if (AutocompleteCommand.hasIndexParameter(commandWord)
                && (!AutocompleteCommand.hasPrefixParameter(commandWord)
                || needIndex(arguments))) {
            resetCountingAndMaxIndexIfNeeded();
            state = AutocompleteState.INDEX;
            return;
        }

        if (AutocompleteCommand.hasPrefixParameter(commandWord)) {
            if (lastTwoCharactersArePrefix(commandBoxText)) {
                resetIndexIfNeeded();
                state = AutocompleteState.COMMAND_CYCLE_PREFIX;
                return;
            }
            possibleAutocompleteResults = getMissingPrefixes(arguments);
            state = AutocompleteState.COMMAND_NEXT_PREFIX;
        }

    }

    private boolean isCyclingThroughCommands(String commandWord) {
        return state.equals(AutocompleteState.MULTIPLE_COMMAND) && textInCommandBox.length() == commandWord.length();
    }

    /**
     * Returns true if the the autocompleter was not cycling through possible options
     * in it's previous state
     */
    private void resetIndexIfNeeded() {
        if (!state.equals(AutocompleteState.MULTIPLE_COMMAND)
                && !state.equals(AutocompleteState.COMMAND_CYCLE_PREFIX)) {
            resultIndex = 0;
        }
    }

    private void resetCountingAndMaxIndexIfNeeded() {
        if (!state.equals(AutocompleteState.INDEX)) {
            countingIndex = 1;
        }
    }

    private boolean needIndex(String arguments) {
        return  (state.equals(AutocompleteState.INDEX) && lastCharIsDigit(textInCommandBox))
                || !(containsIndex(arguments));
    }

    /**
     * Check if the index field in the {@code String} has already been entered
     */
    private boolean containsIndex(String arguments) {
        String parameters = arguments;
        Prefix[] prefixes = AutocompleteCommand.ALL_PREFIXES;
        if (lastTwoCharactersArePrefix(arguments)) {
            parameters = arguments + SPACE;
        }
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(parameters, prefixes);

        String index = argMap.getPreamble();
        return !index.equals(EMPTY_STRING);
    }

    /**
     * Returns true if the last character of the {@code String} is a digit
     */
    private boolean lastCharIsDigit(String text) {
        if (text.length() < 1) {
            return false;
        }
        return Character.isDigit(text.charAt(text.length() - 1));
    }



    /**
     * Handle autocomplete when there is only word in the command box
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

    /**
     * Checks if the last two characters of the {@code String} are prefixes
     */
    private boolean lastTwoCharactersArePrefix(String commandBoxText) {
        if (commandBoxText.length() < 2) {
            return false;
        }
        String lastTwoCharacters = commandBoxText.substring(commandBoxText.length() - 2);
        return Arrays.stream(AutocompleteCommand.ALL_PREFIXES)
                .anyMatch(s -> lastTwoCharacters.equals(s.toString()));
    }

    /**
     * Returns the ArrayList of prefixes that are missing from the {@code String}
     */
    private ArrayList<String> getMissingPrefixes(String arguments) {
        Prefix[] prefixes = AutocompleteCommand.ALL_PREFIXES;
        ArrayList<String> missingPrefixes = new ArrayList<>();
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(arguments, prefixes);
        for (Prefix p : prefixes) {
            if (!argMap.getValue(p).isPresent()) {
                missingPrefixes.add(p.toString());
            }
        }
        return missingPrefixes;
    }

    /**
     * Get a list of possible commands to autocomplete
     */
    private ArrayList<String> getClosestCommands (String commandBoxText) {
        ArrayList<String> possibleResults = new ArrayList<>();
        Arrays.stream(AutocompleteCommand.ALL_COMMANDS)
                .filter(s -> isPossibleMatch(commandBoxText.toLowerCase(), s))
                .forEach(s -> possibleResults.add(s));
        return possibleResults;
    }

    /**
     * Checks if the text in the command box is a substring of a particular command word
     */
    private boolean isPossibleMatch(String commandBoxText, String commandWord) {
        return (commandBoxText.length() <= commandWord.length()
                && commandBoxText.equals(commandWord.substring(0, commandBoxText.length())));
    }

    /**
     * Creates message to tell user that there are multiple results
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
     */
    private void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }
}
