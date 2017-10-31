//@@author 17navasaw
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import org.junit.Test;

import guitests.guihandles.ScheduleCardHandle;

import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;



public class ScheduleCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Schedule testSchedule = new ScheduleBuilder().withPersonName("Bob").build();
        ScheduleCard scheduleCard = new ScheduleCard(testSchedule, 2);
        uiPartRule.setUiPart(scheduleCard);
        assertCardDisplay(scheduleCard, testSchedule, 2);

        Schedule testSchedule2 = new ScheduleBuilder().build();
        ScheduleCard scheduleCard2 = new ScheduleCard(testSchedule, 1);
        // changes made to Schedule reflects on card
        guiRobot.interact(() -> {
            testSchedule2.setPersonInvolvedName(testSchedule2.getPersonInvolvedName());
            testSchedule2.setScheduleDate(testSchedule2.getScheduleDate());
            testSchedule2.setActivity(testSchedule2.getActivity());
        });
        assertCardDisplay(scheduleCard2, testSchedule, 1);
    }

    @Test
    public void equals() {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleCard scheduleCard = new ScheduleCard(schedule, 0);

        // same schedule, same index -> returns true
        ScheduleCard copy = new ScheduleCard(schedule, 0);
        assertTrue(scheduleCard.equals(copy));

        // same object -> returns true
        assertTrue(scheduleCard.equals(scheduleCard));

        // null -> returns false
        assertFalse(scheduleCard.equals(null));

        // different types -> returns false
        assertFalse(scheduleCard.equals(0));

        // different schedule, same index -> returns false
        Schedule differentSchedule = new ScheduleBuilder().withPersonName("differentName").build();
        assertFalse(scheduleCard.equals(new ScheduleCard((differentSchedule), 0)));

        // same person, different index -> returns false
        assertFalse(scheduleCard.equals(new ScheduleCard(schedule, 1)));
    }

    /**
     * Asserts that {@code scheduleCard} displays the details of {@code expectedSchedule} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ScheduleCard scheduleCard, Schedule expectedSchedule, int expectedId) {
        guiRobot.pauseForHuman();

        ScheduleCardHandle scheduleCardHandle = new ScheduleCardHandle(scheduleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", scheduleCardHandle.getNumber());

        // verify schedule details are displayed correctly
        assertCardDisplaysSchedule(expectedSchedule, scheduleCardHandle);
    }
}
