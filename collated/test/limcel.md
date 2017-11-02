# limcel
###### \java\guitests\guihandles\ExtendedPersonCardHandle.java
``` java
/**
 * Provides a handle to a person card in the person list panel.
 */
public class ExtendedPersonCardHandle extends NodeHandle<Node> {
    public static final String EXTENDED_PERSON_CARD_ID = "#extendedPersonCardPlaceholder";
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String FORMCLASS_FIELD_ID = "#formClass";
    private static final String GRADES_FIELD_ID = "#grades";
    private static final String POSTALCODE_FIELD_ID = "#postalCode";
    private static final String REMARK_FIELD_ID = "#remark";


    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label formClassLabel;
    private final Label gradesLabel;
    private final Label postalCodeLabel;
    private final Label remarkLabel;


    public ExtendedPersonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.formClassLabel = getChildNode(FORMCLASS_FIELD_ID);
        this.gradesLabel = getChildNode(GRADES_FIELD_ID);
        this.postalCodeLabel = getChildNode(POSTALCODE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getFormclass() {
        return formClassLabel.getText();
    }

    public String getGrades() {
        return gradesLabel.getText();
    }

    public String getPostalCode() {
        return postalCodeLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> sortByPersonName() throws NullPointerException {
            fail("This method should not be called.");
            return getFilteredPersonList();
        }

        @Override
        public void addSchedule(Schedule schedule) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void removeSchedule(Schedule schedule) throws ScheduleNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Schedule> getScheduleList() {
            fail("This method should not be called.");
            return getScheduleList();
        }
```
###### \java\seedu\address\logic\commands\ScheduleCommandTest.java
``` java
public class ScheduleCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void executeScheduleCommand_success() throws CommandException, ParseException, DuplicatePersonException {
        Index firstPersonIndex = Index.fromOneBased(1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));

        int listSize = model.getFilteredPersonList().size();
        Schedule schedulePerson = new Schedule(model.getFilteredPersonList().get(0).getName().toString(), calendar);
        ScheduleCommand scheduleCommand = new ScheduleCommand(firstPersonIndex, calendar);
        Model model1 = new ModelManager();
        model1.addPerson(TypicalPersons.ALICE);
        scheduleCommand.setData(model1, new CommandHistory(), new UndoRedoStack());
        CommandResult result = scheduleCommand.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        //Add the schedule to the expected model to compare with the model created
        expectedModel.addSchedule(schedulePerson);

        String expectedMessage = "Added " + schedulePerson.getPersonName() + " to consultations schedule "
                + "on " + schedulePerson.getDate().toString();

        assertEquals(result.feedbackToUser, expectedMessage);

    }

    @Test
    public void executeScheduleCommand_targetIndexExceededListSize() throws CommandException, DuplicatePersonException {
        Index targetIndex = Index.fromOneBased(1000);
        Calendar date = Calendar.getInstance();
        ScheduleCommand scheduleCommand = new ScheduleCommand(targetIndex, date);
        Model model = new ModelManager();
        model.addPerson(TypicalPersons.ALICE);
        scheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        thrown.expect(CommandException.class);
        scheduleCommand.execute();
    }

    @Test
    public void executeScheduleCommand_toStringUnitTest() {
        Calendar date = Calendar.getInstance();
        Schedule newSchedule = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        UniqueScheduleList newList = new UniqueScheduleList();
        newList.add(newSchedule);

        String expectedPersonName = "Alice Pauline";
        String personName = newSchedule.getPersonName().toString();
        assertEquals(expectedPersonName, personName);

        String expectedDate = date.getTime().toString();
        String dateInSchedule = newSchedule.getDate().toString();
        assertEquals(expectedDate, dateInSchedule);

    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortingList_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contacts by name in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

    @Test
    public void parseCommand_alias_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS + " 3") instanceof SortCommand);
    }
    @Test
    public void parseCommand_schedule() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-27 17:00:00"));
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SCHEDULE
                        + "27 December 2018 at 5pm");
        assertEquals(new ScheduleCommand(INDEX_FIRST_PERSON, calendar), command);
    }

    @Test
    public void parseCommand_alias_schedule() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SCHEDULE
                        + "25 December 2018 at 10am");
        assertEquals(new ScheduleCommand(INDEX_FIRST_PERSON, calendar), command);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseSchedule_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseSchedule(null);
    }

    @Test
    public void parseSchedule_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseSchedule(Optional.empty()).isPresent());
    }
```
###### \java\seedu\address\logic\parser\ScheduleCommandParserTest.java
``` java
public class ScheduleCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void noPrefixTest() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("1 tomorrow 10am");
    }
    @Test
    public void notEnoughArgumentsTest() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("s/next summer 12pm");
    }
    @Test
    public void noArgumentsTest() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("");
    }
    @Test
    public void parse_validArgs_returnsScheduleCommand() throws ParseException, java.text.ParseException {
        ScheduleCommand scheduleToAdd = parser.parse("1 s/25 December 2018 10am");
        Calendar date = Calendar.getInstance();
        date.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));
        assertEquals(new ScheduleCommand(Index.fromOneBased(1), date), scheduleToAdd);
    }

    @Test
    public void parse_invalidArgs_returnsScheduleCommand() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("1 s/this date cannot be read");
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getScheduleList().remove(0);
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        @Override
        public ObservableList<Schedule> getScheduleList() {
            return schedules;
        }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getScheduleList().remove(0);
    }

    @Test
    public void removeAndAddScheduleUnitTests() throws ScheduleNotFoundException {
        Calendar date = Calendar.getInstance();
        String personToAdd = TypicalPersons.ALICE.getName().toString();
        Schedule newSchedule = new Schedule(personToAdd, date);
        ModelManager modelManager = new ModelManager();

        modelManager.addSchedule(newSchedule);
        assertTrue(modelManager.getScheduleList().get(0).equals(newSchedule));

        modelManager.removeSchedule(newSchedule);
        assertTrue(modelManager.getScheduleList().size() == 0);
    }

    @Test
    public void testDeleteTag() throws PersonNotFoundException, IllegalValueException, TagNotFoundException {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager =  new ModelManager(addressBook, userPrefs);
        modelManager.deleteTag(new Tag("friends"));
        modelManager.deleteTag(new Tag("owesMoney"));

        // deletion of "friend","owesMoney" tag --> returns false
        AddressBook oldAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertFalse(addressBook.getPersonList().equals(oldAddressBook));
    }
```
###### \java\seedu\address\model\UniqueScheduleListTest.java
``` java
public class UniqueScheduleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueScheduleList.asObservableList().remove(0);
    }

    @Test
    public void compareScheduleTest() throws ParseException {
        Calendar date = Calendar.getInstance();
        Schedule scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        Schedule scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), date);
        if (scheduleOne.equals(scheduleTwo)) {
            assert false;
        }
    }

    // Check whether schedule set is non-null
    @Test
    public void scheduleSetUnitTest() {
        Calendar date = Calendar.getInstance();
        Set<Schedule> scheduleSet = new HashSet<Schedule>();
        Schedule scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        Schedule scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), date);
        UniqueScheduleList uniqueList = new UniqueScheduleList(scheduleSet);
        scheduleSet.add(scheduleOne);
    }

    @Test
    public void ifScheduleListContainsScheduleTest() throws ScheduleNotFoundException {
        Calendar date = Calendar.getInstance();
        UniqueScheduleList scheduleList = new UniqueScheduleList();
        Schedule scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        scheduleList.add(scheduleOne);
        assertTrue(scheduleList.contains(scheduleOne));
        scheduleList.remove(scheduleOne);
        assertFalse(scheduleList.contains(scheduleOne));
    }
}
```
###### \java\seedu\address\model\UniqueTagListTest.java
``` java
    @Test
    public void testForDuplicateTags() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        for (int i = 0; i < uniqueTagList.asObservableList().size(); i++) {
            thrown.expect(UniqueTagList.DuplicateTagException.class);
            uniqueTagList.asObservableList().remove(uniqueTagList.asObservableList().remove(i));
        }
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getScheduleList().remove(0);
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void createNewXmlAdaptedScheduleTest() throws IllegalValueException {
        Calendar date = Calendar.getInstance();
        String personToAdd = TypicalPersons.ALICE.getName().toString();

        Schedule expectedSchedule = new Schedule(personToAdd, date);

        XmlAdaptedSchedule newSchedule = new XmlAdaptedSchedule(expectedSchedule);

        UniqueScheduleList scheduleList = new UniqueScheduleList();
        scheduleList.add(newSchedule.toModelType());

        assertTrue(expectedSchedule, scheduleList.asObservableList().get(0));
    }

    //====================================== HELPER METHODS ========================================

    /**
     * Checks if the expectedSchedule is equals to the schedule in the storage
     */
    private boolean assertTrue(Schedule expectedSchedule, Schedule schedule) {
        if (expectedSchedule.equals(schedule)) {
            return true;
        }
        return false;
    }
```
###### \java\seedu\address\ui\ExtendedPersonCardTest.java
``` java
public class ExtendedPersonCardTest extends GuiUnitTest {
    private ExtendedPersonCard extendedPersonCard;
    private ExtendedPersonCardHandle extendedPersonCardHandle;

    @Before
    public void setUp() {
        try {
            guiRobot.interact(() -> extendedPersonCard = new ExtendedPersonCard());
            uiPartRule.setUiPart(extendedPersonCard);
            extendedPersonCardHandle = new ExtendedPersonCardHandle(extendedPersonCard.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
    @Test
    public void display() throws Exception {
        // select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPersonIsDisplayed(ALICE, extendedPersonCardHandle);
        // select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPersonIsDisplayed(BOB, extendedPersonCardHandle);
    }
    //======================== Helper methods ===============================
    /**
     * Asserts that {@code extended person card} displays details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(ReadOnlyPerson expectedPerson, ExtendedPersonCardHandle
            extendedPersonCardHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), extendedPersonCardHandle.getName());
        assertEquals(expectedPerson.getPhone().toString(), extendedPersonCardHandle.getPhone());
        assertEquals(expectedPerson.getAddress().toString(), extendedPersonCardHandle.getAddress());
        assertEquals(expectedPerson.getFormClass().toString(), extendedPersonCardHandle.getFormclass());
        assertEquals(expectedPerson.getGrades().toString(), extendedPersonCardHandle.getGrades());
        assertEquals(expectedPerson.getPostalCode().toString(), extendedPersonCardHandle.getPostalCode());
        assertEquals(expectedPerson.getEmail().toString(), extendedPersonCardHandle.getEmail());
        assertEquals(expectedPerson.getRemark().toString(), extendedPersonCardHandle.getRemark());
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code ExtendedPersonCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExtendedPersonCard extendedpersonCard, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        ExtendedPersonCardHandle extendedPersonCardHandle = new ExtendedPersonCardHandle(extendedPersonCard.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, extendedPersonCardHandle);
    }
}
```
