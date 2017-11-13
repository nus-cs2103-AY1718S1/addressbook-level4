# alexanderleegs
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public ObservableList<Meeting> getFilteredMeetingList() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteMeeting(Meeting meeting) {
            fail("This method should not be called.");
        }

        @Override
        public void sort(String field) {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddTagCommand}.
 */
public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        tags.add(newTag);
        Person editedPerson = new Person(readOnlyPerson);
        editedPerson.setTags(tags);
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        Tag existingTag = tags.iterator().next();
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, existingTag);
        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_invalidTagName_throwsCommandException() throws Exception {
        String tagName = "buddy!";
        try {
            Tag newTag = new Tag(tagName);
            AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);
        } catch (IllegalValueException ive) {
            assertEquals(null, ive.getMessage(), Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(personInFilteredList.getTags());
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        tags.add(newTag);
        Person editedPerson = new Person(personInFilteredList);
        editedPerson.setTags(tags);
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        String tagName = "buddy";
        // ensures that outOfBoundIndex is still in bounds of address book list
        Tag newTag = new Tag(tagName);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Tag tagOne = new Tag("friend");
        Tag tagTwo = new Tag("husband");
        AddTagCommand firstTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, tagOne);
        AddTagCommand secondTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, tagTwo);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_PERSON, tagOne);
        assertTrue(firstTagCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(firstTagCommand.equals(firstTagCommand));

        // null -> returns false
        assertFalse(firstTagCommand.equals(null));

        // different types -> returns false
        assertFalse(firstTagCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(firstTagCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, tagOne)));

        // different descriptor -> returns false
        assertFalse(firstTagCommand.equals(secondTagCommand));
    }


    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tagName}.
     */
    private AddTagCommand prepareCommand(Index index, Tag tagName) throws Exception {
        AddTagCommand addTagCommand = new AddTagCommand(index, tagName);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTagCommand}.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validTagOnePerson_success() throws Exception {
        String tagName = "owesMoney";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(newTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(newTag);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validTagTwoPersons_success() throws Exception {
        String tagName = "friends";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(newTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, tagName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTag(newTag);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTagDoesNotExist_throwsCommandException() throws Exception {
        String tagName = "robot";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(newTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_TAG_DISPLAYED);
    }

    @Test
    public void execute_invalidTagFormat_throwsCommandException() throws Exception {
        String tagName = "hi there";
        try {
            Tag newTag = new Tag(tagName);
            prepareCommand(newTag);
            fail("Expected IllegalValueException to be thrown");
        } catch (IllegalValueException ive) {
            assertEquals(null, ive.getMessage(), Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        Tag existingTag = tags.iterator().next();
        tags.remove(existingTag);
        Person editedPerson = new Person(readOnlyPerson);
        editedPerson.setTags(tags);
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, existingTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, existingTag.tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTag_throwsCommandException() throws Exception {
        ReadOnlyPerson readOnlyPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(readOnlyPerson.getTags());
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, newTag);
        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NO_TAG);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> tags = new HashSet<Tag>(personInFilteredList.getTags());
        Tag existingTag = tags.iterator().next();
        tags.remove(existingTag);
        Person editedPerson = new Person(personInFilteredList);
        editedPerson.setTags(tags);
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON, existingTag);

        String expectedMessage = String.format(DeleteTagCommand.MESSAGE_DELETE_TAG_SUCCESS, existingTag.tagName);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        String tagName = "buddy";
        Tag newTag = new Tag(tagName);
        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, newTag);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {
        Tag tagOne = new Tag("friend");
        Tag tagTwo = new Tag("husband");

        DeleteTagCommand deleteFirstCommand = prepareCommand(tagOne);
        DeleteTagCommand deleteSecondCommand = prepareCommand(tagTwo);
        DeleteTagCommand deleteFirstCommandIndex = prepareCommand(INDEX_FIRST_PERSON, tagOne);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTagCommand deleteFirstCommandCopy = prepareCommand(tagOne);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        DeleteTagCommand deleteFirstCommandIndexCopy = prepareCommand(INDEX_FIRST_PERSON, tagOne);
        assertTrue(deleteFirstCommandIndex.equals(deleteFirstCommandIndexCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different tag -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON, tagOne)));
    }

    /**
     * Returns a {@code DeleteTagCommand} with the parameter {@code tagName}.
     */
    private DeleteTagCommand prepareCommand(Tag tagName) throws Exception {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(tagName);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }

    private DeleteTagCommand prepareCommand(Index index, Tag tagName) throws Exception {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index, tagName);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nameSpecified_success() throws Exception {
        String field = "name";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_phoneSpecified_success() throws Exception {
        String field = "phone";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emailSpecified_success() throws Exception {
        String field = "email";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addressSpecified_success() throws Exception {
        String field = "address";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagSpecified_success() throws Exception {
        String field = "tag";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_meetingSpecified_success() throws Exception {
        String field = "meeting";
        String expectedMessage = SortCommand.MESSAGE_SORT_PERSON_SUCCESS;
        SortCommand sortCommand = prepareCommand(field);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.sort(field);

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidField_failure() throws Exception {
        String field = "birthday";
        String expectedMessage = SortCommand.MESSAGE_INVALID_FIELD;
        SortCommand sortCommand = prepareCommand(field);

        assertCommandFailure(sortCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final SortCommand standardCommand = new SortCommand("TAG");

        // same values -> returns true
        SortCommand commandWithSameValues = new SortCommand("TAG");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different field -> returns false
        assertFalse(standardCommand.equals(new SortCommand("PHONE")));
    }

    private SortCommand prepareCommand(String field) {
        SortCommand sortCommand = new SortCommand(field);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddTagCommandParserTest.java
``` java
public class AddTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE);

    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no tag specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT);

        // multiple tags
        assertParseFailure(parser, "1" + VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        AddTagCommand expectedCommand = new AddTagCommand(targetIndex, targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
```
###### \java\seedu\address\logic\parser\DeleteTagCommandParserTest.java
``` java
public class DeleteTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // no tag specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no tag specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TAG_FRIEND, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT);

        // multiple tags
        assertParseFailure(parser, "1" + VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetIndex, targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllLowerCase_success() {
        String userInput = "all " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllUpperCase_success() {
        String userInput = "ALL " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validAllMixedCase_success() {
        String userInput = "aLl " + VALID_TAG_FRIEND;
        Tag targetTag;
        try {
            targetTag = new Tag(VALID_TAG_FRIEND);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The target tag cannot be invalid");
        }

        DeleteTagCommand expectedCommand = new DeleteTagCommand(targetTag);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        SortCommand expectedSortCommand = new SortCommand("name");
        assertParseSuccess(parser, "name", expectedSortCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, " \n name \t", expectedSortCommand);
    }

}
```
###### \java\seedu\address\model\UniqueMeetingListTest.java
``` java
public class UniqueMeetingListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueMeetingList.asObservableList().remove(0);
    }
}
```
