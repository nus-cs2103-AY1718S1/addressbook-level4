package seedu.address.model.person;

import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Gender> genderProperty();
    Gender getGender();
    ObjectProperty<MatricNo> matricNoProperty();
    MatricNo getMatricNo();
    ObjectProperty<Phone> phoneProperty();
    Phone getPhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<Timetable> timetableProperty();
    Timetable getTimetable();
    ObjectProperty<Remark> remarkProperty();
    Remark getRemark();
    ObjectProperty<PhotoPath> photoPathProperty();
    PhotoPath getPhotoPath();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();
    ObjectProperty<Birthday> birthdayProperty();
    Birthday getBirthday();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getGender().equals(this.getGender())
                && other.getMatricNo().equals(this.getMatricNo())
                && other.getPhone().equals(this.getPhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress())
                && other.getTimetable().equals(this.getTimetable())
                && other.getRemark().equals(this.getRemark()))
                && other.getPhotoPath().equals(this.getPhotoPath())
                && other.getBirthday().equals(this.getBirthday());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nGender: ")
                .append(getGender())
                .append("\nMatric No.: ")
                .append(getMatricNo())
                .append("\nPhone: ")
                .append(getPhone())
                .append("\nEmail: ")
                .append(getEmail())
                .append("\nAddress: ")
                .append(getAddress())
                .append("\nBirthday: ")
                .append(getBirthday())
                .append("\nTimetable: ")
                .append((getTimetable().toString().equals("http://modsn.us/5tN3z")) ? "" : getTimetable())
                .append("\nRemark: ")
                .append(getRemark())
                .append("\nPhoto: ")
                .append(getPhotoPath())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
