# YuchenHe98
###### \java\seedu\address\logic\commands\AddScheduleCommandTest.java
``` java
public class AddScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeTypicalPersonSuccess() throws Exception {

        AddScheduleCommand addScheduleCommand = prepareCommand(Index.fromZeroBased(0),
                new Day("Monday"), new Time("0900"), new Time("1100"));
        Slot slot = new Slot(new Day("Monday"), new Time("0900"), new Time("1100"));
        model.addScheduleToPerson(0, slot.getBusyTime());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addScheduleToPerson(0, slot.getBusyTime());
        String expectedMessage = String.format(AddScheduleCommand.MESSAGE_ADD_SCHEDULE_PERSON_SUCCESS);
        assertCommandSuccess(addScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() throws IllegalValueException {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddScheduleCommand addScheduleCommand = prepareCommand(outOfBoundIndex,
                new Day("Monday"), new Time("0900"), new Time("1100"));

        assertCommandFailure(addScheduleCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    private AddScheduleCommand prepareCommand(Index index, Day day, Time startTime, Time endTime)
            throws IllegalValueException {
        AddScheduleCommand addScheduleCommand = new AddScheduleCommand(index, day, startTime, endTime);
        addScheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addScheduleCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ArrangeCommandTest.java
``` java
/**
 * Arrange a meeting test. As all the tests for date and time classes are added independently, this test only includes
 * the command test with valid time and date form.
 */
public class ArrangeCommandTest {

    private Model model;
    private Model expectedModel;
    private ArrangeCommand arrangeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        int[] indexToTest = {1, 2};
        arrangeCommand = new ArrangeCommand(indexToTest);
        arrangeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void findCommonSlotSuccess() throws IllegalValueException, PersonNotFoundException {
        Slot firstSlot = new Slot(new Day("Monday"), new Time("0700"), new Time("1000"));
        Slot secondSlot = new Slot(new Day("Monday"), new Time("0930"), new Time("1100"));
        model.addScheduleToPerson(0, firstSlot.getBusyTime());
        model.addScheduleToPerson(1, secondSlot.getBusyTime());
        assertCommandSuccess(arrangeCommand, model, ArrangeCommand.MESSAGE_ARRANGE_PERSON_SUCCESS
                + arrangeCommand.scheduleInfo(), expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\FindByAddressCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindByAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate secondPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("second"));

        FindByAddressCommand findFirstCommand = new FindByAddressCommand(firstPredicate);
        FindByAddressCommand findSecondCommand = new FindByAddressCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindByAddressCommand findFirstCommandCopy = new FindByAddressCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        //assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindByAddressCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindByAddressCommand command = prepareCommand("wall michegan tokyo");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindByAddressCommand prepareCommand(String userInput) {
        FindByAddressCommand command =
                new FindByAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindByAddressCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\LocateCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class LocateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeValidIndexUnfilteredListSuccess() throws Exception {
        ReadOnlyPerson personToLocate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LocateCommand locateCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(LocateCommand.MESSAGE_LOCATE_PERSON_SUCCESS, personToLocate);


        assertEquals(expectedMessage, locateCommand.execute().feedbackToUser);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LocateCommand locateCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LocateCommand locateCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private LocateCommand prepareCommand(Index index) {
        LocateCommand locateCommand;
        locateCommand = new LocateCommand(index);
        locateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locateCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}

```
###### \java\seedu\address\logic\commands\VisualizeCommandTest.java
``` java
/**
 * test for visualizing a person's schedule
 */
public class VisualizeCommandTest {

    private Model model;
    private Model expectedModel;
    private VisualizeCommand visualizeCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        visualizeCommand = new VisualizeCommand(Index.fromOneBased(1));
        visualizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void findCommonSlotSuccess() throws IllegalValueException, PersonNotFoundException {
        Slot firstSlot = new Slot(new Day("Monday"), new Time("0700"), new Time("1000"));
        Slot secondSlot = new Slot(new Day("Tuesday"), new Time("0930"), new Time("1100"));
        model.addScheduleToPerson(0, firstSlot.getBusyTime());
        model.addScheduleToPerson(0, secondSlot.getBusyTime());
        assertCommandSuccess(visualizeCommand, model, VisualizeCommand.MESSAGE_VISUALIZE_PERSON_SUCCESS
                + "1" + visualizeCommand.scheduleInfo(), expectedModel);
    }
}

```
###### \java\seedu\address\logic\parser\AddScheduleCommandParserTest.java
``` java
public class AddScheduleCommandParserTest {
    private AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws IllegalValueException {

        Index exampleIndex = Index.fromOneBased(1);
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String input =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;
        assertParseSuccess(parser, input, new AddScheduleCommand(exampleIndex,
                exampleDay, exampleStartTime, exampleEndTime));
    }

    @Test
    public void parseInvalidTimeFailure() throws IllegalValueException {
        String tooEarly =  "1" + DAY_DESC_AMY
                + INVALID_ST_DESC
                + END_TIME_DESC_AMY;
        assertParseFailure(parser, tooEarly, "Not a proper time form");

        String tooLate =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + INVALID_ET_DESC;
        assertParseFailure(parser, tooLate, "Not a proper time form");
    }
}
```
###### \java\seedu\address\logic\parser\ArrangeCommandParserTest.java
``` java
public class ArrangeCommandParserTest {
    private ArrangeCommandParser parser = new ArrangeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        int[] indexList = { 1, 2, 3, 4, 5 };

        assertParseSuccess(parser, "1 2 3 4 5", new ArrangeCommand(indexList));

        assertParseSuccess(parser, "5 4 3 2 1", new ArrangeCommand(indexList));

        assertParseSuccess(parser, "4 1 3 5 2", new ArrangeCommand(indexList));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "a 1", MESSAGE_INVALID_COMMAND_FORMAT);

        assertParseFailure(parser, "0 1", MESSAGE_INVALID_COMMAND_FORMAT);

    }
}

```
###### \java\seedu\address\logic\parser\ClearScheduleCommandParserTest.java
``` java
public class ClearScheduleCommandParserTest {
    private ClearScheduleCommandParser parser = new ClearScheduleCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() throws IllegalValueException {

        Index exampleIndex = Index.fromOneBased(1);
        Day exampleDay = new Day("Friday");
        Time exampleStartTime = new Time("0730");
        Time exampleEndTime = new Time("1000");
        String input =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + END_TIME_DESC_AMY;
        assertParseSuccess(parser, input, new ClearScheduleCommand(exampleIndex,
                exampleDay, exampleStartTime, exampleEndTime));
    }

