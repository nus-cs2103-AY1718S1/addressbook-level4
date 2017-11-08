package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */

public class EmailCommandTest {

    @Test
    public void equals() {


        EmailCommand emailCommandFirst = new EmailCommand("friends", "party");
        EmailCommand emailCommandCommandSecond = new EmailCommand("owesMoney", "debt");

        // same object -> returns true
        assertTrue(emailCommandFirst.equals(emailCommandFirst));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand("friends", "meeting");
        assertTrue(emailFirstCommandCopy.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailCommandFirst.equals(1));

        // null -> returns false
        assertFalse(emailCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(emailCommandFirst.equals(emailCommandCommandSecond));
    }

}
