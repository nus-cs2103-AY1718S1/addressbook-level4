package seedu.address.model.person;

//@@author marvinchin

/**
 * Utility class with useful methods for writing {@code Person} comparators.
 */
public class PersonComparatorUtil {

    /**
     * Compares two {@code Person} based on their {@code Favorite} status.
     * The favorited person will be ordered first.
     * If both persons have the same favorite status (yes/no), they are considered equal.
     */
    public static int compareFavorite(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        boolean isThisPersonFavorite = thisPerson.getFavorite().isFavorite();
        boolean isOtherPersonFavorite = otherPerson.getFavorite().isFavorite();
        if (isThisPersonFavorite && !isOtherPersonFavorite) {
            return -1;
        } else if (!isThisPersonFavorite && isOtherPersonFavorite) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Compares two {@code Person}s based on their {@code Name}.
     */
    public static int compareName(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        String thisPersonName = thisPerson.getName().toString();
        String otherPersonName = otherPerson.getName().toString();
        return thisPersonName.compareToIgnoreCase(otherPersonName);
    }

    /**
     * Compares two {@code Person}s based on their {@code Phone}.
     */
    public static int comparePhone(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        String thisPersonPhone = thisPerson.getPhone().toString();
        String otherPersonPhone = otherPerson.getPhone().toString();
        return thisPersonPhone.compareToIgnoreCase(otherPersonPhone);
    }

    /**
     * Compares two {@code Person}s based on their {@code Address}.
     */
    public static int compareAddress(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        String thisPersonAddress = thisPerson.getAddress().toString();
        String otherPersonAddress = otherPerson.getAddress().toString();
        return thisPersonAddress.compareToIgnoreCase(otherPersonAddress);
    }

    /**
     * Compares two {@code Person}s based on their {@code Email}.
     */
    public static int compareEmail(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        String thisPersonEmail = thisPerson.getEmail().toString();
        String otherPersonEmail = otherPerson.getEmail().toString();
        return thisPersonEmail.compareToIgnoreCase(otherPersonEmail);
    }

    /**
     * Compares two {@code Person}s based on their {@code LastAccessDate}.
     * The person which is most recently accessed person will be ordered first.
     */
    public static int compareLastAccessDate(ReadOnlyPerson thisPerson, ReadOnlyPerson otherPerson) {
        LastAccessDate thisPersonLastAccessDate = thisPerson.getLastAccessDate();
        LastAccessDate otherPersonLastAccessDate = otherPerson.getLastAccessDate();
        int dateCompare = thisPersonLastAccessDate.compareTo(otherPersonLastAccessDate);
        // Date comparison puts earlier dates first, so we need to reverse it
        return -dateCompare;
    }
}
