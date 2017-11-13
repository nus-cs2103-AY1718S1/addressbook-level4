# rushan-khor
###### /java/seedu/address/logic/parser/BatchCommandParserTest.java
``` java
public class BatchCommandParserTest {

    private BatchCommandParser parser = new BatchCommandParser();

    @Test
    public void testValidTags() {
        HashSet<Tag> tagSetForTest = new HashSet<>();
        try {
            tagSetForTest.add(new Tag("tag1", "grey"));
        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        }
        assertParseSuccess(parser, "tag1", new BatchCommand(tagSetForTest));
        assertParseSuccess(parser, " tag1 ", new BatchCommand(tagSetForTest));
        assertParseSuccess(parser, "tag1 ", new BatchCommand(tagSetForTest));
    }

    @Test
    public void testParseException() {
        assertParseFailure(parser, ".", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/CopyCommandParserTest.java
``` java
public class CopyCommandParserTest {

    private CopyCommandParser parser = new CopyCommandParser();

    @Test
    public void parseValidArgsReturnsCopyCommand() {
        assertParseSuccess(parser, "1", new CopyCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/BatchCommandTest.java
``` java
public class BatchCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute() throws IllegalValueException, CommandException {

        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        Set<Tag> tagsToDelete = new HashSet<>();
        BatchCommand command = new BatchCommand(tagsToDelete);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        //Should not throw any error
        try {
            command.execute();
        } catch (CommandException e) {
            fail();
        }

        tagsToDelete.add(new Tag("nosuczhtag", "red"));

        command = new BatchCommand(tagsToDelete);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        thrown.expect(CommandException.class);
        command.execute();

    }
}

```
###### /java/seedu/address/logic/commands/DuplicatesCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code DuplicatesCommand}.
 */
public class DuplicatesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private DuplicatesCommand prepareCommand() {
        DuplicatesCommand command = new DuplicatesCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void executeZeroDuplicatesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DuplicatesCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(DuplicatesCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### /java/seedu/address/logic/commands/CopyCommandTest.java
``` java
public class CopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private ReadOnlyPerson noEmailPerson = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("null@null.com").withAddress("little india")
            .withBloodType("AB-").withAppointment("Hoon Meier").build();

    @Test
    public void testGetValidTargetEmail() {
        CopyCommand command = prepareCommand(INDEX_FIRST_PERSON);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        String result = "";

        try {
            result = command.getTargetEmail();
        } catch (CommandException e) {
            fail();
        }

        assertTrue("alice@example.com".equals(result));
    }

    @Test
    public void testGetInvalidTargetEmail() {
        CopyCommand command = prepareInvalidCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void testIsEmailValid() {
        CopyCommand command = prepareCommand(INDEX_FIRST_PERSON);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        boolean result = command.isEmailValid(noEmailPerson.getEmail().toString());

        assertFalse(result);
    }

    @Test
    public void testExecute() {
        CopyCommand command = prepareInvalidPersonCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = new CommandResult("Empty");

        try {
            result = command.execute();
        } catch (CommandException e) {
            fail();
        }

        assertEquals(result.feedbackToUser, "Jon Anderson has no email address.");
    }

    /**
     * Returns a {@code CopyCommand} with the parameter {@code index}.
     */
    private CopyCommand prepareCommand(Index index) {
        CopyCommand copyCommand = new CopyCommand(index);
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }

    private CopyCommand prepareInvalidPersonCommand() {
        CopyCommand copyCommand = new CopyCommand(Index.fromOneBased(8));
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }

    private CopyCommand prepareInvalidCommand() {
        CopyCommand copyCommand = new CopyCommand(Index.fromOneBased(999));
        copyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return copyCommand;
    }
}

```
###### /java/seedu/address/model/person/HasPotentialDuplicatesPredicateTest.java
``` java
public class HasPotentialDuplicatesPredicateTest {

    @Test
    public void equals() {
        HashSet<Name> firstPredicateKeywordSet = new HashSet<>();
        HashSet<Name> secondPredicateKeywordSet = new HashSet<>();

        try {
            firstPredicateKeywordSet.add(new Name("CARL"));
            secondPredicateKeywordSet.add(new Name("ALICE"));
            firstPredicateKeywordSet.add(new Name("BOB"));
            secondPredicateKeywordSet.add(new Name("BOB"));
        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        }

        HasPotentialDuplicatesPredicate firstPredicate =
                new HasPotentialDuplicatesPredicate(firstPredicateKeywordSet);
        HasPotentialDuplicatesPredicate secondPredicate =
                new HasPotentialDuplicatesPredicate(secondPredicateKeywordSet);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        HasPotentialDuplicatesPredicate firstPredicateCopy =
                new HasPotentialDuplicatesPredicate(firstPredicateKeywordSet);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different blood type -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
    @Test
    public void testDeletePersonsWithTag() {
        // Setup for testing
        AddressBook addressBookUnderTest = new AddressBook();
        try {
            addressBookUnderTest.addPerson(ALICE);
            addressBookUnderTest.addPerson(BENSON);
            addressBookUnderTest.addPerson(CARL);
        } catch (DuplicatePersonException dpe) {
            System.out.println(dpe.getMessage());
        }
        try {
            addressBookUnderTest.deletePersonsWithTag(new Tag("friends", Tag.DEFAULT_COLOR));
        } catch (IllegalValueException | PersonNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Setup expected outcome
        AddressBook expectedAddressBook = new AddressBook();
        try {
            expectedAddressBook.addPerson(CARL);
        } catch (DuplicatePersonException dpe) {
            System.out.println(dpe.getMessage());
        }

        // Test equality
        assertEquals(addressBookUnderTest, expectedAddressBook);
    }

```
###### /java/seedu/address/model/ModelManagerTest.java
``` java
    @Test
    public void testDeletePersonsWithTag() {
        // Setup for testing
        ModelManager modelManagerUnderTest = new ModelManager();
        try {
            modelManagerUnderTest.addPerson(ALICE);
            modelManagerUnderTest.addPerson(BENSON);
            modelManagerUnderTest.addPerson(CARL);
        } catch (DuplicatePersonException dpe) {
            System.out.println(dpe.getMessage());
        }
        Set<Tag> tagSet = new HashSet<>();
        try {
            tagSet.add(new Tag("friends", Tag.DEFAULT_COLOR));
            modelManagerUnderTest.deletePersonsByTags(tagSet);
        } catch (IllegalValueException | PersonNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Setup expected outcome
        ModelManager expectedModelManager = new ModelManager();
        try {
            expectedModelManager.addPerson(CARL);
        } catch (DuplicatePersonException dpe) {
            System.out.println(dpe.getMessage());
        }

        // Test equality
        assertEquals(modelManagerUnderTest, expectedModelManager);
    }
```
