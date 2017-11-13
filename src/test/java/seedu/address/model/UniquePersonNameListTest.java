//@@author 17navasaw
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniquePersonNameList;

public class UniquePersonNameListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonNameList uniquePersonNameList = new UniquePersonNameList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonNameList.asObservableList().remove(0);
    }
}
