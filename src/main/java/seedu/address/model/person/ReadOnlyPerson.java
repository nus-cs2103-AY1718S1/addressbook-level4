package seedu.address.model.person;

import java.util.ArrayList;
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
    ObjectProperty<Avatar> avatarProperty();
    Avatar getAvatar();
    ObjectProperty<NokName> nokNameProperty();
    NokName getNokName();
    ObjectProperty<NokPhone> nokPhoneProperty();
    NokPhone getNokPhone();
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
                && other.getAvatar().equals(this.getAvatar())
                && other.getNokName().equals(this.getNokName())
                && other.getNokPhone().equals(this.getNokPhone()));
    }

    //@@author AceCentury
    /**
     * Returns a List of Strings containing all the property names of a Person.
     */
    default List<String> getPropertyNamesAsList() {
        List<String> propertyNames = new ArrayList<String>();

        propertyNames.add("Name");
        propertyNames.add("Phone");
        propertyNames.add("Email");
        propertyNames.add("Address");
        propertyNames.add("NokName");
        propertyNames.add("NokPhone");

        return propertyNames;
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
                .append(" Address: ")
                .append(getAddress())
                .append(" Avatar: ")
                .append(getAvatar())
                .append(" Next-of-Kin Name: ")
                .append(getNokName())
                .append(" Next-of-Kin Phone: ")
                .append(getNokPhone())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
