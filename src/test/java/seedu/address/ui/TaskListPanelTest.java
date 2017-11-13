package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTask;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestTaskEvent;
import seedu.address.model.task.ReadOnlyTask;

//author charlesgoh
public class TaskListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyTask> TYPICAL_TASKS =
            FXCollections.observableList(getTypicalTasks());

    private static final JumpToListRequestTaskEvent JUMP_TO_SECOND_EVENT = new
            JumpToListRequestTaskEvent(INDEX_SECOND_PERSON);

    private TaskListPanelHandle taskListPanelHandle;

    @Before
    public void setUp() {
        TaskListPanel taskListPanel = new TaskListPanel(TYPICAL_TASKS);
        uiPartRule.setUiPart(taskListPanel);

        taskListPanelHandle = new TaskListPanelHandle(getChildNode(taskListPanel.getRoot(),
                TaskListPanelHandle.TASK_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TASKS.size(); i++) {
            taskListPanelHandle.navigateToCard(TYPICAL_TASKS.get(i));
            ReadOnlyTask expectedTask = TYPICAL_TASKS.get(i);
            TaskCardHandle actualCard = taskListPanelHandle.getTaskCardHandle(i);

            assertCardDisplaysTask(expectedTask, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestTaskEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        TaskCardHandle expectedCard = taskListPanelHandle.getTaskCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        TaskCardHandle selectedCard = taskListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}

