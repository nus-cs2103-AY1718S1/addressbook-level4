package seedu.address.model.person;

import java.util.List;
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
    ObjectProperty<EmailAddress> emailAddressProperty();
    EmailAddress getEmailAddress();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Photo> photoProperty();
    Photo getPhoto();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Birthdate> birthdateProperty();
    Birthdate getBirthdate();
    ObjectProperty<NusModules> nusModulesProperty();
    NusModules getNusModules();
    UserId getUserId();
    ObjectProperty<UserId> userIdProperty();
    boolean containsTags(List<String> tags);

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {

        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmailAddress().equals(this.getEmailAddress())
                && other.getAddress().equals(this.getAddress())
                && other.getBirthdate().equals(this.getBirthdate())
                && other.getNusModules().equals(this.getNusModules())
                && other.getUserId().equals(this.getUserId()));
    }

    /**
     * Returns true if both have the same email. (interfaces cannot override .equals)
     */
    default boolean isSameEmail(ReadOnlyPerson other) {

        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getEmailAddress().toString().toLowerCase()
                .equals(this.getEmailAddress().toString().toLowerCase()));
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
                .append(getEmailAddress())
                .append(" Address: ")
                .append(getAddress())
                .append(" Image: ")
                .append(getPhoto())
                .append(" Birthdate: ")
                .append(getBirthdate())
                .append(" User ID: ")
                .append(getUserId())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
