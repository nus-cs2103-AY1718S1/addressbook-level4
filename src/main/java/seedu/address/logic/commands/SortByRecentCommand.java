package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.PersonRecentComparator;
import seedu.address.model.person.ReadOnlyPerson;

//@@author marvinchin
/**
 * Sorts the {@code Person}s in the address book by the last time they were added, updated, or selected.
 * @see PersonRecentComparator
 */
public class SortByRecentCommand extends SortCommand {

    public static final String COMMAND_OPTION = "recent";

    @Override
    protected Comparator<ReadOnlyPerson> getComparator() {
        return new PersonRecentComparator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SortByRecentCommand; // instanceof handles nulls
    }
}
