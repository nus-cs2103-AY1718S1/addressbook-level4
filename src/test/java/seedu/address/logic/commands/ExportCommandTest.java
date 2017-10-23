package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_ACCESS_DENIED;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

public class ExportCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test(expected = CommandException.class)
    public void accessDeniedFolder() throws CommandException{
        //when trying to create parent folder
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));

    }

    @Test(expected = CommandException.class)
    public void accessDeniedFile() throws CommandException{
        //when trying to create file
        ExportCommand command = new ExportCommand(".txt", "C:/Windows/a");
        command.setData(model, null, null, null);
        assertEquals(command.execute(), new CommandException(MESSAGE_ACCESS_DENIED));
    }

    @Test
    public void success() throws CommandException {
        ExportCommand command = new ExportCommand(".txt", "C:/a");
        command.setData(model, null, null, null);
        assertTrue(command.execute().feedbackToUser.equals(new CommandResult(MESSAGE_SUCCESS).feedbackToUser));
    }
}
