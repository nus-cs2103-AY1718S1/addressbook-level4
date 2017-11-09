# rushan-khor
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/logic/parser/BatchCommandParserTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/logic/parser/CopyCommandParserTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/logic/commands/BatchCommandTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/logic/commands/DuplicatesCommandTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/logic/commands/CopyCommandTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/model/person/HasPotentialDuplicatesPredicateTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/model/AddressBookTest.java
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
###### /,CS2103/addressbook-level4/src/test/java/seedu/address/model/ModelManagerTest.java
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
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/logic/parser/BatchCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class BatchCommandParser implements Parser<BatchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BatchCommand parse(String args) throws ParseException {
        final Set<String> tagNames = new HashSet<>();
        Scanner tagNameScanner = new Scanner(args);

        while (tagNameScanner.hasNext()) {
            tagNames.add(tagNameScanner.next());
        }

        try {
            Set<Tag> tags = ParserUtil.parseTags(tagNames);
            return new BatchCommand(tags);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/logic/parser/CopyCommandParser.java
``` java
/**
 * Parses input arguments and creates a new CopyCommand object
 */
public class CopyCommandParser implements Parser<CopyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CopyCommand
     * and returns an CopyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CopyCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/logic/commands/CopyCommand.java
``` java
    @Override
    public CommandResult execute() throws CommandException {
        String targetEmail = getTargetEmail();
        String commandResultMessage = "";

        boolean emailIsValid = isEmailValid(targetEmail);
        if (emailIsValid) {
            putIntoClipboard(targetEmail);
            commandResultMessage = String.format(MESSAGE_COPY_PERSON_SUCCESS, targetPerson.getName());
        } else {
            commandResultMessage = String.format(MESSAGE_COPY_PERSON_EMPTY, targetPerson.getName());
        }

        return new CommandResult(commandResultMessage);
    }

    /**
     * Gets the target person's email address.
     * @return     the email address of the person at the list {@code targetIndex}
     * @exception  CommandException if the {@code targetIndex}
     *             argument is greater than or equal to the {@code lastShownList} size.
     */
    public String getTargetEmail() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean indexIsOutOfBounds = targetIndex.getZeroBased() >= lastShownList.size();
        if (indexIsOutOfBounds) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetPerson = lastShownList.get(targetIndex.getZeroBased());
        return targetPerson.getEmail().toString();
    }

    public boolean isEmailValid(String email) {
        return !"null@null.com".equalsIgnoreCase(email) && !"".equals(email);
    }

    /**
     * Puts target person's email address into the system clipboard.
     */
    private void putIntoClipboard(String resultantEmailAddress) {
        Clipboard systemClipboard = Clipboard.getSystemClipboard();
        ClipboardContent systemClipboardContent = new ClipboardContent();

        systemClipboardContent.putString(resultantEmailAddress);
        systemClipboard.setContent(systemClipboardContent);
    }
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/logic/commands/BatchCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class BatchCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "batch";
    public static final String COMMAND_ALIAS = "b"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons with the given tags.\n"
            + COMMAND_ALIAS + ": Shorthand equivalent for batch delete. \n"
            + "Parameters: TAG (must be a tag that at least one person has)\n"
            + "Example 1: " + COMMAND_ALIAS + " friends \n"
            + "Example 2: " + COMMAND_WORD + " colleagues";

    public static final String MESSAGE_BATCH_DELETE_SUCCESS = "Deleted Persons with Tags: %1$s";

    private final Set<Tag> tagsToDelete;

    public BatchCommand(Set<Tag> tagsToDelete) {
        this.tagsToDelete = tagsToDelete;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.deletePersonsByTags(tagsToDelete);
        } catch (PersonNotFoundException e) {
            throw new CommandException("One of the tags is not in use. Remove it and try again.");
        }

        return new CommandResult(String.format(MESSAGE_BATCH_DELETE_SUCCESS, tagsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BatchCommand // instanceof handles nulls
                && this.tagsToDelete.equals(((BatchCommand) other).tagsToDelete)); // state check
    }
}
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/logic/commands/DuplicatesCommand.java
``` java
/**
 * Finds and lists persons in address book with possible duplicate entries (by name).
 * Keyword matching is case insensitive.
 */
public class DuplicatesCommand extends Command {

    public static final String COMMAND_WORD = "duplicates";
    public static final String COMMAND_ALIAS = "dups"; // shorthand equivalent alias

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are possible duplicates "
            + " and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_ALIAS;

    @Override
    public CommandResult execute() {
        model.updateDuplicatePersonList();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DuplicatesCommand); // instanceof handles nulls
    }
}
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/person/HasPotentialDuplicatesPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Bloodtype} matches any of the keywords given.
 */
public class HasPotentialDuplicatesPredicate implements Predicate<ReadOnlyPerson> {
    private final HashSet<Name> duplicateNames;

    public HasPotentialDuplicatesPredicate(HashSet<Name> duplicateNames) {
        this.duplicateNames = duplicateNames;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return duplicateNames.contains(person.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HasPotentialDuplicatesPredicate // instanceof handles nulls
                && this.duplicateNames.equals(((HasPotentialDuplicatesPredicate) other).duplicateNames)); // state check
    }

}
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/AddressBook.java
``` java
    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     *
     * @param tag all persons containing this tag will be deleted
     */
    public void deletePersonsWithTag(Tag tag) throws PersonNotFoundException {
        ArrayList<Person> toRemove = new ArrayList<>();
        for (Person person : persons) {
            if (person.hasTag(tag)) {
                toRemove.add(person);
            }
        }

        if (toRemove.isEmpty()) {
            throw new PersonNotFoundException();
        }
        for (Person person : toRemove) {
            removePerson(person);
            removeUnusedTags(person.getTags());
        }
    }
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/AddressBook.java
``` java
    /**
     * Removes {@code tagsToRemove} from this {@code AddressBook} if and only if they are not help by any persons.
     */
    public void removeUnusedTags(Set<Tag> tagsToRemove) {
        Set<Tag> cleanedTagList = getTagsExcluding(tagsToRemove);
        tags.setTags(cleanedTagList);
        syncMasterTagListWith(persons);
    }

    /**
     * Returns tag list from this {@code AddressBook} excluding {@code excludedTags}.
     */
    public Set<Tag> getTagsExcluding(Set<Tag> excludedTags) {
        Set<Tag> results = tags.toSet();
        for (Tag excludedTag : excludedTags) {
            results.remove(excludedTag);
        }
        return results;
    }
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/ModelManager.java
``` java
    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     *
     * @param tag all persons containing this tag will be deleted
     */
    public void deletePersonsWithTag(Tag tag) throws PersonNotFoundException {
        addressBook.deletePersonsWithTag(tag);
        indicateAddressBookChanged();
    }

    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     *
     * @param tags all persons containing this tag will be deleted
     */
    @Override
    public void deletePersonsByTags(Set<Tag> tags) throws PersonNotFoundException {
        for (Tag tag : tags) {
            deletePersonsWithTag(tag);
        }
    }

```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/ModelManager.java
``` java
    /**
     * Gets a list of duplicate names
     */
    private HashSet<Name> getDuplicateNames() {
        HashSet<Name> examinedNames = new HashSet<>();
        HashSet<Name> duplicateNames = new HashSet<>();
        ObservableList<ReadOnlyPerson> allPersonsInAddressBook = getFilteredPersonList();

        for (ReadOnlyPerson person : allPersonsInAddressBook) {
            if (examinedNames.contains(person.getName())) {
                duplicateNames.add(person.getName());
            }
            examinedNames.add(person.getName());
        }
        return duplicateNames;
    }
```
###### /,CS2103/addressbook-level4/src/main/java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateDuplicatePersonList() {
        HashSet<Name> duplicateNames = getDuplicateNames();
        HasPotentialDuplicatesPredicate predicate = new HasPotentialDuplicatesPredicate(duplicateNames);
        updateFilteredPersonList(predicate);
    }
```
