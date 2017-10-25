package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.weblink.UniqueWebLinkList;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the address book.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<ArrayList<Email>> emailProperty();
    ArrayList<Email> getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Remark> remarkProperty();
    Remark getRemark();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<UniqueWebLinkList> webLinkProperty();
    Set<WebLink> getWebLinks();
    ArrayList<String> listOfWebLinkByCategory(String webLinkTag);


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
                && other.getRemark().equals(this.getRemark()));
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
                .append(" Remark: ")
                .append(getRemark())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" WebLinks: ");
        getWebLinks().forEach(builder::append);
        return builder.toString();
    }

}
