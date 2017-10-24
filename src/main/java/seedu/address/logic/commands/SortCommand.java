package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.EmptyListException;


/**
 * Sorts list of all contacts base on given parameter.
 * Accepts both ascending and descending.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String BY_ASCENDING = "asc";
    public static final String BY_DESCENDING = "dsc";

    public static final String MESSAGE_SORT_LIST_SUCCESS = "List has been sorted by %1$s in %2$s order.";
    public static final String MESSAGE_EMPTY_LIST = "The list is empty.";

    private static final String PREFIX_NAME_SORT = "n/";
    private static final String PREFIX_PHONE_SORT = "p/";
    private static final String PREFIX_EMAIL_SORT = "e/";
    private static final String PREFIX_ADDRESS_SORT = "a/";
    private static final String PREFIX_DATEADDED_SORT = "t/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts list of all contacts by their attributes,"
            + " defaults to name when no parameters found and ascending when order not specified.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME_SORT + "(" + BY_ASCENDING + " OR " + BY_DESCENDING + ")] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PHONE_SORT + BY_DESCENDING + " OR "
            + COMMAND_WORD + " " + PREFIX_ADDRESS_SORT + " OR "
            + COMMAND_WORD;

    private final String sortType;
    private final Boolean isDescending;

    //Default setting, not final as subjected to change
    private String sortTypeReadable = "name";
    private String sortOrderReadable = "ascending";

    /**
     * @param sortType     specify which attribute to sort by
     * @param isDescending specify if sorting is to be in descending order
     */
    public SortCommand(String sortType, Boolean isDescending) {
        requireNonNull(sortType);
        requireNonNull(isDescending);

        this.sortType = sortType;
        this.isDescending = isDescending;

        sortOrderReadable = (isDescending) ? "descending" : "ascending";
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Comparator<ReadOnlyPerson> sortType = getComparator(this.sortType);
        try {
            model.sortPerson(sortType, isDescending);
        } catch (EmptyListException ele) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SORT_LIST_SUCCESS, sortTypeReadable, sortOrderReadable));
    }

    public Comparator<ReadOnlyPerson> getComparator(String sortType) {
        switch (sortType) {
        case PREFIX_NAME_SORT:
            sortTypeReadable = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(
                    o2.getName().toString()
            );
        case PREFIX_PHONE_SORT:
            sortTypeReadable = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(
                    o2.getPhone().toString()
            );
        case PREFIX_EMAIL_SORT:
            sortTypeReadable = "email";
            return (o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(
                    o2.getEmail().toString()
            );
        case PREFIX_ADDRESS_SORT:
            sortTypeReadable = "address";
            return (o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(
                    o2.getAddress().toString()
            );
        case PREFIX_DATEADDED_SORT:
            sortTypeReadable = "date added";
            return Comparator.comparing(o -> o.getDateAdded().getDateObject());
        default:
            sortTypeReadable = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(
                    o2.getName().toString()
            );
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sortType.equals(((SortCommand) other).sortType)
                && isDescending.equals(((SortCommand) other).isDescending));
    }
}
