# goweiwen
###### \java\seedu\address\logic\commands\AliasCommand.java
``` java
/**
 * Creates an alias for other commands.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_ADD_SUCCESS = "The alias \"%1$s\" now points to \"%2$s\".";
    public static final String MESSAGE_LIST_SUCCESS = "Aliases:\n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for other commands."
            + "Parameters: [ALIAS COMMAND]\n"
            + "Example: " + COMMAND_WORD + " create add\n"
            + "Example: " + COMMAND_WORD + " friends find t/friends\n";

    private final String alias;
    private final String command;

    public AliasCommand() {
        this.alias = null;
        this.command = null;
    }

    public AliasCommand(String alias, String command) {
        this.alias = alias;
        this.command = command;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        Aliases aliases = UserPrefs.getInstance().getAliases();

        if (alias == null && command == null) {
            StringBuilder output = new StringBuilder();

            for (String alias : aliases.getAllAliases()) {
                String command = aliases.getCommand(alias);
                output.append(String.format("%1$s=%2$s\n", alias, command));
            }
            return new CommandResult(String.format(MESSAGE_LIST_SUCCESS, output));
        }

        aliases.addAlias(alias, command);
```
###### \java\seedu\address\logic\commands\AliasCommand.java
``` java
        return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, alias, command));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && (this.alias == null || this.alias.equals(((AliasCommand) other).alias)) // state check
                && (this.command == null || this.command.equals(((AliasCommand) other).command))); // state check
    }

}
```
###### \java\seedu\address\logic\commands\UnaliasCommand.java
``` java
/**
 * Removes a previously defined alias.
 */
public class UnaliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unalias";
    public static final String MESSAGE_SUCCESS = "Deleted alias \"%1$s\".";
    public static final String MESSAGE_NO_SUCH_ALIAS = "Alias \"%1$s\" doesn't exist.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an alias previously defined."
            + "Parameters: ALIAS\n"
            + "Example: " + COMMAND_WORD + " create\n";

    private final String alias;

    public UnaliasCommand(String alias) {
        this.alias = alias;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Aliases aliases = UserPrefs.getInstance().getAliases();

        try {
            aliases.removeAlias(alias);
        } catch (NoSuchElementException e) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_ALIAS, alias));
        }
        //Text to Speech
        new TextToSpeech(String.format(MESSAGE_SUCCESS, alias));
        return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnaliasCommand // instanceof handles nulls
                && this.alias.equals(((UnaliasCommand) other).alias)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\AliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns an AliasCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String arguments) throws ParseException {

        if (arguments.length() == 0) {
            return new AliasCommand();
        }

        int delimiterPosition = arguments.trim().indexOf(' ');

        if (delimiterPosition == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        final String alias = arguments.trim().substring(0, delimiterPosition).trim();
        final String command = arguments.trim().substring(delimiterPosition + 1).trim();
        return new AliasCommand(alias, command);

    }

}
```
###### \java\seedu\address\logic\parser\HintParser.java
``` java
    private static final ArrayList<String> COMMAND_LIST = new ArrayList<>(Arrays.asList(
        "add", "alias", "clear", "delete", "edit", "exit", "find", "help", "history", "list",
        "music", "redo", "remark", "select", "unalias", "undo"
    ));

    /**
     * Parses {@code String input} and returns an appropriate autocompletion
     */
    public static String autocomplete(String input) {
        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return "";
        }

        userInput = input;
        commandWord = command[0];
        arguments = command[1];

        String hint;

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            AddCommandHint addCommandHint = new AddCommandHint(userInput, arguments);
            addCommandHint.parse();
            return addCommandHint.autocomplete();

        case EditCommand.COMMAND_WORD:
            EditCommandHint editCommandHint = new EditCommandHint(userInput, arguments);
            editCommandHint.parse();
            return editCommandHint.autocomplete();

        case FindCommand.COMMAND_WORD:
            FindCommandHint findCommandHint = new FindCommandHint(userInput, arguments);
            findCommandHint.parse();
            return findCommandHint.autocomplete();

        case DeleteCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
            DeleteCommandHint deleteCommandHint = new DeleteCommandHint(userInput, arguments);
            deleteCommandHint.parse();
            return deleteCommandHint.autocomplete();

        case MusicCommand.COMMAND_WORD:
            if (arguments.isEmpty()) {
                return commandWord + " " + (MusicCommand.isMusicPlaying() ? "pause" : "play");
            }
            hint = autocompleteFromList(arguments.trim(), new String[] {"play", "pause", "stop"});
            return commandWord + (hint != null ? " " + hint : arguments);

        default:
            hint = autocompleteCommand(commandWord);
            return hint != null ? hint + " " : input;
        }
    }

    /**
     * Parses {@code String command} and returns the closest matching command word, or null if nothing
     * matches.
     */
    public static String autocompleteCommand(String command) {
        List<String> commands = new ArrayList<>();
        // We add from COMMAND_LIST first because we want to autocomplete them first.
        commands.addAll(COMMAND_LIST);
        commands.addAll(UserPrefs.getInstance().getAliases().getAllAliases());
        String[] list = commands.toArray(new String[commands.size()]);
        return autocompleteFromList(command, list);
    }

    /**
     * Parses {@code String input} and returns the closest matching string in {@code String[] strings},
     * or null if nothing matches.
     */
    public static String autocompleteFromList(String input, String[] strings) {
        for (String string : strings) {
            if (string.startsWith(input)) {
                return string;
            }
        }
        return null;
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    private static final Map<String, Ikon> Icons = new HashMap<>();
    static {
        Icons.put(MusicCommand.COMMAND_WORD, Feather.FTH_PLAY);
        Icons.put(AddCommand.COMMAND_WORD, Feather.FTH_PLUS);
        Icons.put(AliasCommand.COMMAND_WORD, Feather.FTH_LINK);
        Icons.put(UnaliasCommand.COMMAND_WORD, Feather.FTH_CROSS);
        Icons.put(EditCommand.COMMAND_WORD, Feather.FTH_FILE_ADD);
        Icons.put(SelectCommand.COMMAND_WORD, Feather.FTH_HEAD);
        Icons.put(DeleteCommand.COMMAND_WORD, Feather.FTH_TRASH);
        Icons.put(ClearCommand.COMMAND_WORD, Feather.FTH_CROSS);
        Icons.put(FindCommand.COMMAND_WORD, Feather.FTH_SEARCH);
        Icons.put(ListCommand.COMMAND_WORD, Feather.FTH_PAPER);
        Icons.put(HistoryCommand.COMMAND_WORD, Feather.FTH_CLOCK);
        Icons.put(ExitCommand.COMMAND_WORD, Feather.FTH_POWER);
        Icons.put(HelpCommand.COMMAND_WORD, Feather.FTH_HELP);
        Icons.put(UndoCommand.COMMAND_WORD, Feather.FTH_ARROW_LEFT);
        Icons.put(RedoCommand.COMMAND_WORD, Feather.FTH_ARROW_RIGHT);
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code String command} and returns itself if it is a valid command.
     */
    public static String parseCommand(String command) {
        requireNonNull(command);
        switch (command) {
        case AddCommand.COMMAND_WORD:
        case AliasCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
        case ClearCommand.COMMAND_WORD:
        case FindCommand.COMMAND_WORD:
        case ListCommand.COMMAND_WORD:
        case HistoryCommand.COMMAND_WORD:
        case ExitCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
            return command;
        default:
            return null;
        }
    }

    /**
     * Parses {@code String command} and returns the corresponding {@code Ikon icon}
     * if valid.
     */
    public static Ikon parseIconCode(String command) {
        requireNonNull(command);
        return Icons.get(command);
    }

    /**
     * Parses {@code String userInput} and returns an array of {commandWord, arguments} if valid.
     */
    public static String[] parseCommandAndArguments(String userInput) throws ParseException {
        requireNonNull(userInput);

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");

        Aliases aliases = UserPrefs.getInstance().getAliases();
        String aliasedCommand = aliases.getCommand(commandWord);
        if (aliasedCommand != null) {
            final Matcher aliasMatcher = BASIC_COMMAND_FORMAT.matcher(aliasedCommand.trim());
            if (!aliasMatcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }

            commandWord = aliasMatcher.group("commandWord");
            arguments = aliasMatcher.group("arguments") + " " + arguments;
        }

        return new String[] {commandWord, arguments};
    }

}
```
###### \java\seedu\address\logic\parser\UnaliasCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnaliasCommand object
 */
