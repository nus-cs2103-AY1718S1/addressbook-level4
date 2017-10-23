package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule {
    public final ScheduleDate scheduleDate;
    public final Activity activity;

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleDate scheduleDate, Activity activity) {
        requireAllNonNull(scheduleDate, activity);
        this.scheduleDate = scheduleDate;
        this.activity = activity;
    }

    /**
     * Creates a copy of the given Schedule.
     */
    public Schedule(Schedule source) {
        this(source.getScheduleDate(), source.getActivity());
    }

    public ScheduleDate getScheduleDate() {
        return scheduleDate;
    }

    public Activity getActivity() {
        return activity;
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
        return Objects.hash(scheduleDate, activity);
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
