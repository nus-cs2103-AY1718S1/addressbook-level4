# qihao27
###### \java\guitests\guihandles\FavouriteStarHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a todolist count in the person card.
 */
public class FavouriteStarHandle extends NodeHandle<Node> {
    private static final String FAVOURITE_FIELD_ID = "#favourite";

    private final Label favouriteLabel;

    public FavouriteStarHandle(Node favouriteNode) {
        super(favouriteNode);

        this.favouriteLabel = getChildNode(FAVOURITE_FIELD_ID);
    }

    public String getFavouriteStar() {
        return favouriteLabel.getText();
    }
}
```
###### \java\guitests\guihandles\TodoCountHandle.java
``` java
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a todolist count in the person card.
 */
public class TodoCountHandle extends NodeHandle<Node> {
    private static final String TODO_FIELD_ID = "#totalTodo";

    private final Label todoLabel;

    public TodoCountHandle(Node todoNode) {
        super(todoNode);

        this.todoLabel = getChildNode(TODO_FIELD_ID);
    }

    public String getTodoCount() {
        return todoLabel.getText();
    }
}
```
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
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
###### \java\seedu\address\commons\util\XmlUtilTest.java
``` java
        assertEquals(0, dataFromFile.getTodoList().size());
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortPerson (String option) throws NoPersonFoundException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\DeleteByNameCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByNameCommand}.
 */