public class UnaliasCommandParser implements Parser<UnaliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnaliasCommand
     * and returns an UnaliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnaliasCommand parse(String arguments) throws ParseException {
        String[] args = arguments.trim().split("\\s+");

        if (args.length != 1 || args[0].equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
        }

        return new UnaliasCommand(args[0]);
    }

}
```
###### \java\seedu\address\model\Aliases.java
``` java
/**
 * Contains a mapping of aliases to their commands, which is referred to when parsing commands.
 * When using the Alias and Unalias commands, key-value pairs are added and removed respectively.
 */
public class Aliases {

    private final Map<String, String> map = new HashMap<>();

    /*
     * We initialise the map with aliases for frequently used commands. Users can add other aliases themselves.
     */
    public Aliases() {
        map.put("new", AddCommand.COMMAND_WORD);
        map.put("create", AddCommand.COMMAND_WORD);
        map.put("remove", DeleteCommand.COMMAND_WORD);
        map.put("change", EditCommand.COMMAND_WORD);
        map.put("quit", ExitCommand.COMMAND_WORD);
        map.put("search", FindCommand.COMMAND_WORD);
        map.put("filter", FindCommand.COMMAND_WORD);
        map.put("man", HelpCommand.COMMAND_WORD);
        map.put("ls", ListCommand.COMMAND_WORD);
        map.put("show", ListCommand.COMMAND_WORD);
        map.put("song", MusicCommand.COMMAND_WORD);
        map.put("choose", SelectCommand.COMMAND_WORD);
        map.put("pick", SelectCommand.COMMAND_WORD);
    }

