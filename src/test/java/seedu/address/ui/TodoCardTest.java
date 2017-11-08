//@@author Hailinx
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TodoItemUtil.getTodoItemOne;
import static seedu.address.testutil.TodoItemUtil.getTodoItemThree;
import static seedu.address.ui.testutil.GuiTestAssert.assertTodoDisplaysTodoItem;

import org.junit.Test;

import guitests.guihandles.TodoCardHandle;
import seedu.address.model.person.TodoItem;

public class TodoCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // todoItem with end time
        TodoItem todoItem = getTodoItemOne();
        TodoCard todoCard = new TodoCard(todoItem, 1);
        uiPartRule.setUiPart(todoCard);
        assertCardDisplay(todoCard, todoItem, 1);

        // todoItem without end time
        todoItem = getTodoItemThree();
        todoCard = new TodoCard(todoItem, 2);
        uiPartRule.setUiPart(todoCard);
        assertCardDisplay(todoCard, todoItem, 2);
    }

    @Test
    public void equals() {
        TodoItem todoItem = getTodoItemOne();
        TodoCard todoCard = new TodoCard(todoItem, 0);

        // same item, same index -> returns true
        TodoCard copy = new TodoCard(todoItem, 0);
        assertTrue(todoCard.equals(copy));

        // same object -> returns true
        assertTrue(todoCard.equals(todoCard));

        // different types -> returns false
        assertFalse(todoCard.equals(0));
    }

    /**
     * Asserts that {@code todoCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TodoCard todoCard, TodoItem expectedTodoItem, int expectedId) {
        guiRobot.pauseForHuman();

        TodoCardHandle todoCardHandle = new TodoCardHandle(todoCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", todoCardHandle.getId());

        // verify person details are displayed correctly
        assertTodoDisplaysTodoItem(expectedTodoItem, todoCardHandle);
    }
}
