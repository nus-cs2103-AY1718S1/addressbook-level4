# qihao27
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    //---------------- Tests for isAlnumOnly --------------------------------------

    @Test
    public void isAlnumOnly() {

        // empty strings
        assertFalse(StringUtil.isAlnumOnly("")); // Boundary value
        assertFalse(StringUtil.isAlnumOnly("  "));

        // string with white space
        assertFalse(StringUtil.isAlnumOnly(" john ")); // Leading/trailing spaces

        // contains special characters
        assertFalse(StringUtil.isAlnumOnly("j@hn#"));

        // EP: valid options, should return true
        assertTrue(StringUtil.isAlnumOnly("john"));
        assertTrue(StringUtil.isAlnumOnly("John"));
        assertTrue(StringUtil.isAlnumOnly("Boom Shakalaka"));
        assertTrue(StringUtil.isAlnumOnly("bOoM ShakAlaKa")); // case insensitive
    }

    //---------------- Tests for isSortOption --------------------------------------

    @Test
    public void isSortOption() {

        // empty strings
        assertFalse(StringUtil.isSortOption("")); // Boundary value
        assertFalse(StringUtil.isSortOption("  "));

        // does not contain a prefix option indicator
        assertFalse(StringUtil.isSortOption("a"));

        // contains number
        assertFalse(StringUtil.isSortOption("-1"));
        assertFalse(StringUtil.isSortOption("-e1"));

        // capital letter
        assertFalse(StringUtil.isSortOption("-T"));

        // options with white space
        assertFalse(StringUtil.isSortOption(" -p ")); // Leading/trailing spaces
        assertFalse(StringUtil.isSortOption("- p"));  // Spaces in the middle

        // EP: valid options, should return true
        assertTrue(StringUtil.isSortOption("-n"));
        assertTrue(StringUtil.isSortOption("-p"));
        assertTrue(StringUtil.isSortOption("-e"));
        assertTrue(StringUtil.isSortOption("-a"));
        assertTrue(StringUtil.isSortOption("-t"));
    }

    //---------------- Tests for isFilePathOption --------------------------------------

    @Test
    public void isFilePath() {

        // empty strings
        assertFalse(StringUtil.isFilePath("")); // Boundary value
        assertFalse(StringUtil.isFilePath("  "));

        // does not contain a suffix ".xml"
        assertFalse(StringUtil.isFilePath("data/addressbook"));

        // does not contain a file name
        assertFalse(StringUtil.isFilePath("data/"));

        // EP: valid file path, should return true
        assertTrue(StringUtil.isFilePath("data/addressbook.xml"));
        assertTrue(StringUtil.isFilePath("C:\\shakalaka.xml"));
    }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortPerson (String option) throws NoPersonFoundException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\DeleteAltCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_FIRST_PERSON;
