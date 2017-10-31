package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.module.UniqueRemarkList;

//@@author junming403
public class UniqueRemarkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRemarkList uniqueLessonList = new UniqueRemarkList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLessonList.asObservableList().remove(0);
    }
}
