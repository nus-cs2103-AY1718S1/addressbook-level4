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
            + COMMAND_WORD + " " + ListByTagCommand.COMMAND_SELECTOR + " [TAG/s] \n"
            + COMMAND_WORD + " " + ListAscendingNameCommand.COMMAND_ALIAS + "\n"
            + COMMAND_WORD + " " + ListAscendingNameCommand.COMMAND_WORD + "\n"
            + COMMAND_WORD + " " + ListDescendingNameCommand.COMMAND_ALIAS + "\n"
            + COMMAND_WORD + " " + ListDescendingNameCommand.COMMAND_WORD + "\n"
            + COMMAND_WORD + " " + ListReverseCommand.COMMAND_ALIAS + "\n"
            + COMMAND_WORD + " " + ListReverseCommand.COMMAND_WORD + "\n";

    /**
     * Returns a failure message to indicate invalid command available in list package.
     *
     * @return Failure Message.
     */
    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_FAILURE);
    }

}
