package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ScoreTest {

    @Test
    public void isValidScore() {
        //invalid Scores
        assertFalse("False 1" , Score.isValidScore("B")); //Only letters are not valid.
        assertFalse("False 2" , Score.isValidScore("@")); //Only symbols are not valid.
        assertFalse("False 3" , Score.isValidScore("508")); //More than one digit is not allowed.

        //Valid Scores
        assertTrue("True 1" , Score.isValidScore("5")); //A number between 0 and 9 is valid
        assertTrue("True 2" , Score.isValidScore("@B5")); //This should be valid, as there's 1 digit.

    }
}
