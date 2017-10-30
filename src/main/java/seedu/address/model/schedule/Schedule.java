package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Name;

/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule {
    private ObjectProperty<ScheduleDate> scheduleDate;
    private ObjectProperty<Activity> activity;
    private ObjectProperty<Name> personInvolvedName;

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleDate scheduleDate, Activity activity, Name personInvolvedName) {
        requireAllNonNull(scheduleDate, activity, personInvolvedName);
        this.scheduleDate = new SimpleObjectProperty<>(scheduleDate);
        this.activity = new SimpleObjectProperty<>(activity);
        this.personInvolvedName = new SimpleObjectProperty<>(personInvolvedName);
    }

    /**
     * Creates a copy of the given Schedule.
     */
    public Schedule(Schedule source) {
        this(source.getScheduleDate(), source.getActivity(), source.getPersonInvolvedName());
    }

    public ScheduleDate getScheduleDate() {
        return scheduleDate.get();
    }

    public ObjectProperty<ScheduleDate> getScheduleDateProperty() {
        return scheduleDate;
    }

    public Activity getActivity() {
        return activity.get();
    }

    public ObjectProperty<Activity> getActivityProperty() {
        return activity;
    }

    public Name getPersonInvolvedName() {
        return personInvolvedName.get();
    }

    public ObjectProperty<Name> getPersonInvolvedNameProperty() {
        return personInvolvedName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.isSameStateAs((Schedule) other));
    }

    /**
     * Returns true if both {@code Schedule}have the same state.
     */
    private boolean isSameStateAs(Schedule other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getScheduleDate().equals(this.getScheduleDate()) // state checks here onwards
                && other.getActivity().equals(this.getActivity())
                && other.getPersonInvolvedName().equals(this.getPersonInvolvedName()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(scheduleDate, activity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getScheduleDate())
                .append(" Activity: ")
                .append(getActivity())
                .append(" Person: ")
                .append(getPersonInvolvedName());
        return builder.toString();
    }
}
