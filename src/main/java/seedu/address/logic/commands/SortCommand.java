package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author k-l-a
/**
 * Sorts the current list according to the given arguments.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String ARGUMENT_ASCENDING_WORD = "asc";
    public static final String ARGUMENT_DESCENDING_WORD = "des";
    public static final String ARGUMENT_DEFAULT_ORDER = "default";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the current list according to given prefix in the given order. "
            + "No prefix results in default order.\n"
            + "Parameters: [PREFIX] [ORDER]\n"
            + "Example : sort n/ asc";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted current list by %1$s in %2$s order.";

    public final String prefix;
    public final String order;

    public SortCommand(String prefix, String order) {
        this.prefix = prefix;
        this.order = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Comparator<ReadOnlyPerson> sortOrder = createAscComparator(prefix, order);
        model.updateSortedFilteredPersonList(null); //reset any order first
        model.updateSortedFilteredPersonList(sortOrder);
        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, prefix, order));
    }

    /**
     *  Creates a ReadOnlyPerson Comparator from the given prefix and ordering.
     */
    public Comparator<ReadOnlyPerson> createAscComparator(String prefix, String order) {
        if (prefix.equals(ARGUMENT_DEFAULT_ORDER)) {
            if (ARGUMENT_DESCENDING_WORD.equals(order)) {
                return (person1, person2) -> (1); //reverse order
            } else if (ARGUMENT_ASCENDING_WORD.equals(order)) {
                return (person1, person2) -> (-1); //default order
            }
        }

        Comparator<ReadOnlyPerson> sortOrderComp = null;
        if (prefix.equals(PREFIX_NAME.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getName()
                    .fullName.compareToIgnoreCase(person2.getName().fullName));
        } else if (prefix.equals(PREFIX_PHONE.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getPhone()
                    .value.compareToIgnoreCase(person2.getPhone().value));
        } else if (prefix.equals(PREFIX_EMAIL.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getEmail()
                    .value.compareToIgnoreCase(person2.getEmail().value));
        } else if (prefix.equals(PREFIX_ADDRESS.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getAddress()
                    .value.compareToIgnoreCase(person2.getAddress().value));
        } else if (prefix.equals(PREFIX_BIRTHDAY.getPrefix())) {
            sortOrderComp = (person1, person2) -> (person1.getBirthday()
                    .value.compareToIgnoreCase(person2.getBirthday().value));
        }

        if (ARGUMENT_DESCENDING_WORD.equals(order)) {
            requireNonNull(sortOrderComp);
            sortOrderComp = sortOrderComp.reversed();
        }
        return sortOrderComp;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SortCommand
                && this.prefix.equals(((SortCommand) other).prefix)
                && this.order.equals(((SortCommand) other).order));


    }
}
