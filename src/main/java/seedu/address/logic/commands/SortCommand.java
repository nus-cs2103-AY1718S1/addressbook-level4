//@@author sunarjo-denny
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.EmptyPersonListException;

/**
 * Sorts the persons list according to the attribute specified
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String REVERSE_SEQUENCE = "reverse";

    public static final String PREFIX_NAME = "name";
    public static final String PREFIX_PHONE = "phone";

    public static final String MESSAGE_SUCCESS = "list sorted by %1$s in %2$s order!";
    public static final String MESSAGE_PERSONS_LIST_EMPTY = "The list is empty!";
    public static final String MESSAGE_MULTIPLE_ATTRIBUTE_ERROR = "Only accepts one attribute!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort the person list in ascending or descending order according to the attribute specified.\n"
            + "Parameters: "
            + "[attribute [reverse]]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + " " + REVERSE_SEQUENCE;

    private final String attribute;
    private final boolean isReversed;

    // Default values for sorting
    private String sortBy = "name";
    private String sequence = "ascending";

    public SortCommand(String attribute, boolean isReversed) {
        requireNonNull(attribute);
        requireNonNull(isReversed);

        this.attribute = attribute;
        this.isReversed = isReversed;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Comparator<ReadOnlyPerson> comparator = sortComparatorByPrefix(this.attribute);
        try {
            model.sortPersonList(comparator, isReversed);
        } catch (EmptyPersonListException eple) {
            throw new CommandException(MESSAGE_PERSONS_LIST_EMPTY);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (isReversed) {
            this.sequence = "descending";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sortBy, sequence));
    }

    /**
     * Comparator depending on the attribute specified
     */
    private Comparator<ReadOnlyPerson> sortComparatorByPrefix(String attribute) {
        switch (attribute) {
        case PREFIX_NAME:
            this.sortBy = "name";
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
        case PREFIX_PHONE:
            this.sortBy = "phone";
            return (o1, o2) -> o1.getPhone().toString().compareToIgnoreCase(o2.getPhone().toString());
        default:
            return (o1, o2) -> o1.getName().toString().compareToIgnoreCase(o2.getName().toString());
        }
    }
}
