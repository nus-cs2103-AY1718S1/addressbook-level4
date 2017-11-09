package seedu.address.logic.commands;

//@@author Jeremy
/**
 * Prints failure message if invalid arguments are passed after a list command
 *
 * Command is created for list failure detection instead of command parser because list methods
 * are not taking in any arguments and thus there is nothing for the command to parse.
 * Another option to throw this message is to save it in the Messages.java class but since
 * commands are returned in the AddressBookParser.class, it would be more convenient to catch
 * all errors pertaining to the list features and throw the message via a command class instead.
 */
public class ListFailureCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_FAILURE = "Invalid input detected. Valid list variations: \n"
            + COMMAND_WORD + " \n"
            + COMMAND_WORD + " tag [TAG/s] \n"
            + COMMAND_WORD + " asc \n"
            + COMMAND_WORD + " ascending \n"
            + COMMAND_WORD + " dsc \n"
            + COMMAND_WORD + " descending \n"
            + COMMAND_WORD + " rev \n"
            + COMMAND_WORD + " reverse \n";


    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_FAILURE);
    }

}
