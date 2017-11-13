# KongjiaQi
###### /java/seedu/address/logic/parser/MarkTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.address.logic.commands.MarkTaskCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MarkTaskCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the MarkTaskCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class MarkTaskCommandParserTest {

    private MarkTaskCommandParser parser = new MarkTaskCommandParser();

    @Test
    public void parse_validArgs_returnsMarkTaskCommand() {
        assertParseSuccess(parser, "1", new MarkTaskCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkTaskCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseDescription_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseString(null);
    }

    @Test
    public void parseDescription_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseString(Optional.empty()).isPresent());
    }

    @Test
    public void parseStart_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseString(null);
    }

    @Test
    public void parseStart_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseString(Optional.empty()).isPresent());
    }

    @Test
    public void parseEnd_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseString(null);
    }

    @Test
    public void parseEnd_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseString(Optional.empty()).isPresent());
    }


    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
```
###### /java/seedu/address/logic/parser/EditTaskCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DEMO;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEMO;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;

import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE);

    private EditTaskCommandParser parser = new EditTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_DEMO, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditTaskCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_DEMO, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_DEMO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditTaskCommand expectedCommand = new EditTaskCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/address/logic/commands/EditTaskDescriptorTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOTPOT;

import org.junit.Test;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_DEMO);
        assertTrue(DESC_DEMO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_DEMO.equals(DESC_DEMO));

        // null -> returns false
        assertFalse(DESC_DEMO.equals(null));

        // different types -> returns false
        assertFalse(DESC_DEMO.equals(5));

        // different values -> returns false
        assertFalse(DESC_DEMO.equals(DESC_HOTPOT));

        // different name -> returns false
        EditTaskDescriptor editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withName(VALID_NAME_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different description -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withDescription(VALID_DESCRIPTION_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different start time -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withStart(VALID_START_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different end time -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withEnd(VALID_END_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));

        // different tags -> returns false
        editedDemo = new EditTaskDescriptorBuilder(DESC_DEMO).withTags(VALID_TAG_HOTPOT).build();
        assertFalse(DESC_DEMO.equals(editedDemo));
    }
}
```
###### /java/seedu/address/logic/commands/EditTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertTaskCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());

    /*@Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        //model.updateTaskPriority(model.getFilteredTaskList().get(0), 3);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }*/

    /*@Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        ReadOnlyTask lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withName(VALID_NAME_HOTPOT).withDescription(VALID_DESCRIPTION_HOTPOT)
                .withTags(VALID_TAG_HOTPOT).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT)
                .withDescription(VALID_DESCRIPTION_HOTPOT).withTags(VALID_TAG_HOTPOT).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        ReadOnlyTask editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withName(VALID_NAME_HOTPOT).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }
    /*
    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = new Task(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_SECOND_TASK, descriptor);

        assertTaskCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }
    */
    /*@Test
    public void execute_duplicateTaskFilteredList_failure() {
        showFirstTaskOnly(model);

        // edit task in filtered list into a duplicate in task book
        ReadOnlyTask taskInList = model.getTaskBook().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        assertTaskCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }*/

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(outOfBoundIndex, descriptor);

        assertTaskCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of task book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showFirstTaskOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskBook().getTaskList().size());

        EditTaskCommand editTaskCommand = prepareTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT).build());

        assertTaskCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_DEMO);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_DEMO);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_DEMO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_HOTPOT)));
    }

    /**
     * Returns an {@code EditTaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareTaskCommand(Index index, EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}

```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    //public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Hotpot&"; // '&' not allowed in names
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + " "; // ' ' blank space not allowed
    public static final String INVALID_START_DESC = " " + PREFIX_START_DATE_TIME + "19981209"; // not the correct style
    public static final String INVALID_END_DESC = " " + PREFIX_END_DATE_TIME; // empty string not allowed
    //public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags


    static {
        DESC_DEMO = new EditTaskDescriptorBuilder().withName(VALID_NAME_DEMO)
                .withDescription(VALID_DESCRIPTION_DEMO).withStart(VALID_START_DEMO).withEnd(VALID_END_DEMO)
                .withTags(VALID_TAG_DEMO).build();
        DESC_HOTPOT = new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT)
                .withDescription(VALID_DESCRIPTION_HOTPOT).withStart(VALID_START_HOTPOT).withEnd(VALID_END_HOTPOT)
                .withTags(VALID_TAG_HOTPOT).build();
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
            assertEquals(expectedMessage, result.feedbackToUser);
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
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    /**
     * Executes the given {@code taskCommand}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the TaskCommandException message matches {@code expectedMessage} <br>
     * - the task book and the filtered task list in the {@code actualModel} remain unchanged
     */
    public static void assertTaskCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskBook expectedTaskBook = new TaskBook(actualModel.getTaskBook());
        List<ReadOnlyTask> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTaskBook, actualModel.getTaskBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
        }
    }

    /**
     * Updates {@code model}'s filtered person list to show only the first person in the {@code model}'s 3W.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new ContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered task list to show only the first task in the {@code model}'s 3W.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getTaskBook().getTaskList().get(0);
        List<Name> name = Arrays.asList(task.getName());
        model.updateFilteredTaskList(new TaskNameContainsKeywordsPredicate(name));

        assert model.getFilteredTaskList().size() == 1;
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
```
###### /java/seedu/address/logic/commands/ListTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskCommand.
 */
