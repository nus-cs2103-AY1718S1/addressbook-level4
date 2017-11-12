package seedu.address.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalEvents.EVENT1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.reminder.exceptions.DuplicateReminderException;

//@@author yunpengn
public class UniqueReminderListTest {
    private static final Reminder reminder = new Reminder(EVENT1, "Some message");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void create_viaList_checkCorrectness() throws Exception {
        List<ReadOnlyReminder> list = new ArrayList<>();
        list.add(reminder);
        UniqueReminderList uniqueList = new UniqueReminderList(list);
        assertEquals(list.size(), uniqueList.toList().size());
    }

    @Test
    public void add_checkSize_checkCorrectness() throws Exception {
        UniqueReminderList list = new UniqueReminderList();
        list.add(reminder);
        assertEquals(1, list.toList().size());
    }

    @Test
    public void add_hasDuplicate_checkCorrectness() throws Exception {
        thrown.expect(DuplicateReminderException.class);

        UniqueReminderList list = new UniqueReminderList();
        list.add(reminder);
        list.add(reminder);
    }

    @Test
    public void toList_checkSize_checkCorrectness() {
        UniqueReminderList list = new UniqueReminderList();
        assertEquals(0, list.toList().size());
    }

    @Test
    public void equals_checkCorrectness() {
        UniqueReminderList list1 = new UniqueReminderList();
        UniqueReminderList list2 = new UniqueReminderList();

        assertEquals(list1, list1);
        assertEquals(list1, list2);
        assertNotEquals(list1, null);
        assertNotEquals(list1, 1);
    }
}
