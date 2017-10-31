package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.schedule.UniqueScheduleList;


public class UniqueScheduleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueScheduleList.asObservableList().remove(0);
    }
}
