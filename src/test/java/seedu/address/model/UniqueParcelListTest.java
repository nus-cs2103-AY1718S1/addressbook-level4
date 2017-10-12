package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.parcel.UniqueParcelList;

public class UniqueParcelListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueParcelList uniqueParcelList = new UniqueParcelList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueParcelList.asObservableList().remove(0);
    }
}
