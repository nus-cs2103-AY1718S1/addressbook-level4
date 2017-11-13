# RonakLakhotia
###### \java\guitests\guihandles\ReminderCardHandle.java
``` java
/**
 * Provides a handle to a person card in the person list panel.
 */
public class ReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ABOUT_FIELD_ID = "#about";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DUE_DATE_FIELD_ID = "#date";

    private final Label idLabel;
    private final Label aboutLabel;
    private final Label priorityLabel;
    private final Label dateLabel;


    public ReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.aboutLabel = getChildNode(ABOUT_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);
        this.dateLabel = getChildNode(DUE_DATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDetails() {
        return aboutLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

    public String getDueDate() {
        return dateLabel.getText();
    }
}
```
###### \java\guitests\guihandles\ReminderListPanelHandle.java
``` java
/**
 * Provides a handle for {@code ReminderListPanel} containing the list of {@code ReminderCard}.
 */
public class ReminderListPanelHandle extends NodeHandle<ListView<ReminderCard>> {
    public static final String REMINDER_LIST_VIEW_ID = "#reminderListView";

    private Optional<ReminderCard> lastRememberedSelectedReminderCard;

    public ReminderListPanelHandle(ListView<ReminderCard> reminderListPanelNode) {
        super(reminderListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ReminderCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ReminderCardHandle getHandleToSelectedCard() {
        List<ReminderCard> reminderList = getRootNode().getSelectionModel().getSelectedItems();

        if (reminderList.size() != 1) {
            throw new AssertionError("Reminder list size expected 1.");
        }

        return new ReminderCardHandle(reminderList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<ReminderCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the reminder.
     */
    public void navigateToCard(ReadOnlyReminder reminder) {
        List<ReminderCard> cards = getRootNode().getItems();
        Optional<ReminderCard> matchingCard = cards.stream().filter(card -> card.reminder.equals(reminder)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Reminder does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the reminder card handle of a reminder associated with the {@code index} in the list.
     */
    public ReminderCardHandle getReminderCardHandle(int index) {
        return getReminderCardHandle(getRootNode().getItems().get(index).reminder);
    }

    /**
     * Returns the {@code ReminderCardHandle} of the specified {@code reminder} in the list.
     */
    public ReminderCardHandle getReminderCardHandle(ReadOnlyReminder reminder) {
        Optional<ReminderCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.reminder.equals(reminder))
                .map(card -> new ReminderCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Reminder does not exist."));
    }

    /**
     * Selects the {@code Reminder} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ReminderCard} in the list.
     */
    public void rememberSelectedReminderCard() {
        List<ReminderCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedReminderCard = Optional.empty();
        } else {
            lastRememberedSelectedReminderCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ReminderCard} is different from the value remembered by the most recent
     * {@code rememberSelectedReminderCard()} call.
     */
    public boolean isSelectedReminderCardChanged() {
        List<ReminderCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedReminderCard.isPresent();
        } else {
            return !lastRememberedSelectedReminderCard.isPresent()
                    || !lastRememberedSelectedReminderCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changedReminder) {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddReminderIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AddReminder}.
 */
public class AddReminderIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newReminder_success() throws Exception {
        Reminder validReminder = new ReminderBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addReminder(validReminder);

        assertCommandSuccess(prepareCommand(validReminder, model), model,
                String.format(AddReminder.MESSAGE_SUCCESS, validReminder), expectedModel);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder reminderInList = new Reminder(model.getAddressBook().getReminderList().get(0));
        assertCommandFailure(prepareCommand(reminderInList, model), model, AddReminder.MESSAGE_DUPLICATE_REMINDER);
    }

    /**
     * Generates a new {@code AddReminder} which upon execution, adds {@code reminder} into the {@code model}.
     */
    private AddReminder prepareCommand(Reminder reminder, Model model) {
        AddReminder command = new AddReminder(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\AddReminderTest.java
``` java
public class AddReminderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminder(null);
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = getAddCommandForReminder(validReminder, modelStub).execute();

        assertEquals(String.format(AddReminder.MESSAGE_SUCCESS, validReminder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateReminderException();
        Reminder validReminder = new ReminderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReminder.MESSAGE_DUPLICATE_REMINDER);

        getAddCommandForReminder(validReminder, modelStub).execute();
    }

    @Test
    public void equals() {
        Reminder assignment = new ReminderBuilder().withDetails("Assignment").build();
        Reminder meeting = new ReminderBuilder().withDetails("Meeting").build();
        AddReminder addAssignmentCommand = new AddReminder(assignment);
        AddReminder addMeetingCommand = new AddReminder(meeting);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        AddReminder addAssignmentCommandCopy = new AddReminder(assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand.equals(null));

        // different person -> returns false
        assertFalse(addAssignmentCommand.equals(addMeetingCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminder getAddCommandForReminder(Reminder reminder, Model model) {
        AddReminder command = new AddReminder(reminder);
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
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            fail("This method should not be called.");
        }
        @Override
        public void deleteReminder(ReadOnlyReminder reminder) throws ReminderNotFoundException {
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
        public void addPhotoPerson(ReadOnlyPerson person, String FilePath, Index targetIndex)
                throws PersonNotFoundException {
            fail("This method should not be called.");

        }
```
###### \java\seedu\address\logic\commands\AddReminderTest.java
``` java
        @Override
        public void clearBrowserPanel() {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }
        @Override
        public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changedReminder) {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
            fail("This method should not be called.");
        }
        @Override
        public void sendMailToContacts(String tagName, String subject, List<ReadOnlyPerson> lastShownList) {
            fail("This method should never be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateTagColorPair(Set<Tag> tagList, TagColor color) throws IllegalValueException {
            fail("This method should not be called.");
        }

        @Override
        public void faceBook(ReadOnlyPerson person) throws PersonNotFoundException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateReminderException when trying to add a reminder.
     */
    private class ModelStubThrowingDuplicateReminderException extends ModelStub {
        @Override
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            throw new DuplicateReminderException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            remindersAdded.add(new Reminder(reminder));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### \java\seedu\address\logic\commands\BackUpCommandTest.java
``` java
public class BackUpCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        BackUpCommand command = new BackUpCommand();
        BackUpCommand commandCopy = new BackUpCommand();

        assertFalse(command.equals(null));
        assertFalse(command.equals(0));
        assertTrue(command.equals(command));
        assertTrue(commandCopy.equals(commandCopy));


    }
    @Test
    public void check_backUpEventCollected() throws CommandException {
        BackUpCommand backupCommand = prepareCommand();
        backupCommand.executeUndoableCommand();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BackUpEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    private BackUpCommand prepareCommand() {
        BackUpCommand command = new BackUpCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\ChangeCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangeReminderCommand.
 */
public class ChangeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Reminder changedReminder = new ReminderBuilder().build();
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder(changedReminder).build();
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER, descriptor);

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReminder(model.getFilteredReminderList().get(0), changedReminder);

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastReminder = Index.fromOneBased(model.getFilteredReminderList().size());
        ReadOnlyReminder lastReminder = model.getFilteredReminderList().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder changedReminder = reminderInList.withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();


        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();
        ChangeReminderCommand changeCommand = prepareCommand(indexLastReminder, descriptor);

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReminder(lastReminder, changedReminder);

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER, new ChangeReminderDescriptor());
        ReadOnlyReminder changedReminder = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstReminderOnly(model);

        ReadOnlyReminder reminderInFilteredList = model.getFilteredReminderList()
                .get(INDEX_FIRST_REMINDER.getZeroBased());

        Reminder changedReminder = new ReminderBuilder(reminderInFilteredList)
                .withDetails(VALID_DETAILS_ASSIGNMENT).build();
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER,
                new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_ASSIGNMENT).build());

        String expectedMessage = String.format(ChangeReminderCommand.MESSAGE_CHANGE_REMINDER_SUCCESS, changedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateReminder(model.getFilteredReminderList().get(0), changedReminder);

        assertCommandSuccess(changeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminderUnfilteredList_failure() {
        Reminder firstReminder = new Reminder(model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased()));
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder(firstReminder).build();
        ChangeReminderCommand changeCommand = prepareCommand(INDEX_SECOND_REMINDER, descriptor);

        assertCommandFailure(changeCommand, model, ChangeReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_duplicateReminderFilteredList_failure() {
        showFirstReminderOnly(model);

        // edit reminder in filtered list into a duplicate in address book
        ReadOnlyReminder reminderInList = model.getAddressBook().getReminderList()
                .get(INDEX_SECOND_REMINDER.getZeroBased());

        ChangeReminderCommand changeCommand = prepareCommand(INDEX_FIRST_REMINDER,
                new ChangeReminderDescriptorBuilder(reminderInList).build());

        assertCommandFailure(changeCommand, model, ChangeReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_invalidReminderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_MEETING)
                .build();
        ChangeReminderCommand changeCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(changeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    /**
     * Change filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidReminderIndexFilteredList_failure() {
        showFirstReminderOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ChangeReminderCommand changeCommand = prepareCommand(outOfBoundIndex,
                new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_MEETING).build());

        assertCommandFailure(changeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ChangeReminderCommand standardCommand = new ChangeReminderCommand(INDEX_FIRST_REMINDER, DESC_ASSIGNMENT);

        // same values -> returns true
        ChangeReminderDescriptor copyDescriptor = new ChangeReminderDescriptor(DESC_ASSIGNMENT);
        ChangeReminderCommand commandWithSameValues = new ChangeReminderCommand(INDEX_FIRST_REMINDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ChangeReminderCommand(INDEX_SECOND_REMINDER, DESC_ASSIGNMENT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ChangeReminderCommand(INDEX_FIRST_REMINDER, DESC_MEETING)));
    }

    /**
     * Returns an {@code ChangeCommand} with parameters {@code index} and {@code descriptor}
     */
    private ChangeReminderCommand prepareCommand(Index index, ChangeReminderDescriptor descriptor) {
        ChangeReminderCommand changeCommand = new ChangeReminderCommand(index, descriptor);
        changeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ChangeReminderDescriptorTest.java
``` java
public class ChangeReminderDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ChangeReminderDescriptor descriptorWithSameValues = new ChangeReminderDescriptor(DESC_ASSIGNMENT);
        assertTrue(DESC_ASSIGNMENT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ASSIGNMENT.equals(DESC_ASSIGNMENT));

        // null -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(null));

        // different types -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(5));

        // different values -> returns false
        assertFalse(DESC_ASSIGNMENT.equals(DESC_MEETING));

        // different details -> returns false
        ChangeReminderDescriptor editedAssignment = new ChangeReminderDescriptorBuilder(DESC_ASSIGNMENT)
                .withDetails(VALID_DETAILS_MEETING).build();
        assertFalse(DESC_ASSIGNMENT.equals(editedAssignment));



    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered list to show only the first reminder in the {@code model}'s address book.
     */
    public static void showFirstReminderOnly(Model model) {
        ReadOnlyReminder reminder = model.getAddressBook().getReminderList().get(0);
        final String[] splitDetails = reminder.getDetails().details.split("\\s+");
        model.updateFilteredReminderList(new DetailsContainsKeywordsPredicate(Arrays.asList(splitDetails[0])));

        assert model.getFilteredReminderList().size() == 1;
    }
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */

public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {


        EmailCommand emailCommandFirst = new EmailCommand("friends", "party");
        EmailCommand emailCommandCommandSecond = new EmailCommand("owesMoney", "debt");

        // same object -> returns true
        assertTrue(emailCommandFirst.equals(emailCommandFirst));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand("friends", "meeting");
        assertTrue(emailFirstCommandCopy.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailCommandFirst.equals(1));

        // null -> returns false
        assertFalse(emailCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(emailCommandFirst.equals(emailCommandCommandSecond));
    }
    @Test
    public void valid_subjectForBrowser() {

        String tag = "friends";
        String subject = "party with friends";
        String modifiedSubject = "party+with+friends";
        EmailCommand emailCommand = new EmailCommand(tag, subject);
        String check = emailCommand.getSubjectForBrowser(subject);
        assertTrue(modifiedSubject.equals(check));


    }
    @Test
    public void invalid_tagForEmail() {

        String tagSecond = "fr+";
        EmailCommand emailCommand = prepareCommand(tagSecond, "party");
        assertCommandFailure(emailCommand, model, EmailCommand.MESSAGE_NOT_EXISTING_TAGS);

    }
    @Test
    public void validArgsSuccess() {

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String tag = "friends";
        String friends = "party";
        EmailCommand emailCommand = prepareCommand(tag, friends);
        assertCommandSuccessEmail(emailCommand, model, expectedMessage, expectedModel);
    }
    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(String tag, String subject) {

        EmailCommand emailCommand = new EmailCommand(tag, subject);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccessEmail(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {

        assertEquals(expectedMessage, EmailCommand.MESSAGE_EMAIL_SUCCESS);;
        assertEquals(expectedModel, actualModel);

    }
}
```
###### \java\seedu\address\logic\commands\FaceBookCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code MapCommand}.
 */

public class FaceBookCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {

        ReadOnlyPerson personToShow = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FaceBookCommand.MESSAGE_FACEBOOK_SHOWN_SUCCESS, personToShow);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.faceBook(personToShow);

        assertCommandSuccess(faceBookCommand, model, expectedMessage, expectedModel);

        FaceBookEvent lastFacebookEvent = (FaceBookEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()), lastFacebookEvent.getPerson());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {


        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FaceBookCommand faceBookCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(faceBookCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FaceBookCommand faceBookCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(faceBookCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }
    @Test
    public void execute_no_usernameFacebookCommmand() throws IllegalValueException {

        ReadOnlyPerson personToSearch = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToSearch.usernameProperty().setValue(new FacebookUsername(""));
        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(faceBookCommand, model, Messages.MESSAGE_NO_USERNAME);
    }

    @Test
    public void equals() {


        FaceBookCommand faceBookCommandFirst = new FaceBookCommand(INDEX_FIRST_PERSON);
        FaceBookCommand faceBookCommandSecond = new FaceBookCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(faceBookCommandFirst.equals(faceBookCommandFirst));

        // same values -> returns true
        FaceBookCommand facebookFirstCommandCopy = new FaceBookCommand(INDEX_FIRST_PERSON);
        assertTrue(facebookFirstCommandCopy.equals(facebookFirstCommandCopy));

        // different types -> returns false
        assertFalse(faceBookCommandFirst.equals(1));

        // null -> returns false
        assertFalse(faceBookCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(faceBookCommandFirst.equals(faceBookCommandSecond));

    }

    @Test
    public void check_facebookEventCollected() throws CommandException {
        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);
        faceBookCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof FaceBookEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Returns a {@code FacebookCommand} with the parameter {@code index}.
     */
    private FaceBookCommand prepareCommand(Index index) {
        FaceBookCommand faceBookCommand = new FaceBookCommand(index);
        faceBookCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return faceBookCommand;
    }
}
```
###### \java\seedu\address\logic\commands\PhotoCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PhotoCommand}.
 */
public class PhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Out Of Bounds Exception Testing
     */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String FilePath = "/Users/ronaklakhotia/Desktop/Ronak.jpeg";
        PhotoCommand photoCommand = prepareCommand(outOfBoundIndex, FilePath);

        assertCommandFailure(photoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * No image to delete Exception Testing
     */
    @Test
    public void deleteFaliure() {
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());
        String filePath = "delete";
        PhotoCommand photoCommand = prepareCommand(index, filePath);
        assertCommandFailure(photoCommand, model, Messages.MESSAGE_NO_IMAGE_TO_DELETE);
    }

    /**
     * Returns a {@code PhotoCommand} with the parameter {@code index and Filepath}.
     */
    private PhotoCommand prepareCommand(Index index, String FilePath) {

        PhotoCommand photoCommand = new PhotoCommand(index, FilePath);
        photoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return photoCommand;
    }
    @Test
    public void equals() {
        PhotoCommand photoCommand = new PhotoCommand(INDEX_FIRST_PERSON,
                "/Users/ronaklakhotia/Desktop/Ronak.jpeg");

        PhotoCommand photoSecondCommand = new PhotoCommand(INDEX_SECOND_PERSON,
                "/Users/ronaklakhotia/Desktop/Ronak.jpeg");

        // same object -> returns true
        assertTrue(photoCommand.equals(photoCommand));

        // same values -> returns true
        PhotoCommand photoFirstCommandCopy = new PhotoCommand(INDEX_FIRST_PERSON,
                "/Users/ronaklakhotia/Desktop/Ronak.jpeg");

        assertTrue(photoCommand.equals(photoFirstCommandCopy));

        // different types -> returns false
        assertFalse(photoCommand.equals(1));

        // null -> returns false
        assertFalse(photoCommand.equals(null));

        // different person -> returns false
        assertFalse(photoCommand.equals(photoSecondCommand));
    }

}
```
###### \java\seedu\address\logic\commands\RemoveReminderCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveReminderCommand}.
 */
public class RemoveReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyReminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        RemoveReminderCommand removeCommand = prepareCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(RemoveReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        RemoveReminderCommand removeCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstReminderOnly(model);

        ReadOnlyReminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        RemoveReminderCommand removeCommand = prepareCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(RemoveReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);
        showNoReminder(expectedModel);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstReminderOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_REMINDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getReminderList().size());

        RemoveReminderCommand removeCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        RemoveReminderCommand removeFirstCommand = new RemoveReminderCommand(INDEX_FIRST_REMINDER);
        RemoveReminderCommand removeSecondCommand = new RemoveReminderCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveReminderCommand removeFirstCommandCopy = new RemoveReminderCommand(INDEX_FIRST_REMINDER);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    /**
     * Returns a {@code ReomveCommand} with the parameter {@code index}.
     */
    private RemoveReminderCommand prepareCommand(Index index) {
        RemoveReminderCommand removeCommand = new RemoveReminderCommand(index);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReminder(Model model) {
        model.updateFilteredReminderList(p -> false);

        assert model.getFilteredReminderList().isEmpty();
    }

}
```
###### \java\seedu\address\logic\commands\SearchCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */

public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SearchContainsKeywordsPredicate firstPredicate =
                new SearchContainsKeywordsPredicate(Collections.singletonList("first"));

        SearchContainsKeywordsPredicate secondPredicate =
                new SearchContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // different types -> return false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));

    }
    @Test
    public void execute_zeroKeywords_noPersonFound() {

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SearchCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }



    /**
     * Parses {@code userInput} into a {@code SearchCommand}.
     */
    private SearchCommand prepareCommand(String userInput) {
        SearchCommand command =
                new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple dates - last date accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DOB_DESC_AMY + DOB_DESC_BOB + REMARK_DESC_BOB + IMAGE_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));


        // multiple filepaths - last filepath accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DOB_DESC_AMY +  REMARK_DESC_BOB + IMAGE_BOB + IMAGE_AMY + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple username - last username accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + DOB_DESC_AMY +  REMARK_DESC_BOB + IMAGE_BOB + USERNAME_AMY
                        + USERNAME_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid Date
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_DATE_OF_BIRTH_DESC + REMARK_DESC_BOB + IMAGE_BOB + TAG_DESC_HUSBAND
                + VALID_TAG_FRIEND, DateOfBirth.MESSAGE_BIRTHDAY_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid Image path
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + DOB_DESC_BOB + REMARK_DESC_BOB + INVALID_IMAGE_PATH_DESC + TAG_DESC_HUSBAND
                + VALID_TAG_FRIEND, FileImage.MESSAGE_IMAGE_CONSTRAINTS);

```
###### \java\seedu\address\logic\parser\AddReminderParserTest.java
``` java
public class AddReminderParserTest {
    private AddReminderParser parser = new AddReminderParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Reminder expectedReminder = new ReminderBuilder().withDetails(VALID_DETAILS_MEETING)
                .withPriority("Priority Level: " + VALID_PRIORITY_MEETING)
                .withDueDate(VALID_DUE_DATE_MEETING).build();

        // multiple details - last detail accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + DETAILS_DESC_MEETING
                        + PRIORITY_DESC_MEETING + DUE_DATE_DESC_MEETING,
                new AddReminder(expectedReminder));

        // multiple priorities - last priority accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING
                        + PRIORITY_DESC_ASSIGNMENT + PRIORITY_DESC_MEETING + DUE_DATE_DESC_MEETING,
                new AddReminder(expectedReminder));

        // multiple due dates - last due date accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING
                        + PRIORITY_DESC_MEETING + DUE_DATE_DESC_ASSIGNMENT + DETAILS_DESC_MEETING,
                new AddReminder(expectedReminder));

    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE);

        // missing detail prefix
        assertParseFailure(parser, AddReminder.COMMAND_WORD + VALID_DETAILS_ASSIGNMENT
                + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT , expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, AddReminder.COMMAND_WORD
                + DETAILS_DESC_ASSIGNMENT + VALID_PRIORITY_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT, expectedMessage);

        // missing duedate prefix
        assertParseFailure(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_MEETING
                + VALID_DUE_DATE_ASSIGNMENT, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid reminder
        assertParseFailure(parser, AddReminder.COMMAND_WORD + INVALID_DETAILS_DESC + PRIORITY_DESC_MEETING
                + DUE_DATE_DESC_MEETING, ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

        // invalid priority
        assertParseFailure(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING + INVALID_PRIORITY_DESC
                + DUE_DATE_DESC_MEETING, Priority.PRIORITY_CONSTRAINTS);

        // invalid duedate
        assertParseFailure(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING + PRIORITY_DESC_MEETING
                + INVALID_DUE_DATE_DESC, DueDate.MESSAGE_DATE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddReminder.COMMAND_WORD + INVALID_DETAILS_DESC + PRIORITY_DESC_MEETING
                + INVALID_DUE_DATE_DESC , ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_search() throws Exception {
        SearchCommand command = new SearchCommand(new
                SearchContainsKeywordsPredicate(Arrays.asList("Alice", "13.10.1997")));

        SearchCommand commandCheck = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_WORD + " "
                + PREFIX_NAME + "Alice" + " " + PREFIX_DOB + "13.10.1997");
        assertEquals(commandCheck, command);
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        assertTrue(parser.parseCommand(DeleteTagCommand.COMMAND_WORD
                + " " + "1" + " " + "t/friends"
        ) instanceof DeleteTagCommand);
    }

    @Test
    public void parseCommand_clearPopup() throws Exception {
        assertTrue(parser.parseCommand(ClearPopupCommand.COMMAND_WORD) instanceof ClearPopupCommand);
        assertTrue(parser.parseCommand(ClearPopupCommand.COMMAND_WORD
                + " " + "1") instanceof ClearPopupCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addReminder() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        AddReminder command = (AddReminder) parser.parseCommand(AddReminder.COMMAND_WORD + " "
                + "g/CS2103T Assignment " + "p/high" + " d/12.11.2017");
        assertEquals(new AddReminder(reminder), command);
    }
    @Test
    public void parseCommand_change() throws Exception {
        Reminder reminder = new ReminderBuilder().build();
        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder(reminder).build();
        ChangeReminderCommand command = (ChangeReminderCommand) parser
                .parseCommand(ChangeReminderCommand.COMMAND_WORD + " "
                + "1 " + "g/CS2103T Assignment" + " p/high" + " d/12.11.2017");
        assertEquals(new ChangeReminderCommand(INDEX_FIRST_REMINDER, descriptor), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_remove() throws Exception {
        RemoveReminderCommand command = (RemoveReminderCommand) parser.parseCommand(
                RemoveReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new RemoveReminderCommand(INDEX_FIRST_REMINDER), command);
    }
```
###### \java\seedu\address\logic\parser\ChangeReminderCommandParserTest.java
``` java
public class ChangeReminderCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE);

    private ChangeReminderCommandParser parser = new ChangeReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DETAILS_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ChangeReminderCommand.MESSAGE_NOT_CHANGED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + DETAILS_DESC_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DETAILS_DESC_ASSIGNMENT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1"
                + INVALID_DETAILS_DESC, ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS); // invalid details
        assertParseFailure(parser, "1"
                + INVALID_PRIORITY_DESC, Priority.PRIORITY_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1"
                + INVALID_DUE_DATE_DESC, DueDate.MESSAGE_DATE_CONSTRAINTS); // invalid duedate

        // invalid priority followed by valid duedate
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC
                + DUE_DATE_DESC_ASSIGNMENT, Priority.PRIORITY_CONSTRAINTS);

        // valid priority followed by invalid priority
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC + VALID_PRIORITY_MEETING,
                Priority.PRIORITY_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DETAILS_DESC + INVALID_DUE_DATE_DESC
                        + VALID_PRIORITY_ASSIGNMENT ,
                ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = targetIndex.getOneBased() + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();
        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_someFieldsSpecified_success() {

        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = targetIndex.getOneBased() + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).build();

        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

    }
    @Test
    public void parse_oneFieldSpecified_success() {

        Index targetIndex = INDEX_SECOND_REMINDER;
        //details
        String userInput = targetIndex.getOneBased() + DETAILS_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withDetails(VALID_DETAILS_ASSIGNMENT).build();

        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // PRIORITY
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_ASSIGNMENT;
        descriptor = new ChangeReminderDescriptorBuilder().withPriority(VALID_PRIORITY_ASSIGNMENT)
                .build();

        expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //due date
        userInput = targetIndex.getOneBased() + DUE_DATE_DESC_ASSIGNMENT;
        descriptor = new ChangeReminderDescriptorBuilder().withDueDate(VALID_DUE_DATE_ASSIGNMENT)
                .build();

        expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


    }


    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_REMINDER;

        String userInput = targetIndex.getOneBased()  + PRIORITY_DESC_ASSIGNMENT + DETAILS_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDetails(VALID_DETAILS_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT)
                .build();
        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {

        Index targetIndex = INDEX_SECOND_REMINDER;

        String userInput = targetIndex.getOneBased() + INVALID_PRIORITY_DESC + PRIORITY_DESC_ASSIGNMENT;

        ChangeReminderDescriptor descriptor = new ChangeReminderDescriptorBuilder()
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .build();

        ChangeReminderCommand expectedCommand = new ChangeReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

}
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_allFieldsPresent_faliure() throws ParseException {

        //multiple tags -> rejected
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_TAG + " t/owesMoney"
                + EMAIL_SUBJECT, String.format(
                        EmailCommandParser.MULTIPLE_TAGS_FALIURE, EmailCommand.MESSAGE_USAGE
        ));

        //incorrect subject
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_TAG + INVALID_EMAIL_SUBJECT,
                EmailSubject.MESSAGE_NAME_CONSTRAINTS);


    }
    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE);

        //tags missing
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_SUBJECT, expectedMessage);

        //subject missing
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + EMAIL_TAG, expectedMessage);

        //both tags and subject missing
        assertParseFailure(parser, EmailCommand.COMMAND_WORD + " ", expectedMessage);
    }
}
```
###### \java\seedu\address\logic\parser\FaceBookCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FacebookCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FacebookCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class FaceBookCommandParserTest {

    private FaceBookCommandParser parser = new FaceBookCommandParser();

    @Test
    public void parse_validArgs_returnsFaceBookCommand() {

        assertParseSuccess(parser, "1", new FaceBookCommand(INDEX_FIRST_PERSON));

        /* multiple whitespaces */
        assertParseSuccess(parser, "   1   ", new FaceBookCommand(INDEX_FIRST_PERSON));


        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FaceBookCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE
        ));

    }

    @Test
    public void parse_invalidNumberOfArgs_throwsParseException() {

        assertParseFailure(parser, "1 2", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE
        ));
    }
}
```
###### \java\seedu\address\logic\parser\LogoutCommandParserTest.java
``` java
public class LogoutCommandParserTest {

    private LogoutCommandParser parser = new LogoutCommandParser();

    private LogoutCommand expectedCommand = new LogoutCommand();
    @Test
    public void parse_invalidArgs_returnsLogoutCommand() {
        assertParseFailureLogout(parser, "", expectedCommand);
    }
    /**
     * asserts not equal logout commands.
     */
    public static void assertParseFailureLogout(Parser parser, String userInput, Command expectedCommand) {
        assertNotEquals(expectedCommand, new LogoutCommand());

    }
}
```
###### \java\seedu\address\logic\parser\PhotoCommandParserTest.java
``` java
public class PhotoCommandParserTest {

    private PhotoCommandParser parser = new PhotoCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        String FilePath = "src/main/resources/images/" + "clock" + ".png";

        assertParseSuccess(parser, "1 src/main/resources/images/clock.png",
                new PhotoCommand(INDEX_FIRST_PERSON, FilePath));
    }


}
```
###### \java\seedu\address\logic\parser\RemoveReminderCommandParserTest.java
``` java
public class RemoveReminderCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveCommand() {
        assertParseSuccess(parser, "1", new RemoveReminderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveReminderCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\SearchCommandParserTest.java
``` java
public class SearchCommandParserTest {

