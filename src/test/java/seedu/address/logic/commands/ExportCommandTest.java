package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXPORTED;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_C_CREATE_NEW_FOLDER;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_DOCS;
import static seedu.address.testutil.TypicalFilePath.FILE_PATH_LOCAL_C_DRIVE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_exportSuccess_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CommandResult result = new ExportCommand(FILE_PATH_DOCS).execute();
        assertEquals(MESSAGE_FILE_EXPORTED + FILE_PATH_DOCS, result.feedbackToUser);
    }

    @Test
    public void execute_exportLocalDriveSuccess_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CommandResult result = new ExportCommand(FILE_PATH_LOCAL_C_DRIVE).execute();
        assertEquals(MESSAGE_FILE_EXPORTED + FILE_PATH_LOCAL_C_DRIVE, result.feedbackToUser);
    }

    @Test
    public void execute_exportCreateNewFolderSuccess_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        CommandResult result = new ExportCommand(FILE_PATH_C_CREATE_NEW_FOLDER).execute();
        assertEquals(MESSAGE_FILE_EXPORTED + FILE_PATH_C_CREATE_NEW_FOLDER, result.feedbackToUser);
    }

}
