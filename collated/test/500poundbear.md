# 500poundbear
###### /java/seedu/address/logic/commands/RemarkCommandTest.java
``` java
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Hihi").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON,
                editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));

        // Returns true with itself
        assertTrue(standardCommand.equals(standardCommand));

        // Returns true with same values
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Returns false with null
        assertFalse(standardCommand.equals(null));

        // Returns false with different command types
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Returns false with different indexes
        RemarkCommand commandWithDifferentIndex = new RemarkCommand(INDEX_SECOND_PERSON,
                new Remark(VALID_REMARK_AMY));
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // Returns false with different remarks
        RemarkCommand commandWithDifferentRemarks = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(VALID_REMARK_BOB));
        assertFalse(standardCommand.equals(commandWithDifferentRemarks));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### /java/seedu/address/logic/commands/StatisticsCommandTest.java
``` java
public class StatisticsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_statistics_success() throws CommandException {
        CommandResult result = new StatisticsCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ToggleStatisticsPanelEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### /java/seedu/address/model/person/RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // Return true if same object
        assertTrue(remark.equals(remark));

        // Returns true if remarks have the same value
        Remark remarkSameValue = new Remark(remark.value);
        assertTrue(remark.equals(remarkSameValue));

        // Returns false if different type
        assertFalse(remark.equals(1));

        // Returns false if null
        assertFalse(remark.equals(null));

        // Returns false if different person
        Remark differentRemark = new Remark("Hey");
        assertFalse(remark.equals(differentRemark));
    }
}
```
###### /java/seedu/address/model/StatisticsTest.java
``` java
/**
 * Tests for Statistics model
 */
public class StatisticsTest {
    private AddressBook addressBook1 = new AddressBookBuilder()
            .withPerson(ALICE)
            .withPerson(BENSON)
            .withPerson(CARL)
            .withPerson(DANIEL)
            .withPerson(ELLE)
            .withPerson(FIONA)
            .withPerson(GEORGE)
            .build();

    private AddressBook addressBook2 = new AddressBookBuilder()
            .withPerson(ELLE)
            .withPerson(FIONA)
            .withPerson(GEORGE)
            .withPerson(ALICE)
            .build();

    private ObservableList<ReadOnlyPerson> allPersonsList1 = addressBook1.getPersonList();
    private ObservableList<ReadOnlyPerson> allPersonsList2 = addressBook2.getPersonList();

    private Statistics statistics;

    @Test
    public void getTotalNumberOfPeopleTest() {

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.getTotalNumberOfPeople().intValue(), 7);

        statistics = new Statistics(allPersonsList2, 12, 2017);
        assertEquals(statistics.getTotalNumberOfPeople().intValue(), 4);
    }

    @Test
    public void getTotalNumberOfNoFacebookRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoFacebook().intValue(), 3);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoFacebook().intValue(), 2);
    }

    @Test
    public void getTotalNumberOfNoTwitterRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoTwitter().intValue(), 1);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoTwitter().intValue(), 1);

    }

    @Test
    public void getTotalNumberOfNoInstagramRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoInstagram().intValue(), 2);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoInstagram().intValue(), 1);

    }

    @Test
    public void getNewPersonsAddByMonthTest() {

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(2),
                new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));

        statistics = new Statistics(allPersonsList1, 6, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(2),
                new ArrayList<Integer>(Arrays.asList(5, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));

        statistics = new Statistics(allPersonsList1, 6, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(0),
                new ArrayList<Integer>(Arrays.asList(5)));


        statistics = new Statistics(allPersonsList2, 12, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(1),
                new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 1)));

    }

}
```
