package seedu.address.model.alias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class KeywordTest {

    @Test
    public void isValidKeyword() {
        // invalid keyword
        assertFalse(Keyword.isValidKeyword("")); // empty string
        assertFalse(Keyword.isValidKeyword(" ")); // spaces only
        assertFalse(Keyword.isValidKeyword("t")); // one letter keyword
        assertFalse(Keyword.isValidKeyword("ph tmr")); //two words

        // valid keyword
        assertTrue(Keyword.isValidKeyword("ph")); // 2 letter word
        assertTrue(Keyword.isValidKeyword("ttsh")); // one word
        assertTrue(Keyword.isValidKeyword("88")); // length 2 string of numbers
    }
}
