package seedu.address.model;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

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


        //Check contains tags
        assertFalse(uniqueTagList.contains(new Tag("friends", "")));
        assertFalse(uniqueTagList.contains(new Tag("aaaaaaaa", "")));

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertTrue(tag.getTagColor().equals("grey"));
        }

        uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        uniqueTagList.setTags(TypicalPersons.BENSON.getTags());
        uniqueTagList.setTags(TypicalPersons.CARL.getTags());

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertTrue(tag.getTagColor().equals("grey"));
        }

        Tag tag = new Tag("friends", "blue");

        //Test random color
        tag.setRandomColor();
        assertFalse(tag.getTagColor().equals("blue"));

        //Test set color
        tag.setColor("blue");
        assertTrue(tag.getTagColor().equals("blue"));

        //Test off color
        tag.setOffColor();
        assertTrue(tag.getTagColor().equals("grey"));


        //New tagList
        uniqueTagList = new UniqueTagList();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags = new HashSet<>(model.getAddressBook().getTagList());

        //Set all to random colors
        uniqueTagList.setTags(tags, true, "", "");

        for (Tag tag1 : tags) {
            assertFalse(tag1.getTagColor().equals("grey"));
        }

        uniqueTagList = new UniqueTagList();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags1 = new HashSet<>(model.getAddressBook().getTagList());

        //Set all to grey
        uniqueTagList.setTags(tags1, false, "", "");

        for (Tag tag1 : tags) {
            assertTrue(tag1.getTagColor().equals("grey"));
        }

        uniqueTagList = new UniqueTagList();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags2 = new HashSet<>(model.getAddressBook().getTagList());

        //Set friends to blue
        uniqueTagList.setTags(tags, true, "friends", "blue");

        for (Tag tagtest : tags2) {
            if (tagtest.tagName.equals("friends")) {
                assertTrue(tagtest.getTagColor().equals("blue"));
            }
        }

    }


}
