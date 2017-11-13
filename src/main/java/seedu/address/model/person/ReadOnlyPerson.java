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

    ObjectProperty<Birthday> birthdayProperty();

    Birthday getBirthday();

    ObjectProperty<Address> addressProperty();

    Address getAddress();

    ObjectProperty<Remark> remarkProperty();

    Remark getRemark();

    ObjectProperty<Website> websiteProperty();

    Website getWebsite();

    ObjectProperty<Picture> pictureProperty();

    Picture getPicture();

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
                      && other.getBirthday().equals(this.getBirthday())
                      //@@author chilipadiboy
                      && other.getAddress().equals(this.getAddress())
                      //@@author Jemereny
                      && other.getWebsite().equals(this.getWebsite())
                      && other.getPicture().equals(this.getPicture())
                      //@@author chilipadiboy
                      && other.getRemark().equals(this.getRemark()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        //@@author chilipadiboy
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getPhone().toString() != null) {
            builder.append(" Phone: ")
                .append(getPhone());
        }

        if (getEmail().toString() != null) {
            builder.append(" Email: ")
                .append(getEmail());
        }
        if (getAddress().toString() != null) {
            builder.append(" Address: ")
                .append(getAddress());
        }
        if (getBirthday().toString() != null) {
            builder.append(" Birthday: ")
                .append(getBirthday());
        }
        builder.append(" Remarks: ")
            .append(getRemark());
        if (getWebsite().toString() != null) {
            builder.append(" Website: ")
                .append(getWebsite());
        }

        if (getPicture().toString() != null) {
            builder.append(" Picture: ")
                    .append(getPicture());
        }
        //@@author
        if (!(getTags().isEmpty())) {
            builder.append(" Tags: ");
            getTags().forEach(builder::append);
        }
        return builder.toString();
    }

}
