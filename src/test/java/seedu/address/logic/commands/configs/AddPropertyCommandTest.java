package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONFIG_NEW_PROPERTY;

import java.util.regex.PatternSyntaxException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

public class AddPropertyCommandTest {
    private ConfigCommand successCommand;

    @Before
    public void setUp() {
        successCommand = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b",
                "birthday", "something", "[^\\s].*");
        successCommand.setData(new AddPropertyModelStub(), new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_addNewProperty_success() throws Exception {
        // It should not throw any exception here. The command should succeed.
        successCommand.execute();
    }

    @Test
    public void equal_twoSameCommands_returnTrue() {
        ConfigCommand command1 = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b", "birthday",
                "something", "[^\\s].*");
        ConfigCommand command2 = new AddPropertyCommand(VALID_CONFIG_NEW_PROPERTY, "b", "birthday",
                "something", "[^\\s].*");

        assertEquals(command1, command2);
    }

    private class AddPropertyModelStub extends ModelStub {
        @Override
        public void addProperty(String shortName, String fullName, String message, String regex)
                throws DuplicatePropertyException, PatternSyntaxException {
            PropertyManager.addNewProperty(shortName, fullName, message, regex);
        }
    }
}
