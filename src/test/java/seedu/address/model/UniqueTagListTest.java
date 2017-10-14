package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

<<<<<<< HEAD
import seedu.address.model.lecturer.UniqueTagList;
=======
import seedu.address.model.lecturer.UniqueTagList;
>>>>>>> a7bbacace6bcc3a50c6b9b1dab99446eae012891

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }
}
