package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons based on their name in alphabetical order.
 */
public class OrderByNamePersonComparator implements Comparator<ReadOnlyPerson> {
    @Override
    public int compare(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        String thisPersonName = thisPerson.getName().toString();
        String otherPersonName = otherPerson.getName().toString();
        return thisPersonName.compareToIgnoreCase(otherPersonName);
    }
}

