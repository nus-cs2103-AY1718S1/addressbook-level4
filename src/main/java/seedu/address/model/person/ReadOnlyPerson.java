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
    ObjectProperty<HomeNumber> homeNumberProperty();
    HomeNumber getHomeNumber();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<SchEmail> schEmailProperty();
    SchEmail getSchEmail();
    ObjectProperty<Website> websiteProperty();
    Website getWebsite();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Boolean> favouriteProperty();
    Boolean getFavourite();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getHomeNumber().equals(this.getHomeNumber())
                && other.getEmail().equals(this.getEmail())
                && other.getSchEmail().equals(this.getSchEmail())
                && other.getWebsite().equals(this.getWebsite())
                && other.getAddress().equals(this.getAddress())
                && other.getBirthday().equals(this.getBirthday()))
                && other.getFavourite().equals(this.getFavourite());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" HomeNumber: ")
                .append(getHomeNumber())
                .append(" Email: ")
                .append(getEmail())
                .append(" SchEmail: ")
                .append(getSchEmail())
                .append(" Website: ")
                .append(getWebsite())
                .append(" Address: ")
                .append(getAddress())
                .append(" Birthday: ")
                .append(getBirthday())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the tags as text.
     */
    default String getTagsText() {
        final StringBuilder builder = new StringBuilder();
        for (Tag tag : getTags()) {
            builder.append(tag.tagName)
                .append(" ");
        }
        return builder.toString();
    }

    /**
     * Checks if person is favourited.
     */
    default Boolean isFavourite() {
        return getFavourite();
    }

}
