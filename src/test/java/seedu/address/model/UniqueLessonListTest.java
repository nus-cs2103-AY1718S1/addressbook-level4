package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.module.UniqueLessonList;

public class UniqueLessonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueLessonList uniqueLessonList = new UniqueLessonList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLessonList.asObservableList().remove(0);
    }
}
