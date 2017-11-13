//@@author qihao27
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniqueTodoList;

public class UniqueTodoListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTodoList uniqueTodoList = new UniqueTodoList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTodoList.asObservableList().remove(0);
    }
}
