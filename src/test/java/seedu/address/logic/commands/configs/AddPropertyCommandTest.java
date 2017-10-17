package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_NEW_PROPERTY;

import org.junit.Test;

public class AddPropertyCommandTest {
    @Test
    public void createCommand_allFields_success() throws Exception {
        ConfigCommand command = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b", "birthday",
                "something", "[^\\s].*");
        command.execute();
    }

    @Test
    public void execute_addNewProperty_success() throws Exception {
        ConfigCommand command = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b", "birthday",
                "something", "[^\\s].*");

        // It should not throw any exception here. The command should succeed.
        command.execute();
    }

    @Test
    public void equal_twoSameCommands_returnTrue() {
        ConfigCommand command1 = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b", "birthday",
                "something", "[^\\s].*");
        ConfigCommand command2 = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b", "birthday",
                "something", "[^\\s].*");

        assertEquals(command1, command2);
    }
}
