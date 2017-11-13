package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.UniqueTagList;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    //@@author limcel
    @Test
    public void testForDuplicateTags() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        for (int i = 0; i < uniqueTagList.asObservableList().size(); i++) {
            thrown.expect(UniqueTagList.DuplicateTagException.class);
            uniqueTagList.asObservableList().remove(uniqueTagList.asObservableList().remove(i));
        }
    }
    //@@author
}
