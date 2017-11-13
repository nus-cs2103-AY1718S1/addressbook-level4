package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.insurance.UniqueLifeInsuranceList;
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
    //@@author Pujitha97
    ObjectProperty<DateOfBirth> dobProperty();
    DateOfBirth getDateOfBirth();
    ObjectProperty<Gender> genderProperty();
    Gender getGender();
    //@@author
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    String getReason();
    //@@author OscarWang114
    ObjectProperty<List<UUID>> lifeInsuranceIdProperty();
    List<UUID> getLifeInsuranceIds();
    ObjectProperty<UniqueLifeInsuranceList> lifeInsuranceProperty();
    UniqueLifeInsuranceList getLifeInsurances();
    //@@author


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
                && other.getDateOfBirth().equals(this.getDateOfBirth())
                && other.getGender().equals(this.getGender()));
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
                .append(" DateOfBirth: ")
                .append(getDateOfBirth())
                .append(" Gender: ")
                .append(getGender())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    //@@author arnollim
    default String getAsParagraph() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName() + "\n")
                .append("\nPhone: ")
                .append(getPhone())
                .append("\nEmail: ")
                .append(getEmail())
                .append("\nAddress: ")
                .append(getAddress())
                .append("\nDateOfBirth: ")
                .append(getDateOfBirth())
                .append("\nGender: ")
                .append(getGender())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    //@@author

    String getDetailByPrefix(Prefix prefix);
}
