package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "List has been sorted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list by FIELD\n"
            + "Parameters: FIELD (name/phone/email) ORDER (asc/dsc)";

    private final Comparator<ReadOnlyPerson> comparator;

    public SortCommand(Comparator<ReadOnlyPerson> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute() {
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.comparator.equals(((SortCommand) other).comparator)); // state check
    }
}
