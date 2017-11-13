# aaronyhsoh
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\FavouriteCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for FavouriteCommand.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Favourite filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_favouritePersonUnfilteredList_success() throws Exception {
        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Person favouritedPerson = new PersonBuilder(firstPerson).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);
        favouritedPerson.setFavourite(true);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_SUCCESS, favouritedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.favouritePerson(model.getFilteredPersonList().get(0), favouritedPerson);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_favouritePersonFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favouritedPerson = new PersonBuilder(personInFilteredList).build();
        favouritedPerson.setFavourite(true);
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_PERSON_SUCCESS, favouritedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.favouritePerson(model.getFilteredPersonList().get(0), favouritedPerson);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON);

        // same values -> returns true

        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_SECOND_PERSON)));

    }

    /**
     * Returns an {@code FavouriteCommand} with parameters {@code index} and {@code descriptor}
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}

```
###### \java\seedu\address\logic\commands\RedoCommandTest.java
``` java
    @Test
    public void executeValidIndex() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(deleteCommandOne, deleteCommandTwo));
        RedoCommand redoCommand = new RedoCommand(INDEX_SECOND_COMMAND);
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeIndexGreaterThanRedoStack_success() throws CommandException {
        UndoRedoStack undoRedoStack = prepareStack(Collections.emptyList(),
                Arrays.asList(deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand(INDEX_SECOND_COMMAND);
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // single command in redoStack
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        RedoCommand redoFirstCommand = new RedoCommand(INDEX_FIRST_COMMAND);
        RedoCommand redoSecondCommand = new RedoCommand(INDEX_SECOND_COMMAND);

        // same object -> returns true
        assertTrue(redoFirstCommand.equals(redoFirstCommand));

        // same values -> returns true
        RedoCommand redoFirstCommandCopy = new RedoCommand(INDEX_FIRST_COMMAND);
        assertTrue(redoFirstCommand.equals(redoFirstCommandCopy));

        // different types -> returns false
        assertFalse(redoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(redoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(redoFirstCommand.equals(redoSecondCommand));
    }
}
```
###### \java\seedu\address\logic\commands\UndoCommandTest.java
``` java
    @Test
    public void executeValidIndex() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(INDEX_SECOND_COMMAND);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeIndexGreaterThanUndoStack_success() throws CommandException {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(INDEX_SECOND_COMMAND);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();

        // single command in undoStack
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        UndoCommand undoFirstCommand = new UndoCommand(INDEX_FIRST_COMMAND);
        UndoCommand undoSecondCommand = new UndoCommand(INDEX_SECOND_COMMAND);

        // same object -> returns true
        assertTrue(undoFirstCommand.equals(undoFirstCommand));

        // same values -> returns true
        UndoCommand undoFirstCommandCopy = new UndoCommand(INDEX_FIRST_COMMAND);
        assertTrue(undoFirstCommand.equals(undoFirstCommandCopy));

        // different types -> returns false
        assertFalse(undoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(undoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(undoFirstCommand.equals(undoSecondCommand));
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    public static final String EMPTY_PHONE = "-";
    public static final String EMPTY_EMAIL = "-";
    public static final String EMPTY_ADDRESS = "-";
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // missing phone number
        Person expectedPersonWithoutPhone = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(EMPTY_PHONE)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPersonWithoutPhone));

        // missing address
        Person expectedPersonWithoutAddress = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(EMPTY_ADDRESS).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY, new AddCommand(expectedPersonWithoutAddress));

        // missing email
        Person expectedPersonWithoutEmail = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(EMPTY_EMAIL).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPersonWithoutEmail));
```
###### \java\seedu\address\logic\parser\FavouriteCommandParserTest.java
``` java
public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        assertParseSuccess(parser, "1", new FavouriteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}

```
###### \java\seedu\address\logic\parser\RedoCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RedoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RedoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */

public class RedoCommandParserTest {

    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_validArgs_returnsRedoCommand() {
        assertParseSuccess(parser, "1", new RedoCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UndoCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UndoCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UndoCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        assertParseSuccess(parser, "1", new UndoCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    public static final String EMPTY_PHONE = "-";
    public static final String EMPTY_EMAIL = "-";
    public static final String EMPTY_ADDRESS = "-";
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing phone -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(EMPTY_PHONE).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(EMPTY_EMAIL)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(EMPTY_ADDRESS).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing phone, email, address, -> accepted */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(EMPTY_PHONE).withEmail(EMPTY_EMAIL)
                .withAddress(EMPTY_ADDRESS).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
