# sidhmads
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
                throws PersonNotFoundException, IllegalValueException {
            fail("This method should not be called");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void findLocation(List<ReadOnlyPerson> person) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortPersons() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
        @Override
        public void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
                throws PersonNotFoundException, IllegalValueException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
        @Override
        public String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
        @Override
        public void findLocation(List<ReadOnlyPerson> person) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java
        @Override
        public void sortPersons() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code EmailCommand}.
 */
public class EmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        EmailCommand findFirstCommand = new EmailCommand(firstPredicate, "", "");
        EmailCommand findSecondCommand = new EmailCommand(secondPredicate, "", "");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        EmailCommand findFirstCommandCopy = new EmailCommand(firstPredicate, "", "");
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NOT_SENT);
        EmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, model, expectedMessage, model);
    }
    /**
     * The execution of EmailCommand makes the Travis CI fail.
     * Travis CI is unable to open the desktop mail application.
     * When running locally, use the below test. Comment it otherwise.
     */
    //    @Test
    //    public void execute_multipleKeywords_multiplePersonsFound() {
    //        String expectedMessage = String.format(MESSAGE_EMAIL_APP);
    //        EmailCommand command = prepareCommand("email to/Kurz Elle Kunz");
    //        assertCommandSuccess(command, model, expectedMessage, model);
    //    }

    /**
     * Parses {@code userInput} into a {@code EmailCommand}.
     */
    private EmailCommand prepareCommand(String userInput) {
        EmailCommand command =
                new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))), "", "");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\LocationCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code LocationCommand}.
 */
