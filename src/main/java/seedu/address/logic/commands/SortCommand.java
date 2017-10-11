package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.NoPersonsException;

/**
 * Sorts persons according to field specified.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String REVERSE_ORDER = "r";

    public static final String MESSAGE_SORT_PERSON_SUCCESS = "Sorted address book by %1$s in %2$s order.";
    public static final String MESSAGE_MULTIPLE_ATTRIBUTE_ERROR = "Only one attribute can be entered.";
    public static final String MESSAGE_EMPTY_LIST = "No person(s) to sort.";

    private static final String PREFIX_NAME_FIELD = "n/";
    private static final String PREFIX_PHONE_FIELD = "p/";
    private static final String PREFIX_EMAIL_FIELD = "e/";
    private static final String PREFIX_ADDRESS_FIELD = "a/";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts persons either in ascending or descending order (ascending by default)"
            + " according to prefix specified (name by default)\n"
            + "Parameters: "
            + "[PREFIX\\[r]]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL_FIELD + REVERSE_ORDER;

    private final String field;
    private final boolean isReverseOrder;

    /*
        Default values assigned to variable used in MESSAGE_SORT_PERSON_SUCCESS
     */
    private String sortBy = "name";
    private String order = "ascending";

    /**
     * @param field     specify which field to sort by
     * @param isReverseOrder specify if sorting is to be in reverse order
     */
    public SortCommand(String field, boolean isReverseOrder) {
        requireNonNull(field);
        requireNonNull(isReverseOrder);

        this.field = field;
        this.isReverseOrder = isReverseOrder;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        Comparator<ReadOnlyPerson> sortComparator = getSortComparator(this.field);
        try {
            model.sortPerson(sortComparator, isReverseOrder);
        } catch (NoPersonsException npe) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (isReverseOrder) {
            this.order = "descending";
        }
        return new CommandResult(String.format(MESSAGE_SORT_PERSON_SUCCESS, sortBy, order));


    }

    private Comparator<ReadOnlyPerson> getSortComparator(String field) {
        switch (field) {
        case PREFIX_NAME_FIELD:
            this.sortBy = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString()
            );
        case PREFIX_PHONE_FIELD:
            this.sortBy = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(
                        o2.getPhone().toString()
            );
        case PREFIX_EMAIL_FIELD:
            this.sortBy = "email";
            return (o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(
                        o2.getEmail().toString()
            );
        case PREFIX_ADDRESS_FIELD:
            this.sortBy = "address";
            return (o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(
                        o2.getAddress().toString()
            );
        default:
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(
                        o2.getName().toString()
            );
        }
    }

}
