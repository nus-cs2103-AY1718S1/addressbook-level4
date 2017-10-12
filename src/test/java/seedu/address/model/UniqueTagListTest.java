package seedu.address.model;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Iterator;
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
    public void testThrowDuplicateTagError() throws Exception {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        thrown.expect(UniqueTagList.DuplicateTagException.class);

        Iterator myIterator = TypicalPersons.ALICE.getTags().iterator();
        uniqueTagList.add((Tag) myIterator.next());

    }

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
            assertTrue("grey".equals(tag.getTagColor()));
        }

        uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        uniqueTagList.setTags(TypicalPersons.BENSON.getTags());
        uniqueTagList.setTags(TypicalPersons.CARL.getTags());

        for (Tag tag : uniqueTagList.asObservableList()) {
            assertTrue("grey".equals(tag.getTagColor()));
        }

        Tag tag = new Tag("friends", "blue");

        //Test random color
        tag.setRandomColor();
        assertFalse("blue".equals(tag.getTagColor()));

        //Test set color
        tag.setColor("blue");
        assertTrue("blue".equals(tag.getTagColor()));

        //Test off color
        tag.setOffColor();
        assertTrue("grey".equals(tag.getTagColor()));


        //New tagList
        uniqueTagList = new UniqueTagList();
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags = new HashSet<>(model.getAddressBook().getTagList());

        //Set all to random colors
        uniqueTagList.setTags(tags, true, "", "");

        for (Tag tag1 : tags) {
            assertFalse("grey".equals(tag1.getTagColor()));
        }

        uniqueTagList = new UniqueTagList();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags1 = new HashSet<>(model.getAddressBook().getTagList());

        //Set all to grey
        uniqueTagList.setTags(tags1, false, "", "");

        for (Tag tag1 : tags) {
            assertTrue("grey".equals(tag1.getTagColor()));
        }

        uniqueTagList = new UniqueTagList();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Set<Tag> tags2 = new HashSet<>(model.getAddressBook().getTagList());

        //Set friends to blue
        uniqueTagList.setTags(tags, true, "friends", "blue");

        for (Tag tagtest : tags2) {
            if ("friends".equals(tagtest.tagName)) {
                assertTrue("blue".equals(tagtest.getTagColor()));
            }
        }

    }

    @Test
    public void testEquals() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());

        UniqueTagList uniqueTagListTwo = new UniqueTagList();
        uniqueTagListTwo.setTags(TypicalPersons.ALICE.getTags());

        UniqueTagList uniqueTagListThree = new UniqueTagList();
        uniqueTagListThree.setTags(TypicalPersons.BOB.getTags());

        // same object -> returns true
        assertTrue(uniqueTagList.equals(uniqueTagList));

        // copy of object -> returns true
        assertTrue(uniqueTagList.equals(uniqueTagListTwo));

        // different types -> returns false
        assertFalse(uniqueTagList.equals(1));

        // null -> returns false
        assertFalse(uniqueTagList.equals(null));

        // different sets -> returns false
        assertFalse(uniqueTagList.equals(uniqueTagListThree));
    }

}
