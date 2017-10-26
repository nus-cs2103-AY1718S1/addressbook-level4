package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
* Sorts all persons in the address book by indicated format.
*/
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_WORD_ALIAS = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book in alphabetical "
            + "order of names \n Example: \" + COMMAND_WORD";

    public static final String MESSAGE_SUCCESS = "Sorted successfully, Listing all persons below";

    @Override
    public CommandResult execute() throws CommandException {
        model.sortFilteredPersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        return false;
    }
}
