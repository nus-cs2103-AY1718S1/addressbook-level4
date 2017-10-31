package seedu.address.model.person;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.UniqueRelList;
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
    //@@author sebtsh
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
    //@@author
    ObjectProperty<Photo> photoProperty();

    Photo getPhoto();

    ObjectProperty<UniqueTagList> tagProperty();

    Set<Tag> getTags();

    Set<Relationship> getRelation();
    ObjectProperty<UniqueRelList> relProperty();

    //@@author sebtsh
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
                && other.getNote().equals(this.getNote())
                && other.getPhoto().equals(this.getPhoto()));
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
                .append(" Photo: ")
                .append(getPhoto())
                .append(" Relationship: ")
                .append(getRelation())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    //@@author
}
