package seedu.address.logic.commands.configs;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEW_PROPERTY;
import static seedu.address.logic.commands.configs.AddPropertyCommand.MESSAGE_DUPLICATE_PROPERTY;
import static seedu.address.logic.commands.configs.AddPropertyCommand.MESSAGE_INVALID_REGEX;

import java.util.regex.PatternSyntaxException;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.DuplicatePropertyException;

//@@author yunpengn
public class AddPropertyCommandTest {
    private ConfigCommand successCommand;

    private final String shortName = "b";
    private final String shortNameAlter = "b1";
    private final String fullName = "birthday";
    private final String message = "something";
    private final String validRegex = "[^\\s].*";
    private final String invalidRegex = "*asf";

    @Before
    public void setUp() {
        successCommand = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortName, fullName, message, validRegex);
        successCommand.setData(new AddPropertyModelStub(), new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_addNewProperty_success() throws Exception {
        int propertyCountBefore = PropertyManager.getAllShortNames().size();
        successCommand.execute();
        int propertyCountAfter = PropertyManager.getAllShortNames().size();

        assertEquals(1, propertyCountAfter - propertyCountBefore);
    }

    @Test
    public void execute_addSamePropertyAgain_expectDuplicateException() {
        try {
            // Execute the command (add the same property) again, will get DuplicatePropertyException.
            successCommand.execute();
        } catch (CommandException e) {
            assertEquals(MESSAGE_DUPLICATE_PROPERTY, e.getMessage());
        }
    }

    @Test
    public void execute_invalidRegex_expectRegexException() {
        ConfigCommand invalidRegexCommand = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortNameAlter, fullName, message, invalidRegex);
        invalidRegexCommand.setData(new AddPropertyModelStub(), new CommandHistory(), new UndoRedoStack());

        try {
            // Execute the command (add the same property) again, will get DuplicatePropertyException.
            invalidRegexCommand.execute();
        } catch (CommandException e) {
            assertEquals(MESSAGE_INVALID_REGEX, e.getMessage());
        }
    }

    @Test
    public void equal_twoSameCommands_returnTrue() {
        ConfigCommand command1 = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortName, fullName, message, validRegex);
        ConfigCommand command2 = new AddPropertyCommand(VALID_NEW_PROPERTY,
                shortName, fullName, message, validRegex);

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
