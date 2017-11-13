package seedu.address.model.calendarevent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

//@@author yilun-zhu
/**
 * Represents a calendar event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class CalendarEvent implements ReadOnlyCalendarEvent {

    private ObjectProperty<EventName> name;
    private ObjectProperty<EventStartDate> startDate;
    private ObjectProperty<EventStartTime> startTime;
    private ObjectProperty<EventEndDate> endDate;
    private ObjectProperty<EventEndTime> endTime;


    /**
     * Every field must be present and not null.
     */
    public CalendarEvent(EventName name, EventStartDate startDate, EventStartTime startTime,
                         EventEndDate endDate, EventEndTime endTime) {
        requireAllNonNull(name, startDate, startTime, endDate, endTime);
        this.name = new SimpleObjectProperty<>(name);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endDate = new SimpleObjectProperty<>(endDate);
        this.endTime = new SimpleObjectProperty<>(endTime);
    }

    /**
     * Creates a copy of the given ReadOnlyCalendarEvent.
     */
    public CalendarEvent(ReadOnlyCalendarEvent source) {
        this(source.getEventName(), source.getStartDate(), source.getStartTime(),
                source.getEndDate(), source.getEndTime());
    }

    public void setName(EventName name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<EventName> nameProperty() {
        return name;
    }

    @Override
    public EventName getEventName() {
        return name.get();
    }

    public void setStartDate(EventStartDate date) {
        this.startDate.set(requireNonNull(date));
    }

    public void setStartTime(EventStartTime time) {
        this.startTime.set(requireNonNull(time));
    }

    public void setEndDate(EventEndDate date) {
        this.endDate.set(requireNonNull(date));
    }

    public void setEndTime(EventEndTime time) {
        this.endTime.set(requireNonNull(time));
    }

    @Override
    public ObjectProperty<EventEndDate> endDateProperty() {
        return this.endDate;
    }

    @Override
    public ObjectProperty<EventEndTime> endTimeProperty() {
        return this.endTime;
    }

    @Override
    public ObjectProperty<EventStartDate> startDateProperty() {
        return this.startDate;
    }

    @Override
    public ObjectProperty<EventStartTime> startTimeProperty() {
        return this.startTime;
    }

    @Override
    public EventStartDate getStartDate() {
        return this.startDate.get();
    }

    @Override
    public EventStartTime getStartTime() {
        return this.startTime.get();
    }

    @Override
    public EventEndDate getEndDate() {
        return this.endDate.get();
    }

    @Override
    public EventEndTime getEndTime() {
        return this.endTime.get();
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyCalendarEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyCalendarEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, startTime, endDate, endTime);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
