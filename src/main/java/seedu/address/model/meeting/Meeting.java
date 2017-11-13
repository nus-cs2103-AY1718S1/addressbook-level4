package seedu.address.model.meeting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

//@@author alexanderleegs
/**
 * Represents a Meeting in the address book.
 * Guarantees: immutable
 */
public class Meeting {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time format should be YYYY-MM-DD HH:MM";

    public final LocalDateTime date;
    public final String value;
    public final String meetingName;
    private ReadOnlyPerson person;
    private ObjectProperty<Name> displayName;
    private ObjectProperty<String> displayValue;
    private ObjectProperty<String> displayMeetingName;

    /**
     * Validates given meeting name.
     *
     * @throws IllegalValueException if the given meeting name string is invalid.
     */
    public Meeting(ReadOnlyPerson person, String meetingName, String time) throws IllegalValueException {
        setPerson(person);
        this.displayName = new SimpleObjectProperty<>(person.getName());
        this.meetingName = meetingName;
        this.displayMeetingName = new SimpleObjectProperty<>(meetingName);
        if (time == null) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDateTime date = LocalDateTime.parse(trimmedTime, formatter);
            this.date = date;
            value = date.format(formatter);
            this.displayValue = new SimpleObjectProperty<>(value);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    //@@author LimYangSheng
    /**
     * Overloaded constructor for creating meeting objects with no proper reference to their person object
     */
    public Meeting(String meetingName, String time) throws IllegalValueException {
        this.meetingName = meetingName;
        this.displayMeetingName = new SimpleObjectProperty<>(meetingName);
        if (time == null) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime date = LocalDateTime.parse(trimmedTime, formatter);
            this.date = date;
            value = date.format(formatter);
            this.displayValue = new SimpleObjectProperty<>(value);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Overloaded constructor to create a new meeting object given a meeting for reference purposes.
     */
    public Meeting(Meeting meeting) {
        this.meetingName = meeting.meetingName;
        this.date = meeting.date;
        this.value = meeting.value;
        this.person = meeting.person;
        this.displayValue = meeting.displayValue;
        this.displayMeetingName = meeting.displayMeetingName;
        this.displayName = meeting.displayName;
    }

    /**
     * Set the person attributes of the meeting object.
     */
    public void setPerson(ReadOnlyPerson person) {
        this.person = person;
        this.displayName = new SimpleObjectProperty<>(person.getName());
    }

    /**
     * Returns ReadOnlyPerson of the meeting
     */
    public ReadOnlyPerson getPerson() {
        return person;
    }

    /**
     * Return name for use by UI
     */
    public ObjectProperty<Name> nameProperty() {
        return displayName;
    }

    /**
     * Return meeting name for use by UI
     */
    public ObjectProperty<String> meetingNameProperty() {
        return displayMeetingName;
    }

    /**
     * Return meeting time for use by UI
     */
    public ObjectProperty<String> meetingTimeProperty() {
        return displayValue;
    }

    //@@author alexanderleegs
    @Override
    public boolean equals(Object other) {
        /* Only happens for testing as name attribute will be set for the main app*/
        if (this.person == null && other instanceof Meeting && ((Meeting) other).person == null) {
            return other == this // short circuit if same object
                    || (other instanceof Meeting // instanceof handles nulls
                    && this.date.equals(((Meeting) other).date)); //state check
        }

        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && this.date.equals(((Meeting) other).date)
                && this.person.equals(((Meeting) other).person)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[" + value + "]";
    }

}
