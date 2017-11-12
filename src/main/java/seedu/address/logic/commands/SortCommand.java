package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.ListObserver;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author khooroko
/**
 * Sorts the address book by the input argument (i.e. "name" or "debt").
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String DEFAULT_ORDERING = "name";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the addressbook by specified ordering in "
            + "intuitive order. If no ordering is specified, the addressbook is sorted by name.\n"
            + "Parameters: ORDERING (i.e. \"name\", \"debt\", \"deadline\" or \"cluster\")\n"
            + "Example: " + COMMAND_WORD + " debt";
    public static final String MESSAGE_SUCCESS = "List has been sorted by %1$s!";

    private final String order;

    public SortCommand(String order) {
        //validity of order to sort is checked in {@code SortCommandParser}
        if (order.equals("")) {
            order = DEFAULT_ORDERING;
        }
        this.order = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        model.deselectPerson();
        try {
            model.sortBy(order);
        } catch (IllegalArgumentException ive) {
            throw new CommandException(ive.getMessage());
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_SUCCESS, order));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand s = (SortCommand) other;
        return order.equals(s.order);
    }
}
