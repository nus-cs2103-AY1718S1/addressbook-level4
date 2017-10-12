package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TagColorTest {

    @Test
    public void isValidTagColor() {
        // invalid tag color names
        assertFalse(TagColor.isValidTagColorName("")); // empty string
        assertFalse(TagColor.isValidTagColorName("a")); // random words

        // valid tag color names
        assertTrue(TagColor.isValidTagColorName("red"));
        assertTrue(TagColor.isValidTagColorName("blue"));
        assertTrue(TagColor.isValidTagColorName("yellow"));
    }

}
