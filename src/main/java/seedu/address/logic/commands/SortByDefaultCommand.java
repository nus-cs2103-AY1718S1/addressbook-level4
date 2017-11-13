package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.PersonDefaultComparator;
import seedu.address.model.person.ReadOnlyPerson;

//@@author marvinchin
/**
 * Sorts the {@code Person}s in the address book first by favorite status, then by name in lexicographic order.
 * @see PersonDefaultComparator
 */
public class SortByDefaultCommand extends SortCommand {
    @Override
    protected Comparator<ReadOnlyPerson> getComparator() {
        return new PersonDefaultComparator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SortByDefaultCommand; // instanceof handles nulls
    }
}
