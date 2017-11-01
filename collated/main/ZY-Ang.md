# ZY-Ang
###### \java\seedu\address\commons\core\Messages.java
``` java
    public static final String MESSAGE_INVALID_EXTENSION_FORMAT = "Invalid extension format! "
            + "\nOnly files ending with %1$s are allowed.";
```
###### \java\seedu\address\commons\core\StarWars.java
``` java
/**
 * A star wars object that includes the streamer and telnet client source of the stream.
 * Obviously doesn't work if their server is down.
 * @author towel.blinkenlights.nl
 */
public class StarWars {

    private static StarWars instance;
    private static final String server = "towel.blinkenlights.nl";

    private TelnetClient telnetClient = new TelnetClient();
    private InputStream in;

    private StarWars() {
        try {
            // Connect to the specified server
            telnetClient.connect(server, 23);

            // Get input stream reference
            in = telnetClient.getInputStream();

        } catch (Exception e) {
            // nothing needs to be done.
        }
    }

    public static StarWars getInstance() {
        if (instance == null) {
            instance = new StarWars();
        }
        return instance;
    }

    public static boolean hasInstance() {
        return instance == null;
    }

    /**
     * Disconnects the initialized telnet client on exit.
     */
    public static void shutDown() {
        try {
            if (instance != null) {
                getInstance().telnetClient.disconnect();
            }
        } catch (IOException e) {
            shutDown();
        }
    }

    public InputStream getIn() {
        return in;
    }
}
```
###### \java\seedu\address\commons\events\storage\OpenRolodexRequestEvent.java
``` java
/**
 * Indicates a request for a reload in application with a new Rolodex.
 */
public class OpenRolodexRequestEvent extends BaseEvent {

    private final String filePath;

    public OpenRolodexRequestEvent(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\storage\RolodexChangedDirectoryEvent.java
``` java
/**
 * Indicates a change in the loaded data filepath.
 */
public class RolodexChangedDirectoryEvent extends BaseEvent {

    private final String filePath;

    public RolodexChangedDirectoryEvent(String newFilePath) {
        this.filePath = newFilePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\StarWarsEvent.java
``` java
/**
 * An event requesting to view the star wars window.
 */
public class StarWarsEvent extends BaseEvent {

    private StarWars starWars;

    public StarWarsEvent(StarWars starWarsStreamer) {
        starWars = starWarsStreamer;
    }

    public StarWars getStarWars() {
        return starWars;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\exceptions\InvalidExtensionException.java
``` java
/**
 * Signals an error caused by loading a file without the official .rldx extension.
 */
public class InvalidExtensionException extends IOException {
    public InvalidExtensionException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    public static String appendCrotchets(String s) {
        return "[" + s + "]";
    }

    public static String replaceBackslashes(String s) {
        return s.replace('\\', '/');
    }
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "a", "+"));
```
###### \java\seedu\address\logic\commands\ClearCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "c"));
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "d", "-"));
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "e", "change", "modify"));
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        /**
         * Returns a union-exclusive set between the {@code tags}
         * of the {@code EditPersonDescriptor} and an arbitrary
         * Set of tags {@code otherSet} to be exclusive against.
         *
         * @param otherSet to be XOR-ed to.
         * @return a new union-exclusive set of tags.
         */
        public Set<Tag> getXorTags(Set<Tag> otherSet) {
            if (tags == null) {
                return otherSet;
            }
            Set<Tag> tagsCopy = new HashSet<>(tags);
            Set<Tag> xorSet = new HashSet<>(tagsCopy);
            xorSet.addAll(otherSet);
            tagsCopy.retainAll(otherSet);
            xorSet.removeAll(tagsCopy);
            return xorSet;
        }
```
###### \java\seedu\address\logic\commands\ExitCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "quit", "close", "bye", "esc"));
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "f", "filter", "search"));
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names roughly match any of "
            + "the specified keywords (case-insensitive) "
            + "or whose tags contains the specified keywords (case-sensitive), "
            + "and displays them as a list with index numbers, "
            + "sorted by the specified sort order or the default sort order.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]..." + MESSAGE_SORT_USAGE + "\n"
            + "Example: " + COMMAND_WORD + " alice bobby charlie "
            + SORT_ARGUMENT_PHONE_DEFAULT + " " + SORT_ARGUMENT_NAME_DESCENDING;

    private final PersonDataContainsKeywordsPredicate predicate;
    private final List<SortArgument> sortArguments;
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateSortComparator(sortArguments);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getLatestPersonList().size()));
    }
