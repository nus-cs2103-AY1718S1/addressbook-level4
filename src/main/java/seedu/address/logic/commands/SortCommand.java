//@@author Estois
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.EmptyBookException;


/**
 * Sorts the list of people based on given parameter
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SORT_SUCCESS = "Peersonals sorted by %1$s.";
    public static final String MESSAGE_EMPTY_BOOK = "Peersonals is currently empty. Unable to sort.";
    public static final String SORT_MULTIPLE_INPUT = "Only one parameter can be entered.";

    private static final String PREFIX_NAME = "n/";
    private static final String PREFIX_PHONE = "p/";
    private static final String PREFIX_EMAIL = "e/";
    private static final String PREFIX_ADDRESS = "a/";
    private static final String PREFIX_TAG = "t/";
    private static final String PREFIX_AGE = "o/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts peers in ascending order according to specified parameter (Sorts by name by default)\n"
            + "Parameters: "
            + "Prefix\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "\n"
            + "Accepted Parameters: (n/ p/ e/ a/ t/ o/)";

    private final String parameter;
    private String sortParam;

    public SortCommand(String parameter) {
        requireNonNull(parameter);

        this.parameter = parameter;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Comparator<ReadOnlyPerson> sortComparator = getSortComparator(this.parameter);
        try {
            model.sortPerson(sortComparator);
        } catch (EmptyBookException a) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortParam));
    }

    private Comparator<ReadOnlyPerson> getSortComparator(String parameter) {
        switch (parameter) {
        case PREFIX_NAME:
            this.sortParam = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());

        case PREFIX_EMAIL:
            this.sortParam = "email";
            return (o1, o2) -> o1.getEmail().toString().compareToIgnoreCase(o2.getEmail().toString());

        case PREFIX_PHONE:
            this.sortParam = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(o2.getPhone().toString());

        case PREFIX_ADDRESS:
            this.sortParam = "address";
            return (o1, o2) -> o1.getAddress().toString().compareToIgnoreCase(o2.getAddress().toString());

        case PREFIX_TAG:
            this.sortParam = "tag";
            return (o1, o2) -> o1.getTags().toString().compareToIgnoreCase(o2.getTags().toString());

        case PREFIX_AGE:
            this.sortParam = "age";
            return (o1, o2) -> o1.getAge().toString().compareToIgnoreCase(o2.getAge().toString());

        default:
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
        }
    }

}