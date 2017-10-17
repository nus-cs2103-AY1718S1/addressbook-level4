package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Lists all contacts having a certain tag in the address book.
 */
public class RetrieveCommand extends Command {

    public static final String COMMAND_WORD = "retrieve";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves all persons belonging to the specified tag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_SUCCESS = "Command under development";
    public static final String MESSAGE_EMPTY_ARGS = "Please provide a tag's name! \n%1$s";

    private final String tagName;

    public RetrieveCommand(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RetrieveCommand // instanceof handles nulls
                && this.tagName.equals(((RetrieveCommand) other).tagName)); // state check
    }

}
