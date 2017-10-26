package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.lecturer.UniqueLecturerList;


public class UniqueLecturerListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueLecturerList uniqueLecturerList = new UniqueLecturerList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLecturerList.asObservableList().remove(0);
    }
}
