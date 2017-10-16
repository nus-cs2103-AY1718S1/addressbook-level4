package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule {
    private ObjectProperty<ScheduleDate> date;
    private ObjectProperty<Activity> activity;

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleDate date, Activity activity) {
        requireAllNonNull(date, activity);
        this.date = new SimpleObjectProperty<>(date);
        this.activity = new SimpleObjectProperty<>(activity);
    }

    public void setScheduleDate(ScheduleDate date) {
        this.date.set(requireNonNull(date));
    }

    public ObjectProperty<ScheduleDate> dateProperty() {
        return date;
    }

    public ScheduleDate getScheduleDate() {
        return date.get();
    }

    public void setActivity(Activity activity) {
        this.activity.set(requireNonNull(activity));
    }

    public ObjectProperty<Activity> activityProperty() {
        return activity;
    }

    public Activity getActivity() {
        return activity.get();
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
                && other.getActivity().equals(this.getActivity()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, activity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getScheduleDate())
                .append(" Activity: ")
                .append(getActivity());
        return builder.toString();
    }
}
