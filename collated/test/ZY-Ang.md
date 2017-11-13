# ZY-Ang
###### \java\seedu\address\commons\core\GuiSettingsTest.java
``` java
public class GuiSettingsTest {

    private GuiSettings guiSettings;

    @Before
    public void setUp() {
        guiSettings = new GuiSettings((double) 500, (double) 500, 0, 0);
    }

    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        assertTrue(guiSettings.equals(guiSettings));
    }

    @Test
    public void assertEqualsNotGuiSettingReturnsFalse() {
        assertFalse(guiSettings.equals(new Object()));
    }
}
```
###### \java\seedu\address\commons\core\MessagesTest.java
``` java
public class MessagesTest {

    @Test
    public void assertNonNullStaticUnknownCommand() {
        assertNotNull(MESSAGE_UNKNOWN_COMMAND);
        assertEquals(MESSAGE_UNKNOWN_COMMAND, new Messages().MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void assertNonNullStaticInvalidCommandFormat() {
        assertNotNull(MESSAGE_INVALID_COMMAND_FORMAT);
        assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, new Messages().MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void assertNonNullStaticInvalidExtensionFormat() {
        assertNotNull(MESSAGE_INVALID_EXTENSION_FORMAT);
        assertEquals(MESSAGE_INVALID_EXTENSION_FORMAT, new Messages().MESSAGE_INVALID_EXTENSION_FORMAT);
    }

    @Test
    public void assertNonNullStaticInvalidPersonDisplayedIndex() {
        assertNotNull(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, new Messages().MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void assertNonNullStaticPersonsListedOverview() {
        assertNotNull(MESSAGE_PERSONS_LISTED_OVERVIEW);
        assertEquals(MESSAGE_PERSONS_LISTED_OVERVIEW, new Messages().MESSAGE_PERSONS_LISTED_OVERVIEW);
    }
}
```
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    //---------------- Tests for levenshteinDistance --------------------------------------

    @Test
    public void testlevenshteinDistance() {
        // Assert additions and deletions
        assertEquals(levenshteinDistance("Bobby", "Bob"), 2);
        assertEquals(levenshteinDistance("Bobby", "Bob"), 2);
        assertEquals(levenshteinDistance("Alex", "Alexander"), 5);
        assertEquals(levenshteinDistance("Alex", "Alxe"), 2);

        // Assert substitutions
        assertEquals(levenshteinDistance(KEYWORD_MATCHING_MEIER, "Meyer") , 1);
        assertEquals(levenshteinDistance("Bazinga", "Bazingy"), 1);
        assertEquals(levenshteinDistance("Whoop", "Vroom"), 3);
        assertEquals(levenshteinDistance("substitution", "gajgbzbabzil"), "substitution".length());

        // Assert case-insensitivity
        assertEquals(levenshteinDistance("aaa", "AAA"), 0);
        assertEquals(levenshteinDistance("Alex", "alex"), 0);
        assertEquals(levenshteinDistance("ALEX", "alex"), 0);
    }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void updateSortComparator(List<SortArgument> sortArguments) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the rolodex and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Rolodex expectedRolodex = new Rolodex(actualModel.getRolodex());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getLatestPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedRolodex, actualModel.getRolodex());
            assertEquals(expectedFilteredList, actualModel.getLatestPersonList());
        }
    }
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s latest list to show only the first person in the {@code model}'s rolodex.
     */
    public static void sortAllPersons(Model model, SortArgument argument) {
        model.updateSortComparator(Arrays.asList(argument));
    }
