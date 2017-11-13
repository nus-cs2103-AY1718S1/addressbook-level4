//@@author majunting
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * sort the current person list by an attribute specified by the user
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts currently displayed contact list"
                        + " based on the attribute that is input by the user.\n"
                        + "Parameter: keyword (must be within 'name, phone, email, address')\n"
                        + "Example: " + COMMAND_WORD + " name";
    public static final String MESSAGE_SUCCESS = "All persons in the list are sorted by %1$s.\n";

    private final int keyword;
    private String attribute;

    public SortCommand(int argument) {
        this.keyword = argument;
    }

    public int getKeyword () {
        return keyword;
    }

    @Override
    public CommandResult execute () throws CommandException {
        model.sortBy(keyword);
        switch (keyword) {
        case 1: attribute = "phone";
                break;
        case 2: attribute = "email";
                break;
        case 3: attribute = "address";
                break;
        default: attribute = "name";
                break;
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, attribute));
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.keyword == ((SortCommand) other).keyword); // state check
    }
}
//@@author