```
###### \java\seedu\address\logic\commands\HelpCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "h", "assist", "faq"));
```
###### \java\seedu\address\logic\commands\HistoryCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "his", "past"));
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "l", "show", "display"));
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":"
            + "Displays all persons in the rolodex, "
            + "sorted by the specified sort order or the default sort order." + "\n"
            + "Parameters: " + MESSAGE_SORT_USAGE + "\n"
            + "Example: " + COMMAND_WORD + " "
            + SORT_ARGUMENT_NAME_DEFAULT +  " " + SORT_ARGUMENT_PHONE_DESCENDING;
```
###### \java\seedu\address\logic\commands\ListCommand.java
``` java
    @Override
    public CommandResult execute() {
        model.updateSortComparator(sortArguments);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
```
###### \java\seedu\address\logic\commands\NewCommand.java
``` java
/**
 * Creates a new Rolodex at a specified directory.
 */
public class NewCommand extends Command {

    public static final String COMMAND_WORD = "new";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "n", ">", "touch"));
    public static final String COMMAND_HOTKEY = "Ctrl+N";

    public static final String MESSAGE_CREATING = "Creating new file: `%1$s`";
    public static final String MESSAGE_ALREADY_EXISTS = "`%1$s` already exists. "
            + "Use the `" + OpenCommand.COMMAND_WORD + "` command for opening an existing file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Creates a new Rolodex file at the specified destination and "
            + "reloads the application using the rolodex supplied at the given file path.\n"
            + "Parameters: [FILEPATH]\n"
            + "Example: new data/new.rldx";

    private final String filePath;

    public NewCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (new File(filePath).exists()) {
            throw  new CommandException(String.format(MESSAGE_ALREADY_EXISTS, filePath));
        } else {
            EventsCenter.getInstance().post(new OpenRolodexRequestEvent(filePath));
            return new CommandResult(String.format(MESSAGE_CREATING, filePath));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewCommand // instanceof handles nulls
                && this.filePath.equals(((NewCommand) other).filePath)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\OpenCommand.java
``` java
/**
 * Opens an existing Rolodex in a different directory.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "o", "cd", "ls", "<"));
    public static final String COMMAND_HOTKEY = "Ctrl+O";

    public static final String MESSAGE_OPENING = "Opening file: `%1$s`";
    public static final String MESSAGE_NOT_EXIST = "Unable to find `%1$s`. "
            + "Use the `" + NewCommand.COMMAND_WORD + "` command for creating a new file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Reloads the application using the rolodex supplied at the given file path.\n"
            + "Parameters: [FILEPATH]\n"
            + "Example: open C:/Documents/MyRolodex.rldx";

    private final String filePath;

    public OpenCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (new File(filePath).exists()) {
            EventsCenter.getInstance().post(new OpenRolodexRequestEvent(filePath));
            return new CommandResult(String.format(MESSAGE_OPENING, filePath));
        } else {
            throw new CommandException(String.format(MESSAGE_NOT_EXIST, filePath));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpenCommand // instanceof handles nulls
                && this.filePath.equals(((OpenCommand) other).filePath)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\RedoCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "r"));
```
###### \java\seedu\address\logic\commands\SelectCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "s"));
```
###### \java\seedu\address\logic\commands\StarWarsCommand.java
``` java
/**
 * Opens the the star wars window and streams using telnet from towel.blinkenlights.nl.
 */
public class StarWarsCommand extends Command {
    public static final String COMMAND_WORD = "starwars";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "sw"));

    private StarWars starWars;

    public StarWarsCommand() {
        starWars = StarWars.getInstance();
    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new StarWarsEvent(starWars));
        return new CommandResult("Ba dum tsk");
    }
}
```
###### \java\seedu\address\logic\commands\UndoCommand.java
``` java
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "u"));
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<ReadOnlyPerson> getLatestPersonList();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getLatestPersonList() {
        return model.getLatestPersonList();
    }
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    /* Prefix set */
    public static final Set<Prefix> POSSIBLE_PREFIXES =
            new HashSet<>(Arrays.asList(
                    PREFIX_NAME,
                    PREFIX_PHONE,
                    PREFIX_EMAIL,
                    PREFIX_ADDRESS,
                    PREFIX_TAG
            ));

    /* Postfix definitions */
    public static final Postfix POSTFIX_ASCENDING = new Postfix("asc");
    public static final Postfix POSTFIX_DESCENDING = new Postfix("desc");

    /* SortArgument definitions */
    public static final SortArgument SORT_ARGUMENT_NAME_DEFAULT =
            new SortArgument(PREFIX_NAME.toString());
    public static final SortArgument SORT_ARGUMENT_PHONE_DEFAULT =
            new SortArgument(PREFIX_PHONE.toString());
    public static final SortArgument SORT_ARGUMENT_EMAIL_DEFAULT =
            new SortArgument(PREFIX_EMAIL.toString());
    public static final SortArgument SORT_ARGUMENT_ADDRESS_DEFAULT =
            new SortArgument(PREFIX_ADDRESS.toString());
    public static final SortArgument SORT_ARGUMENT_NAME_ASCENDING =
            new SortArgument(PREFIX_NAME.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_PHONE_ASCENDING =
            new SortArgument(PREFIX_PHONE.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_EMAIL_ASCENDING =
            new SortArgument(PREFIX_EMAIL.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_ADDRESS_ASCENDING =
            new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_ASCENDING));
    public static final SortArgument SORT_ARGUMENT_NAME_DESCENDING =
            new SortArgument(PREFIX_NAME.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_PHONE_DESCENDING =
            new SortArgument(PREFIX_PHONE.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_EMAIL_DESCENDING =
            new SortArgument(PREFIX_EMAIL.concat(POSTFIX_DESCENDING));
    public static final SortArgument SORT_ARGUMENT_ADDRESS_DESCENDING =
            new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_DESCENDING));

    /* SortArgument set */
    public static final Set<SortArgument> POSSIBLE_SORT_ARGUMENTS =
            new HashSet<>(Arrays.asList(
                    SORT_ARGUMENT_NAME_DEFAULT,
                    SORT_ARGUMENT_PHONE_DEFAULT,
                    SORT_ARGUMENT_EMAIL_DEFAULT,
                    SORT_ARGUMENT_ADDRESS_DEFAULT,
                    SORT_ARGUMENT_NAME_DESCENDING,
                    SORT_ARGUMENT_PHONE_DESCENDING,
                    SORT_ARGUMENT_EMAIL_DESCENDING,
                    SORT_ARGUMENT_ADDRESS_DESCENDING,
                    SORT_ARGUMENT_NAME_ASCENDING,
                    SORT_ARGUMENT_PHONE_ASCENDING,
                    SORT_ARGUMENT_EMAIL_ASCENDING,
                    SORT_ARGUMENT_ADDRESS_ASCENDING));
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> dataKeywords = new ArrayList<>();
        List<SortArgument> sortArgumentList = new ArrayList<>();

        setupArguments(keywords, dataKeywords, sortArgumentList, FindCommand.MESSAGE_USAGE);

        if (dataKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonDataContainsKeywordsPredicate(dataKeywords), sortArgumentList);
    }
