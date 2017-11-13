package seedu.address.model.person;

import static seedu.address.model.person.PersonComparatorUtil.compareAddress;
import static seedu.address.model.person.PersonComparatorUtil.compareEmail;
import static seedu.address.model.person.PersonComparatorUtil.compareFavorite;
import static seedu.address.model.person.PersonComparatorUtil.compareName;
import static seedu.address.model.person.PersonComparatorUtil.comparePhone;

import java.util.Comparator;

//@@author marvinchin
/**
 * Default comparator for {@Person}s.
 * Sorts in the order:
 * 1. {@code Favorite} status
 * 2. {@code Name} in lexicographic order
 * 3. {@code Phone} in numeric order
 * 4. {@code Address} in lexicographic order
 * 5. {@code Email} in lexicographic order
 */
public class PersonDefaultComparator implements Comparator<ReadOnlyPerson> {
    @Override
    public int compare(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        if (!thisPerson.getFavorite().equals(otherPerson.getFavorite())) {
            return compareFavorite(thisPerson, otherPerson);
        } else if (!thisPerson.getName().equals(otherPerson.getName())) {
            return compareName(thisPerson, otherPerson);
        } else if (!thisPerson.getPhone().equals(otherPerson.getPhone())) {
            return comparePhone(thisPerson, otherPerson);
        } else if (!thisPerson.getAddress().equals(otherPerson.getAddress())) {
            return compareAddress(thisPerson, otherPerson);
        } else {
            return compareEmail(thisPerson, otherPerson);
        }
    }
}
