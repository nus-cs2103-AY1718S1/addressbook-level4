package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImportCommandTest {
    @Test
    public void configTypes_checkCount() {
        assertEquals(ImportCommand.ImportType.values().length, ImportCommand.TO_ENUM_IMPORT_TYPE.size());
    }
}
