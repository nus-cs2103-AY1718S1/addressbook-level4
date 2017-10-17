package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import org.junit.Test;

public class ChangeTagColorCommandTest {
    @Test
    public void equal_twoSameCommands_returnTrue() {
        ConfigCommand command1 = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);
        ConfigCommand command2 = new ChangeTagColorCommand(VALID_TAG_FRIEND + VALID_TAG_COLOR,
                VALID_TAG_FRIEND, VALID_TAG_COLOR);

        assertEquals(command1, command2);
    }
}