```
###### \java\seedu\address\logic\parser\ListCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new ListCommand(null);
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> dataKeywords = new ArrayList<>();
        List<SortArgument> sortArgumentList = new ArrayList<>();

        setupArguments(keywords, dataKeywords, sortArgumentList, ListCommand.MESSAGE_USAGE);

        if (!dataKeywords.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand(sortArgumentList);
    }

}
```
###### \java\seedu\address\logic\parser\NewCommandParser.java
``` java
/**
 * Parses input arguments and creates a new NewCommand object
 */
public class NewCommandParser implements Parser<NewCommand> {
    @Override
    public NewCommand parse(String args) throws ParseException {
        String trimmedAndFormattedArgs = args.trim().replace("\\", "/");
        if (trimmedAndFormattedArgs.isEmpty()
                || !isValidRolodexStorageFilepath(trimmedAndFormattedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
        }
        if (!isValidRolodexStorageExtension(trimmedAndFormattedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));
        }

        return new NewCommand(trimmedAndFormattedArgs);
    }
}
```
###### \java\seedu\address\logic\parser\OpenCommandParser.java
``` java
/**
 * Parses input arguments and creates a new OpenCommand object
 */
public class OpenCommandParser implements Parser<OpenCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the OpenCommand
     * and returns a OpenCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public OpenCommand parse(String args) throws ParseException {
        String trimmedAndFormattedArgs = replaceBackslashes(args.trim());
        if (trimmedAndFormattedArgs.isEmpty()
                || !isValidRolodexStorageFilepath(trimmedAndFormattedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
        }

        return new OpenCommand(trimmedAndFormattedArgs);
    }
}
```
###### \java\seedu\address\logic\parser\Postfix.java
``` java
/**
 * A postfix that is appended to the end of an argument in an arguments string.
 * E.g. 'desc' in 'find James t/desc'.
 */
public class Postfix {
    private final String postfix;

    public Postfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPostfix() {
        return postfix;
    }

    public String toString() {
        return getPostfix();
    }

    @Override
    public int hashCode() {
        return postfix == null ? 0 : postfix.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Postfix)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Postfix otherPostfix = (Postfix) obj;
        return otherPostfix.getPostfix().equals(getPostfix());
    }
}
```
###### \java\seedu\address\logic\parser\Prefix.java
``` java
    public String concat(Postfix postfix) {
        return prefix.concat(postfix.toString());
    }
```
###### \java\seedu\address\logic\parser\RolodexParser.java
``` java
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        if (AddCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new AddCommandParser().parse(arguments);
        } else if (EditCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new EditCommandParser().parse(arguments);
        } else if (SelectCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new SelectCommandParser().parse(arguments);
        } else if (DeleteCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (ClearCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new ClearCommand();
        } else if (FindCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new FindCommandParser().parse(arguments);
        } else if (ListCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new ListCommandParser().parse(arguments);
        } else if (HistoryCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new HistoryCommand();
        } else if (ExitCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new ExitCommand();
        } else if (HelpCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new HelpCommand();
        } else if (UndoCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new UndoCommand();
        } else if (RedoCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new RedoCommand();
        } else if (OpenCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new OpenCommandParser().parse(arguments);
        } else if (NewCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new NewCommandParser().parse(arguments);
        }  else if (StarWarsCommand.COMMAND_WORD_ABBREVIATIONS.contains(commandWord)) {
            return new StarWarsCommand();
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
```
###### \java\seedu\address\logic\parser\SortArgument.java
``` java
/**
 * A sort argument that marks the way a list should be sorted.
 * E.g. 'n/desc' in 'list n/desc' displays a list sorted by name in descending order
 */
public class SortArgument {
    private final String argument;

    public SortArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return getArgument();
    }

    @Override
    public int hashCode() {
        return argument == null ? 0 : argument.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SortArgument)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        SortArgument otherArgument = (SortArgument) obj;
        return otherArgument.getArgument().equals(getArgument());
    }
}
```
###### \java\seedu\address\logic\parser\SortUtil.java
``` java
/**
 * Contains utility methods for sorting SortArguments in the various sort enabled classes.
 */
public class SortUtil {

    /* Sub-message that lists out all the message usage possibilities of the sort parameters */
    public static final String MESSAGE_SORT_USAGE =
            appendCrotchets(SORT_ARGUMENT_NAME_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_PHONE_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_EMAIL_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_ADDRESS_DEFAULT.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_NAME_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_PHONE_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_EMAIL_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_ADDRESS_DESCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_NAME_ASCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_PHONE_ASCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_EMAIL_ASCENDING.toString()) + " "
            + appendCrotchets(SORT_ARGUMENT_ADDRESS_ASCENDING.toString());

    /**
     * Orders and filters the keywords to the correct lists to be passed into the new find command.
     */
    public static void setupArguments(String[] keywords,
                                      List<String> dataKeywordList,
                                      List<SortArgument> sortArgumentList,
                                      String errorMessage)
            throws ParseException {
        for (String keyword : keywords) {
            SortArgument sortArgument = new SortArgument(keyword);
            if (!POSSIBLE_SORT_ARGUMENTS.contains(sortArgument) && sortArgumentList.isEmpty()) {
                dataKeywordList.add(keyword);
            } else if (!POSSIBLE_SORT_ARGUMENTS.contains(sortArgument) && !sortArgumentList.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, errorMessage));
            } else {
                sortArgumentList.add(sortArgument);
            }
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Reloads the current Rolodex application with new data at the specified filePath.
     * @param newRolodexPath string path of the new Rolodex to be loaded.
     * @throws IOException if unable to save the new filePath into the user preferences
     */
    public void loadNewRolodexPath(String newRolodexPath) throws IOException {
        userPrefs.setRolodexFilePath(newRolodexPath);
        storage.saveUserPrefs(userPrefs);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        RolodexStorage rolodexStorage = new XmlRolodexStorage(userPrefs.getRolodexFilePath());

        storage.setNewRolodexStorage(rolodexStorage);
        Model modelToBeLoaded = initModelManager(storage, userPrefs);
        model.resetData(modelToBeLoaded.getRolodex());
        EventsCenter.getInstance().post(new RolodexChangedDirectoryEvent(newRolodexPath));
    }
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleOpenNewRolodexRequestEvent(OpenRolodexRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        String savedRolodexFilePath = userPrefs.getRolodexFilePath();
        try {
            loadNewRolodexPath(event.getFilePath());
        } catch (IOException e) {
            logger.severe("Failed to save preferences, reverting to previous Rolodex " + StringUtil.getDetails(e));
            loadPreviousRolodex(savedRolodexFilePath);
        }
    }

    /**
     * Loads the previous Rolodex into the current application and updates the userprefs.
     */
    private void loadPreviousRolodex(String savedFilePath) {
        try {
            loadNewRolodexPath(savedFilePath);
        } catch (Exception e) {
            logger.severe("Failed to initialize previous Rolodex, stopping program " + StringUtil.getDetails(e));
            this.stop();
        }
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getLatestPersonList();
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the sortComparator of the model to sort by the given ordered {@code sortArguments}.
     * @throws NullPointerException if {@code sortArguments} is null.
     */
    void updateSortComparator(List<SortArgument> sortArguments);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    private final SortedList<ReadOnlyPerson> sortedPersons;
    private Predicate<ReadOnlyPerson> predicate;

    /**
     * Initializes a ModelManager with the given rolodex and userPrefs.
     */
    public ModelManager(ReadOnlyRolodex rolodex, UserPrefs userPrefs) {
        super();
        requireAllNonNull(rolodex, userPrefs);

        logger.fine("Initializing with rolodex: " + rolodex + " and user prefs " + userPrefs);

        this.rolodex = new Rolodex(rolodex);
        filteredPersons = new FilteredList<>(this.rolodex.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
        updateSortComparator(null);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        rolodex.addPerson(person);
        updateSortComparator(null);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateRolodexChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Latest Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code rolodex}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getLatestPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersons.filtered(predicate));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updateSortComparator(List<SortArgument> sortArguments) {
        sortedPersons.setComparator(generateComparator(sortArguments));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return rolodex.equals(other.rolodex)
                && sortedPersons.equals(other.sortedPersons)
                && filteredPersons.equals(other.filteredPersons);
    }
```
###### \java\seedu\address\model\person\Address.java
``` java
    public int compareTo(Address other) {
        return toString().compareTo(other.toString());
    }
```
###### \java\seedu\address\model\person\Email.java
``` java
    public int compareTo(Email other) {
        return toString().compareTo(other.toString());
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
    /**
     * Retrieves a listing of every word in the name, in order.
     */
    public List<String> getWordsInName() {
        return Arrays.asList(fullName.split("\\s+"));
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
    public int compareTo(Name other) {
        return toString().compareTo(other.toString());
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns whether a person's name contains some of the specified keywords,
     * within a Levenshtein distance of {@link #FIND_NAME_DISTANCE_TOLERANCE},
     * ONLY WHEN the name word contains a number of characters
     * more than or equal to the global limit {@link #FIND_NAME_GLOBAL_TOLERANCE}.
     *
     * Otherwise, it returns the case-insensitive direct match for the particular
     * name word and continues on to the next name word for a check.
     *
     * If no conditions are triggered, the method returns false.
     *
     * @param keyWords for searching
     * @return {@code true} if the name is close to any keyWord,
     *         {@code false} otherwise.
     */
    @Override
    public boolean isNameCloseToAnyKeyword(List<String> keyWords) {
        return getName().getWordsInName().stream()
                .anyMatch(nameWord -> nameWord.length() >= FIND_NAME_GLOBAL_TOLERANCE && keyWords.stream()
                        .anyMatch(keyWord -> levenshteinDistance(nameWord, keyWord) <= FIND_NAME_DISTANCE_TOLERANCE));
    }

    /**
     * Returns whether a person's name matches any of the specified keywords
     * @param keyWords for searching
     * @return {@code true} if the name matches any keyWord,
     *         {@code false} otherwise.
     */
    @Override
    public boolean isNameMatchAnyKeyword(List<String> keyWords) {
        return keyWords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(getName().fullName, keyword));
    }

    /**
     * Returns whether any of a person's tags exist in the search parameters.
     * @param keyWords set of case-sensitive keywords to be matched against
     *                 search parameters
     * @return {@code false} if the sets are disjoint,
     * {@code true} otherwise.
     */
    @Override
    public boolean isTagSetJointKeywordSet(List<String> keyWords) {
        return !Collections.disjoint(keyWords,
                getTags().stream().map(tag -> tag.tagName).collect(Collectors.toSet()));
    }

    @Override
    public boolean isSearchKeyWordsMatchAnyData(List<String> keyWords) {
        return isNameCloseToAnyKeyword(keyWords)
                || isNameMatchAnyKeyword(keyWords)
                || isTagSetJointKeywordSet(keyWords);
    }

    /**
     * Returns the default compareTo integer value for comparing against another person
     * @param otherPerson to be compare to
     * @return object.compareTo(other) value for the two persons.
     */
    @Override
    public int compareTo(ReadOnlyPerson otherPerson) {
        int c;
        if ((c = getName().compareTo(otherPerson.getName())) != 0) {
            return c;
        } else if ((c = getPhone().compareTo(otherPerson.getPhone())) != 0) {
            return c;
        } else if ((c = getEmail().compareTo(otherPerson.getEmail())) != 0) {
            return c;
        } else {
            return getAddress().compareTo(otherPerson.getAddress());
        }
    }

    /**
     * Returns the compareTo integer value for a specified sortArgument.
     * @param otherPerson to be compared to
     * @param sortArgument sortArgument formatted field of the person to be compared
     * @return object.compareTo(other) value for the two persons or the default
     * person.compareTo(otherPerson) if sort argument is invalid.
     */
    @Override
    public int compareTo(ReadOnlyPerson otherPerson, SortArgument sortArgument) {
        if (sortArgument.equals(SORT_ARGUMENT_NAME_DEFAULT)) {
            return getName().compareTo(otherPerson.getName());
        } else if (sortArgument.equals(SORT_ARGUMENT_PHONE_DEFAULT)) {
            return getPhone().compareTo(otherPerson.getPhone());
        } else if (sortArgument.equals(SORT_ARGUMENT_EMAIL_DEFAULT)) {
            return getEmail().compareTo(otherPerson.getEmail());
        } else if (sortArgument.equals(SORT_ARGUMENT_ADDRESS_DEFAULT)) {
            return getAddress().compareTo(otherPerson.getAddress());
        } else if (sortArgument.equals(SORT_ARGUMENT_NAME_DESCENDING)) {
            return otherPerson.getName().compareTo(getName());
        } else if (sortArgument.equals(SORT_ARGUMENT_PHONE_DESCENDING)) {
            return otherPerson.getPhone().compareTo(getPhone());
        } else if (sortArgument.equals(SORT_ARGUMENT_EMAIL_DESCENDING)) {
            return otherPerson.getEmail().compareTo(getEmail());
        } else if (sortArgument.equals(SORT_ARGUMENT_ADDRESS_DESCENDING)) {
            return otherPerson.getAddress().compareTo(getAddress());
        } else if (sortArgument.equals(SORT_ARGUMENT_NAME_ASCENDING)) {
            return getName().compareTo(otherPerson.getName());
        } else if (sortArgument.equals(SORT_ARGUMENT_PHONE_ASCENDING)) {
            return getPhone().compareTo(otherPerson.getPhone());
        } else if (sortArgument.equals(SORT_ARGUMENT_EMAIL_ASCENDING)) {
            return getEmail().compareTo(otherPerson.getEmail());
        } else if (sortArgument.equals(SORT_ARGUMENT_ADDRESS_ASCENDING)) {
            return getAddress().compareTo(otherPerson.getAddress());
        } else {
            return compareTo(otherPerson);
        }
    }
```
###### \java\seedu\address\model\person\PersonDataContainsKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name} roughly matches any of the keywords given or
 * that any of the keywords are an exact match to any {@code Tag} in {@code UniqueTagList}
 */
