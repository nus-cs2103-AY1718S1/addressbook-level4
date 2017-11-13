package seedu.address.model.event;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.timeslot.Timeslot;
import seedu.address.model.event.timeslot.Timing;

//@@author reginleiff
/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent, Comparable<Event> {

    private static final Logger logger = LogsCenter.getLogger(Event.class);

    private ObjectProperty<Title> title;
    private ObjectProperty<seedu.address.model.event.timeslot.Date> date;
    private ObjectProperty<Timing> timing;
    private ObjectProperty<Timeslot> timeslot;
    private ObjectProperty<Description> description;
    private ObjectProperty<Period> period;

    //The template event that this event is created from
    private Optional<ReadOnlyEvent> templateEvent;

    //The scheduled recurring event in the next period
    private Event nextScheduledEvent;


    /**
     * Every field must be present and not null.
     */
    public Event(Title title, Timeslot timeslot, Description description, Period period) {
        requireAllNonNull(title, timeslot, description, period);
        this.title = new SimpleObjectProperty<>(title);
        this.date = new SimpleObjectProperty<>(timeslot.getDate());
        this.timing = new SimpleObjectProperty<>(timeslot.getTiming());
        this.timeslot = new SimpleObjectProperty<>(timeslot);
        this.description = new SimpleObjectProperty<>(description);
        this.period = new SimpleObjectProperty<>(period);
        this.templateEvent = Optional.empty();

    }

    /**
     * Creates a copy of the given ReadOnlyEvent.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getTimeslot(), source.getDescription(), source.getPeriod());
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
    public ObjectProperty<seedu.address.model.event.timeslot.Date> dateProperty() {
        return date;
    }

    @Override
    public seedu.address.model.event.timeslot.Date getDate() {
        return date.get();
    }

    public void setDate(seedu.address.model.event.timeslot.Date date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<Timeslot> timeslotProperty() {
        return timeslot;
    }

    @Override
    public ObjectProperty<Timing> timingProperty() {
        return timing;
    }

    @Override
    public Timing getTiming() {
        return timing.get();
    }

    public void setTiming(Timing timing) {
        this.timing.set(requireNonNull(timing));
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

    //@@author shuang-yang
    @Override
    public ObjectProperty<Period> periodProperty() {
        return period;
    }

    @Override
    public Period getPeriod() {
        return period.get();
    }

    @Override
    public void setPeriod(Period period) {
        this.period.set(requireNonNull(period));
    }

    @Override
    public Optional<ReadOnlyEvent> getTemplateEvent() {
        return this.templateEvent;
    }

    @Override
    public void setTemplateEvent(Optional<ReadOnlyEvent> templateEvent) {
        this.templateEvent = templateEvent;
    }
    /**
     * Check if this event happens at an earlier time than the given timeslot.
     * @return true if indeed earlier.
     */
    public boolean happensBefore(Timeslot slot) {
        int comparison = this.getTimeslot().compareTo(slot);
        return comparison < 0;
    }

    /**
     * Check if this event happens at a later time than the given timeslot.
     * @return true if indeed earlier.
     */
    public boolean happensAfter(Timeslot slot) {
        int comparison = this.getTimeslot().compareTo(slot);
        return comparison > 0;
    }

    /**
     * Obtain the duration of the event.
     * @return a Duration object.
     */
    public Duration getDuration() {
        return Duration.ofMinutes(MINUTES.between(this.getStartTime(), this.getEndTime()));
    }

    /**
     * Obtain the start time of the event.
     * @return a LocalTime object.
     */
    public LocalTime getStartTime() {
        int start = this.getTiming().getStart();
        return LocalTime.of(start / 100, start % 100);
    }

    /**
     * Obtain the end time of the event.
     * @return a LocalTime object.
     */
    public LocalTime getEndTime() {
        int end = this.getTiming().getEnd();
        return LocalTime.of(end / 100, end % 100);
    }

    /**
     * Obtain the end time of the event.
     * @return a Date object.
     */
    public java.util.Date getEndDateTime() {
        LocalDate date = this.getDate().toLocalDate();
        LocalTime endTime = this.getEndTime();
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);
        return java.util.Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Check if two events have time clash.
     * @param other Event to compare with
     * @return true if clashes.
     */
    public boolean clashesWith(Event other) {
        int ts = this.getTiming().getStart();
        int te = this.getTiming().getEnd();
        int os = other.getTiming().getStart();
        int oe = other.getTiming().getEnd();

        if (this.getDate().equals(other.getDate()) && !(ts >= oe) && !(te <= os)) {
            return true;
        }
        return false;
    }

    /**
     * Adds a given number of days to the timeslot of the event
     * @param days number of days to add
     */

    public void plusDays(int days) {
        Timeslot newSlot = this.getTimeslot().plusDays(days);
        this.setTimeslot(newSlot);
    }

    @Override
    public int compareTo(Event other) {
        return this.getTimeslot().compareTo(other.getTimeslot());
    }
    //@@author

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

    public Event getNextScheduledEvent() {
        return nextScheduledEvent;
    }

    public void setNextScheduledEvent(Event nextScheduledEvent) {
        this.nextScheduledEvent = nextScheduledEvent;
    }
}
