package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PortraitPath;

public class PortraitCommandTest {

    @Test
    public void testEquals() {
        PortraitPath expectedPath = null;
        PortraitPath differentPath = null;
        try {
            expectedPath = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
            differentPath = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        } catch (IllegalValueException e) {
            fail("Test data cannot throw exception");
        }
        requireNonNull(expectedPath);

        PortraitCommand command = new PortraitCommand(INDEX_FIRST_PERSON, expectedPath);

        assertTrue(command.equals(command));

        PortraitCommand commandCopy = new PortraitCommand(INDEX_FIRST_PERSON, expectedPath);
        assertTrue(command.equals(commandCopy));

        assertFalse(command.equals(new ClearCommand()));

        assertFalse(command.equals(null));

        PortraitCommand differentCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, differentPath);
        PortraitCommand differentCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, expectedPath);
        assertFalse(command.equals(differentCommandOne));
        assertFalse(command.equals(differentCommandTwo));
    }
}
