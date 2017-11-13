# dennaloh
###### \java\seedu\address\logic\commands\FbCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FbCommand}.
 */
public class FbCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        FbCommand fbFirstCommand = new FbCommand(INDEX_FIRST_PERSON);
        FbCommand fbSecondCommand = new FbCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(fbFirstCommand.equals(fbFirstCommand));

        // same values -> returns true
        FbCommand fbFirstCommandCopy = new FbCommand(INDEX_FIRST_PERSON);
        assertTrue(fbFirstCommand.equals(fbFirstCommandCopy));

        // different types -> returns false
        assertFalse(fbFirstCommand.equals(1));

        // null -> returns false
        assertFalse(fbFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(fbFirstCommand.equals(fbSecondCommand));
    }

    /**
     * Executes a {@code FbCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        FbCommand fbCommand = prepareCommand(index);

        try {
            fbCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code FbCommand} with parameters {@code index}.
     */
    private FbCommand prepareCommand(Index index) {
        FbCommand fbCommand = new FbCommand(index);
        fbCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return fbCommand;
    }
}
```
###### \java\seedu\address\logic\commands\person\EmailCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */
public class EmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

```
###### \java\seedu\address\logic\commands\person\EmailCommandTest.java
``` java
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EmailCommand emailCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EmailCommand emailFirstCommand = new EmailCommand(INDEX_FIRST_PERSON);
        EmailCommand emailSecondCommand = new EmailCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertEquals(emailFirstCommand, emailFirstCommand);
        // different types -> returns false
        assertNotEquals(emailFirstCommand, 1);
        // different person -> returns false
        assertNotEquals(emailFirstCommand, emailSecondCommand);
        // null -> returns false
        assertNotEquals(emailFirstCommand, null);
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        EmailCommand emailCommand = new EmailCommand(index);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}
```
###### \java\seedu\address\logic\commands\person\FindTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findtagFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findtagSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findtagFirstCommand.equals(findtagFirstCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findtagFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findtagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findtagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findtagFirstCommand.equals(findtagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindTagCommand}.
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\person\GMapCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code GMapCommand}.
 */
