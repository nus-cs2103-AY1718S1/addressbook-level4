//@@author cqhchan
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.account.UniqueAccountList;

public class UniqueAccountListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueAccountList uniqueAccountList = new UniqueAccountList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueAccountList.asObservableList().remove(0);
    }
}
