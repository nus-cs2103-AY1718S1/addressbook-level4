//@@author duyson98

package seedu.address.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalReminders.BIRTHDAY;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReminder;

import org.junit.Test;

import guitests.guihandles.ReminderCardHandle;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class ReminderCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Reminder reminderWithNoTags = new ReminderBuilder().withTags(new String[0]).build();
        ReminderCard reminderCard = new ReminderCard(reminderWithNoTags, 1);
        uiPartRule.setUiPart(reminderCard);
        assertCardDisplay(reminderCard, reminderWithNoTags, 1);

        // with tags
        Reminder reminderWithTags = new ReminderBuilder().build();
        reminderCard = new ReminderCard(reminderWithTags, 2);
        uiPartRule.setUiPart(reminderCard);
        assertCardDisplay(reminderCard, reminderWithTags, 2);

        // changes made to Reminder reflects on card
        guiRobot.interact(() -> {
            reminderWithTags.setTask(BIRTHDAY.getTask());
            reminderWithTags.setPriority(BIRTHDAY.getPriority());
            reminderWithTags.setDate(BIRTHDAY.getDate());
            reminderWithTags.setMessage(BIRTHDAY.getMessage());
            reminderWithTags.setTags(BIRTHDAY.getTags());
        });
        assertCardDisplay(reminderCard, reminderWithTags, 2);
    }

    @Test
    public void equals() {
        Reminder reminder = new ReminderBuilder().build();
        ReminderCard reminderCard = new ReminderCard(reminder, 0);

        // same reminder, same index -> returns true
        ReminderCard copy = new ReminderCard(reminder, 0);
        assertTrue(reminderCard.equals(copy));

        // same object -> returns true
        assertTrue(reminderCard.equals(reminderCard));

        // null -> returns false
        assertFalse(reminderCard.equals(null));

        // different types -> returns false
        assertFalse(reminderCard.equals(0));

        // different reminder, same index -> returns false
        Reminder differentReminder = new ReminderBuilder().withTask("differentName").build();
        assertFalse(reminderCard.equals(new ReminderCard(differentReminder, 0)));

        // same reminder, different index -> returns false
        assertFalse(reminderCard.equals(new ReminderCard(reminder, 1)));
    }

    /**
     * Asserts that {@code reminderCard} displays the details of {@code expectedReminder} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ReminderCard reminderCard, ReadOnlyReminder expectedReminder, int expectedId) {
        guiRobot.pauseForHuman();

        ReminderCardHandle reminderCardHandle = new ReminderCardHandle(reminderCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", reminderCardHandle.getId());

        // verify reminder details are displayed correctly
        assertCardDisplaysReminder(expectedReminder, reminderCardHandle);
    }
}