    @Test
    public void parseInvalidTimeFailure() throws IllegalValueException {
        String tooEarly =  "1" + DAY_DESC_AMY
                + INVALID_ST_DESC
                + END_TIME_DESC_AMY;
        assertParseFailure(parser, tooEarly, "Not a proper time form");

        String tooLate =  "1" + DAY_DESC_AMY
                + START_TIME_DESC_AMY
                + INVALID_ET_DESC;
        assertParseFailure(parser, tooLate, "Not a proper time form");
    }
}

```
###### \java\seedu\address\logic\parser\FindByAddressCommandParserTest.java
``` java
public class FindByAddressCommandParserTest {

    private FindByAddressCommandParser parser = new FindByAddressCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() {
        // no leading and trailing whitespaces
        FindByAddressCommand expectedCommand =
                new FindByAddressCommand(new AddressContainsKeywordsPredicate(Arrays.asList("street", "raffles")));
        assertParseSuccess(parser, "street raffles", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n street \n \t raffles  \t", expectedCommand);
    }

}
```
###### \java\seedu\address\logic\parser\LocateCommandParserTest.java
``` java
public class LocateCommandParserTest {

    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parseValidArgsReturnsDeleteCommand() {
        assertParseSuccess(parser, "1", new LocateCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\VisualizeCommandParserTest.java
``` java
public class VisualizeCommandParserTest {

    private VisualizeCommandParser parser = new VisualizeCommandParser();

    @Test
    public void parseValidArgsReturnsVisualizeCommand() {
        assertParseSuccess(parser, "1", new VisualizeCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VisualizeCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                VisualizeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\schedule\DayTest.java
``` java
public class DayTest {

    @Test
    public void isValidDay() {

        // blank input
        assertFalse(Day.isValidDay("")); // empty string
        assertFalse(Day.isValidDay(" ")); // spaces only

        // wrong input
        assertFalse(Day.isValidDay("someday"));
        assertFalse(Day.isValidDay("1"));

        // valid days
        assertTrue(Day.isValidDay("Monday"));
        assertTrue(Day.isValidDay("Tuesday"));
        assertTrue(Day.isValidDay("Wednesday"));
        assertTrue(Day.isValidDay("Thursday"));
        assertTrue(Day.isValidDay("Friday"));
        assertTrue(Day.isValidDay("Saturday"));
        assertTrue(Day.isValidDay("Sunday"));
    }
}
```
###### \java\seedu\address\model\schedule\ScheduleTest.java
``` java
public class ScheduleTest {

    @Test
    public void isValidSchedule() throws IllegalValueException {

        TreeSet<Integer> timeSet = new TreeSet<>();
        timeSet.add(10630);
        timeSet.add(20700);
        timeSet.add(30730);
        timeSet.add(80730);
        assertFalse(Schedule.isValidTimeSet(timeSet));
        timeSet.remove(80730);
        assertTrue(Schedule.isValidTimeSet(timeSet));
        Schedule testSchedule = new Schedule(timeSet);
        TreeSet[] tables = testSchedule.splitScheduleToDays();
        assertTrue(tables[0].contains(630));
        assertTrue(tables[1].contains(700));
        assertTrue(tables[2].contains(730));
    }
}
```
###### \java\seedu\address\model\schedule\SlotTest.java
``` java
public class SlotTest {

    @Test
    public void isValidSlot() throws IllegalValueException {

        // blank input
        assertFalse(Slot.isValidSlot(new Day("Tuesday"), new Time("0700"), new Time("0700")));
        assertFalse(Slot.isValidSlot(new Day("Tuesday"), new Time("1900"), new Time("1830")));
        // valid days
        assertTrue(Slot.isValidSlot(new Day("Tuesday"), new Time("0600"), new Time("2330")));
    }
}
```
###### \java\seedu\address\model\schedule\TimeTest.java
``` java
public class TimeTest {

    @Test
    public void isValidTime() {

        // blank input
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only

        // wrong input
        assertFalse(Time.isValidTime("0a30"));
        assertFalse(Time.isValidTime("600"));

        //too early or too late
        assertFalse(Time.isValidTime("0530"));
        assertFalse(Time.isValidTime("2400"));

        // valid days
        assertTrue(Time.isValidTime("0930"));
        assertTrue(Time.isValidTime("1700"));
        assertTrue(Time.isValidTime("0600"));
        assertTrue(Time.isValidTime("2330"));
    }
}

```
