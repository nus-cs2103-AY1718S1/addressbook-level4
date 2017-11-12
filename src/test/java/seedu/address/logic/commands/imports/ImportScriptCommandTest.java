package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

//@@author yunpengn
public class ImportScriptCommandTest {
    @Test
    public void createCommand_allPresent_checkCorrectness() throws Exception {
        ImportCommand command = new ImportScriptCommand("some script file");
        assertNotNull(command);
    }

    @Test
    public void execute_allPresent_checkCorrectness() throws Exception {
        ImportCommand command = new ImportScriptCommand("some script file");
        command.setData(new ImportScriptModelStub(), new CommandHistory(), new UndoRedoStack());
        assertEquals("The script has been imported.", command.execute().feedbackToUser);
    }

    private class ImportScriptModelStub extends ModelStub {
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
