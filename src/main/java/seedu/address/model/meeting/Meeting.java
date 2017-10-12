package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Meeting implements ReadOnlyMeeting {
    private ObjectProperty<NameMeeting> name;
    private ObjectProperty<DateTime> date;
    //private ObjectProperty<LocalTime> time;
    private ObjectProperty<Place> place;

    public Meeting(NameMeeting name, DateTime date, Place place) {
        requireAllNonNull(name, date, place);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.place = new SimpleObjectProperty<>(place);
        // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyMeeting.
     */
    public Meeting(ReadOnlyMeeting source) {
        this(source.getName(), source.getDate(), source.getPlace());
    }

    public void setName(NameMeeting name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<NameMeeting> nameProperty() {
        return name;
    }

    @Override
    public NameMeeting getName() {
        return name.get();
    }

    public void setDateTime(DateTime date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<DateTime> dateProperty() {
        return date;
    }

    @Override
    public DateTime getDate() {
        return date.get();
    }

    public LocalDateTime getActualDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime;
    }

    public void setPlace(Place place) {
        this.place.set(requireNonNull(place));
    }

    @Override
    public ObjectProperty<Place> placeProperty() {
        return place;
    }

    @Override
    public Place getPlace() {
        return place.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMeeting // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMeeting) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, place);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