```
###### \java\seedu\address\logic\commands\EditCommandTest.java
``` java
    @Test
    public void executeAllFieldsSpecifiedUnfilteredListSuccess() throws Exception {
        ReadOnlyPerson toBeEdited = model.getLatestPersonList().get(0);
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson)
                .build(toBeEdited.getTags());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new Rolodex(model.getRolodex()), new UserPrefs());
        expectedModel.updatePerson(toBeEdited, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeSomeFieldsSpecifiedUnfilteredListSuccess() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getLatestPersonList().size());
        ReadOnlyPerson toBeEdited = model.getLatestPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(toBeEdited);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build(toBeEdited.getTags());
        EditCommand editCommand = prepareCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new Rolodex(model.getRolodex()), new UserPrefs());
        expectedModel.updatePerson(toBeEdited, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void executeMultipleKeywordsSortedByNameDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_NAME_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByNameDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_NAME_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, ELLE, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByNameAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_NAME_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByPhoneDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_PHONE_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByPhoneDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_PHONE_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, ELLE));
    }

    @Test
    public void executeMultipleKeywordsSortedByPhoneAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_PHONE_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByEmailDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_EMAIL_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, ELLE));
    }

    @Test
    public void executeMultipleKeywordsSortedByEmailDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_EMAIL_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByEmailAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_EMAIL_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, ELLE));
    }

    @Test
    public void executeMultipleKeywordsSortedByAddressDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_ADDRESS_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, ELLE, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByAddressDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_ADDRESS_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByAddressAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_ADDRESS_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, ELLE, CARL));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {

        String[] keywords = userInput.split("\\s+");
        List<String> findKeywordList = new ArrayList<>();
        List<SortArgument> sortKeywordList = new ArrayList<>();

        try {
            setupArguments(keywords, findKeywordList, sortKeywordList, FindCommand.MESSAGE_USAGE);
        } catch (ParseArgsException e) {
            throw new AssertionError("Unable to parse arguments.", e);
        }
        FindCommand command = new FindCommand(
                new PersonDataContainsKeywordsPredicate(findKeywordList), sortKeywordList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
    private ListCommand listCommandNameDefault;
    private ListCommand listCommandNameDescending;
    private ListCommand listCommandNameAscending;
    private ListCommand listCommandPhoneDefault;
    private ListCommand listCommandPhoneDescending;
    private ListCommand listCommandPhoneAscending;
    private ListCommand listCommandEmailDefault;
    private ListCommand listCommandEmailDescending;
    private ListCommand listCommandEmailAscending;
    private ListCommand listCommandAddressDefault;
    private ListCommand listCommandAddressDescending;
    private ListCommand listCommandAddressAscending;
    private ListCommand listCommandRemarkDefault;
    private ListCommand listCommandRemarkDescending;
    private ListCommand listCommandRemarkAscending;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalRolodex(), new UserPrefs());
        expectedModel = new ModelManager(model.getRolodex(), new UserPrefs());

        listCommand = new ListCommand(new ArrayList<>());
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandNameDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_DEFAULT));
        listCommandNameDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandNameDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_DESCENDING));
        listCommandNameDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandNameAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_NAME_ASCENDING));
        listCommandNameAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandPhoneDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_DEFAULT));
        listCommandPhoneDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandPhoneDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_DESCENDING));
        listCommandPhoneDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandPhoneAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_PHONE_ASCENDING));
        listCommandPhoneAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandEmailDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_DEFAULT));
        listCommandEmailDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandEmailDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_DESCENDING));
        listCommandEmailDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandEmailAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_EMAIL_ASCENDING));
        listCommandEmailAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandAddressDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_DEFAULT));
        listCommandAddressDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandAddressDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_DESCENDING));
        listCommandAddressDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandAddressAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_ADDRESS_ASCENDING));
        listCommandAddressAscending.setData(model, new CommandHistory(), new UndoRedoStack());

        listCommandRemarkDefault = new ListCommand(Arrays.asList(SORT_ARGUMENT_REMARK_DEFAULT));
        listCommandRemarkDefault.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandRemarkDescending = new ListCommand(Arrays.asList(SORT_ARGUMENT_REMARK_DESCENDING));
        listCommandRemarkDescending.setData(model, new CommandHistory(), new UndoRedoStack());
        listCommandRemarkAscending = new ListCommand(Arrays.asList(SORT_ARGUMENT_REMARK_ASCENDING));
        listCommandRemarkAscending.setData(model, new CommandHistory(), new UndoRedoStack());
    }
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
    @Test
    public void executeListIsSortedByNameDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_DEFAULT);
        assertCommandSuccess(listCommandNameDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByNameDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_DESCENDING);
        assertCommandSuccess(listCommandNameDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByNameAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_NAME_ASCENDING);
        assertCommandSuccess(listCommandNameAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByPhoneDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_DEFAULT);
        assertCommandSuccess(listCommandPhoneDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByPhoneDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_DESCENDING);
        assertCommandSuccess(listCommandPhoneDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByPhoneAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_PHONE_ASCENDING);
        assertCommandSuccess(listCommandPhoneAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByEmailDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_DEFAULT);
        assertCommandSuccess(listCommandEmailDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByEmailDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_DESCENDING);
        assertCommandSuccess(listCommandEmailDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByEmailAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_EMAIL_ASCENDING);
        assertCommandSuccess(listCommandEmailAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByAddressDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_DEFAULT);
        assertCommandSuccess(listCommandAddressDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByAddressDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_DESCENDING);
        assertCommandSuccess(listCommandAddressDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByAddressAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_ADDRESS_ASCENDING);
        assertCommandSuccess(listCommandAddressAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
```
###### \java\seedu\address\logic\parser\CliSyntaxTest.java
``` java
public class CliSyntaxTest {

    @Test
    public void assertValidSortArgument() {
        assert isValidSortArgument(new SortArgument(PREFIX_NAME.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_PHONE.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_EMAIL.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_ADDRESS.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_REMARK.toString()));
        assert isValidSortArgument(new SortArgument(PREFIX_NAME.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_PHONE.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_EMAIL.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_REMARK.concat(POSTFIX_ASCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_NAME.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_PHONE.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_EMAIL.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_ADDRESS.concat(POSTFIX_DESCENDING)));
        assert isValidSortArgument(new SortArgument(PREFIX_REMARK.concat(POSTFIX_DESCENDING)));

        assertFalse(isValidSortArgument(new SortArgument("\n")));
        assertFalse(isValidSortArgument(new SortArgument("abcd")));
        assertFalse(isValidSortArgument(new SortArgument("hello")));
        assertFalse(isValidSortArgument(new SortArgument("r3c4q")));
        assertFalse(isValidSortArgument(new SortArgument("d/")));
        assertFalse(isValidSortArgument(new SortArgument("pp/")));
    }

    private boolean isValidSortArgument(SortArgument sortArgument) {
        return POSSIBLE_SORT_ARGUMENTS.contains(sortArgument);
    }
}
```
###### \java\seedu\address\logic\parser\CommandParserTestUtil.java
``` java
    public static final Set<Set<String>> POSSIBLE_COMMAND_ABBREVIATIONS = new HashSet<>(Arrays.asList(
            AddCommand.COMMAND_WORD_ABBREVIATIONS,
            ClearCommand.COMMAND_WORD_ABBREVIATIONS,
            DeleteCommand.COMMAND_WORD_ABBREVIATIONS,
            EditCommand.COMMAND_WORD_ABBREVIATIONS,
            ExitCommand.COMMAND_WORD_ABBREVIATIONS,
            FindCommand.COMMAND_WORD_ABBREVIATIONS,
            HelpCommand.COMMAND_WORD_ABBREVIATIONS,
            HistoryCommand.COMMAND_WORD_ABBREVIATIONS,
            ListCommand.COMMAND_WORD_ABBREVIATIONS,
            NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS,
            OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS,
            RedoCommand.COMMAND_WORD_ABBREVIATIONS,
            SelectCommand.COMMAND_WORD_ABBREVIATIONS,
            UndoCommand.COMMAND_WORD_ABBREVIATIONS,
            Suggestion.COMMAND_WORD_ABBREVIATIONS,
            StarWarsCommand.COMMAND_WORD_ABBREVIATIONS
    ));
```
###### \java\seedu\address\logic\parser\CommandParserTestUtil.java
``` java
    /**
     * Returns a list permutation of all pairs of Set of Strings in a Set of Set of Strings
     * @param set to be permuted
     */
    public static ArrayList<Pair<Set<String>, Set<String>>> generateCommandAbbreviationPermutations(Set<Set<String>>
                                                                                                            set) {
        ArrayList<Pair<Set<String>, Set<String>>> commandAbbreviationPairList = new ArrayList<>();
        ArrayList<Set<String>> stringList = new ArrayList<>(set);

        for (int i = 0; i < set.size(); i++) {
            for (int j = i + 1; j < set.size(); j++) {
                if (i != j) {
                    Pair<Set<String>, Set<String>> setPair = new Pair<>(stringList.get(i), stringList.get(j));
                    commandAbbreviationPairList.add(setPair);
                }
            }
        }

        return commandAbbreviationPairList;
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
    @Test
    public void parseArgumentsIndexInArgumentsReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 n/Bob Choo", EditCommandParser.parseArguments("edit", "-1 n/Bob Choo"));
        setLastRolodexSize.invoke(null, 8);
        assertEquals(" 8 n/someStringV4lue", EditCommandParser.parseArguments("modify", "8someStringV4lue"));
    }
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
    @Test
    public void parseArgumentsNoIndexFailueThrowsParseException() throws Exception {
        assertTrue(null == EmailCommandParser.parseArguments("mailto", "no index here!"));
        assertTrue(null == EmailCommandParser.parseArguments("send", "none here either!"));
    }

    @Test
    public void parseArgumentsIndexInArgumentsReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 s/some String V4lue", EmailCommandParser.parseArguments("email", "1some String V4lue"));
        setLastRolodexSize.invoke(null, 8);
        assertEquals(" 8 s/someStringV4lue", EmailCommandParser.parseArguments("mail", "8someStringV4lue"));
    }

    @Test
    public void parseArgumentsIndexInCommandWordReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 s/", EmailCommandParser.parseArguments("mail1", ""));
        setLastRolodexSize.invoke(null, 7);
        assertEquals(" 7 s/", EmailCommandParser.parseArguments("send7", ""));
    }
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parseValidSortArgsEmptyDataArgsThrowsParseException() {
        assertParseFailure(parser, " " + SORT_ARGUMENT_NAME_DESCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(
                        new PersonDataContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                        new ArrayList<>());
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parseArgumentsNullStringThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        FindCommandParser.parseArguments(null);
    }

    @Test
    public void parseArgumentsEmptyStringReturnsNull() {
        assertEquals(null, FindCommandParser.parseArguments(""));
        assertEquals(null, FindCommandParser.parseArguments("         "));
    }

    @Test
    public void parseArgumentsValidArgsReturnsSpaceAppendedArgs() {
        assertEquals(" some args", FindCommandParser.parseArguments("some args"));
        assertEquals(" some args", FindCommandParser.parseArguments("    some args"));
    }

```
###### \java\seedu\address\logic\parser\NewRolodexCommandParserTest.java
``` java
public class NewRolodexCommandParserTest {

    private NewRolodexCommandParser parser = new NewRolodexCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNonEmptyArgInvalidFilePathThrowsParseException() {
        // needs a root directory as reference for anchored paths (e.g. ./x.rldx)
        assertParseFailure(parser, "default.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFilePathInvalidExtensionThrowsFileExtensionParseException() {
        String filePathWithInvalidExtension = "C:\\Users\\Rolodex\\Downloads\\default.error";
        assertParseFailure(parser, filePathWithInvalidExtension,
                String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));

        filePathWithInvalidExtension = "directory with/some spacings/invalid.extension";
        assertParseFailure(parser, filePathWithInvalidExtension,
                String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));
    }

    @Test
    public void parseFilePathAllowsSpacing() {
        // spacing should be allowed for directories
        String filePathWithSpacing = "directory with/some spacings/default.rldx";
        NewRolodexCommand expectedOpenRolodexCommand = new NewRolodexCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenRolodexCommand);

        filePathWithSpacing = "56 75/22 3456g 45642345/y5/ bhh3 57y357/ 65467y5 - 0 o1/validExtension.rldx";
        expectedOpenRolodexCommand = new NewRolodexCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenRolodexCommand);
    }

    @Test
    public void parseFilePathSwitchesBackslashes() {
        // backslashes are swapped out for forward slashes in directory
        String filePathWithBackSlashes = "C:\\Users\\Rolodex\\Downloads\\default.rldx";
        String filePathWithForwardSlashes = "C:/Users/Rolodex/Downloads/default.rldx";

        NewRolodexCommand expectedOpenRolodexCommand = new NewRolodexCommand(filePathWithForwardSlashes);
        assertParseSuccess(parser, filePathWithBackSlashes, expectedOpenRolodexCommand);
    }
}
```
###### \java\seedu\address\logic\parser\OpenRolodexCommandParserTest.java
``` java
public class OpenRolodexCommandParserTest {

    private OpenRolodexCommandParser parser = new OpenRolodexCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseNonEmptyArgInvalidFilePathThrowsParseException() {
        // needs a root directory as reference for anchored paths (e.g. ./x.rldx)
        assertParseFailure(parser, "default.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "random.rldx",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseFilePathAllowsSpacing() {
        // spacing should be allowed for directories
        String filePathWithSpacing = "directory with/some spacings/default.rldx";
        OpenRolodexCommand expectedOpenRolodexCommand = new OpenRolodexCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenRolodexCommand);

        filePathWithSpacing = "56 75/22 3456g 45642345/y5/ bhh3 57y357/ 65467y5 - 0 o1.rldx";
        expectedOpenRolodexCommand = new OpenRolodexCommand(filePathWithSpacing);
        assertParseSuccess(parser, filePathWithSpacing, expectedOpenRolodexCommand);
    }

