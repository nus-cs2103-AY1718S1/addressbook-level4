package seedu.address.model.person;

import java.util.Set;

import javafx.beans.property.ObjectProperty;

import seedu.address.model.person.email.Email;
import seedu.address.model.person.email.UniqueEmailList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
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
    ObjectProperty<Country> countryProperty();
    Country getCountry();
    ObjectProperty<UniqueEmailList> emailProperty();
    Set<Email> getEmails();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<UniqueScheduleList> scheduleProperty();
    Set<Schedule> getSchedules();
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
                && other.getCountry().equals(this.getCountry())
                && other.getEmails().equals(this.getEmails())
                && other.getAddress().equals(this.getAddress()));
    }

    /**
     * Formats the person as text, showing all contact details.
     * Excludes all schedules.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Country Code: ")
                .append(getCountry())
                .append(" Emails: ");
        for (Email email: getEmails()) {
            builder.append(email).append("; ");
        }
        builder.append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
