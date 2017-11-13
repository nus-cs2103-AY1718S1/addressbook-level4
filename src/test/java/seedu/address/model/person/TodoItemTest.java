//@@author Hailinx
package seedu.address.model.person;

import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;
import static seedu.address.testutil.TodoItemUtil.EARLY_TIME_ONE;
import static seedu.address.testutil.TodoItemUtil.LATE_TIME_ONE;
import static seedu.address.testutil.TodoItemUtil.getTodoItemOne;
import static seedu.address.testutil.TodoItemUtil.getTodoItemThree;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TodoItemTest {

    @Test
    public void test_equals() throws Exception {
        TodoItem firstTodoItem = new TodoItem(EARLY_TIME_ONE, LATE_TIME_ONE, "task");
        TodoItem secondTodoItem = new TodoItem(EARLY_TIME_ONE, null, "task");

        Assert.assertTrue(firstTodoItem.equals(firstTodoItem));

        Assert.assertFalse(firstTodoItem.equals(secondTodoItem));

        TodoItem firstTodoItemCopy = new TodoItem(EARLY_TIME_ONE, LATE_TIME_ONE, "task");

        Assert.assertTrue(firstTodoItem.equals(firstTodoItemCopy));
    }

    @Test
    public void test_compareTo() throws Exception {
        TodoItem firstTodoItem = new TodoItem(EARLY_TIME_ONE, null, "task");
        TodoItem secondTodoItem = new TodoItem(LATE_TIME_ONE, null, "task");
        TodoItem thirdTodoItem = new TodoItem(EARLY_TIME_ONE, null, "task");

        Assert.assertTrue(firstTodoItem.compareTo(secondTodoItem) < 0);

        Assert.assertTrue(firstTodoItem.compareTo(thirdTodoItem) == 0);

        Assert.assertTrue(secondTodoItem.compareTo(thirdTodoItem) > 0);
    }

    @Test
    public void test_invalidInput_throwException() {
        try {
            new TodoItem(LATE_TIME_ONE, EARLY_TIME_ONE, "task");
            Assert.fail("CommandException is not thrown");
        } catch (IllegalValueException e) {
            Assert.assertEquals(e.getMessage(), TodoItem.MESSAGE_TODOITEM_CONSTRAINTS);
        }

        try {
            new TodoItem(LATE_TIME_ONE, EARLY_TIME_ONE, "");
            Assert.fail("CommandException is not thrown");
        } catch (IllegalValueException e) {
            Assert.assertEquals(e.getMessage(), TodoItem.MESSAGE_TODOITEM_CONSTRAINTS);
        }
    }

    @Test
    public void test_timeString() {
        TodoItem todoItemWithEndTime = getTodoItemOne();
        Assert.assertEquals(todoItemWithEndTime.getTimeString(),
                "From: " + convertTimeToString(EARLY_TIME_ONE) + "   To: " + convertTimeToString(LATE_TIME_ONE));

        TodoItem todoItemWithoutEndTime = getTodoItemThree();
        Assert.assertEquals(todoItemWithoutEndTime.getTimeString(),
                "From: " + convertTimeToString(EARLY_TIME_ONE));
    }
}
