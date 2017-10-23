package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all person";

    private final Comparator<ReadOnlyPerson> comparator;

    public SortCommand(Comparator<ReadOnlyPerson> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute() {
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
