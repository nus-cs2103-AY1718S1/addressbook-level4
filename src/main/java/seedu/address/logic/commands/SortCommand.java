//@@author A0144294A
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts currently displayed contact list" +
                        " based on the attribute that is input by the user.\n" +
                        "Parameter: keyword (must be within 'name, phone, email, address')\n" +
                        "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SUCCESS = "All persons in the list are sorted.";

    private final int keyword;

    public SortCommand(int argument) {
        this.keyword = argument;
    }

    @Override
    public CommandResult execute () throws CommandException {
        model.sortBy(keyword);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.keyword == ((SortCommand) other).keyword); // state check
    }
}
//@@author
