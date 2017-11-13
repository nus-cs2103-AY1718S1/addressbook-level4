//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.testutil.TodoItemUtil.getTodoItemOne;
import static seedu.address.testutil.TodoItemUtil.getTodoItemThree;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TodoOptionAddTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Index index = Index.fromOneBased(1);

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionAdd("", index).parse();
    }

    @Test
    public void test_parseSuccess() throws Exception {
        TodoCommand firstTodoCommand = new TodoOptionAdd(
                  PREFIX_START_TIME + "01-01-2017 12:00" + " "
                + PREFIX_END_TIME + "01-12-2017 12:00" + " "
                + PREFIX_TASK + "task: item one",
                index).parse();
        TodoCommand expectedTodoCommand =
                new TodoCommand(TodoCommand.PREFIX_TODO_ADD, index, getTodoItemOne(), null);
        Assert.assertTrue(firstTodoCommand.equals(expectedTodoCommand));

        TodoCommand secondTodoCommand = new TodoOptionAdd(
                  PREFIX_START_TIME + "01-01-2017 12:00" + " "
                + PREFIX_TASK + "task: item three",
                index).parse();
        expectedTodoCommand =
                new TodoCommand(TodoCommand.PREFIX_TODO_ADD, index, getTodoItemThree(), null);
        Assert.assertTrue(secondTodoCommand.equals(expectedTodoCommand));
    }

    @Test
    public void test_parseEmptyArgs_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionAdd("", index).parse();
    }

    @Test
    public void test_parseInvalidTime_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionAdd(PREFIX_START_TIME + "0-01-2017 12:00" + " " + PREFIX_TASK + "task", index).parse();
    }

    @Test
    public void test_parseEmptyTask_throwsException() throws Exception {
        thrown.expect(ParseException.class);
        new TodoOptionAdd(PREFIX_START_TIME + "01-01-2017 12:00" + " " + PREFIX_TASK + "", index).parse();
    }

}
