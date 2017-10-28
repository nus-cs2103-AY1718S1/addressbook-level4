package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BlockTest {

    @Test
    public void isValidBlock() {
        // invalid blocks
        assertFalse(Block.isValidBlock(""));
        assertFalse(Block.isValidBlock("-23"));
        assertFalse(Block.isValidBlock(" "));

        // valid blocks
        assertTrue(Block.isValidBlock("23A"));
        assertTrue(Block.isValidBlock("23"));
        assertTrue(Block.isValidBlock("A"));
    }

}
