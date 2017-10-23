package seedu.address.logic.commands;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all person";


    @Override
    public CommandResult execute() {
        model.sortFilteredPersonList(ReadOnlyPerson.NAMESORT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
