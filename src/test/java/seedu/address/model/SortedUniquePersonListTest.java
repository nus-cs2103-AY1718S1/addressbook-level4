package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.SortedUniquePersonList;

public class SortedUniquePersonListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        SortedUniquePersonList sortedUniquePersonList = new SortedUniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        sortedUniquePersonList.asObservableList().remove(0);
    }
}
