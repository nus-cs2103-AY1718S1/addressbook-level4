package seedu.address.model.reminder;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class UniqueReminderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        ArrayList<Reminder> reminderList = new ArrayList<>();
        UniqueReminderList uniqueReminderList = new UniqueReminderList(reminderList);
        thrown.expect(UnsupportedOperationException.class);
        uniqueReminderList.asObservableList().remove(0);
    }
}
