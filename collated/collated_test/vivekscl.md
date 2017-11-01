# vivekscl
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void removeTag(ArrayList<Index> targetIndexes, Tag toRemove)  {
            fail("This method should not be called.");
        }

        @Override
        public void addTag(ArrayList<Index> targetIndexes, Tag toAdd)  {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public String getClosestMatchingName(NameContainsKeywordsPredicate predicate) {
            fail("This method should not be called.");
            return null;
        }

```
###### \java\seedu\address\logic\commands\AddTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddTagCommand}.
 */
public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests success of an unfiltered persons list with valid input indexes and tag
     */
    @Test
    public void execute_validIndexAndTagUnfilteredList_success() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toAdd = new Tag("owesMoney");
        AddTagCommand addTagCommand = prepareCommand(indexes, toAdd);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, toAdd);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(indexes, toAdd);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests success of a filtered persons list with valid input indexes and tag
     */
    @Test
    public void execute_validIndexAndTagFilteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toAdd = new Tag("classmate");
        AddTagCommand addTagCommand = prepareCommand(indexes, toAdd);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, toAdd);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.addTag(indexes, toAdd);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests failure of an unfiltered persons list with invalid input indexes but a valid tag
     */
    @Test
    public void execute_invalidIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag toAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(indexes, toAdd);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    /**
     * Tests failure of a filtered persons list with invalid input indexes but a valid tag
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Tag toAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(indexes, toAdd);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests failure of an unfiltered persons list with valid input indexes but a tag that exists in every person
     */
    @Test
    public void execute_invalidTagUnfilteredList_failure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(indexes, toAdd);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    /**
     * Tests failure of a filtered persons list with valid input indexes but a tag that exists in every person
     */
    @Test
    public void execute_invalidTagFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toAdd = new Tag("friends");
        AddTagCommand addTagCommand = prepareCommand(indexes, toAdd);

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<Index> indexes1 = new ArrayList<Index>();
        ArrayList<Index> indexes2 = new ArrayList<Index>();
        indexes1.add(INDEX_FIRST_PERSON);
        indexes1.add(INDEX_SECOND_PERSON);
        indexes2.add(INDEX_SECOND_PERSON);
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("lecturer");
        final AddTagCommand standardCommand = new AddTagCommand(indexes1, firstTag);

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(indexes1, firstTag);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(indexes2, firstTag)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(indexes1, secondTag)));
    }

    /**
     * Returns an {@code AddTagCommand} with parameters {@code targetIndexes} and {@code toAdd}
     */
    private AddTagCommand prepareCommand(ArrayList<Index> targetIndexes, Tag toAdd) {
        AddTagCommand addTagCommand = new AddTagCommand(targetIndexes, toAdd);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }
}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void execute_oneKeyword_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand("car");
        ArrayList<String> keywordList = new ArrayList<String>();
        keywordList.add("car");
        NameContainsKeywordsPredicate keyword = new NameContainsKeywordsPredicate(keywordList);
        expectedMessage += String.format(MESSAGE_NO_PERSON_FOUND,
                model.getClosestMatchingName(keyword));
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        String keywordsAsString = "car ell kun";
        FindCommand command = prepareCommand(keywordsAsString);
        expectedMessage += String.format(MESSAGE_NO_PERSON_FOUND,
                model.getClosestMatchingName(new NameContainsKeywordsPredicate(
                        Arrays.asList(keywordsAsString.split("\\s+")))));
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests success of an unfiltered persons list with valid input indexes and tag
     */
    @Test
    public void execute_validIndexAndTagUnfilteredList_success() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toRemove = new Tag("owesMoney");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, toRemove);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(indexes, toRemove);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests success of a filtered persons list with valid input indexes and tag
     */
    @Test
    public void execute_validIndexAndTagFilteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toRemove = new Tag("friends");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, toRemove);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.removeTag(indexes, toRemove);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Tests failure of an unfiltered persons list with invalid input indexes but a valid tag
     */
    @Test
    public void execute_invalidIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(outOfBoundIndex);
        Tag toRemove = new Tag("friends");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    /**
     * Tests failure of a filtered persons list with invalid input indexes but a valid tag
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexes.add(outOfBoundIndex);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Tag toRemove = new Tag("friends");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests failure of an unfiltered persons list with valid input indexes but a tag that doesn't exist
     */
    @Test
    public void execute_invalidTagUnfilteredList_failure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toRemove = new Tag("hello");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_NO_SUCH_TAG);
    }

    /**
     * Tests failure of a filtered persons list with valid input indexes but a tag that doesn't exist
     */
    @Test
    public void execute_invalidTagFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toRemove = new Tag("hello");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_NO_SUCH_TAG);
    }

    /**
     * Tests failure of an unfiltered persons list with valid input indexes but a tag that exists outside of the
     * target indexes
     */
    @Test
    public void execute_validTagNotInUnFilteredList_failure() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        Tag toRemove = new Tag("owesMoney");
        RemoveTagCommand removeTagCommand = prepareCommand(indexes, toRemove);

        assertCommandFailure(removeTagCommand, model, RemoveTagCommand.MESSAGE_NO_SUCH_TAG);
    }

    @Test
    public void equals() throws Exception {
        ArrayList<Index> indexes1 = new ArrayList<Index>();
        ArrayList<Index> indexes2 = new ArrayList<Index>();
        indexes1.add(INDEX_FIRST_PERSON);
        indexes1.add(INDEX_SECOND_PERSON);
        indexes2.add(INDEX_SECOND_PERSON);
        Tag firstTag = new Tag("friends");
        Tag secondTag = new Tag("lecturer");
        final RemoveTagCommand standardCommand = new RemoveTagCommand(indexes1, firstTag);

        // same values -> returns true
        RemoveTagCommand commandWithSameValues = new RemoveTagCommand(indexes1, firstTag);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different target indexes -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(indexes2, firstTag)));

        // different target tag -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(indexes1, secondTag)));
    }

    /**
     * Returns an {@code RemoveTagCommand} with parameters {@code targetIndexes} and {@code toRemove}
     */
    private RemoveTagCommand prepareCommand(ArrayList<Index> targetIndexes, Tag toRemove) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(targetIndexes, toRemove);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_removeTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toRemove = new Tag(tagName);
        RemoveTagCommand command = (RemoveTagCommand) parser.parseCommand(RemoveTagCommand.COMMAND_WORDVAR_1
                + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName);
        assertEquals(new RemoveTagCommand(indexes, toRemove), command);
    }

    @Test
    public void parseCommand_addTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toAdd = new Tag(tagName);
        AddTagCommand command = (AddTagCommand) parser.parseCommand(AddTagCommand.COMMAND_WORDVAR_1
                + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName);
        assertEquals(new AddTagCommand(indexes, toAdd), command);
    }

```
###### \java\seedu\address\logic\parser\RedoCommandParserTest.java
``` java
public class RedoCommandParserTest {

    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UndoCommandParserTest.java
``` java
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    /*
     * Tests if the actual output of removeTag is equals to the expected
     * output when given valid target indexes and a valid tag to remove.
     */
    @Test
    public void removeTag_validIndexesAndTag_success() throws Exception {
        Person oldPerson1 = new PersonBuilder().withName("BOB").withTags("owesMoney", "friends").build();
        Person oldPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> oldPersonList = new ArrayList<ReadOnlyPerson>();
        oldPersonList.add(oldPerson1);
        oldPersonList.add(oldPerson2);
        AddressBook oldAddressBook = new AddressBook();
        oldAddressBook.setPersons(oldPersonList);

        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toRemove = new Tag("owesMoney");

        ModelManager expectedModel = new ModelManager(oldAddressBook, new UserPrefs());
        expectedModel.removeTag(indexes, toRemove);

        Person newPerson1 = new PersonBuilder().withName("BOB").withTags("friends").build();
        Person newPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> newPersonList = new ArrayList<ReadOnlyPerson>();
        newPersonList.add(newPerson1);
        newPersonList.add(newPerson2);
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(newPersonList);
        ModelManager actualModel = new ModelManager(newAddressBook, new UserPrefs());

        assertEquals(expectedModel.getAddressBook().getPersonList().toString(),
                actualModel.getAddressBook().getPersonList().toString());
    }

    /*
     * Tests if the actual output of addTag is equals to the expected
     * output when given valid target indexes and a valid tag to add.
     */
    @Test
    public void addTag_validIndexesAndTag_success() throws Exception {
        Person oldPerson1 = new PersonBuilder().withName("BOB").withTags("owesMoney", "friends").build();
        Person oldPerson2 = new PersonBuilder().withTags("classmate").build();
        List<ReadOnlyPerson> oldPersonList = new ArrayList<ReadOnlyPerson>();
        oldPersonList.add(oldPerson1);
        oldPersonList.add(oldPerson2);
        AddressBook oldAddressBook = new AddressBook();
        oldAddressBook.setPersons(oldPersonList);

        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        Tag toAdd = new Tag("rich");

        ModelManager expectedModel = new ModelManager(oldAddressBook, new UserPrefs());
        expectedModel.addTag(indexes, toAdd);

        Person newPerson1 = new PersonBuilder().withName("BOB").withTags("owesMoney", "friends", "rich").build();
        Person newPerson2 = new PersonBuilder().withTags("classmate", "rich").build();
        List<ReadOnlyPerson> newPersonList = new ArrayList<ReadOnlyPerson>();
        newPersonList.add(newPerson1);
        newPersonList.add(newPerson2);
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(newPersonList);
        ModelManager actualModel = new ModelManager(newAddressBook, new UserPrefs());

        assertEquals(expectedModel.getAddressBook().getPersonList().toString(),
                actualModel.getAddressBook().getPersonList().toString());
    }

```
###### \java\systemtests\FindCommandSystemTest.java
``` java
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
        if (expectedModel.getFilteredPersonList().size() == 0) {
            String[] parts = command.split(" ");
            ArrayList<String> keywords = new ArrayList<String>();
            for (int i = 1; i < parts.length; i++) {
                keywords.add(parts[i]);
            }
            expectedResultMessage += String.format(MESSAGE_NO_PERSON_FOUND,
                    expectedModel.getClosestMatchingName(new NameContainsKeywordsPredicate(keywords)));
        }

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

```
