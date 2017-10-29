# Eric
###### \java\seedu\address\logic\commands\AddAppointmentCommandTest.java
``` java
public class AddAppointmentCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void equals() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand(Index.fromOneBased(1), calendar);

        assertEquals(command, new AddAppointmentCommand(Index.fromOneBased(1), calendar));
        assertNotEquals(command, new AddAppointmentCommand(Index.fromOneBased(2), calendar));
    }

    @Test
    public void execute() throws ParseException, CommandException {

        Index index1 = Index.fromOneBased(1);
        Index index100 = Index.fromOneBased(100);

        Calendar calendar = Calendar.getInstance();
        //Invalid date (i.e date before current instance)
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2005/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand(index1, calendar);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        CommandResult result = command.execute();

        //Invalid date message returned
        assertEquals(result.feedbackToUser, AddAppointmentCommand.INVALID_DATE);

        //Set to valid date
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2019/08/08 10:10"));

        command = new AddAppointmentCommand(index1, calendar);
        command.setData(model);
        result = command.execute();
        Appointment appointment = new Appointment(model.getFilteredPersonList().get(index1.getZeroBased())
                .getName().toString(),
                calendar);

        //Command success
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet "
                + appointment.getPersonName().toString() + " on "
                + appointment.getDate().toString());

        //No appointment set
        command = new AddAppointmentCommand();
        command.setData(model);
        result = command.execute();
        assertEquals(result.feedbackToUser, "Rearranged contacts to show upcoming appointments.");

        //Out of bounds index
        command = new AddAppointmentCommand(index100, calendar);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);

        //Out of bounds index
        thrown.expect(CommandException.class);
        command.execute();
    }

    @Test
    public void appointmentsWithDurationTest() throws ParseException, CommandException {
        Index index1 = Index.fromOneBased(1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2105/08/08 10:10"));
        Calendar calendar2 = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2106/08/08 10:10"));
        AddAppointmentCommand command = new AddAppointmentCommand(index1, calendar, calendar2);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model);
        CommandResult result = command.execute();
        Appointment appointment = new Appointment(model.getFilteredPersonList().get(index1.getZeroBased())
                .getName().toString(),
                calendar);
        assertEquals(result.feedbackToUser, AddAppointmentCommand.MESSAGE_SUCCESS + "Meet "
                + appointment.getPersonName().toString() + " on "
                + appointment.getDate().toString());

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
        parser.parse("Alice 2018/02/10 10:10");
    }

    @Test
    public void illegalExpression() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("n/@@@@ d/2018/02/10 10:10");
    }

    @Test
    public void nonParsableString() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("appt 1 d/cant parse this string");
    }
    @Test
    public void parseDateExpression() throws ParseException, java.text.ParseException {

        AddAppointmentCommand command = parser.parse("appt 1 d/The 30th of April in the year 2018 12am");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/04/30 00:00"));
        assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), calendar), command);

    }

    @Test
    public void parseEmptyExpression() {

        //No name and no date will just call the parser to return a command with no attributes initialized
        try {
            AddAppointmentCommand command = parser.parse("appointment");
            assertTrue(command.getIndex() == null);
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void parseOffAppointment() {
        try {
            AddAppointmentCommand command = parser.parse("appointment 1 d/off");
            assertTrue(command.getIndex().getOneBased() == 1);
            command.setData(new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs()));
            CommandResult result = command.execute();
            assertEquals(("Appointment with "
                    + TypicalPersons.getTypicalAddressBook().getPersonList().get(0).getName().toString()
                    + " set to off."), result.feedbackToUser);
        } catch (ParseException e) {
            fail(e.getMessage());
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void parseAppointmentsWithDuration() {

        try {
            AddAppointmentCommand command = parser.parse("appt 1 d/7am 5th april 2018 to 10am 5th april 2018");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/04/05 07:00"));
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(Appointment.DATE_FORMATTER.parse("2018/04/05 10:00"));
            assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), calendar, calendar2), command);
        } catch (java.text.ParseException | ParseException e) {
            e.printStackTrace();
        }
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
###### \java\seedu\address\model\UniquePersonListTest.java
``` java
    @Test
    public void addAppointmentReturnsCorrectPerson() throws DuplicatePersonException, PersonNotFoundException {

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Appointment.DATE_FORMATTER.parse("2018/10/10 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Appointment appointment = new Appointment (TypicalPersons.ALICE.getName().toString(), calendar);

        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(TypicalPersons.ALICE);

        uniquePersonList.addAppointment(appointment);

        assertTrue(uniquePersonList.contains(TypicalPersons.ALICE));

        for (ReadOnlyPerson person : uniquePersonList.asObservableList()) {
            if (person.getName().toString().equals(TypicalPersons.ALICE.getName().fullName)) {
                assertTrue(person.getAppointment().equals(appointment));
            }
        }
    }

    @Test
    public void addAppointmentThrowsNoPersonFoundException() throws PersonNotFoundException {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(PersonNotFoundException.class);
        uniquePersonList.addAppointment(new Appointment(TypicalPersons.ALICE.getName().toString()));
    }

    @Test
    public void testSortedAppointment() throws DuplicatePersonException {

        //ALICE appointment = 2018/01/02 00:00 BENSON appointment = 2018/01/01 00:00,
        //BENSON appointment should be before ALICE
        assertTrue(TypicalPersons.BENSON.getAppointment().getDate()
                .before(TypicalPersons.ALICE.getAppointment().getDate()));

        UniquePersonList list = new UniquePersonList();
        list.add(TypicalPersons.ALICE);
        list.add(TypicalPersons.BENSON);

        ObservableList<ReadOnlyPerson> sortedList = list.asObservableListSortedByAppointment();

        //Order should be BENSON then ALICE
        assertEquals(sortedList.get(0).getName(), TypicalPersons.BENSON.getName());
        assertEquals(sortedList.get(1).getName(), TypicalPersons.ALICE.getName());

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
    public PersonBuilder withAppointment(String name, String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Appointment.DATE_FORMATTER.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.person.setAppointment(new Appointment(person.getName().toString(), calendar));
        return this;
    }

    /**
     * With appointment that specified a endDate
     */
    public PersonBuilder withAppointment(String name, String date, String endDate) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar.setTime(Appointment.DATE_FORMATTER.parse(date));
            calendar1.setTime(Appointment.DATE_FORMATTER.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.person.setAppointment(new Appointment(person.getName().toString(), calendar, calendar1));
        return this;
    }

    /**
     * Sets an empty appointment with the person that we are building
     */
    public PersonBuilder withAppointment(String name) {
        this.person.setAppointment(new Appointment(person.getName().toString()));
        return this;
    }
```
