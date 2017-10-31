# goweiwen
###### \java\guitests\guihandles\CommandBoxHandle.java
``` java
    /**
     * Types the given command in the Command Box.
     */
    public void type(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();
    }
```
###### \java\guitests\guihandles\CommandBoxIconHandle.java
``` java
/**
 * A handle to the {@code CommandBoxIcon} in the GUI.
 */
public class CommandBoxIconHandle extends NodeHandle<FontIcon> {

    public static final String COMMAND_ICON_FIELD_ID = "#icon";

    public CommandBoxIconHandle(FontIcon icon) {
        super(icon);
    }

    /**
     * Returns the current icon code.
     */
    public Ikon getIconCode() {
        return getRootNode().getIconCode();
    }
}
```
###### \java\seedu\address\logic\commands\AliasCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AliasCommand}.
 */
public class AliasCommandTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private Model model;
    private AliasCommand aliasCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        aliasCommand = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        aliasCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_alias_addSuccess() throws Exception {
        assertCommandSuccess(
                aliasCommand,
                model,
                String.format(AliasCommand.MESSAGE_ADD_SUCCESS, LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD),
                model
        );

        assertTrue(UserPrefs.getInstance().getAliases().getCommand(LIST_COMMAND_ALIAS) == ListCommand.COMMAND_WORD);
    }

    @Test
    public void execute_alias_listSuccess() throws Exception {
        aliasCommand.execute();

        Command aliasListCommand = new AliasCommand();
        aliasListCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        Aliases aliases = UserPrefs.getInstance().getAliases();

        StringBuilder string = new StringBuilder("");
        for (String alias : aliases.getAllAliases()) {
            string.append(alias);
            string.append("=");
            string.append(aliases.getCommand(alias));
            string.append("\n");
        }

        assertCommandSuccess(
                aliasListCommand,
                model,
                String.format(AliasCommand.MESSAGE_LIST_SUCCESS, string),
                model
        );
    }

    @Test
    public void execute_alias_commandsEqual() throws Exception {
        aliasCommand.execute();

        final AddressBookParser parser = new AddressBookParser();

        Command firstCommand = parser.parseCommand(LIST_COMMAND_ALIAS);
        Command secondCommand = parser.parseCommand(ListCommand.COMMAND_WORD);
        assertEquals(firstCommand.getClass(), secondCommand.getClass());
    }

    @Test
    public void equals() {
        AliasCommand aliasFirstCommand = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        // same object -> returns true
        assertTrue(aliasFirstCommand.equals(aliasFirstCommand));

        // same values -> returns true
        AliasCommand aliasFirstCommandCopy = new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        assertTrue(aliasFirstCommand.equals(aliasFirstCommandCopy));

        // different types -> returns false
        assertFalse(aliasFirstCommand.equals(1));

        // null -> returns false
        assertFalse(aliasFirstCommand.equals(null));

        // different alias -> returns false
        AliasCommand aliasSecondCommand = new AliasCommand(LIST_COMMAND_ALIAS + "2", ListCommand.COMMAND_WORD);
        assertFalse(aliasFirstCommand.equals(aliasSecondCommand));

        // different command -> returns false
        AliasCommand aliasThirdCommand = new AliasCommand(LIST_COMMAND_ALIAS, FindCommand.COMMAND_WORD);
        assertFalse(aliasFirstCommand.equals(aliasThirdCommand));
    }

}
```
###### \java\seedu\address\logic\parser\AliasCommandParserTest.java
``` java
/**
 * Contains unit tests for {@code AliasCommandParser}.
 */
