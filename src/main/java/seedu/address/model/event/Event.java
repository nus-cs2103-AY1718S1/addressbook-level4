package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.commons.util.EventOutputUtil;

//@@author eldriclim

/**
 * Represents a Event in the address book, accepts an event with no members.
 * Guarantees: validity upon creation; potential errors handled in UniqueEventList
 *
 * @see UniqueEventList
 */
public class Event {

    private ObjectProperty<MemberList> members;
    private ObjectProperty<EventName> eventName;
    private ObjectProperty<EventTime> eventTime;
    private ObjectProperty<EventDuration> eventDuration;
    private ObjectProperty<String> eventStatus = new SimpleObjectProperty<>();
    private ObjectProperty<String> eventStatusStyle = new SimpleObjectProperty<>();


    public Event(MemberList members, EventName eventName, EventTime eventStartTime, EventDuration eventDuration) {
        requireNonNull(members);
        requireNonNull(eventName);
        requireNonNull(eventStartTime);
        requireNonNull(eventDuration);

        this.members = new SimpleObjectProperty<>(members);
        this.eventName = new SimpleObjectProperty<>(eventName);
        this.eventTime = new SimpleObjectProperty<>(eventStartTime);
        this.eventDuration = new SimpleObjectProperty<>(eventDuration);

        setEventStatusDefaultState();

        assert getEventTime().getStart().plus(getEventDuration().getDuration()).equals(
                getEventTime().getEnd());
    }

    public Event(Event source) {
        this(source.getMemberList(), source.getEventName(), source.getEventTime(), source.getEventDuration());
    }

    public void setEventName(EventName eventName) {
        this.eventName.set(requireNonNull(eventName));
    }

    public ObjectProperty<EventName> eventNameProperty() {
        return eventName;
    }

    public EventName getEventName() {
        return eventName.get();
    }

    public void setEventTime(EventTime eventTime) {
        this.eventTime.set(requireNonNull(eventTime));
    }

    public ObjectProperty<EventTime> eventTimeProperty() {
        return eventTime;
    }

    public EventTime getEventTime() {
        return eventTime.get();
    }

    public void setEventDuration(EventDuration eventDuration) {
        this.eventDuration.set(requireNonNull(eventDuration));
    }

    public ObjectProperty<EventDuration> eventDurationProperty() {
        return eventDuration;
    }

    public EventDuration getEventDuration() {
        return eventDuration.get();
    }

    public void setMemberList(MemberList members) {
        this.members.set(requireNonNull(members));
    }

    public ObjectProperty<MemberList> eventMemberListProperty() {
        return members;
    }

    public MemberList getMemberList() {
        return members.get();
    }


    public ObjectProperty<String> eventStatusProperty() {
        return eventStatus;
    }

    public String getEventStatus() {
        return eventStatus.get();
    }

    public void setEventStatusDefaultState() {
        if (DateTimeUtil.containsReferenceDate(this, LocalDate.now())) {
            eventStatus.setValue("Today");
            eventStatusStyle.setValue("-fx-background-color: #fd720f");
        } else if (eventTime.get().isUpcoming()) {
            eventStatus.setValue("Upcoming");
            eventStatusStyle.setValue("-fx-background-color: #009e73");
        } else {
            eventStatus.setValue("Past");
            eventStatusStyle.setValue("-fx-background-color: #a31621");
        }
    }

    /**
     * Reset name and style of all status label
     *
     * @param date
     */
    public void updateEventStatusSelection(LocalDate date) {

        setEventStatusDefaultState();

        if (DateTimeUtil.containsReferenceDate(this, date)) {
            eventStatus.setValue("Selected");
            eventStatusStyle.setValue("-fx-background-color: #b91372");
        }
    }

    public ObjectProperty<String> eventStatusStyleProperty() {
        return eventStatusStyle;
    }

    public String getEventStatusStyle() {
        return eventStatusStyle.get();
    }

    /**
     * Returns true if Event is equal.
     *
     * @param other
     * @return true if all attributes are similar; false if otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && this.eventName.get().equals(((Event) other).getEventName())
                && this.eventTime.get().equals(((Event) other).getEventTime())
                && this.eventDuration.get().equals(((Event) other).getEventDuration())); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventName(), getEventTime(), getEventDuration());
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return EventOutputUtil.toStringEvent(getEventName(), getEventTime(), getEventDuration(), getMemberList());
    }

}
