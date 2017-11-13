package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.tag.Tag;

//@@author pohjie
public class TagCardTest extends GuiUnitTest {

    private static final ObservableList<Tag> TYPICAL_TAGS =
            FXCollections.observableList(getTypicalAddressBook().getTagList());

    @Test
    public void display() {
        TagCard tagCard = new TagCard(TYPICAL_TAGS.get(0), 0);
        assertEquals(tagCard.tag.getText(), TYPICAL_TAGS.get(0).tagName);
        assertEquals(tagCard.getIdxText(), "0. ");
    }

    @Test
    public void equals() {
        TagCard tagCard = new TagCard(TYPICAL_TAGS.get(0), 0);

        // same tag, same index -> returns true
        TagCard copy = new TagCard(TYPICAL_TAGS.get(0), 0);
        assertTrue(tagCard.equals(copy));

        // same object -> returns true
        assertTrue(tagCard.equals(tagCard));

        // null -> returns false
        assertFalse(tagCard.equals(null));

        // different types -> returns false
        assertFalse(tagCard.equals(0));

        // different tag, same index -> returns false
        TagCard differentCard = new TagCard(TYPICAL_TAGS.get(1), 0);
        assertFalse(tagCard.equals(differentCard));

        // same tag, different index -> returns false
        differentCard = new TagCard(TYPICAL_TAGS.get(0), 1);
        assertFalse(tagCard.equals(differentCard));
    }
}
