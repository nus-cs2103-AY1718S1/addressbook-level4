package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.*;
//import java.util.Collections;
import java.util.Objects;
//import java.util.Set;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Meeting implements ReadOnlyMeeting {
    private ObjectProperty<Name> name;
    private ObjectProperty<Date> date;
    //private ObjectProperty<LocalTime> time;
    private ObjectProperty<Place> place;

    public Meeting(Name name, Date date, Place place) {
        requireAllNonNull(name, date, place);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        //this.time = new SimpleObjectProperty<>(time);
        this.place = new SimpleObjectProperty<>(place);
        // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyMeeting.
     */
    public Meeting(ReadOnlyMeeting source) {
        this(source.getName(), source.getDate(), source.getPlace());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    /*public void setTime(LocalTime time) {
        this.time.set(requireNonNull(time));
    }

    @Override
    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    @Override
    public LocalTime getTime() {
        return time.get();
    }
    */
    public void setTime(Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    @Override
    public Date getDate() {
        return date.get();
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
