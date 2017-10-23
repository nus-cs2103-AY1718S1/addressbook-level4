package seedu.address.model.person;

import java.util.Comparator;

/**
 * A comparator for comparing two ReadOnlyPerson objects by name.
 */
public class ReadOnlyPersonComparator implements Comparator<ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson a, ReadOnlyPerson b) {
        return a.getName().compareTo(b.getName());
    }

}
