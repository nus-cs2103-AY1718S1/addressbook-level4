package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Name;
import seedu.address.model.person.UniquePersonNameList;

//@@author CT15
/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Schedule implements Comparable<Schedule> {
    private static final Logger logger = LogsCenter.getLogger(Schedule.class);

    private ObjectProperty<ScheduleDate> scheduleDate;
    private ObjectProperty<Activity> activity;
    private ObjectProperty<UniquePersonNameList> personInvolvedNames;

    /**
     * Every field must be present and not null.
     */
    public Schedule(ScheduleDate scheduleDate, Activity activity, Set<Name> personInvolvedNames) {
        requireAllNonNull(scheduleDate, activity, personInvolvedNames);
        this.scheduleDate = new SimpleObjectProperty<>(scheduleDate);
        this.activity = new SimpleObjectProperty<>(activity);
        this.personInvolvedNames = new SimpleObjectProperty<>(new UniquePersonNameList(personInvolvedNames));
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
        return personInvolvedNames.get().toSet();
    }

    public ObjectProperty<UniquePersonNameList> getPersonInvolvedNamesProperty() {
        return personInvolvedNames;
    }

    public void setPersonInvolvedNames(Set<Name> personInvolvedNames) {
        this.personInvolvedNames.set(new UniquePersonNameList(personInvolvedNames));
    }

    /**
     * Returns true if {@code schedule} date is within 1 day from {@code currentDate}.
     */
    public static boolean doesScheduleNeedReminder(Schedule schedule) {
        LocalDate currentDate = LocalDate.now();
        logger.info("Current date: " + currentDate.toString());

        String scheduleDateString = schedule.getScheduleDate().value;

        LocalDate scheduleDateToAlter = currentDate;
        LocalDate scheduleDate = scheduleDateToAlter.withDayOfMonth(DateUtil.getDay(scheduleDateString))
                .withMonth(DateUtil.getMonth(scheduleDateString))
                .withYear(DateUtil.getYear(scheduleDateString));
        logger.info("Schedule date: " + scheduleDate.toString());

        LocalDate dayBeforeSchedule = scheduleDate.minusDays(1);
        final boolean isYearEqual = (dayBeforeSchedule.getYear() == currentDate.getYear());
        final boolean isMonthEqual = (dayBeforeSchedule.getMonthValue() == currentDate.getMonthValue());
        final boolean isDayEqual = (dayBeforeSchedule.getDayOfMonth() == currentDate.getDayOfMonth());

        if (isYearEqual && isMonthEqual && isDayEqual) {
            return true;
        } else {
            return false;
        }
    }

    //@@author CT15
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
        return Objects.hash(scheduleDate, activity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getScheduleDate())
                .append(" Activity: ")
                .append(getActivity())
                .append(" Person(s): ")
                .append(getPersonInvolvedNames());
        return builder.toString();
    }

    @Override
    public int compareTo(Schedule scheduleToCompare) {
        String schedule1DateInString = this.getScheduleDate().value;
        String schedule2DateInString = scheduleToCompare.getScheduleDate().value;

        int schedule1Year = DateUtil.getYear(schedule1DateInString);
        int schedule2Year = DateUtil.getYear(schedule2DateInString);
        if (schedule1Year != schedule2Year) {
            return schedule1Year - schedule2Year;
        }

        int schedule1Month = DateUtil.getMonth(schedule1DateInString);
        int schedule2Month = DateUtil.getMonth(schedule2DateInString);
        if (schedule1Month != schedule2Month) {
            return schedule1Month - schedule2Month;
        }

        int schedule1Day = DateUtil.getDay(schedule1DateInString);
        int schedule2Day = DateUtil.getDay(schedule2DateInString);
        return schedule1Day - schedule2Day;
    }
}
