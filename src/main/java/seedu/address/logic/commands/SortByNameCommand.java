package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.PersonNameComparator;
import seedu.address.model.person.ReadOnlyPerson;

//@@author marvinchin
/**
 * Sorts the {@code Person}s in the address book by their names in lexicographic order order.
 * @see PersonNameComparator
 */
public class SortByNameCommand extends SortCommand {

    public static final String COMMAND_OPTION = "name";

    @Override
    protected Comparator<ReadOnlyPerson> getComparator() {
        return new PersonNameComparator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SortByNameCommand; // instanceof handles nulls
    }
}
