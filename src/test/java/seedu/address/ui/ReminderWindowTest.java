//@@author 17navasaw
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ReminderWindowBottomHandle;
import guitests.guihandles.ScheduleCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;


public class ReminderWindowTest extends GuiUnitTest {
    private static final List<Schedule> scheduleList = Arrays.asList(new ScheduleBuilder().build(),
            new ScheduleBuilder().withPersonName("Prince").build());
    private static final ObservableList<Schedule> TEST_SCHEDULES =
            FXCollections.observableList(scheduleList);

    private ReminderWindow reminderWindow;
    private ReminderWindowBottomHandle reminderWindowBottomHandle;

    @Before
    public void setUp() throws TimeoutException {
        guiRobot.interact(() -> reminderWindow = new ReminderWindow(TEST_SCHEDULES));
        FxToolkit.setupStage((stage) -> stage.setScene(reminderWindow.getRoot().getScene()));
        FxToolkit.showStage();

        reminderWindowBottomHandle = new ReminderWindowBottomHandle(getChildNode(reminderWindow.getRoot(),
                reminderWindowBottomHandle.SCHEDULE_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TEST_SCHEDULES.size(); i++) {
            reminderWindowBottomHandle.navigateToCard(TEST_SCHEDULES.get(i));
            Schedule expectedSchedule = TEST_SCHEDULES.get(i);
            ScheduleCardHandle actualCard = reminderWindowBottomHandle.getScheduleCardHandle(i);

            assertCardDisplaysSchedule(expectedSchedule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getNumber());
        }
    }
}
