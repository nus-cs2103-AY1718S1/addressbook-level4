package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.place.UniquePlaceList;

public class UniquePlaceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePlaceList uniquePlaceList = new UniquePlaceList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePlaceList.asObservableList().remove(0);
    }
}
