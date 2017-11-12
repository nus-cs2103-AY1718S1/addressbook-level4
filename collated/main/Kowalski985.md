# Kowalski985
###### \java\seedu\address\ui\AutocompleteCommand.java
``` java
/**
 * Represents the current command that the autocompleter recognises in the {@code CommandBox}
 */
public enum AutocompleteCommand {
    ADD,
    CLEAR,
    DELETE,
    DELETE_TAG,
    EDIT,
    EXIT,
    FIND,
    HELP,
    HISTORY,
    IMPORT,
    LIST,
    NONE,
    REDO,
    SELECT,
    TAB,
    UNDO;

    public static final String[] ALL_COMMANDS = {"add", "clear", "delete", "deleteTag", "edit", "exit", "find", "help",
        "history", "import", "list", "redo", "select", "tab", "undo"};

    public static final Prefix[] ALL_PREFIXES = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
        PREFIX_DELIVERY_DATE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

    private static final String[] commandsWithIndexes = {"delete", "edit", "select"};

    private static final String[] commandsWithPrefixes = {"add", "edit"};

    public static AutocompleteCommand getInstance(String commandName) {

        switch (commandName) {
        case AddCommand.COMMAND_WORD:
            return ADD;

        case ClearCommand.COMMAND_WORD:
            return CLEAR;

        case DeleteCommand.COMMAND_WORD:
            return DELETE;

        case DeleteTagCommand.COMMAND_WORD:
            return DELETE_TAG;

        case EditCommand.COMMAND_WORD:
            return EDIT;

        case ExitCommand.COMMAND_WORD:
            return EXIT;

        case FindCommand.COMMAND_WORD:
            return FIND;

        case HelpCommand.COMMAND_WORD:
            return HELP;

        case HistoryCommand.COMMAND_WORD:
            return HISTORY;

        case ImportCommand.COMMAND_WORD:
            return IMPORT;

        case ListCommand.COMMAND_WORD:
            return LIST;

        case RedoCommand.COMMAND_WORD:
            return REDO;

        case SelectCommand.COMMAND_WORD:
            return SELECT;

        case TabCommand.COMMAND_WORD:
            return TAB;

        case UndoCommand.COMMAND_WORD:
            return UNDO;

        default:
            return NONE;
        }
    }

    public static boolean hasIndexParameter (String command) {
        return Arrays.asList(commandsWithIndexes).contains(command);
    }

    public static boolean hasPrefixParameter (String command) {
        return Arrays.asList(commandsWithPrefixes).contains(command);
    }


}
```
###### \java\seedu\address\ui\Autocompleter.java
``` java
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
            return (possibleAutocompleteResults.isEmpty()) ? textInCommandBox : possibleAutocompleteResults.get(0);

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

        case COMMAND_COMPLETE_PREFIX:
            clearResultsWindow();
            return textInCommandBox + "/";

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

            ArrayList<String> missingPrefixes = parser.getMissingPrefixes(arguments);
            if (lastCharIsStartOfPrefix(commandBoxText)) {
                state = AutocompleteState.COMMAND_COMPLETE_PREFIX;
                return;
            }

            if (lastTwoCharactersArePrefix(commandBoxText)) {
                setIndexToOneIfNeeded();
                if (missingPrefixes.size() > possibleAutocompleteResults.size()) {
                    possibleAutocompleteResults = missingPrefixes;
                }
                state = AutocompleteState.COMMAND_CYCLE_PREFIX;
                return;
            }

            possibleAutocompleteResults = missingPrefixes;
            state = AutocompleteState.COMMAND_NEXT_PREFIX;
        } else {
            state = AutocompleteState.COMMAND;
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
        if (!state.equals(AutocompleteState.MULTIPLE_COMMAND)) {
            resultIndex = 0;
        }
    }

    private void setIndexToOneIfNeeded() {
        if (!state.equals(AutocompleteState.COMMAND_CYCLE_PREFIX)) {
            resultIndex = 1;
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
        return (!index.equals(EMPTY_STRING) && isNumeric(index));
    }

    /**
     * Returns true if the string is numeric
     */
    private boolean isNumeric (String index) {
        try {
            return index.matches("[0-9]+");
        } catch (NullPointerException e) {
            return false;
        }
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
     * Returns true if the last 2 characters of {@code String} is a space
     * followed the first letter of a {@code Prefix}
     */
    private boolean lastCharIsStartOfPrefix(String commandBoxText) {
        if (commandBoxText.length() < 1) {
            return false;
        }
        String lastTwoCharacter = commandBoxText.substring(commandBoxText.length() - 2);
        return Arrays.stream(AutocompleteCommand.ALL_PREFIXES)
                .map(s -> SPACE + s.toString().substring(0, 1))
                .anyMatch(s -> s.equals(lastTwoCharacter));
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
```
###### \java\seedu\address\ui\AutocompleteState.java
``` java
/**
 * Represents the states of the autcompleter
 */
public enum AutocompleteState {
    COMMAND,
    COMMAND_NEXT_PREFIX,
    COMMAND_CYCLE_PREFIX,
    COMMAND_COMPLETE_PREFIX,
    EMPTY,
    MULTIPLE_COMMAND,
    NO_RESULT,
    INDEX
}
```
###### \java\seedu\address\ui\CommandBoxParser.java
``` java
/**
 * Parses text that the user has entered in the command box
 */
public class CommandBoxParser {

    protected static final int COMMAND_INDEX = 0;
    protected static final int ARGUMENT_INDEX = 1;

    private static final  String EMPTY_STRING = "";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Prefix[] ALL_PREFIXES = {PREFIX_TRACKING_NUMBER, PREFIX_NAME, PREFIX_ADDRESS,
        PREFIX_DELIVERY_DATE, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STATUS, PREFIX_TAG};

    /**
     * Parses {@code String} to see if it contains any instances of a {@code Command} and {@code Prefix}
     * @param commandBoxText
     */
    public String[] parseCommandAndPrefixes(String commandBoxText) {
        String[] parseResults = {EMPTY_STRING, EMPTY_STRING };
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandBoxText.trim());
        if (!matcher.matches()) {
            return parseResults;
        }
        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        if (isValidCommand(commandWord)) {
            parseResults[COMMAND_INDEX] = commandWord;
        }
        parseResults[ARGUMENT_INDEX] = arguments;
        return parseResults;
    }

    /**
     * Returns the ArrayList of prefixes that are missing from the {@code String argument}
     * @return {@code ArrayList} of missing prefixes as {@code Strings}
     */
    public ArrayList<String> getMissingPrefixes(String argument) {
        Prefix[] prefixes = ALL_PREFIXES;
        ArrayList<String> missingPrefixes = new ArrayList<>();
        ArgumentMultimap argMap = ArgumentTokenizer.tokenize(argument, prefixes);
        Arrays.stream(ALL_PREFIXES)
                .filter(p -> isMissing(argMap, p))
                .forEach(p -> missingPrefixes.add(p.toString()));
        return missingPrefixes;
    }

    /**
     * Returns true if the {@code Prefix} is not present in the {@code ArgumentMultiMap}
     * or if it is present but is still missing arguments
     */
    private boolean isMissing(ArgumentMultimap argMap, Prefix prefix) {
        return !argMap.getValue(prefix).isPresent()
                || argMap.getValue(prefix).get().equals(EMPTY_STRING);
    }

    private boolean isValidCommand(String commandWord) {
        return Arrays.asList(AutocompleteCommand.ALL_COMMANDS).contains(commandWord);
    }
}
```
