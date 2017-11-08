package seedu.address.model.person;

import java.util.Comparator;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    //@@author freesoup
    Comparator<ReadOnlyPerson> NAMESORTASC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o1.getName().compareTo(o2.getName());
    Comparator<ReadOnlyPerson> PHONESORTASC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o1.getPhone().compareTo(o2.getPhone());
    Comparator<ReadOnlyPerson> EMAILSORTASC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o1.getEmail().compareTo(o2.getEmail());
    Comparator<ReadOnlyPerson> NAMESORTDSC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o2.getName().compareTo(o1.getName());
    Comparator<ReadOnlyPerson> PHONESORTDSC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o2.getPhone().compareTo(o1.getPhone());
    Comparator<ReadOnlyPerson> EMAILSORTDSC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o2.getEmail().compareTo(o1.getEmail());
    //@@author

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Remark> remarkProperty();
    Remark getRemark();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress())
                && other.getRemark().equals(this.getRemark()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
