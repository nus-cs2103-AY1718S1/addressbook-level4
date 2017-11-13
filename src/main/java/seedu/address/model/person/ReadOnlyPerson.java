package seedu.address.model.person;

import java.util.ArrayList;
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
    ObjectProperty<ArrayList<Remark>> remarkProperty();
    ArrayList<Remark> getRemark();
    ObjectProperty<FavouriteStatus> favouriteStatusProperty();
    FavouriteStatus getFavouriteStatus();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Link> linkProperty();
    Link getLink();

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
                && other.getFavouriteStatus().equals(this.getFavouriteStatus())
                && other.getLink().equals(this.getLink()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Favourite: ")
                .append(getFavouriteStatus().getStatus())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Remark: ");
        getRemark().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Link: ")
                .append(getLink());
        return builder.toString();
    }

}
