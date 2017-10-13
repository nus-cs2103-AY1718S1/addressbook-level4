package seedu.address.model.util;

import java.util.Comparator;
import java.util.List;

import seedu.address.logic.parser.SortArgument;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Utility for any comparison or sorting related actions that include Persons.
 */
public class PersonSortingUtil {

    /**
     * Returns a newly generated comparator from the given sortArguments.
     */
    public static Comparator<ReadOnlyPerson> generateComparator(List<SortArgument> sortArguments) {
        return (person1, person2) -> {
            // default case for empty arguments
            if (sortArguments == null || sortArguments.isEmpty()) {
                return person1.compareTo(person2);

            } else {
                int previousCompareValue = 0;
                for (SortArgument sortArgument : sortArguments) {
                    if (previousCompareValue != 0) {
                        return previousCompareValue;
                    } else {
                        previousCompareValue = person1.compareTo(person2, sortArgument);
                    }
                }
                return previousCompareValue;

            }
        };
    }
}
