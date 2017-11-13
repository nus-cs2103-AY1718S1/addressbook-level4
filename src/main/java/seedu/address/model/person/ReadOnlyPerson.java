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
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Homepage> homepageProperty();
    Homepage getHomepage();
    ObjectProperty<Remark> remarkProperty();
    Remark getRemark();
    ObjectProperty<Avatar> avatarProperty();
    Avatar getAvatar();
    boolean isHomepageManuallySet();

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
                && other.getHomepage().equals(this.getHomepage())
                && other.getRemark().equals(this.getRemark())
                && other.getAvatar().equals(this.getAvatar());
    }

    //@@author karrui
    /**
     * Formats the person as text, showing all available contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ").append(getPhone());
        if (!"".equals(getEmail().value)) {
            builder.append(" Email: ").append(getEmail());
        }
        if (!"".equals(getAddress().value)) {
            builder.append(" Address: ").append(getAddress());
        }
        builder.append(" Homepage: ").append(getHomepage());
        if (!getTags().isEmpty()) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }
}
