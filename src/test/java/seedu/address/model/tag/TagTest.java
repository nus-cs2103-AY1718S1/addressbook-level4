package seedu.address.model.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class TagTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createTag_validName_success() throws Exception {
        Tag myTag = new Tag(VALID_TAG_FRIEND);
        assertEquals(VALID_TAG_FRIEND, myTag.tagName);
    }

    @Test
    public void createTag_invalidName_throwException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Tag myTag = new Tag(INVALID_TAG);
        assertNull(myTag);
    }

    @Test
    public void createTagWithColor_validName_checkColor() throws Exception {
        Tag myTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR);
        assertEquals(VALID_TAG_COLOR, TagColorManager.getColor(myTag));
    }

    @Test
    public void equal_twoSameTag_checkCorrectness() throws Exception {
        Tag tag1 = new Tag(VALID_TAG_FRIEND);
        Tag tag2 = new Tag(VALID_TAG_FRIEND);

        assertEquals(tag1, tag2);
    }
}
