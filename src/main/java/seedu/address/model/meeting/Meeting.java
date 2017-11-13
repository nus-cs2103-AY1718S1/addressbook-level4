package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Melvin-leo
/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Meeting implements ReadOnlyMeeting {
    private ObjectProperty<NameMeeting> name;
    private ObjectProperty<DateTime> date;
    private ObjectProperty<Place> place;
    private ObjectProperty<List<ReadOnlyPerson>> personsMeet;
    private ObjectProperty<MeetingTag> tag;

    public Meeting (NameMeeting name, DateTime date, Place place, List<ReadOnlyPerson> listPerson, MeetingTag tag) {
        requireAllNonNull(name, date, place);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.place = new SimpleObjectProperty<>(place);
        this.personsMeet = new SimpleObjectProperty<>(listPerson);
        this.tag = new SimpleObjectProperty<>(tag);
    }

    /**
     * Creates a copy of the given ReadOnlyMeeting.
     */
    public Meeting(ReadOnlyMeeting source) {
        this(source.getName(), source.getDate(), source.getPlace(), source.getPersonsMeet(),
                source.getMeetTag());
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

    public void setPersonsMeet(List<ReadOnlyPerson> personsMeet) {
        this.personsMeet.set(personsMeet); }

    @Override
    public ObjectProperty<Place> placeProperty() {
        return place;
    }

    @Override
    public Place getPlace() {
        return place.get();
    }

    @Override
    public ObjectProperty<List<ReadOnlyPerson>> personsMeetProperty() {
        return personsMeet;
    }

    @Override
    public List<ReadOnlyPerson> getPersonsMeet() {
        return personsMeet.get();
    }

    @Override
    public ObjectProperty<MeetingTag> meetTagProperty() {
        return tag;
    }

    @Override
    public MeetingTag getMeetTag() {
        return tag.get();
    }
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
        if (personsMeet.get().size() > 1) {
            return getGroupText();
        }
        return getAsText();
    }

}
