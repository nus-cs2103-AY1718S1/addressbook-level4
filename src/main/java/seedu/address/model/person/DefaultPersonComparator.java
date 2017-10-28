package seedu.address.model.person;

import java.util.Comparator;

/**
 * Default comparator for persons. Sorts first by favorites, then by name in alphabetical order.
 */
public class DefaultPersonComparator implements Comparator<ReadOnlyPerson> {
    @Override
    public int compare(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        boolean isThisPersonFavorite = thisPerson.getFavorite().isFavorite();
        boolean isOtherPersonFavorite = otherPerson.getFavorite().isFavorite();
        if (isThisPersonFavorite && !isOtherPersonFavorite) {
            return -1;
        } else if (!isThisPersonFavorite && isOtherPersonFavorite) {
            return 1;
        } else {
            String thisPersonName = thisPerson.getName().toString();
            String otherPersonName = otherPerson.getName().toString();
            return thisPersonName.compareToIgnoreCase(otherPersonName);
        }
    }
}