    @Test
    public void parseFilePathSwitchesBackslashes() {
        // backslashes are swapped out for forward slashes in directory
        String filePathWithBackSlashes = "C:\\Users\\Rolodex\\Downloads\\default.rldx";
        String filePathWithForwardSlashes = "C:/Users/Rolodex/Downloads/default.rldx";

        OpenRolodexCommand expectedOpenRolodexCommand = new OpenRolodexCommand(filePathWithForwardSlashes);
        assertParseSuccess(parser, filePathWithBackSlashes, expectedOpenRolodexCommand);
    }
}
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilAddressSuggestionTest.java
``` java
public class ParserUtilAddressSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableAddressTillEndAssertFalse() {
        assertFalse(isParsableAddressTillEnd("one two three")); // No block or characters
        assertFalse(isParsableAddressTillEnd("~!@# $%^&*()_+ ")); // symbols
        assertFalse(isParsableAddressTillEnd("0")); // zero
        assertFalse(isParsableAddressTillEnd("00")); // zero
        assertFalse(isParsableAddressTillEnd("2147483649")); // large numbers
        assertFalse(isParsableAddressTillEnd("PeterJack_1190@example.com ")); // emails with numbers
        assertFalse(isParsableAddressTillEnd("123@456.789 ")); // emails with numbers only
    }

    @Test
    public void isParsableAddressTillEndAssertTrue() {
        // add command
        assertTrue(isParsableAddressTillEnd(
                "add Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173"));
        // edit command
        assertTrue(isParsableAddressTillEnd(
                "edit Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18"));
        // remark command
        assertTrue(isParsableAddressTillEnd(
                "rmk Davis lives at a/Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a utility"));
        // mixed data
        assertTrue(isParsableAddressTillEnd("8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));
        // mixed data with name in front
        assertTrue(isParsableAddressTillEnd("Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31"));
    }

    @Test
    public void parseAddressTillEndAddCommandReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("972, Pansy Street, #08-12, 093173",
                "add Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173");
    }

    @Test
    public void parseAddressTillEndEditCommandReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("Blk 30 Lorong 3 Serangoon Gardens, #07-18",
                "edit Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18");
    }

    @Test
    public void parseAddressTillEndModifyCommandWithPostFixReturnsAddressWithPostfix() throws IllegalValueException {
        assertAddressTillEnd("Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a utility",
                "rmk Davis lives at a/Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a utility");
    }

    @Test
    public void parseAddressTillEndPhoneRemarkAddressReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("45 Aljunied Street 85, #11-31",
                "8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31");
    }

    @Test
    public void parseAddressTillEndNamePhoneAddressReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("45 Aljunied Street 85, #11-31",
                "Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31");
    }

    @Test
    public void parseAddressTillEndNamePhoneRemarkAddressReturnsAddress() throws IllegalValueException {
        assertAddressTillEnd("45 Aljunied Street 85, #11-31",
                "Lecter Goh 8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31");
    }

    @Test
    public void parseAddressTillEndIntegerStartWithWhitespaceReturnsTrimmedAddress() throws IllegalValueException {
        assertAddressTillEnd("123", "123 ");
    }

    /**
     * Attempts to parse a String containing an address to the end of the String
     * and assert the expected value and validity of the address.
     */
    public void assertAddressTillEnd(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseAddressTillEnd(input));
        Optional possibleAddress = parseAddress(Optional.of(parseAddressTillEnd(input)));
        assertTrue(possibleAddress.isPresent() && possibleAddress.get() instanceof Address);
    }

    @Test
    public void parseAddressTillEndNoBlockNumberThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("one two three Madison Avenue");
    }

    @Test
    public void parseAddressTillEndSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("~!@# $%^&*()_+");
    }

    @Test
    public void parseAddressTillEndIntegersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("123");
    }

    @Test
    public void parseAddressTillEndLargeIntegersWithWhitespaceThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("1233426256 ");
    }

    @Test
    public void parseAddressTillEndIntegersInEmailThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("PeterJack_1190@example.com ");
    }

    @Test
    public void parseAddressTillEndIntegersInEmailOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseAddressTillEnd("123@456.789 ");
    }

    @Test
    public void parseRemoveAddressTillEndRemovesAddressTillEnd() {
        assertEquals("add Adam Brown classmates 11111111 adam@gmail.com",
                parseRemoveAddressTillEnd(
                        "add Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173"));

        assertEquals("edit Bernice Yu 99272758",
                parseRemoveAddressTillEnd(
                        "edit Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18"));

        assertEquals("rmk Davis lives at",
                parseRemoveAddressTillEnd(
                        "rmk Davis lives at a/Blk 436 Serangoon Gardens Street 26, #16-43 p/12358576 this is a"
                                + " utility"));

        assertEquals("8314568937234 Welcome to Rolodex",
                parseRemoveAddressTillEnd(
                        "8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));

        assertEquals("Lecter Goh 8314568937234",
                parseRemoveAddressTillEnd(
                        "Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31"));

        assertEquals("Lecter Goh 8314568937234 Welcome to Rolodex",
                parseRemoveAddressTillEnd(
                        "Lecter Goh 8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));

        assertEquals("", parseRemoveAddressTillEnd("123 "));
    }
}
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilEmailSuggestionTest.java
``` java
public class ParserUtilEmailSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstEmailAssertFalse() {
        assertFalse(isParsableEmail("aloha")); // characters only
        assertFalse(isParsableEmail("+(&%*$^&*")); // symbols only
        assertFalse(isParsableEmail("123")); // numbers
        assertFalse(isParsableEmail("prefix/abc+@gm.com")); // invalid characters before @
        assertFalse(isParsableEmail("add me e/abc+@gmab.com")); // invalid characters
        assertFalse(isParsableEmail("@email")); // missing local part
        assertFalse(isParsableEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(isParsableEmail("peterjack@")); // missing domain name
    }

    @Test
    public void isParsableFirstEmailAssertTrue() {
        assertTrue(isParsableEmail("e/PeterJack_1190@example.com/postfix"));
        assertTrue(isParsableEmail("addemail/a@b/postfix")); // minimal
        assertTrue(isParsableEmail("test@localhost/postfix")); // alphabets only
        assertTrue(isParsableEmail("123@145/postfix")); // numeric local part and domain name
        assertTrue(isParsableEmail("e/a1@example1.com")); // mixture of alphanumeric and dot characters
        assertTrue(isParsableEmail("_user_@_e_x_a_m_p_l_e_.com_")); // underscores
        assertTrue(isParsableEmail("add peter_jack@very_very_very_long_example.com something")); // long domain name
        assertTrue(isParsableEmail("email if.you.dream.it_you.can.do.it@example.com")); // long local part
    }

    @Test
    public void parseFirstEmailSlashedPrefixReturnsStandardEmail() throws IllegalValueException {
        assertFirstEmail("a1@example1.com", "e/a1@example1.com");
    }

    @Test
    public void parseFirstEmailSlashedPrefixPostfixReturnsStandardEmail() throws IllegalValueException {
        assertFirstEmail("PeterJack_1190@example.com", "e/PeterJack_1190@example.com/postfix");
    }

    @Test
    public void parseFirstEmailMinimalPrefixPostfixReturnsMinimalEmail() throws IllegalValueException {
        assertFirstEmail("a@b", "addemail/a@b/postfix");
    }

    @Test
    public void parseFirstEmailNumberEmailPostfixReturnsNumberEmail() throws IllegalValueException {
        assertFirstEmail("123@145", "123@145/postfix");
    }

    @Test
    public void parseFirstEmailUnderscoreEmailPostfixReturnsUnderscoreEmail() throws IllegalValueException {
        assertFirstEmail("_user_@_e_x_a_m_p_l_e_.com_", "_user_@_e_x_a_m_p_l_e_.com_");
    }

    @Test
    public void parseFirstEmailGibberishAddCommandReturnsEmail() throws IllegalValueException {
        assertFirstEmail("peter_jack@very_very_very_long_example.com",
                "add peter_jack@very_very_very_long_example.com something");
    }

    @Test
    public void parseFirstEmailGibberishEmailCommandReturnsEmail() throws IllegalValueException {
        assertFirstEmail("if.you.dream.it_you.can.do.it@example.com",
                "email if.you.dream.it_you.can.do.it@example.com");
    }

