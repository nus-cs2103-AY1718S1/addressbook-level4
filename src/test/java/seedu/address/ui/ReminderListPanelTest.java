package seedu.address.ui;
//@@author RonakLakhotia
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalPersons.getTypicalReminders;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysReminder;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEqualsReminders;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ReminderCardHandle;
import guitests.guihandles.ReminderListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.reminder.ReadOnlyReminder;

public class ReminderListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyReminder> TYPICAL_REMINDERS =
            FXCollections.observableList(getTypicalReminders().subList(1, 2));

    private static final JumpToListRequestEvent JUMP_TO_FIRST_EVENT = new
            JumpToListRequestEvent(INDEX_FIRST_REMINDER);

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
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_FIRST_EVENT);
        guiRobot.pauseForHuman();

        ReminderCardHandle expectedCard = reminderListPanelHandle.getReminderCardHandle(INDEX_FIRST_REMINDER
                .getZeroBased());
        ReminderCardHandle selectedCard = reminderListPanelHandle.getHandleToSelectedCard();
        assertCardEqualsReminders(expectedCard, selectedCard);
    }
}