public class AliasCommandParserTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_noArgs_returnsAliasListCommand() {
        assertParseSuccess(parser, "", new AliasCommand());
    }

    @Test
    public void parse_validArgs_returnsAliasCommand() {
        assertParseSuccess(parser, LIST_COMMAND_ALIAS + " " + ListCommand.COMMAND_WORD,
                new AliasCommand(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\HintParserTest.java
``` java
    @Test
    public void autocomplete_emptyInput_returnsEmpty() {
        assertEquals("", autocomplete(""));
    }

    @Test
    public void autocomplete_invalidCommand_returnsItself() {
        assertEquals(
                "should-not-complete-to-any-commands",
                autocomplete("should-not-complete-to-any-commands"));
    }

    @Test
    public void autocomplete_incompleteCommand_returnsFullCommandAndTrailingSpace() {
        assertEquals(
                AddCommand.COMMAND_WORD + " ",
                autocomplete(AddCommand.COMMAND_WORD.substring(0, AddCommand.COMMAND_WORD.length() - 1)));
        assertEquals(
                ListCommand.COMMAND_WORD + " ",
                autocomplete(ListCommand.COMMAND_WORD.substring(0, ListCommand.COMMAND_WORD.length() - 1)));
    }

    @Test
    public void autocomplete_validCommands_returnsParameters() {
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " "));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " n"));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " n/"));

        assertEquals(EditCommand.COMMAND_WORD + " ", autocomplete(EditCommand.COMMAND_WORD));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 n"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 n/"));

        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD));
        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD + " n"));
        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD + " n/"));

        assertEquals(MusicCommand.COMMAND_WORD + " play", autocomplete(MusicCommand.COMMAND_WORD));
        assertEquals(MusicCommand.COMMAND_WORD + " pause", autocomplete(MusicCommand.COMMAND_WORD + " paus"));
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseCommand_validCommand_returnsCommand() throws Exception {
        assertEquals(ParserUtil.parseCommand(AddCommand.COMMAND_WORD), AddCommand.COMMAND_WORD);
        assertEquals(ParserUtil.parseCommand(ListCommand.COMMAND_WORD), ListCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_invalidCommand_returnsNull() throws Exception {
        assertNull(ParserUtil.parseCommand(""));
        assertNull(ParserUtil.parseCommand("invalid-command"));
    }

    @Test
    public void parseIconCode_validCommand_returnsCorrectIcon() throws Exception {
        assertEquals(ParserUtil.parseIconCode(AddCommand.COMMAND_WORD), Feather.FTH_PLUS);
        assertEquals(ParserUtil.parseIconCode(ListCommand.COMMAND_WORD), Feather.FTH_PAPER);
    }

    @Test
    public void parseIconCode_invalidCommand_returnsEmptyOptional() throws Exception {
        assertNull(ParserUtil.parseIconCode(""));
        assertNull(ParserUtil.parseIconCode("invalid-command"));
    }

    @Test
    public void parseCommandAndArguments_validCommandAndArgument_returnsCommandAndArgument() throws Exception {
        String[] commandAndArguments = {AddCommand.COMMAND_WORD, " arguments"};
        assertArrayEquals(ParserUtil.parseCommandAndArguments(AddCommand.COMMAND_WORD + " arguments"),
                commandAndArguments);
    }

    @Test
    public void parseCommandAndArguments_validCommandNoArgument_returnsCommandAndEmptyArgument() throws Exception {
        String[] commandAndArguments = {AddCommand.COMMAND_WORD, ""};
        assertArrayEquals(ParserUtil.parseCommandAndArguments(AddCommand.COMMAND_WORD), commandAndArguments);
    }

    @Test
    public void parseCommandAndArguments_emptyString_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCommandAndArguments("");
    }
}
```
###### \java\seedu\address\logic\parser\UnaliasCommandParserTest.java
``` java
/**
 * Contains unit tests for {@code UnaliasCommandParser}.
 */
public class UnaliasCommandParserTest {

    private static final String LIST_COMMAND_ALIAS = "show";

    private UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_validAlias_returnsUnaliasCommand() {
        assertParseSuccess(parser, LIST_COMMAND_ALIAS, new UnaliasCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "two arguments",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\AliasesTest.java
``` java
public class AliasesTest {
    private static final String LIST_COMMAND_ALIAS = "everyone";
    private static final String ADD_COMMAND_ALIAS = "someone";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Aliases aliases;

    @Before
    public void setUp() {
        aliases = new Aliases();
    }

    @Test
    public void getAllAliases_withAliases_returnsAllAliases() {
        Set<String> allAliases = new HashSet<>(aliases.getAllAliases());

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        aliases.addAlias(ADD_COMMAND_ALIAS, AddCommand.COMMAND_WORD);
        allAliases.add(LIST_COMMAND_ALIAS);
        allAliases.add(ADD_COMMAND_ALIAS);

        assertEquals(allAliases, aliases.getAllAliases());
    }

    @Test
    public void getCommand_validCommand_returnsCorrectCommand() {
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        assertEquals(ListCommand.COMMAND_WORD, aliases.getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void getCommand_invalidCommand_returnsNull() {
        assertNull(aliases.getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void addCommand_validCommand_addsCorrectAlias() {
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        assertEquals(ListCommand.COMMAND_WORD, aliases.getCommand(LIST_COMMAND_ALIAS));
    }

    @Test
    public void removeAlias_validCommand_removesAlias() {
        Set<String> allAliases = aliases.getAllAliases();
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertEquals(true, aliases.removeAlias(LIST_COMMAND_ALIAS));
        allAliases.remove(LIST_COMMAND_ALIAS);

        assertEquals(allAliases, aliases.getAllAliases());
    }

    @Test
    public void removeAlias_invalidCommand_throwsNoSuchElementException() {
        thrown.expect(NoSuchElementException.class);
        aliases.removeAlias(LIST_COMMAND_ALIAS);
    }

    @Test
    public void toString_withAliases_returnsCorrectString() {
        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        StringBuilder string = new StringBuilder("{");
        for (String alias : aliases.getAllAliases()) {
            string.append(alias);
            string.append("=");
            string.append(aliases.getCommand(alias));
            string.append(", ");
        }
        string.delete(string.length() - 2, string.length());
        string.append("}");

        assertEquals(string.toString(), aliases.toString());
    }

    @Test
    public void equals_sameSet_returnsTrue() {
        Aliases other = new Aliases();
        assertTrue(aliases.equals(other));

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        other.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertTrue(aliases.equals(other));
    }

    @Test
    public void equals_differentSet_returnsFalse() {
        Aliases other = new Aliases();

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertFalse(aliases.equals(other));
    }

    @Test
    public void hashCode_sameSet_returnsSameHashCode() {
        Aliases expectedAliases = new Aliases();
        assertEquals(aliases.hashCode(), expectedAliases.hashCode());

        aliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);
        expectedAliases.addAlias(LIST_COMMAND_ALIAS, ListCommand.COMMAND_WORD);

        assertEquals(aliases.hashCode(), expectedAliases.hashCode());
    }
}
```
###### \java\seedu\address\ui\CommandBoxIconTest.java
``` java
public class CommandBoxIconTest extends GuiUnitTest {

    private static final CommandInputChangedEvent COMMAND_INPUT_PARTIAL_ADD = new CommandInputChangedEvent("ad");
    private static final CommandInputChangedEvent COMMAND_INPUT_ADD = new CommandInputChangedEvent("add");

    private CommandBoxIconHandle commandBoxIconHandle;

    @Before
    public void setUp() {
        CommandBoxIcon commandBoxIcon = new CommandBoxIcon();
        uiPartRule.setUiPart(commandBoxIcon);

        commandBoxIconHandle = new CommandBoxIconHandle(getChildNode(commandBoxIcon.getRoot(),
                CommandBoxIconHandle.COMMAND_ICON_FIELD_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertNull(commandBoxIconHandle.getIconCode());

        // partial command entered
        postNow(COMMAND_INPUT_PARTIAL_ADD);
        guiRobot.pauseForHuman();
        assertNull(commandBoxIconHandle.getIconCode());

        // new command entered
        postNow(COMMAND_INPUT_ADD);
        guiRobot.pauseForHuman();
        assertEquals(Feather.FTH_PLUS, commandBoxIconHandle.getIconCode());
    }
}
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void commandBox_autocomplete() {
        guiRobot.push(KeyCode.TAB);
        assertEquals(HintParser.autocomplete(""), commandBoxHandle.getInput());

        commandBoxHandle.type(AddCommand.COMMAND_WORD);
        guiRobot.push(KeyCode.TAB);
        assertEquals(HintParser.autocomplete(AddCommand.COMMAND_WORD), commandBoxHandle.getInput());
    }
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    /**
     * Types a command that is invalid, then verifies that <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForInvalidCommand() {
        commandBoxHandle.type(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }

    /**
     * Types a command that is valid, then verifies that <br>
     *      - the command box's style is the same as {@code defaultStyleOfCommandBox}.
     */
    private void assertBehaviorForValidCommand() {
        commandBoxHandle.type(COMMAND_THAT_SUCCEEDS);
        assertEquals(defaultStyleOfCommandBox, commandBoxHandle.getStyleClass());
    }
```
