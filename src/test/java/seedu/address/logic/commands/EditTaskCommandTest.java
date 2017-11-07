package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author JYL123
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullPersonThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditTaskCommand(null, new EditTaskCommand.EditTaskDescriptor());
    }
}

