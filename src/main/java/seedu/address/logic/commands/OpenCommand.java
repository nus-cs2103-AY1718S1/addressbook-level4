package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Opens an existing Rolodex in a different directory.
 */
public class OpenCommand extends Command {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_SUCCESS = "Successfully opened %1$s";

    public final String filePath;
    
    public OpenCommand(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }
}
