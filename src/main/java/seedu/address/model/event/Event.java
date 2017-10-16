package seedu.address.model.event;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Person;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


public class Event implements ReadOnlyEvent{
    ObjectProperty<EventName> name;
    ObjectProperty<EventDescription> desc;
    ObjectProperty<EventTime> time;
    ObjectProperty<ParticipantList> participants;

    /**
     * Event name and time must be present and not null.
     */
    public Event (EventName name, EventDescription desc, EventTime time, Set<Person> participants){
        requireAllNonNull(name, time);
        this.name=new SimpleObjectProperty<>(name);
        this.desc=new SimpleObjectProperty<>(desc);
        this.time=new SimpleObjectProperty<>(time);
        this.participants=new SimpleObjectProperty<>(new ParticipantList(participants));
    }

    public Event (ReadOnlyEvent source){
        this(source.getEName(), source.getDesc(), source.getETime(), source.getParticipants());
    }

    public void setEName(EventName name){
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> EnameProperty() { return name; }

    @Override
    public EventName getEName() { return name.get(); }

    public void setEDesc(EventDescription desc) { this.desc.set(desc); }

    @Override
    public ObjectProperty<EventDescription> descProperty() { return desc; }

    @Override
    public EventDescription getDesc() {return desc.get(); }

    public void setETime(EventTime time) { this.time.set(requireNonNull(time)); }

    @Override
    public ObjectProperty<EventTime> timeProperty() { return time; }

    @Override
    public EventTime getETime() {return time.get(); }

    @Override
    public ObjectProperty<ParticipantList> participantProperty() {
        return participants;
    }
    @Override
    public Set<Person> getParticipants() {
        return Collections.unmodifiableSet(participants.get().toSet());
    }

    public void setParticipants(Set<Person> replacement) { this.participants.set(new ParticipantList(replacement)); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.EventIsSameStateAs((ReadOnlyEvent) other));
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