    /**
     * Returns a set of all aliases
     */
    public Set<String> getAllAliases() {
        return map.keySet();
    }

    /**
     * Returns the command linked to a specific alias, or null otherwise.
     */
    public String getCommand(String alias) {
        return map.get(alias);
    }

    /**
     * Adds or updates an alias to the map.
     */
    public void addAlias(String alias, String command) {
        map.put(alias, command);
    }

    /**
     * Removes an alias from the map.
     *
     * @throws NoSuchElementException if no such alias exists
     */
    public boolean removeAlias(String alias) throws NoSuchElementException {
        if (map.remove(alias) == null) {
            throw new NoSuchElementException();
        }
        return true;
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Aliases // instanceof handles nulls
                && this.map.equals(((Aliases) other).map));
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private static UserPrefs instance;

    private Aliases aliases;
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public UserPrefs() {
        if (instance == null) {
            instance = this;
        }

        this.setGuiSettings(500, 500, 0, 0);
    }

    public static UserPrefs getInstance() {
        if (instance == null) {
            return new UserPrefs();
        }
        return instance;
    }
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public Aliases getAliases() {
        if (aliases == null) {
            aliases = new Aliases();
        }
        return aliases;
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            keyEvent.consume();
            autocomplete();
            break;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Automatically completes user's input and replaces it in the command box.
     */
    private void autocomplete() {
        String input = commandTextField.getText();
        String autocompletion = HintParser.autocomplete(input);
        commandTextField.textProperty().set(autocompletion);
        commandTextField.positionCaret(autocompletion.length());
    }

    /**
     * Sets the command box style to match validity of the input. (valid -> default, invalid -> failed)
     */
    private void setStyleByValidityOfInput(String input) {
        if (input.equals("")) {
            return;
        }

        try {
            logic.parse(input);
        } catch (ParseException e) {
            setStyleToIndicateCommandFailure();
            return;
        }
        setStyleToDefault();
    }
```
###### \java\seedu\address\ui\CommandBoxIcon.java
``` java
/**
 * Panel that displays the additional details of a Person
 */
public class CommandBoxIcon extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "CommandBoxIcon.fxml";

    private Ikon iconCode = null;

    @FXML
    private FontIcon icon;

    public CommandBoxIcon() {
        super(FXML);
        icon.setVisible(false);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleCommandInputChangedEvent(CommandInputChangedEvent event) {
        String userInput = event.currentInput;

        String[] command;
        try {
            command = parseCommandAndArguments(userInput);
        } catch (ParseException e) {
            icon.setVisible(false);
            return;
        }

        String commandWord = command[0];
        Ikon iconCode = parseIconCode(commandWord);

        if (iconCode == null) {
            icon.setVisible(false);
            return;
        }

        icon.iconCodeProperty().set(iconCode);
        icon.setVisible(true);
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "CommandBoxIcon updated to " + iconCode.getDescription()));
    }

}
```
###### \resources\view\CommandBoxIcon.fxml
``` fxml
<StackPane alignment="BOTTOM_CENTER" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <FontIcon fx:id="icon" iconSize="32"/>
</StackPane>
```
