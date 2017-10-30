# JasmineSee
###### /java/systemtests/TagCommandSystemTest.java
``` java
public class TagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void tag() {
        /* Case: find multiple tags in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + TagCommand.COMMAND_WORD + " " + "owesMoney" + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // Benson and Daniel has tags "owesMoney"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous tag command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = TagCommand.COMMAND_WORD + " " + "owesMoney";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous tag command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous tag command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find persons with same tags in address book after deleting 1 of the person -> 1 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = TagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag where person list is not displaying the person we are finding -> 1 person found */
        command = TagCommand.COMMAND_WORD + " colleagues";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tags in address book, 2 keywords -> 2 persons found */
        command = TagCommand.COMMAND_WORD + " colleagues criminal";
        ModelHelper.setFilteredList(expectedModel, CARL, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tags in address book, 2 keywords in reversed order -> 2 persons found */
        command = TagCommand.COMMAND_WORD + " criminal colleagues";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tags in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = TagCommand.COMMAND_WORD + " criminal colleagues criminal";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tags in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = TagCommand.COMMAND_WORD + " criminal colleagues NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find tag in address book, keyword is same as name but of different case -> 1 person found */
        command = TagCommand.COMMAND_WORD + " coLlEAguES";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag in address book, keyword is substring of tag name -> 1 persons found */
        command = TagCommand.COMMAND_WORD + " leagues";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag in address book, tag name is substring of keyword -> 0 persons found */
        command = TagCommand.COMMAND_WORD + " colleaguesla";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag not in address book -> 0 persons found */
        command = TagCommand.COMMAND_WORD + " student";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 persons found */
        command = TagCommand.COMMAND_WORD + " " + DANIEL.getName();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = TagCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = TagCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = TagCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tag while a person is selected -> selected card still selected */
        showAllPersons();
        selectPerson(Index.fromOneBased(4));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = TagCommand.COMMAND_WORD + " criminal";
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find tag in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = TagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "TaG fRiEnDS";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
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
     *
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
###### /java/seedu/address/logic/parser/TagCommandParserTest.java
``` java
public class TagCommandParserTest {
    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_validArgs_returnsTagCommand() {
        TagCommand expectedTagCommand =
                new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "colleagues")));
        assertParseSuccess(parser, "friends colleagues", expectedTagCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/UploadPhotoCommandParserTest.java
``` java
public class UploadPhotoCommandParserTest {
    private UploadPhotoCommandParser parser = new UploadPhotoCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "asasd",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsUploadPhotoCommand() {
        // valid index,no file path specified
        UploadPhotoCommand expectedCommand = new UploadPhotoCommand(INDEX_FIRST_PERSON, "");
        assertParseSuccess(parser, "1", expectedCommand);

        // valid index and file path
        expectedCommand = new UploadPhotoCommand(INDEX_FIRST_PERSON,
                ".\\src\\test\\resources\\photos\\connectus_icon.png");
        assertParseSuccess(parser, "1 .\\src\\test\\resources\\photos\\connectus_icon.png", expectedCommand);
    }
}
```
###### /java/seedu/address/logic/parser/RemoveTagCommandParserTest.java
``` java
public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws Exception {
        ArrayList<Tag> tagToRemove = new ArrayList<>();
        tagToRemove.add(new Tag("friends"));
        tagToRemove.add(new Tag("colleagues"));

        RemoveTagCommand expectedRemoveTagCommand = new RemoveTagCommand(tagToRemove);
        assertParseSuccess(parser, "friends colleagues", expectedRemoveTagCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_zeroKeywords_noTagsRemoved() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_TAG_NOT_REMOVED);
        RemoveTagCommand command = prepareCommand(prepareTagList(" "));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleTagsRemoved() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS);
        RemoveTagCommand command = prepareCommand(prepareTagList("colleagues criminal"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagsNotExist_noTagsRemoved() throws Exception {
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_TAG_NOT_REMOVED);
        RemoveTagCommand command = prepareCommand(prepareTagList("nothing"));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        RemoveTagCommand firstCommand = new RemoveTagCommand(prepareTagList(VALID_TAG_FRIEND));
        RemoveTagCommand secondCommand = new RemoveTagCommand(prepareTagList(VALID_TAG_HUSBAND));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        RemoveTagCommand firstCommandCopy = new RemoveTagCommand(prepareTagList(VALID_TAG_FRIEND));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns an {@code ArrayList<Tag>}
     */
    private ArrayList<Tag> prepareTagList(String userInput) throws Exception {
        ArrayList<Tag> tagToRemove = new ArrayList<>();
        String[] tagKeywords = userInput.split("\\s+");

        for (int i = 0; i < tagKeywords.length; i++) {
            tagToRemove.add(new Tag(tagKeywords[i]));
        }

        return tagToRemove;
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code arraylist of tags}
     */
    private RemoveTagCommand prepareCommand(ArrayList<Tag> tagToRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(tagToRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

}
```
###### /java/seedu/address/logic/commands/TagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code TagCommand}.
 */
public class TagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        TagCommand tagFirstCommand = new TagCommand(firstPredicate);
        TagCommand tagSecondCommand = new TagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(tagFirstCommand.equals(tagFirstCommand));

        // same values -> returns true
        TagCommand tagFirstCommandCopy = new TagCommand(firstPredicate);
        assertTrue(tagFirstCommand.equals(tagFirstCommandCopy));

        // different types -> returns false
        assertFalse(tagFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tagFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(tagFirstCommand.equals(tagSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        TagCommand command = prepareCommand("owesMoney colleagues criminal");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, CARL, DANIEL, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code TagCommand}.
     */
    private TagCommand prepareCommand(String userInput) {

        TagCommand command =
                new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.toLowerCase().split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(TagCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/commands/UploadPhotoCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UploadPhotoCommand}.
 */
public class UploadPhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() throws Exception {
        ReadOnlyPerson personToUploadPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON,
                "./src/test/resources/photos/connectus_icon.png");

        String expectedMessage = String.format(UploadPhotoCommand.MESSAGE_UPLOAD_IMAGE_SUCCESS, personToUploadPhoto);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(uploadPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexValidFile_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(outOfBoundIndex,
                "./src/test/resources/photos/connectus_icon.png");

        assertCommandFailure(uploadPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexInvalidFile_throwsCommandException() throws Exception {
        ReadOnlyPerson personToUploadPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UploadPhotoCommand uploadPhotoCommand = prepareCommand(INDEX_FIRST_PERSON,
                "./src/test/resources/photos/default.jpeg");

        assertCommandFailure(uploadPhotoCommand, model, UploadPhotoCommand.MESSAGE_UPLOAD_IMAGE_FALURE);
    }

    @Test
    public void equals() {
        UploadPhotoCommand firstCommand = new UploadPhotoCommand(INDEX_FIRST_PERSON, "");
        UploadPhotoCommand secondCommand = new UploadPhotoCommand(INDEX_SECOND_PERSON, "");

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        UploadPhotoCommand firstCommandCopy = new UploadPhotoCommand(INDEX_FIRST_PERSON, "");
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }

    /**
     * Returns a {@code UploadPhotoCommand} with the parameter {@code index}.
     */
    private UploadPhotoCommand prepareCommand(Index index, String filePath) {
        UploadPhotoCommand uploadPhotoCommand = new UploadPhotoCommand(index, filePath);
        uploadPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return uploadPhotoCommand;
    }

}
```