    /**
     * Attempts to parse a email String and assert the expected value and validity of the email.
     */
    public void assertFirstEmail(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseFirstEmail(input));
        Optional possibleEmail = parseEmail(Optional.of(parseFirstEmail(input)));
        assertTrue(possibleEmail.isPresent() && possibleEmail.get() instanceof Email);
    }

    @Test
    public void parseFirstEmailCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("aloha");
    }

    @Test
    public void parseFirstEmailSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("+(&%*$^&*");
    }

    @Test
    public void parseFirstEmailNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("123");
    }

    @Test
    public void parseFirstEmailInvalidCharactersBeforeAtThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("prefix/abc+@gm.com");
    }

    @Test
    public void parseFirstEmailInvalidCharactersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("add me e/abc+@gmab.com");
    }

    @Test
    public void parseFirstEmailMissingLocalThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("@email");
    }

    @Test
    public void parseFirstEmailMissingAtThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("peterjackexample.com");
    }

    @Test
    public void parseFirstEmailMissingDomainThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstEmail("peterjack@");
    }

    @Test
    public void parseRemoveFirstEmailRemovesFirstEmail() {
        assertEquals("delete second def@ghi", parseRemoveFirstEmail("delete abc@def.com second def@ghi"));
        assertEquals("de", parseRemoveFirstEmail("de abcdef@g"));
        assertEquals("", parseRemoveFirstEmail("ab@de"));
        assertEquals("Not_an_email+@abn.de", parseRemoveFirstEmail("Not_an_email+@abn.de"));
        assertEquals("", parseRemoveFirstEmail("a@abn.de"));
        assertEquals("nothing here", parseRemoveFirstEmail("nothing here"));
    }
}
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilFilePathSuggestionTest.java
``` java
public class ParserUtilFilePathSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstFilePathAssertFalse() {
        assertFalse(isParsableFilePath("one two three")); // characters
        assertFalse(isParsableFilePath("~!@# $%^&*()_+")); // symbols
        assertFalse(isParsableFilePath("2147483649")); // numbers
        assertFalse(isParsableFilePath("////")); // empty directory
        assertFalse(isParsableFilePath("C:/")); // empty directory, with absolute prefix
        assertFalse(isParsableFilePath("C:////")); // empty directory, with absolute prefix
        assertFalse(isParsableFilePath("var////")); // empty directory, with relative prefix
        assertFalse(isParsableFilePath("var\\\\\\\\")); // empty directory, backslashes
    }

    @Test
    public void isParsableFirstFilePathAssertTrue() {
        assertTrue(isParsableFilePath("data/default.rldx"));
        assertTrue(isParsableFilePath("Prefix data/default"));
        assertTrue(isParsableFilePath("C:/Users/Downloads/my.rldx postfix"));
        assertTrue(isParsableFilePath("Some prefix C:/abc.rldx"));
        assertTrue(isParsableFilePath("pref  214/748/36/49 postfix"));
        assertTrue(isParsableFilePath("data////r"));
        assertTrue(isParsableFilePath("C:////g postfix"));
        assertTrue(isParsableFilePath("prefix var////f"));
        assertTrue(isParsableFilePath("prefix var\\a\\b\\c\\"));
    }

    @Test
    public void parseFirstFilePathWhitespacePrefixExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("data/default.rldx", " data/default.rldx");
    }

    @Test
    public void parseFirstFilePathNoExtensionReturnsFilePathWithExtension() throws IllegalArgumentException {
        assertFilePath("pref  214/748/36/49 postfix.rldx", "pref  214/748/36/49 postfix");
    }

    @Test
    public void parseFirstFilePathStringPrefixNoExtensionReturnsPrefixFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("Prefix data/default.rldx", "Prefix data/default");
    }

    @Test
    public void parseFirstFilePathMidFileExtensionReturnsFilePathWithReplaceEndedExtension()
            throws IllegalArgumentException {
        assertFilePath("C:/Users/Downloads/my rolodex.rldx", "C:/Users/Downloads/my.rldx rolodex");
    }

    @Test
    public void parseFirstFilePathStringPrefixExtensionReturnsInput() throws IllegalArgumentException {
        assertFilePath("Some prefix C:/abc.rldx", "Some prefix C:/abc.rldx");
    }

    @Test
    public void parseFirstFilePathRelativeEmptyFolderHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("data////r.rldx", "data////r");
    }

    @Test
    public void parseFirstFilePathAbsoluteEmptyFolderHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("C:////g.rldx", "C:////g");
    }

    @Test
    public void parseFirstFilePathAbsoluteEmptyFolderFinalHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("C:////g.rldx", "C:////g/");
    }

    @Test
    public void parseFirstFilePathRootRelativeEmptyFolderHierarchyNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("var////f.rldx", "var////f");
    }

    @Test
    public void parseFirstFilePathRootRelativeEmptyFolderHierarchyBackslashNoExtensionReturnsFilePathWithExtension()
            throws IllegalArgumentException {
        assertFilePath("var/a/b/c.rldx", "var\\a\\b\\c\\");
    }

    /**
     * Attempts to parse a filepath String and assert the expected value and validity of the file.
     */
    public void assertFilePath(String expected, String input) {
        assertEquals(expected, parseFirstFilePath(input));
        String possibleFilepath = parseFirstFilePath(input);
        assertTrue(isValidRolodexStorageFilepath(possibleFilepath));
        assertTrue(isValidRolodexStorageExtension(possibleFilepath));
    }

    @Test
    public void parseFirstFilePathCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("one two three");
    }

    @Test
    public void parseFirstFilePathSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("~!@# $%^&*()_+");
    }

    @Test
    public void parseFirstFilePathNumbersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("2147483649");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithAbsolutePrefixThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("C:/");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithAbsolutePrefixLongThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("C:////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithRelativePrefixThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("var////");
    }

    @Test
    public void parseFirstFilePathEmptyDirectoryWithRelativePrefixBackslashesThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstFilePath("var\\\\\\\\");
    }
}
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilIndexSuggestionTest.java
``` java
public class ParserUtilIndexSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstIndexAssertFalse() {
        assertFalse(isParsableIndex("one two three", 4)); // characters
        assertFalse(isParsableIndex("~!@# $%^&*()_+", Integer.MAX_VALUE)); // symbols
        assertFalse(isParsableIndex("0", Integer.MAX_VALUE)); // zero
        assertFalse(isParsableIndex("00", Integer.MAX_VALUE)); // zero
        assertFalse(isParsableIndex("51", 50)); // large numbers > rolodex size
        assertFalse(isParsableIndex("2147483649", Integer.MAX_VALUE)); // large numbers > 2^31
    }

    @Test
    public void isParsableFirstIndexAssertTrue() {
        assertTrue(isParsableIndex("abc 1", 1)); // word then number
        assertTrue(isParsableIndex("del1", 1)); // word then number, without spacing
        assertTrue(isParsableIndex("add n/ 8 to Rolodex", 8)); // characters then number, then characters
        assertTrue(isParsableIndex("-1, acba", 1)); // negative numbers
    }

    @Test
    public void parseFirstIndexReturnsNonZeroPositiveInteger() throws Exception {
        assertEquals(parseFirstIndex("abc 1", 1), 1); // word then number
        assertEquals(parseFirstIndex("del1", 1), 1); // word then number, without spacing
        assertEquals(parseFirstIndex("add n/ 8 to Rolodex", 8), 8); // characters then number, then characters
        assertEquals(parseFirstIndex("-1, acba", 1), 1); // negative numbers
        assertEquals(parseFirstIndex("-7", 7), 7); // negative numbers
    }

    @Test
    public void parseFirstIndexCharactersOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("one two three", 4);
    }

    @Test
    public void parseFirstIndexSymbolsOnlyThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("~!@# $%^&*()_+", Integer.MAX_VALUE);
    }

    @Test
    public void parseFirstIndexExceedLimitsThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("51", 50);
    }

    @Test
    public void parseFirstIndexZeroThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("0", Integer.MAX_VALUE);
    }

    @Test
    public void parseFirstIndexLargeNumbersThrowsNumberFormatException() {
        thrown.expect(NumberFormatException.class);
        parseFirstIndex("2147483649", Integer.MAX_VALUE);
    }

    @Test
    public void parseRemoveFirstIndexRemovesFirstIndex() {
        assertEquals("delete second2", parseRemoveFirstIndex("delete1second2", 1));
        assertEquals("delete second2", parseRemoveFirstIndex("delete1 second2", 1));
        assertEquals("de", parseRemoveFirstIndex("de2", 2));
        assertEquals("2 3 4 5 6 7", parseRemoveFirstIndex("1 2 3 4 5 6 7", 1));
        assertEquals("", parseRemoveFirstIndex("1234567", 1234567));
        assertEquals("nothing here", parseRemoveFirstIndex("nothing here", Integer.MAX_VALUE));
    }
}
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilNameSuggestionTest.java
``` java
public class ParserUtilNameSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableNameAssertFalse() {
        assertFalse(isParsableName("")); // Blank
        assertFalse(isParsableName("        ")); // Whitespace
        assertFalse(isParsableName("~!@# $%^&*()_+ ")); // symbols
    }

    @Test
    public void isParsableNameAssertTrue() {
        assertTrue(isParsableName("one two three")); // Characters
        assertTrue(isParsableName("2147483649")); // Numbers
        assertTrue(isParsableName("PeterJack_1190@example.com ")); // emails
        assertTrue(isParsableName("Adam Brown classmates 11111111 adam@gmail.com 972, Pansy Street, #08-12, 093173"));
        assertTrue(isParsableName("Bernice Yu 99272758 Blk 30 Lorong 3 Serangoon Gardens, #07-18"));
        assertTrue(isParsableName("&$Davis lives at a/Blk 436 Serangoon Gardens Street"));
        assertTrue(isParsableName("8314568937234 Welcome to Rolodex 45 Aljunied Street 85, #11-31"));
        assertTrue(isParsableName("Lecter Goh 8314568937234 45 Aljunied Street 85, #11-31"));
    }

    @Test
    public void parseRemainingNameWhiteSpaceCharactersOnlyReturnsRemainingNameTrimmed() throws IllegalValueException {
        assertName("one two three", " one two three");
    }

    @Test
    public void parseRemainingNamePrefixCharactersOnlyReturnsRemainingNameWithPrefix() throws IllegalValueException {
        assertName("one two three", "n/one two three");
    }

    /**
     * Attempts to parse a name String and assert the expected value and validity of the name.
     */
    public void assertName(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseRemainingName(input));
        Optional possibleName = parseName(Optional.of(parseRemainingName(input)));
        assertTrue(possibleName.isPresent() && possibleName.get() instanceof Name);
    }

    @Test
    public void parseNameBlankStringThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseRemainingName("");
    }

    @Test
    public void parseNameWhitespaceStringThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseRemainingName("           ");
    }

    @Test
    public void parseNameSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseRemainingName("~!@# $%^&*()_+ ");
    }
}
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilPhoneSuggestionTest.java
``` java
public class ParserUtilPhoneSuggestionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isParsableFirstPhoneAssertFalse() {
        assertFalse(isParsablePhone("aloha")); // characters only
        assertFalse(isParsablePhone("+(&%*$^&*")); // symbols only
        assertFalse(isParsablePhone("123")); // short numbers
        assertFalse(isParsablePhone("+123")); // short numbers, with +
        assertFalse(isParsablePhone("add me p/+onetwo"));
        assertFalse(isParsablePhone("fasdfe+624"));
        assertFalse(isParsablePhone("015431asd")); // exactly 6 numbers
        assertFalse(isParsablePhone("+015431words")); // exactly 6 numbers, with +
        assertFalse(isParsablePhone("asda 0154361")); // starts with 0
        assertFalse(isParsablePhone("3rfsdf +0154361")); // starts with 0, with +
    }

    @Test
    public void isParsableFirstPhoneAssertTrue() {
        assertTrue(isParsablePhone("prefix9154361postfix")); // exactly 7 numbers
        assertTrue(isParsablePhone("prefix 93121534"));
        assertTrue(isParsablePhone("p/124293842033123postfix")); // long phone numbers
        assertTrue(isParsablePhone("prefix +9154361asd")); // exactly 7 numbers, with +
        assertTrue(isParsablePhone("prefix+93121534postfix")); // with +
        assertTrue(isParsablePhone(" prefix+124293842033123asd")); // long phone numbers, with +
        assertTrue(isParsablePhone(" 123+124293842033123asd")); // short numbers then long phone numbers, with +
    }

    @Test
    public void parseFirstPhoneStringPrefixReturnsPhone() throws IllegalValueException {
        assertFirstPhone("93121534", "prefix 93121534");
    }

    @Test
    public void parseFirstPhoneStringPrefixStringPostfixReturnsPhone() throws IllegalValueException {
        assertFirstPhone("9154361", "prefix9154361postfix");
    }

    @Test
    public void parseFirstPhoneStringSpacedPrefixStringPostfixReturnsPhone() throws IllegalValueException {
        assertFirstPhone("124293842033123", "prefix 124293842033123postfix");
    }

    @Test
    public void parseFirstPhoneStringPlusPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+93121534", "prefix+93121534postfix");
    }

    @Test
    public void parseFirstPhoneStringSpacePlusPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+9154361", "prefix +9154361asd");
    }

    @Test
    public void parseFirstPhoneSpaceStringPlusPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+124293842033123", "prefix+124293842033123asd");
    }

    @Test
    public void parseFirstPhoneShortNumberPrefixStringPostfixReturnsPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+124293842033123", "123+124293842033123asd");
    }

    @Test
    public void parseFirstPhoneTwoPhonesWithPlusReturnsFirstPhoneWithPlus() throws IllegalValueException {
        assertFirstPhone("+123456789", "firstPhone: +123456789 secondPhone: +987654321");
    }

    /**
     * Attempts to parse a phone String and assert the expected value and validity of the phone.
     */
    public void assertFirstPhone(String expected, String input) throws IllegalValueException {
        assertEquals(expected, parseFirstPhone(input));
        Optional possiblePhone = parsePhone(Optional.of(parseFirstPhone(input)));
        assertTrue(possiblePhone.isPresent() && possiblePhone.get() instanceof Phone);
    }

    @Test
    public void parseFirstPhoneCharactersOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("aloha");
    }

    @Test
    public void parseFirstPhoneSymbolsOnlyThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("+(&%*$^&*");
    }

    @Test
    public void parseFirstPhoneShortNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("123");
    }

    @Test
    public void parseFirstPhoneShortNumbersWithPlusThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("+123");
    }

    @Test
    public void parseFirstPhoneSixNumbersThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("015431asd");
    }

    @Test
    public void parseFirstPhoneSixNumbersWithPlusThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("+015431words");
    }

    @Test
    public void parseFirstPhoneStartsWithZeroThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("asda 0154361");
    }

    @Test
    public void parseFirstPhoneStartsWithZeroWithPlusThrowsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        parseFirstPhone("3rfsdf +0154361");
    }

    @Test
    public void parseRemoveFirstPhoneRemovesFirstPhone() {
        assertEquals("delete second+20154361", parseRemoveFirstPhone("delete +90154361second+20154361"));
        assertEquals("de", parseRemoveFirstPhone("de10154361"));
        assertEquals("35015431 3 35015431 5 7", parseRemoveFirstPhone("35015431 35015431 3 35015431 5 7"));
        assertEquals("", parseRemoveFirstPhone("1234567"));
        assertEquals("", parseRemoveFirstPhone("+1234567"));
        assertEquals("nothing here", parseRemoveFirstPhone("nothing here"));
    }
}
```
###### \java\seedu\address\logic\parser\PostfixTest.java
``` java
public class PostfixTest {