public class PersonDataContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonDataContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.isSearchKeyWordsMatchAnyData(keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonDataContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonDataContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Phone.java
``` java
    public int compareTo(Phone other) {
        return toString().compareTo(other.toString());
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    boolean isNameCloseToAnyKeyword(List<String> keyWords);
    boolean isNameMatchAnyKeyword(List<String> keyWords);
    boolean isTagSetJointKeywordSet(List<String> keyWords);
    boolean isSearchKeyWordsMatchAnyData(List<String> keyWords);
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    int compareTo(ReadOnlyPerson otherPerson);
    int compareTo(ReadOnlyPerson otherPerson, SortArgument sortArgument);
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private String rolodexFilePath = "data/default.rldx";
```
###### \java\seedu\address\model\util\PersonSortingUtil.java
``` java
/**
 * Utility for any comparison or sorting related actions that include Persons.
 */
public class PersonSortingUtil {

    /**
     * Returns a newly generated comparator from the given sortArguments.
     */
    public static Comparator<ReadOnlyPerson> generateComparator(List<SortArgument> sortArguments) {
        return (person1, person2) -> {
            // default case for empty arguments
            if (sortArguments == null || sortArguments.isEmpty()) {
                return person1.compareTo(person2);

            } else {
                int previousCompareValue = 0;
                for (SortArgument sortArgument : sortArguments) {
                    if (previousCompareValue != 0) {
                        return previousCompareValue;
                    } else {
                        previousCompareValue = person1.compareTo(person2, sortArgument);
                    }
                }
                return previousCompareValue;

            }
        };
    }
}
```
###### \java\seedu\address\storage\JsonUserPrefsStorage.java
``` java
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof JsonUserPrefsStorage)) {
            return false;
        }

        // state check
        JsonUserPrefsStorage other = (JsonUserPrefsStorage) obj;
        return filePath.equals(other.filePath);
    }
```
###### \java\seedu\address\storage\Storage.java
``` java
    RolodexStorage getExistingRolodexStorage();

    void setNewRolodexStorage(RolodexStorage storage);
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public RolodexStorage getExistingRolodexStorage() {
        return rolodexStorage;
    }

    @Override
    public void setNewRolodexStorage(RolodexStorage newRolodexStorage) {
        rolodexStorage = newRolodexStorage;
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    //=========== State accessor =============================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof StorageManager)) {
            return false;
        }

        // state check
        StorageManager other = (StorageManager) obj;
        return rolodexStorage.equals(other.rolodexStorage)
                && userPrefsStorage.equals(other.userPrefsStorage);
    }
```
###### \java\seedu\address\storage\util\RolodexStorageUtil.java
``` java
/**
 * Helper functions for handling Rolodex storage operations.
 */
public class RolodexStorageUtil {

    public static final Pattern FILEPATH_REGEX = Pattern.compile("^(.+)/([^/]+)$");

    public static final String ROLODEX_FILE_EXTENSION = ".rldx";

    /**
     * Returns true if the full filepath string given has a valid extension
     */
    public static boolean isValidRolodexStorageExtension(String rolodexFilepath) {
        return rolodexFilepath.endsWith(ROLODEX_FILE_EXTENSION);
    }

    /**
     * Returns true if the full filepath string given is a valid filepath
     */
    public static boolean isValidRolodexStorageFilepath(String rolodexFilepath) {
        return FILEPATH_REGEX.matcher(rolodexFilepath).matches();
    }
}
```
###### \java\seedu\address\storage\XmlRolodexStorage.java
``` java
    /**
     * Similar to {@link #saveRolodex(ReadOnlyRolodex)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRolodex(ReadOnlyRolodex rolodex, String filePath) throws IOException {
        requireNonNull(rolodex);
        requireNonNull(filePath);
        if (!isValidRolodexStorageExtension(filePath)) {
            throw new InvalidExtensionException(
                    String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));
        }

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableRolodex(rolodex));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof XmlRolodexStorage)) {
            return false;
        }

        // state check
        XmlRolodexStorage other = (XmlRolodexStorage) obj;
        return filePath.equals(other.filePath);
    }
```
###### \java\seedu\address\ui\HelpWindow.java
``` java
    public static final String HELP_FILE_PATH = "/docs/Help.html";
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the star wars window
     */
    @FXML
    public void handleStarWars(StarWars starWars) {
        StarWarsWindow swWindow = new StarWarsWindow(starWars);
        swWindow.show();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleStarWarsEvent(StarWarsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleStarWars(event.getStarWars());
    }
```
###### \java\seedu\address\ui\StarWarsWindow.java
``` java
/**
 * Controller for a star wars page
 */
public class StarWarsWindow extends UiPart<Region> {

    private static Timeline timeline;

    private static final Logger logger = LogsCenter.getLogger(StarWarsWindow.class);
    private static final String ICON = "/images/rolodex_icon_32.png";
    private static final String FXML = "StarWarsWindow.fxml";
    private static final String TITLE = "Star Wars IV";

    @FXML
    private TextArea textArea;

    private final Stage dialogStage;
    private StarWars starWars;

    public StarWarsWindow(StarWars starWarsObject) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMinWidth(720.0);
        dialogStage.setMinHeight(480.0);
        dialogStage.setOnCloseRequest(event -> {
            StarWars.shutDown();
            timeline.stop();
        });
        dialogStage.setOnHiding(event -> StarWars.shutDown());
        FxViewUtil.setStageIcon(dialogStage, ICON);

        starWars = starWarsObject;
    }

    /**
     * Shows the star wars window.
     */
    public void show() {
        logger.fine("Showing star wars page.");
        dialogStage.show();

        InputStream inputStream = starWars.getIn();

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            String nextScene = getNextScene("[H", inputStream);
            if (nextScene != null) {
                textArea.setText(nextScene);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static String getNextScene(String delimeterPattern, InputStream in) {
        try {
            char lastChar = delimeterPattern.charAt(delimeterPattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            int ch = in.read();
            while (ch != -1) {
                sb.append((char) ch);
                if ((char) ch == lastChar && sb.toString().endsWith(delimeterPattern)) {
                    return sb.toString().replace(delimeterPattern, "");
                }
                ch = in.read();
            }
            if (ch == -1) {
                in.close();
                return sb.toString();
            }
        } catch (Exception e) {
            timeline.stop();
            StarWars.shutDown();
        }
        return null;
    }
}
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    @Subscribe
    public void handleRolodexChangedLocationEvent(RolodexChangedDirectoryEvent rcde) {
        setSaveLocation(rcde.getFilePath());
    }
```
###### \resources\view\StarWarsWindow.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.TextArea?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Font?>

<StackPane fx:id="starWarsWindowRoot" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <TextArea fx:id="textArea" editable="false" StackPane.alignment="CENTER">
      <font>
         <Font name="Consolas" size="14.0" />
      </font></TextArea>
</StackPane>
```
