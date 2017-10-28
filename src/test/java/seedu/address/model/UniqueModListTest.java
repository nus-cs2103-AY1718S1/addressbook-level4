package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.mod.UniqueModList;

public class UniqueModListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueModList uniqueModList = new UniqueModList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueModList.asObservableList().remove(0);
    }
}