    @Test
    public void assertEqualsNotPostfixReturnsFalse() {
        assertFalse(new Postfix("abcd").equals(new Object()));
        assertFalse(new Postfix("abcd").equals(new Prefix("abcd")));
    }

    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        Postfix postfix = new Postfix("abcd");
        assertTrue(postfix.equals(postfix));
    }

    @Test
    public void assertEqualsSameStringValueDifferentInstanceReturnsTrue() {
        assertTrue(new Postfix("abcd").equals(new Postfix("abcd")));
    }

    @Test
    public void assertHashCodeNullStringReturnZero() {
        assertEquals(new Postfix(null).hashCode(), 0);
    }

    @Test
    public void assertHashCodeSameStringValueDifferentInstanceReturnsTrue() {
        assertTrue(new Postfix("abcd").hashCode() == new Postfix("abcd").hashCode());
    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
    @Test
    public void parseArgumentsNoIndexFailueThrowsParseException() throws Exception {
        assertTrue(null == RemarkCommandParser.parseArguments("rmk", "no index here!"));
        assertTrue(null == RemarkCommandParser.parseArguments("remark", "none here either!"));
    }

    @Test
    public void parseArgumentsIndexInArgumentsReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 r/someStringV4lue", RemarkCommandParser.parseArguments("rmk", "1someStringV4lue"));
        setLastRolodexSize.invoke(null, 8);
        assertEquals(" 8 r/someStringV4lue", RemarkCommandParser.parseArguments("rmk", "8someStringV4lue"));
    }

    @Test
    public void parseArgumentsIndexInCommandWordReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 r/", RemarkCommandParser.parseArguments("rmk1", ""));
        setLastRolodexSize.invoke(null, 7);
        assertEquals(" 7 r/", RemarkCommandParser.parseArguments("rmk7", ""));
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        for (String abbreviation : ClearCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof ClearCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandRemark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommandExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        for (String abbreviation : ExitCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof ExitCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonDataContainsKeywordsPredicate(keywords), new ArrayList<>()), command);
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        for (String abbreviation : HelpCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof HelpCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandHistory() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        for (String abbreviation : HistoryCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof HistoryCommand);
        }

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandList() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + SORT_ARGUMENT_NAME_DESCENDING) instanceof ListCommand);
        for (String abbreviation : ListCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof ListCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandOpen() throws Exception {
        String validRolodexFile = "valid/filePath/valid.rldx";
        OpenRolodexCommand command = (OpenRolodexCommand) parser.parseCommand(
                OpenRolodexCommand.COMMAND_WORD + " " + validRolodexFile);
        assertEquals(new OpenRolodexCommand(validRolodexFile), command);
    }

    @Test
    public void parseCommandNew() throws Exception {
        String validRolodexFile = "valid/filePath/valid.rldx";
        NewRolodexCommand command = (NewRolodexCommand) parser.parseCommand(
                NewRolodexCommand.COMMAND_WORD + " " + validRolodexFile);
        assertEquals(new NewRolodexCommand(validRolodexFile), command);
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandRedoCommandWordReturnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
        for (String abbreviation : RedoCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof RedoCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandUndoCommandWordReturnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
        for (String abbreviation : UndoCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof UndoCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandStarWarsCommandWordReturnsStarWarsCommand() throws Exception {
        assertTrue(parser.parseCommand(StarWarsCommand.COMMAND_WORD) instanceof StarWarsCommand);
        assertTrue(parser.parseCommand("starwars 4") instanceof StarWarsCommand);
        for (String abbreviation : StarWarsCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof StarWarsCommand);
        }
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandListInvalidArgumentThrowsSuggestibleParseException() throws Exception {
        thrown.expect(SuggestibleParseException.class);
        thrown.expectMessage(String.format(MESSAGE_PROMPT_COMMAND, ListCommand.COMMAND_WORD));
        parser.parseCommand(ListCommand.COMMAND_WORD + " 3");
        parser.parseCommand(ListCommand.COMMAND_WORD + " Bazinga");
    }

    @Test
    public void parseAllCommandAbbreviationsAreDisjoint() {
        ArrayList<Pair<Set<String>, Set<String>>> commandAbbreviationPermutations =
                generateCommandAbbreviationPermutations(POSSIBLE_COMMAND_ABBREVIATIONS);
        for (Pair<Set<String>, Set<String>> commandAbbreviationPair : commandAbbreviationPermutations) {
            assertTrue(Collections.disjoint(commandAbbreviationPair.getKey(), commandAbbreviationPair.getValue()));
        }
    }
```
###### \java\seedu\address\model\person\PersonCompareTest.java
``` java
public class PersonCompareTest {

    private Set<ReadOnlyPerson> personSet = new HashSet<>();
    private ArrayList<Pair<ReadOnlyPerson, ReadOnlyPerson>> personPermutations = new ArrayList<>();

    @Before
    public void setUp() {
        personSet.addAll(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA));
        personPermutations = generatePersonPermutations(personSet);
    }

    @Test
    public void assertCompareToPersonInvalidSortArgument() {
        for (Pair<ReadOnlyPerson, ReadOnlyPerson> personPair : personPermutations) {
            assertEquals(personPair.getKey().compareTo(personPair.getValue(), new SortArgument("InvalidSorter")),
                    personPair.getKey().compareTo(personPair.getValue()));
            assertEquals(personPair.getValue().compareTo(personPair.getKey(), new SortArgument("InvalidSorter")),
                    personPair.getValue().compareTo(personPair.getKey()));
        }
    }

    @Test
    public void assertHashCode() {
        for (ReadOnlyPerson person : personSet) {
            assertEquals(person.hashCode(),
                    Objects.hash(person.nameProperty(),
                            person.phoneProperty(),
                            person.emailProperty(),
                            person.addressProperty(),
                            person.remarkProperty(),
                            person.tagProperty()));
        }
    }

    /**
     * Returns a list permutation of all pairs of persons in a set
     * @param set to be permuted
     */
    private static ArrayList<Pair<ReadOnlyPerson, ReadOnlyPerson>> generatePersonPermutations(Set<ReadOnlyPerson> set) {
        ArrayList<Pair<ReadOnlyPerson, ReadOnlyPerson>> personPairList = new ArrayList<>();
        ArrayList<ReadOnlyPerson> personList = new ArrayList<>(set);

        for (int i = 0; i < set.size(); i++) {
            for (int j = i + 1; j < set.size(); j++) {
                if (i != j) {
                    Pair<ReadOnlyPerson, ReadOnlyPerson> personPair = new Pair<>(personList.get(i), personList.get(j));
                    personPairList.add(personPair);
                }
            }
        }

        return personPairList;
    }
}
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 7 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("0154361")); // starts with 0
        assertFalse(Phone.isValidPhone("+91")); // less than 7 numbers, with +
        assertFalse(Phone.isValidPhone("+0154361")); // starts with 0, with +

        // valid phone numbers
        assertTrue(Phone.isValidPhone("9154361")); // exactly 7 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
        assertTrue(Phone.isValidPhone("+9154361")); // exactly 7 numbers, with +
        assertTrue(Phone.isValidPhone("+93121534")); // with +
        assertTrue(Phone.isValidPhone("+124293842033123")); // long phone numbers, with +
    }
```
###### \java\seedu\address\model\UserPrefsTest.java
``` java
public class UserPrefsTest {

    private GuiSettings guiSettings;
    private String rolodexName;
    private UserPrefs userPrefs;

    @Before
    public void setUp() {
        // Set up default values to values from an empty UserPrefs object
        guiSettings = new GuiSettings((double) 800, (double) 600, 0, 0);
        String rolodexFilePath = "data/default.rldx";
        rolodexName = "MyRolodex";
        userPrefs = new UserPrefs();
        assertEquals(guiSettings, userPrefs.getGuiSettings());
        assertEquals(rolodexFilePath, userPrefs.getRolodexFilePath());
        assertEquals(rolodexName, userPrefs.getRolodexName());
    }

    @Test
    public void assertSetRolodexName() {
        // Check set to different name on setter call
        userPrefs.setRolodexName("DifferentRolodex");
        assertFalse(rolodexName.equals(userPrefs.getRolodexName()));
    }

    @Test
    public void assertGetRolodexName() {
        // Check name is default name on getter call
        assertEquals(userPrefs.getRolodexName(), rolodexName);
    }

    @Test
    public void assertEqualsSameObjectReturnsTrue() {
        assertTrue(userPrefs.equals(userPrefs));
    }

    @Test
    public void assertEqualsNotUserPrefsObjectReturnsFalse() {
        assertFalse(userPrefs.equals(new Object()));
    }

    @Test
    public void assertEqualsDifferentRolodexNameReturnsFalse() {
        userPrefs.setRolodexName("DifferentRolodex");
        assertFalse(userPrefs.equals(new UserPrefs()));
    }

    @Test
    public void assertEqualsDifferentRolodexFilePathReturnsFalse() {
        userPrefs.setRolodexFilePath("data/different.rldx");
        assertFalse(userPrefs.equals(new UserPrefs()));
    }

    @Test
    public void assertEqualsDifferentGuiSettingsReturnsFalse() {
        userPrefs.setGuiSettings(501, 500, 0, 0);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));

        userPrefs.setGuiSettings(500, 501, 0, 0);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));

        userPrefs.setGuiSettings(500, 500, 1, 0);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));

        userPrefs.setGuiSettings(500, 500, 0, 1);
        assertFalse(userPrefs.equals(new UserPrefs()));
        assertFalse(guiSettings.equals(userPrefs.getGuiSettings()));
    }

    @Test
    public void assertEqualsSameRolodexDifferentInstanceReturnsTrue() {
        UserPrefs newUserPrefs = new UserPrefs();
        assertTrue(userPrefs.equals(newUserPrefs));

        assertEquals(userPrefs.hashCode(), newUserPrefs.hashCode());
    }
}
```
###### \java\seedu\address\storage\JsonUserPrefsStorageTest.java
``` java
    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);
        assertTrue(jsonUserPrefsStorage.equals(jsonUserPrefsStorage));
    }

    @Test
    public void assertEqualsNotJsonUserPrefsStorageInstanceReturnsFalse() {
        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);
        assertFalse(jsonUserPrefsStorage.equals(new Object()));
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void assertGetUserPrefsFilePath() {
        assertEquals(storageManager.getUserPrefsFilePath(), userPrefsStorage.getUserPrefsFilePath());
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void assertSetNewRolodexStorageDifferentStorage() {
        RolodexStorage currentRoldexStorage = storageManager.getExistingRolodexStorage();
        storageManager.setNewRolodexStorage(new XmlRolodexStorage("data/"));
        assertFalse(currentRoldexStorage.getRolodexFilePath().equals(
                storageManager.getExistingRolodexStorage().getRolodexFilePath()));
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        assertTrue(storageManager.equals(storageManager));
    }

    @Test
    public void assertEqualsNotStorageManagerInstanceReturnsFalse() {
        assertFalse(storageManager.equals(new Object()));
    }
```
###### \java\seedu\address\storage\XmlRolodexStorageTest.java
``` java
    @Test
    public void saveRolodexInvalidStorageExtensionThrowsInvalidExtensionException() throws IOException {
        String invalidExtensionedFilePath = "invalid/extension.megaPoop";
        XmlRolodexStorage rolodexStorage = new XmlRolodexStorage(invalidExtensionedFilePath);
        thrown.expect(InvalidExtensionException.class);
        rolodexStorage.saveRolodex(new Rolodex());
    }

    @Test
    public void assertEqualsSameInstanceReturnsTrue() {
        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.rldx";
        XmlRolodexStorage xmlRolodexStorage = new XmlRolodexStorage(pefsFilePath);
        assertTrue(xmlRolodexStorage.equals(xmlRolodexStorage));
    }

    @Test
    public void assertEqualsNotXmlRolodexStorageInstanceReturnsFalse() {
        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.rldx";
        XmlRolodexStorage xmlRolodexStorage = new XmlRolodexStorage(pefsFilePath);
        assertFalse(xmlRolodexStorage.equals(new Object()));
    }
```
###### \java\seedu\address\TestApp.java
``` java
    public static final String SECONDARY_SAVE_LOCATION = TestUtil.getFilePathInSandboxFolder("tempRolodex.rldx");
    public static final String APP_TITLE = "Test App";
```
###### \java\seedu\address\TestApp.java
``` java
    /**
     * Returns a defensive copy of the storage.
     */
    public Storage getStorage() {
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefsCopy = initPrefs(userPrefsStorage);
        RolodexStorage rolodexStorage = new XmlRolodexStorage(userPrefsCopy.getRolodexFilePath());

        return new StorageManager(rolodexStorage, userPrefsStorage);
    }

    /**
     * Returns a defensive copy of the user prefs.
     */
    public UserPrefs getUserPrefs() {
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs copy = initPrefs(userPrefsStorage);
        return copy;
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Similar to the previous {@code build} except that this build returns a copy of the descriptor with tags
     * union-exclusive to the specified {@code otherSet} of {@code Tag}s.
     */
    public EditPersonDescriptor build(Set<Tag> otherSet) {
        EditPersonDescriptor copy = new EditPersonDescriptor(descriptor);
        copy.setTags(copy.getXorTags(otherSet));
        return copy;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Similar to the previous {@code build} except that this build returns a copy of the descriptor with tags
     * union-exclusive to the specified {@code otherSet} of {@code Tag}s.
     */
    public Person build(Set<Tag> otherSet) {
        EditPersonDescriptorBuilder descriptorBuilder = new EditPersonDescriptorBuilder(person);
        EditCommand.EditPersonDescriptor descriptor = descriptorBuilder.build();
        Person copy = new Person(person);
        copy.setTags(descriptor.getXorTags(otherSet));
        return copy;
    }
```
###### \java\seedu\address\testutil\TestUtil.java
``` java
    /**
     * Returns a random file path with supplied extension in the sandbox folder.
     */
    public static String generateRandomSandboxDirectory(String extension) {
        return replaceBackslashes(getFilePathInSandboxFolder(Double.toString(Math.random()).concat(extension)));
    }
```
###### \java\seedu\address\ui\StarWarsTest.java
``` java
public class StarWarsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void assertGetNextSceneExceptionStopsInstance() {
        thrown.expect(NullPointerException.class);
        StarWarsWindow.getNextScene("", null);
        assertTrue(StarWars.hasInstance());
    }

    @Test
    public void assertGetNextSceneDelimitedDisplaysCorrectly() throws UnsupportedEncodingException {
        String start = "12yy5711308t47`1470`mg98rr3478n4t4";
        String delimter = "_._";
        String end = "109n567894890ty38892-13423t545v";
        String inputString = start.concat(delimter).concat(end);
        // NOTE: For java versions less than 7, use UTF-7 instead
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8.name()));
        assertEquals(start, StarWarsWindow.getNextScene(delimter, inputStream));
        assertEquals(end, StarWarsWindow.getNextScene(delimter, inputStream));
    }
}
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    private static final String STUB_SAVE_LOCATION = "Stub" + ROLODEX_FILE_EXTENSION;
    private static final String STUB_SAVE_LOCATION_NEW = "NewStub" + ROLODEX_FILE_EXTENSION;
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    private static final RolodexChangedEvent EVENT_STUB_CHANGE_DATA =
            new RolodexChangedEvent(new Rolodex());
    private static final RolodexChangedDirectoryEvent EVENT_STUB_CHANGE_DIRECTORY =
            new RolodexChangedDirectoryEvent(RELATIVE_PATH + STUB_SAVE_LOCATION_NEW);
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL);

        // after rolodex is updated
        postNow(EVENT_STUB_CHANGE_DATA);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));

        // after rolodex directory is updated
        postNow(EVENT_STUB_CHANGE_DIRECTORY);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION_NEW,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        assertCommandFailure(command, command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code expectedCommandBox},
     * the result display box displays {@code expectedResultMessage} and the model related components equal to the
     * current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedCommandBox, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(expectedCommandBox, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        assertCommandFailure(command, command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code expectedCommandBox}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedCommandBox, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(expectedCommandBox, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        assertCommandFailure(command, command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedCommandBox}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedCommandBox, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(expectedCommandBox, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
    @Test
    public void find() {
        /* Case: find multiple persons in rolodex, command with leading spaces and trailing spaces
         * -> 3 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        // first names of Benson and Daniel are "Meier", first name of Elle is "Meyer" - one hop away from "Meier"
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " " + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 1 person found */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in rolodex, 2 keywords -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in rolodex, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in rolodex, 2 keywords with 1 repeat -> 2 persons found */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in rolodex, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next();
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in rolodex after deleting 1 of them -> 2 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getRolodex().getPersonList().contains(BENSON);
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in rolodex, keyword is same as name but of different case -> 1 person found */
        command = FindCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in rolodex, keyword is substring of name -> 1 persons found */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " Mei";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in rolodex, name is substring of keyword -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel, DANIEL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " Mark";
        ModelHelper.setFilteredList(expectedModel, CARL); // Carl is two hops away from Mark
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD_ABBREVIATIONS.iterator().next() + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find remark of person in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + GEORGE.getRemark().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag friends of persons in rolodex -> 6 persons found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        assert tags.get(0).tagName.equals("friends");
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag FRIENDS of persons in rolodex -> 5 persons found */
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName.toUpperCase();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag frie of persons in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName.substring(0, 4);
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty rolodex -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getRolodex().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }
```
###### \java\systemtests\NewCommandSystemTest.java
``` java
public class NewCommandSystemTest extends RolodexSystemTest {

    private static final String DIRECTORY_VALID_DEFAULT = replaceBackslashes(SAVE_LOCATION_FOR_TESTING);

    @Test
    public void newFile() {
        /* Case: Create a new directory with different random valid filepath,
         * command with leading spaces and trailing spaces
         * -> opened
         */
        String newFilePath = generateRandomSandboxDirectory(ROLODEX_FILE_EXTENSION);
        String commandString = "         " + NewCommand.COMMAND_WORD + "  " + newFilePath + "      ";
        assertCommandSuccess(commandString, newFilePath);

        /* Case: Create a different random directory but with single constructor test
         * -> opened
         */
        newFilePath = generateRandomSandboxDirectory(ROLODEX_FILE_EXTENSION);
        assertCommandSuccess(newFilePath);

        /* Case: try Creating a new file that already exists
         * -> fail. Remain on current file, prompts to use `open` command.
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        commandString = NewCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_ALREADY_EXISTS, newFilePath));

        /*Case: try Creating file that is not a valid formatted directory
         * -> fail. Remain on current file, displays invalid command format.
         */
        newFilePath = "invalidParseDirectory.xml";
        commandString = NewCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewCommand.MESSAGE_USAGE));
    }

    @After
    public void resetData() {
        /* End of test: Open original test file
         * -> opened
         * Otherwise, go to test/data/sandbox/pref_testing.json
         * and manually change the rolodexFilePath to the correct path
         */
        UserPrefs userPrefs = getUserPrefs();
        userPrefs.setRolodexFilePath(replaceBackslashes(SAVE_LOCATION_FOR_TESTING));
        Storage storage = getStorage();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code OpenCommand#MESSAGE_OPENING} with the valid filePath, and the model related components
     * equal to {@code expectedModel}.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar save directory changes to the new rolodex location, the command box has the
     * default style class, and the selected card updated accordingly, depending on {@code cardStatus}.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandSuccess(String filePath) {
        assertCommandSuccess(NewCommand.COMMAND_WORD + " " + filePath, filePath);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)}. Executes {@code command}
     * instead.
     * @see OpenCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String filePath) {
        getStorage().setNewRolodexStorage(new XmlRolodexStorage(filePath));
        assertCommandSuccess(command, filePath, getStorage());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see OpenCommandSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String filePath, Storage expectedStorage) {
        String expectedResultMessage = String.format(MESSAGE_CREATING, filePath);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedStorage);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarDirectoryChanged(filePath);
        assertUndoRedoStackCleared();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\NewRolodexCommandSystemTest.java
``` java
public class NewRolodexCommandSystemTest extends RolodexSystemTest {

    private static final String DIRECTORY_VALID_DEFAULT = replaceBackslashes(SAVE_LOCATION_FOR_TESTING);

    @Test
    public void newFile() {
        /* Case: Create a new directory with different random valid filepath,
         * command with leading spaces and trailing spaces
         * -> opened
         */
        String newFilePath = generateRandomSandboxDirectory(ROLODEX_FILE_EXTENSION);
        String commandString = "         " + NewRolodexCommand.COMMAND_WORD + "  " + newFilePath + "      ";
        assertCommandSuccess(commandString, newFilePath);

        /* Case: Create a different random directory but with single constructor test
         * -> opened
         */
        newFilePath = generateRandomSandboxDirectory(ROLODEX_FILE_EXTENSION);
        assertCommandSuccess(newFilePath);

        /* Case: try Creating a new file that already exists
         * -> fail. Remain on current file, prompts to use `open` command.
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        commandString = NewRolodexCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_ALREADY_EXISTS, newFilePath));

        /*Case: try Creating file that is not a valid formatted directory
         * -> fail. Remain on current file, displays invalid command format.
         */
        newFilePath = "invalidParseDirectory.xml";
        commandString = NewRolodexCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
    }

    @After
    public void resetData() {
        /* End of test: Open original test file
         * -> opened
         * Otherwise, go to test/data/sandbox/pref_testing.json
         * and manually change the rolodexFilePath to the correct path
         */
        UserPrefs userPrefs = getUserPrefs();
        userPrefs.setRolodexFilePath(replaceBackslashes(SAVE_LOCATION_FOR_TESTING));
        Storage storage = getStorage();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code OpenRolodexCommand#MESSAGE_OPENING} with the valid filePath, and the model related components
     * equal to {@code expectedModel}.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar save directory changes to the new rolodex location, the command box has the
     * default style class, and the selected card updated accordingly, depending on {@code cardStatus}.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandSuccess(String filePath) {
        assertCommandSuccess(NewRolodexCommand.COMMAND_WORD + " " + filePath, filePath);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)}. Executes {@code command}
     * instead.
     * @see OpenRolodexCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String filePath) {
        getStorage().setNewRolodexStorage(new XmlRolodexStorage(filePath));
        assertCommandSuccess(command, filePath, getStorage());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see OpenRolodexCommandSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String filePath, Storage expectedStorage) {
        String expectedResultMessage = String.format(MESSAGE_CREATING, filePath);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedStorage);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarDirectoryChanged(filePath);
        assertUndoRedoStackCleared();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\OpenCommandSystemTest.java
``` java
public class OpenCommandSystemTest extends RolodexSystemTest {

    private static final String DIRECTORY_VALID_DEFAULT = replaceBackslashes(SAVE_LOCATION_FOR_TESTING);

    private static final String DIRECTORY_VALID_DIFFERENT = replaceBackslashes(SECONDARY_SAVE_LOCATION);

    @Test
    public void open() {
        /* Case: open a new directory with different valid filepath, command with leading spaces and trailing spaces
         * -> opened
         */
        String newFilePath = DIRECTORY_VALID_DIFFERENT;
        String commandString = "         " + OpenCommand.COMMAND_WORD + "  " + newFilePath + "      ";
        assertCommandSuccess(commandString, newFilePath);

        /* Case: open back original directory with valid filepath, normal command
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        commandString = OpenCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandSuccess(commandString, newFilePath);

        /* Case: open back different directory but with single constructor test
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DIFFERENT;
        assertCommandSuccess(newFilePath);

        /* Case: open back default directory but with single constructor test
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        assertCommandSuccess(newFilePath);

        /* Case: try opening file that does not exist
         * -> fail. Remain on current file, prompts to use `new` command.
         */
        newFilePath = replaceBackslashes(getFilePathInSandboxFolder("notFound.rldx"));
        commandString = OpenCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_NOT_EXIST, newFilePath));

        /*Case: try opening file that is not a valid formatted directory
         * -> fail. Remain on current file, displays invalid command format.
         */
        newFilePath = "invalidParseDirectory.xml";
        commandString = OpenCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenCommand.MESSAGE_USAGE));
    }

    @After
    public void resetData() {
        /* End of test: Open original test file
         * -> opened
         * Otherwise, go to test/data/sandbox/pref_testing.json
         * and manually change the rolodexFilePath to the correct path
         */
        UserPrefs userPrefs = getUserPrefs();
        userPrefs.setRolodexFilePath(replaceBackslashes(SAVE_LOCATION_FOR_TESTING));
        Storage storage = getStorage();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code OpenCommand#MESSAGE_OPENING} with the valid filePath, and the model related components
     * equal to {@code expectedModel}.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar save directory changes to the new rolodex location, the command box has the
     * default style class, and the selected card updated accordingly, depending on {@code cardStatus}.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandSuccess(String filePath) {
        assertCommandSuccess(OpenCommand.COMMAND_WORD + " " + filePath, filePath);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)}. Executes {@code command}
     * instead.
     * @see OpenCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String filePath) {
        getStorage().setNewRolodexStorage(new XmlRolodexStorage(filePath));
        assertCommandSuccess(command, filePath, getStorage());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see OpenCommandSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String filePath, Storage expectedStorage) {
        String expectedResultMessage = String.format(MESSAGE_OPENING, filePath);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedStorage);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarDirectoryChanged(filePath);
        assertUndoRedoStackCleared();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\OpenRolodexCommandSystemTest.java
