package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

public class TaskCardTest extends GuiUnitTest {

    @Test
    public void editFontSizeTests() {
        int fontSizeMultiplier = TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER;
        Task testTask = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(testTask, 1, fontSizeMultiplier);
        assertEquals(TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER, taskCard.getFontSizeMultiplier());
        assertNotEquals(taskCard.getFontSizeMultiplier(), fontSizeMultiplier + 1);

        // Verify font size increase
        fontSizeMultiplier = TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER + 1;
        taskCard.setFontSizeMultiplier(fontSizeMultiplier);
        assertEquals(taskCard.getFontSizeMultiplier(), fontSizeMultiplier);
        assertNotEquals(taskCard.getFontSizeMultiplier(), TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER);

        // Verify font size decrease
        fontSizeMultiplier = TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER - 1;
        taskCard.setFontSizeMultiplier(fontSizeMultiplier);
        assertEquals(taskCard.getFontSizeMultiplier(), fontSizeMultiplier);
        assertNotEquals(taskCard.getFontSizeMultiplier(), TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER);
    }


    @Test
    public void display() {
        // no tags
        Task taskWithNoTags = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(taskWithNoTags, 1, TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER);
        uiPartRule.setUiPart(taskCard);
        assertCardDisplay(taskCard, taskWithNoTags, 1);
    }

    @Test
    public void equals() {
        Task task = new TaskBuilder().build();
        TaskCard taskCard = new TaskCard(task, 0, TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER);

        // same task, same index -> returns true
        TaskCard copy = new TaskCard(task, 0, TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER);
        assertTrue(taskCard.equals(copy));

        // same object -> returns true
        assertTrue(taskCard.equals(taskCard));

        // null -> returns false
        assertFalse(taskCard.equals(null));

        // different types -> returns false
        assertFalse(taskCard.equals(0));

        // different task, same index -> returns false
        Task differentTask = new TaskBuilder().withTaskName("differentName").build();
        assertFalse(taskCard.equals(new TaskCard(differentTask, 0, TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER)));

        // same task, different index -> returns false
        assertFalse(taskCard.equals(new TaskCard(task, 1, TaskCard.DEFAULT_FONT_SIZE_MULTIPLIER)));
    }

    /**
     * Asserts that {@code taskCard} displays the details of {@code expectedTask} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(TaskCard taskCard, ReadOnlyTask expectedTask, int expectedId) {
        guiRobot.pauseForHuman();

        TaskCardHandle taskCardHandle = new TaskCardHandle(taskCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", taskCardHandle.getId());

        // verify task details are displayed correctly
        assertCardDisplaysTask(expectedTask, taskCardHandle);
    }
}
