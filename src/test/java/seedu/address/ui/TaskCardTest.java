//@@author srishag
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalTasks.CODE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 1);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, task, 1);

        // changes made to Task reflects on card
        guiRobot.interact(() -> {
            task.setHeader(CODE.getHeader());
            task.setDesc(CODE.getDesc());
            task.setDeadline(CODE.getDeadline());
        });
        assertCardDisplay(taskCard, task, 1);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different tasks, same index -> returns false
        Task differentTask = new TaskBuilder().withHeader("differentHeader").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedCard} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, ReadOnlyTask expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }
}
