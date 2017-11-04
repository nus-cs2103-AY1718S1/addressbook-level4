package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ImportScriptCommandTest {
    @Test
    public void createCommand_allPresent_checkCorrectness() throws Exception {
        ImportCommand command = new ImportScriptCommand("some script file");
        assertNotNull(command);
    }
}
