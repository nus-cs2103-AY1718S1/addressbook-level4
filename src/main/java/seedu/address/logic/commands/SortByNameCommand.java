package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.person.OrderByNamePersonComparator;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Sorts the displayed person list by their names in alphabetical order.
 */
public class SortByNameCommand extends SortCommand {

    public static final String COMMAND_OPTION = "name";

    @Override
    public Comparator<ReadOnlyPerson> getComparator() {
        return new OrderByNamePersonComparator();
    }
}
