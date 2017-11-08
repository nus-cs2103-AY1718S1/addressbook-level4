//@@author 17navasaw
package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BlockTest {

    @Test
    public void isValidBlock() {
        // invalid blocks
        assertFalse(Block.isValidBlock(""));        // empty string
        assertFalse(Block.isValidBlock("-23"));     // unaccepted symbol
        assertFalse(Block.isValidBlock(" "));       // whitespace

        // valid blocks
        assertTrue(Block.isValidBlock("23A"));      // numbers and alphabets with no whitespaces
        assertTrue(Block.isValidBlock("23"));       // numbers only
        assertTrue(Block.isValidBlock("A"));        // alphabets only
        assertTrue(Block.isValidBlock("23 Alpha")); // numbers and alphabets with whitespace
    }

}
