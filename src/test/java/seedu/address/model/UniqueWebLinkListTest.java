package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.weblink.UniqueWebLinkList;

public class UniqueWebLinkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueWebLinkList uniqueTagList = new UniqueWebLinkList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
