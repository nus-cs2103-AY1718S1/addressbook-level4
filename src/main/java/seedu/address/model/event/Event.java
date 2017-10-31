package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a Event in the address book.
 */
public class Event implements ReadOnlyEvent {
    private ObjectProperty<EventName> name;
    private ObjectProperty<EventDescription> desc;
    private ObjectProperty<EventTime> time;
    private ObjectProperty<ParticipantList> participants;
    private ObjectProperty<String> days;
    private EventTime eTime;

    /**
     * Event name and time must be present and not null.
     */
    public Event (EventName name, EventDescription desc, EventTime time, Set<Person> participants) {
        requireAllNonNull(name, time);
        this.eTime = time;
        this.days = new SimpleObjectProperty<>(time.getDaysLeft());
        this.name = new SimpleObjectProperty<>(name);
        this.desc = new SimpleObjectProperty<>(desc);
        this.time = new SimpleObjectProperty<>(time);
        this.participants = new SimpleObjectProperty<>(new ParticipantList(participants));
    }

    /**
     * Event name and time must be present and not null.
     */
    public Event (EventName name, EventDescription desc, EventTime time) {
        requireAllNonNull(name, time);
        this.eTime = time;
        this.days = new SimpleObjectProperty<>(time.getDaysLeft());
        this.name = new SimpleObjectProperty<>(name);
        this.desc = new SimpleObjectProperty<>(desc);
        this.time = new SimpleObjectProperty<>(time);
        this.participants = new SimpleObjectProperty<>(new ParticipantList());
    }

    public Event (ReadOnlyEvent source) {
        this(source.getEventName(), source.getDescription(), source.getEventTime(), source.getParticipants());
    }

    public void setEventName(EventName name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> eventNameProperty() {
        return name;
    }

    @Override
    public EventName getEventName() {
        return name.get();
    }

    public void setEventDescription(EventDescription desc) {
        this.desc.set(desc);
    }

    @Override
    public ObjectProperty<EventDescription> descriptionProperty() {
        return desc;
    }

    @Override
    public EventDescription getDescription() {
        return desc.get();
    }

    public void setETime(EventTime time) {
        this.time.set(requireNonNull(time));
    }

    @Override
    public ObjectProperty<EventTime> timeProperty() {
        return time;
    }

    @Override
    public EventTime getEventTime() {
        return time.get();
    }

    @Override
    public ObjectProperty<ParticipantList> participantProperty() {
        return participants;
    }

    @Override
    public Set<Person> getParticipants() {
        return Collections.unmodifiableSet(participants.get().toSet());
    }

    public ParticipantList getParticipantList() {
        return participants.get();
    }

    @Override
    public ObjectProperty<String> daysProperty() {
        return days;
    }
    /**
     * Replaces this event's participants with the persons in the argument set.
     */
    public void setParticipants(Set<Person> replacement) {
        this.participants.set(new ParticipantList(replacement));
    }

    public void removeParticipant(ReadOnlyPerson person) throws PersonNotFoundException {
        this.participants.get().remove(person);
    }

    public void addParticipant(Person person) throws DuplicatePersonException {
        this.participants.get().add(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.eventIsSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, desc, time, participants);
    }

    @Override
    public String toString() {
        return getEventAsText();
    }
}
