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

    ObjectProperty<FormClass> formClassProperty();

    FormClass getFormClass();

    ObjectProperty<Grades> gradesProperty();

    Grades getGrades();

    ObjectProperty<PostalCode> postalCodeProperty();

    PostalCode getPostalCode();

    ObjectProperty<Remark> remarkProperty();

    Remark getRemark();

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
                && other.getFormClass().equals(this.getFormClass())
                && other.getGrades().equals(this.getGrades())
                && other.getPostalCode().equals(this.getPostalCode()))
                && other.getRemark().equals((this.getRemark()));
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
               .append(" FormClass: ")
               .append(getFormClass())
               .append(" Grades: ")
               .append(getGrades())
               .append(" PostalCode: ")
               .append(getPostalCode())
               .append(" Remark: ")
               .append(getRemark())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


    //@@author lincredibleJC
    default String getTagsAsString() {
        StringBuilder sb = new StringBuilder();
        getTags().forEach(tag -> sb.append(tag.tagName + " "));
        return sb.toString();
    }
    //@@author

}
