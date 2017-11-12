package seedu.room.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.room.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

//@@author sushinoya
/**
 * Represents a Event in the event book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Title> title;
    private ObjectProperty<Description> description;
    private ObjectProperty<Location> location;
    private ObjectProperty<Datetime> datetime;
    private String sortCriteria = "title";

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Description description, Location location, Datetime datetime) {
        requireAllNonNull(title, description, location, datetime);
        this.title = new SimpleObjectProperty<>(title);
        this.description = new SimpleObjectProperty<>(description);
        this.location = new SimpleObjectProperty<>(location);
        this.datetime = new SimpleObjectProperty<>(datetime);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getDescription(), source.getLocation(), source.getDatetime());
    }

    @Override
    public ObjectProperty<Title> titleProperty() {
        return title;
    }

    @Override
    public Title getTitle() {
        return title.get();
    }

    public void setTitle(Title title) {
        this.title.set(requireNonNull(title));
    }

    @Override
    public ObjectProperty<Description> descriptionProperty() {
        return description;
    }

    @Override
    public Description getDescription() {
        return description.get();
    }

    public void setDescription(Description description) {
        this.description.set(requireNonNull(description));
    }

    @Override
    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    @Override
    public Location getLocation() {
        return location.get();
    }

    public void setLocation(Location location) {
        this.location.set(requireNonNull(location));
    }

    @Override
    public ObjectProperty<Datetime> datetimeProperty() {
        return datetime;
    }

    @Override
    public Datetime getDatetime() {
        return datetime.get();
    }

    public void setDatetime(Datetime datetime) {
        this.datetime.set(requireNonNull(datetime));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, description, location, datetime);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Sets the field the list should be sorted by
     */
    public void setComparator(String field) {
        Set<String> validFields = new HashSet<String>(Arrays.asList(
                new String[] {"title", "description", "location", "datetime"}
        ));

        if (validFields.contains(field)) {
            this.sortCriteria = field;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return this.title.getValue().equals(otherEvent.title.getValue())
                &&  this.location.getValue().equals(otherEvent.location.getValue())
                && this.datetime.getValue().equals(otherEvent.datetime.getValue());
    }

    //@@ author
    @Override
    public int compareTo(Object otherEvent) {

        ReadOnlyEvent event = (ReadOnlyEvent) otherEvent;
        String firstField = this.getTitle().toString();
        String secondField = event.getTitle().toString();

        if (sortCriteria.equals("title")) {
            firstField = this.getTitle().toString();
            secondField = event.getTitle().toString();

        } else if (sortCriteria.equals("location")) {
            firstField = this.getLocation().toString();
            secondField = event.getLocation().toString();

        } else if (sortCriteria.equals("datetime")) {
            firstField = this.getDatetime().toString();
            secondField = event.getDatetime().toString();
        } else {
            return firstField.compareTo(secondField);
        }

        // If a field is "Not Set" put the corresponding event at the end of the list.
        if (firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return 0;
        } else if (!firstField.equals("Not Set") && secondField.equals("Not Set")) {
            return -1;
        } else if (firstField.equals("Not Set") && !secondField.equals("Not Set")) {
            return 1;
        } else {
            return firstField.compareTo(secondField);
        }
    }
}