``` java
public class OpenRolodexCommandSystemTest extends RolodexSystemTest {

    private static final String DIRECTORY_VALID_DEFAULT = replaceBackslashes(SAVE_LOCATION_FOR_TESTING);

    private static final String DIRECTORY_VALID_DIFFERENT = replaceBackslashes(SECONDARY_SAVE_LOCATION);

    @Test
    public void open() {
        /* Case: open a new directory with different valid filepath, command with leading spaces and trailing spaces
         * -> opened
         */
        String newFilePath = DIRECTORY_VALID_DIFFERENT;
        String commandString = "         " + OpenRolodexCommand.COMMAND_WORD + "  " + newFilePath + "      ";
        assertCommandSuccess(commandString, newFilePath);

        /* Case: open back original directory with valid filepath, normal command
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        commandString = OpenRolodexCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandSuccess(commandString, newFilePath);

        /* Case: open back different directory but with single constructor test
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DIFFERENT;
        assertCommandSuccess(newFilePath);

        /* Case: open back default directory but with single constructor test
         * -> opened
         */
        newFilePath = DIRECTORY_VALID_DEFAULT;
        assertCommandSuccess(newFilePath);

        /* Case: try opening file that does not exist
         * -> fail. Remain on current file, prompts to use `new` command.
         */
        newFilePath = replaceBackslashes(getFilePathInSandboxFolder("notFound.rldx"));
        commandString = OpenRolodexCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString, String.format(MESSAGE_NOT_EXIST, newFilePath));

        /*Case: try opening file that is not a valid formatted directory
         * -> fail. Remain on current file, displays invalid command format.
         */
        newFilePath = "invalidParseDirectory.xml";
        commandString = OpenRolodexCommand.COMMAND_WORD + " " + newFilePath;
        assertCommandFailure(commandString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, OpenRolodexCommand.MESSAGE_USAGE));
    }

    @After
    public void resetData() {
        /* End of test: Open original test file
         * -> opened
         * Otherwise, go to test/data/sandbox/pref_testing.json
         * and manually change the rolodexFilePath to the correct path
         */
        UserPrefs userPrefs = getUserPrefs();
        userPrefs.setRolodexFilePath(replaceBackslashes(SAVE_LOCATION_FOR_TESTING));
        Storage storage = getStorage();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code OpenRolodexCommand#MESSAGE_OPENING} with the valid filePath, and the model related components
     * equal to {@code expectedModel}.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar save directory changes to the new rolodex location, the command box has the
     * default style class, and the selected card updated accordingly, depending on {@code cardStatus}.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandSuccess(String filePath) {
        assertCommandSuccess(OpenRolodexCommand.COMMAND_WORD + " " + filePath, filePath);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)}. Executes {@code command}
     * instead.
     * @see OpenRolodexCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String filePath) {
        getStorage().setNewRolodexStorage(new XmlRolodexStorage(filePath));
        assertCommandSuccess(command, filePath, getStorage());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see OpenRolodexCommandSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String filePath, Storage expectedStorage) {
        String expectedResultMessage = String.format(MESSAGE_OPENING, filePath);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedStorage);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarDirectoryChanged(filePath);
        assertUndoRedoStackCleared();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Storage)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\RolodexSystemTest.java
``` java
    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same person objects as {@code expectedStorage},
     * the person list panel displays the persons in the model correctly and the storage is equal to the supplied
     * {@code expectedStorage}.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Storage expectedStorage) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedStorage, getStorage());
    }
