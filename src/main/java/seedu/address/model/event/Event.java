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
    ObjectProperty<EName> name;
    ObjectProperty<EDesc> desc;
    ObjectProperty<ETime> time;
    ObjectProperty<ParticipantList> participants;

    /**
     * Event name and time must be present and not null.
     */
    public Event (EName name, EDesc desc, ETime time, Set<Person> participants){
        requireAllNonNull(name, time);
        this.name=new SimpleObjectProperty<>(name);
        this.desc=new SimpleObjectProperty<>(desc);
        this.time=new SimpleObjectProperty<>(time);
        this.participants=new SimpleObjectProperty<>(new ParticipantList(participants));
    }

    public Event (ReadOnlyEvent source){
        this(source.getEName(), source.getDesc(), source.getETime(), source.getParticipants());
    }

    public void setEName(EName name){
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EName> EnameProperty() { return name; }

    @Override
    public EName getEName() { return name.get(); }

    public void setEDesc(EDesc desc) { this.desc.set(desc); }

    @Override
    public ObjectProperty<EDesc> descProperty() { return desc; }

    @Override
    public EDesc getDesc() {return desc.get(); }

    public void setETime(ETime time) { this.time.set(requireNonNull(time)); }

    @Override
    public ObjectProperty<ETime> timeProperty() { return time; }

    @Override
    public ETime getETime() {return time.get(); }

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
