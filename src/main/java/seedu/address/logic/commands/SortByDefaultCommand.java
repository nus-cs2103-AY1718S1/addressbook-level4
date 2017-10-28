package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.DefaultPersonComparator;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts the displayed person list by their names in alphabetical order.
 */
public class SortByDefaultCommand extends SortCommand {
    @Override
    public Comparator<ReadOnlyPerson> getComparator() {
        return new DefaultPersonComparator();
    }
}
