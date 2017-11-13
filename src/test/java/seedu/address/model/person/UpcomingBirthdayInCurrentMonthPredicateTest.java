package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.UpcomingBirthdayInCurrentMonthPredicate.getTestInstance;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author justinpoh
public class UpcomingBirthdayInCurrentMonthPredicateTest {

    private static final int MONTH_DECEMBER = 12;
    private static final int MONTH_NOVEMBER = 11;
    private static final  int MONTH_OCTOBER = 10;
    private static final int DAY_TEN = 10;
    private static final int DAY_ELEVEN = 11;
    private static final int DAY_TWELVE = 12;
    private static final int DEFAULT_YEAR = 2017;
    private static final String FIELD_SEPARATOR = "-";

    @Test
    public void equals() {
        UpcomingBirthdayInCurrentMonthPredicate firstPredicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);
        UpcomingBirthdayInCurrentMonthPredicate secondPredicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);
        UpcomingBirthdayInCurrentMonthPredicate thirdPredicate = getTestInstance(MONTH_DECEMBER, DAY_ELEVEN);
        UpcomingBirthdayInCurrentMonthPredicate fourthPredicate = getTestInstance(MONTH_NOVEMBER, DAY_TWELVE);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> return true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different month -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

        // different day -> returns false
        assertFalse(firstPredicate.equals(fourthPredicate));
    }

    @Test
    public void test_personContainsBirthdayCorrectMonthAndDay_returnsTrue() {
        UpcomingBirthdayInCurrentMonthPredicate predicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);

        // same month and same day
        final String birthdaySameMonthSameDay = DAY_ELEVEN + FIELD_SEPARATOR + MONTH_NOVEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertTrue(predicate.test(new PersonBuilder().withBirthday(birthdaySameMonthSameDay).build()));

        // same month and later day
        final String birthdaySameMonthLaterDay = DAY_TWELVE + FIELD_SEPARATOR + MONTH_NOVEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertTrue(predicate.test(new PersonBuilder().withBirthday(birthdaySameMonthLaterDay).build()));

    }

    @Test
    public void test_personContainsBirthdayWrongMonthWrongDay_returnsFalse() {
        UpcomingBirthdayInCurrentMonthPredicate predicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);

        // same month and earlier day
        final String birthdaySameMonthEarlierDay = DAY_TEN + FIELD_SEPARATOR + MONTH_NOVEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertFalse(predicate.test(new PersonBuilder().withBirthday(birthdaySameMonthEarlierDay).build()));

        // different month same day
        final String birthdayEarlierMonthSameDay = DAY_ELEVEN + FIELD_SEPARATOR + MONTH_OCTOBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertFalse(predicate.test(new PersonBuilder().withBirthday(birthdayEarlierMonthSameDay).build()));

        final String birthdayLaterMonthSameDay = DAY_ELEVEN + FIELD_SEPARATOR + MONTH_DECEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertFalse(predicate.test(new PersonBuilder().withBirthday(birthdayLaterMonthSameDay).build()));
    }

    @Test
    public void test_personContainsNoBirthday_returnsFalse() {
        UpcomingBirthdayInCurrentMonthPredicate predicate = new UpcomingBirthdayInCurrentMonthPredicate();
        assertFalse(predicate.test(new PersonBuilder().withBirthday("").build()));
    }
}
