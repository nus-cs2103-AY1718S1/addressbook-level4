package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static seedu.address.testutil.TypicalReminders.getTypicalReminders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReminder;
import static seedu.address.ui.testutil.GuiTestAssert.assertReminderCardEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ReminderCardHandle;
import guitests.guihandles.ReminderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToReminderRequestEvent;
import seedu.address.model.reminder.ReadOnlyReminder;

public class ReminderListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyReminder> TYPICAL_REMINDERS =
            FXCollections.observableList(getTypicalReminders());

    private static final JumpToReminderRequestEvent JUMP_TO_SECOND_EVENT = new JumpToReminderRequestEvent(
            INDEX_SECOND_REMINDER);

    private ReminderListPanelHandle reminderListPanelHandle;

    @Before
    public void setUp() {
        ReminderListPanel reminderListPanel = new ReminderListPanel(TYPICAL_REMINDERS);
        uiPartRule.setUiPart(reminderListPanel);

        reminderListPanelHandle = new ReminderListPanelHandle(getChildNode(reminderListPanel.getRoot(),
                ReminderListPanelHandle.REMINDER_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_REMINDERS.size(); i++) {
            reminderListPanelHandle.navigateToCard(TYPICAL_REMINDERS.get(i));
            ReadOnlyReminder expectedReminder = TYPICAL_REMINDERS.get(i);
            ReminderCardHandle actualCard = reminderListPanelHandle.getReminderCardHandle(i);

            assertCardDisplaysReminder(expectedReminder, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToReminderRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ReminderCardHandle expectedCard = reminderListPanelHandle.getReminderCardHandle(
                INDEX_SECOND_REMINDER.getZeroBased());
        ReminderCardHandle selectedCard = reminderListPanelHandle.getHandleToSelectedCard();
        assertReminderCardEquals(expectedCard, selectedCard);
    }
}
