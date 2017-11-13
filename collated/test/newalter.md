# newalter
###### \java\guitests\guihandles\CommandBoxHandle.java
``` java
    /**
     * Enters the given command in the Command Box and presses the given keys and then presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean pressAndRun(String command, KeyCode... keyPresses) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(keyPresses);

        guiRobot.type(KeyCode.ENTER);
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public Predicate<? super ReadOnlyPerson> getPersonListPredicate() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        Predicate<ReadOnlyPerson> component1 = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        Predicate<ReadOnlyPerson> component2 = new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        Predicate<ReadOnlyPerson> component3 = new PhoneContainsKeywordsPredicate(Collections.singletonList("third"));
        Predicate<ReadOnlyPerson> component4 = new PhoneContainsKeywordsPredicate(Collections.singletonList("fourth"));

        ArrayList<Predicate<ReadOnlyPerson>> firstPredicates = new ArrayList<>();
        ArrayList<Predicate<ReadOnlyPerson>> secondPredicates = new ArrayList<>();
        firstPredicates.addAll(Arrays.asList(component1, component3, FALSE, FALSE, FALSE));
        secondPredicates.addAll(Arrays.asList(component2, component4, FALSE, FALSE, FALSE));

        FindCommand findFirstCommand = new FindCommand(firstPredicates);
        FindCommand findSecondCommand = new FindCommand(secondPredicates);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicates);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different keyword -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_wildcardKeywordsOnName_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/b?nson");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_wildcardKeywordsOnName_twoPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("n/b*");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, GEORGE));
    }

    @Test
    public void execute_multipleKeywordsOnName_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("n/Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleKeywordsOnPhone_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("p/85355255 98765432");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_wildcardKeywordOnEmail_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("e/*a@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DANIEL, FIONA, GEORGE));
    }

    @Test
    public void execute_oneKeywordOnAddress_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("a/clementi");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_oneKeywordOnTag_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("t/owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleFields_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("n/meier t/fami??");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, DANIEL));
    }

    @Test
    public void execute_multipleFields_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/alice p/85355255 t/fami?y e/alice*");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command = new FindCommand(new ArrayList<>());
        try {
            command = (new FindCommandParser()).parse(" " + userInput);
            command.setData(model, new CommandHistory(), new UndoRedoStack());
        } catch (ParseException e) {
            assert false : "userInput is valid";
        }
        return command;
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

    @Test
    public void null_argFail() {
        try {
            ArgumentWildcardMatcher.processKeywords(null);
        } catch (Exception e) {
            assertEquals(e.getClass(), NullPointerException.class);
        }
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        Predicate<ReadOnlyPerson> component1 = new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
        Predicate<ReadOnlyPerson> component2 = new PhoneContainsKeywordsPredicate(Arrays.asList("88887777"));
        Predicate<ReadOnlyPerson> component3 = new EmailContainsKeywordsPredicate(Arrays.asList("alice@example.com"));
        Predicate<ReadOnlyPerson> component4 = new AddressContainsKeywordsPredicate(Arrays.asList("Clementi"));
        Predicate<ReadOnlyPerson> component5 = new ContainsTagsPredicate(Arrays.asList("family", "friends"));

        ArrayList<Predicate<ReadOnlyPerson>> predicates = new ArrayList<>();
        predicates.addAll(Arrays.asList(component1, component2, component3, component4, component5));
        FindCommand expectedFindCommand =
                new FindCommand(predicates);
        assertParseSuccess(parser,
                " n/Alice Bob e/alice@example.com a/Clementi t/family friends p/88887777", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  n/Alice Bob \t e/alice@example.com "
                + "a/Clementi t/family \n friends \t p/88887777 \t ", expectedFindCommand);
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
        assertParseFailure(parser, String.format("%d %d", ResizeCommand.MAX_WIDTH, MainWindow.getMinHeight() - 1),
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
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Presses {@code keyPresses} and executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void pressAndExecuteCommand(String command, KeyCode... keyPresses) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().pressAndRun(command, keyPresses);
    }
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
    @Test
    public void find() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by tag -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + "owes*ney";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person where person list is not displaying the person we are finding -> 0 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "95352563";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person when person list is empty -> 0 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + "owesMoney";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by email -> 2 persons found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "????@example.com cornelia*";
        ModelHelper.setFilteredList(expectedModel, DANIEL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by email, 2 keywords in reversed order -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "cornelia* ?nn?@example.com";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by address -> 3 persons found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_ADDRESS + "AVE";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons by tags -> 2 persons found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_TAG + "family owesmoney";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons by phone -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + PREFIX_PHONE + "85355255 95352563";
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find persons by name and phone and tag -> 3 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "alic? "
                + PREFIX_TAG + "owesmoney " + PREFIX_PHONE + "953525??";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by name and phone and tag with substring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "alic "
                + PREFIX_TAG + "owesmone " + PREFIX_PHONE + "953525";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by name and phone and tag with superstring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "alicee "
                + PREFIX_TAG + "aowesmoney " + PREFIX_PHONE + "953525633";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by email and address -> 2 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "alice@*.com "
                + PREFIX_ADDRESS + "tokyo";
        ModelHelper.setFilteredList(expectedModel, ALICE, FIONA);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by email and address with substring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "alice@example.co "
                + PREFIX_ADDRESS + "toky";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by email and address with superstring keywords -> 0 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_EMAIL + "calice@example.com "
                + PREFIX_ADDRESS + "tokyo1";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons by name and phone and tag -> 3 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "b*n "
                + PREFIX_TAG + "FAmily " + PREFIX_PHONE + "*3525??";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL);
        assertCommandSuccess(command, expectedModel);

        /* Case: find persons in address book after deleting 1 of them -> 1 person found */
        showAllPersons();
        executeCommand(DeleteCommand.COMMAND_WORD + " 3");
        assert !getModel().getAddressBook().getPersonList().contains(CARL);
        showAllPersons();
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person by name and email, keyword of different case -> 2 person found */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "MeIeR "
                + PREFIX_EMAIL + "lYdIa@eXaMple.Com";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(BENSON.getName().fullName);
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + "Daniel";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: empty argument -> rejected */
        command = "find";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        command = "FiNd n/Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }
```
###### \java\systemtests\TabCompleteSystemTest.java
``` java
public class TabCompleteSystemTest extends AddressBookSystemTest {

    @Test
    public void tab_complete() {
        /* Case: partial name in find command, TAB Pressed
         * -> 2 persons found
         */
        showAllPersons();
        String command = FindCommand.COMMAND_WORD + " n/Mei";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, KeyCode.TAB);
        assertSelectedCardUnchanged();

        /* Case: partial name in find command, DOWN, ENTER Pressed
         * -> 1 person found
         */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " n/Be";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel, KeyCode.DOWN, KeyCode.ENTER);
        assertSelectedCardUnchanged();

        /* Case: partial name in find command, DOWN, DOWN, ENTER Pressed
         * -> 1 person found
         */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " n/Be";
        ModelHelper.setFilteredList(expectedModel, GEORGE);
        assertCommandSuccess(command, expectedModel, KeyCode.DOWN, KeyCode.DOWN, KeyCode.ENTER);
        assertSelectedCardUnchanged();

        /* Case: Non-matching keywords in find command, no suggestions
         * -> 0 persons found
         */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " n/Bee";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel, KeyCode.TAB);
        assertSelectedCardUnchanged();

        /* Case: partial name in second argument, TAB Pressed
         * -> 2 persons found
         */
        showAllPersons();
        command = FindCommand.COMMAND_WORD + " n/Benson Da";
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel, KeyCode.TAB);
        assertSelectedCardUnchanged();
    }


    /**
     * Presses the keys {@code keyPresses} and then Executes {@code command}
     * and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, KeyCode... keyPresses) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        pressAndExecuteCommand(command, keyPresses);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

}
```
