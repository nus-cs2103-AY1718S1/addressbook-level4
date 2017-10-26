package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.InternalId;

/**
 * Represents a Meeting
 * Guarantees: immutable; meeting time is in the future
 */
public class Meeting implements Comparable<Meeting> {

    //public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    //public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final String MESSAGE_INVALID_DATE = "The meeting must be in the future.";
    private LocalDateTime dateTime;
    private String location;
    private String notes;
    private ArrayList<InternalId> listOfPersonsId;

    /**
     * Validates params given for meeting
     *
     * @throws IllegalValueException if the given meeting time is not in the future
     */
    public Meeting(LocalDateTime dateTime, String location, String notes, ArrayList<InternalId> listOfPersonsId)
            throws IllegalValueException {
        requireNonNull(dateTime);
        requireNonNull(location);
        requireNonNull(listOfPersonsId);
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalValueException(MESSAGE_INVALID_DATE);
        }

        this.dateTime = dateTime;
        this.location = location.trim();
        this.notes = notes.trim();
        this.listOfPersonsId = listOfPersonsId;
    }

    // Get methods
    public String getDate() {
        return dateTime.format(DATE_FORMATTER);
    }

    public String getTime() {
        return dateTime.format(TIME_FORMATTER);
    }

    public String getDateTime() {
        return dateTime.toString();
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    public ArrayList<InternalId> getListOfPersonsId() {
        return listOfPersonsId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Meeting // instanceof handles nulls
                && this.dateTime.equals(((Meeting) other).dateTime)
                && this.location.equals(((Meeting) other).location)
                && this.notes.equals(((Meeting) other).notes)
                && this.listOfPersonsId.equals(((Meeting) other).listOfPersonsId)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, location, notes, listOfPersonsId);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "Date: " + dateTime.format(DATE_FORMATTER) + '\n'
                + "Time: " + dateTime.format(TIME_FORMATTER) + '\n'
                + "Location: " + location + '\n'
                + "Notes: " + notes;
    }

    @Override
    public int compareTo(Meeting other) {
        return dateTime.compareTo(other.dateTime);
    }
}
