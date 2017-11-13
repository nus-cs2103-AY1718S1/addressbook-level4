package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author charlesgoh
/**
 * Tests for lock and unlock functionality. Covers implementation across userprefs and model classes
 */
public class UnlockLockCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager();
    }

    @Test
    public void checkLockFunctionality() throws Exception {
        // Check initial state is unlocked
        assertFalse(model.getLockState());

        // Check that lock works
        model.lockAddressBookFromModel();
        assertTrue(model.getLockState());

        // Check double lock
        model.lockAddressBookFromModel();
        assertTrue(model.getLockState());

        // Check that unlock works
        model.unlockAddressBookFromModel();
        assertFalse(model.getLockState());

        // Check double unlock
        model.unlockAddressBookFromModel();
        assertFalse(model.getLockState());
    }
}
