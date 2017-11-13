package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author thehelpfulbees

public class NumTimesSearchedTest {

    @Test
    public void isValidValue() {
        // invalid name
        assertFalse(NumTimesSearched.isValidValue((-2)));
        assertFalse(NumTimesSearched.isValidValue((-1)));
        assertFalse(NumTimesSearched.isValidValue((-1000)));

        // valid name
        assertTrue(NumTimesSearched.isValidValue((0)));
        assertTrue(NumTimesSearched.isValidValue((1)));
        assertTrue(NumTimesSearched.isValidValue((2)));
        assertTrue(NumTimesSearched.isValidValue((200)));
    }

}
