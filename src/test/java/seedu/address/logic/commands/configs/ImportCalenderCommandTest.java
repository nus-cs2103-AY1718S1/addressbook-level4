package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_URL;

import org.junit.Test;

public class ImportCalenderCommandTest {
    @Test
    public void equal_twoSameCommands_returnTrue() {
        ConfigCommand command1 = new ImportCalenderCommand(VALID_CONFIG_URL);
        ConfigCommand command2 = new ImportCalenderCommand(VALID_CONFIG_URL);

        assertEquals(command1, command2);
    }
}