public class LocationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<String> list = new ArrayList<>();
        list.add("1");
        LocationCommand locationCommand = prepareCommand(list);

        String expectedMessage = String.format(LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        List<ReadOnlyPerson> person = new ArrayList<>();
        person.add(personToFind);
        expectedModel.findLocation(person);

        assertCommandSuccess(locationCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<String> list = new ArrayList<>();
        list.add(outOfBoundIndex.toString());
        LocationCommand locationCommand = prepareCommand(list);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<String> list = new ArrayList<>();
        list.add("1");
        LocationCommand locationCommand = prepareCommand(list);

        String expectedMessage = String.format(LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        List<ReadOnlyPerson> person = new ArrayList<>();
        person.add(personToFind);
        expectedModel.findLocation(person);

        assertCommandSuccess(locationCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        List<String> list = new ArrayList<>();
        list.add(outOfBoundIndex.toString());
        LocationCommand locationCommand = prepareCommand(list);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<String> first = new ArrayList<>();
        first.add("1");
        LocationCommand findFirstCommand = new LocationCommand(first);
        List<String> second = new ArrayList<>();
        second.add("2");
        LocationCommand findSecondCommand = new LocationCommand(second);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        List<String> firstCopy = new ArrayList<>();
        firstCopy.add("1");
        LocationCommand findFirstCommandCopy = new LocationCommand(firstCopy);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Returns a {@code LocationCommand} with the parameter {@code index}.
     */
    private LocationCommand prepareCommand(List<String> index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code RemoveTagCommand}.
 */
public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws IllegalValueException {
        Set<Tag> tagFriend = new HashSet<>();
        tagFriend.add(new Tag(VALID_TAG_FRIEND));
        RemoveTagCommand findFirstCommand = new RemoveTagCommand(new ArrayList<Index>(), tagFriend);
        Set<Tag> tagHusband = new HashSet<>();
        tagHusband.add(new Tag(VALID_TAG_HUSBAND));
        RemoveTagCommand findSecondCommand = new RemoveTagCommand(new ArrayList<Index>(), tagHusband);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        RemoveTagCommand findFirstCommandCopy = new RemoveTagCommand(new ArrayList<Index>(), tagFriend);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords() throws Exception {
        String expectedMessage = String.format("The Tag is invalid!");
        RemoveTagCommand command = prepareCommand(new ArrayList<Index>(), new HashSet<Tag>());
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws Exception {
        ObservableList<ReadOnlyPerson> personToRemoveTag = FXCollections.observableArrayList();
        personToRemoveTag.add(getTypicalAddressBook().getPersonList().get(0));
        personToRemoveTag.add(getTypicalAddressBook().getPersonList().get(1));
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(ParserUtil.parseIndex("1"));
        indexes.add(ParserUtil.parseIndex("2"));
        Set<Tag> tagFriend = new HashSet<>();
        tagFriend.add(new Tag("friends"));
        String expectedMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tagFriend.toString()) + String.format("from %s", personToRemoveTag.toString());
        RemoveTagCommand command = prepareCommand(indexes, tagFriend);
        Model mod = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        mod.removeTag(personToRemoveTag, tagFriend);
        assertCommandSuccess(command, model, expectedMessage, mod);
    }

    /**
     * Parses {@code userInput} into a {@code RemoveTagCommand}.
     */
    private RemoveTagCommand prepareCommand(ArrayList<Index> indexes, Set<Tag> tags) {
        RemoveTagCommand command =
                new RemoveTagCommand(indexes, tags);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {

    @Test
    public void executeSortOnEmptyAddressBookSuccess() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, SortCommand.COMMAND_SUCCESS, model);
    }

    @Test
    public void executeSortOnNonEmptyAddressBookSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, SortCommand.COMMAND_SUCCESS, model);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contents in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the EmailCommand code. For example, inputs "to/adam" and "to/adam sam subject/hi body/cool" take the
 * same path through the EmailCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        String[] val = new String[2];
        val[0] = "Adam";
        val[1] = "Sam";
        assertParseSuccess(parser, "email to/Adam Sam subject/hi body/cool",
                new EmailCommand((new NameContainsKeywordsPredicate(Arrays.asList(val))), "hi", "cool"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}

```
###### \java\seedu\address\logic\parser\LocationCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the LocationCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the LocationCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class LocationCommandParserTest {

    private LocationCommandParser parser = new LocationCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        List<String> valid = new ArrayList<>();
        valid.add("1");
        assertParseSuccess(parser, "1", new LocationCommand(valid));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocationCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemoveTagCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemoveTagCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() {
        try {
            Set<Tag> tag = new HashSet<>();
            tag.add(new Tag("friend"));
            RemoveTagCommand expectedRemoveTagCommand =
                    new RemoveTagCommand(new ArrayList<Index>(), tag);
            assertParseSuccess(parser, "removeTag t/friend", expectedRemoveTagCommand);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default tag's value is invalid.");
        }
    }
}
```
###### \java\systemtests\RemoveTagCommandSystemTest.java
``` java
public class RemoveTagCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE);

    @Test
    public void removeTag() {
        /* --------------- Performing removeTag operation while an unfiltered list is being shown ------------------ */
        /* the same tags will be used */
        Set<Tag> tags = tagList("friends");
        /* Case: removeTag from the first person in the list
         * command with leading spaces and trailing spaces -> removedTag
         */
        Model expectedModel = getModel();
        String command = "     " + RemoveTagCommand.COMMAND_WORD + " rm/    "
                + INDEX_FIRST_PERSON.getOneBased() + "  t/friends    ";
        ObservableList<ReadOnlyPerson> personToRemoveTag = removePersonTag(expectedModel,
                personList(String.valueOf(INDEX_FIRST_PERSON.getOneBased())), tags);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tags.toString()) + String.format("from %s", personToRemoveTag.toString());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: removeTag from the last person in the list -> removedTag */
        Model modelBeforeDeletingLast = getModel();
        ArrayList<Index> indexes = personList(String.valueOf(getLastIndex(modelBeforeDeletingLast).getOneBased()));
        assertCommandSuccess(indexes, tags);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo removeTag the last person in the list -> last person's tag removed again */
        command = RedoCommand.COMMAND_WORD;
        removePersonTag(modelBeforeDeletingLast, indexes, tags);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: removeTag from the middle person in the list -> removedTag */
        indexes.clear();
        indexes = personList(String.valueOf(getMidIndex(getModel()).getOneBased()));
        assertCommandSuccess(indexes, tags);

        /* -------------Performing removeTag operation while a filtered list is being shown --------------- */

        /* Case: filtered person list, removeTag's person's index
         * within bounds of address book and person list -> removedTag
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        indexes.clear();
        indexes = personList(String.valueOf(INDEX_FIRST_PERSON.getOneBased()));
        assertTrue(INDEX_FIRST_PERSON.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(indexes, tags);

        /* Case: filtered person list, removeTag person's index
         * within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = RemoveTagCommand.COMMAND_WORD + " rm/"
                + invalidIndex + " t/friends";
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* ---------------- Performing removeTag operation while a person card is selected ------------------- */

        /* Case: removeTag the selected person
         * -> person list panel selects the person before the deleted person
         */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = Index.fromOneBased(getLastIndex(expectedModel).getOneBased() - 1);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased());
        selectPerson(selectedIndex);
        indexes.clear();
        indexes =  personList(String.valueOf(selectedIndex.getOneBased()));
        command = RemoveTagCommand.COMMAND_WORD + " rm/ "
                + selectedIndex.getOneBased() + "  t/friends ";
        personToRemoveTag.clear();
        personToRemoveTag = removePersonTag(expectedModel, indexes, tags);
        expectedResultMessage = String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tags.toString()) + String.format("from %s", personToRemoveTag.toString());
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* -------------------------- Performing invalid delete operation ----------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " rm/ 0 " + "  t/friends ";
        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = RemoveTagCommand.COMMAND_WORD + " rm/ -1 " + "  t/friends ";
        assertCommandFailure(command, MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = RemoveTagCommand.COMMAND_WORD + " rm/ " + outOfBoundsIndex.getOneBased() + "  t/friends ";
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(RemoveTagCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(RemoveTagCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_REMOVE_TAG_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code tags} at the specified {@code indexes} in {@code model}'s address book.
     * @return the removed tags
     */
    private ObservableList<ReadOnlyPerson> removePersonTag(Model model, ArrayList<Index> indexes, Set<Tag> tags) {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ObservableList<ReadOnlyPerson> personToRemoveTag = FXCollections.observableArrayList();
        for (Index i : indexes) {
            personToRemoveTag.add(lastShownList.get(i.getZeroBased()));
        }

        try {
            model.removeTag(personToRemoveTag, tags);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("targetPerson is retrieved from model.");
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError(MESSAGE_DUPLICATE_PERSON);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Tag is invalid");
        }

        return personToRemoveTag;
    }

    /**
     * RemovesTags from the person at {@code removePersonTag} by
     * creating a default {@code RemoveTagCommand} using {@code removePersonTag} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see RemoveTagCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(ArrayList<Index> indexes, Set<Tag> tags) {
        Model expectedModel = getModel();
        ObservableList<ReadOnlyPerson> personToRemoveTag = removePersonTag(expectedModel, indexes, tags);
        String expectedResultMessage = (indexes.isEmpty())
                ? String.format(MESSAGE_DELETE_TAG_SUCCESS, tags.toString())
                : String.format(MESSAGE_DELETE_TAG_SUCCESS,
                tags.toString()) + String.format("from %s", personToRemoveTag.toString());
        String tagInitial = "";
        for (Tag tag : tags) {
            tagInitial += tag.tagName;
            tagInitial += " ";
        }
        String indexInitial = "";
        for (Index index : indexes) {
            indexInitial += String.valueOf(index.getOneBased());
            indexInitial += " ";
        }
        if (indexes.isEmpty()) {
            assertCommandSuccess(
                    RemoveTagCommand.COMMAND_WORD + "  t/"
                            + tagInitial, expectedModel, expectedResultMessage);
        } else {
            assertCommandSuccess(
                    RemoveTagCommand.COMMAND_WORD + " rm/"
                            + indexInitial + "t/" + tagInitial, expectedModel, expectedResultMessage);
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }
```
###### \java\systemtests\RemoveTagCommandSystemTest.java
``` java
    /**
     * Creates a ArrayList of Index
     * To be used for the RemoveTagCommand, as it is the input type
     */
    private ArrayList<Index> personList (String index) {
        String[] indexes = index.split("//s");
        ArrayList<Index> personList = new ArrayList<>();
        for (String number : indexes) {
            personList.add(Index.fromOneBased(Integer.parseInt(number)));
        }
        return personList;
    }

    /**
     * Creates a Set of Tags
     * To be used for the RemoveTagCommand, as it is the input type
     */
    private Set<Tag> tagList (String tags) {
        String[] tagNames = tags.split("//s");
        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tagNames) {
            try {
                tagSet.add(new Tag(tag));
            } catch (IllegalValueException ive) {
                throw new AssertionError("tag is invalid");
            }

        }
        return tagSet;
    }
```
