package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.stub.ModelStub;

public class ImportScriptCommandTest {
    @Test
    public void createCommand_allPresent_checkCorrectness() throws Exception {
        ImportCommand command = new ImportScriptCommand("some script file");
        assertNotNull(command);
    }
}