import static seedu.address.testutil.TypicalNames.NAME_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteAltCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validName_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAltCommand deleteAltCommand = prepareCommand(NAME_FIRST_PERSON);

        String expectedMessage = String.format(DeleteAltCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteAltCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() throws Exception {
        DeleteAltCommand deleteAltCommand = prepareCommand("invalid name");

        assertCommandFailure(deleteAltCommand, model, Messages.MESSAGE_PERSON_NAME_ABSENT);
    }

    @Test
    public void execute_insufficientInput_throwsCommandException() throws Exception {
        DeleteAltCommand deleteAltCommand = prepareCommand("al");

        assertCommandFailure(deleteAltCommand, model, Messages.MESSAGE_PERSON_NAME_INSUFFICIENT);
    }

    @Test
    public void equals() {
        DeleteAltCommand deleteFirstAltCommand = new DeleteAltCommand(NAME_FIRST_PERSON);
        DeleteAltCommand deleteSecondAltCommand = new DeleteAltCommand(NAME_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstAltCommand.equals(deleteFirstAltCommand));

        // same values -> returns true
        DeleteAltCommand deleteFirstAltCommandCopy = new DeleteAltCommand(NAME_FIRST_PERSON);
        assertTrue(deleteFirstAltCommand.equals(deleteFirstAltCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstAltCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstAltCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstAltCommand.equals(deleteSecondAltCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code name}.
     */
    private DeleteAltCommand prepareCommand(String name) {
        DeleteAltCommand deleteAltCommand = new DeleteAltCommand(name);
        deleteAltCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAltCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXPORTED;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_C_CREATE_NEW_FOLDER;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_DOCS;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_LOCAL_C_DRIVE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_exportSuccess_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CommandResult result = new ExportCommand(FILE_PATH_DOCS).execute();
        assertEquals(MESSAGE_FILE_EXPORTED + FILE_PATH_DOCS, result.feedbackToUser);
    }

    @Test
    public void execute_exportLocalDriveSuccess_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CommandResult result = new ExportCommand(FILE_PATH_LOCAL_C_DRIVE).execute();
        assertEquals(MESSAGE_FILE_EXPORTED + FILE_PATH_LOCAL_C_DRIVE, result.feedbackToUser);
    }

    @Test
    public void execute_exportCreateNewFolderSuccess_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CommandResult result = new ExportCommand(FILE_PATH_C_CREATE_NEW_FOLDER).execute();
        assertEquals(MESSAGE_FILE_EXPORTED + FILE_PATH_C_CREATE_NEW_FOLDER, result.feedbackToUser);
    }

}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOptions.OPTION_ADDRESS;
import static seedu.address.testutil.TypicalOptions.OPTION_EMAIL;
import static seedu.address.testutil.TypicalOptions.OPTION_NAME;
import static seedu.address.testutil.TypicalOptions.OPTION_PHONE;
import static seedu.address.testutil.TypicalOptions.OPTION_TAG;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.NoPersonFoundException;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_sortListByName() throws NoPersonFoundException {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortPerson(OPTION_NAME);

        SortCommand sortByName = prepareCommand(OPTION_NAME);
        assertCommandSuccess(sortByName, model, SortCommand.MESSAGE_SUCCESS_BY_NAME, expectedModel);
    }

    @Test
    public void execute_sortListByPhone() throws NoPersonFoundException {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortPerson(OPTION_PHONE);

        SortCommand sortByName = prepareCommand(OPTION_PHONE);
        assertCommandSuccess(sortByName, model, SortCommand.MESSAGE_SUCCESS_BY_PHONE, expectedModel);
    }

    @Test
    public void execute_sortListByEmail() throws NoPersonFoundException {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortPerson(OPTION_EMAIL);

        SortCommand sortByName = prepareCommand(OPTION_EMAIL);
        assertCommandSuccess(sortByName, model, SortCommand.MESSAGE_SUCCESS_BY_EMAIL, expectedModel);
    }

    @Test
    public void execute_sortListByAddress() throws NoPersonFoundException {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortPerson(OPTION_ADDRESS);

        SortCommand sortByName = prepareCommand(OPTION_ADDRESS);
        assertCommandSuccess(sortByName, model, SortCommand.MESSAGE_SUCCESS_BY_ADDRESS, expectedModel);
    }

    @Test
    public void execute_sortListByTag() throws NoPersonFoundException {
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.sortPerson(OPTION_TAG);

        SortCommand sortByName = prepareCommand(OPTION_TAG);
        assertCommandSuccess(sortByName, model, SortCommand.MESSAGE_SUCCESS_BY_TAG, expectedModel);
    }

    /**
     * Returns a {@code SortCommand} with the parameter {@code option}.
     */
    private SortCommand prepareCommand(String option) {
        SortCommand sortCommand = new SortCommand(option);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_delete_alt() throws Exception {
        DeleteAltCommand command = (DeleteAltCommand) parser.parseCommand(
                DeleteAltCommand.COMMAND_WORD + " " + NAME_FIRST_PERSON);
        assertEquals(new DeleteAltCommand(NAME_FIRST_PERSON), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + OPTION_NAME);
        assertEquals(new SortCommand(OPTION_NAME), command);
    }

    @Test
    public void parseCommand_export() throws Exception {
        ExportCommand command = (ExportCommand) parser.parseCommand(
            ExportCommand.COMMAND_WORD + " " + FILE_PATH_DOCS);
        assertEquals(new ExportCommand(FILE_PATH_DOCS), command);
    }

    @Test
    public void parseCommand_export_alias() throws Exception {
        ExportCommand command = (ExportCommand) parser.parseCommand(
            ExportCommand.COMMAND_ALIAS + " " + FILE_PATH_DOCS);
        assertEquals(new ExportCommand(FILE_PATH_DOCS), command);
    }

```
###### \java\seedu\address\logic\parser\DeleteAltCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNames.NAME_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteAltCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteAltCommand code. For example, inputs "abc" and "abc 1" take the
 * same path through the DeleteAltCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteAltCommandParserTest {

    private DeleteAltCommandParser parser = new DeleteAltCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteAltCommand() {
        assertParseSuccess(parser, "Alice Pauline", new DeleteAltCommand(NAME_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAltCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseString_invalidInput_throwsIllegalValueExceptionException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_STRING);
        ParserUtil.parseString("1a");
    }

    @Test
    public void parseString_validInput_success() throws Exception {
        // No numbers
        assertEquals(NAME_FIRST_PERSON, ParserUtil.parseString("Alice Pauline"));

        // Leading and trailing whitespaces
        assertEquals(NAME_FIRST_PERSON, ParserUtil.parseString("  Alice Pauline  "));
    }

    @Test
    public void parseOption_invalidInput_throwsIllegalValueExceptionException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_OPTION);
        ParserUtil.parseOption("-T");
    }

    @Test
    public void parseOption_validInput_success() throws Exception {
        // No capital letter
        assertEquals(OPTION_NAME, ParserUtil.parseOption("-n"));

        // Leading and trailing whitespaces
        assertEquals(OPTION_NAME, ParserUtil.parseOption("  -n  "));
    }

    @Test
    public void parseFilePath_invalidInput_throwsIllegalValueExceptionException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_FILE_PATH);
        ParserUtil.parseFilePath("docs/");
    }

    @Test
    public void parseFilePath_validInput_success() throws Exception {
        // Contains ".xml" as suffix
        assertEquals(FILE_PATH_DOCS, ParserUtil.parseFilePath("docs/AcquaiNote.xml"));

        // Leading and trailing whitespaces
        assertEquals(FILE_PATH_DOCS, ParserUtil.parseFilePath("  docs/AcquaiNote.xml  "));
    }
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOptions.OPTION_NAME;
import static seedu.address.testutil.TypicalOptions.OPTION_TAG;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SortCommand code. For example, inputs "-n 1" and "-n1" take the
 * same path through the SortCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "-n", new SortCommand(OPTION_NAME));
        assertParseSuccess(parser, "-t", new SortCommand(OPTION_TAG));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "- p", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-e2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-A", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        assertEquals(Collections.emptyList(), addressBook.getTodoList());
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        List<TodoItem> newTodo = new ArrayList<>(ALICE.getTodoItems());
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newTodo);
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void getTodoList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTodoList().remove(0);
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        private final ObservableList<TodoItem> todo = FXCollections.observableArrayList();

        AddressBookStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags,
                        Collection<? extends TodoItem> todoItems) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
            this.todo.setAll(todoItems);
        }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        @Override
        public ObservableList<TodoItem> getTodoList() {
            return todo;
        }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
        AddressBook backup = getTypicalAddressBook();
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
        storageManager.backupAddressBook(backup);
        ReadOnlyAddressBook retrivedBackup = storageManager.readAddressBook().get();
        assertEquals(backup, new AddressBook(retrivedBackup));
```
###### \java\seedu\address\testutil\TypicalFilePath.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalFilePath {
    public static final String FILE_PATH_DOCS = "docs/AcquaiNote.xml";
    public static final String FILE_PATH_LOCAL_C_DRIVE = "C:\\AcquaiNote.xml";
    public static final String FILE_PATH_C_CREATE_NEW_FOLDER = "C:\\shalkalaka\\AcquaiNote.xml";
}
```
###### \java\seedu\address\testutil\TypicalNames.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalNames {
    public static final String NAME_FIRST_PERSON = "Alice Pauline";
    public static final String NAME_SECOND_PERSON = "Benson Meier";
    public static final String NAME_THIRD_PERSON = "Carl Kurz";
}
```
###### \java\seedu\address\testutil\TypicalOptions.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalOptions {
    public static final String OPTION_NAME = "-n";
    public static final String OPTION_PHONE = "-p";
    public static final String OPTION_EMAIL = "-e";
    public static final String OPTION_ADDRESS = "-a";
    public static final String OPTION_TAG = "-t";
}
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    private static final NewResultCheckEvent NEW_CHECK_EVENT_STUB =
        new NewResultCheckEvent("Stub", true);
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
        // new result for invalid/wrong command
        postNow(NEW_CHECK_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(NEW_CHECK_EVENT_STUB.message, resultDisplayHandle.getText());
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    private static final int TOTAL_PERSONS_STUB = 0;
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, TOTAL_PERSONS_STUB);
```
###### \java\seedu\address\ui\testutil\UiPartRule.java
``` java
    private static final String[] CSS_FILES = {"view/LightTheme.css", "view/Extensions.css"};
```
