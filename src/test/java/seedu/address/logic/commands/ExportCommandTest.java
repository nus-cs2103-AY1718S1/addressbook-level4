//@@author Hoang
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.ExportCommand.MESSAGE_ACCESS_DENIED;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.GuiUnitTest;

public class ExportCommandTest extends GuiUnitTest {
    private Model model;
    private String os;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        os = System.getProperty("os.name").toLowerCase();

    }

    @Test
    public void accessDeniedFolder() throws CommandException {
        //when trying to create parent folder
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        if (os.indexOf("win") > 0) {
            assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
        }
    }

    @Test
    public void accessDeniedFile() throws CommandException {
        //when trying to create file
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        if (os.indexOf("win") > 0) {
            assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
        }
    }

    @Test
    public void success() throws CommandException {
        ExportCommand command = new ExportCommand(".txt", "C:/a");
        command.setData(model, null, null, null);
        assertTrue(command.execute().feedbackToUser.equals(new CommandResult(MESSAGE_SUCCESS).feedbackToUser));
    }
}
//@@author Hoang
