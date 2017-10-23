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

    ObjectProperty<Company> companyProperty();

    Company getCompany();

    ObjectProperty<Position> positionProperty();

    Position getPosition();

    ObjectProperty<Status> statusProperty();

    Status getStatus();

    ObjectProperty<Priority> priorityProperty();

    Priority getPriority();

    ObjectProperty<Note> noteProperty();

    Note getNote();

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
                && other.getCompany().equals(this.getCompany())
                && other.getPosition().equals(this.getPosition())
                && other.getStatus().equals(this.getStatus())
                && other.getPriority().equals(this.getPriority())
                && other.getNote().equals(this.getNote()));
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
                .append(" Company: ")
                .append(getCompany())
                .append(" Position: ")
                .append(getPosition())
                .append(" Status: ")
                .append(getStatus())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Note: ")
                .append(getNote())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
