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
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();
    ObjectProperty<Remark> remarkProperty();
    Remark getRemark();
    ObjectProperty<Favorite> favoriteProperty();
    Favorite getFavorite();
    ObjectProperty<UniqueTagList> tagProperty();
    Major getMajor();
    ObjectProperty<Major> majorProperty();
    Facebook getFacebook();
    ObjectProperty<Facebook> facebookProperty();
    Set<Tag> getTags();
    List<String> getTagsString();

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
                && other.getBirthday().equals(this.getBirthday())
                && other.getRemark().equals(this.getRemark())
                && other.getFavorite().equals(this.getFavorite())
                && other.getMajor().equals(this.getMajor())
                && other.getFacebook().equals(this.getFacebook());
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
                .append(" Birthday: ")
                .append(getBirthday())
                .append(" Remark: ")
                .append(getRemark())
                .append(" Favorite: ")
                .append(getFavorite())
                .append(" Major: ")
                .append(getMajor())
                .append(" Facebook: ")
                .append(getFacebook())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
