# newalter
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public Predicate<? super ReadOnlyPerson> getPersonListPredicate() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\FilterCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ContainsTagsPredicate firstPredicate =
                new ContainsTagsPredicate(Collections.singletonList("first"));
        ContainsTagsPredicate secondPredicate =
                new ContainsTagsPredicate(Collections.singletonList("second"));

        FilterCommand findFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand findSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different tags -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroTags_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleTags_twoPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterCommand command = prepareCommand("owesMoney family");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_multipleTags_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        FilterCommand command = prepareCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_wildcardKeywords_aliceFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareCommand("fami??");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    @Test
    public void execute_wildcardKeywords_bensonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command = prepareCommand("o*");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_successiveCommands_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterCommand command1 = prepareCommand("owesMoney");
        FilterCommand command2 = prepareCommand("friends");
        assertSuccessiveCommandSuccess(command1, command2, expectedMessage, Arrays.asList(BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code FilterCommand}.
     */
    private FilterCommand prepareCommand(String userInput) {
        FilterCommand command =
                new FilterCommand(new ContainsTagsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Asserts that {@code command1, command2} are successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertSuccessiveCommandSuccess(FilterCommand command1, FilterCommand command2,
                                                String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        command1.execute();
        CommandResult commandResult = command2.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void execute_wildcardKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("b?nson");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_wildcardKeywords_twoPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("b*");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, GEORGE));
    }
```
###### \java\seedu\address\logic\commands\ResizeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ResizeCommand}.
 */
public class ResizeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validParameters_success() {
        assertExecutionSuccess(ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT);
        assertExecutionSuccess(ResizeCommand.MAX_WIDTH - 100, ResizeCommand.MAX_HEIGHT - 100);
    }

    @Test
    public void execute_invalidParameters_failure() {
        assertExecutionFailure(ResizeCommand.MAX_WIDTH + 1, ResizeCommand.MAX_HEIGHT,
                Messages.MESSAGE_INVALID_COMMAND_PARAMETERS);
    }

    @Test
    public void equals() {
        ResizeCommand resizeFirstCommand = new ResizeCommand(1280, 720);
        ResizeCommand resizeSecondCommand = new ResizeCommand(1280, 600);

        // same object -> returns true
        assertTrue(resizeFirstCommand.equals(resizeFirstCommand));

        // same values -> returns true
        ResizeCommand resizeFirstCommandCopy = new ResizeCommand(1280, 720);
        assertTrue(resizeFirstCommand.equals(resizeFirstCommandCopy));

        // different types -> returns false
        assertFalse(resizeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(resizeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(resizeFirstCommand.equals(resizeSecondCommand));
    }

    /**
     * Executes a {@code ResizeCommand} with the given {@code width, height},
     * and checks that {@code ResizeMainWindowEvent} is raised with the correct parameters.
     */
    private void assertExecutionSuccess(int width, int height) {
        ResizeCommand resizeCommand = prepareCommand(width, height);

        try {
            CommandResult commandResult = resizeCommand.execute();
            assertEquals(String.format(ResizeCommand.MESSAGE_SUCCESS, width, height),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ResizeMainWindowEvent lastEvent = (ResizeMainWindowEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(width, lastEvent.getWidth());
        assertEquals(height, lastEvent.getHeight());
    }

    /**
     * Executes a {@code ResizeCommand} with the given {@code width, height}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(int width, int height, String expectedMessage) {
        ResizeCommand resizeCommand = prepareCommand(width, height);

        try {
            resizeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ResizeCommand} with parameters {@code width, height}.
     */
    private ResizeCommand prepareCommand(int width, int height) {
        ResizeCommand resizeCommand = new ResizeCommand(width, height);
        resizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return resizeCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("tag1", "tag2", "tag3");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new ContainsTagsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_resize() throws Exception {
        ResizeCommand command = (ResizeCommand) parser.parseCommand(
                ResizeCommand.COMMAND_WORD
                        + String.format(" %d %d", ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT));
        assertEquals(new ResizeCommand(ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sync() throws Exception {
        SyncCommand command = (SyncCommand) parser.parseCommand(
                SyncCommand.COMMAND_WORD);
        assertEquals(new SyncCommand(), command);
    }
```
###### \java\seedu\address\logic\parser\ArgumentWildcardMatcherTest.java
``` java
public class ArgumentWildcardMatcherTest {
    @Test
    public void wildcard_matcherSuccess() {
        String args = "Fa* *s* G*d a?b";
        List<String> keywords = ArgumentWildcardMatcher.processKeywords(Arrays.asList(args.split("\\s+")));
        List<String> expected = Arrays.asList("fa\\S*", "\\S*s\\S*", "g\\S*d", "a\\Sb");
        assertEquals(keywords, expected);
    }
}
```
###### \java\seedu\address\logic\parser\FilterCommandParserTest.java
``` java
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new ContainsTagsPredicate(Arrays.asList("friends", "family")));
        assertParseSuccess(parser, "friends family", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t family  \t", expectedFilterCommand);
    }

}
```
###### \java\seedu\address\logic\parser\ResizeCommandParserTest.java
``` java
public class ResizeCommandParserTest {

    private ResizeCommandParser parser = new ResizeCommandParser();

    @Test
    public void parse_validArgs_returnsResizeCommand() {
        assertParseSuccess(parser, String.format("%d %d", ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT),
                new ResizeCommand(ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid argument
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));

        // invalid number of arguments
        assertParseFailure(parser, "800 600 600",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));

        // invalid arguments that are out of bound
        assertParseFailure(parser, String.format("%d %d", ResizeCommand.MAX_WIDTH + 1, ResizeCommand.MAX_HEIGHT),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));


    }
}
```
###### \java\seedu\address\model\person\ContainsTagsPredicateTest.java
``` java
public class ContainsTagsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContainsTagsPredicate firstPredicate = new ContainsTagsPredicate(firstPredicateKeywordList);
        ContainsTagsPredicate secondPredicate = new ContainsTagsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsTagsPredicate firstPredicateCopy = new ContainsTagsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsTags_returnsTrue() {
        // One keyword
        ContainsTagsPredicate predicate = new ContainsTagsPredicate(Collections.singletonList("family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));

        // Multiple keywords
        predicate = new ContainsTagsPredicate(Arrays.asList("family", "friends"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Mixed-case keywords
        predicate = new ContainsTagsPredicate(Arrays.asList("FamIly", "FrIeNds"));
        assertTrue(predicate.test(new PersonBuilder().withTags("fAmilY", "friEnDS").build()));

        // Wildcard Symbol in keywords
        predicate = new ContainsTagsPredicate(Arrays.asList("f*"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));
    }

    @Test
    public void test_doesNotContainTags_returnsFalse() {
        // Zero keywords
        ContainsTagsPredicate predicate = new ContainsTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Non-matching keyword
        predicate = new ContainsTagsPredicate(Arrays.asList("family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Keywords match name, phone, email and address, but does not match tags
        predicate = new ContainsTagsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("family").build()));
    }
}
```
###### \java\seedu\address\model\person\NameContainsKeywordsPredicateTest.java
``` java
        // Wildcard Symbol in keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("A*e", "*b"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
```
###### \java\systemtests\FilterCommandSystemTest.java
``` java
public class FilterCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void filter() {
        /* Case: filter one person in address book, command with leading spaces and trailing spaces
         * -> 1 person found
         */
        String command = "   " + FilterCommand.COMMAND_WORD + " " + "owesMoney" + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous filter command where person list is displaying the person we are finding
         * -> 1 person found
         */
        command = FilterCommand.COMMAND_WORD + " " + "owesMoney";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person where person list is not displaying the person we are finding -> 0 person found */
        command = FilterCommand.COMMAND_WORD + " son";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords -> 2 persons found */
        showAllPersons();
        command = FilterCommand.COMMAND_WORD + " family owesMoney";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        command = FilterCommand.COMMAND_WORD + " owesMoney family";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FilterCommand.COMMAND_WORD + " family owesMoney family";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FilterCommand.COMMAND_WORD + " family owesMoney NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: filter same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(ALICE);
        command = FilterCommand.COMMAND_WORD + " family owesMoney";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, keyword is same as name but of different case -> 1 person found */
        command = FilterCommand.COMMAND_WORD + " OwEsMoNeY";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, keyword is substring of tag -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " owes";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, whose tag is substring of keyword -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " owesMoney!";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter name of person in address book -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + DANIEL.getName();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter phone number of person in address book -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter address of person in address book -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter email of person in address book -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(4));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(BENSON.getName().fullName);
        command = FilterCommand.COMMAND_WORD + " owesMoney";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: filter person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FilterCommand.COMMAND_WORD + " friends";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiLtEr friends";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }


    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
