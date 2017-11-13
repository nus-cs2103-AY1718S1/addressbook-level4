package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalReminders.COFFEE_REMINDER;
import static seedu.address.testutil.TypicalReminders.MEETING_REMINDER;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;
import seedu.address.storage.XmlSerializableReminders;

//@@author justinpoh
public class UniqueReminderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueReminderList reminderList = new UniqueReminderList();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueReminderList uniqueReminderList = new UniqueReminderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueReminderList.asObservableList().remove(0);
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), reminderList.asObservableList());
    }

    @Test
    public void constructor_withDuplicateReminders_throwsAssertionError() {
        // repeat coffee reminder twice
        List<ReadOnlyReminder> newReminders = Arrays.asList(COFFEE_REMINDER, COFFEE_REMINDER);
        XmlSerializableReminders serializableReminders = new XmlSerializableReminders(newReminders);

        thrown.expect(AssertionError.class);
        new UniqueReminderList(serializableReminders);
    }

    @Test
    public void contains_withReminderInside_returnTrue() throws Exception {
        reminderList.add(COFFEE_REMINDER);
        assertTrue(reminderList.contains(COFFEE_REMINDER));
    }

    @Test
    public void contains_withoutReminderInside_returnFalse() {
        assertFalse(reminderList.contains(COFFEE_REMINDER));
    }

    @Test
    public void add_null_throwsNullPointerException() throws DuplicateReminderException {
        thrown.expect(NullPointerException.class);
        reminderList.add(null);
    }

    @Test
    public void size_initialSizeZero() throws Exception {
        assertEquals(0, reminderList.size());

        // after adding one Reminder, size should increase by 1
        reminderList.add(COFFEE_REMINDER);
        assertEquals(1, reminderList.size());
    }

    @Test
    public void remove_null_throwsNullPointerException() throws ReminderNotFoundException {
        thrown.expect(NullPointerException.class);
        reminderList.remove(null);
    }

    @Test
    public void setReminders_sameReminders() {
        reminderList.setReminders(getUniqueTypicalReminders());
        assertEquals(reminderList, getUniqueTypicalReminders());
    }

    @Test
    public void equals() throws Exception {

        // null -> returns false
        assertFalse(reminderList.equals(null));

        // different types -> returns false
        assertFalse(reminderList.equals(0));

        // different reminder -> return false
        reminderList.setReminders(getUniqueTypicalReminders());
        UniqueReminderList differentReminderList = new UniqueReminderList();
        differentReminderList.setReminders(getUniqueTypicalReminders());
        differentReminderList.add(MEETING_REMINDER);
        assertFalse(reminderList.equals(differentReminderList));

        // same object -> returns true
        assertTrue(reminderList.equals(reminderList));

        // same reminders -> returns true
        assertTrue(reminderList.equals(getUniqueTypicalReminders()));
    }
}
