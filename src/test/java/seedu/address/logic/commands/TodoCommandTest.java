//@@author Hailinx
package seedu.address.logic.commands;

import static seedu.address.logic.commands.TodoCommand.MESSAGE_DUPLICATE_TODOITEM;
import static seedu.address.logic.commands.TodoCommand.MESSAGE_ERROR_PREFIX;
import static seedu.address.logic.commands.TodoCommand.MESSAGE_SUCCESS_ADD;
import static seedu.address.logic.commands.TodoCommand.MESSAGE_SUCCESS_DELETE_ALL;
import static seedu.address.logic.commands.TodoCommand.MESSAGE_SUCCESS_DELETE_ONE;
import static seedu.address.logic.commands.TodoCommand.MESSAGE_SUCCESS_LIST;
import static seedu.address.logic.commands.TodoCommand.MESSAGE_SUCCESS_LIST_ALL;
import static seedu.address.testutil.TodoItemUtil.getTodoItemOne;
import static seedu.address.testutil.TodoItemUtil.getTodoItemTwo;
import static seedu.address.testutil.TypicalPersons.getTodoItemAddressBook;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;

public class TodoCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTodoItemAddressBook(), new UserPrefs());

    @Test
    public void test_equals() {
        TodoCommand firstTodoCommand;
        TodoCommand secondTodoCommand;
        firstTodoCommand = new TodoCommand(TodoCommand.PREFIX_TODO_ADD,
                Index.fromOneBased(1), getTodoItemOne(), Index.fromOneBased(1));

        secondTodoCommand = new TodoCommand(TodoCommand.PREFIX_TODO_ADD,
                Index.fromOneBased(1), getTodoItemOne(), Index.fromOneBased(1));
        Assert.assertTrue(firstTodoCommand.equals(secondTodoCommand));

        secondTodoCommand =  new TodoCommand(TodoCommand.PREFIX_TODO_ADD,
                null, getTodoItemOne(), Index.fromOneBased(1));
        Assert.assertFalse(firstTodoCommand.equals(secondTodoCommand));

        secondTodoCommand =  new TodoCommand(TodoCommand.PREFIX_TODO_ADD,
                Index.fromOneBased(1), null, Index.fromOneBased(1));
        Assert.assertFalse(firstTodoCommand.equals(secondTodoCommand));

        secondTodoCommand =  new TodoCommand(TodoCommand.PREFIX_TODO_ADD,
                Index.fromOneBased(1), getTodoItemOne(), Index.fromOneBased(3));
        Assert.assertFalse(firstTodoCommand.equals(secondTodoCommand));
    }

    @Test
    public void execute_addTodo_success() throws Exception {
        // add a new todoItem to Alice (INDEX = 1)
        Index index = Index.fromOneBased(1);
        TodoCommand todoCommand = prepareCommand(TodoCommand.PREFIX_TODO_ADD,
                index, getTodoItemOne(), null);
        String expectedResult = String.format(MESSAGE_SUCCESS_ADD, getTodoItemOne());

        CommandResult commandResult = todoCommand.execute();
        Assert.assertTrue(commandResult.feedbackToUser.equals(expectedResult));

        // check whether the new TodoItem is added
        ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
        Assert.assertTrue(person.getTodoItems().contains(getTodoItemOne()));
    }

    @Test
    public void execute_deleteOne_success() throws Exception {
        // delete a todoItem from Bill (INDEX = 2)
        Index index = Index.fromOneBased(2);

        // check whether the TodoItem exists
        ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
        Assert.assertTrue(person.getTodoItems().contains(getTodoItemOne()));

        // prepare the command
        TodoCommand todoCommand = prepareCommand(TodoCommand.PREFIX_TODO_DELETE_ONE,
                index, null, Index.fromOneBased(1));
        String expectedResult = String.format(MESSAGE_SUCCESS_DELETE_ONE, getTodoItemOne());

        CommandResult commandResult = todoCommand.execute();
        Assert.assertTrue(commandResult.feedbackToUser.equals(expectedResult));

        // check whether the TodoItem is deleted
        person = model.getFilteredPersonList().get(index.getZeroBased());
        Assert.assertFalse(person.getTodoItems().contains(getTodoItemOne()));
    }

    @Test
    public void execute_deleteAll_success() throws Exception {
        // delete all todoItem from Darwin (INDEX = 4)
        Index index = Index.fromOneBased(4);

        // check whether the TodoItem exists
        ReadOnlyPerson person = model.getFilteredPersonList().get(index.getZeroBased());
        Assert.assertTrue(person.getTodoItems().contains(getTodoItemOne()));
        Assert.assertTrue(person.getTodoItems().contains(getTodoItemTwo()));

        // prepare the command
        TodoCommand todoCommand = prepareCommand(TodoCommand.PREFIX_TODO_DELETE_ALL,
                index, null, null);
        String expectedResult = String.format(MESSAGE_SUCCESS_DELETE_ALL, index.getOneBased());

        CommandResult commandResult = todoCommand.execute();
        Assert.assertTrue(commandResult.feedbackToUser.equals(expectedResult));

        // check whether the TodoItem is deleted
        person = model.getFilteredPersonList().get(index.getZeroBased());
        Assert.assertFalse(person.getTodoItems().contains(getTodoItemOne()));
        Assert.assertFalse(person.getTodoItems().contains(getTodoItemTwo()));
    }

    @Test
    public void execute_listTodoList_success() throws Exception {
        Index index = Index.fromOneBased(3);
        TodoCommand todoCommand = prepareCommand(TodoCommand.PREFIX_TODO_LIST,
                index, null, null);
        String expectedResult = String.format(MESSAGE_SUCCESS_LIST, index.getOneBased());

        CommandResult commandResult = todoCommand.execute();
        Assert.assertTrue(commandResult.feedbackToUser.equals(expectedResult));
    }

    @Test
    public void execute_listAllTodoList_success()  throws Exception {
        TodoCommand todoCommand = prepareCommand(TodoCommand.PREFIX_TODO_LIST_ALL, null, null, null);

        CommandResult commandResult = todoCommand.execute();
        Assert.assertTrue(commandResult.feedbackToUser.equals(MESSAGE_SUCCESS_LIST_ALL));
    }

    @Test
    public void execute_undefinedMode_throwsException() {
        try {
            prepareCommand("-p", null, null, null).execute();
            Assert.fail("Execute without throwing exception");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), MESSAGE_ERROR_PREFIX);
        }
    }

    @Test
    public void execute_duplicateTodoItem_throwsException() {
        try {
            Index index = Index.fromOneBased(4);
            prepareCommand(TodoCommand.PREFIX_TODO_ADD, index, getTodoItemOne(), null).execute();
            Assert.fail("Execute without throwing exception");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), MESSAGE_DUPLICATE_TODOITEM);
        }
    }

    @Test
    public void execute_invalidIndex_throwsException() {
        try {
            Index index = Index.fromOneBased(5);
            prepareCommand(TodoCommand.PREFIX_TODO_ADD, index, getTodoItemOne(), null).execute();
            Assert.fail("Execute without throwing exception");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Test
    public void execute_invalidDeleteIndex_throwsException() {
        try {
            Index index = Index.fromOneBased(2);
            Index itemIndex = Index.fromOneBased(2);
            prepareCommand(TodoCommand.PREFIX_TODO_DELETE_ONE, index, null, itemIndex).execute();
            Assert.fail("Execute without throwing exception");
        } catch (CommandException e) {
            Assert.assertEquals(e.getMessage(), TodoCommand.MESSAGE_INVALID_TODOITEM_INDEX);
        }
    }

    /**
     * Returns an {@code TodoCommand} with parameters
     */
    private TodoCommand prepareCommand(String optionPrefix,
            Index personIndex, TodoItem todoItem, Index itemIndex) {
        TodoCommand todoCommand = new TodoCommand(optionPrefix, personIndex, todoItem, itemIndex);
        todoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return todoCommand;
    }

}
