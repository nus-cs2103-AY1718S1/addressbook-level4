//@@author Houjisan
package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for getting Comparators to sort {@code AddressBook}
 */
public class ComparatorUtil {

    // Person Comparators

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

    // Comparator that sorts Tag objects by their name
    private static final Comparator<Tag> tagComparator = new Comparator<Tag>() {
        @Override
        public int compare(Tag t1, Tag t2) {
            return t1.tagName.compareTo(t2.tagName);
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

    // Returns a lexicographic-order comparator that compares by name first,
    // followed by phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsNameFirst() {
        return nameComparator.thenComparing(phoneComparator).thenComparing(emailComparator)
                .thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by name first in reverse order,
    // followed by phone, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsNameFirstReversed() {
        return nameComparator.reversed().thenComparing(phoneComparator).thenComparing(emailComparator)
                .thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by phone first,
    // followed by name, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsPhoneFirst() {
        return phoneComparator.thenComparing(nameComparator).thenComparing(emailComparator)
                .thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by phone first in reverse order,
    // followed by name, email then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsPhoneFirstReversed() {
        return phoneComparator.reversed().thenComparing(nameComparator).thenComparing(emailComparator)
                .thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by email first,
    // followed by name, phone then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsEmailFirst() {
        return emailComparator.thenComparing(nameComparator).thenComparing(phoneComparator)
                .thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by email first in reverse order,
    // followed by name, phone then address
    public static Comparator<ReadOnlyPerson> getAllComparatorsEmailFirstReversed() {
        return emailComparator.reversed().thenComparing(nameComparator).thenComparing(phoneComparator)
                .thenComparing(addressComparator);
    }

    // Returns a lexicographic-order comparator that compares by address first,
    // followed by name, phone, then email
    public static Comparator<ReadOnlyPerson> getAllComparatorsAddressFirst() {
        return addressComparator.thenComparing(nameComparator).thenComparing(phoneComparator)
                .thenComparing(emailComparator);
    }

    // Returns a lexicographic-order comparator that compares by address first in reverse order,
    // followed by name, phone, then email
    public static Comparator<ReadOnlyPerson> getAllComparatorsAddressFirstReversed() {
        return addressComparator.reversed().thenComparing(nameComparator).thenComparing(phoneComparator)
                .thenComparing(emailComparator);
    }

    public static Comparator<Tag> getTagComparator() {
        return tagComparator;
    }
}
