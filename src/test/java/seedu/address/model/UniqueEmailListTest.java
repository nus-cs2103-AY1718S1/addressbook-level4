package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.email.UniqueEmailList;


public class UniqueEmailListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEmailList uniqueEmailList = new UniqueEmailList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEmailList.asObservableList().remove(0);
    }
}
