package seedu.address.model;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.TypicalPersons;

public class UniqueTagListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    @Test
    public void tagsTests() throws IllegalValueException {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        uniqueTagList.setTags(TypicalPersons.BENSON.getTags());
        uniqueTagList.setTags(TypicalPersons.CARL.getTags());

        assertTrue(uniqueTagList.contains(new Tag("Test1", "")));
        assertFalse(uniqueTagList.contains(new Tag("aaaaaaaa", "")));

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertTrue(tag.getTagColor().equals("grey"));
        }

        uniqueTagList.setTagsColorOn();
        uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        uniqueTagList.setTags(TypicalPersons.BENSON.getTags());
        uniqueTagList.setTags(TypicalPersons.CARL.getTags());

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertFalse(tag.getTagColor().equals("grey"));
        }
    }
}
