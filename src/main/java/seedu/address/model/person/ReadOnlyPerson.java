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
    //@@author yanji1221
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
    //@@author
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    //@@author quangtdn
    ObjectProperty<ProfilePage> profilepageProperty();
    ProfilePage getProfilePage();
    //@@author
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    ObjectProperty<Favorite> favoriteProperty();
    Favorite getFavorite();

    //@@author yanji1221
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getBirthday().equals(this.getBirthday())
                && other.getAddress().equals(this.getAddress()))
                && other.getProfilePage().equals(this.getProfilePage());
    }
    //@@author
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
                .append(" Birthday ")
                .append(getBirthday())
                .append(" Address: ")
                .append(getAddress())
                .append(" Profile Page: ")
                .append(getProfilePage())
                .append(" Status: ")
                .append(" ")
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
