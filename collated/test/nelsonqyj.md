# nelsonqyj
###### \java\seedu\address\logic\commands\AddMeetingCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddMeetingCommand}.
 */
public class AddMeetingCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newMeeting_success() throws Exception {
        Person validPerson = new PersonBuilder().build();

        model.addPerson(validPerson);

        Meeting validMeeting = new MeetingBuilder().build();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addMeeting(validMeeting);

        assertCommandSuccess(prepareCommand(validMeeting, model), model,
                String.format(AddMeetingCommand.MESSAGE_SUCCESS, validMeeting), expectedModel);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() throws Exception {
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        Meeting newMeeting = new MeetingBuilder().build();
        model.addMeeting(newMeeting);
        Meeting meetingInList = new Meeting(model.getAddressBook().getMeetingList().get(0));
        assertCommandFailure(prepareCommand(meetingInList, model), model, AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    /**
     * Generates a new {@code AddMeetingCommand} which upon execution, adds {@code meeting} into the {@code model}.
     */
    private AddMeetingCommand prepareCommand(Meeting meeting, Model model) {
        List<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromOneBased(1));
        AddMeetingCommand command =
                new AddMeetingCommand(meeting.getName(), meeting.getDate(), meeting.getPlace(), indexes,
                        meeting.getMeetTag());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\commands\AddMeetingCommandTest.java
``` java
public class AddMeetingCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Index> indexes;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();
        Meeting validMeeting = new MeetingBuilder().build();

        CommandResult commandResult = getAddMeetingCommandForMeeting(validMeeting, modelStub).execute();

        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS, validMeeting), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validMeeting), modelStub.meetingsAdded);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateMeetingException();
        Meeting validMeeting = new MeetingBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);

        getAddMeetingCommandForMeeting(validMeeting, modelStub).execute();
    }


    @Test
    public void equals() {
        List<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromOneBased(1));
        Meeting project = new MeetingBuilder().withNameMeeting("Project").build();
        Meeting meeting = new MeetingBuilder().withNameMeeting("Meeting").build();
        AddMeetingCommand addProjectCommand = new AddMeetingCommand(project.getName(), project.getDate(),
                project.getPlace(), indexes, project.getMeetTag());
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(meeting.getName(), meeting.getDate(),
                meeting.getPlace(), indexes, meeting.getMeetTag());

        // same object -> returns true
        assertTrue(addProjectCommand.equals(addProjectCommand));


        // same values -> returns true
        //AddMeetingCommand addProjectCommandCopy = new AddMeetingCommand(project.getName(), project.getDate(),
        //        project.getPlace(), index);
        //assertTrue(addProjectCommand.equals(addProjectCommandCopy));

        // different types -> returns false
        assertFalse(addProjectCommand.equals(1));

        // null -> returns false
        assertFalse(addProjectCommand.equals(null));

        // different person -> returns false
        //assertFalse(addProjectCommand.equals(addMeetingCommand));
    }


    /**
     * Generates a new AddMeetingCommand with the details of the given meeting.
     */
    private AddMeetingCommand getAddMeetingCommandForMeeting(Meeting meeting, Model model) {
        List<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromOneBased(1));
        AddMeetingCommand command = new AddMeetingCommand(meeting.getName(), meeting.getDate(),
                meeting.getPlace(), indexes, meeting.getMeetTag());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteMeeting(ReadOnlyMeeting target) throws MeetingNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateMeeting(ReadOnlyMeeting target, ReadOnlyMeeting editedPerson)
                throws DuplicateMeetingException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public ObservableList<ReadOnlyMeeting> getFilteredMeetingList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredMeetingList(Predicate<ReadOnlyMeeting> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateMeetingException extends ModelStub {
        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            throw new DuplicateMeetingException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        final ArrayList<Meeting> meetingsAdded = new ArrayList<>();

        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            meetingsAdded.add(new Meeting(meeting));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\testutil\MeetingBuilder.java
``` java
/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_NAMEMEETING = "Project Meeting";
    public static final String DEFAULT_DATETIME = "27-01-2018 21:30";
    public static final String DEFAULT_PLACE = "School of Computing";
    public static final String DEFAULT_TAG = "1";

    private Meeting meeting;

    public MeetingBuilder() {
        try {
            List<Index> indexes = new ArrayList<>();
            indexes.add(Index.fromOneBased(1));
            NameMeeting defaultNameMeeting = new NameMeeting(DEFAULT_NAMEMEETING);
            DateTime defaultDateTime = new DateTime(DEFAULT_DATETIME);
            Place defaultPlace = new Place(DEFAULT_PLACE);
            List<ReadOnlyPerson> defaultPersonsMeet = new ArrayList<>();
            defaultPersonsMeet.add(getTypicalPersons().get(indexes.get(0).getZeroBased()));
            MeetingTag defaultMeetingTag = new MeetingTag(DEFAULT_TAG);

            this.meeting = new Meeting(defaultNameMeeting, defaultDateTime, defaultPlace,
                    defaultPersonsMeet, defaultMeetingTag);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default meeting's values are invalid.");
        }
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(ReadOnlyMeeting meetingToCopy) {
        this.meeting = new Meeting(meetingToCopy);
    }
```
