package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Method to Clear ConnectUsLog.txt Command
 */
//@@author danielweide
public class ClearLogCommand extends Command {
    public static final String COMMAND_WORD = "clearlog";
    public static final String COMMAND_ALIAS = "cl";
    public static final String MESSAGE_SUCCESS = "ConnectUs.txt log has been cleared!";

    /**
     * Output Results After "Clearing" ConnectUsLog.txt file
     */
    public CommandResult execute() throws CommandException, IOException {
        File file = new File("ConnectUsLog.txt");
        file.delete();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