public class ListTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private ListTaskCommand listTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalTaskbook(), new UserPrefs());

        listTaskCommand = new ListTaskCommand();
        listTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeListIsNotFilteredShowsSameList() {
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsFilteredShowsEverything() {
        showFirstTaskOnly(model);
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### /java/seedu/address/logic/commands/MarkTaskCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code markTaskCommand}.
 */
public class MarkTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() throws Exception {

        ReadOnlyTask taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        //System.out.println(taskToMark);
        MarkTaskCommand markTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        //markTaskCommand.executeUndoableCommand();
        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskBook(), new UserPrefs());
        expectedModel.markTask(taskToMark);

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        MarkTaskCommand markTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(markTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkTaskCommand markFirstCommand = new MarkTaskCommand(INDEX_FIRST_TASK);
        MarkTaskCommand markSecondCommand = new MarkTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkTaskCommand markFirstCommandCopy = new MarkTaskCommand(INDEX_FIRST_TASK);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    /**
     * Returns a {@code MarkTaskCommand} with the parameter {@code index}.
     */
    private MarkTaskCommand prepareCommand(Index index) {
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(index);
        markTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return markTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no task.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }
}
```
###### /java/seedu/address/testutil/TaskUtil.java
``` java
package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add task command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(ReadOnlyTask task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(ReadOnlyTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().name + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription().value + " ");
        sb.append(PREFIX_START_DATE_TIME + task.getStartDateTime().getState() + " ");
        sb.append(PREFIX_END_DATE_TIME + task.getEndDateTime().getState() + " ");
        task.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/EditTaskDescriptorBuilder.java
``` java
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(ReadOnlyTask task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setDescription(task.getDescription());
        descriptor.setStart(task.getStartDateTime());
        descriptor.setEnd(task.getEndDateTime());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        try {
            Optional<String> parserName = ParserUtil.parseString(Optional.of(name));
            if (parserName.isPresent()) {
                descriptor.setName(new Name(name));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        try {
            Optional<String> parserDescription = ParserUtil.parseString(Optional.of(description));
            if (parserDescription.isPresent()) {
                descriptor.setDescription(new Description(description));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Start} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStart(String start) {
        try {
            Optional<String> parserStartTime = ParserUtil.parseString(Optional.of(start));
            if (parserStartTime.isPresent()) {
                descriptor.setStart(new DateTime(start));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException(ive.getMessage());
        }
        return this;
    }

    /**
     * Sets the {@code End} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEnd(String end) {
        try {
            Optional<String> parserEndTime = ParserUtil.parseString(Optional.of(end));
            if (parserEndTime.isPresent()) {
                descriptor.setEnd(new DateTime(end));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException(ive.getMessage());
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
```