    public static final String INVALID_DETAILS = "You might have entered invalid date or name with invalid characters!";
    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {

        /* no input */
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* only name -> rejected */
        assertParseFailure(parser, "n/Ronak",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* only date of birth -> rejected */
        assertParseFailure(parser, "b/13.10.1997",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* prefixes missing */
        assertParseFailure(parser, "ronak 13.10.1997",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* Invalid date */
        assertParseFailure(parser, " n/ronak b/31.02.1997",
                String.format(INVALID_DETAILS));

        /* Invalid name */
        assertParseFailure(parser, " n/ronak. b/31.01.1997",
                String.format(INVALID_DETAILS));
    }
    @Test
    public void parse_allFieldsPresent_success() throws ParseException {

        /* Multiple names last one taken */
        assertParseSuccess(parser, SearchCommand.COMMAND_WORD + NAME_DESC_BOB
                + NAME_DESC_AMY + DOB_DESC_AMY, new SearchCommand(
                        new SearchContainsKeywordsPredicate(Arrays.asList(VALID_NAME_AMY, VALID_DOB_AMY)))
        );

        /* Multiple dates last one taken */
        assertParseSuccess(parser, SearchCommand.COMMAND_WORD + NAME_DESC_AMY
                + DOB_DESC_BOB + DOB_DESC_AMY, new SearchCommand(
                        new SearchContainsKeywordsPredicate(Arrays.asList(VALID_NAME_AMY, VALID_DOB_AMY))
        ));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);
        // missing name
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + VALID_DOB_AMY,
                expectedMessage);

        // missing date
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + VALID_NAME_AMY,
                expectedMessage);


    }
    @Test
    public void parse_invalidValue_failure() {

        // Invalid name
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + INVALID_NAME_DESC
                + INVALID_DATE_OF_BIRTH_DESC, String.format(INVALID_DETAILS));

