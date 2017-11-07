package seedu.address.model.place;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for the places in the Tourist-Book.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPlace {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Website> websiteProperty();
    Website getWebsite();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<PostalCode> postalcodeProperty();
    PostalCode getPostalCode();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPlace other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPhone().equals(this.getPhone())
                && other.getWebsite().equals(this.getWebsite())
                && other.getAddress().equals(this.getAddress())
                && other.getPostalCode().equals(this.getPostalCode()));
    }

    /**
     * Formats the place as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Website: ")
                .append(getWebsite())
                .append(" Address: ")
                .append(getAddress())
                .append(" PostalCode: ")
                .append(getPostalCode())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
