package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.meeting.UniqueMeetingList;

public class UniqueMeetingListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueMeetingList.asObservableList().remove(0);
    }
}
