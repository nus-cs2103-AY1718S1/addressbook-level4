# limcel
###### /java/guitests/guihandles/ExtendedPersonCardHandle.java
``` java
/**
 * Provides a handle to a person card in the person list panel.
 */
public class ExtendedPersonCardHandle extends NodeHandle<Node> {
    public static final String EXTENDED_PERSON_CARD_ID = "#extendedPersonCardPlaceholder";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String FORMCLASS_FIELD_ID = "#formClass";
    private static final String GRADES_FIELD_ID = "#grades";
    private static final String POSTALCODE_FIELD_ID = "#postalCode";
    private static final String REMARK_FIELD_ID = "#remark";


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

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.formClassLabel = getChildNode(FORMCLASS_FIELD_ID);
        this.gradesLabel = getChildNode(GRADES_FIELD_ID);
        this.postalCodeLabel = getChildNode(POSTALCODE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
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
###### /java/seedu/address/commons/core/MessagesTest.java
``` java
public class MessagesTest {

    @Test
    public void testMessageClass() {
        Messages messageClass = new Messages();

        assertTrue("Unknown command".equals(messageClass.MESSAGE_UNKNOWN_COMMAND));
        assertTrue("Invalid command format! \n%1$s".equals(messageClass.MESSAGE_INVALID_COMMAND_FORMAT));
        assertTrue("The person index provided is invalid"
                .equals(messageClass.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        assertTrue("The schedule index provided is invalid"
                .equals(messageClass.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX));
        assertTrue("%1$d persons listed!".equals(messageClass.MESSAGE_PERSONS_LISTED_OVERVIEW));
        assertTrue("Add or edit command do not accept/change remarks."
                .equals(messageClass.MESSAGE_ADDEDITCOMMANDREMARK_INVALID));
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
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
###### /java/seedu/address/logic/commands/DeleteScheduleCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteScheduleCommand}.
 */
public class DeleteScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexScheduleList_success() throws Exception {

        model = createAndAddScheduleToModel(model);
        Schedule scheduleToDelete = model.getScheduleList().get(INDEX_FIRST_SCHEDULE.getZeroBased());
        DeleteScheduleCommand deleteScheduleCommand = prepareCommand(INDEX_FIRST_SCHEDULE);

        String expectedMessage = String.format(DeleteScheduleCommand.MESSAGE_DELETE_SCHEDULE_SUCCESS,
                scheduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeSchedule(scheduleToDelete);

        assertCommandSuccess(deleteScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexScheduleList_throwsCommandException() {

        Index outOfBoundIndex = INDEX_SECOND_SCHEDULE;
        // ensures that outOfBoundIndex is not in bounds of address book list, because
        // schedule list contains zero schedules by default
        assertFalse(outOfBoundIndex.getZeroBased() < model.getAddressBook().getScheduleList().size());

        DeleteScheduleCommand deleteScheduleCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteScheduleCommand, model, Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteScheduleCommand deleteFirstScheduleCommand = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        DeleteScheduleCommand deleteSecondScheduleCommand = new DeleteScheduleCommand(INDEX_SECOND_SCHEDULE);

        // same object -> returns true
        assertTrue(deleteFirstScheduleCommand.equals(deleteFirstScheduleCommand));

        // same values -> returns true
        DeleteScheduleCommand deleteFirstCommandCopy = new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE);
        assertTrue(deleteFirstScheduleCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstScheduleCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstScheduleCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstScheduleCommand.equals(deleteSecondScheduleCommand));
    }

    //======================================= HELPER METHODS ==========================================
    /**
     * Returns a {@code DeleteScheduleCommand} with the parameter {@code index}.
     */
    private DeleteScheduleCommand prepareCommand(Index index) {
        DeleteScheduleCommand deleteScheduleCommand = new DeleteScheduleCommand(index);
        deleteScheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteScheduleCommand;
    }

    /**
     * Updates {@code model}'s list to show no schedule.
     */
    private void showNoSchedule(Model model) {
        assert model.getScheduleList().isEmpty();
    }

    /**
     * Creates and add schedule to the model.
     */
    private Model createAndAddScheduleToModel(Model model) throws PersonNotFoundException {
        Calendar calendar = Calendar.getInstance();
        Schedule newSchedule = new Schedule(ALICE.getName().toString(), calendar);
        model.addSchedule(newSchedule);
        return model;
    }

}
```
###### /java/seedu/address/logic/commands/ScheduleCommandTest.java
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

        Schedule schedulePerson = new Schedule(model.getFilteredPersonList().get(0).getName().toString(), calendar);
        ScheduleCommand scheduleCommand = new ScheduleCommand(firstPersonIndex, calendar);
        Model model1 = createAndSetModel(scheduleCommand);
        model1.addPerson(TypicalPersons.ALICE);
        CommandResult result = scheduleCommand.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        //Add the schedule to the expected model to compare with the model created
        expectedModel.addSchedule(schedulePerson);

        String expectedMessage = "Added " + schedulePerson.getPersonName() + " to consultations schedule "
                + "on " + schedulePerson.getDate().toString() + ".\n"
                + "Use 'viewsch' or 'viewschedule' command to view all your schedules.";

        assertEquals(expectedMessage , result.feedbackToUser);

    }

    @Test
    public void executeScheduleCommand_targetIndexExceededListSize() throws CommandException, DuplicatePersonException {
        Index targetIndex = Index.fromOneBased(1000);
        ScheduleCommand scheduleCommand = createNewScheduleCommand(targetIndex);
        Model model = createAndSetModel(scheduleCommand);
        model.addPerson(TypicalPersons.ALICE);
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
        String personName = newSchedule.getPersonName();
        assertEquals(expectedPersonName, personName);

        String expectedDate = date.getTime().toString();
        String dateInSchedule = newSchedule.getDate().toString();
        assertEquals(expectedDate, dateInSchedule);

    }

    //================================= HELPER METHODS =====================================

    /**
     * Returns a model that is set
     */
    private Model createAndSetModel(ScheduleCommand scheduleCommand) {
        Model model = new ModelManager();
        scheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return model;
    }

    /**
     * Creates a new ScheduleCommand
     */
    private ScheduleCommand createNewScheduleCommand(Index index) {
        Calendar date = Calendar.getInstance();
        ScheduleCommand scheduleCommand = new ScheduleCommand(index, date);
        return scheduleCommand;
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        sortCommand = prepareCommand(model);
    }

    @Test
    public void execute_sortingList_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        sortCommand = prepareCommand(model);
        CommandResult result = sortCommand.execute();
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }

    //======================================== HELPER METHODS ==========================================
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
###### /java/seedu/address/logic/commands/ViewScheduleCommandTest.java
``` java
public class ViewScheduleCommandTest {
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
    public void executeViewScheduleCommand_success() throws PersonNotFoundException {
        Model model = new ModelManager();
        Calendar date = Calendar.getInstance();
        Schedule newSchedule = new Schedule(getTypicalAddressBook().getPersonList().get(0).getName().toString(), date);
        ViewScheduleCommand newViewCommand = new ViewScheduleCommand();
        model.addSchedule(newSchedule);
        newViewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        ObservableList<Schedule> newScheduleList = model.getAddressBook().getScheduleList();
        String expectedMessage = "Listed your schedule. \n" + newScheduleList.toString();

        CommandResult result = newViewCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);

    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
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

    @Test
    public void parseCommand_viewSchedule() throws Exception {
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_WORD) instanceof ViewScheduleCommand);
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_WORD + " 3") instanceof ViewScheduleCommand);
    }

    @Test
    public void parseCommand_alias_viewSchedule() throws Exception {
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_ALIAS) instanceof ViewScheduleCommand);
        assertTrue(parser.parseCommand(ViewScheduleCommand.COMMAND_ALIAS + " 3") instanceof ViewScheduleCommand);
    }

    @Test
    public void parseCommand_deleteSchedule() throws Exception {
        DeleteScheduleCommand command = (DeleteScheduleCommand) parser.parseCommand(
                DeleteScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_SCHEDULE.getOneBased());
        assertEquals(new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE), command);
    }

    @Test
    public void parseCommand_alias_deleteSchedule() throws Exception {
        DeleteScheduleCommand command = (DeleteScheduleCommand) parser.parseCommand(
                DeleteScheduleCommand.COMMAND_ALIAS + " " + INDEX_FIRST_SCHEDULE.getOneBased());
        assertEquals(new DeleteScheduleCommand(INDEX_FIRST_SCHEDULE), command);
    }
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
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
###### /java/seedu/address/logic/parser/ScheduleCommandParserTest.java
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
###### /java/seedu/address/model/AddressBookTest.java
``` java
    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getScheduleList().remove(0);
    }
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
        @Override
        public ObservableList<Schedule> getScheduleList() {
            return schedules;
        }
```
###### /java/seedu/address/model/ModelManagerTest.java
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
###### /java/seedu/address/model/person/PostalCodeTest.java
``` java
public class PostalCodeTest {
    @Test
    public void isValidPostalCode() {
        // invalid PostalCodes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("6239029")); // numbers that are not exactly 6 digits
        assertFalse(PostalCode.isValidPostalCode("!@@#$#@$#")); // contains random symbols
        assertFalse(PostalCode.isValidPostalCode("97jhb9")); // contains alphanumeric numbers
        assertFalse(PostalCode.isValidPostalCode("gfxgfx")); // contains purely alphabets

        // valid PostalCodes
        assertTrue(PostalCode.isValidPostalCode("672943")); // PostalCode number is exactly 6 numbers
    }
}
```
###### /java/seedu/address/model/UniquePersonListTest.java
``` java
public class UniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void test_sortedList() throws DuplicatePersonException {
        UniquePersonList uniquePersonList = new UniquePersonList();

        uniquePersonList.add(ALICE);
        uniquePersonList.add(ELLE);
        uniquePersonList.add(BENSON);

        assertFalse(isSorted(uniquePersonList));

        ObservableList<ReadOnlyPerson> sortedList = uniquePersonList.asObservableListSortedByName();
        assertTrue(isSorted(sortedList));
    }

    //======================================== HELPER METHODS ============================================

    /**
     * @param e
     * @return boolean of value true if the list is not sorted, false otherwise.
     */
    private boolean isSorted(UniquePersonList e) {
        Iterator<Person> iterator = e.iterator();
        while (iterator.hasNext()) {
            Person person1 = iterator.next();
            Person person2 = iterator.hasNext() ? iterator.next() : null;
            if (person2 != null) {
                if (person1.getName().toString().toLowerCase().compareTo(person2.getName().toString().toLowerCase())
                        < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param e
     * @return boolean of value true if the list is sorted, false otherwise.
     */
    private boolean isSorted(ObservableList<ReadOnlyPerson> e) {
        Iterator<ReadOnlyPerson> iterator = e.iterator();
        while (iterator.hasNext()) {
            ReadOnlyPerson person1 = iterator.next();
            ReadOnlyPerson person2 = iterator.hasNext() ? iterator.next() : null;
            if (person2 != null) {
                if (person1.getName().toString().toLowerCase().compareTo(person2.getName().toString().toLowerCase())
                        > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
```
###### /java/seedu/address/model/UniqueScheduleListTest.java
``` java
public class UniqueScheduleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Schedule scheduleOne;
    private Schedule scheduleTwo;

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueScheduleList.asObservableList().remove(0);
    }

    @Test
    public void compareScheduleTest() throws ParseException {
        scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), getDate());
        scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), getDate());
        assert !scheduleOne.equals(scheduleTwo);
    }

    // Check whether schedule set is non-null
    @Test
    public void scheduleSetUnitTest() {
        Set<Schedule> scheduleSet = new HashSet<Schedule>();
        scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), getDate());
        scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), getDate());
        UniqueScheduleList uniqueList = new UniqueScheduleList(scheduleSet);
        scheduleSet.add(scheduleOne);
    }

    @Test
    public void ifScheduleListContainsScheduleTest() throws ScheduleNotFoundException {
        UniqueScheduleList scheduleList = new UniqueScheduleList();
        scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), getDate());
        scheduleList.add(scheduleOne);
        assertTrue(scheduleList.contains(scheduleOne));
        scheduleList.remove(scheduleOne);
        assertFalse(scheduleList.contains(scheduleOne));
    }

    @Test
    public void test_chronologicallySortedList() throws ParseException {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        Calendar dateOne = getDate();
        Calendar dateTwo = getDate();
        dateOne.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2019-12-25 10:00:00"));
        dateTwo.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));
        scheduleOne = new Schedule(ALICE.getName().toString(), dateOne);
        scheduleTwo = new Schedule(ELLE.getName().toString(), dateTwo);

        uniqueScheduleList.add(scheduleOne);
        uniqueScheduleList.add(scheduleTwo);

        TestCase.assertTrue(isSorted(uniqueScheduleList));

        ObservableList<Schedule> sortedList = uniqueScheduleList.asObservableListSortedChronologically();
        assertTrue(isSorted(sortedList));
    }


    //===================================== HELPER METHODS ========================================

    /**
     * @return boolean of value true if the list is not sorted chronologically, false otherwise.
     */
    private boolean isSorted(UniqueScheduleList e) {
        Iterator<Schedule> iterator = e.iterator();
        while (iterator.hasNext()) {
            Schedule date1 = iterator.next();
            Schedule date2 = iterator.hasNext() ? iterator.next() : null;
            if (date2 != null) {
                if (date1.getDate().compareTo(date2.getDate()) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @returns true if the list is sorted chronologically, false otherwise.
     */
    private boolean isSorted(ObservableList<Schedule> e) {
        Iterator<Schedule> iterator = e.iterator();
        while (iterator.hasNext()) {
            Schedule date1 = iterator.next();
            Schedule date2 = iterator.hasNext() ? iterator.next() : null;
            if (date2 != null) {
                if (date1.getDate().compareTo(date2.getDate()) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the instance of date
     */
    private Calendar getDate() {
        Calendar date = Calendar.getInstance();
        return date;
    }
```
###### /java/seedu/address/model/UniqueTagListTest.java
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
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
``` java
    @Test
    public void getScheduleList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getScheduleList().remove(0);
    }
```
###### /java/seedu/address/storage/XmlAddressBookStorageTest.java
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
        return expectedSchedule.equals(schedule);
    }
```
###### /java/seedu/address/ui/ExtendedPersonCardTest.java
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

    //======================================== HELPER METHODS ==========================================
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
