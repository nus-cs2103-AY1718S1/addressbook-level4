package seedu.address.logic.commands;

/**
 * Prints failure message if invalid arguments are passed after
 * a list command
 */
public class ListCommandFailure extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_FAILURE = "Invalid input detected. Valid list variations: \n"
            + COMMAND_WORD + " \n"
            + COMMAND_WORD + " tag [TAG/s] \n"
            + COMMAND_WORD + " asc \n"
            + COMMAND_WORD + " ascending \n";

    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_FAILURE);
    }

}
