package seedu.address.model;

import seedu.address.model.person.InternalId;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * A read-only immutable interface for a Meeting in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMeeting {
    String getDate();
    String getTime();
    String getDateTimeStr();
    String getLocation();
    String getNotes();
    LocalDateTime getDateTime();
    ArrayList<InternalId> getListOfPersonsId();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMeeting other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getDateTimeStr().equals(this.getDateTimeStr()) // state checks here onwards
                && other.getLocation().equals(this.getLocation())
                && other.getNotes().equals(this.getNotes()));
    }

    /*
    /**
     * Formats the meeting as text, showing all contact details.
     *
    default String getAsText() {
        ReadOnlyAddressBook abData = ;
        final StringBuilder builder = new StringBuilder();
        builder.append("You have meeting on ")
                .append(getDate())
                .append("from ")
                .append(getTime())
                .append("at ")
                .append(getLocation())
                .append("with ");
        getListOfPersonsId().forEach(abData.getPersonByInternalIndex(i));

        getListOfPersonsId().forEach(builder::append);
        return builder.toString();
    }*/


}
