package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.event.Event;

/**
 * Represents an Reminder in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Reminder implements ReadOnlyReminder {
    private ObjectProperty<String> message;
    private ObjectProperty<Event> event;

    /**
     * Every field must be present and not null.
     */
    public Reminder(Event event, String message) {
        requireAllNonNull(message, event);
        this.event = new SimpleObjectProperty<>(event);
        this.message = new SimpleObjectProperty<>(message);
    }
    public Reminder() {
    }

    /**
     * Creates a copy of the given ReadOnlyReminder.
     */
    public Reminder(ReadOnlyReminder source) {
        this( source.getEvent(), source.getMessage());
    }
    @Override
    public ObjectProperty<Event> eventProperty() {
        return event;
    }
    @Override
    public Event getEvent() {
        return event.get();
    }

    public void setMessage(String message) {
        this.message.set(requireNonNull(message));
    }

    @Override
    public ObjectProperty<String> messageProperty() {
        return message;
    }

    @Override
    public String getMessage() {
        return message.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyReminder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyReminder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(event, message);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}