```
###### \java\systemtests\RolodexSystemTest.java
``` java
    /**
     * Asserts that the status bar directory has changed to {@code expectedSaveLocation}
     */
    protected void assertStatusBarDirectoryChanged(String expectedSaveLocation) {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertEquals(handle.getSaveLocation(), expectedSaveLocation);
    }

    /**
     * Asserts that the undo redo stack has been cleared.
     */
    protected void assertUndoRedoStackCleared() {
        UndoRedoStack undoRedoStack = getUndoRedoStack();
        assertFalse(undoRedoStack.canUndo());
        assertFalse(undoRedoStack.canRedo());
    }
```
###### \java\systemtests\RolodexSystemTest.java
``` java
    /**
     * Returns a defensive copy of the current storage.
     */
    protected Storage getStorage() {
        return testApp.getStorage();
    }

    /**
     * Returns a defensive copy of the current userPrefs.
     */
    protected UserPrefs getUserPrefs() {
        return testApp.getUserPrefs();
    }
```
###### \java\systemtests\SelectCommandSystemTest.java
``` java
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        assertCommandFailure(command, command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code expectedCommandBox},
     * the result display box displays {@code expectedResultMessage} and the model related components
     * equal to the current model.
     * These verifications are done by
     * {@code RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RolodexSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedCommandBox, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(expectedCommandBox, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
```