public class DeleteByNameCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        DeleteByNameCommand deleteFirstCommand = new DeleteByNameCommand(firstPredicate);
        DeleteByNameCommand deleteSecondCommand = new DeleteByNameCommand(secondPredicate);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByNameCommand deleteFirstCommandCopy = new DeleteByNameCommand(firstPredicate);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void execute_validInput_success() throws Exception {
        DeleteByNameCommand deleteByNameCommand = prepareCommand("carl");
        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, CARL);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(CARL);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multiplePersonsFound_throwCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_FOUND);

        DeleteByNameCommand command = prepareCommand("Meier");
        CommandResult commandResult = command.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_FOUND, expectedModel);
        assertCommandExecuteSuccess(commandResult, DeleteByNameCommand.MESSAGE_MULTIPLE_PERSON_FOUND,
            Arrays.asList(DANIEL, BENSON, HOON), Arrays.asList(DANIEL, BENSON, HOON));
    }

    @Test
    public void execute_nameNotFound_throwCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteByNameCommand.MESSAGE_PERSON_NAME_ABSENT);

        DeleteByNameCommand command = prepareCommand("John");
        CommandResult commandResult = command.execute();
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, DeleteByNameCommand.MESSAGE_PERSON_NAME_ABSENT, expectedModel);
        assertCommandExecuteSuccess(commandResult, DeleteByNameCommand.MESSAGE_PERSON_NAME_ABSENT,
            Arrays.asList(DANIEL, BENSON, HOON), Arrays.asList());
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code name}.
     */
    private DeleteByNameCommand prepareCommand(String userInput) {
        DeleteByNameCommand deleteByNameCommand =
                new DeleteByNameCommand(
                new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        deleteByNameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByNameCommand;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same
     * if the size of {@code ActualList<ReadOnlyPerson>} is more than 1
     */
    private void assertCommandExecuteSuccess(CommandResult commandResult, String expectedMessage,
                                      List<ReadOnlyPerson> oldList,
                                      List<ReadOnlyPerson> newList) throws CommandException {
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(oldList, model.getFilteredPersonList());
        if (newList.size() > 1) {
            AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
            assertEquals(expectedAddressBook, model.getAddressBook());
        }
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXPORTED;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_EXPORT_TEST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ExportCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_exportSuccess_throwsParseException() throws ParseException {
        ExportCommand command = prepareCommand(FILE_PATH_EXPORT_TEST);
        assertCommandSuccess(command, MESSAGE_FILE_EXPORTED + FILE_PATH_EXPORT_TEST);
    }

    /**
     * Parses {@code userInput} into a {@code LockCommand} in default mode.
     */
    private ExportCommand prepareCommand(String userInput) throws ParseException {
        ExportCommand command = new ExportCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     */
    private void assertCommandSuccess(ExportCommand command, String expectedMessage) {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.NoPersonFoundException;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void execute_emptyList_throwCommandException() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(SortCommand.NO_PERSON_FOUND);

        model.resetData((new ModelManager()).getAddressBook());

        SortCommand sortEmptyList = prepareCommand(OPTION_TAG);
        sortEmptyList.executeUndoableCommand();
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
    public void parseCommand_deleteByName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        DeleteByNameCommand command = (DeleteByNameCommand) parser.parseCommand(
            DeleteByNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new DeleteByNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
###### \java\seedu\address\logic\parser\DeleteByNameCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteByNameCommand code. For example, inputs "abc" and "abc 1" take the
 * same path through the DeleteByNameCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() {
        // no leading and trailing whitespaces
        DeleteByNameCommand expectedDeleteByNameCommand =
            new DeleteByNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedDeleteByNameCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedDeleteByNameCommand);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
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
###### \java\seedu\address\model\UniqueTodoListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniqueTodoList;

public class UniqueTodoListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTodoList uniqueTodoList = new UniqueTodoList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTodoList.asObservableList().remove(0);
    }
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
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void getTodoList_modifyList_throwsUnsupportedOperationException() {
        XmlSerializableAddressBook addressBook = new XmlSerializableAddressBook();
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTodoList().remove(0);
    }
```
###### \java\seedu\address\testutil\TypicalFilePath.java
``` java
package seedu.address.testutil;

/**
 * A utility class containing a list of {@code String} objects to be used in tests.
 */
public class TypicalFilePath {
    public static final String FILE_PATH_EXPORT_TEST = "data/exported/test.xml";
    public static final String FILE_PATH_DOCS = "docs/AcquaiNote.xml";
    public static final String FILE_PATH_LOCAL_D_DRIVE = "D:\\AcquaiNote.xml";
    public static final String FILE_PATH_CREATE_NEW_FOLDER = "C:\\shalkalaka\\AcquaiNote.xml";
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
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    // Persons with favourite star
    public static final ReadOnlyPerson JOHN = new PersonBuilder().withName("John")
        .withAddress("PGPR B4").withEmail("john@example.com")
        .withPhone("45678901").withTags("friends")
        .withTodoItem(getTodoItemOne())
        .withFavourite()
        .build();
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
        // is not favurited
        assertEquals(false, personWithNoTags.getFavourite());

        // is favourited
        Person personIsFavourited = new PersonBuilder().build();
        personIsFavourited.setFavourite(JOHN.getFavourite());
        assertEquals(true, personIsFavourited.getFavourite());

        personCard = new PersonCard(personIsFavourited, 3);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personIsFavourited, 3);

        // no todolists
        assertEquals(0, personWithNoTags.getTodoItems().size());

        // with todolists
        Person personWithTodo = new PersonBuilder().build();
        personWithTodo.setTodoItems(BILL.getTodoItems());
        assertEquals(1, personWithTodo.getTodoItems().size());

        personCard = new PersonCard(personWithTodo, 4);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTodo, 4);
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
        TodoCountHandle todoCountHandle = new TodoCountHandle(personCard.getRoot());
        FavouriteStarHandle favouriteStarHandle = new FavouriteStarHandle(personCard.getRoot());
```
###### \java\seedu\address\ui\PersonCardTest.java
``` java
        // verify todolist count is displayed correctly
        assertCardDisplaysTodoCount(expectedPerson, todoCountHandle);

        // verify favourite star is displayed correctly
        assertCardDisplaysFavouriteStar(expectedPerson, favouriteStarHandle);
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
    private static final String STUB_ZERO_TOTAL_PERSON = "0";
    private static final String STUB_ONE_TOTAL_PERSON = "1";
    private static final String TOTAL_PERSONS_SUFFIX = " person(s) total";
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, STUB_ZERO_TOTAL_PERSON);
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the todolists count of {@code expectedPerson}.
     */
    public static void assertCardDisplaysTodoCount(ReadOnlyPerson expectedPerson, TodoCountHandle actualCard) {
        if (expectedPerson.getTodoItems().size() > 0) {
            assertEquals(Integer.toString(expectedPerson.getTodoItems().size()), actualCard.getTodoCount());
        } else {
            assertEquals("", actualCard.getTodoCount());
        }
    }

    /**
     * Asserts that {@code actualCard} displays the favourite star of {@code expectedPerson}.
     */
    public static void assertCardDisplaysFavouriteStar(ReadOnlyPerson expectedPerson, FavouriteStarHandle actualCard) {
        if (expectedPerson.getFavourite()) {
            assertEquals("", actualCard.getFavouriteStar());
        }
    }

```
###### \java\seedu\address\ui\testutil\UiPartRule.java
``` java
    private static final String[] CSS_FILES = {"view/LightTheme.css", "view/Extensions.css"};
```
