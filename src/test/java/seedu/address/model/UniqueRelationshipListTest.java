package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.relationship.UniqueRelationshipList;

public class UniqueRelationshipListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRelationshipList uniqueRelationshipList = new UniqueRelationshipList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRelationshipList.asObservableList().remove(0);
    }
}
