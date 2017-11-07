# RonakLakhotia
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
###### \java\seedu\address\logic\commands\FaceBookCommandTest.java
``` java
public class FaceBookCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {

        ReadOnlyPerson personToShow = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        FaceBookCommand faceBookCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FaceBookCommand.MESSAGE_FACEBOOK_SHOWN_SUCCESS, personToShow);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.faceBook(personToShow);

        assertCommandSuccess(faceBookCommand, model, expectedMessage, expectedModel);
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

    /**
     * Returns a {@code MapCommand} with the parameter {@code index}.
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
     *
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
     * Returns a {@code PhotoCommand} with the parameter {@code index and Filepath}.
     */
    private PhotoCommand prepareCommand(Index index, String FilePath) {
        //DeleteCommand deleteCommand = new DeleteCommand(index);
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
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
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

    }
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SearchCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        SearchCommand command = prepareCommand("Kurz 13.10.1997");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL));
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
    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SearchCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
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

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_MEETING
                         + PRIORITY_DESC_MEETING + DUE_DATE_DESC_MEETING,
                new AddReminder(expectedReminder));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        Reminder expectedReminder = new ReminderBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority("Priority Level: " + VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();

        assertParseSuccess(parser, AddReminder.COMMAND_WORD + DETAILS_DESC_ASSIGNMENT
                        + PRIORITY_DESC_ASSIGNMENT + DUE_DATE_DESC_ASSIGNMENT,
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

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_DETAILS_DESC + INVALID_DUE_DATE_DESC
                        + VALID_PRIORITY_ASSIGNMENT ,
                ReminderDetails.MESSAGE_REMINDER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
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
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;

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



}
```
###### \java\seedu\address\logic\parser\FaceBookCommandParserTest.java
``` java
public class FaceBookCommandParserTest {

    private FaceBookCommandParser parser = new FaceBookCommandParser();

    @Test
    public void parse_validArgs_returnsFaceBookCommand() {

        assertParseSuccess(parser, "1", new FaceBookCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format
                (MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FaceBookCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNumberOfArgs_throwsParseException() {

        assertParseFailure(parser, "1 2", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FaceBookCommand.MESSAGE_USAGE
        ));
    }
}
```
###### \java\seedu\address\logic\parser\SearchCommandParserTest.java
``` java
public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand =
                new SearchCommand(new SearchContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedSearchCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchCommand);
    }

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

        //Find the relative path of a file.

        String path = "/Users/ronaklakhotia/Desktop/Ronak.jpeg";
        String base = "/Users/ronaklakhotia/Desktop";
        String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();

        File fileToRead = null;
        BufferedImage image = null;

        File fileToWrite = null;

        String uniquePath = null;
        //System.out.println(relative);

        try {

            String url = path + "";
            image = new BufferedImage(963, 640, BufferedImage.TYPE_INT_ARGB);
            fileToRead = new File(url);
            image = ImageIO.read(fileToRead);
            uniquePath = Integer.toString(path.hashCode());
            fileToWrite = new File("src/main/resources/images/" + uniquePath + ".jpg");
            ImageIO.write(image, "jpg", fileToWrite);


        } catch (IOException io) {
            new AssertionError("Invalid input");
        }


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
    public void checkForInvalidPath() {

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
        }
        return targetPerson;
    }
    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see PhotoCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }


        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }


}

```
###### \java\systemtests\SearchCommandSystemTest.java
``` java
public class SearchCommandSystemTest extends AddressBookSystemTest {

    @Test

    public void search() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_RONAK + " "
                + "13.10.1997" + "   ";

        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, LAKHOTIA, RANDOM); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where person list is displaying the persons we are Searching
         * -> 2 persons found
         */
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_RONAK + " " + "13.10.1997";
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

        /* Case: mixed case command word -> success */
        command = "SeaRch RonAk 13.10.1997";
        command = command.toLowerCase();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;


        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_RONAK + " " +  "13.10.1997";

        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();



    }
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }


}
```
