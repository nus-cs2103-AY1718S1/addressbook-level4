package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.CommandException;

public class RemoveTagCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullString_throwException() throws Exception {
        thrown.expect(NullPointerException.class);
        new RemoveTagCommand(null);
    }

}