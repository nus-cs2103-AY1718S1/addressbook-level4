package seedu.address.model.group;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }
}