        // Invalid date
        assertParseFailure(parser, SearchCommand.COMMAND_WORD + NAME_DESC_AMY
                + INVALID_DATE_OF_BIRTH_DESC, String.format((INVALID_DETAILS)));

    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList("Alice", "13.10.1997")));
        assertParseSuccess(parser, " n/Alice b/13.10.1997", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t b/13.10.1997  \t", expectedSearchCommand);
    }

}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getFilteredReminderList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void facebook_eventRaised() throws IOException {
        ModelManager model = new ModelManager();
        try {
            model.faceBook(ALICE);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person not found");
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof FaceBookEvent);
    }

    @Test
    public void clearBrowserPanel_eventRaised() throws IOException {
        ModelManager model = new ModelManager();
        model.clearBrowserPanel();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClearBrowserPanelEvent);
    }
    @Test
    public void sendEmailToContacts() throws IOException, URISyntaxException, IllegalValueException {

        ModelManager model = new ModelManager();
        String expectedAppendedMail = "johnd@example.com+johnd@example.com+johnd@example.com+";
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String appendedMail = model.getAppendedEmailIdOfContacts("friends", lastShownList);
        assertFalse(appendedMail.equals(expectedAppendedMail));
    }
```
###### \java\seedu\address\model\person\DateOfBirthTest.java
``` java
public class DateOfBirthTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(DateOfBirth.isValidBirthday("29.02.2001"));
        assertFalse(DateOfBirth.isValidBirthday("31.04.2000"));


        assertTrue(DateOfBirth.isValidBirthday("1.1.1997"));
        assertTrue(DateOfBirth.isValidBirthday("1-1-1997"));

    }
}
```
###### \java\seedu\address\model\person\EmailSubjectTest.java
``` java
public class EmailSubjectTest {

