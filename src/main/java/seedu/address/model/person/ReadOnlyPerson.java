package seedu.address.model.person;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<PostalCode> postalCodeProperty();
    PostalCode getPostalCode();
    ObjectProperty<Debt> debtProperty();
    Debt getDebt();
    ObjectProperty<DateBorrow> dateBorrowProperty();
    DateBorrow getDateBorrow();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();
    ObjectProperty<DateRepaid> dateRepaidProperty();
    DateRepaid getDateRepaid();
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
                && other.getAddress().equals(this.getAddress()))
                && other.getPostalCode().equals(this.getPostalCode())
                && other.getDebt().equals(this.getDebt())
                && other.getDateBorrow().equals(this.getDateBorrow())
                && other.getDeadline().equals(this.getDeadline())
                && other.getDateRepaid().equals(this.getDateRepaid());
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
                .append(" Postal Code: ")
                .append(getPostalCode())
                .append(getDebt())
                .append(getDateBorrow())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(getDateRepaid())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
