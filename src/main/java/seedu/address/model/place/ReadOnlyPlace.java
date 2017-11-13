package seedu.address.model.place;

import java.util.ArrayList;
import java.util.List;
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
     * Returns a List containing all the property names of a Place.
     */
    default List<String> getPropertyNamesAsList() {
        List<String> propertyNames = new ArrayList<String>();

        propertyNames.add("Name");
        propertyNames.add("Phone");
        propertyNames.add("Address");
        propertyNames.add("Postal Code");
        propertyNames.add("Website");

        return propertyNames;
    }

    /**
     * Formats the place as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Address: ")
                .append(getAddress())
                .append(" PostalCode: ")
                .append(getPostalCode())
                .append(" Website: ")
                .append(getWebsite())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
