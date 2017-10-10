package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts all persons in the address book by indicated format.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book in ascending order by an indicated format. "
            + "Parameters: "
            + COMMAND_WORD
            + " [name/email/phone/address/tag]\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "This command has not been implemented";

    public static final String MESSAGE_SUCCESS = "Sorted successfully, Listing all persons below";


    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}