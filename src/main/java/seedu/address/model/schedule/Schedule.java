package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.Name;

//@@author CT15
/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule {
    private ObjectProperty<ScheduleDate> scheduleDate;
    private ObjectProperty<Activity> activity;
    private ObjectProperty<Set<Name>> personInvolvedNames;

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleDate scheduleDate, Activity activity, Set<Name> personInvolvedNames) {
        requireAllNonNull(scheduleDate, activity, personInvolvedNames);
        this.scheduleDate = new SimpleObjectProperty<>(scheduleDate);
        this.activity = new SimpleObjectProperty<>(activity);
        this.personInvolvedNames = new SimpleObjectProperty<>(personInvolvedNames);
    }

    /**
     * Creates a copy of the given Schedule.
     */
    public Schedule(Schedule source) {
        this(source.getScheduleDate(), source.getActivity(), source.getPersonInvolvedNames());
    }

    public ScheduleDate getScheduleDate() {
        return scheduleDate.get();
    }

    public ObjectProperty<ScheduleDate> getScheduleDateProperty() {
        return scheduleDate;
    }

    public void setScheduleDate(ScheduleDate scheduleDate) {
        this.scheduleDate.set(scheduleDate);
    }

    public Activity getActivity() {
        return activity.get();
    }

    public ObjectProperty<Activity> getActivityProperty() {
        return activity;
    }

    //@@author 17navasaw
    public void setActivity(Activity activity) {
        this.activity.set(activity);
    }

    public Set<Name> getPersonInvolvedNames() {
        return personInvolvedNames.get();
    }

    public ObjectProperty<Set<Name>> getPersonInvolvedNamesProperty() {
        return personInvolvedNames;
    }

    public void setPersonInvolvedNames(Set<Name> personInvolvedNames) {
        this.personInvolvedNames.set(personInvolvedNames);
    }

    //@@author
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
                && other.getPersonInvolvedNames().equals(this.getPersonInvolvedNames()));
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
                .append(getPersonInvolvedNames());
        return builder.toString();
    }
}
