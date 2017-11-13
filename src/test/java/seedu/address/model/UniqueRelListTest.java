package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.relationship.UniqueRelList;

public class UniqueRelListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRelList uniqueRelList = new UniqueRelList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRelList.asObservableList().remove(0);
    }
}
