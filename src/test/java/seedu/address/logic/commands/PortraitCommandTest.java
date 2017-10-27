package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

public class PortraitCommandTest {

    @Test
    public void testEquals() {
        PortraitCommand command = new PortraitCommand(INDEX_FIRST_PERSON, VALID_PORTRAIT_PATH_FIRST);

        assertTrue(command.equals(command));

        PortraitCommand commandCopy = new PortraitCommand(INDEX_FIRST_PERSON, VALID_PORTRAIT_PATH_FIRST);
        assertTrue(command.equals(commandCopy));

        assertFalse(command.equals("Not a command"));

        assertFalse(command.equals(null));

        PortraitCommand differentCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, VALID_PORTRAIT_PATH_SECOND);
        PortraitCommand differentCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, VALID_PORTRAIT_PATH_FIRST);
        assertFalse(command.equals(differentCommandOne));
        assertFalse(command.equals(differentCommandTwo));
    }
}
