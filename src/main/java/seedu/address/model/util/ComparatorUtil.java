package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains utility methods for getting Comparators to sort {@code AddressBook}
 */
public class ComparatorUtil {

    // Comparator that sorts ReadOnlyPerson objects by their favourite status, favourited first
    private static final Comparator<ReadOnlyPerson> favouriteComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            boolean v1 = p1.getFavouriteStatus().getStatus();
            boolean v2 = p2.getFavouriteStatus().getStatus();
            if (v1 && !v2) {
                return -1;
            } else if (!v1 && v2) {
                return 1;
            }
            return 0;
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their name
    private static final Comparator<ReadOnlyPerson> nameComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getName().toString().compareTo(p2.getName().toString());
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their phone
    private static final Comparator<ReadOnlyPerson> phoneComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getPhone().toString().compareTo(p2.getPhone().toString());
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their email
    private static final Comparator<ReadOnlyPerson> emailComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getEmail().toString().compareTo(p2.getEmail().toString());
        }
    };

    // Comparator that sorts ReadOnlyPerson objects by their address
    private static final Comparator<ReadOnlyPerson> addressComparator = new Comparator<ReadOnlyPerson>() {
        @Override
        public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
            return p1.getAddress().toString().compareTo(p2.getAddress().toString());
        }
    };

    public static Comparator<ReadOnlyPerson> getFavouriteComparator() {
        return favouriteComparator;
    }

    public static Comparator<ReadOnlyPerson> getNameComparator() {
        return nameComparator;
    }

    public static Comparator<ReadOnlyPerson> getPhoneComparator() {
        return phoneComparator;
    }

    public static Comparator<ReadOnlyPerson> getEmailComparator() {
        return emailComparator;
    }

    public static Comparator<ReadOnlyPerson> getAddressComparator() {
        return addressComparator;
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenNameFirst() {
        return favouriteComparator.thenComparing(nameComparator).thenComparing(phoneComparator)
                .thenComparing(emailComparator).thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenPhoneFirst() {
        return favouriteComparator.thenComparing(phoneComparator).thenComparing(nameComparator)
                .thenComparing(emailComparator).thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenEmailFirst() {
        return favouriteComparator.thenComparing(emailComparator).thenComparing(nameComparator)
                .thenComparing(phoneComparator).thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by favourite status first,
    // followed by name, phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsFavThenAddressFirst() {
        return favouriteComparator.thenComparing(addressComparator).thenComparing(nameComparator)
                .thenComparing(phoneComparator).thenComparing(emailComparator);
    }
}
