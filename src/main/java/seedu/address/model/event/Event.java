package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.event.timeslot.Timeslot;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private ObjectProperty<Title> title;
    private ObjectProperty<Timeslot> timeslot;
    private ObjectProperty<Description> description;

    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Timeslot timeslot, Description description) {
        requireAllNonNull(title, timeslot, description);
        this.title = new SimpleObjectProperty<>(title);
        this.timeslot = new SimpleObjectProperty<>(timeslot);
        this.description = new SimpleObjectProperty<>(description);
    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getTimeslot(), source.getDescription());
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
    public ObjectProperty<Timeslot> timeslotProperty() {
        return timeslot;
    }

    @Override
    public Timeslot getTimeslot() {
        return timeslot.get();
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot.set(requireNonNull(timeslot));
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, timeslot, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
