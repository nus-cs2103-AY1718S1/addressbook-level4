package seedu.address.model.person;

import java.util.Calendar;
import java.util.function.Predicate;

//@@author justinpoh
/**
 * Tests that a {@code ReadOnlyPerson}'s birthday is within this month and have not passed.
 * If a {@code ReadOnlyPerson} does not have a birthday recorded, return false.
 */
public class UpcomingBirthdayInCurrentMonthPredicate implements Predicate<ReadOnlyPerson> {
    private final int currentMonth;
    private final int currentDay;

    public UpcomingBirthdayInCurrentMonthPredicate() {
        // Added 1 because Calendar.getInstance().get(Calendar.MONTH) is zero-based.
        this.currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        this.currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * This constructor is used for testing purposes only.
     */
    private UpcomingBirthdayInCurrentMonthPredicate(int monthToFilter, int dayToFilter) {
        this.currentMonth = monthToFilter;
        this.currentDay = dayToFilter;
    }

    /**
     * Returns a UpcomingBirthdayInCurrentMonthPredicate instance that is used for testing,
     * initialized with {@code monthToFilter} and {@code dayToFilter}.
     */
    public static UpcomingBirthdayInCurrentMonthPredicate getTestInstance(int monthToFilter, int dayToFilter) {
        UpcomingBirthdayInCurrentMonthPredicate testPredicate =
                new UpcomingBirthdayInCurrentMonthPredicate(monthToFilter, dayToFilter);
        return testPredicate;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        final int month = person.getBirthday().getMonthOfBirthday();
        final int day = person.getBirthday().getDayOfBirthday();
        if (month == Birthday.EMPTY_BIRTHDAY_FIELD_MONTH) {
            return false;
        }

        if (month != currentMonth) {
            return false;
        }

        return day >= currentDay;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpcomingBirthdayInCurrentMonthPredicate // instanceof handles nulls
                && this.currentMonth == ((UpcomingBirthdayInCurrentMonthPredicate) other).currentMonth) // state check
                && this.currentDay == ((UpcomingBirthdayInCurrentMonthPredicate) other).currentDay;
    }
}
