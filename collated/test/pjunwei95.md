# pjunwei95
###### \java\seedu\address\logic\commands\ClearPopupCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearPopupCommandTest {

    @Test
    public void execute_emptyAddressBook() {
        Model model = new ModelManager();
        ClearPopupCommand clearPopupCommand = new ClearPopupCommand();
        assertEquals(prepareCommand(model, clearPopupCommand), clearPopupCommand);
    }

    @Test
    public void execute_nonEmptyAddressBook() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearPopupCommand clearPopupCommand = new ClearPopupCommand();
        assertEquals(prepareCommand(model, clearPopupCommand), clearPopupCommand);
    }

    /**
     * Generates a new {@code ClearPopupCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearPopupCommand prepareCommand(Model model, ClearPopupCommand clearPopupCommand) {
        ClearPopupCommand command = new ClearPopupCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return clearPopupCommand;
    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        TAG_DESC_AMY = new DeleteTagDescriptorBuilder()
                .withTags(VALID_TAG_FRIEND).build();
        TAG_DESC_BOB = new DeleteTagDescriptorBuilder()
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java

        VALID_TAGLIST_1 = new HashSet<>();
        try {
            VALID_TAGLIST_1.add(new Tag(VALID_TAG_FRIEND));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        VALID_TAGLIST_2 = new HashSet<>();
        try {
            VALID_TAGLIST_2.add(new Tag(VALID_TAG_FRIEND));
            VALID_TAGLIST_2.add(new Tag(VALID_TAG_FAMILY));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        INVALID_TAGLIST = new HashSet<>();
        try {
            INVALID_TAGLIST.add(new Tag(INVALID_TAG));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        INVALID_TAGLIST_2 = new HashSet<>();
        try {
            INVALID_TAGLIST_2.add(new Tag(INVALID_TAG));
            INVALID_TAGLIST_2.add(new Tag(INVALID_TAG_2));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    static {

        DESC_ASSIGNMENT = new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();

        DESC_MEETING = new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_MEETING)
                .withPriority(VALID_PRIORITY_MEETING).withDueDate(VALID_DUE_DATE_MEETING).build();


    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {

            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);;
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredListPerson = new ArrayList<>(actualModel.getFilteredPersonList());
        List<ReadOnlyReminder> expectedFilteredListReminder = new ArrayList<>(actualModel.getFilteredReminderList());
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredListPerson, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredListReminder, actualModel.getFilteredReminderList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }
```
###### \java\seedu\address\logic\commands\DeleteTagCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.DeleteTagDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        DeleteTagDescriptor descriptor = new DeleteTagDescriptorBuilder(firstPerson).build();
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXISTING_TAGS);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showFirstPersonOnly(model);

        // edit person in filtered list into a duplicate in address book
        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON,
                new DeleteTagDescriptorBuilder(personInList).build());

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXISTING_TAGS);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTagDescriptor descriptor = new DeleteTagDescriptorBuilder().withTags(VALID_TAG_HUSBAND).build();
        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Delete the tags of  filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex,
                new DeleteTagDescriptorBuilder().withTags(VALID_TAG_HUSBAND).build());

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void checkTagDeletedPerson() {

        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteTagDescriptor descriptor = new DeleteTagDescriptorBuilder().withTags(VALID_TAG_HUSBAND)
                .build();
        DeleteTagCommand command = new DeleteTagCommand(INDEX_SECOND_PERSON, descriptor);
        ReadOnlyPerson changed = command.createTagDeletedPerson(personInList, descriptor);
        assertTrue(!changed.getTags().isEmpty());
    }



    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, TAG_DESC_AMY);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON, TAG_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_PERSON, TAG_DESC_BOB)));
    }

    /**
     * Returns an {@code DeleteTagCommand} with parameters {@code index} and {@code descriptor}
     */
    private DeleteTagCommand prepareCommand(Index index, DeleteTagDescriptor descriptor) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index, descriptor);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTagDescriptorTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.testutil.DeleteTagDescriptorBuilder;

public class DeleteTagDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        DeleteTagDescriptor descriptorWithSameValues = new DeleteTagDescriptor(TAG_DESC_AMY);
        assertTrue(TAG_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TAG_DESC_AMY.equals(TAG_DESC_AMY));

        // null -> returns false
        assertFalse(TAG_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(TAG_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(TAG_DESC_AMY.equals(TAG_DESC_BOB));

        // different tags -> returns false
        DeleteTagDescriptor editedAmy = new DeleteTagDescriptorBuilder(TAG_DESC_AMY)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(TAG_DESC_AMY.equals(editedAmy));
    }
}
```
###### \java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagCommand}.
 */
public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstTagCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondTagCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstTagCommand.equals(findFirstTagCommand));

        // same values -> returns true
        FindTagCommand findFirstTagCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstTagCommand.equals(findFirstTagCommandCopy));

        // different types -> returns false
        assertFalse(findFirstTagCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstTagCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstTagCommand.equals(findSecondTagCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleKeywords_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindTagCommand command = prepareCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code FindTagCommand}.
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTagCommand command,
                                      String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findTag() throws Exception {

        List<String> keywords = Arrays.asList("friends");
        final FindTagCommand command = new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends")));
        assertEquals(command, new FindTagCommand(new TagContainsKeywordsPredicate(keywords)));
    }


    @Test
    public void parseCommand_backup() throws Exception {
        assertTrue(parser.parseCommand(BackUpCommand.COMMAND_WORD) instanceof BackUpCommand);
        assertTrue(parser.parseCommand(BackUpCommand.COMMAND_WORD + " 3") instanceof BackUpCommand);
    }

```
###### \java\seedu\address\logic\parser\DeleteTagCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.model.tag.Tag;

public class DeleteTagCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE);

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", DeleteTagCommand.MESSAGE_NOT_DELETED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

}
```
###### \java\seedu\address\logic\parser\FindTagCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

public class FindTagCommandParserTest {

    private FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTagCommand expectedFindTagCommand =
                new FindTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("friends", "husband")));
        assertParseSuccess(parser, "friends husband", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t husband  \t", expectedFindTagCommand);
    }

}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BackUpEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

```
###### \java\seedu\address\testutil\DeleteTagDescriptorBuilder.java
``` java
import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building DeleteTagDescriptor objects.
 */
public class DeleteTagDescriptorBuilder {

    private DeleteTagDescriptor descriptor;

    public DeleteTagDescriptorBuilder() {
        descriptor = new DeleteTagDescriptor();
    }

    public DeleteTagDescriptorBuilder(DeleteTagDescriptor descriptor) {
        this.descriptor = new DeleteTagDescriptor(descriptor);
    }

    /**
     * Returns an {@code DeleteTagDescriptor} with fields containing {@code person}'s details
     */
    public DeleteTagDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new DeleteTagDescriptor();
        descriptor.setTags(person.getTags());
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code DeleteTagDescriptor}
     * that we are building.
     */
    public DeleteTagDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public DeleteTagDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\ui\ClearConfirmationTest.java
``` java
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

public class ClearConfirmationTest extends GuiUnitTest {

    private ClearConfirmation clearConfirmation;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> clearConfirmation = new ClearConfirmation());

        FxToolkit.showStage();
    }

    @Test
    public void display() {
        ClearConfirmation expectedClearConfirmation = clearConfirmation;
        assertEquals(expectedClearConfirmation, clearConfirmation);
    }
}
```