    @Test
    public void isValidSubject() throws IllegalValueException {
        // invalid subject
        assertFalse(EmailSubject.isValidSubject("ss."));


        assertTrue(EmailSubject.isValidSubject("party"));
        assertTrue(EmailSubject.isValidSubject("birthday   party"));

        EmailSubject emailSubject = new EmailSubject("subject");
        assertFalse(emailSubject.equals(0));
        assertFalse(emailSubject.equals(null));
        assertTrue(emailSubject.equals(emailSubject));
    }
}
```
###### \java\seedu\address\model\person\FaceBookUserNameTest.java
``` java
public class FaceBookUserNameTest {

    @Test
    public void isValidUsername() throws IllegalValueException {
        // invalid addresses
        FacebookUsername username = new FacebookUsername("ronak.lakhotia");
        FacebookUsername usernameCopy = new FacebookUsername("ronak.lakhotia");
        assertFalse(username.equals(usernameCopy));
        assertTrue(username.equals(username));
        assertFalse(username.equals(null));
        assertFalse(username.equals(5));
    }
}

```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {
    @Test
    public void isValidRemark() {
        // invalid remark
        assertFalse(Remark.isValidRemark(" ")); // spaces only
        assertFalse(Remark.isValidRemark("/LEC/1")); // missing module title
        assertFalse(Remark.isValidRemark("CS2102//1")); // missing module type
        assertFalse(Remark.isValidRemark("CS2102/LEC/")); // missing module number

        // valid remark
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark("CS2102/LEC/1"));
    }
}
```
###### \java\seedu\address\model\reminder\DueDateTest.java
``` java
public class DueDateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(DueDate.isValidDate("29.02.2001"));
        assertFalse(DueDate.isValidDate("31.04.2000"));
        assertFalse(DueDate.isValidDate("1.13.2000"));
        assertFalse(DueDate.isValidDate("1*1*2000"));

        //valid date
        assertTrue(DueDate.isValidDate("1.1.1997"));
        assertTrue(DueDate.isValidDate("1-1-1997"));

    }
}
```
###### \java\seedu\address\model\UniqueReminderListTest.java
``` java
public class UniqueReminderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueReminderList uniqueReminderList = new UniqueReminderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueReminderList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void handleAddressBookBackUpEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        storage.handleBackUpEvent(new BackUpEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java

    /**
     * Backups {@code addressBook} at the specified {@code filePath}.
     */
    private void backUpAddressBook(ReadOnlyAddressBook addressBook, String filepath) {
        try {
            new XmlAddressBookStorage(filepath).backupAddressBook(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void backUpAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        backUpAddressBook(new AddressBook(), null);
    }

    @Test
    public void backUpAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        backUpAddressBook(null, "SomeFile.xml");
    }

    @Test
    public void readAndbackUpAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.backupAddressBook(original);
        String trimmedFilePath = filePath.substring(0, filePath.length() - 4) + "-backup.xml";
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(trimmedFilePath).get();
        assertEquals(original, new AddressBook(readBack));


    }
}

```
###### \java\seedu\address\testutil\ChangeReminderDescriptorBuilder.java
``` java
/**
 * A utility class to help with building ChangeReminderDescriptor objects.
 */
public class ChangeReminderDescriptorBuilder {

    private ChangeReminderDescriptor descriptor;

    public ChangeReminderDescriptorBuilder() {
        descriptor = new ChangeReminderDescriptor();
    }

    public ChangeReminderDescriptorBuilder(ChangeReminderDescriptor descriptor) {
        this.descriptor = new ChangeReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code ChangeReminderDescriptor} with fields containing {@code person}'s details
     */
    public ChangeReminderDescriptorBuilder(ReadOnlyReminder reminder) {
        descriptor = new ChangeReminderDescriptor();
        descriptor.setDetails(reminder.getDetails());
        descriptor.setPriority(reminder.getPriority());
        descriptor.setDueDate(reminder.getDueDate());
    }

    /**
     * Sets the {@code details} of the {@code ChangeReminderDescriptor} that we are building.
     */
    public ChangeReminderDescriptorBuilder withDetails(String details) {
        try {
            ParserUtil.parseDetails(Optional.of(details)).ifPresent(descriptor::setDetails);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("details are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code ChangeReminderDescriptor} that we are building.
     */
    public ChangeReminderDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is not expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code ChangeReminderDescriptor} that we are building.
     */
    public ChangeReminderDescriptorBuilder withDueDate(String date) {
        try {
            ParserUtil.parseDueDate(Optional.of(date)).ifPresent(descriptor::setDueDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("due date is not expected to be unique.");
        }
        return this;
    }



    public ChangeReminderDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\ReminderBuilder.java
``` java
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_DETAILS = "CS2103T Assignment";
    public static final String TEST_PRIORITY = "high";
    public static final String DEFAULT_DUE_DATE = "12.11.2017";


    private Reminder reminder;

    public ReminderBuilder() {
        try {
            ReminderDetails defaultDetails = new ReminderDetails(DEFAULT_DETAILS);
            Priority defaultPriority = new Priority("Priority Level: " + TEST_PRIORITY);
            DueDate defaultDueDate = new DueDate(DEFAULT_DUE_DATE);
            this.reminder = new Reminder(defaultDetails, defaultPriority, defaultDueDate);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default reminder's values are invalid.");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(ReadOnlyReminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }

    /**
     * Sets the {@code details} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDetails(String details) {
        try {
            this.reminder.setDetails(new ReminderDetails(details));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("details are expected to be unique.");
        }
        return this;
    }
    /**
     * Sets the {@code Priority} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withPriority(String priority) {
        try {
            this.reminder.setPriority(new Priority(priority));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDueDate(String date) {
        try {
            this.reminder.setDueDate(new DueDate(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is not expected to be unique.");
        }
        return this;
    }


    public Reminder build() {
        return this.reminder;
    }

}
```
###### \java\seedu\address\testutil\ReminderUtil.java
``` java
/**
 * A utility class for Reminder
 */
public class ReminderUtil {

    /**
     * Returns an add command string for adding the {@code reminder}.
     */
    public static String getAddCommand(ReadOnlyReminder reminder) {
        return AddReminder.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(ReadOnlyReminder reminder) {

        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMINDER_DETAILS + reminder.getDetails().details + " ");
        sb.append(PREFIX_REMINDER_PRIORITY + reminder.getPriority().priority + " ");
        sb.append(PREFIX_REMINDER_DUE_DATE + reminder.getDueDate().date + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\ui\ReminderCardTest.java
``` java
public class ReminderCardTest extends GuiUnitTest {

    @Test
    public void equals() {
        Reminder reminder = new ReminderBuilder().build();
        ReminderCard reminderCard = new ReminderCard(reminder, 0);

        // same reminder, same index -> returns true
        ReminderCard copy = new ReminderCard(reminder, 0);
        assertTrue(reminderCard.equals(copy));

        // same object -> returns true
        assertTrue(reminderCard.equals(reminderCard));

        // null -> returns false
        assertFalse(reminderCard.equals(null));

        // different types -> returns false
        assertFalse(reminderCard.equals(0));

        // different reminder, same index -> returns false
        Reminder differentReminder = new ReminderBuilder().withDetails("different").build();
        assertFalse(reminderCard.equals(new ReminderCard(differentReminder, 0)));

        // same reminder, different index -> returns false
        assertFalse(reminderCard.equals(new ReminderCard(reminder, 1)));
    }

    /**
     * Asserts that {@code reminderCard} displays the details of {@code expectedReminder} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ReminderCard reminderCard, ReadOnlyReminder expectedReminder, int expectedId) {
        guiRobot.pauseForHuman();

        ReminderCardHandle reminderCardHandle = new ReminderCardHandle(reminderCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", reminderCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysReminder(expectedReminder, reminderCardHandle);
    }
}
```
###### \java\seedu\address\ui\ReminderListPanelTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalPersons.getTypicalReminders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReminder;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEqualsReminders;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ReminderCardHandle;
import guitests.guihandles.ReminderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.reminder.ReadOnlyReminder;

public class ReminderListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyReminder> TYPICAL_REMINDERS =
            FXCollections.observableList(getTypicalReminders().subList(1, 2));

    private static final JumpToListRequestEvent JUMP_TO_FIRST_EVENT = new
            JumpToListRequestEvent(INDEX_FIRST_REMINDER);

    private ReminderListPanelHandle reminderListPanelHandle;

    @Before
    public void setUp() {
        ReminderListPanel reminderListPanel = new ReminderListPanel(TYPICAL_REMINDERS);
        uiPartRule.setUiPart(reminderListPanel);

        reminderListPanelHandle = new ReminderListPanelHandle(getChildNode(reminderListPanel.getRoot(),
                ReminderListPanelHandle.REMINDER_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_REMINDERS.size(); i++) {
            reminderListPanelHandle.navigateToCard(TYPICAL_REMINDERS.get(i));
            ReadOnlyReminder expectedReminder = TYPICAL_REMINDERS.get(i);
            ReminderCardHandle actualCard = reminderListPanelHandle.getReminderCardHandle(i);

            assertCardDisplaysReminder(expectedReminder, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_FIRST_EVENT);
        guiRobot.pauseForHuman();

        ReminderCardHandle expectedCard = reminderListPanelHandle.getReminderCardHandle(INDEX_FIRST_REMINDER
                .getZeroBased());
        ReminderCardHandle selectedCard = reminderListPanelHandle.getHandleToSelectedCard();
        assertCardEqualsReminders(expectedCard, selectedCard);
    }
}
```
###### \java\systemtests\AddReminderCommandSystemTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DETAILS_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DUE_DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAILS_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUE_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TUTORIAL;
import static seedu.address.testutil.TypicalPersons.TUTORIAL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddReminder;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.ReminderUtil;

public class AddReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a reminder command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyReminder toAdd = TUTORIAL;
        String command = "   " + AddReminder.COMMAND_WORD + "  " + DETAILS_DESC_TUTORIAL + " "
                + PRIORITY_DESC_TUTORIAL + " " + DUE_DATE_DESC_TUTORIAL;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding TUTORIAL to the list -> SUCCESS*/
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding to the list -> SUCCESS */
        command = RedoCommand.COMMAND_WORD;
        model.addReminder(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: add a duplicate reminder -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_TUTORIAL + PRIORITY_DESC_TUTORIAL
                + DUE_DATE_DESC_TUTORIAL;
        assertCommandFailure(command, AddReminder.MESSAGE_DUPLICATE_REMINDER);

        /* Case: add a reminder with all fields same as another reminder in the address book except priority -> added */

        toAdd = new ReminderBuilder().withDetails(VALID_DETAILS_TUTORIAL)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_TUTORIAL).build();

        command = AddReminder.COMMAND_WORD + DETAILS_DESC_TUTORIAL + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_TUTORIAL;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the address book except details -> added */
        toAdd = new ReminderBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_TUTORIAL)
                .withDueDate(VALID_DUE_DATE_TUTORIAL).build();

        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_TUTORIAL
                + DUE_DATE_DESC_TUTORIAL;
        assertCommandSuccess(command, toAdd);


        /* Case: missing duedate -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));

        /* Case: missing priority -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + DUE_DATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));

        /* Case: missing details -> rejected */
        command = AddReminder.COMMAND_WORD  + PRIORITY_DESC_ASSIGNMENT + DUE_DATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminder.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "reminders " + ReminderUtil.getReminderDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid details -> rejected */
        command = AddReminder.COMMAND_WORD + INVALID_DETAILS_DESC + PRIORITY_DESC_ASSIGNMENT
                + DUE_DATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + INVALID_PRIORITY_DESC
                + DUE_DATE_DESC_ASSIGNMENT;

        assertCommandFailure(command, Priority.PRIORITY_CONSTRAINTS);

        /* Case: invalid duedate -> rejected */
        command = AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + INVALID_DUE_DATE_DESC;
        assertCommandFailure(command, DueDate.MESSAGE_DATE_CONSTRAINTS);


    }
```
###### \java\systemtests\ChangeReminderCommandSystemTest.java
``` java
public class ChangeReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown
 ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_REMINDER;
        String command = " " + ChangeReminderCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + DETAILS_DESC_ASSIGNMENT + "  " + PRIORITY_DESC_ASSIGNMENT + " " + DUE_DATE_DESC_ASSIGNMENT;

        Reminder editedReminder = new ReminderBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();
        assertCommandSuccess(command, index, editedReminder);

        /* Case: undo editing the last reminder in the list -> last reminder restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last reminder in the list -> last reminder edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateReminder(
                getModel().getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased()), editedReminder);
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_REMINDER;
        command = ChangeReminderCommand.COMMAND_WORD + " " + index.getOneBased() + DETAILS_DESC_ASSIGNMENT;
        ReadOnlyReminder reminderToEdit = getModel().getFilteredReminderList().get(index.getZeroBased());
        editedReminder = new ReminderBuilder(reminderToEdit).withDetails(VALID_DETAILS_ASSIGNMENT).build();
        assertCommandSuccess(command, index, editedReminder);


        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " 0" + DETAILS_DESC_ASSIGNMENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " -1" + DETAILS_DESC_ASSIGNMENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */

        int invalidIndex = getModel().getFilteredReminderList().size() + 1;
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + invalidIndex
                        + DETAILS_DESC_ASSIGNMENT,
                Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ChangeReminderCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased(),
                ChangeReminderCommand.MESSAGE_NOT_CHANGED);

        /* Case: invalid details -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased()
                        + INVALID_DETAILS_DESC,
                ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);

        /* Case: invalid priority -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased()
                        + INVALID_PRIORITY_DESC,
                Priority.PRIORITY_CONSTRAINTS);

        /* Case: invalid duedate -> rejected */
        assertCommandFailure(ChangeReminderCommand.COMMAND_WORD + " " + INDEX_FIRST_REMINDER.getOneBased()
                        + INVALID_DUE_DATE_DESC,
                DueDate.MESSAGE_DATE_CONSTRAINTS);


    }
```
###### \java\systemtests\PhotoCommandSystemTest.java
``` java
public class PhotoCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_PHOTO_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE);

    @Test
    public void addPhotoTests() throws IOException {
        /* ----------------- Performing photo operation while an unfiltered list is being shown -------------------- */

        /* Case: Add photo to the first person in the list, command with leading spaces and
          trailing spaces -> deleted */

        Model expectedModel = getModel();
        String command = "     " + PhotoCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased()
                + "       " + "src/main/resources/images/" + "clock" + ".png" + "   ";

        String FilePath = "src/main/resources/images/" + "clock" + ".png";

        ReadOnlyPerson photoPerson = getTargetPerson(expectedModel, INDEX_FIRST_PERSON, FilePath);
        String expectedResultMessage = String.format(MESSAGE_PHOTO_PERSON_SUCCESS, photoPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);


        /* --------------------------------- Performing invalid photo operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = PhotoCommand.COMMAND_WORD + " 0 " + FilePath;
        assertCommandFailure(command, MESSAGE_INVALID_PHOTO_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = PhotoCommand.COMMAND_WORD + " -1 " + FilePath;
        assertCommandFailure(command, MESSAGE_INVALID_PHOTO_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = PhotoCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased() + " " + FilePath;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(PhotoCommand.COMMAND_WORD
                + " abc " + FilePath, MESSAGE_INVALID_PHOTO_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(PhotoCommand.COMMAND_WORD + " 1 abc "
                + FilePath, MESSAGE_INVALID_IMAGE);

        /* Case: No index entered -> rejected 8 */
        assertCommandFailure(PhotoCommand.COMMAND_WORD + " "
                + FilePath, MESSAGE_INVALID_PHOTO_COMMAND_FORMAT);

    }
    @Test
    public void check_invalidPath() {

        /* Case When incorrect path is entered */
        String command =  PhotoCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " " + "src/main/resources/images/" + "random" + ".jpg";

        assertCommandFailure(command, MESSAGE_INVALID_IMAGE);

    }

    /**
     * Adds photo to the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the person with the photo added
     */
    private ReadOnlyPerson getTargetPerson(Model model, Index index, String FilePath) {
        ReadOnlyPerson targetPerson = getPerson(model, index);
        try {
            model.addPhotoPerson(targetPerson, FilePath, index);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (IOException ioe) {
            throw new AssertionError("Illegal values entered");
        } catch (IllegalValueException ive) {
            throw new AssertionError("Illegal value");
        }
        return targetPerson;
    }
```
###### \java\systemtests\RemoveReminderCommandSystemTest.java
``` java

public class RemoveReminderCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_REMINDER_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveReminderCommand.MESSAGE_USAGE);

    @Test
    public void remove() {
        /* ----------------- Performing remove operation while an unfiltered list is being shown
 -------------------- */

        /* Case: delete the first reminder in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + RemoveReminderCommand.COMMAND_WORD + "      "
                + INDEX_SECOND_REMINDER.getOneBased() + "       ";
        ReadOnlyReminder removedReminder = removeReminder(expectedModel, INDEX_SECOND_REMINDER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_REMINDER_SUCCESS, removedReminder);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last reminder in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastReminderIndex = getLastIndexReminder(modelBeforeDeletingLast);
        assertCommandSuccess(lastReminderIndex);

        /* Case: undo deleting the last reminder in the list -> last reminder restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last reminder in the list -> last reminder deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeReminder(modelBeforeDeletingLast, lastReminderIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* --------------------------------- Performing invalid remove operation
 ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = RemoveReminderCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RemoveReminderCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getReminderList().size() + 1);
        command = RemoveReminderCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RemoveReminderCommand.COMMAND_WORD
                + " abc", MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RemoveReminderCommand.COMMAND_WORD
                + " 1 abc", MESSAGE_INVALID_REMINDER_COMMAND_FORMAT);

    }

    /**
     * Removes the {@code ReadOnlyReminder} at the specified {@code index} in {@code model}'s weaver.
     * @return the removed reminder
     */
    private ReadOnlyReminder removeReminder(Model model, Index index) {
        ReadOnlyReminder targetReminder = getReminder(model, index);
        try {
            model.deleteReminder(targetReminder);
        } catch (ReminderNotFoundException pnfe) {
            throw new AssertionError("targetReminder is retrieved from model.");
        }
        return targetReminder;
    }
```
###### \java\systemtests\SearchCommandSystemTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_OF_BIRTH_DESC_BOUNDS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_RONAK;
import static seedu.address.testutil.TypicalPersons.LAKHOTIA;
import static seedu.address.testutil.TypicalPersons.RANDOM;
import static seedu.address.testutil.TypicalPersons.RONAK;
import static seedu.address.testutil.TypicalPersons.SHARMA;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;


public class SearchCommandSystemTest extends AddressBookSystemTest {

    public static final String INVALID_DETAILS = "You might have entered invalid date or name with invalid characters!";
    @Test

    public void search() {
        /* Case: search multiple persons in address book, command with leading spaces and trailing spaces
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " "
                + "b/13.10.1997" + "   ";

        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel,
                LAKHOTIA, RANDOM, RONAK, SHARMA); // first names of Lakhotia and Random are "Ronak"
        assertCommandSuccess(command, expectedModel);

        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where person list is displaying the persons we are Searching
         * -> 2 persons found
         */
        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " " + "b/13.10.1997";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous search command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous search command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);


        /* Case: search person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;


        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " " +  "b/13.10.1997";

        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* missing name */
        command = SearchCommand.COMMAND_WORD + " " +  "b/13.10.1997";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* missing date */
        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

        /* invalid name */
        command = SearchCommand.COMMAND_WORD + " " + INVALID_NAME_DESC + " " + "b/13.10.1997";
        assertCommandFailure(command, String.format(INVALID_DETAILS));

        /* Invalid date */
        command = SearchCommand.COMMAND_WORD + " " + "n/" + KEYWORD_MATCHING_RONAK + " "
                + INVALID_DATE_OF_BIRTH_DESC_BOUNDS;
        assertCommandFailure(command, String.format(INVALID_DETAILS));


    }
```
