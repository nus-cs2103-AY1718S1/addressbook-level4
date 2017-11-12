# Eric
###### \java\seedu\address\logic\commands\AddAppointmentCommandTest.java
``` java
public class AddAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void equals() throws ParseException, seedu.address.logic.parser.exceptions.ParseException {
        String arg = "Lunch, tomorrow 5pm";

        AddAppointmentCommand command = new AddAppointmentCommand(Index.fromOneBased(1), setAppointment(arg));
        AddAppointmentCommand command2 = new AddAppointmentCommand(Index.fromOneBased(1), setAppointment(arg));
        assertEquals(command, command2);
        assertNotEquals(command, new AddAppointmentCommand(Index.fromOneBased(2), setAppointment(arg)));
    }

    @Test
    public void execute() throws ParseException, CommandException {

        Index index1 = Index.fromOneBased(1);
        try {
            //Invalid date
            String arg = "lunch, yesterday 5pm";
            Command command = setCommand(index1, setAppointment(arg));
            CommandResult result = command.execute();
            assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

            //Set to valid date
            arg = "lunch, tomorrow 5pm";
            command = setCommand(index1, setAppointment(arg));
            result = command.execute();
            assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS);

            //Set to valid date with end time
            arg = "lunch, tomorrow 5pm to 7pm";
            command = setCommand(index1, setAppointment(arg));
            result = command.execute();
            assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS);
        } catch (seedu.address.logic.parser.exceptions.ParseException ive) {
            fail();
        }

    }

    @Test
    public void outOfBoundsIndex() throws CommandException, seedu.address.logic.parser.exceptions.ParseException {
        thrown.expect(CommandException.class);
        setCommand(Index.fromOneBased(100),
                AddAppointmentParser.getAppointmentFromString("lunch,tomorrow 5pm")).execute();
    }

    /**
     * Util methods to set appointment command
     */
    private Command setCommand(Index index, Appointment appointment) {
        AddAppointmentCommand command = new AddAppointmentCommand(index, appointment);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        return command;
    }

    private Appointment setAppointment(String str) throws seedu.address.logic.parser.exceptions.ParseException {
        return AddAppointmentParser.getAppointmentFromString(str);
    }

}
```
###### \java\seedu\address\logic\commands\CancelAppointmentCommandTest.java
``` java
public class CancelAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String personName = "Alice";
        String appointmentDescription  = "Lunch, tonight 5pm";
        CancelAppointmentCommand command = new CancelAppointmentCommand(personName, appointmentDescription);
        CancelAppointmentCommand command1 = new CancelAppointmentCommand(personName, appointmentDescription);

        assertEquals(command, command1);

        String personName1 = "Bob";
        command1 = new CancelAppointmentCommand(personName1, appointmentDescription);

        //Different name
        assertNotEquals(command, command1);

        String appointmentDescription1 = "Dinner, tonight 5pm";
        command = new CancelAppointmentCommand(personName1, appointmentDescription1);

        //Different appointment
        assertNotEquals(command, command1);

    }

    @Test
    public void noSuchPersonTest() throws CommandException {
        CancelAppointmentCommand command = new CancelAppointmentCommand("noSuchPerson", "Dinner, 5pm");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        thrown.expect(CommandException.class);
        command.executeUndoableCommand();
    }

    @Test
    public void noSuchAppointmentTest() throws CommandException {
        CancelAppointmentCommand command = new CancelAppointmentCommand("Alice Pauline", "Study, 5pm");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        thrown.expect(CommandException.class);
        command.executeUndoableCommand();
    }

}
```
###### \java\seedu\address\logic\commands\ToggleTagCommandTest.java
``` java
public class ToggleTagCommandTest {


    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {

        ToggleTagColorCommand testCommand = new ToggleTagColorCommand("", "");
        ToggleTagColorCommand testCommandTwo = new ToggleTagColorCommand("", "");
        //Test to ensure command is strictly a RemarkCommand
        assertFalse(testCommand.equals(new AddCommand(CARL)));
        assertFalse(testCommand.equals(new ClearCommand()));
        assertFalse(testCommand.equals(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(testCommand.equals(new HistoryCommand()));
        assertFalse(testCommand.equals(new HelpCommand()));
        assertFalse(testCommand.equals(new RedoCommand()));
        assertFalse(testCommand.equals(new UndoCommand()));
        assertFalse(testCommand.equals(new ListCommand()));
        assertFalse(testCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_AMY)));

        //Test for same object
        assertTrue(testCommand.equals(testCommand));
        assertTrue(testCommandTwo.equals(testCommandTwo));

        //Test to check for null
        assertFalse(testCommand == null);
        assertFalse(testCommandTwo == null);

        //Test to check different tag string returns false
        assertFalse(testCommand.equals(new ToggleTagColorCommand("aaa", "")));
        assertFalse(testCommandTwo.equals(new ToggleTagColorCommand("abc", "")));

    }

    @Test
    public void checkCommandResult() throws CommandException {

        //Check if the result message is correct when there is no tags found
        ToggleTagColorCommand command = new ToggleTagColorCommand("nosuchtag", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertTrue(command.execute().feedbackToUser.equals("No such tag"));

        resetAddressBook();

        //When tag can be found in addressBook
        command = new ToggleTagColorCommand("friends", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertFalse(command.execute().feedbackToUser.equals("No such tag"));
        assertTrue(command.execute().feedbackToUser.equals("friends tag color set to blue"));

        resetAddressBook();

        //Check if friends tags are set to color
        command = new ToggleTagColorCommand("friends", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertTrue(command.execute().feedbackToUser.equals("friends tag color set to blue"));
        for (Tag tag : model.getAddressBook().getTagList()) {
            if ("friends".equals(tag.tagName)) {
                assertTrue(tag.getTagColor().equals("blue"));
                assertFalse(tag.getTagColor().equals("pink"));
            }
        }

        //Check if color tag will off properly
        command = new ToggleTagColorCommand("off", null);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = command.execute();
        for (Tag tag : model.getAddressBook().getTagList()) {
            assertTrue(tag.getTagColor().equals("grey"));
            assertFalse(tag.getTagColor().equals("blue"));
        }
        assertTrue("tag color set to off".equals(commandResult.feedbackToUser));

        //Check if color will set to random
        command = new ToggleTagColorCommand("random", null);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        commandResult = command.execute();
        assertTrue("tag color set to random".equals(commandResult.feedbackToUser));
    }

    @Test
    public void checkNotNull() throws CommandException {

        ToggleTagColorCommand command = new ToggleTagColorCommand("nosuchtag", "blue");
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        assertNotNull(command.execute());
        assertNotNull(command);

    }

    private void resetAddressBook() {
        model = new ModelManager(new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build(),
                new UserPrefs());
    }


}
```
###### \java\seedu\address\logic\parser\AddAppointmentParserTest.java
``` java
public class AddAppointmentParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddAppointmentParser parser = new AddAppointmentParser();

    @Test
    public void prefixesNotPresent() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("1 lunch tomorrow 5pm");
    }

    @Test
    public void illegalExpression() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("n/@@@@ d/2018/02/10 10:10");
    }

    @Test
    public void nonParsableString() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("appt 1 d/lunch ,cant parse this string");
    }
    @Test
    public void parseDateExpression() throws ParseException, java.text.ParseException {

        AddAppointmentCommand command = parser.parse("appt 1 d/Lunch, tomorrow 5pm");
        Appointment appointment = AddAppointmentParser.getAppointmentFromString("Lunch, tomorrow 5pm");
        assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), appointment), command);

    }

    @Test
    public void parseAppointmentsWithDuration() {

        try {
            AddAppointmentCommand command = parser.parse("appt 1 d/Lunch, tomorrow 5pm to 7pm");
            Appointment appointment = AddAppointmentParser.getAppointmentFromString("Lunch, tomorrow 5pm to 7pm");
            assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), appointment), command);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
```
###### \java\seedu\address\logic\parser\CancelAppointmentParserTest.java
``` java
public class CancelAppointmentParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseException() throws ParseException {
        String str = "this cant be parsed";
        CancelAppointmentParser parser = new CancelAppointmentParser();
        thrown.expect(ParseException.class);
        parser.parse(str);
    }

    @Test
    public void parseWithoutKeyWordWith() throws ParseException {
        String str = "Lunch Alice Pauline";
        CancelAppointmentParser parser = new CancelAppointmentParser();
        thrown.expect(ParseException.class);
        parser.parse(str);
    }

    @Test
    public void parseSuccess() throws ParseException {
        String str = "Lunch with Alice Pauline";
        CancelAppointmentParser parser = new CancelAppointmentParser();
        CancelAppointmentCommand command = (CancelAppointmentCommand) parser.parse(str);
        CancelAppointmentCommand command2 = new CancelAppointmentCommand("Alice Pauline", "Lunch");

        assertEquals(command, command2);

    }
}
```
###### \java\seedu\address\logic\parser\ToggleTagColorParserTest.java
``` java
    @Test
    public void parseMoreThanTwoWords() throws Exception {
        ToggleTagColorParser parser = new ToggleTagColorParser();
        thrown.expect(ParseException.class);
        parser.parse("This is longer than what is accepted");
    }
```
###### \java\seedu\address\model\AppointmentListTest.java
``` java
public class AppointmentListTest {

    @Test
    public void equals() throws ParseException {

        AppointmentList emptyList = new AppointmentList();
        AppointmentList emptyList2 = new AppointmentList();

        assertEquals(emptyList, emptyList2);
        try {
            String apptString = "Lunch, tomorrow 5pm to 7pm";
            List<Appointment> appts = new ArrayList<>();
            appts.add(getAppointmentFromString(apptString));

            AppointmentList appointmentList = new AppointmentList(appts);
            AppointmentList appointmentList1 = new AppointmentList(appts);

            assertEquals(appointmentList, appointmentList1);
        } catch (ParseException e) {
            throw new ParseException("wrong appointment string");
        }
    }

    @Test
    public void contains() throws ParseException {

        try {
            String apptString = "Lunch, tomorrow 5pm to 7pm";
            Appointment appt = getAppointmentFromString(apptString);
            List<Appointment> appts = new ArrayList<>();
            appts.add(appt);

            AppointmentList appointmentList = new AppointmentList(appts);
            assertTrue(appointmentList.contains(appt));

            String apptStringNotInList = "Not in List, tomorrow 5pm";
            appt = getAppointmentFromString(apptStringNotInList);

            assertFalse(appointmentList.contains(appt));

        } catch (ParseException e) {
            throw new ParseException("wrong appointment string");
        }
    }

    @Test
    public void addAppointmentsInChronologicalOrder() throws ParseException {
        try {
            List<Appointment> appts = new ArrayList<>();

            appts.add(getAppointmentFromString("Lunch, tomorrow 2pm to 3pm"));
            appts.add(getAppointmentFromString("Lunch, tomorrow 12pm to 2pm"));
            appts.add(getAppointmentFromString("Lunch, tomorrow 1pm to 2pm"));

            //empty list should be sorted
            AppointmentList appointmentList = new AppointmentList();
            assertTrue(sorted(appointmentList));

            //Should be sorted
            appointmentList = new AppointmentList(appts);
            assertTrue(sorted(appointmentList));

        } catch (ParseException e) {
            throw new ParseException("wrong appointment string");
        }
    }

    /**
     * Util method to help check if the appts are sorted
     */
    private boolean sorted(AppointmentList appts) {
        List<Appointment> apptList = appts.toList();
        for (int i = 0; i < apptList.size() - 1; i++) {
            if (apptList.get(i + 1).getDate().before(apptList.get(i).getDate())) {
                return false;
            }
        }
        return true;
    }


    private Appointment getAppointmentFromString(String str) throws ParseException {
        return AddAppointmentParser.getAppointmentFromString(str);
    }
}
```
###### \java\seedu\address\model\person\AppointmentTest.java
``` java
public class AppointmentTest {

    @Test
    public void equals() {

        Calendar calendar = Calendar.getInstance();
        String apptString = "Test";
        Appointment appt = new Appointment(apptString, calendar, calendar);
        Appointment appt2 = new Appointment(apptString, calendar, calendar);

        assertEquals(appt, appt2);
    }

    @Test
    public void toStringTest() {
        Calendar calendar = Calendar.getInstance();
        String apptString = "Test";
        Appointment appt = new Appointment(apptString, calendar, calendar);

        assertEquals(appt.toString(), "Appointment on " + appt.getDateInStringFormat());
    }
}
```
###### \java\seedu\address\model\UniquePersonListTest.java
``` java
    @Test
    public void addAppointmentThrowsNoPersonFoundException() throws PersonNotFoundException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);

        try {
            uniquePersonList.addAppointment(TypicalPersons.ALICE,
                    AddAppointmentParser.getAppointmentFromString("lunch, tomorrow 5pm"));
        } catch (seedu.address.logic.parser.exceptions.ParseException e) {
            fail();
        }
    }
```
###### \java\seedu\address\model\UniqueTagListTest.java
``` java
    @Test
    public void tagsTests() throws IllegalValueException {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        uniqueTagList.setTags(TypicalPersons.BENSON.getTags());
        uniqueTagList.setTags(TypicalPersons.CARL.getTags());


        //Check contains tags
        assertFalse(uniqueTagList.contains(new Tag("friends", "")));
        assertFalse(uniqueTagList.contains(new Tag("aaaaaaaa", "")));

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertTrue("grey".equals(tag.getTagColor()));
        }

        uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        uniqueTagList.setTags(TypicalPersons.BENSON.getTags());
        uniqueTagList.setTags(TypicalPersons.CARL.getTags());

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertTrue("grey".equals(tag.getTagColor()));
        }

        Tag tag = new Tag("friends", "blue");

        //Test random color
        tag.setRandomColor();
        assertFalse("blue".equals(tag.getTagColor()));

        //Test set color
        tag.setColor("blue");
        assertTrue("blue".equals(tag.getTagColor()));

        //Test off color
        tag.setOffColor();
        assertTrue("grey".equals(tag.getTagColor()));


        //New tagList
        uniqueTagList = new UniqueTagList();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags = new HashSet<>(model.getAddressBook().getTagList());

        //Set all to random colors
        uniqueTagList.setTags(tags, "random", null);

        for (Tag tag1 : tags) {
            assertFalse("grey".equals(tag1.getTagColor()));
        }

        uniqueTagList = new UniqueTagList();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags1 = new HashSet<>(model.getAddressBook().getTagList());

        //Set all to grey
        uniqueTagList.setTags(tags1, "off", null);

        for (Tag tag1 : tags) {
            assertTrue("grey".equals(tag1.getTagColor()));
        }

        uniqueTagList = new UniqueTagList();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags2 = new HashSet<>(model.getAddressBook().getTagList());

        //Set friends to blue
        uniqueTagList.setTags(tags, "friends", "blue");

        for (Tag tagtest : tags2) {
            if ("friends".equals(tagtest.tagName)) {
                assertTrue("blue".equals(tagtest.getTagColor()));
            }
        }

    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets appointment with Date of the person that we are building
     */
    public PersonBuilder withAppointment(String... arg) {
        List<Appointment> list = new ArrayList<>();
        for (String s : arg) {
            try {
                list.add(AddAppointmentParser.getAppointmentFromString(s));
            } catch (seedu.address.logic.parser.exceptions.ParseException e) {
                e.printStackTrace();
            }
        }
        this.person.setAppointment(list);
        return this;
    }
```
###### \java\seedu\address\ui\CalendarWindowTest.java
``` java
public class CalendarWindowTest extends GuiUnitTest {

    private CalendarWindow calendarWindow;
    @Before
    public void setUp() {
        calendarWindow = new CalendarWindow(TypicalPersons.getTypicalAddressBook().getPersonList());
        uiPartRule.setUiPart(calendarWindow);
    }

    @Test
    public void display() {
        assertNotNull(calendarWindow.getRoot());
        //default page should be daily
        assertEquals(calendarWindow.getRoot().getSelectedPage(), calendarWindow.getRoot().getDayPage());
    }
}
```
###### \java\systemtests\AppointmentSystemTest.java
``` java
public class AppointmentSystemTest extends AddressBookSystemTest {

    @Test
    public void addAndRemoveAppointment() throws Exception {
        Model model = getModel();
        ReadOnlyPerson toAddAppointment = model.getAddressBook().getPersonList().get(0);
        String description = "dinner";
        String str = " 1 d/" + description + ", tonight 7pm to 10pm";
        String command = AddAppointmentCommand.COMMAND_WORD + str;
        assertCommandSuccess(command, toAddAppointment, AddAppointmentParser.getAppointmentFromString(str));

        command = CancelAppointmentCommand.COMMAND_WORD + " " + description + " with " + toAddAppointment.getName();
        assertCommandSuccess(command, toAddAppointment, AddAppointmentParser.getAppointmentFromString(str));
    }


    @Test
    public void changeCalendarView() {
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " d", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " w", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " m", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " y", CalendarViewCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(CalendarViewCommand.COMMAND_WORD + " q",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarViewCommand.MESSAGE_USAGE));
    }

    /**
     * Performs verification that calendarview changed
     */
    private void assertCommandSuccess(String command, String message) {
        executeCommand(command);
        assertEquals(getResultDisplay().getText() , message);
    }

    /**
     * Performs verification that the expected model is the same after command is executing
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd, Appointment appointment) {
        Model expectedModel = getModel();
        String expectedResultMessage;

        try {
            if (!command.contains("cancel")) {
                expectedModel.addAppointment(toAdd, appointment);
                expectedResultMessage = AddAppointmentCommand.MESSAGE_SUCCESS;
            } else {
                expectedModel.removeAppointment(toAdd, appointment);
                expectedResultMessage = CancelAppointmentCommand.MESSAGE_SUCCESS;
            }
        } catch (PersonNotFoundException e) {
            throw new IllegalArgumentException("person not found in model.");
        }

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     *
     * @see AppointmentSystemTest#assertCommandSuccess(String, ReadOnlyPerson, Appointment)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }

}
```
