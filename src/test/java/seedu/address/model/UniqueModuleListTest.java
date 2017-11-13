package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.module.UniqueModuleList;

public class UniqueModuleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueModuleList uniqueModList = new UniqueModuleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueModList.asObservableList().remove(0);
    }
}