public class GMapCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GMapCommand gMapFirstCommand = new GMapCommand(INDEX_FIRST_PERSON);
        GMapCommand gmapSecondCommand = new GMapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(gMapFirstCommand.equals(gMapFirstCommand));

        // same values -> returns true
        GMapCommand gMapFirstCommandCopy = new GMapCommand(INDEX_FIRST_PERSON);
        assertTrue(gMapFirstCommand.equals(gMapFirstCommandCopy));

        // different types -> returns false
        assertFalse(gMapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gMapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(gMapFirstCommand.equals(gmapSecondCommand));
    }

    /**
     * Executes a {@code GMapCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        GMapCommand gMapCommand = prepareCommand(index);

        try {
            gMapCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code GMapCommand} with parameters {@code index}.
     */
    private GMapCommand prepareCommand(Index index) {
        GMapCommand gMapCommand = new GMapCommand(index);
        gMapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return gMapCommand;
    }
}
```
###### \java\seedu\address\logic\commands\stub\ModelStub.java
``` java
    @Override
    public String getGMapUrl(ReadOnlyPerson target)  {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public String getFbUrl (ReadOnlyPerson target) {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void openUrl (String url) {
        fail("This method should not be called.");
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_fb() throws Exception {
        FbCommand command = (FbCommand) parser.parseCommand(
                FbCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new FbCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_gMap() throws Exception {
        GMapCommand command = (GMapCommand) parser.parseCommand(
                GMapCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new GMapCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindTagCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTagCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new EmailCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\ui\EventCalendarTest.java
``` java
public class EventCalendarTest {

    @Test
    public void testJan() throws Exception {
        MonthDateBuilder monthDateBuilderJan = new MonthDateBuilder(0, 2018);
        String[] monthDateArrayJan = monthDateBuilderJan.getMonthDateArray();
        assertEquals(monthDateBuilderJan.getNameOfMonth(), "January");
        assertEquals(monthDateBuilderJan.getFirstDayOfMonth().toString(), "1");
        assertEquals(monthDateArrayJan[0], " ");
        assertEquals(monthDateArrayJan[1], "1");
        assertEquals(monthDateArrayJan[31], "31");
        assertEquals(monthDateArrayJan[35], " ");
    }

    @Test
    public void testFeb() throws Exception {
        MonthDateBuilder monthDateBuilderFeb = new MonthDateBuilder(1, 2017);
        String[] monthDateArrayFeb = monthDateBuilderFeb.getMonthDateArray();
        assertEquals(monthDateBuilderFeb.getNameOfMonth(), "February");
        assertEquals(monthDateBuilderFeb.getFirstDayOfMonth().toString(), "3");
        assertEquals(monthDateArrayFeb[2], " ");
        assertEquals(monthDateArrayFeb[3], "1");
        assertEquals(monthDateArrayFeb[30], "28");
        assertEquals(monthDateArrayFeb[34], " ");
    }

    @Test
    public void testMar() throws Exception {
        MonthDateBuilder monthDateBuilderMar = new MonthDateBuilder(2, 2018);
        assertEquals(monthDateBuilderMar.getNameOfMonth(), "March");
        assertEquals(monthDateBuilderMar.getFirstDayOfMonth().toString(), "4");
    }

    @Test
    public void testApr() throws Exception {
        MonthDateBuilder monthDateBuilderApr = new MonthDateBuilder(3, 2018);
        String[] monthDateArrayApr = monthDateBuilderApr.getMonthDateArray();
        assertEquals(monthDateBuilderApr.getNameOfMonth(), "April");
        assertEquals(monthDateBuilderApr.getFirstDayOfMonth().toString(), "0");
        assertEquals(monthDateArrayApr[0], "1");
        assertEquals(monthDateArrayApr[29], "30");
        assertEquals(monthDateArrayApr[34], " ");
    }

    @Test
    public void testJune() throws Exception {
        MonthDateBuilder monthDateBuilderJune = new MonthDateBuilder(5, 2018);
        String[] monthDateArrayJune = monthDateBuilderJune.getMonthDateArray();
        assertEquals(monthDateBuilderJune.getNameOfMonth(), "June");
        assertEquals(monthDateBuilderJune.getFirstDayOfMonth().toString(), "5");
        assertEquals(monthDateArrayJune[4], " ");
        assertEquals(monthDateArrayJune[5], "1");
        assertEquals(monthDateArrayJune[34], "30");
        assertEquals(monthDateArrayJune[35], " ");
    }

    @Test
    public void testJuly() throws Exception {
        MonthDateBuilder monthDateBuilderJuly = new MonthDateBuilder(6, 2017);
        assertEquals(monthDateBuilderJuly.getNameOfMonth(), "July");
        assertEquals(monthDateBuilderJuly.getFirstDayOfMonth().toString(), "6");
    }

    @Test
    public void testAug() throws Exception {
        MonthDateBuilder monthDateBuilderAug = new MonthDateBuilder(7, 2017);
        assertEquals(monthDateBuilderAug.getNameOfMonth(), "August");
        assertEquals(monthDateBuilderAug.getFirstDayOfMonth().toString(), "2");
    }

    @Test
    public void testNov() throws Exception {
        MonthDateBuilder monthDateBuilderNov = new MonthDateBuilder(10, 2017);
        String[] monthDateArrayNov = monthDateBuilderNov.getMonthDateArray();
        assertEquals(monthDateBuilderNov.getNameOfMonth(), "November");
        assertEquals(monthDateBuilderNov.getFirstDayOfMonth().toString(), "3");
        assertEquals(monthDateArrayNov[2], " ");
        assertEquals(monthDateArrayNov[3], "1");
        assertEquals(monthDateArrayNov[20], "18");
        assertEquals(monthDateArrayNov[34], " ");
    }

}
```
