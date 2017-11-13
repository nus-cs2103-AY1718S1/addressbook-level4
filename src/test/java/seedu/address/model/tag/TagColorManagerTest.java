package seedu.address.model.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.exceptions.TagNotFoundException;

//@@author yunpengn
public class TagColorManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getTagColor_createColorWhenCreateTag_noException() throws Exception {
        Tag tag = new Tag(VALID_TAG_HUSBAND);

        if (!TagColorManager.contains(tag)) {
            thrown.expect(TagNotFoundException.class);
        }
        TagColorManager.getColor(tag);
    }

    @Test
    public void setTagColor_success_checkCorrectness() throws  Exception {
        Tag tag = new Tag(VALID_TAG_HUSBAND);
        TagColorManager.setColor(tag, VALID_TAG_COLOR);
        assertEquals(VALID_TAG_COLOR, TagColorManager.getColor(tag));
    }

    @Test
    public void setTagColor_randomColor_checkCorrectness() throws Exception {
        Tag tag = new Tag(VALID_TAG_FRIEND);
        TagColorManager.setColor(tag);
        assertTrue(TagColorManager.getColor(tag).matches("#[A-Fa-f0-9]{6}"));
    }
}
