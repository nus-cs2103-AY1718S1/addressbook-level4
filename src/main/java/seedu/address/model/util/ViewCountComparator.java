package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;

//@@author soonwj
/**
 * Compares the view counts of 2 ReadOnlyPersons
 */
public class ViewCountComparator implements Comparator<ReadOnlyPerson> {
    @Override
    public int compare(ReadOnlyPerson r1, ReadOnlyPerson r2) {
        int r1ViewCount = r1.getViewCount();
        int r2ViewCount = r2.getViewCount();

        if (r1ViewCount < r2ViewCount) {
            return 1;
        } else if (r1ViewCount > r2ViewCount) {
            return -1;
        } else {
            return 0;
        }
    }
}
