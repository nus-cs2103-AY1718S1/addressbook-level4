package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import java.util.Arrays;
import java.util.List;

import guitests.guihandles.AgendaPanelHandle;
import guitests.guihandles.ScheduleCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

public class AgendaPanelTest extends GuiUnitTest {
    private static final List<Schedule> scheduleList = Arrays.asList(new ScheduleBuilder().build(),
            new ScheduleBuilder().withPersonName("Prince").build());
    private static final ObservableList<Schedule> TEST_SCHEDULES =
            FXCollections.observableList(scheduleList);

    private AgendaPanelHandle agendaPanelHandle;

    @Before
    public void setUp() {
        AgendaPanel agendaPanel = new AgendaPanel(TEST_SCHEDULES);
        uiPartRule.setUiPart(agendaPanel);

        agendaPanelHandle = new AgendaPanelHandle(getChildNode(agendaPanel.getRoot(),
                AgendaPanelHandle.SCHEDULE_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TEST_SCHEDULES.size(); i++) {
            agendaPanelHandle.navigateToCard(TEST_SCHEDULES.get(i));
            Schedule expectedSchedule = TEST_SCHEDULES.get(i);
            ScheduleCardHandle actualCard = agendaPanelHandle.getScheduleCardHandle(i);

            assertCardDisplaysSchedule(expectedSchedule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getNumber());
        }
    }
}
