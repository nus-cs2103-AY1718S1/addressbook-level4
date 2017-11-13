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
    //@@author siri99
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
    //@@author siri99
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Score> scoreProperty();
    Score getScore();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    Avatar getAvatarPic();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getBirthday().equals(this.getBirthday())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                //@@author siri99
                .append(" Birthday: ")
                .append(getBirthday())
                //@@author siri99
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                //@@author Henning
                .append(" ")
                .append(getScore())
                //@@author
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    default String getOnlyTags() {
        final StringBuilder builder = new StringBuilder();
        for (Tag tag : getTags()) {
            builder.append(" " + (tag.tagName).toLowerCase());
        }
        return builder.toString();
    }
}
