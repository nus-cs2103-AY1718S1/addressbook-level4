package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author tby1994
public class CommandModeTest {

    @Test
    public void isValid() {
        //Invalid command modes
        assertFalse(CommandMode.isValidMode("")); //empty string
        assertFalse(CommandMode.isValidMode(" ")); //empty string with 1 white space
        assertFalse(CommandMode.isValidMode("abc123")); //wrong command mode
        assertFalse(CommandMode.isValidMode("ab 123")); //more than 1 word
        assertFalse(CommandMode.isValidMode("abtm")); //mixture of both command modes

        //Valid command modes
        assertTrue(CommandMode.isValidMode("ab")); //addressbook mode short form
        assertTrue(CommandMode.isValidMode("addressbook")); //addressbook mode
        assertTrue(CommandMode.isValidMode("AddReSsBoOk")); //mixed case addressbook mode
        assertTrue(CommandMode.isValidMode("tm")); //taskmanager mode
        assertTrue(CommandMode.isValidMode("taskmanager")); //taskmanager mode
        assertTrue(CommandMode.isValidMode("TaSkMaNaGeR")); //mixed case taskmanager mode
    }
}
